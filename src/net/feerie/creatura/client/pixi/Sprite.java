package net.feerie.creatura.client.pixi;

import com.google.gwt.core.client.JavaScriptObject;

public final class Sprite extends JavaScriptObject
{
	protected Sprite()
	{
	}
	
	public static native Sprite newSprite(String textureName)/*-{
		var PIXI = $wnd.PIXI;
		return new PIXI.Sprite(PIXI.loader.resources[textureName].texture);
	}-*/;
}
