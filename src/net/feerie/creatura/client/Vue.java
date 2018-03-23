package net.feerie.creatura.client;

import java.util.HashMap;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.dom.client.CanvasElement;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;

import net.feerie.creatura.client.pixi.Application;
import net.feerie.creatura.client.pixi.Container;
import net.feerie.creatura.client.pixi.Loader;
import net.feerie.creatura.client.pixi.Options;
import net.feerie.creatura.client.pixi.Settings;
import net.feerie.creatura.client.renduPixi.RenduArbre;
import net.feerie.creatura.client.renduPixi.RenduCreature;
import net.feerie.creatura.client.renduPixi.RenduEau;
import net.feerie.creatura.client.renduPixi.RenduElement;
import net.feerie.creatura.client.renduPixi.RenduEntiteSimple;
import net.feerie.creatura.client.renduPixi.RenduMachines;
import net.feerie.creatura.client.renduPixi.RenduNuisible;
import net.feerie.creatura.client.renduPixi.RenduZone;
import net.feerie.creatura.client.renduPixi.Scene;
import net.feerie.creatura.shared.Console;
import net.feerie.creatura.shared.entites.Creature;
import net.feerie.creatura.shared.entites.CreatureNuisible;
import net.feerie.creatura.shared.entites.Entite;
import net.feerie.creatura.shared.entites.EntiteArbre;
import net.feerie.creatura.shared.entites.EntiteEau;
import net.feerie.creatura.shared.entites.TypeEntite;
import net.feerie.creatura.shared.events.ObservateurEntiteAjoutee;
import net.feerie.creatura.shared.events.ObservateurEntiteSupprimee;
import net.feerie.creatura.shared.monde.Monde;
import net.feerie.creatura.shared.monde.Zone;

public class Vue
{
	// Modèle
	private final Monde monde;
	
	// Interface
	private Canvas canvas;
	private CanvasElement canvasElement;
	private Application application;
	private int largeurFenetre;
	private int hauteurFenetre;
	private int largeurVue;
	private int hauteurVue;
	private int xVue;
	
	// Elements de rendu
	private final Container stage;
	private final Scene scene;
	private final HashMap<Integer, RenduElement> rendusEntites;
	
	public Vue(final Monde monde)
	{
		this.monde = monde;
		
		largeurVue = Window.getClientWidth();
		hauteurVue = Window.getClientHeight();
		largeurFenetre = Window.getClientWidth();
		hauteurFenetre = Window.getClientHeight();
		if (largeurFenetre / (double) largeurVue > hauteurFenetre / (double) hauteurVue)
			largeurFenetre = (int) (largeurVue * hauteurFenetre / (double) hauteurVue);
		else
			hauteurFenetre = (int) (hauteurVue * largeurFenetre / (double) largeurVue);
		
		//Création du canvas
		this.canvas = Canvas.createIfSupported();
		canvas.setCoordinateSpaceWidth(largeurFenetre);
		canvas.setCoordinateSpaceHeight(hauteurFenetre);
		RootPanel.get("canvas").add(canvas);
		canvasElement = canvas.getCanvasElement();
		
		//Création de l'application PIXI
		Settings.PRECISION_FRAGMENT = "highp";
		Settings.PRECISION_VERTEX = "highp";
		Options options = new Options();
		options.view = canvasElement;
		options.width = largeurFenetre;
		options.height = hauteurFenetre;
		options.backgroundColor = 0xc9ecff;
		application = new Application(options);
		stage = application.stage;
		scene = new Scene(stage);
		
		//Observateurs
		final OnEntiteAjoutee onEntiteAjoutee = new OnEntiteAjoutee();
		final OnEntiteSupprimee onEntiteSupprimee = new OnEntiteSupprimee();
		monde.onEntiteAjoutee(onEntiteAjoutee);
		monde.onEntiteSupprimee(onEntiteSupprimee);
		
		//Contenu de la vue
		rendusEntites = new HashMap<>();
		
		Settings.WRAP_MODE = Settings.WrapModes.REPEAT;
		Loader.add("images/Zone.png");
		Loader.load(new Loader.OnLoadCallback()
		{
			@Override
			public void onLoad()
			{
				Settings.WRAP_MODE = Settings.WrapModes.CLAMP;
				Loader.add("images/Creature.png");
				Loader.add("images/Nuisible.png");
				Loader.add("images/Granule.png");
				Loader.add("images/Fruit.png");
				Loader.add("images/Popo.png");
				
				Loader.load(new Loader.OnLoadCallback()
				{
					@Override
					public void onLoad()
					{
						for (Zone zone : monde.getCarte().getZones())
						{
							RenduElement rendu = new RenduZone(zone);
							rendu.ajoute(scene);
						}
						for (Entite entite : monde.getListeEntites())
							onEntiteAjoutee.onEntiteAjoutee(entite);
					}
				});
			}
		});
	}
	
	public int getLargeurFenetre()
	{
		return largeurFenetre;
	}
	
	public int getHauteurFenetre()
	{
		return hauteurFenetre;
	}
	
	public int getLargeurVue()
	{
		return largeurVue;
	}
	
	public int getHauteurVue()
	{
		return hauteurVue;
	}
	
	public int getXVue()
	{
		return xVue;
	}
	
	public void setXVue(int xVue)
	{
		if (xVue < 0)
			xVue = 0;
		if (xVue > monde.getCarte().getLongueurTotale() - largeurVue)
			xVue = monde.getCarte().getLongueurTotale() - largeurVue;
		this.xVue = xVue;
		stage.setPosition(-xVue, 0);
	}
	
	public Canvas getCanvas()
	{
		return canvas;
	}
	
	public void metAJour(long dateActuelle, double progressionTic)
	{
		for (RenduElement rendu : rendusEntites.values())
			rendu.metAJour(dateActuelle, progressionTic);
	}
	
	private class OnEntiteAjoutee implements ObservateurEntiteAjoutee
	{
		@Override
		public void onEntiteAjoutee(Entite entite)
		{
			RenduElement rendu = null;
			
			Console.log(entite.toString());
			
			if (entite.getType() == TypeEntite.CREATURE)
				rendu = new RenduCreature((Creature) entite);
			else if (entite.getType() == TypeEntite.CREATURE_NUISIBLE)
				rendu = new RenduNuisible((CreatureNuisible) entite);
			else if (entite.getType() == TypeEntite.POPO)
				rendu = new RenduEntiteSimple(entite);
			else if (entite.getType() == TypeEntite.NOURRITURE_GRANULE)
				rendu = new RenduEntiteSimple(entite);
			else if (entite.getType() == TypeEntite.NOURRITURE_FRUIT)
				rendu = new RenduEntiteSimple(entite);
			else if (entite.getType() == TypeEntite.NOURRITURE_FIBRE)
				rendu = new RenduEntiteSimple(entite);
			else if (entite.getType() == TypeEntite.NOURRITURE_GRAINE)
				rendu = new RenduEntiteSimple(entite);
			else if (entite.getType() == TypeEntite.NOURRITURE_INSECTE)
				rendu = new RenduEntiteSimple(entite);
			else if (entite.getType() == TypeEntite.NOURRITURE_POISSON)
				rendu = new RenduEntiteSimple(entite);
			else if (entite.getType() == TypeEntite.ARBRE_FRUITIER || entite.getType() == TypeEntite.ARBRE_COQUE)
				rendu = new RenduArbre((EntiteArbre) entite);
			else if (entite.getType() == TypeEntite.EAU)
				rendu = new RenduEau((EntiteEau) entite);
			else
				rendu = new RenduMachines(entite);
			
			rendusEntites.put(entite.getID(), rendu);
			rendu.ajoute(scene);
		}
	}
	
	private class OnEntiteSupprimee implements ObservateurEntiteSupprimee
	{
		@Override
		public void onEntiteSuprimee(Entite entite)
		{
			RenduElement renduADetruire = rendusEntites.remove(entite.getID());
			if (renduADetruire != null)
				renduADetruire.detruit();
		}
	}
}
