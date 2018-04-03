package net.feerie.creatura.client.ui;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;

import net.feerie.creatura.shared.actions.Action;
import net.feerie.creatura.shared.creature.moodles.TypeMoodle;
import net.feerie.creatura.shared.entites.Creature;
import net.feerie.creatura.shared.events.ObservateurCreature;

/**
 * Le panneau qui affiche les moodles de la créature
 * 
 * @author greewi
 */
public class PanneauInfosCreatureMoodles implements ObservateurCreature
{
	private Creature creature;
	private boolean ouvert;
	private final DivElement panneau;
	private final DivElement moodleCreature;
	private final Map<TypeMoodle, DivElement> elementsMoodles;
	
	public PanneauInfosCreatureMoodles()
	{
		panneau = DivElement.as(Document.get().getElementById("infosCreatureMoodle"));
		moodleCreature = DivElement.as(Document.get().getElementById("moodleCreature"));
		elementsMoodles = new HashMap<>();
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
		for (TypeMoodle moodle : TypeMoodle.values())
			if (creature.estAffectePar(moodle))
				onGagneMoodle(moodle);
		panneau.addClassName("infosCreatureMoodle--ouvert");
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
		for (DivElement elementMoodle : elementsMoodles.values())
			moodleCreature.removeChild(elementMoodle);
		elementsMoodles.clear();
		panneau.removeClassName("infosCreatureMoodle--ouvert");
	}
	
	@Override
	public void onChangeSante(int sante)
	{
		// Ce panneau ne s'en occupe pas
	}
	
	@Override
	public void onGagneMoodle(TypeMoodle moodle)
	{
		if (elementsMoodles.containsKey(moodle))
			return;
		DivElement elementMoodle = Document.get().createDivElement();
		elementMoodle.addClassName("icone");
		elementMoodle.addClassName("icone-moodle-" + moodle.name());
		moodleCreature.appendChild(elementMoodle);
		elementsMoodles.put(moodle, elementMoodle);
	}
	
	@Override
	public void onPerdMoodle(TypeMoodle moodle)
	{
		DivElement elementMoodle = elementsMoodles.remove(moodle);
		if (elementMoodle != null)
			moodleCreature.removeChild(elementMoodle);
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
