package net.feerie.creatura.client.renduPixi;

import net.feerie.creatura.client.pixi.Container;
import net.feerie.creatura.client.pixi.Sprite;
import net.feerie.creatura.shared.entites.Entite;
import net.feerie.creatura.shared.entites.TypeEntite;

public class RenduEntiteSimple implements RenduElement
{
	private final Entite entite;
	private final Sprite sprite;
	
	public RenduEntiteSimple(Entite entite)
	{
		this.entite = entite;
		if (entite.getType() == TypeEntite.NOURRITURE_GRANULE)
			this.sprite = Sprite.newSprite("images/Granule.png");
		else if (entite.getType() == TypeEntite.NOURRITURE_FRUIT)
			this.sprite = Sprite.newSprite("images/Fruit.png");
		else if (entite.getType() == TypeEntite.POPO)
			this.sprite = Sprite.newSprite("images/Popo.png");
		else
			this.sprite = Sprite.newSprite("images/Popo.png");
		this.sprite.setDimensions(16, 16);
		this.sprite.anchor.x = 0.5;
		this.sprite.anchor.y = 1;
	}
	
	@Override
	public void ajoute(Scene scene)
	{
		scene.ajouteElementAvant(sprite);
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
