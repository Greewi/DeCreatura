package net.feerie.creatura.client.renduPixi;

import net.feerie.creatura.client.pixi.Container;
import net.feerie.creatura.client.pixi.Graphics;
import net.feerie.creatura.shared.entites.Entite;

public class RenduArbre implements RenduElement
{
	private final Entite entite;
	private final Graphics graphics;
	
	public RenduArbre(Entite entite)
	{
		this.entite = entite;
		this.graphics = new Graphics();
		this.graphics.beginFill(0x55CC11, 1.0);
		this.graphics.drawRect(0, 0, 1, 1);
		this.graphics.setDimensions(entite.getTaille().l, entite.getTaille().h);
	}
	
	@Override
	public void ajoute(Scene scene)
	{
		scene.ajouteElementFond(graphics);
	}
	
	@Override
	public void metAJour(long dateActuelle, double progressionTic)
	{
		double x = ((1 - progressionTic) * entite.position.x + progressionTic * entite.positionProchainTic.x);
		double y = 1000 - ((1 - progressionTic) * entite.position.z + progressionTic * entite.positionProchainTic.z);
		this.graphics.setPosition(x - entite.getTaille().l / 2, y - entite.getTaille().h);
	}
	
	@Override
	public void detruit()
	{
		Container parent = graphics.getParent();
		if (parent != null)
			parent.removeChild(graphics);
	}
}
