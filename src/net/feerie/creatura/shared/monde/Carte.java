package net.feerie.creatura.shared.monde;

import java.util.Arrays;
import java.util.List;

import net.feerie.creatura.shared.commons.Position;

/**
 * Gère la géométrie du monde
 * 
 * @author greewi
 */
public class Carte
{
	private final Zone[] zones;
	private final int longueur;
	private final Zone[] zonesParPixels;
	
	/**
	 * @param zones les zones (tranches) de la carte
	 */
	public Carte(Zone[] zones)
	{
		this.zones = zones;
		int decalage = 0;
		for (int i = 0; i < zones.length; i++)
		{
			this.zones[i].setX(decalage);
			decalage += zones[i].getLongueur();
		}
		this.longueur = decalage;
		this.zonesParPixels = new Zone[this.longueur];
		for (Zone zone : zones)
		{
			for (int x = 0; x < zone.getLongueur(); x++)
				this.zonesParPixels[x + zone.getX()] = zone;
		}
	}
	
	/**
	 * Renvoie la longueur de la zone
	 * 
	 * @return la longueur de la zone
	 */
	public int getLongueurTotale()
	{
		return longueur;
	}
	
	/**
	 * Renvoie la liste des zones de la carte
	 * 
	 * @return la liste des zones de la carte
	 */
	public List<Zone> getZones()
	{
		return Arrays.asList(zones);
	}
	
	/**
	 * Récupère la zone dans laquelle la position est.
	 * 
	 * @param position la position pour laquelle on veut la zone
	 * @return la zone correspondant à la position ou <tt>null</tt> si la
	 *         position est en dehors de la carte.
	 */
	public Zone getZone(Position position)
	{
		int x = position.x;
		if (x < 0 || x >= longueur)
			throw new IllegalArgumentException("La position est hors de la carte !");
		return zonesParPixels[x];
	}
	
	/**
	 * Renvoie l'environnement affectant la position donnée
	 * 
	 * @param position la position pour laquelle on veut l'environnement
	 * @return l'environnement correspondant à la position
	 */
	public Environnement getEnvironnement(Position position)
	{
		return getZone(position).getEnvironnement();
	}
	
	/**
	 * Renvoei la hauteur du sol sous la position donnée.
	 * 
	 * @param position la position en dessous de laquelle on veut la hauteur du
	 *        sol
	 * @return la hauteur du sol sous la position ou <tt>0</tt> si la position
	 *         est en dehors de la carte.
	 */
	public int getHauteurSol(Position position)
	{
		Zone zone = getZone(position);
		return zone.getHauteurSol(position.x - zone.getX());
	}
	
	/**
	 * Renvoei la hauteur de l'eau sous la position donnée.
	 * 
	 * @param position la position en dessous de laquelle on veut la hauteur de
	 *        l'eau
	 * @return la hauteur de l'eau sous la position ou <tt>0</tt> si la position
	 *         est en dehors de la carte.
	 */
	public int getHauteurEau(Position position)
	{
		Zone zone = getZone(position);
		return zone.getHauteurEau();
	}
}
