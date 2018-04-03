package net.feerie.creatura.client;

import net.feerie.creatura.client.pixi.Container;
import net.feerie.creatura.shared.entites.Entite;
import net.feerie.creatura.shared.monde.Monde;

/**
 * Classe reponsable du positionnement de l'affichage et des conversions de
 * coordonnées écran<=>monde.
 * 
 * @author greewi
 */
public class Camera
{
	private double x; //Coordonnée x dans le monde du centre de l'écran 
	private double z; //Coordonnée y dans le monde du centre de l'écran
	private double zoom; //Facteur de zoom actuel (1.0 => hauteurVue = hauteurMonde)
	private int largeurMonde; //Largeur du monde en pixel de monde
	private int hauteurMonde; //Hauteur du monde en pixel de monde
	private int largeurEcran; //Largeur de l'écran en pixel
	private int hauteurEcran; //Hauteur de l'écran en pixel
	private double largeurVue; //Largeur de la vue en pixel de monde
	private double hauteurVue; //Hauteur de la vue en pixel de monde
	private Entite focus; //L'entité actuellement suivie. null si aucune entité n'est suivie 
	
	public Camera(Monde monde, int largeurEcran, int hauteurEcran)
	{
		this.largeurMonde = monde.getCarte().getLongueurTotale();
		this.hauteurMonde = 1000;
		this.largeurEcran = largeurEcran;
		this.hauteurEcran = hauteurEcran;
		this.zoom = 1.0;
		calculeCadrage();
	}
	
	/**
	 * Converti un coordonné écran en coordonné monde
	 * 
	 * @param xEcran
	 * @return
	 */
	public int getXMonde(double xEcran)
	{
		xEcran = xEcran - largeurEcran / 2;
		double xVue = xEcran * largeurVue / largeurEcran;
		int xMonde = (int) (xVue + x);
		return xMonde;
	}
	
	/**
	 * Converti un coordonné écran en coordonné monde
	 * 
	 * @param yEcran
	 * @return
	 */
	public int getZMonde(double yEcran)
	{
		yEcran = hauteurEcran / 2 - yEcran;
		double zVue = yEcran * hauteurVue / hauteurEcran;
		return (int) (zVue + z);
	}
	
	/**
	 * Renvoie la coordonnée X de la caméra dans le monde
	 * 
	 * @return la coordonnée X de la caméra dans le monde
	 */
	public double getX()
	{
		return x;
	}
	
	/**
	 * Définis la coordonnée X de la caméra dans le monde
	 * 
	 * @param x la nouvelle coordonnée X de la caméra dans le monde
	 */
	public void setX(double x)
	{
		if (x < largeurVue / 2)
			x = largeurVue / 2;
		if (x > largeurMonde - largeurVue / 2)
			x = largeurMonde - largeurVue / 2;
		this.x = x;
	}
	
	/**
	 * Renvoie la coordonnée Z de la caméra dans le monde
	 * 
	 * @return la coordonnée Z de la caméra dans le monde
	 */
	public double getZ()
	{
		return z;
	}
	
	/**
	 * Définis la coordonnée Z de la caméra dans le monde
	 * 
	 * @param z la nouvelle coordonnée Z de la caméra dans le monde
	 */
	public void setZ(double z)
	{
		if (z < hauteurVue / 2)
			z = hauteurVue / 2;
		if (z > hauteurMonde - hauteurVue / 2)
			z = hauteurMonde - hauteurVue / 2;
		this.z = z;
	}
	
	/**
	 * Applique les modifications de position et d'échelle à la vue
	 * 
	 * @param stage le conteneur racine de PIXI
	 * @param scene le conteneur de la scène
	 */
	public void applique(Container stage, Container scene)
	{
		stage.scale.x = largeurEcran / largeurVue;
		stage.scale.y = hauteurEcran / hauteurVue;
		scene.x = -(x - largeurVue / 2);
		scene.y = (hauteurVue / 2 + z) - hauteurMonde;
	}
	
	/**
	 * Recalcule le cadrage de la vue
	 */
	private void calculeCadrage()
	{
		hauteurVue = hauteurMonde / zoom;
		largeurVue = largeurEcran * hauteurVue / (double) hauteurEcran;
		setX(getX());
		setZ(getZ());
	}
	
	/**
	 * Redimentionne l'écran
	 * 
	 * @param largeurEcran la nouvelle largeur de l'écran en pixel
	 * @param hauteurEcran la nouvelle hauteur de l'écran en pixel
	 */
	public void redimentionne(int largeurEcran, int hauteurEcran)
	{
		this.largeurEcran = largeurEcran;
		this.hauteurEcran = hauteurEcran;
		calculeCadrage();
	}
	
	/**
	 * Focus la camera sur une entité
	 * 
	 * @param entite l'entité à focuser
	 * @param zoom le zoom lors du focus
	 */
	public void focusEntite(Entite entite, double zoom)
	{
		this.focus = entite;
		this.zoom = zoom;
		calculeCadrage();
		if (focus != null)
		{
			setX(focus.position.x);
			setZ(focus.position.z);
		}
	}
	
	/**
	 * Renvoie <tt>true</tt> si la caméra a le focus sur une entité
	 * 
	 * @return <tt>true</tt> si la caméra a le focus sur une entité
	 */
	public boolean aFocus()
	{
		return focus != null;
	}
	
	/**
	 * Renvoie l'entité actuellement sous le focus de la caméra ou <tt>null</tt>
	 * si la caméra n'a pas de focus
	 * 
	 * @return l'entité actuellement sous le focus de la caméra ou <tt>null</tt>
	 *         si la caméra n'a pas de focus
	 */
	public Entite getFocus()
	{
		return focus;
	}
	
	public void metAJour(long dateActuelle, double progressionTic)
	{
		final double taux = 1;
		
		if (focus != null)
		{
			double x = ((1 - progressionTic) * focus.position.x + progressionTic * focus.positionProchainTic.x);
			double z = ((1 - progressionTic) * focus.position.z + progressionTic * focus.positionProchainTic.z) + focus.getTaille().h / 2;
			x = getX() * (1 - taux) + x * taux;
			z = getZ() * (1 - taux) + z * taux;
			setX(x);
			setZ(z);
		}
	}
}
