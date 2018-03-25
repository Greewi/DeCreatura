package net.feerie.creatura.shared.entites;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Set;

import net.feerie.creatura.shared.actions.Action;
import net.feerie.creatura.shared.commons.Dimension;
import net.feerie.creatura.shared.commons.Position;
import net.feerie.creatura.shared.creature.ia.IAEvolutive;
import net.feerie.creatura.shared.creature.moodles.Moodle;
import net.feerie.creatura.shared.creature.moodles.TypeMoodle;
import net.feerie.creatura.shared.events.ObservateurCreature;
import net.feerie.creatura.shared.monde.Monde;

/**
 * Représente une créature
 * 
 * @author greewi
 */
public class Creature extends EntiteCreature
{
	private int sante;
	private final EnumMap<TypeMoodle, Moodle> moodles;
	private Set<ObservateurCreature> observateurs = new HashSet<>();
	
	public Creature(Monde monde, Position position)
	{
		super(monde, position, new Dimension(50, 50));
		this.sante = 100;
		this.moodles = new EnumMap<>(TypeMoodle.class);
		setIA(new IAEvolutive(this, 20));
	}
	
	@Override
	public String active(EntiteCreature activateur)
	{
		return "Creature";
	}
	
	/**
	 * Ajoute un observateur à cette créature
	 * 
	 * @param observateur l'observateur à ajouter
	 */
	public void ajouteObservateur(ObservateurCreature observateur)
	{
		observateurs.add(observateur);
	}
	
	/**
	 * Retire un observateur de cette créature
	 * 
	 * @param observateur l'observateur à ajouter
	 */
	public void retireObservateur(ObservateurCreature observateur)
	{
		observateurs.remove(observateur);
	}
	
	/**
	 * Renvoie le niveau de santé de la créature
	 * 
	 * @return le niveau de santé de la créature
	 */
	public int getSante()
	{
		return sante;
	}
	
	/**
	 * Réduit la santé de la créature
	 * 
	 * @param montant le montant de santé à réduire
	 */
	public void reduitSante(int montant)
	{
		sante -= montant;
		if (sante <= 0)
		{
			sante = 0;
			for (ObservateurCreature observateur : new ArrayList<>(observateurs))
				observateur.onMeurt();
		}
		for (ObservateurCreature observateur : observateurs)
			observateur.onChangeSante(sante);
	}
	
	/**
	 * Détermine si la créature est vivante.
	 * 
	 * @return <tt>true</tt> si la créature est vivante
	 */
	public boolean estVivante()
	{
		return sante > 0;
	}
	
	/**
	 * Récupére un moodle
	 * 
	 * @param moodle le type du moodle à récupérer
	 * @return le moodle
	 */
	public Moodle getMoodle(TypeMoodle moodle)
	{
		if (!moodles.containsKey(moodle))
			moodles.put(moodle, moodle.instancie(this));
		return moodles.get(moodle);
	}
	
	/**
	 * Détermine si la créature est affecté par un moodle.
	 * 
	 * @param moodle le type du moodle dont on cherche à savoir si la créature
	 *        est affectée par.
	 * @return <tt>true</tt> si la créature est affectée par le moodle
	 */
	public boolean estAffectePar(TypeMoodle moodle)
	{
		return getMoodle(moodle).estActif();
	}
	
	/**
	 * Appelée à chaque fois qu'un moodle apparait
	 * 
	 * @param moodle le moodle activé
	 */
	public void onGagneMoodle(TypeMoodle moodle)
	{
		for (ObservateurCreature observateur : observateurs)
			observateur.onGagneMoodle(moodle);
	}
	
	/**
	 * Appelé à chaque fois qu'un moodle disparait
	 * 
	 * @param moodle le moodle désactivé
	 */
	public void onPerdMoodle(TypeMoodle moodle)
	{
		for (ObservateurCreature observateur : observateurs)
			observateur.onPerdMoodle(moodle);
	}
	
	@Override
	public TypeEntite getType()
	{
		return TypeEntite.CREATURE;
	}
	
	@Override
	public void setActionActuelle(Action action)
	{
		super.setActionActuelle(action);
		for (ObservateurCreature observateur : observateurs)
			observateur.onChangeAction(action);
	}
	
	@Override
	public void effectueTic()
	{
		super.effectueTic();
		
		//Application des effets d'environnement
		if (position.z <= getMonde().getCarte().getHauteurEau(position))
			getMoodle(TypeMoodle.MOUILLE).active();
	}
	
	@Override
	public void effectueCycleMetabolique()
	{
		if (estVivante())
		{
			//Gain sante
			int ancienneSante = sante;
			sante++;
			
			//MAJ Moodle
			for (TypeMoodle typeMoodle : TypeMoodle.values())
			{
				Moodle moodle = getMoodle(typeMoodle);
				if (moodle.estActif())
					moodle.appliqueEffets();
			}
			for (TypeMoodle typeMoodle : TypeMoodle.values())
				getMoodle(typeMoodle).appliqueChargements();
			
			//Cap de santé
			if (sante > 100)
				sante = 100;
			if (sante < 0)
				sante = 0;
			if(sante!=ancienneSante)
				for (ObservateurCreature observateur : observateurs)
					observateur.onChangeSante(sante);
		}
	}
	
}
