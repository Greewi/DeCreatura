package net.feerie.creatura.shared.actions;

import net.feerie.creatura.shared.commons.Position;
import net.feerie.creatura.shared.commons.Vecteur;
import net.feerie.creatura.shared.entites.Creature;
import net.feerie.creatura.shared.entites.Entite;

/**
 * Repr�sente une action de d�placement
 * 
 * @author greewi
 */
public class ActionSeDeplacer extends Action
{
	private final Action actionAEnchainer;
	private final double vitesse = 0.5;
	
	/**
	 * @param creature la cr�ature se d�pl�ant
	 * @param cible l'entit� vers laquelle se d�place la cr�ature
	 * @param actionAEnchainer l'action � effetuer une fois arriv�e sur place.
	 *        <tt>null</tt> si aucune action n'est � faire.
	 */
	public ActionSeDeplacer(Creature creature, Entite cible, Action actionAEnchainer)
	{
		super(creature, cible);
		this.actionAEnchainer = actionAEnchainer;
	}
	
	@Override
	public TypeAction getType()
	{
		return TypeAction.SE_DEPLACER;
	}
	
	@Override
	public boolean metAJour(int frame)
	{
		//On avance d'une frame
		boolean arrive = avance();
		
		//Si on est arriv�, on se place bien sur le point d'arriv�e et on effectuer l'action en attente
		if (arrive)
		{
			creature.setPosition(new Position(cible.getPosition()));
			if (actionAEnchainer != null)
			{
				creature.setActionActuelle(actionAEnchainer);
				actionAEnchainer.debute(frame);
				return true;
			}
			else
				return false;
		}
		//Si on n'est pas encore arriv� il faudra continuer � la prochaine frame
		else
			return true;
	}
	
	/**
	 * Avance d'une frame
	 * 
	 * @param frame le num�ro de la frame actuelle
	 * @return <tt>true</tt> si et seulement si le d�plaement est termin�.
	 */
	private boolean avance()
	{
		//Si la cible n'existe plus on arr�te
		if (!cible.existe())
			return true;
		
		Position position = creature.getPosition();
		Vecteur direction = new Vecteur(position, cible.getPosition());
		double distance = direction.getNorme();
		
		//Si on est arriv�, on s'arr�te
		if (distance < 0.0001)
			return true;
		
		double vitesse = (this.vitesse < distance) ? this.vitesse : distance;
		position = position.translate(direction.multiplie(vitesse / distance));
		creature.setPosition(position);
		return false;
	}
	
}
