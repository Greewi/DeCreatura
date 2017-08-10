package net.feerie.creatura.shared;

/**
 * Repr�sente les donn�es envrionnementales d'une zone ou celles affectant une
 * cr�ature
 */
public class Environnement
{
	private int temperature;
	private double luminosite;
	private String couleur;
	
	/**
	 * Constructeur
	 * 
	 * @param temperature la temp�rature (en degr�s celsius)
	 * @param luminosite la luminosit� (de 0.0f � 1.0f)
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
	 * @return la temp�rature de l'environnement
	 */
	public int getTemperature()
	{
		return temperature;
	}
	
	/**
	 * @return la luminosit� de l'environnement
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
