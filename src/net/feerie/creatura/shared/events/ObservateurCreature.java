package net.feerie.creatura.shared.events;

import net.feerie.creatura.shared.actions.Action;
import net.feerie.creatura.shared.creature.moodles.TypeMoodle;

/**
 * Définis les observateurs de créature.
 * 
 * @author greewi
 */
public interface ObservateurCreature extends ObservateurEntite
{
	/**
	 * Appelé quand la créature voit sa santé modifiée
	 * 
	 * @param sante la modification de santé
	 */
	public void onChangeSante(int sante);
	
	/**
	 * Appelé quand la créature gagne un moodle
	 * 
	 * @param moodle le moodle gagné
	 */
	public void onGagneMoodle(TypeMoodle moodle);
	
	/**
	 * Appelé quand la créature perd un moodle
	 * 
	 * @param moodle le moodle perdu
	 */
	public void onPerdMoodle(TypeMoodle moodle);
	
	/**
	 * Appelé quand la créature commence une nouvelle action
	 * 
	 * @param action la nouvelle action, <tt>null</tt> si la créature n'a plus
	 *        rien à faire
	 */
	public void onChangeAction(Action action);
	
	/**
	 * Appelé quand la créature meurt
	 */
	public void onMeurt();
}
