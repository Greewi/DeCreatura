package net.feerie.creatura.client.rendu2d;

import com.google.gwt.canvas.dom.client.Context2d;

import net.feerie.creatura.shared.entites.EntiteArbre;
import net.feerie.creatura.shared.entites.EntiteArbre.Fruit;

public class RenduArbre implements RenduElement
{
	private final EntiteArbre arbre;
	private final Context2d contexte;
	private final String couleur;
	private final String couleurFruits;
	
	/**
	 * @param arbre l'arbre à dessiner
	 * @param contexte le contexte dans lequel dessiner
	 */
	public RenduArbre(EntiteArbre arbre, Context2d contexte)
	{
		this.arbre = arbre;
		this.contexte = contexte;
		this.couleur = "#275b24";
		this.couleurFruits = "#000000";
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
	 * Dessine l'arbre
	 * 
	 * @param timestamp
	 */
	@Override
	public void dessine(long dateActuelle, double progressionTic)
	{
		//Arbre
		double x = ((1 - progressionTic) * arbre.position.x + progressionTic * arbre.positionProchainTic.x);
		double z = ((1 - progressionTic) * arbre.position.z + progressionTic * arbre.positionProchainTic.z);
		int l = arbre.getTaille().l;
		int h = arbre.getTaille().h;
		contexte.setFillStyle(couleur);
		contexte.fillRect(x - l / 2, z, l, h);
		
		//Fruits
		for(Fruit fruit : arbre.getFruits())
		{
			x = fruit.getPosition().x;
			z = fruit.getPosition().z;
			if(fruit.estMur())
				l = 20;
			else
				l = 10;
			contexte.setFillStyle(couleurFruits);
			contexte.beginPath();
			contexte.arc(x, z, l / 2, 0, 20);
			contexte.closePath();
			contexte.fill();
		}
		
		
		
	}
}
