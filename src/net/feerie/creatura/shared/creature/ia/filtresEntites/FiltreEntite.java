package net.feerie.creatura.shared.creature.ia.filtresEntites;

import net.feerie.creatura.shared.entites.Entite;

/**
 * Cette interface définis un filtre d'entité
 * 
 * @author greewi
 */
public interface FiltreEntite
{
	/**
	 * Teste si une entité passe ce filtre
	 * 
	 * @param entite l'entité à tester
	 * @return <tt>true</tt> si et seulement si l'entité passe ce filtre
	 */
	public boolean teste(Entite entite);
}
