//package org.andengine.examples;
package com.android.coffeesim.resourcemanager;

import java.io.IOException;
import java.io.InputStream;

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
	// Constants
	// ===========================================================

//	private static final int CAMERA_WIDTH = 480;
//	private static final int CAMERA_HEIGHT = 320;

	// ===========================================================
	// Fields
	// ===========================================================

	// for single splash
	private ITexture mTexture;
	private ITextureRegion mFaceTextureRegion;	
	
	// for actual sprites
	private BuildableBitmapTextureAtlas mBitmapTextureAtlas;
	
	private TiledTextureRegion mSnapdragonTextureRegion;
	private TiledTextureRegion mHelicopterTextureRegion;
	private TiledTextureRegion mBananaTextureRegion;
	private TiledTextureRegion mFaceTextureRegionT;

	// ===========================================================
	// Constructors
	// ===========================================================

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

//	@Override
//	public EngineOptions onCreateEngineOptions() {
//		final Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
//
//		return new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);
//	}

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
	
	@Override
	public void onCreateResources() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

		this.mBitmapTextureAtlas = new BuildableBitmapTextureAtlas(this.getTextureManager(), 512, 256, TextureOptions.NEAREST);
//		this.mBitmapTextureAtlas = new BuildableBitmapTextureAtlas(this.getTextureManager(), 512, 256, TextureOptions.BILINEAR);
		
		this.mSnapdragonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mBitmapTextureAtlas, this, "snapdragon_tiled.png", 4, 3);
		this.mHelicopterTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mBitmapTextureAtlas, this, "helicopter_tiled.png", 2, 2);
		this.mBananaTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mBitmapTextureAtlas, this, "banana_tiled.png", 4, 2);
		this.mFaceTextureRegionT = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mBitmapTextureAtlas, this, "face_box_tiled.png", 2, 1);

		try {
			this.mBitmapTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 1));
			this.mBitmapTextureAtlas.load();
		} catch (TextureAtlasBuilderException e) {
			Debug.e(e);
		}
	}

	@Override
	public Scene onCreateScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());

		final Scene scene = new Scene();
		scene.setBackground(new Background(0.09804f, 0.6274f, 0.8784f));

		/* Quickly twinkling face. */
		final AnimatedSprite face = new AnimatedSprite(100, 50, this.mFaceTextureRegionT, this.getVertexBufferObjectManager());
		face.animate(100);
		scene.attachChild(face);

		/* Continuously flying helicopter. */
		final AnimatedSprite helicopter = new AnimatedSprite(320, 50, this.mHelicopterTextureRegion, this.getVertexBufferObjectManager());
		helicopter.animate(new long[] { 100, 100 }, 1, 2, true);
		scene.attachChild(helicopter);

		/* Snapdragon. */
		final AnimatedSprite snapdragon = new AnimatedSprite(300, 200, this.mSnapdragonTextureRegion, this.getVertexBufferObjectManager());
		snapdragon.animate(100);
		scene.attachChild(snapdragon);

		/* Funny banana. */
		final AnimatedSprite banana = new AnimatedSprite(100, 220, this.mBananaTextureRegion, this.getVertexBufferObjectManager());
		banana.animate(100);
		scene.attachChild(banana);

		return scene;
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
