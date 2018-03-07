package net.feerie.creatura.client;

import com.google.gwt.animation.client.AnimationScheduler;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.ButtonElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

import net.feerie.creatura.client.rendu2d.RenduMonde;
import net.feerie.creatura.shared.Console;
import net.feerie.creatura.shared.Environnement;
import net.feerie.creatura.shared.Monde;
import net.feerie.creatura.shared.commons.Dimension;
import net.feerie.creatura.shared.commons.Position;
import net.feerie.creatura.shared.creature.moodles.Moodle;
import net.feerie.creatura.shared.creature.moodles.TypeMoodle;
import net.feerie.creatura.shared.entites.Creature;
import net.feerie.creatura.shared.entites.Entite;
import net.feerie.creatura.shared.entites.EntiteEau;
import net.feerie.creatura.shared.entites.Litiere;
import net.feerie.creatura.shared.entites.Nourriture;
import net.feerie.creatura.shared.entites.TypeEntite;
import net.feerie.creatura.shared.entites.TypeNourriture;
import net.feerie.creatura.shared.entites.Zone;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Creatura implements EntryPoint
{
	//Modele
	private Monde monde;
	private Creature creature;
	//Vue
	private Canvas canvas;
	private Context2d contexte;
	private RenduMonde vueMonde;
	private int numeroFrame;
	private int taille;
	//UI
	private Outil outilActuel;
	private HTML uiInformations;
	private ButtonElement boutonPlacerNourriture;
	private ButtonElement boutonSecher;
	private ButtonElement boutonNettoyer;
	
	public void onModuleLoad()
	{
		//Initialisation du contexte
		this.canvas = Canvas.createIfSupported();
		taille = Window.getClientWidth() > Window.getClientHeight() ? Window.getClientHeight() : Window.getClientWidth();
		canvas.setCoordinateSpaceWidth(taille);
		canvas.setCoordinateSpaceHeight(taille);
		RootPanel.get("canvas").add(canvas);
		this.contexte = canvas.getContext2d();
		
		//Initialisation de l'UI
		uiInformations = new HTML();
		RootPanel.get("informations").add(uiInformations);
		boutonPlacerNourriture = (ButtonElement) Document.get().getElementById("boutonPlacerNourriture");
		boutonSecher = (ButtonElement) Document.get().getElementById("boutonSecher");
		boutonNettoyer = (ButtonElement) Document.get().getElementById("boutonNettoyer");
		
		//Construction du monde
		this.monde = new Monde(new Environnement(0, 0, "#000000"));
		this.vueMonde = new RenduMonde(monde, contexte);
		
		//Ajout des zones
		monde.ajouteZone(new Zone(monde, new Position(25, 50), new Dimension(50, 100), new Environnement(22, 1, "#A6FFAD")));
		monde.ajouteZone(new Zone(monde, new Position(75, 25), new Dimension(50, 50), new Environnement(31, 1, "#FCFFA6")));
		monde.ajouteZone(new Zone(monde, new Position(75, 75), new Dimension(50, 50), new Environnement(7, 1, "#A6FFEF")));
		
		//Ajout d'une litierre
		monde.nouvelleEntite(new Litiere(monde, 10, new Position(90, 10)));
		
		//Ajout d'un point d'eau
		monde.nouvelleEntite(new EntiteEau(monde, new Position(15, 85)));
		
		//Ajout de la nourriture
		for (int i = 0; i < 0; i++)
		{
			TypeNourriture[] types = TypeNourriture.values();
			TypeNourriture type = types[Random.nextInt(types.length)];
			monde.nouvelleEntite(new Nourriture(monde, type, new Position(Random.nextInt(100), Random.nextInt(100))));
		}
		
		//Ajout d'une créature
		this.creature = new Creature(monde, new Position(25, 50));
		monde.nouvelleEntite(creature);
		
		//Boucle de rendu
		numeroFrame = 0;
		BoucleRendu boucleRendu = new BoucleRendu();
		AnimationScheduler.get().requestAnimationFrame(boucleRendu);
		
		initialiseEvenement();
	}
	
	private final void initialiseEvenement()
	{
		outilActuel = new OutilPlacerNourriture();
		boutonPlacerNourriture.addClassName("actif");
		canvas.addClickHandler(new ClickHandler()
		{
			@Override
			public void onClick(ClickEvent event)
			{
				if (outilActuel != null)
					outilActuel.onClick(event);
			}
		});
		
		Button.wrap(boutonPlacerNourriture).addClickHandler(new ClickHandler()
		{
			@Override
			public void onClick(ClickEvent event)
			{
				Console.log("Click sur boutonPlacerNourriture");
				boutonPlacerNourriture.addClassName("actif");
				boutonSecher.removeClassName("actif");
				boutonNettoyer.removeClassName("actif");
				outilActuel = new OutilPlacerNourriture();
			}
		});
		
		Button.wrap(boutonSecher).addClickHandler(new ClickHandler()
		{
			@Override
			public void onClick(ClickEvent event)
			{
				Console.log("Click sur boutonSecher");
				boutonPlacerNourriture.removeClassName("actif");
				boutonSecher.addClassName("actif");
				boutonNettoyer.removeClassName("actif");
				outilActuel = new OutilSecher();
			}
		});
		
		Button.wrap(boutonNettoyer).addClickHandler(new ClickHandler()
		{
			@Override
			public void onClick(ClickEvent event)
			{
				Console.log("Click sur boutonNettoyer");
				boutonPlacerNourriture.removeClassName("actif");
				boutonSecher.removeClassName("actif");
				boutonNettoyer.addClassName("actif");
				outilActuel = new OutilNettoyer();
			}
		});
	}
	
	private class BoucleRendu implements AnimationScheduler.AnimationCallback
	{
		@Override
		public void execute(double timestamp)
		{
			//Mise à jour du monde
			monde.metAJour(numeroFrame++);
			
			//Affichage du canvas
			canvas.setCoordinateSpaceWidth(canvas.getCoordinateSpaceWidth());
			//Mise à l'échelle
			if (canvas.getCoordinateSpaceWidth() < canvas.getCoordinateSpaceHeight())
				contexte.scale(canvas.getCoordinateSpaceWidth() / 100.0, canvas.getCoordinateSpaceWidth() / 100.0);
			else
				contexte.scale(canvas.getCoordinateSpaceHeight() / 100.0, canvas.getCoordinateSpaceHeight() / 100.0);
			
			//Dessin du monde
			vueMonde.dessine(timestamp);
			
			//Mise à jour de l'UI
			metAJourUI();
			
			//Appel de la frame suivante
			AnimationScheduler.get().requestAnimationFrame(this);
		}
	}
	
	private void metAJourUI()
	{
		//Action et besoins
		StringBuilder htmlInfos = new StringBuilder();
		if (creature.getActionActuelle() != null)
			htmlInfos.append("<p>Action : ").append(creature.getActionActuelle().getType().getNom()).append("</p>");
		else
			htmlInfos.append("<p>Action : aucune</p>");
		htmlInfos.append("<hr/>");
		htmlInfos.append("<p>Sante : ").append(creature.getSante()).append("</p>");
		htmlInfos.append("<hr/>");
		htmlInfos.append("<p><b>Cliquez sur la carte pour déposer de la nourriture. Soyez sympa !</b></p>");
		htmlInfos.append("<hr>");
		htmlInfos.append("<p>Moodles : </p>");
		htmlInfos.append("<ul>");
		for (TypeMoodle typeMoodle : TypeMoodle.values())
		{
			Moodle moodle = creature.getMoodle(typeMoodle);
			int charge = moodle.getCharge();
			if (moodle.estActif())
				htmlInfos.append("<li><b>").append(typeMoodle.getNom()).append(" : ").append(charge).append("%</li></b>");
			else
				htmlInfos.append("<li>").append(typeMoodle.getNom()).append(" : ").append(charge).append("%</li>");
		}
		htmlInfos.append("</ul>");
		
		//On affiche les informations
		uiInformations.setHTML(htmlInfos.toString());
	}
	
	private interface Outil
	{
		public void onClick(ClickEvent event);
	}
	
	private class OutilPlacerNourriture implements Outil
	{
		@Override
		public void onClick(ClickEvent event)
		{
			double x = event.getRelativeX(event.getRelativeElement()) * 100.0 / taille;
			double y = event.getRelativeY(event.getRelativeElement()) * 100.0 / taille;
			TypeNourriture[] types = TypeNourriture.values();
			TypeNourriture type = types[Random.nextInt(types.length)];
			monde.nouvelleEntite(new Nourriture(monde, type, new Position(x, y)));
		}
	}
	
	private class OutilSecher implements Outil
	{
		@Override
		public void onClick(ClickEvent event)
		{
			double x = event.getRelativeX(event.getRelativeElement()) * 100.0 / taille;
			double y = event.getRelativeY(event.getRelativeElement()) * 100.0 / taille;
			double xc = creature.getPosition().x;
			double yc = creature.getPosition().y;
			double rayon = creature.getTaille().l / 2;
			
			if ((x - xc) * (x - xc) + (y - yc) * (y - yc) <= rayon * rayon)
			{
				creature.getMoodle(TypeMoodle.MOUILLE).desactive();
			}
		}
	}
	
	private class OutilNettoyer implements Outil
	{
		@Override
		public void onClick(ClickEvent event)
		{
			double x = event.getRelativeX(event.getRelativeElement()) * 100.0 / taille;
			double y = event.getRelativeY(event.getRelativeElement()) * 100.0 / taille;
			
			for (Entite entite : monde.getEntiteAPosition(new Position(x, y)))
			{
				if (entite.getType() == TypeEntite.NOURRITURE || entite.getType() == TypeEntite.POPO)
					entite.detruit();
				else if (entite.getType() == TypeEntite.LITIERE)
					((Litiere) entite).vide();
			}
		}
	}
}
