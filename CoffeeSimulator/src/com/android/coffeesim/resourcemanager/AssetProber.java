package com.android.coffeesim.resourcemanager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import android.content.Context;
import android.graphics.BitmapFactory;


public class AssetProber
{
	private static final String DOT = ".";
	private Context mContext;
	public AssetProber (Context c) 
	{
		mContext = c;
	}
	
	/**
	 * 
	 * @param pExt - File extension to search for: ".*"
	 * @return Collection of strings representing filenames
	 */
	public Collection < String > getAssetNamesOfType ( String pExt )
	{
		ArrayList < String > fileNames = new ArrayList < String > ( 8 ); 
		if ( pExt.startsWith ( DOT ) == false )
		{
			pExt = DOT + pExt;
		}
		try
		{
			String[] assetFolders = mContext.getAssets ().list ( "" );
			for ( int i = 0; i < assetFolders.length; i ++ )
			{
				if ( assetFolders [i].contains ( DOT ) )
				{
					continue;
				}
				
				String[] assetFiles = mContext.getAssets ().list ( assetFolders [i] + File.separator );
				for ( int j = 0; j < assetFiles.length; j ++ )
				{
					if ( assetFiles [j].contains ( pExt ) )
					{
						fileNames.add ( assetFiles[j] );
					}
				}
			}
		}
		catch ( IOException e )
		{
			e.printStackTrace();
		}
		return fileNames;
	}
	
	class ImageInfo 
	{
		String mFilename;
		int mWidth, mHeight;
		ImageInfo (String name, int w, int h)
		{
			mFilename = name;
			mWidth = w;
			mHeight = h;
		}
	}
	
	/**
	 * @return Collection of AssetProber.ImageInfo objects representing filenames
	 */
	public Collection < ImageInfo > getPNGAssetInfo ()
	{
		ArrayList < ImageInfo > fileInfos = new ArrayList < ImageInfo > ( 8 ); 
		
		try
		{
			String[] assetFolders = mContext.getAssets ().list ( "" );
			for ( int i = 0; i < assetFolders.length; i ++ )
			{
				if ( assetFolders [i].contains ( DOT ) )
				{
					continue;
				}
				
				String[] assetFiles = mContext.getAssets ().list ( assetFolders [i] + File.separator );
				for ( int j = 0; j < assetFiles.length; j ++ )
				{
					if ( assetFiles [j].contains ( ".png" ) )
					{
						BitmapFactory.Options options = new BitmapFactory.Options();
						options.inJustDecodeBounds = true;
						BitmapFactory.decodeFile( assetFiles [j], options );
						fileInfos.add ( new ImageInfo (assetFiles [j], options.outWidth, options.outHeight) );
					}
				}
			}
		}
		catch ( IOException e )
		{
			e.printStackTrace();
		}
		return fileInfos;
	}
}
