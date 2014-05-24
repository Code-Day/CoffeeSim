package com.android.coffeesim.scene;

import org.andengine.engine.Engine;
import org.andengine.entity.scene.Scene;

import com.android.coffeesim.CoffeeSimMainActivity;
import com.android.coffeesim.scene.CoffeeSimScene.AllScenes;

public class SceneManager {

	private CoffeeSimScene coffeeSimScene;

	
	public SceneManager(CoffeeSimMainActivity act, Engine eng) {
		coffeeSimScene = new CoffeeSimScene(act, eng);
	}

	public void loadSceneResources(AllScenes scene) {
		// TODO Auto-generated method stub
		
		coffeeSimScene.loadSceneResources(scene);
	}

	public Scene createScene(AllScenes scene) {
		// TODO Auto-generated method stub
		
		return coffeeSimScene.createScene(scene);
	}
	
	public void setCurrentScene(AllScenes cur) {

		coffeeSimScene.setCurrentScene(cur);
	}

}
