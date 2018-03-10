package net.feerie.creatura.shared.entites;

import java.util.EnumMap;

import net.feerie.creatura.shared.actions.IAction;
import net.feerie.creatura.shared.commons.Dimension;
import net.feerie.creatura.shared.commons.Position;
import net.feerie.creatura.shared.creature.ia.IA;
import net.feerie.creatura.shared.creature.ia.IABasique;
import net.feerie.creatura.shared.creature.moodles.Moodle;
import net.feerie.creatura.shared.creature.moodles.TypeMoodle;
import net.feerie.creatura.shared.monde.Environnement;
import net.feerie.creatura.shared.monde.Monde;

/**
 * Représente une créature
 * 
 * @author greewi
 */
public class Creature extends Entite
{
	
	private IAction action;
	private final IA ia;
	
	private int sante;
	private final EnumMap<TypeMoodle, Moodle> moodles;
	
	public Creature(Monde monde, Position position)
	{
		super(monde, position, new Dimension(50, 50));
		
		//Action et environnement
		this.action = null;
		this.ia = new IABasique(this);
		
		//Santé
		this.sante = 100;
		this.moodles = new EnumMap<>(TypeMoodle.class);
		
		getMoodle(TypeMoodle.ENNUI).active();
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
	public void setActionActuelle(IAction action)
	{
		this.action = action;
	}
	
	/**
	 * @return l'action actuellement entreprise par la créature
	 */
	public IAction getActionActuelle()
	{
		return action;
	}
	
	@Override
	public TypeEntite getType()
	{
		return TypeEntite.CREATURE;
	}
	
	@Override
	public void effectueTic()
	{
		super.effectueTic();
		
		//Application des effets d'environnement
		if (position.z <= getMonde().getCarte().getHauteurEau(position))
			getMoodle(TypeMoodle.MOUILLE).active();
		
		//Si la créature est vivante, elle fait des trucs
		if (estVivante())
		{
			if (action != null)
				if (!action.effectueTic())
					this.action = null;
		}
		//Sinon rien...
		else
			action = null;
		
		//On applique la gravité
		appliqueGravite();
	}
	
	@Override
	public void effectueCycleIA()
	{
		if (estVivante())
			action = ia.decideProchaineAction();
	}
	
	@Override
	public void effectueCycleMetabolique()
	{
		if (estVivante())
		{
			//Gain sante
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
		return monde.getCarte().getEnvironnement(position);
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
