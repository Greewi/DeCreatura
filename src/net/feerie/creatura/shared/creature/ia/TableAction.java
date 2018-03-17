package net.feerie.creatura.shared.creature.ia;

import java.util.Set;

import net.feerie.creatura.shared.actions.TypeAction;
import net.feerie.creatura.shared.entites.TypeEntite;

/**
 * Cette classe représente un tableau associant toutes les actions et leurs
 * cibles et leur associe un score.
 * 
 * @author greewi
 */
public class TableAction
{
	private final int[] poids;
	private final int nombreActions;
	private final int nombreEntites;
	private Set<TypeAction> actions;
	private Set<TypeEntite> entites;
	
	public TableAction()
	{
		nombreActions = TypeAction.values().length;
		nombreEntites = TypeEntite.values().length;
		poids = new int[nombreActions * nombreEntites];
	}
	
	/**
	 * Réinitialise tous les scores à zéro et réinitialise les ensembles
	 * d'actions et de cible disponibles
	 * 
	 * @param actions les actions disponibles
	 * @param entites les types d'entités disponibles
	 */
	public void initialiseZero(Set<TypeAction> actions, Set<TypeEntite> entites)
	{
		this.actions = actions;
		this.entites = entites;
		for (TypeAction action : actions)
			for (TypeEntite entite : entites)
				setPoids(action, entite, 0);
	}
	
	/**
	 * Réinitialise tous les scores avec une valeur aléatoire et réinitialise
	 * les ensembles d'actions et de cible disponibles
	 * 
	 * @param actions les actions disponibles
	 * @param entites les types d'entités disponibles
	 */
	public void initialiseAleatoire(Set<TypeAction> actions, Set<TypeEntite> entites, int scoreMin, int scoreMax)
	{
		this.actions = actions;
		this.entites = entites;
		for (TypeAction action : actions)
			for (TypeEntite entite : entites)
				setPoids(action, entite, scoreMin + (int) (Math.random() * (scoreMax - scoreMin)));
	}
	
	/**
	 * Calcule la position du score d'une action dans le tableau de poids
	 * 
	 * @param action l'action recherchée
	 * @param entite la cible de l'action recherchée
	 * @return la position du poids de l'action
	 */
	private int getIndex(TypeAction action, TypeEntite entite)
	{
		return action.ordinal() + entite.ordinal() * nombreActions;
	}
	
	/**
	 * Renvoie le poids d'une action
	 * 
	 * @param action l'action recherchée
	 * @param entite la cible de l'action recherchée
	 * @return le poids de l'action
	 */
	public int getPoids(TypeAction action, TypeEntite entite)
	{
		return poids[getIndex(action, entite)];
	}
	
	/**
	 * Définis le poids d'une action
	 * 
	 * @param action l'action recherchée
	 * @param entite la cible de l'action recherchée
	 * @param poids le nouveau poids
	 */
	public void setPoids(TypeAction action, TypeEntite entite, int poids)
	{
		this.poids[getIndex(action, entite)] = poids;
	}
	
	/**
	 * Recherche l'action avec le plus grand poids
	 * 
	 * @return l'action avec le plus grand poids
	 */
	public ActionPonderee getMeilleureAction()
	{
		TypeAction meilleureAction = null;
		TypeEntite meilleureentite = null;
		int meilleurScore = 0;
		
		for (TypeAction action : actions)
			for (TypeEntite entite : entites)
			{
				if (entite.estActionPossible(action))
				{
					int score = getPoids(action, entite);
					if (meilleureAction == null || score > meilleurScore)
					{
						meilleureAction = action;
						meilleureentite = entite;
						meilleurScore = score;
					}
				}
			}
		if (meilleureAction != null)
			return new ActionPonderee(meilleureAction, meilleureentite, meilleurScore);
		return null;
	}
}
