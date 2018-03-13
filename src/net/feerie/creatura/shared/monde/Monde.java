package net.feerie.creatura.shared.monde;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gwt.user.client.Random;

import net.feerie.creatura.shared.Constantes;
import net.feerie.creatura.shared.commons.Position;
import net.feerie.creatura.shared.entites.CreatureNuisible;
import net.feerie.creatura.shared.entites.Entite;
import net.feerie.creatura.shared.entites.TypeEntite;
import net.feerie.creatura.shared.events.ObservateurEntiteAjoutee;
import net.feerie.creatura.shared.events.ObservateurEntiteSupprimee;

/**
 * Représente le monde. Il contiendra les entités.
 */
public class Monde
{
	//Carte
	private final Carte carte;
	//Entités
	private final HashMap<Integer, Entite> entites = new HashMap<>();
	private int IDEntiteSuivante = 1;
	
	//Observateurs d'événements
	private final ArrayList<ObservateurEntiteAjoutee> observateursEntiteAjoutee = new ArrayList<>();
	private final ArrayList<ObservateurEntiteSupprimee> observateursEntiteSuprimee = new ArrayList<>();
	
	/**
	 * Contructeur
	 */
	public Monde(Carte carte)
	{
		this.carte = carte;
	}
	
	/**
	 * Génére et renvoie un nouvel ID d'entité
	 * 
	 * @return l'ID généré
	 */
	public int genereIDEntite()
	{
		return IDEntiteSuivante++;
	}
	
	/**
	 * Renvoie une entité
	 * 
	 * @param ID l'ID de l'entité qu'on veut avoir
	 * @return l'entité correspondant à l'ID ou <tt>null</tt> sinon.
	 */
	public Entite getEntite(int ID)
	{
		return entites.get(ID);
	}
	
	/**
	 * Récupère la liste des entités à une position
	 * 
	 * @param position
	 * @return
	 */
	public List<Entite> getEntiteAPosition(Position position)
	{
		double xc = position.x;
		double yc = position.z;
		
		List<Entite> entites = new ArrayList<>();
		for (Entite e : getListeEntites())
		{
			double xe = e.position.x;
			double ye = e.position.z;
			double le = e.getTaille().l;
			double he = e.getTaille().h;
			
			if (xc >= xe - le / 2 && xc <= xe + le / 2 && yc >= ye && yc < ye + he)
				entites.add(e);
		}
		
		return entites;
	}
	
	/**
	 * Ajoute une entité
	 * 
	 * @param entite l'entité à ajouter
	 */
	void ajouteEntite(Entite entite)
	{
		entites.put(entite.getID(), entite);
		for (ObservateurEntiteAjoutee observateur : observateursEntiteAjoutee)
			observateur.onEntiteAjoutee(entite);
	}
	
	public void nouvelleEntite(Entite entite)
	{
		ajouteEntite(entite);
	}
	
	/**
	 * Supprime une entité (ne supprimera pas une Zone !)
	 * 
	 * @param ID l'ID de l'entité à supprimer
	 */
	public void supprimeEntite(int ID)
	{
		if (entites.containsKey(ID))
		{
			Entite entite = entites.get(ID);
			entites.remove(ID);
			for (ObservateurEntiteSupprimee observateur : observateursEntiteSuprimee)
				observateur.onEntiteSuprimee(entite);
		}
	}
	
	/**
	 * Renvoie la liste des entités contenues dans le monde
	 * 
	 * @return la liste des entités contenues dans le monde
	 */
	public List<Entite> getListeEntites()
	{
		return new ArrayList<>(entites.values());
	}
	
	/**
	 * Renvoie la carte du monde
	 * 
	 * @return la carte du monde
	 */
	public Carte getCarte()
	{
		return carte;
	}
	
	/**
	 * Renvoie l'accélération de la gravité en pixels par tics carrés
	 * 
	 * @return
	 */
	public int getAccelerationGravite()
	{
		return Constantes.ACCELERATION_GRAVITE;
	}
	
	private final List<Entite> entitesTic = new ArrayList<>();
	
	/**
	 * Exécute le nouveau tic
	 * 
	 * @param cycleIA si <tt>true</tt> exécute le cycle des IA
	 * @param cycleMetabolique si <tt>true</tt> exécute le cycle métabolique
	 */
	public void effectueTic(boolean cycleIA, boolean cycleMetabolique)
	{
		entitesTic.clear();
		entitesTic.addAll(this.entites.values());
		for (Entite entite : entitesTic)
			entite.effectueTic();
		if (cycleIA)
			for (Entite entite : entitesTic)
				entite.effectueCycleIA();
		if (cycleMetabolique)
		{
			boolean nuisibleExiste = false;
			int quantiteNourriture = 0;
			for (Entite entite : entitesTic)
			{
				if (entite.getType() == TypeEntite.CREATURE_NUISIBLE)
					nuisibleExiste = true;
				if (entite.getType().peutEtreMange())
					quantiteNourriture++;
				entite.effectueCycleMetabolique();
			}
			
			//Génération des nuisibles
			if (quantiteNourriture > Constantes.QUANTITE_ELEMENT_POUR_NUISIBLE && !nuisibleExiste)
			{
				int x = Random.nextInt(carte.getLongueurTotale());
				Position position = new Position(x, 0);
				position.z = carte.getHauteurSol(position);
				nouvelleEntite(new CreatureNuisible(this, position));
			}
		}
	}
	
	/**
	 * Ajoute un observateur sur les événements d'ajout d'entite
	 * 
	 * @param observateur un observateur qui écoutera les ajouts d'entites
	 */
	public void onEntiteAjoutee(ObservateurEntiteAjoutee observateur)
	{
		observateursEntiteAjoutee.add(observateur);
	}
	
	/**
	 * Ajoute un observateur sur les événements de suppressions d'entite
	 * 
	 * @param observateur un observateur qui écoutera les supressions d'entites
	 */
	public void onEntiteSupprimee(ObservateurEntiteSupprimee observateur)
	{
		observateursEntiteSuprimee.add(observateur);
	}
}
