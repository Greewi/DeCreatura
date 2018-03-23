package net.feerie.creatura.client.pixi;

public class Utils
{
	
	public static native void sayHello()/*-{
		var PIXI = $wnd.PIXI;
		var type = "WebGL"
		if (!PIXI.utils.isWebGLSupported()) {
			type = "canvas"
		}
		PIXI.utils.sayHello(type);
	}-*/;
}
