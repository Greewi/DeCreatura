package net.feerie.creatura.client.ui;

import com.google.gwt.dom.client.ButtonElement;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;

import net.feerie.creatura.shared.actions.Action;
import net.feerie.creatura.shared.creature.moodles.TypeMoodle;
import net.feerie.creatura.shared.entites.Creature;
import net.feerie.creatura.shared.events.ObservateurCreature;

public class PanneauToolbarCreature implements ObservateurCreature
{
	private Creature creature;
	private boolean ouvert;
	private final DivElement panneau;
	private final ButtonElement boutonSecherCreature;
	private final ButtonElement boutonMouillerCreature;
	
	public PanneauToolbarCreature()
	{
		panneau = DivElement.as(Document.get().getElementById("toolbarCreature"));
		boutonSecherCreature = ButtonElement.as(Document.get().getElementById("boutonSecherCreature"));
		boutonMouillerCreature = ButtonElement.as(Document.get().getElementById("boutonMouillerCreature"));
		creature = null;
		ouvert = false;
		
		Button.wrap(boutonSecherCreature).addClickHandler(new ClickHandler()
		{
			@Override
			public void onClick(ClickEvent event)
			{
				if (ouvert)
					creature.getMoodle(TypeMoodle.MOUILLE).desactive();
			}
		});
		
		Button.wrap(boutonMouillerCreature).addClickHandler(new ClickHandler()
		{
			@Override
			public void onClick(ClickEvent event)
			{
				if (ouvert)
					creature.getMoodle(TypeMoodle.MOUILLE).active();
			}
		});
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
		
		if (creature.estAffectePar(TypeMoodle.MOUILLE))
		{
			boutonSecherCreature.addClassName("toolbarCreature-bouton--actif");
			boutonMouillerCreature.removeClassName("toolbarCreature-bouton--actif");
		}
		else
		{
			boutonMouillerCreature.addClassName("toolbarCreature-bouton--actif");
			boutonSecherCreature.removeClassName("toolbarCreature-bouton--actif");
		}
		
		panneau.addClassName("toolbarCreature--ouvert");
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
		panneau.removeClassName("toolbarCreature--ouvert");
	}
	
	@Override
	public void onChangeSante(int sante)
	{
		// Ce panneau ne s'en occupe pas
	}
	
	@Override
	public void onGagneMoodle(TypeMoodle moodle)
	{
		if (moodle == TypeMoodle.MOUILLE)
		{
			boutonSecherCreature.addClassName("toolbarCreature-bouton--actif");
			boutonMouillerCreature.removeClassName("toolbarCreature-bouton--actif");
		}
	}
	
	@Override
	public void onPerdMoodle(TypeMoodle moodle)
	{
		if (moodle == TypeMoodle.MOUILLE)
		{
			boutonMouillerCreature.addClassName("toolbarCreature-bouton--actif");
			boutonSecherCreature.removeClassName("toolbarCreature-bouton--actif");
		}
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
