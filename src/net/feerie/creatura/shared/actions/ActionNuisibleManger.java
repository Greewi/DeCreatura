package net.feerie.creatura.shared.actions;

import net.feerie.creatura.shared.Constantes;
import net.feerie.creatura.shared.entites.Entite;

public class ActionNuisibleManger extends AbstractActionAvecDuree
{
	private final Entite cible;
	
	/**
	 * @param creature la creature qui mange
	 * @param cible la chose qui sera mangée (enfin que la créature va tenter de
	 *        manger)
	 */
	public ActionNuisibleManger(Entite cible)
	{
		this.cible = cible;
		setDuree(Constantes.ACTION_NUISIBLE_MANGER_DUREE);
	}
	
	@Override
	public TypeAction getType()
	{
		return TypeAction.MANGER;
	}
	
	@Override
	public boolean termine()
	{
		//Si c'est de la nourriture on applique les effets
		if (cible.getType().estActionPossible(TypeAction.MANGER))
		{
			cible.detruit();
		}
		return false;
	}
	
}
