package net.feerie.creatura.shared.entites;

import java.util.EnumMap;

import net.feerie.creatura.shared.Environnement;
import net.feerie.creatura.shared.Monde;
import net.feerie.creatura.shared.actions.Action;
import net.feerie.creatura.shared.commons.Dimension;
import net.feerie.creatura.shared.commons.Position;
import net.feerie.creatura.shared.creature.ia.IA;
import net.feerie.creatura.shared.creature.ia.IABasique;
import net.feerie.creatura.shared.creature.moodles.Moodle;
import net.feerie.creatura.shared.creature.moodles.TypeMoodle;

/**
 * Représente une créature
 * 
 * @author greewi
 */
public class Creature extends Entite
{
	private final static long PERIODE_CYCLE_IA = 300l;
	private final static long PERIODE_CYCLE_METABOLIQUE = PERIODE_CYCLE_IA * 10;
	
	private Action action;
	private Environnement environnementActuel;
	private final IA ia;
	private long dateDernierCycleIA;
	
	private int sante;
	private final EnumMap<TypeMoodle, Moodle> moodles;
	private long dateDernierCycleMetabolique;
	
	public Creature(Monde monde, Position position)
	{
		super(monde, position, new Dimension(5.0, 5.0));
		
		//Action et environnement
		this.action = null;
		this.environnementActuel = monde.getEnvironnement(position);
		this.ia = new IABasique(this);
		this.dateDernierCycleIA = System.currentTimeMillis();
		
		//Sant�
		this.sante = 100;
		this.moodles = new EnumMap<>(TypeMoodle.class);
		this.dateDernierCycleMetabolique = this.dateDernierCycleIA;
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
			sante = 0;
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
		if (!estVivante())
		{
			action = null;
			return;
		}
		
		//Execution des actions
		if (action != null)
		{
			if (!action.metAJour(frame))
				this.action = null;
		}
		
		//IA
		if (dateDernierCycleIA + PERIODE_CYCLE_IA < System.currentTimeMillis())
		{
			dateDernierCycleIA += PERIODE_CYCLE_IA;
			if (action == null)
				action = ia.decideProchaineAction();
		}
		
		//Mise à jour de l'environnement
		environnementActuel = monde.getEnvironnement(getPosition());
		
		//Mise à jour du metabolisme
		if (dateDernierCycleMetabolique + PERIODE_CYCLE_METABOLIQUE < System.currentTimeMillis())
		{
			dateDernierCycleMetabolique += PERIODE_CYCLE_METABOLIQUE;
			
			//Gain sante
			sante++;
			
			//MAJ Moodle
			for (TypeMoodle typeMoodle : TypeMoodle.values())
			{
				Moodle moodle = getMoodle(typeMoodle);
				moodle.nouveauCycle();
			}
			
			//Cap de santé
			if (sante > 100)
				sante = 100;
			if (sante < 0)
				sante = 0;
		}
	}
	
	/**
	 * Renvoie le monde dans lequel �volue la cr�ature
	 * 
	 * @return le monde dans lequel �volue la cr�ature
	 */
	public Monde getMonde()
	{
		return monde;
	}
	
	/**
	 * R�cup�re l'environnement actuel
	 * 
	 * @return l'environnement actuel
	 */
	public Environnement getEnvironnement()
	{
		return environnementActuel;
	}
	
	/**
	 * Recherche l'entite d'un type donn� le plus proche
	 * 
	 * @param typeEntite le type de l'entit�
	 * @return l'entit� trouv�e ou <tt>null</tt>
	 */
	public Entite cherche(TypeEntite typeEntite)
	{
		Entite entite = null;
		double distance = 100000000000.0;
		for (Entite e : monde.getListeEntites())
			if (e.getType() == typeEntite && getDistanceCarre(e) < distance)
			{
				entite = e;
				distance = getDistanceCarre(e);
			}
		return entite;
	}
}
