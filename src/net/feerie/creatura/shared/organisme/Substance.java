package net.feerie.creatura.shared.organisme;

/**
 * Cette �num�ration d�fini les differentes substances impliqu�e dans le
 * fonctionnement (ou le disfonctionnement) des organismes.
 * 
 * @author greewi
 */
public enum Substance
{
	/**
	 * Substance vitale de base
	 */
	EAU,
	/**
	 * Produit l'�nergie dans les organes
	 */
	GLUCIDES,
	/**
	 * Peut �tre converti en glucides pour produire de l'�nergie ou �tre stock�
	 * dans les tissus adipeux.
	 */
	LIPIDES,
	/**
	 * Utilis�s par les organes pour produire les autres substances utiles.
	 */
	PROTEINES,
	/**
	 * D�chets produits par les organes
	 */
	TOXINES,
	/**
	 * Produit par l'estomac pour signaler la sati�t�
	 */
	CHOLECYSTOKININE,
	/**
	 * Produit par le cerveau pour rester actif
	 */
	NORALDRENALINE,
	/**
	 * Produit par le cerveau, repr�sente le moral
	 */
	SEROTONINE,
	/**
	 * Produit par le cerveau quand de nouvelles connexions sont cr�es
	 */
	DOPAMINE
}
