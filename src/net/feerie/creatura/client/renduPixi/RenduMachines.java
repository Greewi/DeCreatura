package net.feerie.creatura.client.renduPixi;

import net.feerie.creatura.client.pixi.Container;
import net.feerie.creatura.client.pixi.Graphics;
import net.feerie.creatura.shared.entites.Entite;

public class RenduMachines implements RenduElement
{
	private final Entite entite;
	private final Graphics graphics;
	
	public RenduMachines(Entite entite)
	{
		this.entite = entite;
		this.graphics = new Graphics();
		this.graphics.beginFill(0xCCCCCC, 1.0);
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
