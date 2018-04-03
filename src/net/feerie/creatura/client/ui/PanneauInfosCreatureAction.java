package net.feerie.creatura.client.ui;

import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;

import net.feerie.creatura.shared.actions.Action;
import net.feerie.creatura.shared.creature.ia.IAEvolutive;
import net.feerie.creatura.shared.creature.moodles.TypeMoodle;
import net.feerie.creatura.shared.entites.Creature;
import net.feerie.creatura.shared.events.ObservateurCreature;

/**
 * Le panneau qui affiche l'action actuelle de la créature
 * 
 * @author greewi
 */
public class PanneauInfosCreatureAction implements ObservateurCreature
{
	private Creature creature;
	private boolean ouvert;
	private final DivElement panneau;
	private final DivElement actionCreature;
	private final DivElement cibleCreature;
	
	public PanneauInfosCreatureAction()
	{
		panneau = DivElement.as(Document.get().getElementById("infosCreatureAction"));
		actionCreature = DivElement.as(Document.get().getElementById("actionCreatureAction"));
		cibleCreature = DivElement.as(Document.get().getElementById("actionCreatureCible"));
		creature = null;
		ouvert = false;
	}
	
	/**
	 * Ouvre le panneau et inspecte une créature
	 * 
	 * @param creature la créature qui est inspectée
	 */
	public void ouvre(Creature creature)
	{
		ouvert = true;
		this.creature = creature;
		creature.ajouteObservateur(this);
		this.onChangeAction(creature.getActionActuelle());
		panneau.addClassName("infosCreatureAction--ouvert");
	}
	
	/**
	 * Ferme le panneau et cesse d'inspecter la créature
	 */
	public void ferme()
	{
		if (!ouvert)
			return;
		ouvert = false;
		creature.retireObservateur(this);
		creature = null;
		panneau.removeClassName("infosCreatureAction--ouvert");
	}
	
	@Override
	public void onChangeSante(int sante)
	{
		// Ce panneau ne s'en occupe pas
	}
	
	@Override
	public void onGagneMoodle(TypeMoodle moodle)
	{
		// Ce panneau ne s'en occupe pas
	}
	
	@Override
	public void onPerdMoodle(TypeMoodle moodle)
	{
		// Ce panneau ne s'en occupe pas
	}
	
	@Override
	public void onChangeAction(Action action)
	{
		if (action == null)
		{
			actionCreature.setClassName("icone icone-gris");
			cibleCreature.setClassName("icone icone-bleu");
		}
		else
		{
			if (creature.getIA() instanceof IAEvolutive)
			{
				IAEvolutive ia = (IAEvolutive) creature.getIA();
				actionCreature.setClassName("icone icone-action-" + ia.getAction().name());
				cibleCreature.setClassName("icone icone-entite-" + ia.getCibleAction().name());
			}
			else
			{
				actionCreature.setClassName("icone icone-action-" + action.getType().name());
				cibleCreature.setClassName("icone icone-bleu");
			}
		}
	}
	
	@Override
	public void onMeurt()
	{
		ferme();
	}
}
