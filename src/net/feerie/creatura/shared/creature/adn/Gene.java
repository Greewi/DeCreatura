package net.feerie.creatura.shared.creature.adn;

public enum Gene
{
	CERVEAU_SIMPLE("ACTGAG"),
	ESTOMAC_SIMPLE("CTTAAG"),
	YEUX_SIMPLE("AAATTA"),
	PEAU_SIMPLE("CCTGTG");
	
	private final String codons;
	
	private Gene(String codons)
	{
		this.codons=codons;
	}
	
	public String getCodons()
	{
		return codons;
	}
}
