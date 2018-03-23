package net.feerie.creatura.client.pixi;

import jsinterop.annotations.JsConstructor;
import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = "PIXI")
public class Graphics extends Container
{
	@JsProperty
	public double alpha;
	@JsProperty
	public int blendMode;
	@JsProperty
	public double fillAlpha;
	@JsProperty
	public String lineColor;
	@JsProperty
	public double lineWidth;
	@JsProperty
	public boolean nativeLines;
	@JsProperty
	public int tint;
	
	@JsConstructor
	public Graphics()
	{
	}
	
	@JsMethod
	public native Graphics arc(double cx, double cy, double radius, double startAngle, double endAngle);
	
	@JsMethod
	public native Graphics arc(double cx, double cy, double radius, double startAngle, double endAngle, boolean anticlockwise);
	
	@JsMethod
	public native Graphics arcTo(double x1, double y1, double x2, double y2, double radius);
	
	@JsMethod
	public native Graphics beginFill(int color, double alpha);
	
	@JsMethod
	public native Graphics bezierCurveTo(double cpX, double cpY, double cpX2, double cpY2, double toX, double toY);
	
	@JsMethod
	public native Graphics clear();
	
	@JsMethod
	public native Graphics clone();
	
	@JsMethod
	public native Graphics closePath();
	
	@JsMethod
	public native boolean containsPoint(Point p);
	
	@JsMethod
	public native Graphics drawCircle(double cx, double cy, double radius);
	
	@JsMethod
	public native Graphics drawEllipse(double cx, double cy, double width, double height);
	
	/*
	 * TODO drawPolygon
	 */
	
	@JsMethod
	public native Graphics drawRect(double x, double y, double width, double height);
	
	@JsMethod
	public native Graphics drawRoundedRect(double x, double y, double width, double height, double radius);
	
	/*
	 * TODO drawShape
	 */
	
	@JsMethod
	public native Graphics drawStar(double x, double y, int points, double radius, double innerRadius, double rotation);
	
	@JsMethod
	public native Graphics endFill();
	
	@JsMethod
	public native Texture generateCanvasTexture(int scaleMode, int resolution);
	
	@JsMethod
	public native boolean isFastRect();
	
	@JsMethod
	public native Graphics lineStyle(double lineWidth, int color, double alpha);
	
	@JsMethod
	public native Graphics lineTo(double x, double y);
	
	@JsMethod
	public native Graphics moveTo(double x, double y);
	
	@JsMethod
	public native Graphics quadraticCurveTo(double cpX, double cpY, double toX, double toY);
	
	@JsMethod
	public native void updateLocalBounds();
}
