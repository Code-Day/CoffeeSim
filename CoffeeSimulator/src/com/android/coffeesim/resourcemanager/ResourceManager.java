//package org.andengine.examples;
package com.android.coffeesim.resourcemanager;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.examples.IOException;
import org.andengine.examples.InputStream;
import org.andengine.examples.Override;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.adt.io.in.IInputStreamOpener;
import org.andengine.util.debug.Debug;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.region.ITextureRegion;

/**
 * David Parsons
 */
public class ResourceManager {

	// ===========================================================
	// Fields
	// ===========================================================

	// for single splash
	private ITexture mTexture;
	private ITextureRegion mFaceTextureRegion;	
	
	// for actual sprites (later)
//	private BuildableBitmapTextureAtlas mBitmapTextureAtlas;
//	
//	private TiledTextureRegion mSnapdragonTextureRegion;
//	private TiledTextureRegion mHelicopterTextureRegion;
//	private TiledTextureRegion mBananaTextureRegion;
//	private TiledTextureRegion mFaceTextureRegionT;

	// ===========================================================
	// This function grabs a single image to load before game starts
	// ===========================================================
	public void onCreateSplashResources() {
		try {
			this.mTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
				@Override
				public InputStream open() throws IOException {
					return getAssets().open("splash/splash.png");
				}
			});
			this.mTexture.load();
			this.mFaceTextureRegion = TextureRegionFactory.extractFromTexture(this.mTexture);
		} catch (IOException e) {
			Debug.e(e);
		}
	}
	
	// ===========================================================
	// This function grabs a single image to load before game starts
	// ===========================================================
	@Override
	public void onCreateResources() {
		// hashmap for texture regions
		HashMap<String, TiledTextureRegion> images = new HashMap<String, TiledTextureRegion>();
		
		// texture
		BitmapTextureAtlas playerTexture;
		// texture region
		TiledTextureRegion playerTextureRegion;
				
		// Set the gfx folder as our base
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		
		// set playerTexture (note: width and hight must be set to power of x^2)
		playerTexture = BitmapTextureAtlas(getTextureManager(), 64, 64);
		// grab the image and put it in a region (0,0 stands for top left corner of the atlas and gets the texture region)
		playerTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(playerTexture, this,"face_box.png",0,0);
		
		// load all the textures (can unload: playerTexture.unload();)
		playerTexture.load();
		
		// add all the textures to the hashmap
		images.put("face_box", playerTextureRegion);
		
		// create all the sprites
		// TODO: understand pVertexBufferObject (our engine should be passed in)
		Sprite sPlayer = new Sprite(CAMERA_WIDTH/2, CAMERA_HEIGHT/2, playerTextureRegion, this.mEngine.getVertexBufferObjectManager());
		// TEST
		sPlayer.setRotation(45.0f);
		// ADD TO SCENE???
		this.scene.attachChild(sPlayer);

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

}
