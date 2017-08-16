package net.feerie.creatura.client;

import com.google.gwt.animation.client.AnimationScheduler;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

import net.feerie.creatura.client.rendu2d.RenduMonde;
import net.feerie.creatura.shared.Environnement;
import net.feerie.creatura.shared.Monde;
import net.feerie.creatura.shared.commons.Dimension;
import net.feerie.creatura.shared.commons.Position;
import net.feerie.creatura.shared.entites.Creature;
import net.feerie.creatura.shared.entites.Litiere;
import net.feerie.creatura.shared.entites.Nourriture;
import net.feerie.creatura.shared.entites.TypeNourriture;
import net.feerie.creatura.shared.entites.Zone;
import net.feerie.creatura.shared.organisme.Substance;
import net.feerie.creatura.shared.organisme.organes.Organe;
import net.feerie.creatura.shared.organisme.organes.TypeOrgane;
import net.feerie.creatura.shared.organisme.sens.SystemeSensoriel;
import net.feerie.creatura.shared.organisme.sens.TypeCanalSensoriel;

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
	//UI
	private HTML uiInformations;
	private HTML uiVariables;
	
	public void onModuleLoad()
	{
		//Initialisation du contexte
		this.canvas = Canvas.createIfSupported();
		canvas.setCoordinateSpaceWidth(Window.getClientWidth());
		canvas.setCoordinateSpaceHeight(Window.getClientHeight());
		RootPanel.get("canvas").add(canvas);
		this.contexte = canvas.getContext2d();
		
		//Initialisation de l'UI
		uiInformations = new HTML();
		RootPanel.get("informations").add(uiInformations);
		uiVariables = new HTML();
		RootPanel.get("variables").add(uiVariables);
		
		//Construction du monde
		this.monde = new Monde(new Environnement(0, 0, "#000000"));
		this.vueMonde = new RenduMonde(monde, contexte);
		
		//Ajout des zones
		monde.ajouteZone(new Zone(monde, new Position(25, 50), new Dimension(50, 100), new Environnement(28, 1, "#A6FFAD")));
		monde.ajouteZone(new Zone(monde, new Position(75, 25), new Dimension(50, 50), new Environnement(37, 1, "#FCFFA6")));
		monde.ajouteZone(new Zone(monde, new Position(75, 75), new Dimension(50, 50), new Environnement(12, 1, "#A6FFEF")));
		
		//Ajout d'une litierre
		monde.nouvelleEntite(new Litiere(monde, 3000, new Position(75, 25)));
		
		//Ajout d'une créature
		this.creature = new Creature(monde, new Position(50, 50));
		monde.nouvelleEntite(creature);
		
		//Ajout de la nourriture
		for (int i = 0; i < 100; i++)
		{
			TypeNourriture[] types = TypeNourriture.values();
			TypeNourriture type = types[Random.nextInt(types.length)];
			monde.nouvelleEntite(new Nourriture(monde, type, new Position(Random.nextInt(100), Random.nextInt(100))));
		}
		
		//Boucle de rendu
		numeroFrame = 0;
		BoucleRendu boucleRendu = new BoucleRendu();
		AnimationScheduler.get().requestAnimationFrame(boucleRendu);
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
				contexte.scale(canvas.getCoordinateSpaceWidth() / 100, canvas.getCoordinateSpaceWidth() / 100);
			else
				contexte.scale(canvas.getCoordinateSpaceHeight() / 100, canvas.getCoordinateSpaceHeight() / 100);
			
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
		htmlInfos.append("<ul>");
		if (creature.getActionActuelle() != null)
			htmlInfos.append("<li>Action : ").append(creature.getActionActuelle().getType()).append("</li>");
		else
			htmlInfos.append("<li>Action : aucune</li>");
		htmlInfos.append("</ul>");
		htmlInfos.append("<hr/>");
		htmlInfos.append("<ul>");
		SystemeSensoriel systemeSensoriel = (SystemeSensoriel) creature.getOrganisme().getOrgane(TypeOrgane.SYSTEME_SENSORIEL);
		for (TypeCanalSensoriel canal : TypeCanalSensoriel.values())
		{
			int valeur = systemeSensoriel.getValeurCanal(canal);
			htmlInfos.append("<li>").append(canal).append(" : ").append(valeur).append("%</li>");
		}
		htmlInfos.append("</ul>");
		//Variables
		StringBuilder htmlVariables = new StringBuilder();
		for (TypeOrgane typeOrgane : TypeOrgane.values())
		{
			Organe organe = creature.getOrganisme().getOrgane(typeOrgane);
			htmlVariables.append(organe.getType());
			htmlVariables.append("<br/>");
			htmlVariables.append("<ul>");
			for(Substance substance : Substance.values())
			{
				int quantite = organe.getSubstance(substance);
				if(quantite>0)
				htmlVariables.append("<li>").append(substance).append(" : ").append(quantite).append("</li>");
			}
			htmlVariables.append("</ul>");
			htmlVariables.append("<hr/>");
		}
		
		//On affiche les informations
		uiInformations.setHTML(htmlInfos.toString());
		uiVariables.setHTML(htmlVariables.toString());
	}
}
