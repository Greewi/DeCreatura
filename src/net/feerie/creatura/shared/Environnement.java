package net.feerie.creatura.shared;

/**
 * Représente les données envrionnementales d'une zone ou celles affectant une
 * créature
 */
public class Environnement
{
	private int temperature;
	private double luminosite;
	private String couleur;
	
	/**
	 * Constructeur
	 * 
	 * @param temperature la température (en degrés celsius)
	 * @param luminosite la luminosité (de 0.0f à 1.0f)
	 * @param couleur le code couleur de l'environnement
	 */
	public Environnement(int temperature, double luminosite, String couleur)
	{
		super();
		this.temperature = temperature;
		this.luminosite = luminosite;
		this.couleur = couleur;
	}
	
	/**
	 * @return la température de l'environnement
	 */
	public int getTemperature()
	{
		return temperature;
	}
	
	/**
	 * @return la luminosité de l'environnement
	 */
	public double getLuminosite()
	{
		return luminosite;
	}
	
	/**
	 * @return la couleur de l'environnement
	 */
	public String getCouleur()
	{
		return couleur;
	}
}
