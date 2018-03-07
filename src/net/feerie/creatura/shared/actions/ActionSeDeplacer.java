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
	private Position destination;
	private final double vitesse = 0.5;
	
	/**
	 * @param creature la créature se déplaçant
	 */
	public ActionSeDeplacer(Creature creature)
	{
		super(creature);
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
		this.destination = destination;
		this.actionAEnchainer = null;
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
		this.destination = destination;
		this.actionAEnchainer = actionAEnchainer;
	}
	
	/**
	 * Définis la destination
	 * 
	 * @param destination
	 */
	public void setDestination(Position destination)
	{
		this.destination = destination;
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
	
	@Override
	public TypeAction getType()
	{
		return TypeAction.SE_DEPLACER;
	}
	
	@Override
	public boolean metAJour(int frame)
	{
		if (destination == null)
			return false;
		
		//On avance d'une frame
		boolean arrive = avance();
		
		//Si on est arrivé, on se place bien sur le point d'arrivée et on effectuer l'action en attente
		if (arrive)
		{
			getCreature().setPosition(new Position(destination));
			if (actionAEnchainer != null)
			{
				getCreature().setActionActuelle(actionAEnchainer);
				actionAEnchainer.debute(frame);
				return true;
			}
			else
				return false;
		}
		//Si on n'est pas encore arrivé il faudra continuer à la prochaine frame
		else
			return true;
	}
	
	/**
	 * Avance d'une frame
	 * 
	 * @param frame le numéro de la frame actuelle
	 * @return <tt>true</tt> si et seulement si le déplaement est terminé.
	 */
	private boolean avance()
	{
		Position position = getCreature().getPosition();
		Vecteur direction = new Vecteur(position, destination);
		double distance = direction.getNorme();
		
		//Si on est arriv�, on s'arr�te
		if (distance < 0.0001)
			return true;
		
		double vitesse = (this.vitesse < distance) ? this.vitesse : distance;
		position = position.translate(direction.multiplie(vitesse / distance));
		getCreature().setPosition(position);
		return false;
	}
	
}
