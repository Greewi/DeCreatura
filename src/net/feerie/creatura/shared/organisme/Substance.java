package net.feerie.creatura.shared.organisme;

/**
 * Cette énumération défini les differentes substances impliquée dans le
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
	 * Produit l'énergie dans les organes
	 */
	GLUCIDES,
	/**
	 * Peut être converti en glucides pour produire de l'énergie ou être stocké
	 * dans les tissus adipeux.
	 */
	LIPIDES,
	/**
	 * Utilisés par les organes pour produire les autres substances utiles.
	 */
	PROTEINES,
	/**
	 * Déchets produits par les organes
	 */
	TOXINES,
	/**
	 * Produit par l'estomac pour signaler la satiété
	 */
	CHOLECYSTOKININE,
	/**
	 * Produit par le cerveau pour rester actif
	 */
	NORALDRENALINE,
	/**
	 * Produit par le cerveau, représente le moral
	 */
	SEROTONINE,
	/**
	 * Produit par le cerveau quand de nouvelles connexions sont crées
	 */
	DOPAMINE
}
