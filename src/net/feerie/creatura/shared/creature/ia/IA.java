package net.feerie.creatura.shared.creature.ia;

import net.feerie.creatura.shared.actions.Action;
import net.feerie.creatura.shared.creature.moodles.TypeMoodle;
import net.feerie.creatura.shared.entites.TypeEntite;

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
	Action decideProchaineAction();
	
	/**
	 * Encourage l'IA de la créature et renforce le schéma qui a permis la
	 * dernière décision.
	 */
	void encourage();
	
	/**
	 * Gronde l'IA de la créature et affaiblit le schéma qui a permis la
	 * dernière décision.
	 */
	void gronde();
	
	/**
	 * Découvre qu'une nouvelle entité est apparue suite à la dernière action
	 * effectuée
	 * 
	 * @param entite le type de l'entité apparue
	 */
	void constateNouvelleEntite(TypeEntite entite);
	
	/**
	 * Découvre qu'un moodle est apparu suite à la dernière action effectuée
	 * 
	 * @param moodle le type du moodle apparu
	 */
	void constateApparitionMoodle(TypeMoodle moodle);
	
	/**
	 * Découvre qu'un moodle a disparu suite à la dernière action effectuée
	 * 
	 * @param moodle le type du moodle disparu
	 */
	void constateDisparitionMoodle(TypeMoodle moodle);
	
}