package net.feerie.creatura.client.rendu2d;

import net.feerie.creatura.shared.entites.Entite;
import net.feerie.creatura.shared.entites.Litiere;
import net.feerie.creatura.shared.entites.Nourriture;
import net.feerie.creatura.shared.entites.TypeEntite;
import net.feerie.creatura.shared.entites.Zone;

import com.google.gwt.canvas.dom.client.Context2d;

/**
 * Représente un rendu d'entité. Cette classe s'occupe de dessiner les entités
 * 
 * @author greewi
 */
public class RenduEntite
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
		
		if (entite.getType() == TypeEntite.CREATURE)
			this.couleur = "#FF7300";
		else if (entite.getType() == TypeEntite.NOURRITURE)
			this.couleur = ((Nourriture) entite).getCouleur();
		else if (entite.getType() == TypeEntite.EAU)
			this.couleur = "#11AAFF";
		else if (entite.getType() == TypeEntite.ZONE)
			this.couleur = ((Zone) entite).getCouleur();
		else if (entite.getType() == TypeEntite.POPO)
			this.couleur = "#303010";
		else
			this.couleur = "#737373";
	}
	
	/**
	 * détruit ce rendu
	 */
	public void detruit()
	{
		//rien à faire pour le moment
	}
	
	/**
	 * Dessine l'entite
	 * 
	 * @param timestamp
	 */
	public void dessine(double timestamp)
	{
		contexte.setFillStyle(couleur);
		if (entite.getType() == TypeEntite.LITIERE)
		{
			contexte.fillRect(entite.getPosition().x - entite.getTaille().l / 2, entite.getPosition().y - entite.getTaille().h / 2, entite.getTaille().l, entite.getTaille().h);
			double taux = ((Litiere) entite).getContenu() / ((Litiere) entite).getCapacite();
			double l = entite.getTaille().l * taux;
			double h = entite.getTaille().h * taux;
			contexte.setFillStyle("#505013");
			contexte.fillRect(entite.getPosition().x - l / 2, entite.getPosition().y - h / 2, l, h);
		}
		else if (entite.getType() == TypeEntite.ZONE)
			contexte.fillRect(entite.getPosition().x - entite.getTaille().l / 2, entite.getPosition().y - entite.getTaille().h / 2, entite.getTaille().l, entite.getTaille().h);
		else
		{
			contexte.beginPath();
			contexte.arc(entite.getPosition().x, entite.getPosition().y, entite.getTaille().l / 2, 0, 20);
			contexte.closePath();
			contexte.fill();
		}
	}
}
