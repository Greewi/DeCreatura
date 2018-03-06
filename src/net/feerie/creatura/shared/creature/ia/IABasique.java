package net.feerie.creatura.shared.creature.ia;

import com.google.gwt.user.client.Random;

import net.feerie.creatura.shared.actions.Action;
import net.feerie.creatura.shared.actions.ActionDormir;
import net.feerie.creatura.shared.actions.ActionManger;
import net.feerie.creatura.shared.actions.ActionPopo;
import net.feerie.creatura.shared.actions.ActionSeDeplacer;
import net.feerie.creatura.shared.commons.Position;
import net.feerie.creatura.shared.commons.Vecteur;
import net.feerie.creatura.shared.creature.moodles.TypeMoodle;
import net.feerie.creatura.shared.entites.Creature;
import net.feerie.creatura.shared.entites.Entite;
import net.feerie.creatura.shared.entites.TypeEntite;

/**
 * L'IABasique de base/debug d'une créature
 * 
 * @author greewi
 */
public class IABasique implements IA
{
	private final Creature creature;
	
	public IABasique(Creature creature)
	{
		this.creature = creature;
	}

	@Override
	public Action decideProchaineAction()
	{
		if (creature.estAffectePar(TypeMoodle.FAIM))
		{
			Entite nourriture = creature.cherche(TypeEntite.NOURRITURE);
			if (nourriture != null)
				return new ActionSeDeplacer(creature, nourriture.getPosition(), new ActionManger(creature, nourriture));
		}
		if (creature.estAffectePar(TypeMoodle.SOIF))
		{
			Entite eau = creature.cherche(TypeEntite.NOURRITURE);
			if (eau != null)
				return new ActionSeDeplacer(creature, eau.getPosition(), new ActionManger(creature, eau));
		}
		if (creature.estAffectePar(TypeMoodle.POPO))
		{
			Entite litiere = creature.cherche(TypeEntite.LITIERE);
			if (litiere != null)
				return new ActionSeDeplacer(creature, litiere.getPosition(), new ActionPopo(creature, litiere));
		}
		if (creature.estAffectePar(TypeMoodle.FATIGUE))
		{
			return new ActionDormir(creature);
		}
		if (creature.estAffectePar(TypeMoodle.ENNUI))
		{
			Position destination = new Position(Random.nextInt(100), Random.nextInt(100));
			Vecteur vecteur = new Vecteur(creature.getPosition(), destination);
			double norme = vecteur.getNorme();
			if (norme > 25)
				destination = creature.getPosition().translate(vecteur.multiplie(25 / norme));
			creature.getMoodle(TypeMoodle.ENNUI).decharge(Random.nextInt(10)+15);
			return new ActionSeDeplacer(creature, destination, null);
		}
		
		creature.getMoodle(TypeMoodle.ENNUI).charge(5);
		return null;
	}
}
