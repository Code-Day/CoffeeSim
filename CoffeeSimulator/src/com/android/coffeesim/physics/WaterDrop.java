package com.android.coffeesim.physics;

import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.util.color.Color;

import com.badlogic.gdx.physics.box2d.FixtureDef;


public class WaterDrop
{
	public static final String ASSET_NAME = "droplet";
	public static final int MAX_DROPS = 100;
	private static final short DROP_MASK = PhysicsManager.ENV_CAT + PhysicsManager.DROP_CAT;
	public static final FixtureDef DROPLET_FIX = PhysicsFactory.createFixtureDef( 0.15f, -0.5f, 0.3f, false, PhysicsManager.DROP_CAT, DROP_MASK, (short)0 );
	public static final float RADIUS = 8;
	
	private Color mColor;
	private float mX, mY;
	
	public WaterDrop ( float pX, float pY, float r, float g, float b )
	{
		mX = pX;
		mY = pY;
		mColor = new Color (r, g, b);
	}
	
	public float getX ()
	{
		return mX;
	}
	public void setX ( float mX )
	{
		this.mX = mX;
	}
	public float getY ()
	{
		return mY;
	}
	public void setY ( float mY )
	{
		this.mY = mY;
	}
	public Color getColor ()
	{
		return mColor;
	}
	public void setColor ( Color mColor )
	{
		this.mColor = mColor;
	}
	
	
}
