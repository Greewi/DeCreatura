package net.feerie.creatura.shared.organisme.sens;

public enum TypeCanalSensoriel
{
	/**
	 * La sant� de la cr�ature (0 morte, 100 en pleine forme)
	 */
	SANTE,
	/**
	 * L'�nergie de la cr�ature (0 coma/sommeil forc�, 100 pleine d'�nergie)
	 */
	ENERGIE,
	/**
	 * Le moral de la cr�ature (0 d�prim�e, 100 heureuse)
	 */
	MORAL,
	/**
	 * L'�veil de la cr�ature (0 ennui total, 100 tr�s active)
	 */
	EVEIL,
	/**
	 * Canal rapportant la sati�t� au cerveau (0 ventre vide, 100 ventre plein)
	 */
	SATIETE,
	/**
	 * Hydratation de la cr�ature (0 la cr�ature est d�hydrat�e, 100 la cr�ature
	 * est pleine hydrat�e)
	 */
	HYDRATATION,
	/**
	 * Niveau de solicitation des intestins (0 vides, 100 pleins)
	 */
	INTESTINS,
	/**
	 * Chaleur de la cr�ature (0 frigorifi�e, 100 en train de cuire)
	 */
	CHALEUR
}
