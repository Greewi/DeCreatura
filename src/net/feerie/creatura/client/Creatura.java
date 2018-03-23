package net.feerie.creatura.client;

import java.util.ArrayList;

import com.google.gwt.animation.client.AnimationScheduler;
import com.google.gwt.core.client.EntryPoint;

import net.feerie.creatura.client.pixi.Utils;
import net.feerie.creatura.client.ui.PanneauInfosCreatureMentales;
import net.feerie.creatura.client.ui.PanneauInfosCreaturePhysique;
import net.feerie.creatura.client.ui.PanneauInfosDebug;
import net.feerie.creatura.client.ui.PanneauToolbarCreature;
import net.feerie.creatura.client.ui.PanneauVersion;
import net.feerie.creatura.shared.Constantes;
import net.feerie.creatura.shared.Metronome;
import net.feerie.creatura.shared.commons.Dimension;
import net.feerie.creatura.shared.commons.Position;
import net.feerie.creatura.shared.entites.Creature;
import net.feerie.creatura.shared.entites.EntiteArbre;
import net.feerie.creatura.shared.entites.EntiteDistributeurGranule;
import net.feerie.creatura.shared.entites.EntiteEau;
import net.feerie.creatura.shared.entites.EntiteLitiere;
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
	private Vue vue;
	@SuppressWarnings("unused")
	private ControleurVue controleurVue;
	//UI
	private PanneauInfosDebug panneauInfosDebug;
	private PanneauVersion panneauVersion;
	private PanneauInfosCreaturePhysique panneauInfosCreaturePhysique;
	private PanneauInfosCreatureMentales panneauInfosCreatureMentales;
	private PanneauToolbarCreature panneauToolbarCreature;
	
	public void onModuleLoad()
	{
		Utils.sayHello();
		
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
		
		// Interface et vue
		//Initialisation de l'UI
		panneauInfosDebug = new PanneauInfosDebug();
		panneauVersion = new PanneauVersion();
		panneauInfosCreaturePhysique = new PanneauInfosCreaturePhysique();
		panneauInfosCreatureMentales = new PanneauInfosCreatureMentales();
		panneauToolbarCreature = new PanneauToolbarCreature();
		vue = new Vue(monde);
		controleurVue = new ControleurVue(this);
		ouvreInterfaceGenerale();
		//Placement de la camera
		vue.setXVue(longeurOceanGauche);
		
		//Boucle de rendu
		BoucleRendu boucleRendu = new BoucleRendu();
		AnimationScheduler.get().requestAnimationFrame(boucleRendu);
	}
	
	public Vue getVue()
	{
		return vue;
	}
	
	public Monde getMonde()
	{
		return monde;
	}
	
	public void ouvreInterfaceGenerale()
	{
		panneauInfosCreaturePhysique.ferme();
		panneauInfosCreatureMentales.ferme();
		panneauToolbarCreature.ferme();
		panneauInfosDebug.ouvre(creature);
		panneauVersion.ouvre();
	}
	
	public void ouvreInterfaceCreature(Creature creature)
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
			//Mise à jour du monde
			long dateActuelle = System.currentTimeMillis();
			metronome.nouvelleFrame(dateActuelle);
			double progressionTic = (dateActuelle - metronome.getDateDernierTic()) / (double) Constantes.PERIODE_TIC;
			if (progressionTic < 0)
				progressionTic = 0;
			if (progressionTic > 1)
				progressionTic = 1;
			
			//Mise à jour de l'UI
			panneauInfosDebug.metAJourUI();
			
			//Dessin du monde
			vue.metAJour(dateActuelle, progressionTic);
			
			//Appel de la frame suivante
			AnimationScheduler.get().requestAnimationFrame(this);
		}
	}
	
}
