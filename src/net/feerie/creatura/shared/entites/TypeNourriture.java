package net.feerie.creatura.shared.entites;

/**
 * Représente un type de nourriture
 * 
 * @author greewi
 */
public enum TypeNourriture
{
	
	TOMATE("Tomate", "#EB4B4B", 200, 200, 100, 0),
	CERISE("Cerise", "#BA3442", 300, 100, 100, 0),
	PRUNE("Prune", "#832B8F", 300, 100, 100, 0),
	FRAMBOISE("Framboise", "#ED268A", 400, 50, 50, 0),
	FRAISE("Fraise", "#F0401D", 200, 200, 100, 0),
	NOIX("Noix", "#A3754D", 100, 300, 100, 0),
	CACAHUETE("Cacahuete", "#C9BB7F", 100, 200, 200, 0),
	NOISETTE("Noisette", "#D18834", 100, 100, 300, 0),
	MAIS("Mais", "#D7DE12", 300, 100, 100, 0);
	
	/**
	 * @return le nom du type de nourriture
	 */
	public String getNom()
	{
		return nom;
	}
	
	/**
	 * @return la quantité d'eau contenue dans ce type de nourriture
	 */
	public int getEau()
	{
		return eau;
	}
	
	/**
	 * @return la quantité de sucres contenue dans ce type de nourriture
	 */
	public int getSucres()
	{
		return sucres;
	}
	
	/**
	 * @return la quantité de proteines contenue dans ce type de nourriture
	 */
	public int getProteines()
	{
		return proteines;
	}
	
	/**
	 * @return la quantité de gras contenue dans ce type de nourriture
	 */
	public int getGras()
	{
		return gras;
	}
	
	/**
	 * @return le code couleur de ce type de nourriture
	 */
	public String getCouleur()
	{
		return couleur;
	}
	
	private final String nom;
	private final String couleur;
	private final int sucres;
	private final int proteines;
	private final int gras;
	private final int eau;
	
	private TypeNourriture(String nom, String couleur, int sucres, int proteines, int gras, int eau)
	{
		this.nom = nom;
		this.couleur = couleur;
		this.sucres = sucres;
		this.proteines = proteines;
		this.gras = gras;
		this.eau = eau;
	}
	
}
