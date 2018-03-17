package net.feerie.creatura.client.ui;

import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;

import net.feerie.creatura.shared.actions.Action;
import net.feerie.creatura.shared.creature.ia.Croyance;
import net.feerie.creatura.shared.creature.ia.IAEvolutive;
import net.feerie.creatura.shared.creature.moodles.TypeMoodle;
import net.feerie.creatura.shared.entites.Creature;
import net.feerie.creatura.shared.events.ObservateurCreature;

public class PanneauInfosCreatureMentales implements ObservateurCreature
{
	private Creature creature;
	private boolean ouvert;
	private final DivElement panneau;
	private final DivElement actionCreature;
	private final DivElement croyancesCreature;
	
	public PanneauInfosCreatureMentales()
	{
		panneau = DivElement.as(Document.get().getElementById("infosCreatureMentales"));
		actionCreature = DivElement.as(Document.get().getElementById("actionCreature"));
		croyancesCreature = DivElement.as(Document.get().getElementById("croyancesCreature"));
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
		//Action
		onChangeAction(creature.getActionActuelle());
		//Croyances
		metAJourCroyances();
		
		panneau.addClassName("infosCreatureMentales--ouvert");
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
		panneau.removeClassName("infosCreatureMentales--ouvert");
	}
	
	/**
	 * Met à jour les concepts
	 */
	private void metAJourCroyances()
	{
		if (creature.getIA() instanceof IAEvolutive)
		{
			IAEvolutive ia = (IAEvolutive) creature.getIA();
			StringBuilder builder = new StringBuilder();
			for (Croyance croyance : ia.getCroyances())
				builder.append("<div>").append(croyance).append("</div>");
			croyancesCreature.setInnerHTML(builder.toString());
		}
		else
			croyancesCreature.setInnerText("IA Non Compatible");
		
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
		metAJourCroyances();
		if (action == null)
			actionCreature.setInnerText("Attend");
		else
		{
			if (creature.getIA() instanceof IAEvolutive)
			{
				IAEvolutive ia = (IAEvolutive) creature.getIA();
				actionCreature.setInnerText(ia.getAction().getNom() + " + " + ia.getCibleAction());
			}
			else
				actionCreature.setInnerText(action.getType().getNom());
		}
		
	}
	
	@Override
	public void onMeurt()
	{
		ferme();
	}
}
