package net.feerie.creatura.client.rendu2d;

import com.google.gwt.canvas.dom.client.Context2d;

import net.feerie.creatura.shared.entites.Creature;

public class RenduCreature implements RenduElement
{
	private final Context2d contexte;
	private final String couleur;
	private final Creature creature;
	
	public RenduCreature(Creature creature, Context2d contexte)
	{
		this.creature = creature;
		this.contexte = contexte;
		this.couleur = "#FF7300";
	}
	
	/**
	 * détruit ce rendu
	 */
	@Override
	public void detruit()
	{
		//rien à faire pour le moment
	}
	
	/**
	 * Dessine l'creature
	 * 
	 * @param timestamp
	 */
	@Override
	public void dessine(long dateActuelle, double progressionTic)
	{
		double x = ((1 - progressionTic) * creature.position.x + progressionTic * creature.positionProchainTic.x);
		double z = ((1 - progressionTic) * creature.position.z + progressionTic * creature.positionProchainTic.z);
		int l = creature.getTaille().l;
		int h = creature.getTaille().h;
		contexte.setFillStyle(couleur);
		contexte.beginPath();
		contexte.arc(x, z + h / 2, l / 2, 0, 20);
		contexte.closePath();
		contexte.fill();
	}
}
