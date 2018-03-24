package net.feerie.creatura.client.renduPixi;

import net.feerie.creatura.client.pixi.Container;

/**
 * Réprésente la scène. Elle sert à organiser les éléments de rendu par couches
 * 
 * @author greewi
 */
public class Scene
{
	private final Container conteneur;
	private final Container coucheArrierePlan;
	private final Container coucheElementsFond;
	private final Container coucheElementsMilieu;
	private final Container coucheElementsAvant;
	private final Container coucheAvantPlan;
	
	/**
	 * Le stage PIXI dans lequel insérer la scène
	 * 
	 * @param stage
	 */
	public Scene(Container stage)
	{
		conteneur = new Container();
		stage.addChild(conteneur);
		coucheArrierePlan = new Container();
		conteneur.addChild(coucheArrierePlan);
		coucheElementsFond = new Container();
		conteneur.addChild(coucheElementsFond);
		coucheElementsMilieu = new Container();
		conteneur.addChild(coucheElementsMilieu);
		coucheElementsAvant = new Container();
		conteneur.addChild(coucheElementsAvant);
		coucheAvantPlan = new Container();
		conteneur.addChild(coucheAvantPlan);
	}
	
	/**
	 * Renvoie le conteneur de la scène
	 * 
	 * @return le conteneur de la scène
	 */
	public Container getConteneur()
	{
		return conteneur;
	}
	
	/**
	 * Ajoute un élément à l'arrière plan
	 * 
	 * @param container
	 */
	public void ajouteArrierePlan(Container container)
	{
		coucheArrierePlan.addChild(container);
	}
	
	/**
	 * Ajoute un élément à l'arrière de la scène
	 * 
	 * @param container
	 */
	public void ajouteElementFond(Container container)
	{
		coucheElementsFond.addChild(container);
	}
	
	/**
	 * Ajoute un élément au milieu de la scène
	 * 
	 * @param container
	 */
	public void ajouteElementMilieu(Container container)
	{
		coucheElementsMilieu.addChild(container);
	}
	
	/**
	 * Ajoute un élément à l'avant de la scène
	 * 
	 * @param container
	 */
	public void ajouteElementAvant(Container container)
	{
		coucheElementsAvant.addChild(container);
	}
	
	/**
	 * Ajoute un élément à l'avant plan
	 * 
	 * @param container
	 */
	public void ajouteAvantPlan(Container container)
	{
		coucheAvantPlan.addChild(container);
	}
	
	/**
	 * Ajoute un élément de la scène
	 * 
	 * @param container
	 */
	public void retireElement(Container container)
	{
		Container parent = container.getParent();
		if (parent != null)
			parent.removeChild(container);
	}
	
}
