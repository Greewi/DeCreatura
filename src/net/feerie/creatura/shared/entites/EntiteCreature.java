package net.feerie.creatura.shared.entites;

import net.feerie.creatura.shared.actions.Action;
import net.feerie.creatura.shared.commons.Dimension;
import net.feerie.creatura.shared.commons.Position;
import net.feerie.creatura.shared.creature.ia.IA;
import net.feerie.creatura.shared.creature.ia.filtresEntites.FiltreEntite;
import net.feerie.creatura.shared.monde.Environnement;
import net.feerie.creatura.shared.monde.Monde;

public abstract class EntiteCreature extends Entite
{
	private Action action;
	private IA ia;
	
	protected EntiteCreature(Monde monde, Position position, Dimension taille)
	{
		super(monde, position, taille);
		this.action = null;
		this.ia = null;
	}
	
	/**
	 * Défini l'IA de la créature
	 * 
	 * @param ia
	 */
	protected void setIA(IA ia)
	{
		this.ia = ia;
	}
	
	/**
	 * Défini l'action actuelle que doit entreprendre cette créature
	 * 
	 * @param action l'action que doit entreprendre cette créature
	 */
	public void setActionActuelle(Action action)
	{
		this.action = action;
	}
	
	/**
	 * @return l'action actuellement entreprise par la créature
	 */
	public Action getActionActuelle()
	{
		return action;
	}
	
	/**
	 * Détermine si la créature est vivante.
	 * 
	 * @return <tt>true</tt> si la créature est vivante
	 */
	public abstract boolean estVivante();
	
	@Override
	public void effectueTic()
	{
		super.effectueTic();
		
		//Si la créature est vivante, elle fait des trucs
		if (estVivante())
		{
			if (action != null)
				if (!action.effectueTic())
					this.action = null;
		}
		//Sinon rien...
		else
			action = null;
		
		//On applique la gravité
		appliqueGravite();
	}
	
	@Override
	public void effectueCycleIA()
	{
		if (estVivante())
			action = ia.decideProchaineAction();
	}
	
	/**
	 * Renvoie le monde dans lequel �volue la cr�ature
	 * 
	 * @return le monde dans lequel �volue la cr�ature
	 */
	public Monde getMonde()
	{
		return monde;
	}
	
	/**
	 * R�cup�re l'environnement actuel
	 * 
	 * @return l'environnement actuel
	 */
	public Environnement getEnvironnement()
	{
		return monde.getCarte().getEnvironnement(position);
	}
	
	/**
	 * Recherche l'entite d'un type donné le plus proche
	 * 
	 * @param typeEntite le type de l'entité
	 * @return l'entité trouvée ou <tt>null</tt>
	 */
	public Entite cherche(FiltreEntite filtre)
	{
		Entite entite = null;
		double distance = 100000000000.0;
		for (Entite e : monde.getListeEntites())
			if (filtre.teste(e) && getDistanceCarre(e) < distance)
			{
				entite = e;
				distance = getDistanceCarre(e);
			}
		return entite;
	}
}
