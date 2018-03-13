package net.feerie.creatura.client.rendu2d;

import com.google.gwt.canvas.dom.client.Context2d;

import net.feerie.creatura.shared.monde.Zone;

public class RenduZone implements RenduElement
{
	private final Zone zone;
	private final Context2d contexte;
	private final String couleurFond;
	private final String couleurSol;
	private final String couleurEau;
	
	public RenduZone(Zone zone, Context2d contexte)
	{
		this.zone = zone;
		this.contexte = contexte;
		
		int temperature = zone.getEnvironnement().getTemperature();
		
		if (temperature < 12)
		{
			couleurFond = "#c6d1ff";
			couleurSol = "#224149";
		}
		else if (temperature > 25)
		{
			couleurFond = "#fff9a6";
			couleurSol = "#5e492f";
		}
		else
		{
			couleurFond = "#c6fbff";
			couleurSol = "#4a663f";
		}
		couleurEau = "#0058a5";
	}
	
	@Override
	public void detruit()
	{
		//rien à faire pour le moment
	}
	
	@Override
	public void dessine(long dateActuelle, double progressionTic)
	{
		//Fond
		contexte.setFillStyle(couleurFond);
		contexte.fillRect(zone.getX(), 0, zone.getX() + zone.getLongueur(), 1000);
		//Eau
		if (zone.getHauteurEau() > 0)
		{
			contexte.setFillStyle(couleurEau);
			contexte.fillRect(zone.getX(), 0, zone.getX() + zone.getLongueur()+1, zone.getHauteurEau());
		}
		//Sol
		contexte.setFillStyle(couleurSol);
		contexte.beginPath();
		contexte.moveTo(zone.getX()-1, zone.getHauteurSolGauche());
		contexte.lineTo(zone.getX() + zone.getLongueur(), zone.getHauteurSolDroite());
		contexte.lineTo(zone.getX() + zone.getLongueur(), 0);
		contexte.lineTo(zone.getX()-1, 0);
		contexte.lineTo(zone.getX()-1, zone.getHauteurSolGauche());
		contexte.closePath();
		contexte.fill();
	}
}
