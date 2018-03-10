package net.feerie.creatura.shared.actions;

import net.feerie.creatura.shared.Constantes;
import net.feerie.creatura.shared.commons.Position;
import net.feerie.creatura.shared.entites.Creature;

/**
 * Représente une action de déplacement
 * 
 * @author greewi
 */
public class ActionMarcher extends AbstractAction
{
	private IAction actionAEnchainer;
	private Position depart;
	private Position destination;
	private int vitesseHorizontale;
	
	/**
	 * @param creature la créature se déplaçant
	 */
	public ActionMarcher(Creature creature)
	{
		super(creature);
		this.depart = new Position(creature.position);
		this.destination = null;
		this.actionAEnchainer = null;
	}
	
	/**
	 * @param creature la créature se déplaçant
	 * @param destination la position vers laquelle se déplace la créature
	 */
	public ActionMarcher(Creature creature, Position destination)
	{
		super(creature);
		this.depart = new Position(creature.position);
		this.destination = destination;
		this.actionAEnchainer = null;
		calculeTrajet();
	}
	
	/**
	 * @param creature la créature se déplaçant
	 * @param destination la position vers laquelle se déplace la créature
	 * @param actionAEnchainer l'action à effetuer une fois arrivée sur place.
	 *        <tt>null</tt> si aucune action n'est à faire.
	 */
	public ActionMarcher(Creature creature, Position destination, IAction actionAEnchainer)
	{
		super(creature);
		this.depart = new Position(creature.position);
		this.destination = destination;
		this.actionAEnchainer = actionAEnchainer;
		calculeTrajet();
	}
	
	/**
	 * Définis la destination
	 * 
	 * @param destination
	 */
	public void setDestination(Position destination)
	{
		this.destination = destination;
		calculeTrajet();
	}
	
	/**
	 * Renvoie la position de départ de la créature
	 * 
	 * @return la position de départ de la créature
	 */
	public Position getDepart()
	{
		return depart;
	}
	
	/**
	 * Renvoie la position d'arrivée de la créature
	 * 
	 * @return la position d'arrivée de la créature
	 */
	public Position getDestination()
	{
		return destination;
	}
	
	/**
	 * Défini l'action à enchainer une fois le déplacement terminé
	 * 
	 * @param actionAEnchainer
	 */
	public void setActionAEnchainer(IAction actionAEnchainer)
	{
		this.actionAEnchainer = actionAEnchainer;
	}
	
	private void calculeTrajet()
	{
		vitesseHorizontale = Constantes.VITESSE_MARCHE_CREATURE;
		if (destination.x < depart.x)
			vitesseHorizontale = -vitesseHorizontale;
	}
	
	@Override
	public TypeAction getType()
	{
		return TypeAction.SE_DEPLACER;
	}
	
	@Override
	public boolean effectueTic()
	{
		Position positionActuelle = getCreature().position;
		Position positionProchainTic = getCreature().positionProchainTic;
		
		positionProchainTic.x = positionActuelle.x + vitesseHorizontale;
		if ((vitesseHorizontale > 0 && positionProchainTic.x >= destination.x) || (vitesseHorizontale < 0 && positionProchainTic.x <= destination.x))
		{
			positionProchainTic.x = destination.x;
			if (actionAEnchainer != null)
			{
				getCreature().setActionActuelle(actionAEnchainer);
				return true;
			}
			else
				return false;
		}
		return true;
	}
}
