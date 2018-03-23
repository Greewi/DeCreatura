package net.feerie.creatura.client.pixi;

import jsinterop.annotations.JsConstructor;
import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = "PIXI")
public class Point
{
	@JsProperty
	public double x;
	@JsProperty
	public double y;
	
	@JsConstructor
	public Point(double x, double y)
	{
	}
	
	@JsMethod
	public native Point clone();
	
	@JsMethod
	public native void copy(Point p);
	
	@JsMethod
	public native boolean equals(Point p);
	
	@JsMethod
	public native void set(double x, double y);
}
