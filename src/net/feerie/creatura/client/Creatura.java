package net.feerie.creatura.client;

import java.util.EnumMap;

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
import net.feerie.creatura.shared.organisme.Besoin;
import net.feerie.creatura.shared.organisme.TypeBesoin;
import net.feerie.creatura.shared.organisme.TypeVariableVitale;
import net.feerie.creatura.shared.organisme.VariableVitale;

import com.google.gwt.animation.client.AnimationScheduler;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

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
	private EnumMap<TypeVariableVitale, Double> ancienneVariables;
	
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
		ancienneVariables = new EnumMap<>(TypeVariableVitale.class);
		for (TypeVariableVitale type : TypeVariableVitale.values())
			ancienneVariables.put(type, 0.0);
		
		//Construction du monde
		this.monde = new Monde(new Environnement(0, 0, "#000000"));
		this.vueMonde = new RenduMonde(monde, contexte);
		
		//Ajout des zones
		monde.ajouteZone(new Zone(monde, new Position(25, 50), new Dimension(50, 100), new Environnement(28, 1, "#A6FFAD")));
		monde.ajouteZone(new Zone(monde, new Position(75, 25), new Dimension(50, 50), new Environnement(37, 1, "#FCFFA6")));
		monde.ajouteZone(new Zone(monde, new Position(75, 75), new Dimension(50, 50), new Environnement(12, 1, "#A6FFEF")));
		
		//Ajout d'une litierre
		monde.ajouteEntite(new Litiere(monde, 3000, new Position(75, 25)));
		
		//Ajout d'une créature
		this.creature = new Creature(monde, new Position(50, 50));
		monde.ajouteEntite(creature);
		
		//Ajout de la nourriture
		for (int i = 0; i < 100; i++)
		{
			TypeNourriture[] types = TypeNourriture.values();
			TypeNourriture type = types[Random.nextInt(types.length)];
			monde.ajouteEntite(new Nourriture(monde, type, new Position(Random.nextInt(100), Random.nextInt(100))));
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
		htmlInfos.append("<hr/>");
		for (TypeBesoin type : TypeBesoin.values())
		{
			Besoin besoin = creature.getBesoin(type);
			htmlInfos.append("<li>").append(type).append(" : ").append(Math.round(besoin.get() * 100)).append("%</li>");
		}
		htmlInfos.append("</ul>");
		//Variables
		StringBuilder htmlVariables = new StringBuilder();
		htmlVariables.append("<ul>");
		htmlVariables.append("<li>Variables</li>");
		htmlVariables.append("<hr/>");
		for (TypeVariableVitale type : TypeVariableVitale.values())
		{
			VariableVitale variable = creature.getVariableVitale(type);
			double delta = Math.round((variable.get() - ancienneVariables.get(type)) * 100) / 100.0;
			ancienneVariables.put(type, variable.get());
			htmlVariables.append("<li>").append(type).append(" : ").append(Math.round(variable.get())).append("/").append(variable.getIdeal()).append(delta >= 0 ? "+" : "").append(delta).append("</li>");
		}
		htmlVariables.append("</ul>");
		
		//On affiche les informations
		uiInformations.setHTML(htmlInfos.toString());
		uiVariables.setHTML(htmlVariables.toString());
	}
}
