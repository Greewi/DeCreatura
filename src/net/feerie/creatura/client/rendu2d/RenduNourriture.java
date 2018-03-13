package net.feerie.creatura.client.rendu2d;

import com.google.gwt.canvas.dom.client.Context2d;

import net.feerie.creatura.shared.entites.Entite;
import net.feerie.creatura.shared.entites.TypeEntite;

/**
 * Effectue le rendu d'une entité nourriture
 * 
 * @author greewi
 */
public class RenduNourriture implements RenduElement
{
	private final Entite entite;
	private final Context2d contexte;
	private final String couleur;
	
	public RenduNourriture(Entite entite, Context2d contexte)
	{
		this.entite = entite;
		this.contexte = contexte;
		
		if (entite.getType() == TypeEntite.NOURRITURE_GRANULE)
			this.couleur = "#CCCCCC";
		else if (entite.getType() == TypeEntite.NOURRITURE_FRUIT)
			this.couleur = "#CC0000";
		else if (entite.getType() == TypeEntite.NOURRITURE_FIBRE)
			this.couleur = "#00CC00";
		else if (entite.getType() == TypeEntite.NOURRITURE_GRAINE)
			this.couleur = "#CCCC00";
		else if (entite.getType() == TypeEntite.NOURRITURE_INSECTE)
			this.couleur = "#CC7700";
		else if (entite.getType() == TypeEntite.NOURRITURE_POISSON)
			this.couleur = "#0077CC";
		else
			this.couleur = "#CC00CC";
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
	 * Dessine l'entite
	 * 
	 * @param timestamp
	 */
	@Override
	public void dessine(long dateActuelle, double progressionTic)
	{
		double x = ((1 - progressionTic) * entite.position.x + progressionTic * entite.positionProchainTic.x);
		double z = ((1 - progressionTic) * entite.position.z + progressionTic * entite.positionProchainTic.z);
		int l = entite.getTaille().l;
		int h = entite.getTaille().h;
		
		contexte.setFillStyle(couleur);
		contexte.beginPath();
		contexte.arc(x, z + h / 2, l / 2, 0, 20);
		contexte.closePath();
		contexte.fill();
	}
}
