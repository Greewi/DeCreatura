package net.feerie.creatura.client.pixi;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class PIXI
{
	
	@JsType(isNative = true, namespace = "PIXI")
	public static class BLEND_MODES
	{
		@JsProperty
		public static int NORMAL;
		@JsProperty
		public static int ADD;
		@JsProperty
		public static int MULTIPLY;
		@JsProperty
		public static int SCREEN;
		@JsProperty
		public static int OVERLAY;
		@JsProperty
		public static int DARKEN;
		@JsProperty
		public static int LIGHTEN;
		@JsProperty
		public static int COLOR_DODGE;
		@JsProperty
		public static int COLOR_BURN;
		@JsProperty
		public static int HARD_LIGHT;
		@JsProperty
		public static int SOFT_LIGHT;
		@JsProperty
		public static int DIFFERENCE;
		@JsProperty
		public static int EXCLUSION;
		@JsProperty
		public static int HUE;
		@JsProperty
		public static int SATURATION;
		@JsProperty
		public static int COLOR;
		@JsProperty
		public static int LUMINOSITY;
	}
}
