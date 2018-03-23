package net.feerie.creatura.client.renduPixi;

import net.feerie.creatura.client.pixi.Container;
import net.feerie.creatura.client.pixi.Sprite;
import net.feerie.creatura.shared.entites.EntiteArbre;

public class RenduArbre implements RenduElement
{
	private final EntiteArbre arbre;
	private final Sprite spriteArriere;
	private final Sprite spriteAvant;
	
	public RenduArbre(EntiteArbre arbre)
	{
		this.arbre = arbre;
		this.spriteArriere = Sprite.newSprite("images/ArbreFruitier.png");
		this.spriteArriere.setDimensions(arbre.getTaille().l, arbre.getTaille().h);
		this.spriteArriere.anchor.x = 0.5;
		this.spriteArriere.anchor.y = 1;
		
		this.spriteAvant = Sprite.newSprite("images/ArbreFruitier-avant.png");
		this.spriteAvant.setDimensions(arbre.getTaille().l, arbre.getTaille().h);
		this.spriteAvant.anchor.x = 0.5;
		this.spriteAvant.anchor.y = 1;
	}
	
	@Override
	public void ajoute(Scene scene)
	{
		scene.ajouteElementFond(spriteArriere);
		scene.ajouteAvantPlan(spriteAvant);
	}
	
	@Override
	public void metAJour(long dateActuelle, double progressionTic)
	{
		double x = ((1 - progressionTic) * arbre.position.x + progressionTic * arbre.positionProchainTic.x);
		double y = 1000 - ((1 - progressionTic) * arbre.position.z + progressionTic * arbre.positionProchainTic.z);
		this.spriteArriere.setPosition(x, y);
		this.spriteAvant.setPosition(x, y);
	}
	
	@Override
	public void detruit()
	{
		Container parent = spriteArriere.getParent();
		if (parent != null)
			parent.removeChild(spriteArriere);
		parent = spriteAvant.getParent();
		if (parent != null)
			parent.removeChild(spriteAvant);
	}
}
