package net.feerie.creatura.client.renduPixi;

import net.feerie.creatura.client.pixi.Container;
import net.feerie.creatura.client.pixi.Sprite;
import net.feerie.creatura.shared.entites.Creature;

public class RenduCreature implements RenduElement
{
	private final Creature creature;
	private final Sprite sprite;
	
	public RenduCreature(Creature creature)
	{
		this.creature = creature;
		this.sprite = Sprite.newSprite("images/Creature.png");
		this.sprite.setDimensions(64, 64);
		this.sprite.anchor.x = 0.5;
		this.sprite.anchor.y = 1;
	}
	
	@Override
	public void ajoute(Scene scene)
	{
		scene.ajouteElementMilieu(sprite);
	}
	
	@Override
	public void metAJour(long dateActuelle, double progressionTic)
	{
		double x = ((1 - progressionTic) * creature.position.x + progressionTic * creature.positionProchainTic.x);
		double y = 1000 - ((1 - progressionTic) * creature.position.z + progressionTic * creature.positionProchainTic.z);
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
