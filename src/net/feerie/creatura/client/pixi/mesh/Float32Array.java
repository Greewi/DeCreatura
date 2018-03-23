package net.feerie.creatura.client.pixi.mesh;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;
import net.sourceforge.htmlunit.corejs.javascript.annotations.JSConstructor;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class Float32Array
{
	@JSConstructor
	public Float32Array(double[] array)
	{
	}
}
