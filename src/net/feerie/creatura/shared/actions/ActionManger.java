package net.feerie.creatura.shared.actions;

import net.feerie.creatura.shared.creature.moodles.TypeMoodle;
import net.feerie.creatura.shared.entites.Creature;
import net.feerie.creatura.shared.entites.Entite;
import net.feerie.creatura.shared.entites.TypeEntite;

/**
 * Représente l'action de manger
 * 
 * @author greewi
 */
public class ActionManger extends Action
{
	private final Entite cible;
	
	/**
	 * @param creature la creature qui mange
	 * @param cible la chose qui sera mangée (enfin que la créature va tenter de
	 *        manger)
	 */
	public ActionManger(Creature creature, Entite cible)
	{
		super(creature);
		this.cible = cible;
	}
	
	@Override
	public TypeAction getType()
	{
		return TypeAction.MANGER;
	}
	
	@Override
	public boolean metAJour(int frame)
	{
		//Si la cible n'existe plus on arrète
		if (!cible.existe())
			return false;
		
		//Si la créature n'a pas fini de manger on continue
		if (frame < this.debut + 60)
			return true;
		
		//Sinon on applique les effets
		if (cible.getType() == TypeEntite.NOURRITURE)
		{
			cible.detruit();
			getCreature().getMoodle(TypeMoodle.FAIM).decharge(50);
			getCreature().getMoodle(TypeMoodle.POPO).charge(30);
		}
		return false;
	}
}
