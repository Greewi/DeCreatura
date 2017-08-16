package net.feerie.creatura.shared;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.feerie.creatura.shared.commons.Position;
import net.feerie.creatura.shared.entites.Entite;
import net.feerie.creatura.shared.entites.TypeEntite;
import net.feerie.creatura.shared.entites.Zone;
import net.feerie.creatura.shared.events.ObservateurEntiteAjoutee;
import net.feerie.creatura.shared.events.ObservateurEntiteSupprimee;

/**
 * Repr�sente le monde. Il contiendra les entit�s.
 */
public class Monde
{
	private HashMap<Integer, Entite> entites = new HashMap<>();
	private List<Entite> nouvellesEntites = new ArrayList<>();
	private int IDEntiteSuivante = 1;
	private ArrayList<Zone> zones = new ArrayList<>();
	private Environnement environnementParDefaut;
	//Observateurs d'�v�nements
	private final ArrayList<ObservateurEntiteAjoutee> observateursEntiteAjoutee = new ArrayList<>();
	private final ArrayList<ObservateurEntiteSupprimee> observateursEntiteSuprimee = new ArrayList<>();
	
	/**
	 * Contructeur
	 */
	public Monde(Environnement environnementParDefaut)
	{
		this.environnementParDefaut = environnementParDefaut;
	}
	
	/**
	 * G�n�re et renvoie un nouvel ID d'entit�
	 * 
	 * @return l'ID g�n�r�
	 */
	public int genereIDEntite()
	{
		return IDEntiteSuivante++;
	}
	
	/**
	 * Renvoie une entit�
	 * 
	 * @param ID l'ID de l'entit� qu'on veut avoir
	 * @return l'entit� correspondant � l'IA ou <tt>null</tt> sinon.
	 */
	public Entite getEntite(int ID)
	{
		return entites.get(ID);
	}
	
	/**
	 * Ajoute une entit�
	 * 
	 * @param entite l'entit� � ajouter
	 */
	void ajouteEntite(Entite entite)
	{
		entites.put(entite.getID(), entite);
		for (ObservateurEntiteAjoutee observateur : observateursEntiteAjoutee)
			observateur.onEntiteAjoutee(entite);
	}
	
	public void nouvelleEntite(Entite entite)
	{
		nouvellesEntites.add(entite);
	}
	
	/**
	 * Supprime une entit� (ne supprimera pas une Zone !)
	 * 
	 * @param ID l'ID de l'entit� � supprimer
	 */
	public void supprimeEntite(int ID)
	{
		if (entites.containsKey(ID))
		{
			Entite entite = entites.get(ID);
			if (entite.getType() == TypeEntite.ZONE) // On ne supprime pas les zones
				return;
			entites.remove(ID);
			for (ObservateurEntiteSupprimee observateur : observateursEntiteSuprimee)
				observateur.onEntiteSuprimee(entite);
		}
	}
	
	/**
	 * Renvoie la liste des entit�s contenues dans le monde
	 * 
	 * @return la liste des entit�s contenues dans le monde
	 */
	public ArrayList<Entite> getListeEntites()
	{
		return new ArrayList<>(entites.values());
	}
	
	/**
	 * Renvoie la zone contenant les coordonn�es pass�es en param�tre
	 * 
	 * @param position la position �valu�e
	 * @return la zone contenant les coordonn�es ou <tt>null</tt>
	 */
	public Zone getZone(Position position)
	{
		for (Zone zone : zones)
			if (zone.contient(position))
				return zone;
		return null;
	}
	
	/**
	 * Ajoute une zone dans le monde
	 * 
	 * @param zone la zone � ajouter
	 */
	public void ajouteZone(Zone zone)
	{
		ajouteEntite(zone);
		zones.add(zone);
	}
	
	/**
	 * D�termine l'environnement � la position donn�e
	 * 
	 * @param position la position o� on souhaite connaitre l'environnement
	 * @return l'environnement de la position
	 */
	public Environnement getEnvironnement(Position position)
	{
		Zone zone = getZone(position);
		if (zone == null)
			return environnementParDefaut;
		else
			return zone.getEnvironnement();
	}
	
	/**
	 * Met le monde � jour .
	 * 
	 * @param frame le num�ro de la nouvelle frame
	 */
	public void metAJour(int frame)
	{
		for (Entite entite : entites.values())
			entite.metAJour(frame);
		//Ajout des nouvelles entit�s
		for(Entite entite : nouvellesEntites)
			ajouteEntite(entite);
		//Nettoyage des entit�s disparues
		for (Entite entite : new ArrayList<>(entites.values()))
			if (!entite.existe())
				supprimeEntite(entite.getID());
	}
	
	/**
	 * Ajoute un observateur sur les �v�nements d'ajout d'entite
	 * 
	 * @param observateur un observateur qui �coutera les ajouts d'entites
	 */
	public void onEntiteAjoutee(ObservateurEntiteAjoutee observateur)
	{
		observateursEntiteAjoutee.add(observateur);
	}
	
	/**
	 * Ajoute un observateur sur les �v�nements de suppressions d'entite
	 * 
	 * @param observateur un observateur qui �coutera les supressions d'entites
	 */
	public void onEntiteSupprimee(ObservateurEntiteSupprimee observateur)
	{
		observateursEntiteSuprimee.add(observateur);
	}
}
