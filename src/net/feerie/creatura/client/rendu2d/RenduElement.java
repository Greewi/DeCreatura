package net.feerie.creatura.client.rendu2d;

public interface RenduElement
{
	/**
	 * détruit ce rendu
	 */
	public void detruit();
	
	/**
	 * Dessine l'entite
	 * 
	 * @param dateActuelle la date actuelle en millisecondes
	 */
	public void dessine(long dateActuelle);
}
