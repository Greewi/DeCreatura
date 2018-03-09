package net.feerie.creatura.shared.actions;

import net.feerie.creatura.shared.commons.Position;
import net.feerie.creatura.shared.commons.Vecteur;
import net.feerie.creatura.shared.entites.Creature;

/**
 * Représente une action de déplacement
 * 
 * @author greewi
 */
public class ActionSeDeplacer extends Action
{
	private Action actionAEnchainer;
	private Position depart;
	private Position destination;
	private final int vitesse = 10; //Pixels par tics
	
	/**
	 * @param creature la créature se déplaçant
	 */
	public ActionSeDeplacer(Creature creature)
	{
		super(creature);
		this.depart = creature.getPosition();
		this.destination = null;
		this.actionAEnchainer = null;
	}
	
	/**
	 * @param creature la créature se déplaçant
	 * @param destination la position vers laquelle se déplace la créature
	 */
	public ActionSeDeplacer(Creature creature, Position destination)
	{
		super(creature);
		this.depart = creature.getPosition();
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
	public ActionSeDeplacer(Creature creature, Position destination, Action actionAEnchainer)
	{
		super(creature);
		this.depart = creature.getPosition();
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
	public void setActionAEnchainer(Action actionAEnchainer)
	{
		this.actionAEnchainer = actionAEnchainer;
	}
	
	private void calculeTrajet()
	{
		Vecteur direction = new Vecteur(depart, destination);
		double distance = direction.getNorme();
		setDuree((int) Math.ceil(distance / vitesse));
	}
	
	@Override
	public TypeAction getType()
	{
		return TypeAction.SE_DEPLACER;
	}
	
	@Override
	public boolean effectueTic()
	{
		dureeEcoulee++;
		double progression = getProgression();
		Position positionActuelle = new Position((1 - progression) * depart.x + progression * destination.x, (1 - progression) * depart.y + progression * destination.y);
		getCreature().setPosition(positionActuelle);
		if (dureeEcoulee >= dureeTotale)
			return termine();
		return true;
	}
	
	@Override
	public boolean termine()
	{
		getCreature().setPosition(new Position(destination));
		if (actionAEnchainer != null)
		{
			getCreature().setActionActuelle(actionAEnchainer);
			actionAEnchainer.debute();
			return true;
		}
		else
			return false;
	}
}
