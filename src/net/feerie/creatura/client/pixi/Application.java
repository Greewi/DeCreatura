package net.feerie.creatura.client.pixi;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.CanvasElement;

public final class Application extends JavaScriptObject
{
	protected Application()
	{
	}
	
	public static native Application newApplication(Config config)/*-{
		var PIXI = $wnd.PIXI;
		var app = new PIXI.Application(config);
		return app;
	}-*/;
	
	public native Container getStage()/*-{
		return this.stage;
	}-*/;
	
	public native void render()/*-{
		this.render();
	}-*/;
	
	public static final class Config extends JavaScriptObject
	{
		protected Config()
		{
		}
		
		public static native Config newConfig()/*-{
			return {};
		}-*/;
		
		public native void setWidth(int width)/*-{
			this.width = width;
		}-*/;
		
		public native void setHeight(int height)/*-{
			this.height = height;
		}-*/;
		
		public native void setAntialias(boolean antialias)/*-{
			this.antialias = antialias;
		}-*/;
		
		public native void setTransparent(boolean transparent)/*-{
			this.transparent = transparent;
		}-*/;
		
		public native void setBackgroundColor(int backgroundColor)/*-{
			this.backgroundColor = backgroundColor;
		}-*/;

		public native void setClearBeforeRender(boolean clearBeforeRender)/*-{
			this.clearBeforeRender = clearBeforeRender;
		}-*/;
		
		public native void setView(CanvasElement canvas)/*-{
			this.view = canvas;
		}-*/;
	}
}
