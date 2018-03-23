package net.feerie.creatura.client.pixi;

import jsinterop.annotations.JsConstructor;
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
}
