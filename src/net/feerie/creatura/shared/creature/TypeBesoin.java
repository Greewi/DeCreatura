package net.feerie.creatura.shared.creature;

public enum TypeBesoin
{
	//Besoins primaires
	SOIF("Soif"),
	SUCRES("Sucre"),
	PROTEINES("Proteine"),
	GRAS("Gras"),
	POPO("Popo"),
	//Besoins environnementaux
	CHAUD("Chaud"),
	FROID("Froid"),
	//Besoins
	ENNUI("Ennui"),
	FATIGUE("Fatigue"),
	SOUFFLE("Souffle"),
	SOLITUDE("Solitude"),
	//Etats d'esprits
	TRISTESSE("Tristesse"),
	COLERE("Colère"),
	PEUR("Peur");
	
	public String getNom()
	{
		return nom;
	}
	
	private final String nom;
	
	private TypeBesoin(String nom)
	{
		this.nom = nom;
	}
}
