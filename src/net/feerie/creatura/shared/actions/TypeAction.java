package net.feerie.creatura.shared.actions;

public enum TypeAction
{
	SE_DEPLACER("Se Deplace"),
	MANGER("Mange"),
	BOIRE("Boit"),
	FAIRE_POPO("Fait Popo"),
	ACTIVER("Active"),
	DORMIR("Dort");
	
	/**
	 * @return le nom de l'action
	 */
	public String getNom()
	{
		return nom;
	}
	
	private final String nom;
	
	private TypeAction(String nom)
	{
		this.nom = nom;
	}
	
	@Override
	public String toString()
	{
		return nom;
	}
}
