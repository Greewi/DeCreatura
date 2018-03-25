package net.feerie.creatura.client.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;

public class PanneauVersion
{
	private final DivElement panneau;
	
	public PanneauVersion()
	{
		panneau = DivElement.as(Document.get().getElementById("infosVersion"));
		List<PatchNote> patchnotes = Arrays.asList(version0_9());
		
		StringBuilder html = new StringBuilder();
		for (PatchNote patchnote : patchnotes)
			patchnote.genereHTML(html);
		
		panneau.setInnerHTML(html.toString());
	}
	
	public void ouvre()
	{
		panneau.addClassName("infosVersion--ouvert");
	}
	
	public void ferme()
	{
		panneau.removeClassName("infosVersion--ouvert");
	}
	
	@SuppressWarnings("unused")
	private PatchNote version0_5()
	{
		PatchNote patchNote = new PatchNote("Patch note (v0.5) : ");
		patchNote.ajoute("Vous pouvez scroller la carte.");
		patchNote.ajoute("Plus d'outils : cliquez directement sur les éléments pour agir avec !");
		patchNote.ajoute("De petits nusibles viennent dévorer la nourriture s'il y en a trop au sol.");
		patchNote.ajoute("Le régime alimentaire de votre créature n'accepte plus les fruits (pour le moment). Mais vous avez un distributeur de granule (le truc vertical gris).");
		return patchNote;
	}
	
	@SuppressWarnings("unused")
	private PatchNote version0_6()
	{
		PatchNote patchNote = new PatchNote("Patch note (v0.6) : ");
		patchNote.ajoute("Nouvelle UI lorsqu'on clique sur la créature");
		patchNote.ajoute("Vous pouvez à nouveau sécher votre créature (ou la mouiller !)");
		return patchNote;
	}
	
	@SuppressWarnings("unused")
	private PatchNote version0_7()
	{
		PatchNote patchNote = new PatchNote("Patch note (v0.7) : ");
		patchNote.ajoute("Nouvelle IA (qui fait nawak XD)");
		patchNote.ajoute("Votre créature a désormais des croyances");
		patchNote.ajoute("La créature peut activer des choses");
		patchNote.ajoute("Vous pouvez maintenant gronder ou encourager votre créature");
		patchNote.ajoute("Les touches + et - accélèrent et ralentissent le temps");
		return patchNote;
	}
	
	@SuppressWarnings("unused")
	private PatchNote version0_7b()
	{
		PatchNote patchNote = new PatchNote("Patch note (v0.7b) : ");
		patchNote.ajoute("La créature tiens compte des actions Activer dans son apprentissage");
		patchNote.ajoute("La créature s'empoisonne si elle manger des choses pas bonnes pour elle");
		patchNote.ajoute("Quelques corrections d'interactions lors de la perte de focus de la fenêtre");
		patchNote.ajoute("La décrémentation des croyances est plus continue");
		return patchNote;
	}
	
	@SuppressWarnings("unused")
	private PatchNote version0_8()
	{
		PatchNote patchNote = new PatchNote("Patch note (v0.8 - Avec PIXI.js) : ");
		patchNote.ajoute("Nouveau moteur de rendu à base de PIXI.js à travers JSInterop");
		patchNote.ajoute("Correction d'un bug qui permettait a une créature de manger ce qu'une autre créature a déjà mangé");
		patchNote.ajoute("Réduction de la violence de l'empoisonnement");
		return patchNote;
	}
	
	@SuppressWarnings("unused")
	private PatchNote version0_8b()
	{
		PatchNote patchNote = new PatchNote("Patch note (v0.8b) : ");
		patchNote.ajoute("Correction dimensions d'affichage");
		patchNote.ajoute("Prise en compte du redimentionnement de la fenêtre");
		return patchNote;
	}
	
	@SuppressWarnings("unused")
	private PatchNote version0_8c()
	{
		PatchNote patchNote = new PatchNote("Patch note (v0.8c) : ");
		patchNote.ajoute("Correction position éléments");
		return patchNote;
	}
	
	private PatchNote version0_9()
	{
		PatchNote patchNote = new PatchNote("Patch note (v0.9) : ");
		patchNote.ajoute("Prise en charge des écrans tactiles");
		patchNote.ajoute("Focus de la créature");
		patchNote.ajoute("Gronder et encourager la créature est plus efficace");
		return patchNote;
	}
	
	private class PatchNote
	{
		private String titre;
		private List<String> features;
		
		public PatchNote(String titre)
		{
			this.titre = titre;
			features = new ArrayList<>();
		}
		
		public void ajoute(String feature)
		{
			features.add(feature);
		}
		
		public void genereHTML(StringBuilder htmlInfos)
		{
			htmlInfos.append("<p>").append(titre).append("</p>");
			htmlInfos.append("<ul>");
			for (String feature : features)
				htmlInfos.append("<li>").append(feature).append("</li>");
			htmlInfos.append("</ul>");
		}
	}
}
