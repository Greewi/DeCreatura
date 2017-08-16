package net.feerie.creatura.shared.entites;

import java.util.ArrayList;
import java.util.EnumMap;

import net.feerie.creatura.shared.Environnement;
import net.feerie.creatura.shared.Monde;
import net.feerie.creatura.shared.actions.Action;
import net.feerie.creatura.shared.actions.ActionDormir;
import net.feerie.creatura.shared.actions.ActionManger;
import net.feerie.creatura.shared.actions.ActionPopo;
import net.feerie.creatura.shared.actions.ActionSeDeplacer;
import net.feerie.creatura.shared.commons.Dimension;
import net.feerie.creatura.shared.commons.Position;
import net.feerie.creatura.shared.organisme.Besoin;
import net.feerie.creatura.shared.organisme.Metabolisme;
import net.feerie.creatura.shared.organisme.TypeBesoin;
import net.feerie.creatura.shared.organisme.TypeVariableVitale;
import net.feerie.creatura.shared.organisme.VariableVitale;

import com.google.gwt.user.client.Random;

/**
 * Représente une créature
 * 
 * @author greewi
 */
public class Creature extends Entite
{
	private Action action;
	private Environnement environnementActuel;
	
	private final Metabolisme metabolisme;
	private final EnumMap<TypeBesoin, Besoin> besoins;
	
	public Creature(Monde monde, Position position)
	{
		super(monde, position, new Dimension(5.0, 5.0));
		//Metabolisme
		this.metabolisme = new Metabolisme(this);
		//Besoins
		besoins = new EnumMap<>(TypeBesoin.class);
		for (TypeBesoin typeBesoin : TypeBesoin.values())
			besoins.put(typeBesoin, new Besoin(typeBesoin, 0, this));
		//Action actuelle
		action = null;
	}
	
	/**
	 * @return l'environnement actuelle de la créature
	 */
	public Environnement getEnvironnementActuel()
	{
		return environnementActuel;
	}
	
	/**
	 * @param typeBesoin le type du besoin demandé
	 * @return le besoin demandé
	 */
	public Besoin getBesoin(TypeBesoin typeBesoin)
	{
		return besoins.get(typeBesoin);
	}
	
	/**
	 * @param type le type de la variable vitale demandée
	 * @return la variable vitale demandée
	 */
	public VariableVitale getVariableVitale(TypeVariableVitale type)
	{
		return metabolisme.get(type);
	}
	
	/**
	 * Défini l'action actuelle que doit entreprendre cette créature
	 * 
	 * @param action l'action que doit entreprendre cette créature
	 */
	public void setActionActuelle(Action action)
	{
		this.action = action;
	}
	
	/**
	 * @return l'action actuellement entreprise par la créature
	 */
	public Action getActionActuelle()
	{
		return action;
	}
	
	@Override
	public TypeEntite getType()
	{
		return TypeEntite.CREATURE;
	}
	
	@Override
	public void metAJour(int frame)
	{
		//IA et exécution des actions
		if (action != null)
		{
			if (!action.metAJour(frame))
				this.action = null;
		}
		else if (frame % 10 == 0)
			this.action = determineAction(frame);
		
		//Mise à jour de l'environnement
		environnementActuel = monde.getEnvironnement(getPosition());
		
		//Mise à jour du metabolisme
		this.metabolisme.metAJour();
		
		//Mise à jour des besoins
		this.metAJourBesoins();
	}
	
	private void metAJourBesoins()
	{
		//Nutriments
		double eauMin = metabolisme.get(TypeVariableVitale.EAU).getIdeal() / 4;
		double eauActuel = metabolisme.get(TypeVariableVitale.EAU).get() + metabolisme.get(TypeVariableVitale.EAU_DIGESTION).get();
		this.besoins.get(TypeBesoin.SOIF).set(1 - (eauActuel - eauMin) / (3.0 * eauMin));
		
		double sucresMin = metabolisme.get(TypeVariableVitale.SUCRES).getIdeal() / 4;
		double sucresActuel = metabolisme.get(TypeVariableVitale.SUCRES).get() + metabolisme.get(TypeVariableVitale.SUCRES_DIGESTION).get();
		this.besoins.get(TypeBesoin.SUCRES).set(1 - (sucresActuel - sucresMin) / (3.0 * sucresMin));
		
		double proteinesMin = metabolisme.get(TypeVariableVitale.PROTEINES).getIdeal() / 4;
		double proteinesActuel = metabolisme.get(TypeVariableVitale.PROTEINES).get() + metabolisme.get(TypeVariableVitale.PROTEINES_DIGESTION).get();
		this.besoins.get(TypeBesoin.PROTEINES).set(1 - (proteinesActuel - proteinesMin) / (3.0 * proteinesMin));
		
		double grasMin = metabolisme.get(TypeVariableVitale.GRAS).getIdeal() / 4;
		double grasActuel = metabolisme.get(TypeVariableVitale.GRAS).get() + metabolisme.get(TypeVariableVitale.GRAS_DIGESTION).get();
		this.besoins.get(TypeBesoin.GRAS).set(1 - (grasActuel - grasMin) / (3.0 * grasMin));
		
		//Dechets
		double dechetsMax = 3000;
		this.besoins.get(TypeBesoin.POPO).set(metabolisme.get(TypeVariableVitale.DECHETS).get() / dechetsMax);
		
		//Chaud/Froid
		double temperatureActuelle = metabolisme.get(TypeVariableVitale.TEMPERATURE).get();
		double temperatureIdeale = metabolisme.get(TypeVariableVitale.TEMPERATURE).getIdeal();
		this.besoins.get(TypeBesoin.CHAUD).set(0);
		this.besoins.get(TypeBesoin.FROID).set(0);
		if (temperatureActuelle > temperatureIdeale)
			this.besoins.get(TypeBesoin.FROID).set((temperatureActuelle - temperatureIdeale) / 2);
		else if (temperatureActuelle < temperatureIdeale)
			this.besoins.get(TypeBesoin.CHAUD).set((temperatureIdeale - temperatureActuelle) / 2);
		
		//Sommeil
		this.besoins.get(TypeBesoin.FATIGUE).set(1 - metabolisme.get(TypeVariableVitale.FATIGUE).get() / metabolisme.get(TypeVariableVitale.FATIGUE).getIdeal());
	};
	
	/**
	 * Détermine la prochaine action à entreprendre
	 */
	private Action determineAction(int frame)
	{
		//Focus aléatoire
		ArrayList<Entite> entites = monde.getListeEntites();
		Entite focus = entites.get(Random.nextInt(entites.size()));
		
		if (focus.getType() == TypeEntite.NOURRITURE && getBesoin(TypeBesoin.SUCRES).get() > 0.5)
			return new ActionSeDeplacer(this, focus, new ActionManger(this, focus));
		if (focus.getType() == TypeEntite.NOURRITURE && getBesoin(TypeBesoin.PROTEINES).get() > 0.5)
			return new ActionSeDeplacer(this, focus, new ActionManger(this, focus));
		if (focus.getType() == TypeEntite.NOURRITURE && getBesoin(TypeBesoin.GRAS).get() > 0.5)
			return new ActionSeDeplacer(this, focus, new ActionManger(this, focus));
		if (focus.getType() == TypeEntite.NOURRITURE && getBesoin(TypeBesoin.SOIF).get() > 0.5)
			return new ActionSeDeplacer(this, focus, new ActionManger(this, focus));
		if (getBesoin(TypeBesoin.POPO).get() > 0.7)
			return new ActionSeDeplacer(this, focus, new ActionPopo(monde, this, focus));
		if (getBesoin(TypeBesoin.FATIGUE).get() > 0.9)
			return new ActionSeDeplacer(this, focus, new ActionDormir(this, focus));
		return null;
	};
}
