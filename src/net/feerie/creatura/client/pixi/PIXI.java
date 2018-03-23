package net.feerie.creatura.client.pixi;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class PIXI
{
	@JsType(isNative = true, namespace = "PIXI", name = "settings")
	public static class Settings
	{
		@JsProperty
		public static boolean CAN_UPLOAD_SAME_BUFFER;
		@JsProperty
		public static int FILTER_RESOLUTION;
		@JsProperty
		public static int GC_MAX_CHECK_COUNT;
		@JsProperty
		public static int GC_MAX_IDLE;
		/*
		 * TODO GC_MODE
		 */
		@JsProperty
		public static int MESH_CANVAS_PADDING;
		@JsProperty
		public static boolean MIPMAP_TEXTURES;
		@JsProperty
		public static String PRECISION_FRAGMENT;
		@JsProperty
		public static String PRECISION_VERTEX;
		@JsProperty
		public static int RESOLUTION;
		@JsProperty
		public static String RETINA_PREFIX;
		/*
		 * TODO SCALE_MODE
		 */
		@JsProperty
		public static int SPRITE_MAX_TEXTURES;
		@JsProperty
		public static int TARGET_FPMS;
		/*
		 * TODO TRANSFORM_MODE
		 */
		@JsProperty
		public static int UPLOADS_PER_FRAME;
		@JsProperty
		public static int WRAP_MODE;
	}
	
	@JsType(isNative = true, namespace = "PIXI")
	public static class WRAP_MODES
	{
		@JsProperty
		public static int CLAMP;
		@JsProperty
		public static int REPEAT;
		@JsProperty
		public static int MIRRORED_REPEAT;
	}
	
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
