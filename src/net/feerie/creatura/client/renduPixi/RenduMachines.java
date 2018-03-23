package net.feerie.creatura.client.renduPixi;

import net.feerie.creatura.client.pixi.Container;
import net.feerie.creatura.client.pixi.Sprite;
import net.feerie.creatura.shared.entites.Entite;
import net.feerie.creatura.shared.entites.TypeEntite;

public class RenduMachines implements RenduElement
{
	private final Entite entite;
	private final Sprite sprite;
	
	public RenduMachines(Entite entite)
	{
		this.entite = entite;
		if (entite.getType() == TypeEntite.DISTRIBUTEUR_GRANULE)
			this.sprite = Sprite.newSprite("images/Distributeur.png");
		else
			this.sprite = Sprite.newSprite("images/Popo.png");
		this.sprite.setDimensions(entite.getTaille().l, entite.getTaille().h);
		this.sprite.anchor.x = 0.5;
		this.sprite.anchor.y = 1;
	}
	
	@Override
	public void ajoute(Scene scene)
	{
		scene.ajouteElementFond(sprite);
	}
	
	@Override
	public void metAJour(long dateActuelle, double progressionTic)
	{
		double x = ((1 - progressionTic) * entite.position.x + progressionTic * entite.positionProchainTic.x);
		double y = 1000 - ((1 - progressionTic) * entite.position.z + progressionTic * entite.positionProchainTic.z);
		this.sprite.setPosition(x, y);
	}
	
	@Override
	public void detruit()
	{
		Container parent = sprite.getParent();
		if (parent != null)
			parent.removeChild(sprite);
	}
	
}
