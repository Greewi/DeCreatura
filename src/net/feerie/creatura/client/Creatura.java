package net.feerie.creatura.client;

import java.util.ArrayList;

import com.google.gwt.animation.client.AnimationScheduler;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;

import net.feerie.creatura.client.rendu2d.RenduMonde;
import net.feerie.creatura.client.ui.PanneauInfosCreatureMentales;
import net.feerie.creatura.client.ui.PanneauInfosCreaturePhysique;
import net.feerie.creatura.client.ui.PanneauInfosDebug;
import net.feerie.creatura.client.ui.PanneauToolbarCreature;
import net.feerie.creatura.client.ui.PanneauVersion;
import net.feerie.creatura.shared.Console;
import net.feerie.creatura.shared.Constantes;
import net.feerie.creatura.shared.Metronome;
import net.feerie.creatura.shared.commons.Dimension;
import net.feerie.creatura.shared.commons.Position;
import net.feerie.creatura.shared.entites.Creature;
import net.feerie.creatura.shared.entites.Entite;
import net.feerie.creatura.shared.entites.EntiteArbre;
import net.feerie.creatura.shared.entites.EntiteDistributeurGranule;
import net.feerie.creatura.shared.entites.EntiteEau;
import net.feerie.creatura.shared.entites.EntiteLitiere;
import net.feerie.creatura.shared.entites.TypeEntite;
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
	private int xVue;
	//UI
	private PanneauInfosDebug panneauInfosDebug;
	private PanneauVersion panneauVersion;
	private PanneauInfosCreaturePhysique panneauInfosCreaturePhysique;
	private PanneauInfosCreatureMentales panneauInfosCreatureMentales;
	private PanneauToolbarCreature panneauToolbarCreature;
	
	public void onModuleLoad()
	{
		largeurVue = Window.getClientWidth();//2000;
		hauteurVue = Window.getClientHeight();//1000;
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
		panneauInfosDebug = new PanneauInfosDebug();
		panneauVersion = new PanneauVersion();
		panneauInfosCreaturePhysique = new PanneauInfosCreaturePhysique();
		panneauInfosCreatureMentales = new PanneauInfosCreatureMentales();
		panneauToolbarCreature = new PanneauToolbarCreature();
		
		//Construction du monde
		Environnement eChaud = new Environnement(31, 1, "#FCFFA6");
		Environnement eTempe = new Environnement(22, 1, "#A6FFAD");
		Environnement eFroid = new Environnement(7, 1, "#A6FFEF");
		
		int longeurOceanGauche = 800;
		
		ArrayList<Zone> zones = new ArrayList<>();
		zones.add(new Zone(longeurOceanGauche, 0, 0, 150, eChaud));
		zones.add(new Zone(200, 0, 100, 150, eChaud));
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
		zones.add(new Zone(200, 240, 220, 0, eFroid));
		zones.add(new Zone(200, 220, 180, 0, eFroid));
		zones.add(new Zone(100, 180, 60, 150, eFroid));
		zones.add(new Zone(100, 60, 0, 150, eFroid));
		zones.add(new Zone(longeurOceanGauche, 0, 0, 150, eFroid));
		
		Carte carte = new Carte(zones.toArray(new Zone[1]));
		this.monde = new Monde(carte);
		this.metronome = new Metronome(monde);
		this.vueMonde = new RenduMonde(monde, contexte);
		
		//Ajout d'une litierre
		monde.nouvelleEntite(new EntiteLitiere(monde, 10, new Position(850 + longeurOceanGauche, 150)));
		
		//Ajout d'un distributeur de granulés
		monde.nouvelleEntite(new EntiteDistributeurGranule(monde, new Position(950 + longeurOceanGauche, 150), new Dimension(50, 150)));
		
		//Ajout d'un point d'eau
		monde.nouvelleEntite(new EntiteEau(monde, new Position(300 + longeurOceanGauche, 00), new Dimension(200, 150)));
		
		//Ajout des arbres
		monde.nouvelleEntite(new EntiteArbre(monde, new Position(600 + longeurOceanGauche, 170), new Dimension(100, 200)));
		monde.nouvelleEntite(new EntiteArbre(monde, new Position(1300 + longeurOceanGauche, 170), new Dimension(120, 260)));
		monde.nouvelleEntite(new EntiteArbre(monde, new Position(2100 + longeurOceanGauche, 240), new Dimension(80, 160)));
		
		//Ajout d'une créature
		this.creature = new Creature(monde, new Position(400 + longeurOceanGauche, 170));
		monde.nouvelleEntite(creature);
		
		//Placement de la camera
		xVue = longeurOceanGauche;
		
		//Boucle de rendu
		BoucleRendu boucleRendu = new BoucleRendu();
		AnimationScheduler.get().requestAnimationFrame(boucleRendu);
		
		initialiseEvenement();
		ouvreInterfaceGenerale();
	}
	
	private int xVueInitial;
	private int xSourisInitial;
	private int ySourisInitial;
	private boolean clicEnCours;
	private boolean scrollEnCours;
	
	private final void initialiseEvenement()
	{
		clicEnCours = false;
		scrollEnCours = false;
		canvas.addMouseDownHandler(new MouseDownHandler()
		{
			@Override
			public void onMouseDown(MouseDownEvent event)
			{
				xSourisInitial = event.getRelativeX(event.getRelativeElement());
				ySourisInitial = event.getRelativeY(event.getRelativeElement());
				clicEnCours = true;
				xVueInitial = xVue;
			}
		});
		
		canvas.addMouseMoveHandler(new MouseMoveHandler()
		{
			@Override
			public void onMouseMove(MouseMoveEvent event)
			{
				if (clicEnCours)
				{
					int x = event.getRelativeX(event.getRelativeElement());
					int y = event.getRelativeY(event.getRelativeElement());
					if ((x - xSourisInitial) * (x - xSourisInitial) + (y - ySourisInitial) * (y - ySourisInitial) > 20 * 20) //20 pixels de tolérance au scroll
						scrollEnCours = true;
					xVue = xVueInitial - ((x - xSourisInitial) * largeurVue) / largeurFenetre;
					if (xVue < 0)
						xVue = 0;
					if (xVue > monde.getCarte().getLongueurTotale() - largeurVue)
						xVue = monde.getCarte().getLongueurTotale() - largeurVue;
				}
			}
		});
		
		canvas.addMouseUpHandler(new MouseUpHandler()
		{
			@Override
			public void onMouseUp(MouseUpEvent event)
			{
				clicEnCours = false;
				if (scrollEnCours)
				{
					scrollEnCours = false;
				}
				else
				{
					int x = xVue + (event.getRelativeX(event.getRelativeElement()) * largeurVue) / largeurFenetre;
					int y = hauteurVue - (event.getRelativeY(event.getRelativeElement()) * hauteurVue) / hauteurFenetre;
					
					Entite entiteSelectionnee = null;
					for (Entite entite : monde.getEntiteAPosition(new Position(x, y)))
					{
						if (entiteSelectionnee == null || entite.getType().getPrioriteSelection() > entiteSelectionnee.getType().getPrioriteSelection())
							entiteSelectionnee = entite;
					}
					
					ouvreInterfaceGenerale();
					
					if (entiteSelectionnee != null)
					{
						String outilAOuvrir = entiteSelectionnee.active(true);
						Console.log("Outil à ouvrir : " + outilAOuvrir);
						if (outilAOuvrir != null && outilAOuvrir.equalsIgnoreCase("Creature") && entiteSelectionnee.getType() == TypeEntite.CREATURE)
						{
							ouvreInterfaceCreature((Creature) entiteSelectionnee);
						}
					}
				}
			}
		});
	}
	
	private void ouvreInterfaceGenerale()
	{
		panneauInfosCreaturePhysique.ferme();
		panneauInfosCreatureMentales.ferme();
		panneauToolbarCreature.ferme();
		panneauInfosDebug.ouvre(creature);
		panneauVersion.ouvre();
	}
	
	private void ouvreInterfaceCreature(Creature creature)
	{
		if (creature.estVivante())
		{
			panneauInfosCreaturePhysique.ouvre(creature);
			panneauInfosCreatureMentales.ouvre(creature);
			panneauToolbarCreature.ouvre(creature);
			panneauInfosDebug.ferme();
			panneauVersion.ferme();
		}
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
			contexte.translate(-xVue, -hauteurVue);
			
			//Dessin du monde
			double progressionTic = (dateActuelle - metronome.getDateDernierTic()) / (double) Constantes.PERIODE_TIC;
			if (progressionTic < 0)
				progressionTic = 0;
			if (progressionTic > 1)
				progressionTic = 1;
			vueMonde.dessine(dateActuelle, progressionTic);
			
			//Mise à jour de l'UI
			panneauInfosDebug.metAJourUI();
			
			//Appel de la frame suivante
			AnimationScheduler.get().requestAnimationFrame(this);
		}
	}
	
}
