package net.feerie.creatura.client.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;

import net.feerie.creatura.shared.actions.Action;
import net.feerie.creatura.shared.creature.ia.Croyance;
import net.feerie.creatura.shared.creature.ia.IAEvolutive;
import net.feerie.creatura.shared.creature.ia.TypeCroyance;
import net.feerie.creatura.shared.creature.moodles.TypeMoodle;
import net.feerie.creatura.shared.entites.Creature;
import net.feerie.creatura.shared.events.ObservateurCreature;

/**
 * Panneau qui affiche les croyances de la créature
 * 
 * @author greewi
 */
public class PanneauInfosCreatureCroyances implements ObservateurCreature
{
	private Creature creature;
	private boolean ouvert;
	private final DivElement panneau;
	private final DivElement croyancesCreature;
	private final Map<Croyance, DivElement> elementsCroyances;
	
	public PanneauInfosCreatureCroyances()
	{
		panneau = DivElement.as(Document.get().getElementById("infosCreatureCroyances"));
		croyancesCreature = DivElement.as(Document.get().getElementById("croyancesCreature"));
		elementsCroyances = new HashMap<>();
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
		metAJourCroyances();
		panneau.addClassName("infosCreatureCroyances--ouvert");
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
		for (DivElement elementCroyance : elementsCroyances.values())
			croyancesCreature.removeChild(elementCroyance);
		elementsCroyances.clear();
		panneau.removeClassName("infosCreatureCroyances--ouvert");
	}
	
	/**
	 * Met à jour les concepts
	 */
	private void metAJourCroyances()
	{
		croyancesCreature.removeAllChildren();
		if (creature.getIA() instanceof IAEvolutive)
		{
			IAEvolutive ia = (IAEvolutive) creature.getIA();
			List<Croyance> croyances = ia.getCroyances();
			croyances.sort((Croyance c1, Croyance c2) -> {
				return c2.getPoids() - c1.getPoids();
			});
			for (Croyance croyance : croyances)
			{
				DivElement elementCroyance = genereElementCroyance(croyance);
				double opacite = croyance.getPoids() / 100.0;
				opacite *= 2;
				if (opacite > 1)
					opacite = 1;
				
				elementCroyance.getStyle().setOpacity(opacite);
				croyancesCreature.appendChild(elementCroyance);
			}
		}
	}
	
	/**
	 * Génère l'élément d'une croyance
	 * 
	 * @param croyance
	 * @return
	 */
	private DivElement genereElementCroyance(Croyance croyance)
	{
		DivElement elementCroyance = Document.get().createDivElement();
		DivElement iconeAction = Document.get().createDivElement();
		iconeAction.setClassName("icone icone-action-" + croyance.getAction().name());
		elementCroyance.appendChild(iconeAction);
		DivElement iconePlus = Document.get().createDivElement();
		iconePlus.setClassName("icone icone-PLUS");
		elementCroyance.appendChild(iconePlus);
		DivElement iconeCible = Document.get().createDivElement();
		iconeCible.setClassName("icone icone-entite-" + croyance.getCibleAction().name());
		elementCroyance.appendChild(iconeCible);
		DivElement iconeImplique = Document.get().createDivElement();
		iconeImplique.setClassName("icone icone-IMPLIQUE");
		elementCroyance.appendChild(iconeImplique);
		
		if (croyance.getType() == TypeCroyance.BIEN || croyance.getType() == TypeCroyance.MAL)
		{
			DivElement iconeCroyance = Document.get().createDivElement();
			iconeCroyance.setClassName("icone icone-croyance-" + croyance.getType().name());
			elementCroyance.appendChild(iconeCroyance);
		}
		else if (croyance.getType() == TypeCroyance.APPARITION_MOODLE || croyance.getType() == TypeCroyance.DISPARITION_MOODLE)
		{
			DivElement iconeMoodle = Document.get().createDivElement();
			iconeMoodle.setClassName("icone icone-moodle-" + croyance.getResultatMoodle().name());
			elementCroyance.appendChild(iconeMoodle);
			DivElement iconeModificateur = Document.get().createDivElement();
			iconeModificateur.setClassName("icone icone-croyance-" + (croyance.getType() == TypeCroyance.APPARITION_MOODLE ? "PLUS" : "MOINS"));
			elementCroyance.appendChild(iconeModificateur);
		}
		else if (croyance.getType() == TypeCroyance.NOUVELLE_ENTITE)
		{
			DivElement iconeEntite = Document.get().createDivElement();
			iconeEntite.setClassName("icone icone-entite-" + croyance.getResultatEntite().name());
			elementCroyance.appendChild(iconeEntite);
			DivElement iconeModificateur = Document.get().createDivElement();
			iconeModificateur.setClassName("icone icone-croyance-PLUS");
			elementCroyance.appendChild(iconeModificateur);
		}
		return elementCroyance;
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
	}
	
	@Override
	public void onMeurt()
	{
		ferme();
	}
	
}
