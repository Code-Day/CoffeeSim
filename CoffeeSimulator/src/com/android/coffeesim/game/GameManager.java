package com.android.coffeesim.game;

import org.andengine.engine.Engine;

import com.android.coffeesim.CoffeeSimMainActivity;
import com.android.coffeesim.physics.PhysicsManager;
import com.android.coffeesim.resourcemanager.ResourceManager;

public class GameManager {

	CoffeeSimMainActivity activity;
	Engine engine;
	ResourceManager resourceManager;
	PhysicsManager physicsManager;
	
	public GameManager(CoffeeSimMainActivity act, Engine eng, ResourceManager r, PhysicsManager p) {
		// TODO Auto-generated constructor stub
		activity = act;
		engine = eng;
		resourceManager = r;
		physicsManager = p;
		
	}

}
