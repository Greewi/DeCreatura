package net.feerie.creatura.client.ui;

import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;

import net.feerie.creatura.shared.actions.Action;
import net.feerie.creatura.shared.creature.moodles.TypeMoodle;
import net.feerie.creatura.shared.entites.Creature;
import net.feerie.creatura.shared.events.ObservateurCreature;

public class PanneauInfosCreatureMentales implements ObservateurCreature
{
	private Creature creature;
	private boolean ouvert;
	private final DivElement panneau;
	private final DivElement actionCreature;
	private final DivElement conceptsCreature;
	
	public PanneauInfosCreatureMentales()
	{
		panneau = DivElement.as(Document.get().getElementById("infosCreatureMentales"));
		actionCreature = DivElement.as(Document.get().getElementById("actionCreature"));
		conceptsCreature = DivElement.as(Document.get().getElementById("conceptsCreature"));
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
		//Concepts
		conceptsCreature.setInnerText("Pas encore implémenté");//TODO
		
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
			actionCreature.setInnerText("Attend");
		else
			actionCreature.setInnerText(action.getType().getNom());
	}
	
	@Override
	public void onMeurt()
	{
		ferme();
	}
}
