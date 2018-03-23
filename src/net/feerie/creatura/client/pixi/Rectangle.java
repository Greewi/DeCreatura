package net.feerie.creatura.client.pixi;

import jsinterop.annotations.JsConstructor;
import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = "PIXI")
public class Rectangle
{
	@JsConstructor
	public Rectangle(double x, double y, double width, double height)
	{
	}
	
	@JsProperty
	public double bottom;
	@JsProperty
	public double height;
	@JsProperty
	public double left;
	@JsProperty
	public double right;
	@JsProperty
	public double top;
	@JsProperty
	public int type;
	@JsProperty
	public double width;
	@JsProperty
	public double x;
	@JsProperty
	public double y;
	
	@JsMethod
	public native Rectangle clone();
	
	@JsMethod
	public native boolean contains(double x, double y);
	
	@JsMethod
	public native Rectangle copy(Rectangle rectangle);
	
	@JsMethod
	public native void enlarge(Rectangle rectangle);
	
	@JsMethod
	public native void fit(Rectangle rectangle);
	
	@JsMethod
	public native void pad(double paddingX, double paddingY);
}
