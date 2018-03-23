package net.feerie.creatura.client.renduPixi;

public interface RenduElement
{
	/**
	 * Ajoute l'élément dans le conteneur PIXI donné en paramètre
	 */
	public void ajoute(Scene scene);
	
	/**
	 * Met à jour l'élément (appelé à chaque frame)
	 * 
	 * @param dateActuelle la date actuelle en millisecondes
	 * @param progressionTic la progression du tic actuel (de 0 à 1);
	 */
	public void metAJour(long dateActuelle, double progressionTic);
	
	/**
	 * Détruit l'élément et libère les ressources associées
	 */
	public void detruit();
}
