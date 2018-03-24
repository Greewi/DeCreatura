package net.feerie.creatura.client.pixi;

import jsinterop.annotations.JsConstructor;
import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = "PIXI")
public final class Application
{
	@JsProperty
	public Container stage;
	@JsProperty
	public WebGLRenderer renderer;
	
	@JsConstructor
	public Application(Options options)
	{
	}
	
	@JsMethod
	public native void render();
}
