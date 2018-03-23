package net.feerie.creatura.shared;

import net.feerie.creatura.shared.monde.Monde;

/**
 * Cette classe sert à cadenser le rythme de la simulation
 * 
 * @author greewi
 */
public class Metronome
{
	private long dateDernierTic;
	private long ticDernierCycleIA;
	private long ticDernierCycleMetabolique;
	private final Monde monde;
	private long ticActuel;
	
	/**
	 * @param monde le monde à cadenser
	 */
	public Metronome(Monde monde)
	{
		this.monde = monde;
		this.dateDernierTic = System.currentTimeMillis();
		ticDernierCycleIA = 0;
		ticDernierCycleMetabolique = 0;
		ticActuel = 0;
	}
	
	/**
	 * Renvoie la date du dernier tic en millisecondes
	 * 
	 * @return la date du dernier tic en millisecondes
	 */
	public long getDateDernierTic()
	{
		return dateDernierTic;
	}
	
	/**
	 * Exécute une nouvelle frame.
	 */
	public void nouvelleFrame(long dateActuelle)
	{	
		while (dateDernierTic + Constantes.PERIODE_TIC <= dateActuelle)
		{
			dateDernierTic += Constantes.PERIODE_TIC;
			ticActuel++;
			
			boolean effectuerCycleIA = false;
			if (ticDernierCycleIA + Constantes.PERIODE_CYCLE_IA <= ticActuel)
			{
				ticDernierCycleIA += Constantes.PERIODE_CYCLE_IA;
				effectuerCycleIA = true;
			}
			
			boolean effectuerCycleMetabolique = false;
			if (ticDernierCycleMetabolique + Constantes.PERIODE_CYCLE_METABOLIQUE <= ticActuel)
			{
				ticDernierCycleMetabolique += Constantes.PERIODE_CYCLE_METABOLIQUE;
				effectuerCycleMetabolique = true;
			}
			
			monde.effectueTic(effectuerCycleIA, effectuerCycleMetabolique);
		}
	}
	
}
