package net.feerie.creatura.shared.entites;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.Random;

import net.feerie.creatura.shared.Constantes;
import net.feerie.creatura.shared.commons.Dimension;
import net.feerie.creatura.shared.commons.Position;
import net.feerie.creatura.shared.commons.Rectangle;
import net.feerie.creatura.shared.monde.Monde;

public class EntiteArbre extends Entite
{
	private List<Fruit> fruits;
	private int cyclesDepuisDernierFruit;
	private final Rectangle rectangleFruits;
	
	public EntiteArbre(Monde monde, Position position, Dimension taille)
	{
		super(monde, position, taille);
		fruits = new ArrayList<>();
		rectangleFruits = Rectangle.creeDepuisCoinHautGauche(new Position(position.x - taille.l / 2, position.z + taille.h / 2), new Dimension(taille.l, taille.h / 2));
	}
	
	@Override
	public TypeEntite getType()
	{
		return TypeEntite.ARBRE;
	}
	
	/**
	 * Renvoie la liste des fruits de cet arbre
	 * 
	 * @return la liste des fruits de cet arbre
	 */
	public List<Fruit> getFruits()
	{
		return fruits;
	}
	
	/**
	 * Secoue l'arbre et fait tomber ses fruits murs
	 */
	public void secoue()
	{
		while (fruits.size() > 0 && fruits.get(0).estMur())
			faitTomberPremierFruit();
	}
	
	/**
	 * Fait tomber le premier fruit de l'arbte
	 */
	private void faitTomberPremierFruit()
	{
		Fruit fruit = fruits.remove(0);
		TypeNourriture[] types = TypeNourriture.values();
		TypeNourriture type = types[Random.nextInt(types.length)];
		monde.nouvelleEntite(new EntiteNourriture(monde, type, fruit.getPosition()));
	}
	
	@Override
	public void effectueCycleMetabolique()
	{
		// Génération de nouveaux fruits
		cyclesDepuisDernierFruit++;
		if (cyclesDepuisDernierFruit >= Constantes.ARBRE_CYCLE_NOUVEAU_FRUIT)
		{
			cyclesDepuisDernierFruit = 0;
			fruits.add(new Fruit(rectangleFruits.getPositionAleatoire()));
		}
		
		// Maturation des fruits
		for (Fruit fruit : fruits)
			fruit.EffectueCycleMetabolique();
		
		// On fait tomber le premier fruit s'il est assez mur
		if (fruits.size() > 0)
		{
			if (fruits.get(0).peutTomber())
				faitTomberPremierFruit();
		}
	}
	
	/**
	 * Représente un fruit dans l'arbre
	 */
	public class Fruit
	{
		private int cycles;
		private Position position;
		
		/**
		 * @param position la position du fruit
		 */
		public Fruit(Position position)
		{
			this.cycles = 0;
			this.position = position;
		}
		
		/**
		 * Renvoie la positon du fruit
		 * 
		 * @return la positon du fruit
		 */
		public Position getPosition()
		{
			return position;
		}
		
		/**
		 * Détermine si le fruit est mur
		 * 
		 * @return <tt>true</tt> ssi le fruit est mur
		 */
		public boolean estMur()
		{
			return cycles >= Constantes.ARBRE_CYCLE_MATURATION;
		}
		
		/**
		 * Détermine si le fruit est assez mur pour tomber de lui-même
		 * 
		 * @return <tt>true</tt> ssi le fruit est assez mur pour tomber de
		 *         lui-même
		 */
		public boolean peutTomber()
		{
			return cycles >= Constantes.ARBRE_CYCLE_TOMBE;
		}
		
		/**
		 * Effectue un cycle métabolique
		 */
		public void EffectueCycleMetabolique()
		{
			cycles++;
		}
	}
}
