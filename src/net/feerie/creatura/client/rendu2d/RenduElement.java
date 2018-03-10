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
	 * @param progressionTic la progression du tic actuel (de 0 à 1);
	 */
	public void dessine(long dateActuelle, double progressionTic);
}
