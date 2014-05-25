//package org.andengine.examples;
package com.android.coffeesim.resourcemanager;

import java.util.HashMap;

import org.andengine.engine.Engine;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
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
		BitmapTextureAtlas playerTexture;
		// texture region
		ITextureRegion playerTextureRegion;
				
		// Set the gfx folder as our base
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		
		// set playerTexture (note: width and hight must be set to power of x^2)
		playerTexture = new BitmapTextureAtlas(engine.getTextureManager(), 64, 64);
		// grab the image and put it in a region (0,0 stands for top left corner of the atlas and gets the texture region)
		playerTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(playerTexture, activity,"face_box.png",0,0);
		
		// load all the textures (can unload: playerTexture.unload();)
		playerTexture.load();
		
		// add all the textures to the hashmap
		images.put("face_box", playerTextureRegion);

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
	public Sprite makeDatButtonSprite(String theTexture, Float w, Float h) {
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
