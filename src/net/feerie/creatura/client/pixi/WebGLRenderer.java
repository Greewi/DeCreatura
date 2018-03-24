package net.feerie.creatura.client.pixi;

import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = "PIXI")
public class WebGLRenderer
{
	@JsMethod
	public native void resize(double screenWidth, double screenHeight);
	
}
