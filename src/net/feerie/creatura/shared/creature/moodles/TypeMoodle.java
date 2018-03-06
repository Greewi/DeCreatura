package net.feerie.creatura.shared.creature.moodles;

import net.feerie.creatura.shared.entites.Creature;

/**
 * Regroupe les différents moodles.
 * 
 * @author greewi
 */
public enum TypeMoodle
{
	FAIM("A faim")
	{
		@Override
		public Moodle instancie(Creature creature)
		{
			return new MoodleFaim(creature);
		}
	},
	SOIF("A soif")
	{
		@Override
		public Moodle instancie(Creature creature)
		{
			return new MoodleSoif(creature);
		}
	},
	FATIGUE("Est fatigué")
	{
		@Override
		public Moodle instancie(Creature creature)
		{
			return new MoodleFatigue(creature);
		}
	},
	ENNUI("S'ennuie")
	{
		@Override
		public Moodle instancie(Creature creature)
		{
			return new MoodleEnnui(creature);
		}
	},
	FROID("A froid")
	{
		@Override
		public Moodle instancie(Creature creature)
		{
			return new MoodleFroid(creature);
		}
	},
	CHAUD("A chaud")
	{
		@Override
		public Moodle instancie(Creature creature)
		{
			return new MoodleChaud(creature);
		}
	},
	MOUILLE("Est mouillé(e)")
	{
		@Override
		public Moodle instancie(Creature creature)
		{
			return new MoodleMouille(creature);
		}
	},
	POPO("A besoin de faire")
	{
		@Override
		public Moodle instancie(Creature creature)
		{
			return new MoodlePopo(creature);
		}
	};
	
	private final String nom;
	
	private TypeMoodle(String nom)
	{
		this.nom = nom;
	}
	
	/**
	 * Renvoie le nom (une chaine lisible) du moodle
	 * 
	 * @return le nom (une chaine lisible) du moodle
	 */
	public String getNom()
	{
		return nom;
	}
	
	/**
	 * Instancie le moodle
	 * 
	 * @param creature la cérature potentiellement affectée par le moodle
	 * @return le moodle créé.
	 */
	public abstract Moodle instancie(Creature creature);
}
