package net.feerie.creatura.client;

import java.util.HashMap;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.dom.client.CanvasElement;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;

import net.feerie.creatura.client.pixi.Application;
import net.feerie.creatura.client.pixi.Container;
import net.feerie.creatura.client.pixi.Loader;
import net.feerie.creatura.client.pixi.Options;
import net.feerie.creatura.client.pixi.PIXI;
import net.feerie.creatura.client.renduPixi.RenduArbre;
import net.feerie.creatura.client.renduPixi.RenduCreature;
import net.feerie.creatura.client.renduPixi.RenduEau;
import net.feerie.creatura.client.renduPixi.RenduElement;
import net.feerie.creatura.client.renduPixi.RenduEntiteSimple;
import net.feerie.creatura.client.renduPixi.RenduLitere;
import net.feerie.creatura.client.renduPixi.RenduMachines;
import net.feerie.creatura.client.renduPixi.RenduNuisible;
import net.feerie.creatura.client.renduPixi.RenduZone;
import net.feerie.creatura.client.renduPixi.Scene;
import net.feerie.creatura.shared.entites.Creature;
import net.feerie.creatura.shared.entites.CreatureNuisible;
import net.feerie.creatura.shared.entites.Entite;
import net.feerie.creatura.shared.entites.EntiteArbre;
import net.feerie.creatura.shared.entites.EntiteDistributeurGranule;
import net.feerie.creatura.shared.entites.EntiteEau;
import net.feerie.creatura.shared.entites.EntiteLitiere;
import net.feerie.creatura.shared.entites.TypeEntite;
import net.feerie.creatura.shared.events.ObservateurEntiteAjoutee;
import net.feerie.creatura.shared.events.ObservateurEntiteSupprimee;
import net.feerie.creatura.shared.monde.Monde;
import net.feerie.creatura.shared.monde.Zone;

public class Vue
{
	// Interface
	private final Canvas canvas;
	private final CanvasElement canvasElement;
	private final Application application;
	private final Camera camera;
	
	// Elements de rendu
	private final Container stage;
	private final Scene scene;
	private final HashMap<Integer, RenduElement> rendusEntites;
	
	public Vue(final Monde monde)
	{
		//Création du canvas
		this.canvas = Canvas.createIfSupported();
		canvas.setCoordinateSpaceWidth(Window.getClientWidth());
		canvas.setCoordinateSpaceHeight(Window.getClientHeight());
		RootPanel.get("canvas").add(canvas);
		canvasElement = canvas.getCanvasElement();
		
		//Création de l'application PIXI
		PIXI.Settings.RESOLUTION = (int) getPixelRatio();
		Options options = new Options();
		options.view = canvasElement;
		options.width = Window.getClientWidth();
		options.height = Window.getClientHeight();
		options.backgroundColor = 0xc9ecff;
		application = new Application(options);
		stage = application.stage;
		
		//Création de la scène
		scene = new Scene(stage);
		
		//Création de la caméra
		camera = new Camera(monde, Window.getClientWidth(), Window.getClientHeight());
		camera.applique(stage, scene.getConteneur());
		
		//Observateurs
		final OnEntiteAjoutee onEntiteAjoutee = new OnEntiteAjoutee();
		final OnEntiteSupprimee onEntiteSupprimee = new OnEntiteSupprimee();
		monde.onEntiteAjoutee(onEntiteAjoutee);
		monde.onEntiteSupprimee(onEntiteSupprimee);
		
		//Contenu de la vue
		rendusEntites = new HashMap<>();
		
		PIXI.Settings.MIPMAP_TEXTURES = false;
		PIXI.Settings.WRAP_MODE = PIXI.WRAP_MODES.REPEAT;
		Loader.add("images/zones/SableFond.png");
		Loader.add("images/zones/SableSol.png");
		
		Loader.load(new Loader.OnLoadCallback()
		{
			@Override
			public void onLoad()
			{
				PIXI.Settings.MIPMAP_TEXTURES = true;
				PIXI.Settings.WRAP_MODE = PIXI.WRAP_MODES.CLAMP;
				Loader.add("images/Creature.png");
				Loader.add("images/Nuisible.png");
				Loader.add("images/Granule.png");
				Loader.add("images/Fruit.png");
				Loader.add("images/Popo.png");
				
				Loader.add("images/ArbreFruitier.png");
				Loader.add("images/ArbreFruitier-avant.png");
				
				Loader.add("images/Distributeur.png");
				Loader.add("images/Litiere.png");
				Loader.add("images/Litiere-contenu.png");
				
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
		
		Window.addResizeHandler((ResizeEvent event) -> {
			redimentionne(event.getWidth(), event.getHeight());
		});
	}
	
	public final native double getPixelRatio()/*-{
		return window.devicePixelRatio;
	}-*/;
	
	public Canvas getCanvas()
	{
		return canvas;
	}
	
	public Camera getCamera()
	{
		return camera;
	}
	
	/**
	 * Redimentionne la vue
	 * 
	 * @param largeurFenetre la largeur de la fenêtre
	 * @param hauteurFenetre la hauteur de la fenêtre
	 */
	public void redimentionne(int largeurFenetre, int hauteurFenetre)
	{
		canvas.setCoordinateSpaceWidth(largeurFenetre);
		canvas.setCoordinateSpaceHeight(hauteurFenetre);
		application.renderer.resize(largeurFenetre, hauteurFenetre);
		camera.redimentionne(largeurFenetre, hauteurFenetre);
	}
	
	public void setZVue(int zVue)
	{
		if (zVue < 0)
			zVue = 0;
		
	}
	
	public void metAJour(long dateActuelle, double progressionTic)
	{
		for (RenduElement rendu : rendusEntites.values())
			rendu.metAJour(dateActuelle, progressionTic);
		camera.metAJour(dateActuelle, progressionTic);
		camera.applique(stage, scene.getConteneur());
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
			else if (entite.getType() == TypeEntite.LITIERE)
				rendu = new RenduLitere((EntiteLitiere) entite);
			else if (entite.getType() == TypeEntite.DISTRIBUTEUR_GRANULE)
				rendu = new RenduMachines((EntiteDistributeurGranule) entite);
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
