package net.feerie.creatura.client.pixi;

import jsinterop.annotations.JsConstructor;
import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = "PIXI")
public class Container
{
	@JsProperty
	public double x;
	@JsProperty
	public double y;
	@JsProperty
	public double width;
	@JsProperty
	public double height;
	@JsProperty
	private Container parent;
	
	@JsConstructor
	public Container()
	{
	}
	
	@JsMethod
	public native void addChild(Container container);
	
	@JsMethod
	public native void removeChild(Container container);
	
	@JsOverlay	
	public final void setPosition(double x, double y)
	{
		this.x = x;
		this.y = y;
	}

	@JsOverlay
	public final void setDimensions(double width, double height)
	{
		this.width = width;
		this.height = height;
	}

	@JsOverlay
	public final Container getParent()
	{
		return this.parent;
	};
}
