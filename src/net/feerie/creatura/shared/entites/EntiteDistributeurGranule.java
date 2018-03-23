package net.feerie.creatura.shared.entites;

import net.feerie.creatura.shared.commons.Dimension;
import net.feerie.creatura.shared.commons.Position;
import net.feerie.creatura.shared.commons.Rectangle;
import net.feerie.creatura.shared.monde.Monde;

public class EntiteDistributeurGranule extends Entite
{
	private final Rectangle rectangleGeneration;
	
	public EntiteDistributeurGranule(Monde monde, Position position)
	{
		super(monde, position, new Dimension(64, 128));
		rectangleGeneration = Rectangle.creeDepuisCoinHautGauche(new Position(position.x - 24 + 4, position.z + 48), new Dimension(40, 8));
	}
	
	@Override
	public TypeEntite getType()
	{
		return TypeEntite.DISTRIBUTEUR_GRANULE;
	}
	
	@Override
	public String active(EntiteCreature activateur)
	{
		monde.nouvelleEntite(new EntiteNourriture(monde, rectangleGeneration.getPositionAleatoire(), TypeEntite.NOURRITURE_GRANULE));
		if (activateur != null)
			activateur.getIA().constateNouvelleEntite(TypeEntite.NOURRITURE_GRANULE);
		return null;
	}
}
