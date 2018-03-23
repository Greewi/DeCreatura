package net.feerie.creatura.client.pixi;

import com.google.gwt.dom.client.CanvasElement;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "Object")
public class Options
{
	@JsProperty
	public CanvasElement view;
	@JsProperty
	public double width;
	@JsProperty
	public double height;
	@JsProperty
	public boolean transparent;
	@JsProperty
	public boolean antialias;
	@JsProperty
	public int backgroundColor;
	@JsProperty
	public boolean clearBeforeRender;
}
