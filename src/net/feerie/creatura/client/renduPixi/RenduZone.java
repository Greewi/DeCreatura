package net.feerie.creatura.client.renduPixi;

import net.feerie.creatura.client.pixi.BaseTexture;
import net.feerie.creatura.client.pixi.Container;
import net.feerie.creatura.client.pixi.Graphics;
import net.feerie.creatura.client.pixi.Loader;
import net.feerie.creatura.client.pixi.Settings;
import net.feerie.creatura.client.pixi.Texture;
import net.feerie.creatura.client.pixi.mesh.Float32Array;
import net.feerie.creatura.client.pixi.mesh.Mesh;
import net.feerie.creatura.client.pixi.mesh.Uint16Array;
import net.feerie.creatura.shared.monde.Zone;

/**
 * Rendu d'une zone
 * 
 * @author greewi
 */
public class RenduZone implements RenduElement
{
	@SuppressWarnings("unused")
	private final Zone zone;
	private final Mesh mesh;
	private final Graphics eau;
	
	public RenduZone(Zone zone)
	{
		this.zone = zone;
		
		//Sol
		double[] vertices = new double[] { //
				zone.getX(), 1000, //
				zone.getX() + zone.getLongueur(), 1000, //		
				zone.getX() + zone.getLongueur(), 1000 - zone.getHauteurSolDroite(), //
				zone.getX(), 1000 - zone.getHauteurSolGauche(), //		
		};
		double[] uvs = new double[vertices.length];
		for (int i = 0; i < vertices.length; i++)
			uvs[i] = vertices[i] / 1024;
		double[] indices = new double[] { 0, 1, 3, 2 };
		Texture texture = Loader.getTexture("images/Zone.png");
		BaseTexture baseTexture = texture.baseTexture;
		baseTexture.WRAP_MODE = Settings.WrapModes.REPEAT;
		mesh = new Mesh(new Texture(baseTexture), new Float32Array(vertices), new Float32Array(uvs), new Uint16Array(indices));
		
		//Eau
		if (zone.getHauteurEau() > 0)
		{
			eau = new Graphics();
			eau.beginFill(0x00CCFF, 1.0);
			eau.drawRect(0, 0, 1, 1);
			eau.setDimensions(zone.getLongueur(), zone.getHauteurEau());
			eau.setPosition(zone.getX(), 1000 - zone.getHauteurEau());
		}
		else
			eau = null;
		
	}
	
	@Override
	public void ajoute(Scene scene)
	{
		scene.ajouteElementAvant(mesh);
		if (eau != null)
			scene.ajouteElementFond(eau);
	}
	
	@Override
	public void metAJour(long dateActuelle, double progressionTic)
	{
		//Rien à faire
	}
	
	@Override
	public void detruit()
	{
		Container parent = mesh.getParent();
		if (parent != null)
			parent.removeChild(mesh);
	}
}
