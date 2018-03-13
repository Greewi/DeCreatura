package net.feerie.creatura.client.rendu2d;

import com.google.gwt.canvas.dom.client.Context2d;

import net.feerie.creatura.shared.entites.Entite;
import net.feerie.creatura.shared.entites.EntiteLitiere;
import net.feerie.creatura.shared.entites.TypeEntite;

/**
 * Représente un rendu d'entité. Cette classe s'occupe de dessiner les entités
 * 
 * @author greewi
 */
public class RenduEntite implements RenduElement
{
	private final Entite entite;
	private final Context2d contexte;
	private final String couleur;
	
	/**
	 * @param entite l'entite à dessiner
	 * @param contexte le contexte dans lequel dessiner
	 */
	public RenduEntite(Entite entite, Context2d contexte)
	{
		this.entite = entite;
		this.contexte = contexte;
		
		if (entite.getType() == TypeEntite.POPO)
			this.couleur = "#303010";
		else
			this.couleur = "#737373";
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
		
		if (entite.getType() == TypeEntite.LITIERE)
		{
			contexte.setFillStyle(couleur);
			contexte.fillRect(x - l / 2, z, l, h);
			double taux = ((EntiteLitiere) entite).getContenu() / ((EntiteLitiere) entite).getCapacite();
			l *= 0.9;
			h *= taux;
			contexte.setFillStyle("#505013");
			contexte.fillRect(x - l / 2, z, l, h);
		}
		else if (entite.getType() == TypeEntite.DISTRIBUTEUR_GRANULE)
		{
			contexte.setFillStyle(couleur);
			contexte.fillRect(x - l / 2, z, l, h);
		}
		else if (entite.getType() == TypeEntite.EAU)
		{
			//L'eau n'est pas rendu ici (voir RenduZone)
		}
		else
		{
			contexte.setFillStyle(couleur);
			contexte.beginPath();
			contexte.arc(x, z + h / 2, l / 2, 0, 20);
			contexte.closePath();
			contexte.fill();
		}
	}
}
