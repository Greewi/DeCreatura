package net.feerie.creatura.shared;

/**
 * Cette classe sert à cadenser le rythme de la simulation
 * 
 * @author greewi
 */
public class Metronome
{
	public final static long PERIODE_TIC = 300l;
	public final static long PERIODE_CYCLE_IA = 1;
	public final static long PERIODE_CYCLE_METABOLIQUE = 10;
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
	 * Exécute une nouvelle frame.
	 */
	public void nouvelleFrame()
	{
		long dateActuelle = System.currentTimeMillis();
		
		while (dateDernierTic + PERIODE_TIC <= dateActuelle)
		{
			dateDernierTic += PERIODE_TIC;
			ticActuel++;
			
			boolean effectuerCycleIA = false;
			if (ticDernierCycleIA + PERIODE_CYCLE_IA <= ticActuel)
			{
				ticDernierCycleIA += PERIODE_CYCLE_IA;
				effectuerCycleIA = true;
			}
			
			boolean effectuerCycleMetabolique = false;
			if (ticDernierCycleMetabolique + PERIODE_CYCLE_METABOLIQUE <= ticActuel)
			{
				ticDernierCycleMetabolique += PERIODE_CYCLE_METABOLIQUE;
				effectuerCycleMetabolique = true;
			}
			
			monde.effectueTic(effectuerCycleIA, effectuerCycleMetabolique);
		}
	}
	
}
