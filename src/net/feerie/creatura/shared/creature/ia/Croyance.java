package net.feerie.creatura.shared.creature.ia;

import net.feerie.creatura.shared.actions.TypeAction;
import net.feerie.creatura.shared.creature.moodles.TypeMoodle;
import net.feerie.creatura.shared.entites.TypeEntite;

/**
 * Réprésente une croyance de l'IA. Une croyance est l'association d'une action
 * et de son résultat. Une croyance possède aussi un poids qui permet à l'IA
 * d'estimer sa véracité.
 * 
 * @author greewi
 */
public class Croyance
{
	private final TypeAction action;
	private final TypeEntite cibleAction;
	private final TypeCroyance type;
	private final TypeEntite resultatEntite;
	private final TypeMoodle resultatMoodle;
	private int poids;
	private int tempsDepuisDerniereActivation;
	
	/**
	 * Créé une croyance Nouvelle Entité
	 * 
	 * @param action l'action effectuée
	 * @param cibleAction la cible de l'action
	 * @param resultatEntite le type de la nouvelle entité
	 * @return la croyance créée
	 */
	public static Croyance creeNouvelleEntite(TypeAction action, TypeEntite cibleAction, TypeEntite resultatEntite)
	{
		return new Croyance(action, cibleAction, TypeCroyance.NOUVELLE_ENTITE, resultatEntite, null);
	}
	
	/**
	 * Créé une croyance Disparition Moodle
	 * 
	 * @param action l'action effectuée
	 * @param cibleAction la cible de l'action
	 * @param moodle le moodle qui a disparu
	 * @return la croyance créée
	 */
	public static Croyance creeDisparitionMoodle(TypeAction action, TypeEntite cibleAction, TypeMoodle moodle)
	{
		return new Croyance(action, cibleAction, TypeCroyance.DISPARITION_MOODLE, null, moodle);
	}
	
	/**
	 * Créé une croyance Apparition Moodle
	 * 
	 * @param action l'action effectuée
	 * @param cibleAction la cible de l'action
	 * @param moodle le moodle qui est apparu
	 * @return la croyance créée
	 */
	public static Croyance creeApparitionMoodle(TypeAction action, TypeEntite cibleAction, TypeMoodle moodle)
	{
		return new Croyance(action, cibleAction, TypeCroyance.APPARITION_MOODLE, null, moodle);
	}
	
	/**
	 * Créé une croyance Bien
	 * 
	 * @param action l'action effectuée
	 * @param cibleAction la cible de l'action
	 * @param moodle le moodle qui est apparu
	 * @return la croyance créée
	 */
	public static Croyance creeBien(TypeAction action, TypeEntite cibleAction)
	{
		return new Croyance(action, cibleAction, TypeCroyance.BIEN, null, null);
	}
	
	/**
	 * Créé une croyance Mal
	 * 
	 * @param action l'action effectuée
	 * @param cibleAction la cible de l'action
	 * @param moodle le moodle qui est apparu
	 * @return la croyance créée
	 */
	public static Croyance creeMal(TypeAction action, TypeEntite cibleAction)
	{
		return new Croyance(action, cibleAction, TypeCroyance.MAL, null, null);
	}
	
	/**
	 * Renvoie le type de la croyance
	 * 
	 * @return le type de la croyance
	 */
	public TypeCroyance getType()
	{
		return type;
	}
	
	/**
	 * Renvoie l'action de la croyance
	 * 
	 * @return l'action de la croyance
	 */
	public TypeAction getAction()
	{
		return action;
	}
	
	/**
	 * Renvoie la cible de l'action de la croyance
	 * 
	 * @return la cible de l'action de la croyance
	 */
	public TypeEntite getCibleAction()
	{
		return cibleAction;
	}
	
	/**
	 * Renvoie le type de l'entité pour les croyances Nouvelle Entité
	 * 
	 * @return le type de l'entité
	 */
	public TypeEntite getResultatEntite()
	{
		return resultatEntite;
	}
	
	/**
	 * Renvoie le type du moodle pour les croyances Apparition Moodle et
	 * Disparition Moodle
	 * 
	 * @return le type du moodle
	 */
	public TypeMoodle getResultatMoodle()
	{
		return resultatMoodle;
	}
	
	/**
	 * Renforce la croyance
	 */
	public void renforce()
	{
		poids += 20;
		if (poids > 100)
			poids = 100;
		tempsDepuisDerniereActivation = 0;
	}
	
	/**
	 * Renforce la croyance au maximum
	 */
	public void renforceAuMaximum()
	{
		poids = 100;
		tempsDepuisDerniereActivation = 0;
	}
	
	/**
	 * Affaiblit la croyance
	 */
	public void affaiblit()
	{
		poids -= 20;
		if (poids < 0)
			poids = 0;
		tempsDepuisDerniereActivation = 0;
	}
	
	/**
	 * Affaiblit la croyance par le temps
	 */
	public void affaiblitTemps()
	{
		tempsDepuisDerniereActivation++;
	}
	
	/**
	 * Renvoie le poids de la croyance
	 * 
	 * @return le poids de la croyance
	 */
	public int getPoids()
	{
		return poids * 1000 / (1000 + (tempsDepuisDerniereActivation * 1000) / 80);
	}
	
	private Croyance(TypeAction action, TypeEntite cibleAction, TypeCroyance type, TypeEntite resultatEntite, TypeMoodle resultatMoodle)
	{
		this.poids = 40;
		this.action = action;
		this.cibleAction = cibleAction;
		this.type = type;
		this.resultatEntite = resultatEntite;
		this.resultatMoodle = resultatMoodle;
	}
	
	@Override
	public int hashCode()
	{
		int hashcode = 0;
		if (action != null)
			hashcode += action.ordinal();
		hashcode *= 20;
		if (cibleAction != null)
			hashcode += cibleAction.ordinal();
		hashcode *= 20;
		if (type != null)
			hashcode += type.ordinal();
		hashcode *= 20;
		if (resultatEntite != null)
			hashcode += resultatEntite.ordinal();
		hashcode *= 20;
		if (resultatMoodle != null)
			hashcode += resultatMoodle.ordinal();
		return hashcode;
	}
	
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append(action).append("+").append(cibleAction).append(" : ").append(type);
		if (type == TypeCroyance.NOUVELLE_ENTITE)
			builder.append(" ").append(resultatEntite);
		else if (type == TypeCroyance.APPARITION_MOODLE || type == TypeCroyance.DISPARITION_MOODLE)
			builder.append(" ").append(resultatMoodle);
		builder.append(" (").append(getPoids()).append("%)");
		return builder.toString();
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof Croyance))
			return false;
		Croyance croyance = (Croyance) obj;
		if (croyance.action != action || croyance.cibleAction != cibleAction || croyance.type != type || croyance.resultatEntite != resultatEntite || croyance.resultatMoodle != resultatMoodle)
			return false;
		return true;
	}
}
