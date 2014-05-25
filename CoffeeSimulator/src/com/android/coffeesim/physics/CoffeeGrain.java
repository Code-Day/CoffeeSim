package com.android.coffeesim.physics;

import org.andengine.extension.physics.box2d.PhysicsFactory;

import com.badlogic.gdx.physics.box2d.FixtureDef;


public class CoffeeGrain
{
	public static final String ASSET_NAME = "coffeegrain";
	private static final short COFF_MASK = PhysicsManager.ENV_CAT + PhysicsManager.COFF_CAT;
	public static final FixtureDef COFFEE_FIX = PhysicsFactory.createFixtureDef( 0.15f, -0.5f, 0.7f, false, PhysicsManager.COFF_CAT, COFF_MASK, (short)0 );
}