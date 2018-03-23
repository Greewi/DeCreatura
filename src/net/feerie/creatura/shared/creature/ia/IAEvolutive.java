package net.feerie.creatura.shared.creature.ia;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import net.feerie.creatura.shared.actions.Action;
import net.feerie.creatura.shared.actions.ActionActiver;
import net.feerie.creatura.shared.actions.ActionBoire;
import net.feerie.creatura.shared.actions.ActionDormir;
import net.feerie.creatura.shared.actions.ActionManger;
import net.feerie.creatura.shared.actions.ActionMarcherVersEntite;
import net.feerie.creatura.shared.actions.ActionPopo;
import net.feerie.creatura.shared.actions.TypeAction;
import net.feerie.creatura.shared.creature.ia.filtresEntites.FiltreTypeEntite;
import net.feerie.creatura.shared.creature.moodles.TypeMoodle;
import net.feerie.creatura.shared.entites.Creature;
import net.feerie.creatura.shared.entites.Entite;
import net.feerie.creatura.shared.entites.TypeEntite;

/**
 * Représente une IA cappable d'évoluer
 * 
 * @author greewi
 */
public class IAEvolutive implements IA
{
	private final Creature creature;
	private TypeAction derniereActionEffectuee;
	private TypeEntite cibleDerniereActionEffectuee;
	
	private final List<Croyance> croyances;
	private final int nombreCroyanceMaximal;
	
	private final FiltreTypeEntite filtreTypeEntite;
	private final Set<TypeAction> actionsDisponibles;
	private final TableAction tableAction;
	
	public IAEvolutive(Creature creature, int nombreCroyanceMaximal)
	{
		this.creature = creature;
		croyances = new ArrayList<>();
		this.nombreCroyanceMaximal = nombreCroyanceMaximal;
		derniereActionEffectuee = null;
		cibleDerniereActionEffectuee = null;
		
		filtreTypeEntite = new FiltreTypeEntite(null);
		actionsDisponibles = EnumSet.of(TypeAction.ACTIVER, TypeAction.DORMIR, TypeAction.BOIRE, TypeAction.MANGER, TypeAction.FAIRE_POPO);
		tableAction = new TableAction();
	}
	
	/**
	 * Renvoie la liste des croyances de la créature
	 * 
	 * @return
	 */
	public List<Croyance> getCroyances()
	{
		return croyances;
	}
	
	/**
	 * Renvoie le type de l'action actuelle
	 * 
	 * @return le type de l'action actuelle
	 */
	public TypeAction getAction()
	{
		return derniereActionEffectuee;
	}
	
	/**
	 * Renvoie le type de la cible de l'action actuelle
	 * 
	 * @return le type de la cible de l'action actuelle
	 */
	public TypeEntite getCibleAction()
	{
		return cibleDerniereActionEffectuee;
	}
	
	/**
	 * Récupère une croyance de la créature
	 * 
	 * @param type
	 * @param action
	 * @param cible
	 * @param moodleResultat
	 * @param entiteResultat
	 * @return
	 */
	private Croyance getCroyance(TypeCroyance type, TypeAction action, TypeEntite cible, TypeMoodle moodleResultat, TypeEntite entiteResultat)
	{
		for (Croyance croyance : croyances)
		{
			if (croyance.getType() == type && croyance.getAction() == action && croyance.getCibleAction() == cible && croyance.getResultatEntite() == entiteResultat && croyance.getResultatMoodle() == moodleResultat)
				return croyance;
		}
		return null;
	}
	
	@Override
	public void encourage()
	{
		if (!estActionOkPourApprentissage(derniereActionEffectuee))
			return;
		
		Croyance croyanceBien = getCroyance(TypeCroyance.BIEN, derniereActionEffectuee, cibleDerniereActionEffectuee, null, null);
		Croyance croyanceMal = getCroyance(TypeCroyance.MAL, derniereActionEffectuee, cibleDerniereActionEffectuee, null, null);
		
		if (croyanceBien != null)
			croyanceBien.renforce();
		else
		{
			if (croyanceMal != null)
			{
				croyanceMal.affaiblit();
				if (croyanceMal.getPoids() < 20)
					croyances.remove(croyanceMal);
			}
			else
				ajouteCroyance(Croyance.creeBien(derniereActionEffectuee, cibleDerniereActionEffectuee));
		}
	}
	
	@Override
	public void gronde()
	{
		if (!estActionOkPourApprentissage(derniereActionEffectuee))
			return;
		
		Croyance croyanceBien = getCroyance(TypeCroyance.BIEN, derniereActionEffectuee, cibleDerniereActionEffectuee, null, null);
		Croyance croyanceMal = getCroyance(TypeCroyance.MAL, derniereActionEffectuee, cibleDerniereActionEffectuee, null, null);
		
		if (croyanceMal != null)
			croyanceMal.renforce();
		else
		{
			if (croyanceBien != null)
			{
				croyanceBien.affaiblit();
				if (croyanceBien.getPoids() < 20)
					croyances.remove(croyanceBien);
			}
			else
				ajouteCroyance(Croyance.creeMal(derniereActionEffectuee, cibleDerniereActionEffectuee));
		}
	}
	
	@Override
	public void constateNouvelleEntite(TypeEntite entite)
	{
		if (!estActionOkPourApprentissage(derniereActionEffectuee))
			return;
		
		Croyance croyance = getCroyance(TypeCroyance.NOUVELLE_ENTITE, derniereActionEffectuee, cibleDerniereActionEffectuee, null, entite);
		if (croyance != null)
			croyance.renforce();
		else
			ajouteCroyance(Croyance.creeNouvelleEntite(derniereActionEffectuee, cibleDerniereActionEffectuee, entite));
	}
	
	@Override
	public void constateApparitionMoodle(TypeMoodle moodle)
	{
		if (!estActionOkPourApprentissage(derniereActionEffectuee))
			return;
		
		Croyance croyance = getCroyance(TypeCroyance.APPARITION_MOODLE, derniereActionEffectuee, cibleDerniereActionEffectuee, moodle, null);
		if (croyance != null)
			croyance.renforce();
		else
			ajouteCroyance(Croyance.creeApparitionMoodle(derniereActionEffectuee, cibleDerniereActionEffectuee, moodle));
	}
	
	@Override
	public void constateDisparitionMoodle(TypeMoodle moodle)
	{
		if (!estActionOkPourApprentissage(derniereActionEffectuee))
			return;
		
		Croyance croyance = getCroyance(TypeCroyance.DISPARITION_MOODLE, derniereActionEffectuee, cibleDerniereActionEffectuee, moodle, null);
		if (croyance != null)
			croyance.renforce();
		else
			ajouteCroyance(Croyance.creeDisparitionMoodle(derniereActionEffectuee, cibleDerniereActionEffectuee, moodle));
	}
	
	/**
	 * Détermine si l'action est propice pour un apprentissage.
	 * 
	 * @param action l'action considérée
	 * @return <tt>true</tt> si l'action est pertinente pour l'apprentissage
	 */
	private boolean estActionOkPourApprentissage(TypeAction action)
	{
		if (action == null)
			return false;
		switch (action)
		{
		case BOIRE:
		case MANGER:
		case DORMIR:
		case FAIRE_POPO:
		case ACTIVER:
			return true;
		default:
			return false;
		}
	}
	
	/**
	 * Ajoute une croyance. Si le nombre maximum de croyances a été atteint,
	 * supprime la croyance la plus faible.
	 * 
	 * @param croyance la croyance à ajouter
	 */
	private void ajouteCroyance(Croyance croyance)
	{
		// Si la créature a déjà trop de croyances, on supprime la croyance avec le plus petit poids 
		if (croyances.size() >= nombreCroyanceMaximal)
		{
			Croyance croyanceLaPlusFaible = null;
			for (Croyance c : croyances)
			{
				if (croyanceLaPlusFaible == null || c.getPoids() < croyanceLaPlusFaible.getPoids())
					croyanceLaPlusFaible = c;
			}
			if (croyanceLaPlusFaible != null)
				croyances.remove(croyanceLaPlusFaible);
		}
		// On ajoute la croyance
		croyances.add(croyance);
	}
	
	/**
	 * Fait évoluer les croyances
	 */
	private void evolueCroyances()
	{
		for (Croyance croyance : croyances)
			croyance.affaiblitTemps();
		
		Croyance croyanceManger = getCroyance(TypeCroyance.DISPARITION_MOODLE, TypeAction.MANGER, TypeEntite.NOURRITURE_GRANULE, TypeMoodle.FAIM, null);
		Croyance croyanceBoire = getCroyance(TypeCroyance.DISPARITION_MOODLE, TypeAction.BOIRE, TypeEntite.EAU, TypeMoodle.SOIF, null);
		if (croyanceManger == null)
			ajouteCroyance(Croyance.creeDisparitionMoodle(TypeAction.MANGER, TypeEntite.NOURRITURE_GRANULE, TypeMoodle.FAIM));
		else
			croyanceManger.renforce();
		if (croyanceBoire == null)
			ajouteCroyance(Croyance.creeDisparitionMoodle(TypeAction.BOIRE, TypeEntite.EAU, TypeMoodle.SOIF));
		else
			croyanceBoire.renforce();
	}
	
	@Override
	public Action decideProchaineAction()
	{
		evolueCroyances();
		
		Action actionActuelle = creature.getActionActuelle();
		if (actionActuelle != null)
			return actionActuelle;
		
		if (creature.estAffectePar(TypeMoodle.POPO))
			actionsDisponibles.add(TypeAction.FAIRE_POPO);
		else
			actionsDisponibles.remove(TypeAction.FAIRE_POPO);
		if (creature.estAffectePar(TypeMoodle.FATIGUE))
			actionsDisponibles.add(TypeAction.DORMIR);
		else
			actionsDisponibles.remove(TypeAction.DORMIR);
		
		Set<TypeEntite> entitesAPortee = creature.chercheEntiteDisponibles();
		tableAction.initialiseAleatoire(actionsDisponibles, entitesAPortee, -10, 10);
		
		// On applique les croyances
		for (Croyance croyance : croyances)
		{
			TypeAction action = croyance.getAction();
			TypeEntite cible = croyance.getCibleAction();
			
			int scoreCroyance = calculeScoreCroyance(croyance);
			int scoreAction = tableAction.getPoids(action, cible);
			tableAction.setPoids(action, cible, scoreAction + scoreCroyance);
		}
		
		ActionPonderee actionAJouer = tableAction.getMeilleureAction();
		derniereActionEffectuee = actionAJouer.getAction();
		cibleDerniereActionEffectuee = actionAJouer.getEntite();
		return creeAction(actionAJouer);
	}
	
	private int calculeScoreCroyance(Croyance croyance)
	{
		if (croyance.getType() == TypeCroyance.DISPARITION_MOODLE)
		{
			if (creature.estAffectePar(croyance.getResultatMoodle()))
				return 10 * croyance.getPoids();
		}
		if (croyance.getType() == TypeCroyance.APPARITION_MOODLE)
		{
			if (!creature.estAffectePar(croyance.getResultatMoodle()))
				return -5 * croyance.getPoids();
		}
		if (croyance.getType() == TypeCroyance.BIEN)
			return 5 * croyance.getPoids();
		if (croyance.getType() == TypeCroyance.MAL)
			return -10 * croyance.getPoids();
		return 0;
	}
	
	private Action creeAction(ActionPonderee actionCandidate)
	{
		filtreTypeEntite.setTypeEntite(actionCandidate.getEntite());
		Entite cible = creature.cherche(filtreTypeEntite);
		
		Action finale = null;
		if (actionCandidate.getAction() == TypeAction.BOIRE)
			finale = new ActionBoire(creature, cible);
		else if (actionCandidate.getAction() == TypeAction.MANGER)
			finale = new ActionManger(creature, cible);
		else if (actionCandidate.getAction() == TypeAction.FAIRE_POPO)
			finale = new ActionPopo(creature, cible);
		else if (actionCandidate.getAction() == TypeAction.DORMIR)
			finale = new ActionDormir(creature);
		else if (actionCandidate.getAction() == TypeAction.ACTIVER)
			finale = new ActionActiver(creature, cible);
		if (finale == null)
			return null;
		return new ActionMarcherVersEntite(creature, cible, finale);
	}
}
