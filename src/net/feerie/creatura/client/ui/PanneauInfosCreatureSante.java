package net.feerie.creatura.client.ui;

import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Style.Unit;

import net.feerie.creatura.shared.actions.Action;
import net.feerie.creatura.shared.creature.moodles.TypeMoodle;
import net.feerie.creatura.shared.entites.Creature;
import net.feerie.creatura.shared.events.ObservateurCreature;

/**
 * Le panneau qui affiche la santé de la créature
 * 
 * @author greewi
 */
public class PanneauInfosCreatureSante implements ObservateurCreature
{
	private Creature creature;
	private boolean ouvert;
	private final DivElement panneau;
	private final DivElement santeCreature;
	
	public PanneauInfosCreatureSante()
	{
		panneau = DivElement.as(Document.get().getElementById("infosCreatureSante"));
		santeCreature = DivElement.as(Document.get().getElementById("santeCreature"));
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
		this.onChangeSante(creature.getSante());
		panneau.addClassName("infosCreatureSante--ouvert");
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
		panneau.removeClassName("infosCreatureSante--ouvert");
	}
	
	@Override
	public void onChangeSante(int sante)
	{
		santeCreature.getStyle().setWidth(sante, Unit.PCT);
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
		// Ce panneau ne s'en occupe pas
	}
	
	@Override
	public void onMeurt()
	{
		ferme();
	}
	
}
