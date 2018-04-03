package net.feerie.creatura.client.renduPixi;

import net.feerie.creatura.client.pixi.Container;
import net.feerie.creatura.client.pixi.Graphics;
import net.feerie.creatura.client.pixi.Loader;
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
	private final Mesh solArriere;
	private final Mesh solAvant;
	private final Graphics eau;
	
	public RenduZone(Zone zone)
	{
		this.zone = zone;
		
		//Sol
		double[] vertices = new double[] { //
				zone.getX(), 1000, //
				zone.getX() + zone.getLongueur(), 1000, //		
				zone.getX() + zone.getLongueur(), 1000 - zone.getHauteurSolDroite() + 2, //
				zone.getX(), 1000 - zone.getHauteurSolGauche() + 2, //		
		};
		double[] uvs = new double[vertices.length];
		for (int i = 0; i < vertices.length; i++)
			uvs[i] = vertices[i] / 1024;
		double[] indices = new double[] { 0, 1, 3, 2 };
		Texture texture = Loader.getTexture("images/zones/SableFond.png");
		solArriere = new Mesh(texture, new Float32Array(vertices), new Float32Array(uvs), new Uint16Array(indices));
		
		//Zone avant plan
		vertices = new double[] { //
				zone.getX(), 1000 - zone.getHauteurSolGauche() - 32, //
				zone.getX() + zone.getLongueur(), 1000 - zone.getHauteurSolDroite() - 32, //		
				zone.getX() + zone.getLongueur(), 1000 - zone.getHauteurSolDroite() + 96, //
				zone.getX(), 1000 - zone.getHauteurSolGauche() + 96, //		
		};
		uvs = new double[] { 0, 0, 1, 0, 1, 1, 0, 1 };
		for (int i = 0; i < vertices.length; i++)
		{
			if(i%2==0)
				uvs[i] = vertices[i] / 512;
		}
		indices = new double[] { 0, 1, 3, 2 };
		texture = Loader.getTexture("images/zones/SableSol.png");
		solAvant = new Mesh(texture, new Float32Array(vertices), new Float32Array(uvs), new Uint16Array(indices));
		
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
		if (eau != null)
			scene.ajouteElementFond(eau);
		scene.ajouteElementFond(solArriere);
		scene.ajouteAvantPlan(solAvant);
	}
	
	@Override
	public void metAJour(long dateActuelle, double progressionTic)
	{
		//Rien à faire
	}
	
	@Override
	public void detruit()
	{
		Container parent = solArriere.getParent();
		if (parent != null)
			parent.removeChild(solArriere);
		parent = eau.getParent();
		if (parent != null)
			parent.removeChild(eau);
		parent = solAvant.getParent();
		if (parent != null)
			parent.removeChild(solAvant);
	}
}
