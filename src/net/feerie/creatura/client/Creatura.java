package net.feerie.creatura.client;

import java.util.ArrayList;

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
import net.feerie.creatura.shared.Constantes;
import net.feerie.creatura.shared.Metronome;
import net.feerie.creatura.shared.commons.Dimension;
import net.feerie.creatura.shared.commons.Position;
import net.feerie.creatura.shared.creature.moodles.Moodle;
import net.feerie.creatura.shared.creature.moodles.TypeMoodle;
import net.feerie.creatura.shared.entites.Creature;
import net.feerie.creatura.shared.entites.Entite;
import net.feerie.creatura.shared.entites.EntiteArbre;
import net.feerie.creatura.shared.entites.EntiteEau;
import net.feerie.creatura.shared.entites.EntiteLitiere;
import net.feerie.creatura.shared.entites.EntiteNourriture;
import net.feerie.creatura.shared.entites.TypeEntite;
import net.feerie.creatura.shared.entites.TypeNourriture;
import net.feerie.creatura.shared.monde.Carte;
import net.feerie.creatura.shared.monde.Environnement;
import net.feerie.creatura.shared.monde.Monde;
import net.feerie.creatura.shared.monde.Zone;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Creatura implements EntryPoint
{
	//Modele
	private Monde monde;
	private Metronome metronome;
	private Creature creature;
	//Vue
	private Canvas canvas;
	private Context2d contexte;
	private RenduMonde vueMonde;
	private int largeurFenetre;
	private int hauteurFenetre;
	private int largeurVue;
	private int hauteurVue;
	//UI
	private Outil outilActuel;
	private HTML uiInformations;
	private ButtonElement boutonPlacerNourriture;
	private ButtonElement boutonSecher;
	private ButtonElement boutonNettoyer;
	private ButtonElement boutonSecouer;
	
	public void onModuleLoad()
	{
		largeurVue = 2000;
		hauteurVue = 1000;
		largeurFenetre = Window.getClientWidth();
		hauteurFenetre = Window.getClientHeight();
		if (largeurFenetre / (double) largeurVue > hauteurFenetre / (double) hauteurVue)
			largeurFenetre = (int) (largeurVue * hauteurFenetre / (double) hauteurVue);
		else
			hauteurFenetre = (int) (hauteurVue * largeurFenetre / (double) largeurVue);
		
		//Initialisation du contexte
		this.canvas = Canvas.createIfSupported();
		canvas.setCoordinateSpaceWidth(largeurFenetre);
		canvas.setCoordinateSpaceHeight(hauteurFenetre);
		RootPanel.get("canvas").add(canvas);
		this.contexte = canvas.getContext2d();
		
		//Initialisation de l'UI
		uiInformations = new HTML();
		RootPanel.get("informations").add(uiInformations);
		boutonPlacerNourriture = (ButtonElement) Document.get().getElementById("boutonPlacerNourriture");
		boutonSecher = (ButtonElement) Document.get().getElementById("boutonSecher");
		boutonNettoyer = (ButtonElement) Document.get().getElementById("boutonNettoyer");
		boutonSecouer = (ButtonElement) Document.get().getElementById("boutonSecouer");
		
		//Construction du monde
		Environnement eChaud = new Environnement(31, 1, "#FCFFA6");
		Environnement eTempe = new Environnement(22, 1, "#A6FFAD");
		Environnement eFroid = new Environnement(7, 1, "#A6FFEF");
		ArrayList<Zone> zones = new ArrayList<>();
		
		zones.add(new Zone(200, 100, 150, 150, eChaud));
		zones.add(new Zone(100, 150, 170, 0, eChaud));
		zones.add(new Zone(200, 170, 170, 0, eTempe));
		zones.add(new Zone(100, 170, 150, 0, eTempe));
		zones.add(new Zone(200, 150, 150, 0, eTempe));
		zones.add(new Zone(200, 150, 170, 0, eTempe));
		zones.add(new Zone(200, 170, 170, 0, eTempe));
		zones.add(new Zone(200, 170, 180, 0, eTempe));
		zones.add(new Zone(200, 180, 220, 0, eFroid));
		zones.add(new Zone(200, 220, 240, 0, eFroid));
		zones.add(new Zone(200, 240, 240, 0, eFroid));
		
		Carte carte = new Carte(zones.toArray(new Zone[1]));
		this.monde = new Monde(carte);
		this.metronome = new Metronome(monde);
		this.vueMonde = new RenduMonde(monde, contexte);
		
		//Ajout d'une litierre
		monde.nouvelleEntite(new EntiteLitiere(monde, 10, new Position(700, 150)));
		
		//Ajout d'un point d'eau
		monde.nouvelleEntite(new EntiteEau(monde, new Position(100, 00), new Dimension(200, 150)));
		
		//Ajout des arbres
		monde.nouvelleEntite(new EntiteArbre(monde, new Position(400, 170), new Dimension(100, 200)));
		monde.nouvelleEntite(new EntiteArbre(monde, new Position(1100, 170), new Dimension(120, 260)));
		monde.nouvelleEntite(new EntiteArbre(monde, new Position(1900, 240), new Dimension(80, 160)));
		
		//Ajout d'une créature
		this.creature = new Creature(monde, new Position(400, 170));
		monde.nouvelleEntite(creature);
		
		//Boucle de rendu
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
				boutonSecouer.removeClassName("actif");
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
				boutonSecouer.removeClassName("actif");
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
				boutonSecouer.removeClassName("actif");
				outilActuel = new OutilNettoyer();
			}
		});
		
		Button.wrap(boutonSecouer).addClickHandler(new ClickHandler()
		{
			@Override
			public void onClick(ClickEvent event)
			{
				Console.log("Click sur boutonSecouer");
				boutonPlacerNourriture.removeClassName("actif");
				boutonSecher.removeClassName("actif");
				boutonNettoyer.removeClassName("actif");
				boutonSecouer.addClassName("actif");
				outilActuel = new OutilSecouer();
			}
		});
	}
	
	private class BoucleRendu implements AnimationScheduler.AnimationCallback
	{
		@Override
		public void execute(double timestamp)
		{
			long dateActuelle = System.currentTimeMillis();
			
			//Mise à jour du monde
			metronome.nouvelleFrame();
			
			//Affichage du canvas
			canvas.setCoordinateSpaceWidth(canvas.getCoordinateSpaceWidth());
			//Mise à l'échelle
			contexte.scale(largeurFenetre / (double) largeurVue, -hauteurFenetre / (double) hauteurVue);
			contexte.translate(0, -hauteurVue);
			
			//Dessin du monde
			double progressionTic = (dateActuelle - metronome.getDateDernierTic()) / (double) Constantes.PERIODE_TIC;
			if (progressionTic < 0)
				progressionTic = 0;
			if (progressionTic > 1)
				progressionTic = 1;
			vueMonde.dessine(dateActuelle, progressionTic);
			
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
			int x = (event.getRelativeX(event.getRelativeElement()) * largeurVue) / largeurFenetre;
			int y = hauteurVue - (event.getRelativeY(event.getRelativeElement()) * hauteurVue) / hauteurFenetre;
			TypeNourriture[] types = TypeNourriture.values();
			TypeNourriture type = types[Random.nextInt(types.length)];
			monde.nouvelleEntite(new EntiteNourriture(monde, type, new Position(x, y)));
		}
	}
	
	private class OutilSecher implements Outil
	{
		@Override
		public void onClick(ClickEvent event)
		{
			double x = (event.getRelativeX(event.getRelativeElement()) * largeurVue) / largeurFenetre;
			double y = hauteurVue - (event.getRelativeY(event.getRelativeElement()) * hauteurVue) / hauteurFenetre;
			double xc = creature.position.x;
			double yc = creature.position.z;
			double l = creature.getTaille().l;
			double h = creature.getTaille().h;
			
			if (x >= xc - l / 2 && x <= xc + l / 2 && y >= yc && y <= yc + h)
				creature.getMoodle(TypeMoodle.MOUILLE).desactive();
		}
	}
	
	private class OutilNettoyer implements Outil
	{
		@Override
		public void onClick(ClickEvent event)
		{
			int x = (event.getRelativeX(event.getRelativeElement()) * largeurVue) / largeurFenetre;
			int y = hauteurVue - (event.getRelativeY(event.getRelativeElement()) * hauteurVue) / hauteurFenetre;
			
			for (Entite entite : monde.getEntiteAPosition(new Position(x, y)))
			{
				if (entite.getType() == TypeEntite.NOURRITURE || entite.getType() == TypeEntite.POPO)
					entite.detruit();
				else if (entite.getType() == TypeEntite.LITIERE)
					((EntiteLitiere) entite).vide();
			}
		}
	}
	
	private class OutilSecouer implements Outil
	{
		@Override
		public void onClick(ClickEvent event)
		{
			int x = (event.getRelativeX(event.getRelativeElement()) * largeurVue) / largeurFenetre;
			int y = hauteurVue - (event.getRelativeY(event.getRelativeElement()) * hauteurVue) / hauteurFenetre;
			
			for (Entite entite : monde.getEntiteAPosition(new Position(x, y)))
			{
				if (entite.getType() == TypeEntite.ARBRE)
					((EntiteArbre) entite).secoue();
			}
		}
	}
}
