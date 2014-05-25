
package com.android.coffeesim;


import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.BaseGameActivity;

import android.util.DisplayMetrics;
import android.view.Display;

import com.android.coffeesim.game.GameManager;
import com.android.coffeesim.physics.PhysicsManager;
import com.android.coffeesim.resourcemanager.ResourceManager;
import com.android.coffeesim.scene.CoffeeSimScene.AllScenes;
import com.android.coffeesim.scene.SceneManager;



/**
 * Main Activity
 *
 */
public class CoffeeSimMainActivity extends BaseGameActivity {

	//Main Camera
	private Camera mCamera;
	
	//Screen dimensions
	private int cameraWidth;
	private int cameraHeight;
	
	//Every manager
	private SceneManager sceneManager;
	private ResourceManager resourceManager;
	private GameManager gameManager;
	private PhysicsManager physicsManager;
	
	@Override
	public EngineOptions onCreateEngineOptions() {
		// TODO Auto-generated method stub
		
		//get width and height of screen
		Display display = getWindowManager().getDefaultDisplay();
		DisplayMetrics outMetrics = new DisplayMetrics();
		display.getMetrics(outMetrics);
		cameraWidth = outMetrics.widthPixels;
		cameraHeight = outMetrics.heightPixels;
		
		//make a camera that is the full screen
		mCamera = new Camera(0, 0, cameraWidth, cameraHeight);
		
		//create new Engine options: fullscreen, portrait, use our mCamera
		EngineOptions options = new EngineOptions(true,
				ScreenOrientation.PORTRAIT_FIXED, new RatioResolutionPolicy(
						cameraWidth, cameraHeight), mCamera);
		
		return options;
	}

	@Override
	public void onCreateResources(
			OnCreateResourcesCallback pOnCreateResourcesCallback)
			throws Exception {
		// TODO
		
		//make the managers
		resourceManager = new ResourceManager(mEngine, this);
		gameManager = new GameManager(this, mEngine, resourceManager, physicsManager);		
		physicsManager = new PhysicsManager(mEngine, resourceManager);
		sceneManager = new SceneManager(this, mEngine, resourceManager);

		
		//load splash resources
		resourceManager.onCreateSplashResources();

		pOnCreateResourcesCallback.onCreateResourcesFinished();
	}

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback)
			throws Exception {
		// TODO Auto-generated method stub
		
		//returns the splash scene
		pOnCreateSceneCallback.onCreateSceneFinished(sceneManager
				.createScene(AllScenes.SPLASH));
		
	}

	@Override
	public void onPopulateScene(Scene pScene,
			OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {
		// TODO Auto-generated method stub
		
		//Loads all other resources and creates their scenes then shows the menu scene
//		sceneManager.loadSceneResources(AllScenes.MENU);
//		sceneManager.loadSceneResources(AllScenes.GAME);
//		sceneManager.createScene(AllScenes.MENU);
//		sceneManager.createScene(AllScenes.GAME);
//		sceneManager.setCurrentScene(AllScenes.MENU);
		
		mEngine.registerUpdateHandler(new TimerHandler(3f,
				new ITimerCallback() {

					@Override
					public void onTimePassed(TimerHandler pTimerHandler) {
						// TODO Auto-generated method stub
						mEngine.unregisterUpdateHandler(pTimerHandler);
						resourceManager.onCreateResources();
						sceneManager.createScene(AllScenes.MENU);
						sceneManager.createScene(AllScenes.GAME);
						sceneManager.setCurrentScene(AllScenes.MENU);
					}
				}));
		
		pOnPopulateSceneCallback.onPopulateSceneFinished();
	}
}
