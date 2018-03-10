package net.feerie.creatura.shared.monde;

/**
 * Représente une zone (un endroit, une salle) dans le monde
 * 
 * @author greewi
 */
public class Zone
{
	private final int longueur;
	private final int hauteurSolGauche;
	private final int hauteurSolDroite;
	private final int hauteurEau;
	private int x;
	private final Environnement environnement;
	
	/**
	 * @param longueur la longueur de la zone
	 * @param hauteurSolGauche la hauteur du sol de gauche
	 * @param hauteurSolDroite la hauteur du sol de droite
	 * @param hauteurEau la hauteur de l'eau
	 * @param environnement l'environnement de la zone
	 */
	public Zone(int longueur, int hauteurSolGauche, int hauteurSolDroite, int hauteurEau, Environnement environnement)
	{
		this.longueur = longueur;
		this.hauteurSolGauche = hauteurSolGauche;
		this.hauteurSolDroite = hauteurSolDroite;
		this.hauteurEau = hauteurEau;
		this.environnement = environnement;
		this.x = 0;
	}
	
	/**
	 * Renvoie la longueur de la zone
	 * 
	 * @return la longueur de la zone
	 */
	public int getLongueur()
	{
		return longueur;
	}
	
	/**
	 * Renvoie la position x du bord gauche de la zone.
	 * 
	 * @return la position x du bord gauche de la zone.
	 */
	public int getX()
	{
		return x;
	}
	
	/**
	 * Definis la position x du bord gauche de la zone.
	 * 
	 * @param x la position x du bord gauche de la zone.
	 */
	public void setX(int x)
	{
		this.x = x;
	}
	
	/**
	 * Renvoie la hauteur du sol en x (x compris entre 0 et la longueur de la
	 * zone)
	 * 
	 * @param x
	 * @return
	 */
	public int getHauteurSol(int x)
	{
		int denivele = hauteurSolDroite - hauteurSolGauche;
		return hauteurSolGauche + (denivele * x) / longueur;
	}
	
	public int getHauteurSolGauche()
	{
		return hauteurSolGauche;
	}
	
	public int getHauteurSolDroite()
	{
		return hauteurSolDroite;
	}
	
	/**
	 * Renvoie la hauteur du niveau de l'eau
	 * 
	 * @return la hauteur du niveau de l'eau
	 */
	public int getHauteurEau()
	{
		return hauteurEau;
	}
	
	/**
	 * @return l'environnement de la zone
	 */
	public Environnement getEnvironnement()
	{
		return environnement;
	}
	
	/**
	 * @return la couleur de la zone
	 * @deprecated
	 */
	public String getCouleur()
	{
		return environnement.getCouleur();
	}
}
