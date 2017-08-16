package net.feerie.creatura.shared.organisme;

public class VariableVitale
{
	private final TypeVariableVitale type;
	private double valeur;
	private final double valeurMin;
	private final double valeurMax;
	private final double valeurIdeale;
	
	/**
	 * Construit une variable vitale et l'intialise avec la valeur intiale
	 * 
	 * @param type le type de la variable
	 */
	public VariableVitale(TypeVariableVitale type)
	{
		this.type = type;
		this.valeur = type.getValeurInitiale();
		this.valeurMin = type.getValeurMin();
		this.valeurMax = type.getValeurMax();
		this.valeurIdeale = type.getValeurInitiale();
	}
	
	/**
	 * @return le type de la variable
	 */
	public TypeVariableVitale getType()
	{
		return type;
	}
	
	/**
	 * @return la valeur actuelle de la variable
	 */
	public double get()
	{
		return valeur;
	}
	
	/**
	 * Fixe la valeur actuelle de cette variable vitale
	 * 
	 * @param la nouvelle valeur de la variable
	 */
	public void set(double valeur)
	{
		if (valeurMax > 0 && valeur > valeurMax)
			this.valeur = valeurMax;
		else if (valeurMin >= 0 && valeur < valeurMin)
			this.valeur = valeurMin;
		else
			this.valeur = valeur;
	}
	
	/**
	 * @return la valeur id�ale de la variable
	 */
	public double getIdeal()
	{
		return valeurIdeale;
	}
	
	/**
	 * Incremente la valeur actuelle de cette variable vitale d'un certain
	 * montant.
	 * 
	 * @param increment la qunatit� � ajouter
	 * @return la quantit� effectivement ajout�e
	 */
	public double ajoute(double increment)
	{
		double valeurPrecedente = get();
		set(valeurPrecedente + increment);
		return get() - valeurPrecedente;
	}
	
	/**
	 * D�cr�mente la valeur actuelle de cette variable vitale d'un certain
	 * montant.
	 * 
	 * @param decrement la quantit� � retirer (positif)
	 * @return la quantit� effectivement retir�e
	 */
	public double retire(double decrement)
	{
		return -ajoute(-decrement);
	}
}
