package net.feerie.creatura.client.pixi;

public class Loader
{
	protected Loader()
	{
	}
	
	public static native void add(String ressourceName)/*-{
		$wnd.PIXI.loader.add(ressourceName);
	}-*/;
	
	public static native void load(OnLoadCallback callback)/*-{
		var callbackJS = function() {
			callback.@net.feerie.creatura.client.pixi.Loader.OnLoadCallback::onLoad()();
		};
		$wnd.PIXI.loader.load(callbackJS);
	}-*/;
	
	public static native Texture getTexture(String textureName)/*-{
		return $wnd.PIXI.loader.resources[textureName].texture
	}-*/;
	
	public static native BaseTexture getBaseTexture(String textureName)/*-{
		return $wnd.PIXI.loader.resources[textureName].texture
	}-*/;
	
	public static interface OnLoadCallback
	{
		public void onLoad();
	}
}
