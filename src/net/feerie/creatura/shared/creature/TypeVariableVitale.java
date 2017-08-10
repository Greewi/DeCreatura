package net.feerie.creatura.shared.creature;

/**
 * Représente le type des variables vitales
 * 
 * @author greewi
 */
public enum TypeVariableVitale
{
	//Nutriments
	EAU_DIGESTION("Eau (Digestion)", 0, 0, 0, -1),
	EAU("Eau", 2000, 2000, 0, -1),
	SUCRES_DIGESTION("Sucres (Digestion)", 0, 0, 0, -1),
	SUCRES("Sucres", 800, 800, 0, -1),
	PROTEINES_DIGESTION("Proteines (Digestion)", 0, 0, 0, -1),
	PROTEINES("Proteines", 300, 300, 0, -1),
	GRAS_DIGESTION("Gras (Digestion)", 0, 0, 0, -1),
	GRAS("Gras", 700, 700, 0, -1),
	DECHETS("Dechets", 0, 0, 0, -1),
	//Variables physiques
	TEMPERATURE("Temperature", 35, 35, -1, -1),
	//Meta Variables
	FATIGUE("Fatigue", 0, 20000, 20000, 20000),
	SANTE("Sante", 1000, 1000, 0, 1000);
	
	public String getNom()
	{
		return nom;
	}
	
	public double getValeurInitiale()
	{
		return valeurInitiale;
	}
	
	public double getValeurIdeale()
	{
		return valeurIdeale;
	}
	
	public double getValeurMin()
	{
		return valeurMin;
	}
	
	public double getValeurMax()
	{
		return valeurMax;
	}
	
	private final String nom;
	private final double valeurInitiale;
	private final double valeurIdeale;
	private final double valeurMin;
	private final double valeurMax;
	
	private TypeVariableVitale(String nom, double valeurInitiale, double valeurIdeale, double valeurMin, double valeurMax)
	{
		this.nom = nom;
		this.valeurInitiale = valeurInitiale;
		this.valeurIdeale = valeurIdeale;
		this.valeurMin = valeurMin;
		this.valeurMax = valeurMax;
	}
	
}
