//package org.andengine.examples;
package com.android.coffeesim.resourcemanager;

import java.util.HashMap;

import org.andengine.engine.Engine;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;

import com.android.coffeesim.CoffeeSimMainActivity;

/**
 * David Parsons
 */
public class ResourceManager {

	// ===========================================================
	// Fields
	// ===========================================================
	
	// for actual sprites (later)
	//	private BuildableBitmapTextureAtlas mBitmapTextureAtlas;
	//	
	//	private TiledTextureRegion mSnapdragonTextureRegion;
	//	private TiledTextureRegion mHelicopterTextureRegion;
	//	private TiledTextureRegion mBananaTextureRegion;
	//	private TiledTextureRegion mFaceTextureRegionT;
	
	// ===========================================================
	// private variables
	// ===========================================================
	private HashMap<String, ITextureRegion> images;
	private Engine engine;
	private CoffeeSimMainActivity activity;
	
	// ===========================================================
	// constructor
	// ===========================================================
	public ResourceManager(Engine eng, CoffeeSimMainActivity act) {
		engine = eng;
		activity = act;
		images = new HashMap<String, ITextureRegion>();
	}

	// ===========================================================
	// This function grabs a single image to load before game starts
	// ===========================================================
	public void onCreateSplashResources() {
		// texture
		BitmapTextureAtlas splashTexture;
		// texture region
		ITextureRegion splashTextureRegion;
				
		// Set the gfx folder as our base
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("splash/");
		
		// set playerTexture (note: width and hight must be set to power of x^2)
		splashTexture = new BitmapTextureAtlas(engine.getTextureManager(), 256, 256);
		// grab the image and put it in a region (0,0 stands for top left corner of the atlas and gets the texture region)
		splashTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(splashTexture, activity,"splash.png",0,0);
		
		// load all the textures (can unload: playerTexture.unload();)
		splashTexture.load();
		
		// add all the textures to the hashmap
		images.put("splash", splashTextureRegion);
	}
	
	// ===========================================================
	// This function grabs a single image to load before game starts
	// ===========================================================
	public void onCreateResources() {
		// texture
		BitmapTextureAtlas backgroundTexture;
		BitmapTextureAtlas buttonStartTexture;
		BitmapTextureAtlas buttonWaterTexture;
		BitmapTextureAtlas buttonCoffeeTexture;
		BitmapTextureAtlas buttonLidTexture;
		BitmapTextureAtlas speachTextTexture;
		BitmapTextureAtlas pressMainTexture;
		BitmapTextureAtlas pressTopTexture;
		
		// texture region
		ITextureRegion backgroundTextureRegion;
		ITextureRegion buttonStartTextureRegion;
		ITextureRegion buttonWaterTextureRegion;
		ITextureRegion buttonCoffeeTextureRegion;
		ITextureRegion buttonLidTextureRegion;
		ITextureRegion speachTextTextureRegion;
		ITextureRegion pressMainTextureRegion;
		ITextureRegion pressTopTextureRegion;
				
		// Set the gfx folder as our base
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		
		// set playerTexture (note: width and hight must be set to power of x^2)
		backgroundTexture = new BitmapTextureAtlas(engine.getTextureManager(), 1080, 1920);
		buttonStartTexture = new BitmapTextureAtlas(engine.getTextureManager(), 256, 256);
		buttonWaterTexture = new BitmapTextureAtlas(engine.getTextureManager(), 256, 256);
		buttonCoffeeTexture = new BitmapTextureAtlas(engine.getTextureManager(), 256, 256);
		buttonLidTexture = new BitmapTextureAtlas(engine.getTextureManager(), 256, 256);
		speachTextTexture = new BitmapTextureAtlas(engine.getTextureManager(), 256, 256);
		pressMainTexture = new BitmapTextureAtlas(engine.getTextureManager(), 1000, 750);
		pressTopTexture = new BitmapTextureAtlas(engine.getTextureManager(), 1000, 250);
		
		// grab the image and put it in a region (0,0 stands for top left corner of the atlas and gets the texture region)
		backgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backgroundTexture, activity,"GameBackground.png",0,0);
		buttonStartTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(buttonStartTexture, activity,"button_start.png",0,0);
		buttonWaterTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(buttonWaterTexture, activity,"button_water.png",0,0);
		buttonCoffeeTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(buttonCoffeeTexture, activity,"button_coffee.png",0,0);
		buttonLidTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(buttonLidTexture, activity,"button_lid.png",0,0);
		speachTextTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(speachTextTexture, activity,"speech_text.png",0,0);
		pressMainTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(pressMainTexture, activity,"press_main.png",0,0);
		pressTopTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(pressTopTexture, activity,"press_top.png",0,0);
		
		// load all the textures (can unload: playerTexture.unload();)
		backgroundTexture.load();
		buttonStartTexture.load();
		buttonWaterTexture.load();
		buttonCoffeeTexture.load();
		buttonLidTexture.load();
		speachTextTexture.load();
		pressMainTexture.load();
		pressTopTexture.load();
		
		// add all the textures to the hashmap
		images.put("background", backgroundTextureRegion);
		images.put("button_start", buttonStartTextureRegion);
		images.put("button_water", buttonWaterTextureRegion);
		images.put("button_coffee", buttonCoffeeTextureRegion);
		images.put("button_lid", buttonLidTextureRegion);
		images.put("speach_text", speachTextTextureRegion);
		images.put("press_main", pressMainTextureRegion);
		images.put("press_top", pressTopTextureRegion);

//		// ADVNACED METHOD FOR SPRITES... LATER
//		this.mBitmapTextureAtlas = new BuildableBitmapTextureAtlas(this.getTextureManager(), 512, 256, TextureOptions.NEAREST);
//		//this.mBitmapTextureAtlas = new BuildableBitmapTextureAtlas(this.getTextureManager(), 512, 256, TextureOptions.BILINEAR);
//		
//		this.mSnapdragonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mBitmapTextureAtlas, this, "snapdragon_tiled.png", 4, 3);
//		this.mHelicopterTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mBitmapTextureAtlas, this, "helicopter_tiled.png", 2, 2);
//		this.mBananaTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mBitmapTextureAtlas, this, "banana_tiled.png", 4, 2);
//		this.mFaceTextureRegionT = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mBitmapTextureAtlas, this, "face_box_tiled.png", 2, 1);
//
//		try {
//			this.mBitmapTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 1));
//			this.mBitmapTextureAtlas.load();
//		} catch (TextureAtlasBuilderException e) {
//			Debug.e(e);
//		}
	}
	
	// ===========================================================
	// 1. check hashmap to see if we have texture
	// 2. if we have texture, create sprite and return
	// ===========================================================
	public Sprite makeDatSprite(String theTexture, Float w, Float h) {
		// check dat hashmap
		if (images.containsKey(theTexture)) {
			// create dat sprite
			Sprite sPlayer = new Sprite(w, h, images.get(theTexture), engine.getVertexBufferObjectManager());
			// TEST
			//sPlayer.setRotation(45.0f);
			return sPlayer;
		}
		return null;
	}
	
	// ===========================================================
	// 1. check hashmap to see if we have texture
	// 2. if we have texture, create sprite and return
	// ===========================================================
	public ButtonSprite makeDatButtonSprite(String theTexture, Float w, Float h) {
		// check dat hashmap
		if (images.containsKey(theTexture)) {
			// create dat sprite
			ButtonSprite sPlayer = new ButtonSprite(w, h, images.get(theTexture), engine.getVertexBufferObjectManager());
			// TEST
			//sPlayer.setRotation(45.0f);
			return sPlayer;
		}
		return null;
	}
	
	// ===========================================================
	// 1. check hashmap to see if we have texture
	// 2. if we have texture, return the texture region
	// ===========================================================
	public ITextureRegion getDatTextureRegion(String theTexture) {
		// check dat hashmap
		if (images.containsKey(theTexture)) {
			// return dat Texture Region
			return images.get(theTexture);
		}
		return null;
	}

}
