package com.android.coffeesim.game;

import org.andengine.engine.Engine;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.entity.sprite.Sprite;

import com.android.coffeesim.CoffeeSimMainActivity;
import com.android.coffeesim.physics.PhysicsManager;
import com.android.coffeesim.resourcemanager.ResourceManager;

public class GameManager {

	CoffeeSimMainActivity activity;
	Engine engine;
	ResourceManager resourceManager;
	PhysicsManager physicsManager;
	Scene gameScene;
	Rectangle coffeeGoal;

	public GameManager(CoffeeSimMainActivity act, Engine eng,
			ResourceManager r, PhysicsManager p) {
		// TODO Auto-generated constructor stub
		activity = act;
		engine = eng;
		resourceManager = r;
		physicsManager = p;

	}

	public void initialize(Scene s) {
		// buttons, speech bubble
		gameScene = s;
		ButtonSprite water = resourceManager.makeDatButtonSprite(
				"button_water", 16.0f, 16.0f);

		water.setSize(128.0f, 128.0f);
		water.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(ButtonSprite pButtonSprite,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				// TODO Auto-generated method stub
				// //set to water
				System.out.println("water bitches");

			}

		});

		ButtonSprite coffee = resourceManager.makeDatButtonSprite(
				"button_coffee", 16.0f,
				16.0f + water.getWidth() + 4);
		coffee.setSize(128.0f, 128.0f);
		coffee.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(ButtonSprite pButtonSprite,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				// TODO Auto-generated method stub
				// //set to water
				System.out.println("coffee bitches");

			}

		});

		ButtonSprite lid = resourceManager.makeDatButtonSprite("button_lid",
				16.0f, 16.0f + (water.getWidth()*2) + 8);
		lid.setSize(128.0f, 128.0f);
		lid.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(ButtonSprite pButtonSprite,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				// TODO Auto-generated method stub
				// //set to water
				System.out.println("lid bitches");

			}

		});
		
		Sprite speechBubble = resourceManager.makeDatSprite("speach_text", engine.getCamera().getWidth() - 256.0f, 0.0f);
		speechBubble.setSize(256.0f, 256.0f);
		
		Rectangle coffeeGoal = new Rectangle(engine.getCamera().getWidth() - 128 - 8, 128 - 8, 32, 32 + 8, engine.getVertexBufferObjectManager());
		coffeeGoal.setColor(100, 100, 100);
		
		gameScene.registerTouchArea(water);
		gameScene.attachChild(water);
		
		gameScene.registerTouchArea(coffee);
		gameScene.attachChild(coffee);
		
		gameScene.registerTouchArea(lid);
		gameScene.attachChild(lid);
		
		gameScene.attachChild(speechBubble);
		gameScene.attachChild(coffeeGoal);
	}

}
