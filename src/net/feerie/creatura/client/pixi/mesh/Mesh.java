package net.feerie.creatura.client.pixi.mesh;

import jsinterop.annotations.JsConstructor;
import jsinterop.annotations.JsType;
import net.feerie.creatura.client.pixi.Container;
import net.feerie.creatura.client.pixi.Texture;

@JsType(isNative = true, namespace = "PIXI.mesh")
public class Mesh extends Container
{
	@JsConstructor
	public Mesh(Texture texture, Float32Array vertices, Float32Array uvs, Uint16Array indices)
	{
	}
}
