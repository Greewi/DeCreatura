package net.feerie.creatura.client.pixi;

import com.google.gwt.core.client.JavaScriptObject;

public final class Container extends JavaScriptObject
{
	protected Container()
	{
	}

	public native void addChild(Sprite sprite)/*-{
		this.addChild(sprite);
	}-*/;
}
