package net.feerie.creatura.client.rendu2d;

import java.util.HashMap;

import net.feerie.creatura.shared.Monde;
import net.feerie.creatura.shared.entites.Entite;
import net.feerie.creatura.shared.events.ObservateurEntiteAjoutee;
import net.feerie.creatura.shared.events.ObservateurEntiteSupprimee;

import com.google.gwt.canvas.dom.client.Context2d;

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
	private final HashMap<Integer, RenduEntite> rendusEntites;
	
	/**
	 * @param monde le monde à dessiner
	 * @param contexte le contexte dns lequel dessiner
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
				rendusEntites.put(entite.getID(), new RenduEntite(entite, contexte));
			}
		});
		monde.onEntiteSupprimee(new ObservateurEntiteSupprimee()
		{
			@Override
			public void onEntiteSuprimee(Entite entite)
			{
				RenduEntite renduADetruire = rendusEntites.remove(entite.getID());
				if (renduADetruire != null)
					renduADetruire.detruit();
			}
		});
	}
	
	/**
	 * Détruit ce moteur de rendu
	 */
	public void detruit()
	{
		for (RenduEntite rendu : rendusEntites.values())
			rendu.detruit();
		rendusEntites.clear();
	}
	
	/**
	 * Dessine le monde.
	 * 
	 * @param timestamp la date du rendu
	 */
	public void dessine(double timestamp)
	{
		for (RenduEntite rendu : rendusEntites.values())
			rendu.dessine(timestamp);
	}
}
