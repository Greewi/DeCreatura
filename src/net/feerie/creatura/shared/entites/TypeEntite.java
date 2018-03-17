package net.feerie.creatura.shared.entites;

import java.util.EnumSet;

import net.feerie.creatura.shared.actions.TypeAction;
import static net.feerie.creatura.shared.actions.TypeAction.*;

public enum TypeEntite
{
	// Bestioles
	CREATURE("Creature", 20),
	CREATURE_POISSON("Poisson", 20, ACTIVER),
	CREATURE_NUISIBLE("Nuisible", 20, ACTIVER),
	
	// Nourritures
	NOURRITURE_GRANULE("Granulé", 30, MANGER),
	NOURRITURE_FRUIT("Fruit", 30, MANGER),
	NOURRITURE_GRAINE("Graine", 30, MANGER),
	NOURRITURE_FIBRE("Fibre", 30, MANGER),
	NOURRITURE_INSECTE("Insecte", 30, MANGER),
	NOURRITURE_POISSON("Poisson", 30, MANGER),
	EAU("Eau", 0, BOIRE, FAIRE_POPO, DORMIR),
	POPO("Popo", 30, MANGER, FAIRE_POPO),
	
	// Appareils
	LITIERE("Litiere", 10, ACTIVER, FAIRE_POPO, DORMIR),
	DISTRIBUTEUR_GRANULE("Distributeur", 10, ACTIVER, FAIRE_POPO, DORMIR),
	
	// FLORE
	ARBRE_FRUITIER("Arbre fruitier", 10, ACTIVER, FAIRE_POPO, DORMIR),
	ARBRE_COQUE("Arbre coque", 10, ACTIVER, FAIRE_POPO, DORMIR);
	
	private final String nom;
	private final int prioriteSelection;
	private final EnumSet<TypeAction> actionsPossibles;
	
	private TypeEntite(String nom, int prioriteSelection, TypeAction... actionsPossibles)
	{
		this.nom = nom;
		this.prioriteSelection = prioriteSelection;
		this.actionsPossibles = EnumSet.noneOf(TypeAction.class);
		for (TypeAction action : actionsPossibles)
			this.actionsPossibles.add(action);
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
	 * Détermine si les entités de ce type peuvent être la cible de l'action
	 * passée en paramètre
	 * 
	 * @param action l'action dont on veu savoir si elle est possible sur ce
	 *        type d'entité
	 * @return <tt>true</tt> si l'action est possible sur ce type d'entité
	 */
	public boolean estActionPossible(TypeAction action)
	{
		return actionsPossibles.contains(action);
	}
	
	@Override
	public String toString()
	{
		return nom;
	}
}
