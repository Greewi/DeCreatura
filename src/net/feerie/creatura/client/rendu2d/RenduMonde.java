package net.feerie.creatura.client.rendu2d;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gwt.canvas.dom.client.Context2d;

import net.feerie.creatura.shared.entites.Creature;
import net.feerie.creatura.shared.entites.CreatureNuisible;
import net.feerie.creatura.shared.entites.Entite;
import net.feerie.creatura.shared.entites.EntiteArbre;
import net.feerie.creatura.shared.entites.TypeEntite;
import net.feerie.creatura.shared.events.ObservateurEntiteAjoutee;
import net.feerie.creatura.shared.events.ObservateurEntiteSupprimee;
import net.feerie.creatura.shared.monde.Monde;
import net.feerie.creatura.shared.monde.Zone;

/**
 * Représente le rendu d'un monde. Se charger de dessiner le monde.
 * 
 * @author greewi
 */
public class RenduMonde
{
	@SuppressWarnings("unused")
	private final Monde monde;
	@SuppressWarnings("unused")
	private final Context2d contexte;
	private final List<RenduElement> rendusZones;
	private final HashMap<Integer, RenduElement> rendusEntites;
	
	/**
	 * @param monde le monde à dessiner
	 * @param contexte le contexte dans lequel dessiner
	 */
	public RenduMonde(Monde monde, final Context2d contexte)
	{
		this.monde = monde;
		this.contexte = contexte;
		rendusEntites = new HashMap<>();
		//On attache les observateurs sur les événements du monde pour mettre à jour la vue 
		monde.onEntiteAjoutee(new ObservateurEntiteAjoutee()
		{
			@Override
			public void onEntiteAjoutee(Entite entite)
			{
				if (entite.getType() == TypeEntite.CREATURE)
					rendusEntites.put(entite.getID(), new RenduCreature((Creature) entite, contexte));
				else if (entite.getType() == TypeEntite.CREATURE_NUISIBLE)
					rendusEntites.put(entite.getID(), new RenduNuisible((CreatureNuisible) entite, contexte));
				else if (entite.getType() == TypeEntite.NOURRITURE_GRANULE)
					rendusEntites.put(entite.getID(), new RenduNourriture(entite, contexte));
				else if (entite.getType() == TypeEntite.NOURRITURE_FRUIT)
					rendusEntites.put(entite.getID(), new RenduNourriture(entite, contexte));
				else if (entite.getType() == TypeEntite.NOURRITURE_FIBRE)
					rendusEntites.put(entite.getID(), new RenduNourriture(entite, contexte));
				else if (entite.getType() == TypeEntite.NOURRITURE_GRAINE)
					rendusEntites.put(entite.getID(), new RenduNourriture(entite, contexte));
				else if (entite.getType() == TypeEntite.NOURRITURE_INSECTE)
					rendusEntites.put(entite.getID(), new RenduNourriture(entite, contexte));
				else if (entite.getType() == TypeEntite.NOURRITURE_POISSON)
					rendusEntites.put(entite.getID(), new RenduNourriture(entite, contexte));
				else if (entite.getType() == TypeEntite.ARBRE_FRUITIER || entite.getType() == TypeEntite.ARBRE_COQUE)
					rendusEntites.put(entite.getID(), new RenduArbre((EntiteArbre) entite, contexte));
				else
					rendusEntites.put(entite.getID(), new RenduEntite(entite, contexte));
			}
		});
		monde.onEntiteSupprimee(new ObservateurEntiteSupprimee()
		{
			@Override
			public void onEntiteSuprimee(Entite entite)
			{
				RenduElement renduADetruire = rendusEntites.remove(entite.getID());
				if (renduADetruire != null)
					renduADetruire.detruit();
			}
		});
		rendusZones = new ArrayList<RenduElement>();
		for (Zone zone : monde.getCarte().getZones())
			rendusZones.add(new RenduZone(zone, contexte));
	}
	
	/**
	 * Détruit ce moteur de rendu
	 */
	public void detruit()
	{
		for (RenduElement rendu : rendusEntites.values())
			rendu.detruit();
		rendusEntites.clear();
		rendusZones.clear();
	}
	
	/**
	 * Dessine le monde.
	 * 
	 * @param dateActuelle la date actuelle en millisecondes
	 * @param progressionTic la progression du tic actuel (de 0 à 1);
	 */
	public void dessine(long dateActuelle, double progressionTic)
	{
		for (RenduElement rendu : rendusZones)
			rendu.dessine(dateActuelle, progressionTic);
		
		for (RenduElement rendu : rendusEntites.values())
			rendu.dessine(dateActuelle, progressionTic);
	}
}
