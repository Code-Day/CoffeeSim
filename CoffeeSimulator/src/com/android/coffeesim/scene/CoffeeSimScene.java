package com.android.coffeesim.scene;

import org.andengine.engine.Engine;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;

import com.android.coffeesim.CoffeeSimMainActivity;

public class CoffeeSimScene {
	
	private CoffeeSimMainActivity activity;
	private Engine engine;
	private Scene splashScene;
	private Scene menuScene;
	private Scene gameScene;
	
	public enum AllScenes {
		SPLASH, MENU, GAME
	}

	public CoffeeSimScene(CoffeeSimMainActivity act, Engine eng) {
		// TODO ADD RESOURCE MANAGER, PHYSICS MANAGER
		activity = act;
		engine = eng;
	}
	
	public void loadSplashResources() {
		// TODO Auto-generated method stub
		
		//List of things to load
		//splash image
		
	}

	public Scene createSplashScene() {
		splashScene = new Scene();
		splashScene.setBackground(new Background(12, 0, 100));
		//TODO
		//Sprite loading = resourceManager.getSprite("loading", (engine.getCamera().getCenterX()/2) - (resourceManager.getTR("loading").getWidth()/2), (engine.getCamera().getCenterY()/2) - (resourceManager.getTR("loading").getHeight()/2));
		//splashScene.attachChild(loading);
		return splashScene;
	}

	public void loadMenuResources() {
		// TODO Auto-generated method stub
		//List of things to load
		//background
		//start
	}

	public Scene createMenuScene() {
		// TODO Auto-generated method stub
		menuScene = new Scene();
		menuScene.setBackground(new Background(100, 12, 0)); //will be gone
		//Sprite background = resourceManager.getSprite("background", 0,0);
		//Sprite lid = resourceManager.getSprite("lid", (engine.getCamera().getCenterX()/2) - (resourceManager.getTR("lid").getWidth()/2), engine.getCamera().getCenterY() - resourceManager.getTR("frenchpress").getHeight() - 5 - resourceManager.getTR("lid").getHeight());
		//Sprite frenchPress = resourceManager.getSprite("frenchpress", (engine.getCamera().getCenterX()/2) - (resourceManager.getTR("frenchpress").getWidth()/2), engine.getCamera().getCenterY() - resourceManager.getTR("frenchpress").getHeight() - 5);
		//ButtonSprite start = resourceManager.getButtonSprite("start", (engine.getCamera().getCenterX()/2) - (resourceManager.getTR("start").getWidth()/2), (engine.getCamera().getCenterY()/2) - (resourceManager.getTR("start").getHeight()/2));
		// 

//		start.setOnClickListener(new OnClickListener(){
//
//			@Override
//			public void onClick(ButtonSprite pButtonSprite,
//					float pTouchAreaLocalX, float pTouchAreaLocalY) {
//				// TODO Auto-generated method stub
//				setCurrentScene(AllScenes.GAME);
//			}
//			
//		});
		
		//menuScene.attachChild(background);
		//menuScene.attachChild(frenchPress);
		//menuScene.attachChild(start);
		
		return menuScene;
		
	}
	
	public void loadGameResources() {
		// TODO Auto-generated method stub
		//background (already loaded)
		//coffee
		//water
		//lid (already loaded)
		//press (already loaded)
		//lidbutton
		//waterbutton
		//coffeebutton
		//pausebutton
		//speachbubble
		//brownRect
		
	}

	public Scene createGameScene() {
		// TODO Auto-generated method stub
		gameScene = new Scene();
		gameScene.setBackground(new Background(0, 100, 12));
		
		//physicsManager.initializePhysicsManager();
		return gameScene;
		
	}

	public void loadSceneResources(AllScenes scene) {
		// TODO Auto-generated method stub
		switch (scene) {
		case SPLASH:
			this.loadSplashResources();
			break;
		case MENU:
			this.loadMenuResources();
			break;
		case GAME:
			this.loadGameResources();
			break;
		default:
			break;
		}
	}

	public Scene createScene(AllScenes scene) {
		// TODO Auto-generated method stub
		
		switch (scene) {
		case SPLASH:
			return this.createSplashScene();
		case MENU:
			return this.createMenuScene();
		case GAME:
			return this.createGameScene();
		default:
			return null;
		}
	}
	
	public void setCurrentScene(AllScenes cur) {

		switch (cur) {
		case SPLASH:
			engine.setScene(splashScene);
			break;
		case MENU:
			engine.setScene(menuScene);
			break;
		case GAME:
			engine.setScene(gameScene);
			break;
		default:
			break;
		}
	}

}
