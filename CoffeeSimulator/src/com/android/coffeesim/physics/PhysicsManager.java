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
	public static final short ENV_CAT = 1;
	public static final short DROP_CAT = 2;
	public static final short COFF_CAT = 4;
	public static final short FILTER_CAT = 8;
	
	private final short FILTER_MASK = PhysicsManager.COFF_CAT + PhysicsManager.ENV_CAT;
	private final short ENV_MASK = PhysicsManager.COFF_CAT + PhysicsManager.ENV_CAT + PhysicsManager.DROP_CAT;
	
	private SceneManager mSceneManager;
	private ResourceManager mResourceManager;
	private VertexBufferObjectManager mVertexBufferObjectManager;
	private PhysicsWorld mPhysWorld;
	private SparseArray <MouseJoint> mMiceMap;
	private Engine mEngine;
	
	private ArrayList < WaterDrop > mWaterDrops;
	private ArrayList < CoffeeGrain > mCoffeeGrains;
	
	private ArrayList < Body > mBodies;
	private ArrayList < Joint > mJoints;
	
	private FixtureDef filterFix;
	private FixtureDef wallFix;
	
	private float cX;
	private float cY;
	private float W910;
	private float W10;
	private float H10;
	private float H5;
	private float H310;
	private float H610;
	private float H910;
	
	private float sideLen;
	private float halfSideLen;
	private float bodyThickness;
	private float halfThickness;
	
	
	public PhysicsManager ( Engine pEngine, SceneManager pSceneManager, ResourceManager pResMgr )
	{
		mBodies = new ArrayList < Body > ();
		mJoints = new ArrayList < Joint > ();
		
		mMiceMap = new SparseArray <MouseJoint> (4);
		mPhysWorld = new PhysicsWorld ( new Vector2 (0.0f, 9.81f), false );
		
		mWaterDrops = new ArrayList < WaterDrop > ( WaterDrop.MAX_DROPS );
		mCoffeeGrains = new ArrayList < CoffeeGrain > ();
		
		mVertexBufferObjectManager = pEngine.getVertexBufferObjectManager ();
	
		mSceneManager = pSceneManager;
		mEngine = pEngine;
		mResourceManager = pResMgr;
		
		createFixtureDefs ();
		
		cX = mEngine.getCamera ().getCenterX ();
		cY = mEngine.getCamera ().getHeight () - ( mEngine.getCamera ().getHeight () * .2f );
		W910 = ( mEngine.getCamera ().getWidth () / 10 ) * 9;
		W10 = mEngine.getCamera ().getWidth () / 10;
		H10 = mEngine.getCamera ().getHeight () / 10;
		H5 = mEngine.getCamera ().getHeight () / 5;
		H310 = ( mEngine.getCamera ().getHeight () / 10 ) * 3;
		H610 = ( mEngine.getCamera ().getHeight () / 10 ) * 6;
		H910 = ( mEngine.getCamera ().getHeight () / 10 ) * 9;
		
		sideLen = ( mEngine.getCamera ().getWidth () * .34f );
		halfSideLen = ( mEngine.getCamera ().getWidth () * .34f ) / 2;
		bodyThickness = 3;
		halfThickness = bodyThickness / 2;
		
		
		mSceneManager.getCurScene ().registerUpdateHandler ( mPhysWorld );
	}
	
	private void createFixtureDefs ()
	{
		wallFix = PhysicsFactory.createFixtureDef(0, 0.5f, 0.5f, false, ENV_CAT, ENV_MASK, (short)0);
		filterFix = PhysicsFactory.createFixtureDef(0, 0.5f, 0.5f, false, FILTER_CAT, FILTER_MASK, (short)0);
	}
	
	public void dispose ()
	{
		this.destroyAllThings ();
	}
	
	/**
	 * Convenience method to initialize or reinitialize the main game scene
	 */
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
		Rectangle ground = new Rectangle ( cX, H910, sideLen, bodyThickness, this.mVertexBufferObjectManager );
		ground.setColor ( 0, 0, 1f );
		Rectangle left = new Rectangle ( W10 - halfThickness, H610 - halfSideLen, bodyThickness, sideLen, this.mVertexBufferObjectManager );
		left.setColor ( 0, 1f, 0 );
		Rectangle right = new Rectangle ( W910  - halfThickness, H610 - halfSideLen, bodyThickness, sideLen, this.mVertexBufferObjectManager );
		right.setColor ( 1f, 0, 0 );
		
		Body groundBody = PhysicsFactory.createBoxBody(this.mPhysWorld, cX, H910, sideLen, bodyThickness, BodyType.StaticBody, wallFix);
		Body leftBody = PhysicsFactory.createBoxBody(this.mPhysWorld, W10, H610, bodyThickness, sideLen, BodyType.StaticBody, wallFix);
		Body rightBody = PhysicsFactory.createBoxBody(this.mPhysWorld, W910, H610, bodyThickness, sideLen, BodyType.StaticBody, wallFix);
		
		mBodies.add ( groundBody );
		mBodies.add ( leftBody );
		mBodies.add ( rightBody );
		
		this.createWeldJointBetween ( groundBody, leftBody,
				(W10)/PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT,
				(H910)/PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT );
		this.createWeldJointBetween ( groundBody, rightBody, 
				(W910)/PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT,
				(H910)/PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT );
		
		Sprite coffeePotSprite = mResourceManager.makeDatSprite ( "coffeepot", W10 - halfThickness, H610 - halfSideLen );
		coffeePotSprite.setSize ( sideLen, sideLen );
		
		mPhysWorld.registerPhysicsConnector ( new PhysicsConnector ( coffeePotSprite, groundBody, false, false ) );
		mSceneManager.getCurScene ().attachChild ( coffeePotSprite );
		
		mSceneManager.getCurScene ().attachChild ( ground );
		mSceneManager.getCurScene ().attachChild ( left );
		mSceneManager.getCurScene ().attachChild ( right );
	}
	
	private void createCoffeePress ()
	{
		Rectangle handleTop = new Rectangle ( cX - 16, H10 - 16, 32, 32, this.mVertexBufferObjectManager );
		handleTop.setColor ( 0, 0, 1f );
		Rectangle handleBar = new Rectangle ( cX - halfThickness, H5 - halfSideLen, bodyThickness, sideLen, this.mVertexBufferObjectManager );
		handleBar.setColor ( 0, 1f, 0 );
		Rectangle filter = new Rectangle ( cX - halfSideLen, H310 - halfThickness, sideLen, bodyThickness, this.mVertexBufferObjectManager );
		filter.setColor ( 1f, 0, 0 );
		
		Body handleTopBody = PhysicsFactory.createCircleBody ( this.mPhysWorld, cX, H10, 32, BodyType.StaticBody, wallFix );
		Body handleBarBody = PhysicsFactory.createBoxBody ( this.mPhysWorld, cX, H5, bodyThickness, sideLen, BodyType.StaticBody, wallFix );
		Body filterBody = PhysicsFactory.createBoxBody ( this.mPhysWorld, cX, H310, sideLen, bodyThickness, BodyType.StaticBody, filterFix );
		
		mBodies.add ( handleTopBody );
		mBodies.add ( handleTopBody );
		mBodies.add ( filterBody );
		
		this.createWeldJointBetween ( handleTopBody, handleBarBody,
				(cX)/PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT,
				(H10)/PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT );
		this.createWeldJointBetween ( handleBarBody, filterBody, 
				(cX)/PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT,
				(H310)/PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT );
		
		mPhysWorld.registerPhysicsConnector ( new PhysicsConnector ( handleTop, handleTopBody, true, false ) );
		mPhysWorld.registerPhysicsConnector ( new PhysicsConnector ( handleBar, handleBarBody, true, false ) );
		mPhysWorld.registerPhysicsConnector ( new PhysicsConnector ( filter, filterBody, true, false ) );
		mSceneManager.getCurScene ().attachChild ( handleTop );
		mSceneManager.getCurScene ().attachChild ( handleBar );
		mSceneManager.getCurScene ().attachChild ( filter );
	}
	
	private void createWaterDrop ( float x, float y )
	{	
		Sprite droplet = mResourceManager.makeDatSprite ( WaterDrop.ASSET_NAME, x, y );
		Body dropletBody = PhysicsFactory.createCircleBody ( this.mPhysWorld, x, y, WaterDrop.RADIUS, BodyType.DynamicBody, WaterDrop.DROPLET_FIX );
		mBodies.add ( dropletBody );
		mWaterDrops.add ( new WaterDrop ( x, y, 0.0f, .61f, 1.0f ) );
		
		if ( mWaterDrops.size () > WaterDrop.MAX_DROPS )
		{
			mPhysWorld.destroyBody ( mBodies.remove ( 0 ) );
			mWaterDrops.remove ( 0 );
		}
		mPhysWorld.registerPhysicsConnector ( new PhysicsConnector ( droplet, dropletBody, true, false ) );
		
		mSceneManager.getCurScene ().attachChild ( droplet );
	}
	
	private void createCoffeegrain ( float x, float y )
	{
		Body coffeeGrainBody = PhysicsFactory.createCircleBody ( this.mPhysWorld, x, y, 16, BodyType.DynamicBody, CoffeeGrain.COFFEE_FIX );
		mBodies.add ( coffeeGrainBody );
		Sprite coffeegrain = mResourceManager.makeDatSprite ( CoffeeGrain.ASSET_NAME, x, y );
		
		mCoffeeGrains.add ( new CoffeeGrain () );
		
		mPhysWorld.registerPhysicsConnector ( new PhysicsConnector ( coffeegrain, coffeeGrainBody, true, true ) );
		mSceneManager.getCurScene ().attachChild ( coffeegrain );
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
}
