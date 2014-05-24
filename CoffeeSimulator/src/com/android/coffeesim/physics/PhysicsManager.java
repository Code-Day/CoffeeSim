package com.android.coffeesim.physics;

import org.andengine.engine.Engine;
import org.andengine.entity.scene.IOnAreaTouchListener;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.shape.IAreaShape;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.physics.box2d.util.Vector2Pool;
import org.andengine.extension.physics.box2d.util.constants.PhysicsConstants;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.util.SparseArray;

import com.android.coffeesim.SceneManager;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;

public class PhysicsManager implements IOnSceneTouchListener, IOnAreaTouchListener
{
	private final short ENV_CAT = 1;
	private final short DROP_CAT = 2;
	private final short COFF_CAT = 4;
	
	private final short ENV_MASK = COFF_CAT + ENV_CAT + DROP_CAT;
	private final short DROP_MASK = ENV_CAT + DROP_CAT;
	private final short COFF_MASK = ENV_CAT + COFF_CAT;
	
	private ResourceManager mResourceManager;
	private SceneManager mSceneManager;
	private VertexBufferObjectManager mVertexBufferObjectManager;
	private PhysicsWorld mPhysWorld;
	private SparseArray <MouseJoint> mMiceMap;
	private Engine mEngine;
	
	private FixtureDef dropletFix;
	private FixtureDef coffeeGrainFix;
	private FixtureDef wallFix;
	
	public PhysicsManager ( Engine pEngine, SceneManager pSceneMgr, ResourceManager pResMgr )
	{
		mMiceMap = new SparseArray <MouseJoint> (4);
		mPhysWorld = new PhysicsWorld ( new Vector2 (0.0f, 9.81f), false );
		
		mVertexBufferObjectManager = pEngine.getVertexBufferObjectManager ();
	
		mEngine = pEngine;
		mResourceManager = pResMgr;
		
		createFixtureDefs ();
		
		mSceneManager = pSceneMgr;
		mSceneManager.getCurrentScene ().registerUpdateHandler ( mPhysWorld );
	}
	
	private void createFixtureDefs ()
	{
		dropletFix = PhysicsFactory.createFixtureDef( 0.15f, -0.5f, 0.3f, false, DROP_CAT, DROP_MASK, (short)0 );
		coffeeGrainFix = PhysicsFactory.createFixtureDef( 0.15f, -0.5f, 0.7f, false, COFF_CAT, COFF_MASK, (short)0 );
		wallFix = PhysicsFactory.createFixtureDef(0, 0.5f, 0.5f, false, ENV_CAT, ENV_MASK, (short)0);
	}
	
	/**
	 * Convenience method to initialize or reinitialize the main game scene
	 */
	public void initializeGameScene ()
	{
		
	}
	
	public void createCoffeePot ()
	{
		final int cX = ( int ) mEngine.getCamera ().getCenterX ();
		final int cY = ( int ) ( mEngine.getCamera ().getHeight () - (mEngine.getCamera ().getHeight () * .2) );
		final int W = ( int ) ( mEngine.getCamera ().getWidth () * .34f );
		final int H = 3;
		
		Body groundBody = PhysicsFactory.createBoxBody(this.mPhysWorld, cX, cY, W, H, BodyType.StaticBody, wallFix);
		Body leftBody = PhysicsFactory.createBoxBody(this.mPhysWorld, cX, cY - W + H, H, W, BodyType.StaticBody, wallFix);
		Body rightBody = PhysicsFactory.createBoxBody(this.mPhysWorld, cX + W, cY - W + H, H, W, BodyType.StaticBody, wallFix);
		
		this.createJointBetween ( groundBody, leftBody,
				(cX + H/2)/PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT,
				(cY + H/2)/PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT );
		this.createJointBetween ( groundBody, rightBody, 
				(cX - H/2 + W)/PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT,
				(cY + H/2)/PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT );
		
		Sprite coffeePotSprite = mResourceManager.getSpriteByName ( "coffeepot", 128, 384 );
		
		mPhysWorld.registerPhysicsConnector ( new PhysicsConnector ( coffeePotSprite, groundBody, false, false ) );
		mSceneManager.getCurrentScene ().attachChild ( coffeePotSprite );
	}
	
	public void createCoffeePress ()
	{
		
	}
	
	//TODO track num created for water and grains
	public void createWaterdrop ( float x, float y )
	{		
		Body dropletBody = PhysicsFactory.createCircleBody ( this.mPhysWorld, x, y, 8, BodyType.DynamicBody, dropletFix );
		Sprite droplet = mResourceManager.getSpriteByName ( "droplet", 32, 32 );
		mPhysWorld.registerPhysicsConnector ( new PhysicsConnector ( droplet, dropletBody, true, false ) );
		mSceneManager.getCurrentScene ().attachChild ( droplet );
	}
	
	public void createCoffeegrain ( float x, float y )
	{
		Body coffeeGrainBody = PhysicsFactory.createCircleBody ( this.mPhysWorld, x, y, 8, BodyType.DynamicBody, coffeeGrainFix );
		Sprite coffeegrain = mResourceManager.getSpriteByName ( "coffeegrain", 32, 32 );
		mPhysWorld.registerPhysicsConnector ( new PhysicsConnector ( coffeegrain, coffeeGrainBody, true, true ) );
		mSceneManager.getCurrentScene ().attachChild ( coffeegrain );
	}
	
	@ Override
	public boolean onSceneTouchEvent ( final Scene pScene, final TouchEvent pSceneTouchEvent )
	{
		if ( this.mPhysWorld != null )
		{
			switch ( pSceneTouchEvent.getAction () )
			{
				case TouchEvent.ACTION_DOWN :
					return true;
				case TouchEvent.ACTION_MOVE :
					if ( mMiceMap.get ( pSceneTouchEvent.getPointerID () ) != null )
					{
						final Vector2 vec = Vector2Pool.obtain ( 
								pSceneTouchEvent.getX () / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT,
								pSceneTouchEvent.getY () / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT );
						mMiceMap.get ( pSceneTouchEvent.getPointerID () ).setTarget ( vec );
						Vector2Pool.recycle ( vec );
					}
					return true;
				case TouchEvent.ACTION_UP :
					if ( mMiceMap.get ( pSceneTouchEvent.getPointerID () ) != null )
					{
						this.mPhysWorld.destroyJoint ( mMiceMap.get ( pSceneTouchEvent.getPointerID () ) );
						mMiceMap.remove ( pSceneTouchEvent.getPointerID () );
					}
					return true;
			}
			return false;
		}
		return false;
	}

	@ Override
	public boolean onAreaTouched ( final TouchEvent pSceneTouchEvent, final ITouchArea pTouchArea, final float pTouchAreaLocalX, final float pTouchAreaLocalY )
	{
		if ( pSceneTouchEvent.isActionDown () )
		{
			final IAreaShape obj = ( IAreaShape ) pTouchArea;
			mMiceMap.put ( pSceneTouchEvent.getPointerID (), this.createMouseJoint ( obj, pTouchAreaLocalX, pTouchAreaLocalY ) );
			return true;
		}
		return false;
	}
	
	public MouseJoint createMouseJoint ( final IAreaShape pBody, final float pTouchAreaLocalX, final float pTouchAreaLocalY )
	{
		final Body body = ( Body ) pBody.getUserData ();
		final MouseJointDef mouseJointDef = new MouseJointDef ();
		final Vector2 localPoint = Vector2Pool.obtain ( ( pTouchAreaLocalX - pBody.getWidth () * 0.5f ) / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT, ( pTouchAreaLocalY - pBody.getHeight () * 0.5f ) / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT );
		
		Body b = this.mPhysWorld.createBody(new BodyDef());
		b.setTransform ( localPoint, 0 );
		mouseJointDef.bodyA = b;
		
		mouseJointDef.bodyB = body;
		mouseJointDef.dampingRatio = 0.95f;
		mouseJointDef.frequencyHz = 30;
		mouseJointDef.maxForce = ( 500.0f * body.getMass () );
		mouseJointDef.collideConnected = true;
		mouseJointDef.target.set ( body.getWorldPoint ( localPoint ) );
		
		Vector2Pool.recycle ( localPoint );
		return ( MouseJoint ) this.mPhysWorld.createJoint ( mouseJointDef );
	}
	
	public void createJointBetween ( final Body pA, final Body pB, final float pAnchorX, final float pAnchorY )
	{
		final WeldJointDef weldJointDef = new WeldJointDef ();
		weldJointDef.initialize ( pA, pB, Vector2Pool.obtain (pAnchorX, pAnchorY) );
		weldJointDef.collideConnected = true;
		mPhysWorld.createJoint ( weldJointDef );
	}
}
