package net.feerie.creatura.client.pixi;

import jsinterop.annotations.JsConstructor;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType(isNative=true, namespace="PIXI")
public class Sprite extends Container
{
	@JsProperty
	public ObservablePoint anchor;
	
	@JsConstructor
	public Sprite(Texture texture)
	{
	}
	
	@JsOverlay
	public final static Sprite newSprite(String textureName){
		return new Sprite(Loader.getTexture(textureName));
	};
}
