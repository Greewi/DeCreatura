package net.feerie.creatura.client.ui;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;

import net.feerie.creatura.shared.actions.Action;
import net.feerie.creatura.shared.creature.moodles.TypeMoodle;
import net.feerie.creatura.shared.entites.Creature;
import net.feerie.creatura.shared.events.ObservateurCreature;

public class PanneauInfosCreaturePhysique implements ObservateurCreature
{
	private Creature creature;
	private boolean ouvert;
	private final DivElement panneau;
	private final DivElement organeCreature;
	private final DivElement moodleCreature;
	private final DivElement santeCreature;
	private final Map<TypeMoodle, DivElement> elementsMoodles;
	
	public PanneauInfosCreaturePhysique()
	{
		panneau = DivElement.as(Document.get().getElementById("infosCreaturePhysique"));
		organeCreature = DivElement.as(Document.get().getElementById("organeCreature"));
		moodleCreature = DivElement.as(Document.get().getElementById("moodleCreature"));
		santeCreature = DivElement.as(Document.get().getElementById("santeCreature"));
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
		//Santé
		onChangeSante(creature.getSante());
		//Organes
		organeCreature.setInnerText("Pas encore implémenté");//TODO
		//Moodle
		for (TypeMoodle moodle : TypeMoodle.values())
			if (creature.estAffectePar(moodle))
				onGagneMoodle(moodle);
			
		panneau.addClassName("infosCreaturePhysique--ouvert");
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
		panneau.removeClassName("infosCreaturePhysique--ouvert");
	}
	
	@Override
	public void onChangeSante(int sante)
	{
		santeCreature.setInnerText(Integer.toString(sante));
	}
	
	@Override
	public void onGagneMoodle(TypeMoodle moodle)
	{
		if (elementsMoodles.containsKey(moodle))
			return;
		DivElement elementMoodle = Document.get().createDivElement();
		elementMoodle.setInnerText(moodle.getNom());
		elementMoodle.setClassName("infosCreaturePhysique-moodle");
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
