package com.android.coffeesim.scene;

import org.andengine.engine.Engine;
import org.andengine.entity.scene.Scene;

import com.android.coffeesim.CoffeeSimMainActivity;
import com.android.coffeesim.scene.CoffeeSimScene.AllScenes;

public class SceneManager {

	private CoffeeSimScene coffeeSimScene;

	
	/**
	 * @param act - the main activity
	 * @param eng - the engine
	 */
	public SceneManager(CoffeeSimMainActivity act, Engine eng) {
		coffeeSimScene = new CoffeeSimScene(act, eng);
	}

	
	/**
	 * @param scene - the scene you would like to load
	 */
	public void loadSceneResources(AllScenes scene) {
		// TODO Auto-generated method stub
		
		coffeeSimScene.loadSceneResources(scene);
	}

	
	/**
	 * @param  - the scene you would like to load
	 * @return - the Scene you loaded
	 */
	public Scene createScene(AllScenes scene) {
		// TODO Auto-generated method stub
		
		return coffeeSimScene.createScene(scene);
	}
	
	/**
	 * @param cur - the scene you would like to set as the scene being shown
	 */
	public void setCurrentScene(AllScenes cur) {

		coffeeSimScene.setCurrentScene(cur);
	}

}
