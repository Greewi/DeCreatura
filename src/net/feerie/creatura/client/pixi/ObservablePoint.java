package net.feerie.creatura.client.pixi;

import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace="PIXI")
public class ObservablePoint
{
	@JsProperty
	public double x;
	@JsProperty
	public double y;
}
