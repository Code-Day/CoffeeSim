package com.android.coffeesim.physics;

import java.util.ArrayList;

import org.andengine.engine.Engine;
import org.andengine.entity.primitive.Rectangle;
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

import com.android.coffeesim.resourcemanager.ResourceManager;
import com.android.coffeesim.scene.SceneManager;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.badlogic.gdx.physics.box2d.joints.LineJointDef;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;

public class PhysicsManager implements IOnSceneTouchListener, IOnAreaTouchListener
{

	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
			ITouchArea pTouchArea, float pTouchAreaLocalX,
			float pTouchAreaLocalY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		// TODO Auto-generated method stub
		return false;
	}
	/*public static final short ENV_CAT = 1;
	public static final short DROP_CAT = 2;
	public static final short COFF_CAT = 4;
	
	private final short ENV_MASK = PhysicsManager.COFF_CAT + PhysicsManager.ENV_CAT + PhysicsManager.DROP_CAT;
	
	private ResourceManager mResourceManager;
	private SceneManager mSceneManager;
	private VertexBufferObjectManager mVertexBufferObjectManager;
	private PhysicsWorld mPhysWorld;
	private SparseArray <MouseJoint> mMiceMap;
	private Engine mEngine;
	
	private ArrayList < WaterDrop > mWaterDrops;
	private ArrayList < CoffeeGrain > mCoffeeGrains;
	
	private ArrayList < Body > mBodies;
	private ArrayList < Joint > mJoints;
	
	private FixtureDef wallFix;
	
	public PhysicsManager ( Engine pEngine, SceneManager pSceneMgr, ResourceManager pResMgr )
	{
		mBodies = new ArrayList < Body > ();
		mJoints = new ArrayList < Joint > ();
		
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
		wallFix = PhysicsFactory.createFixtureDef(0, 0.5f, 0.5f, false, ENV_CAT, ENV_MASK, (short)0);
	}
	
	public void dispose ()
	{
		this.destroyAllThings ();
	}
	
	/**
	 * Convenience method to initialize or reinitialize the main game scene
	 */
	/*
	public void initializeGameScene ()
	{
		this.destroyAllThings ();
		
		createCoffeePot ();
		createCoffeePress ();
	}
	
	private void destroyAllThings ()
	{
		mPhysWorld.clearPhysicsConnectors ();
		for ( int i = 0; i < mBodies.size (); i ++ )
		{
			mPhysWorld.destroyBody ( mBodies.get ( i ) );
		}
		for ( int i = 0; i < mJoints.size (); i ++ )
		{
			mPhysWorld.destroyJoint ( mJoints.get ( i ) );
		}
	
		mWaterDrops.clear ();
		mCoffeeGrains.clear ();
		
		mBodies.clear ();
		mJoints.clear ();
	}
	
	private void createCoffeePot ()
	{
		final int cX = ( int ) mEngine.getCamera ().getCenterX ();
		final int cY = ( int ) ( mEngine.getCamera ().getHeight () - (mEngine.getCamera ().getHeight () * .2) );
		final int W = ( int ) ( mEngine.getCamera ().getWidth () * .34f );
		final int H = 3;
		
		Rectangle ground = new Rectangle ( cX, cY, W, H, this.mVertexBufferObjectManager );
		ground.setColor ( 0, 0, 1f );
		Rectangle left = new Rectangle ( cX, cY - W + H, H, W, this.mVertexBufferObjectManager );
		left.setColor ( 0, 1f, 0 );
		Rectangle right = new Rectangle ( cX + W, cY - W + H, H, W, this.mVertexBufferObjectManager );
		right.setColor ( 1f, 0, 0 );
		
		Body groundBody = PhysicsFactory.createBoxBody(this.mPhysWorld, cX, cY, W, H, BodyType.StaticBody, wallFix);
		Body leftBody = PhysicsFactory.createBoxBody(this.mPhysWorld, cX, cY - W + H, H, W, BodyType.StaticBody, wallFix);
		Body rightBody = PhysicsFactory.createBoxBody(this.mPhysWorld, cX + W, cY - W + H, H, W, BodyType.StaticBody, wallFix);
		
		mBodies.add ( groundBody );
		mBodies.add ( leftBody );
		mBodies.add ( rightBody );
		
		this.createWeldJointBetween ( groundBody, leftBody,
				(cX + H/2)/PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT,
				(cY + H/2)/PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT );
		this.createWeldJointBetween ( groundBody, rightBody, 
				(cX - H/2 + W)/PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT,
				(cY + H/2)/PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT );
		
		Sprite coffeePotSprite = mResourceManager.getSpriteByName ( "coffeepot" );
		coffeePotSprite.setSize ( W, cY - W + H );
		
		mPhysWorld.registerPhysicsConnector ( new PhysicsConnector ( coffeePotSprite, groundBody, false, false ) );
		mSceneManager.getCurrentScene ().attachChild ( coffeePotSprite );
		
		mSceneManager.getCurrentScene ().attachChild ( ground );
		mSceneManager.getCurrentScene ().attachChild ( left );
		mSceneManager.getCurrentScene ().attachChild ( right );
	}
	
	private void createCoffeePress ()
	{
		final int cX = ( int ) mEngine.getCamera ().getCenterX ();
		final int cY = ( int ) ( mEngine.getCamera ().getWidth () * .10f );
		final int barW = ( int ) ( mEngine.getCamera ().getWidth () * .05f );
		final int barH = ( int ) ( mEngine.getCamera ().getWidth () * .30f );
		
		Body handleTopBody = PhysicsFactory.createCircleBody(this.mPhysWorld, cX, cY, 32, BodyType.StaticBody, wallFix);
		Body handleBarBody = PhysicsFactory.createBoxBody(this.mPhysWorld, cX - (barW / 2), cY, barW, barH, BodyType.StaticBody, wallFix);
		
		Body groundBody = PhysicsFactory.createBoxBody(this.mPhysWorld, cX, cY, W, H, BodyType.StaticBody, wallFix);
		Body leftBody = PhysicsFactory.createBoxBody(this.mPhysWorld, cX, cY - W + H, H, W, BodyType.StaticBody, wallFix);
		Body rightBody = PhysicsFactory.createBoxBody(this.mPhysWorld, cX + W, cY - W + H, H, W, BodyType.StaticBody, wallFix);
		
		mBodies.add ( groundBody );
		mBodies.add ( leftBody );
		mBodies.add ( rightBody );
		
		this.createWeldJointBetween ( handleTopBody, handleBarBody,
				(cX + H/2)/PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT,
				(cY + H/2)/PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT );
		this.createWeldJointBetween ( groundBody, rightBody, 
				(cX - H/2 + W)/PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT,
				(cY + H/2)/PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT );
		
		Sprite coffeePotSprite = mResourceManager.getSpriteByName ( "coffeepot" );
		coffeePotSprite.setSize ( W, cY - W + H );
		
		mPhysWorld.registerPhysicsConnector ( new PhysicsConnector ( coffeePotSprite, groundBody, false, false ) );
		mSceneManager.getCurrentScene ().attachChild ( coffeePotSprite );
	}
	
	private void createWaterDrop ( float x, float y )
	{	
		Body dropletBody = PhysicsFactory.createCircleBody ( this.mPhysWorld, x, y, WaterDrop.RADIUS, BodyType.DynamicBody, WaterDrop.DROPLET_FIX );
		mBodies.add ( dropletBody );
		mWaterDrops.add ( new WaterDrop ( x, y, 0.0f, .61f, 1.0f ) );
		//mPhysWorld.registerPhysicsConnector ( new PhysicsConnector ( droplet, dropletBody, true, false ) );
		
		//mSceneManager.getCurrentScene ().attachChild ( droplet );
	}
	
	private void createCoffeegrain ( float x, float y )
	{
		Body coffeeGrainBody = PhysicsFactory.createCircleBody ( this.mPhysWorld, x, y, 16, BodyType.DynamicBody, CoffeeGrain.COFFEE_FIX );
		mBodies.add ( coffeeGrainBody );
		Sprite coffeegrain = mResourceManager.getSpriteByName ( CoffeeGrain.ASSET_NAME );
		
		mCoffeeGrains.add ( new CoffeeGrain () );
		
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
			if ( pTouchArea != null )
			{
				mMiceMap.put ( pSceneTouchEvent.getPointerID (), this.createMouseJoint ( obj, pTouchAreaLocalX, pTouchAreaLocalY ) );
				return true;
			}
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
	
	private void createLineJointBetween ( final Body pA, final Body pB, final float pAnchorX, final float pAnchorY )
	{
		final Vector2 anchor = Vector2Pool.obtain (pAnchorX, pAnchorY);
		final Vector2 axis = Vector2Pool.obtain (0, 10);
		final LineJointDef prisJointDef = new LineJointDef ();
		prisJointDef.initialize ( pA, pB, anchor, axis );
		prisJointDef.collideConnected = true;
		mPhysWorld.createJoint ( prisJointDef );
		Vector2Pool.recycle ( anchor );
	}
	
	private void createWeldJointBetween ( final Body pA, final Body pB, final float pAnchorX, final float pAnchorY )
	{
		final Vector2 anchor = Vector2Pool.obtain (pAnchorX, pAnchorY);
		final WeldJointDef weldJointDef = new WeldJointDef ();
		weldJointDef.initialize ( pA, pB, anchor );
		weldJointDef.collideConnected = true;
		mPhysWorld.createJoint ( weldJointDef );
		Vector2Pool.recycle ( anchor );
	}
	
	private void createDistanceJointBetween ( final Body pA, final Body pB, final float pAnchorXA, final float pAnchorYA, final float pAnchorXB, final float pAnchorYB )
	{
		final Vector2 anchorA = Vector2Pool.obtain (pAnchorXA, pAnchorYA);
		final Vector2 anchorB = Vector2Pool.obtain (pAnchorXB, pAnchorYB);
		final DistanceJointDef distJointDef = new DistanceJointDef ();
		distJointDef.initialize ( pA, pB, anchorA, anchorB );
		distJointDef.collideConnected = true;
		mPhysWorld.createJoint ( distJointDef );
		Vector2Pool.recycle ( anchorA );
		Vector2Pool.recycle ( anchorB );
	}
	*/
	
}

