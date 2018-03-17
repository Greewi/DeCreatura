package net.feerie.creatura.shared.creature.ia;

/**
 * Définis les types de croyances d'une IA
 * 
 * @author greewi
 */
public enum TypeCroyance
{
	/**
	 * Nouvelle Entité : l'action fait apparaître une nouvelle entité
	 */
	NOUVELLE_ENTITE("+"),
	/**
	 * Disparition Moodle : l'action fait disparaitre un moodle
	 */
	DISPARITION_MOODLE("-"),
	/**
	 * ApparitionMoodle : l'action fait apparaitre un moodle
	 */
	APPARITION_MOODLE("+"),
	/**
	 * Mal : l'action à conduit à se faire gronder
	 */
	MAL("Mal"),
	/**
	 * Bien : l'action a été encouragée
	 */
	BIEN("Bien");
	
	private final String nom;
	
	private TypeCroyance(String nom)
	{
		this.nom = nom;
	}
	
	@Override
	public String toString()
	{
		return nom;
	}
	
}
