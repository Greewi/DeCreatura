package net.feerie.creatura.shared.entites;

public enum TypeEntite
{
	// Bestioles
	CREATURE(20, false),
	CREATURE_POISSON(20, false),
	CREATURE_NUISIBLE(20, false),
	
	// Nourritures
	NOURRITURE_GRANULE(30, true),
	NOURRITURE_FRUIT(30, true),
	NOURRITURE_GRAINE(30, true),
	NOURRITURE_FIBRE(30, true),
	NOURRITURE_INSECTE(30, true),
	NOURRITURE_POISSON(30, true),
	EAU(0, false),
	POPO(30, true),
	
	// Appareils
	LITIERE(10, false),
	DISTRIBUTEUR_GRANULE(10, false),
	
	// FLORE
	ARBRE_FRUITIER(10, false),
	ARBRE_COQUE(10, false);
	
	private final int prioriteSelection;
	private final boolean comestible;
	
	private TypeEntite(int prioriteSelection, boolean comestible)
	{
		this.prioriteSelection = prioriteSelection;
		this.comestible = comestible;
	}
	
	/**
	 * Renvoie le niveau de priorité de sélection. Plus le niveau de sélection
	 * est élevé, plus les entités de ce type sont prioritaires lors d'une
	 * sélection.
	 * 
	 * @return le niveau de priorité de sélection.
	 */
	public int getPrioriteSelection()
	{
		return prioriteSelection;
	};
	
	/**
	 * Détermine si les entités de ce type peuvent être mangée
	 * 
	 * @return <tt>true<tt> si l'entité de ce tuype peut être mangée.
	 */
	public boolean peutEtreMange()
	{
		return comestible;
	};
	
}
