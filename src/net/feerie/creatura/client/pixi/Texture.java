package net.feerie.creatura.client.pixi;

import jsinterop.annotations.JsConstructor;
import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = "PIXI")
public class Texture
{
	@JsConstructor
	public Texture(BaseTexture baseTexture){
	}
	
	@JsProperty
	public BaseTexture baseTexture;
	@JsProperty
	public Rectangle frame;
	
	@JsMethod
	public native void _updateUvs();
}
