package net.feerie.creatura.client.renduPixi;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.dom.client.CanvasElement;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;

import net.feerie.creatura.client.pixi.Application;
import net.feerie.creatura.client.pixi.Loader;
import net.feerie.creatura.client.pixi.Sprite;
import net.feerie.creatura.shared.monde.Monde;

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
	
	public Vue(Monde monde)
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
		Application.Config config = Application.Config.newConfig();
		config.setView(canvasElement);
		config.setWidth(largeurFenetre);
		config.setHeight(hauteurFenetre);
		config.setBackgroundColor(0xFF0000);
		application = Application.newApplication(config);
		
		Loader.add("images/cat.png");
		Loader.load(new Loader.OnLoadCallback()
		{
			@Override
			public void onLoad()
			{
				Sprite sprite = Sprite.newSprite("images/cat.png");
				application.getStage().addChild(sprite);
			}
		});
	}
	
	public void setXVue(int xvue)
	{
		this.xVue = xvue;
	}
	
	public void dessine()
	{
		//application.render();
	}
}
