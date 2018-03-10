package net.feerie.creatura.shared.creature.ia;

import net.feerie.creatura.shared.actions.IAction;

/**
 * Représente l'IA d'une créature
 * 
 * @author greewi
 */
public interface IA
{
	
	/**
	 * Décide de la prochaine action de la créature
	 * 
	 * @return la prochaine action à effectuer
	 */
	IAction decideProchaineAction();
}