package net.feerie.creatura.client.rendu2d;

import com.google.gwt.canvas.dom.client.Context2d;

import net.feerie.creatura.shared.actions.Action;
import net.feerie.creatura.shared.actions.ActionSeDeplacer;
import net.feerie.creatura.shared.actions.TypeAction;
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
	public void dessine(long dateActuelle)
	{
		Action actionActuelle = creature.getActionActuelle();
		
		double x = creature.getPosition().x;
		double y = creature.getPosition().y;
		
		if (actionActuelle != null && actionActuelle.getType() == TypeAction.SE_DEPLACER)
		{
			ActionSeDeplacer deplacement = (ActionSeDeplacer) actionActuelle;
			double t = deplacement.getProgressionAffichage(dateActuelle);
			double x0 = deplacement.getDepart().x;
			double y0 = deplacement.getDepart().y;
			double x1 = deplacement.getDestination().x;
			double y1 = deplacement.getDestination().y;
			x = (1 - t) * x0 + t * x1;
			y = (1 - t) * y0 + t * y1;
		}
		
		contexte.setFillStyle(couleur);
		contexte.beginPath();
		contexte.arc(x, y, creature.getTaille().l / 2, 0, 20);
		contexte.closePath();
		contexte.fill();
	}
}
