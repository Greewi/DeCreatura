package net.feerie.creatura.client.renduPixi;

import net.feerie.creatura.client.pixi.Container;
import net.feerie.creatura.client.pixi.Loader;
import net.feerie.creatura.client.pixi.Sprite;
import net.feerie.creatura.client.pixi.Texture;
import net.feerie.creatura.shared.entites.EntiteLitiere;

public class RenduLitere implements RenduElement
{
	private final static int HAUTEUR_CONTENU_LITIERE = 32;
	
	private final EntiteLitiere litiere;
	private final Sprite spriteBoite;
	private final Sprite spriteContenu;
	private final Texture textureContenu;
	
	public RenduLitere(EntiteLitiere litiere)
	{
		
		this.litiere = litiere;
		this.spriteBoite = Sprite.newSprite("images/Litiere.png");
		this.spriteBoite.setDimensions(64, 32);
		this.spriteBoite.anchor.x = 0.5;
		this.spriteBoite.anchor.y = 1;
		
		this.textureContenu = new Texture(Loader.getBaseTexture("images/Litiere-contenu.png"));
		this.spriteContenu = new Sprite(this.textureContenu);
		this.spriteContenu.setDimensions(64, 16);
		this.spriteContenu.anchor.x = 0.5;
		this.spriteContenu.anchor.y = 1;
		this.textureContenu.frame.y = HAUTEUR_CONTENU_LITIERE;
		this.textureContenu.frame.height = 0;
	}
	
	@Override
	public void ajoute(Scene scene)
	{
		scene.ajouteElementAvant(spriteContenu);
		scene.ajouteElementAvant(spriteBoite);
	}
	
	@Override
	public void metAJour(long dateActuelle, double progressionTic)
	{
		double x = ((1 - progressionTic) * litiere.position.x + progressionTic * litiere.positionProchainTic.x);
		double y = 1000 - ((1 - progressionTic) * litiere.position.z + progressionTic * litiere.positionProchainTic.z);
		this.spriteBoite.setPosition(x, y);
		this.spriteContenu.setPosition(x, y);
		
		//Remplissage
		double remplissage = litiere.getContenu() / litiere.getCapacite();
		if (remplissage > 1)
			remplissage = 1;
		this.textureContenu.frame.y = (1 - remplissage) * HAUTEUR_CONTENU_LITIERE;
		this.textureContenu.frame.height = remplissage * HAUTEUR_CONTENU_LITIERE;
		this.textureContenu._updateUvs();
	}
	
	@Override
	public void detruit()
	{
		Container parent = spriteContenu.getParent();
		if (parent != null)
			parent.removeChild(spriteContenu);
		parent = spriteBoite.getParent();
		if (parent != null)
			parent.removeChild(spriteBoite);
	}
	
}
