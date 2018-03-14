package net.feerie.creatura.client.ui;

import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;

import net.feerie.creatura.shared.creature.moodles.Moodle;
import net.feerie.creatura.shared.creature.moodles.TypeMoodle;
import net.feerie.creatura.shared.entites.Creature;

public class PanneauInfosDebug
{
	private Creature creature;
	private final DivElement panneau;
	
	public PanneauInfosDebug()
	{
		this.creature = null;
		panneau = DivElement.as(Document.get().getElementById("infosDebug"));
	}
	
	public void ouvre(Creature creature)
	{
		this.creature = creature;
		metAJourUI();
		panneau.addClassName("infosDebug--ouvert");
	}
	
	public void ferme()
	{
		panneau.removeClassName("infosDebug--ouvert");
	}
	
	public void metAJourUI()
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
		htmlInfos.append("<p><b>Cliquez sur le distributeur pour déposer de la nourriture. Soyez sympa !</b></p>");
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
		panneau.setInnerHTML(htmlInfos.toString());
	}
}
