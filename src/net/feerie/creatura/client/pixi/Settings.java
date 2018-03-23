package net.feerie.creatura.client.pixi;

import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = "PIXI", name = "settings")
public class Settings
{
	@JsProperty
	public static String PRECISION_FRAGMENT;
	@JsProperty
	public static String PRECISION_VERTEX;
	@JsProperty
	public static int WRAP_MODE;
	
	@JsType(isNative = true, namespace = "PIXI", name = "WRAP_MODES")
	public static class WrapModes
	{
		@JsProperty
		public static int CLAMP;
		@JsProperty
		public static int REPEAT;
		@JsProperty
		public static int MIRRORED_REPEAT;
	}
}
