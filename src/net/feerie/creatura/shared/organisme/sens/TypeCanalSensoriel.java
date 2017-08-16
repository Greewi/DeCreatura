package net.feerie.creatura.shared.organisme.sens;

public enum TypeCanalSensoriel
{
	/**
	 * La santé de la créature (0 morte, 100 en pleine forme)
	 */
	SANTE,
	/**
	 * L'énergie de la créature (0 coma/sommeil forcé, 100 pleine d'énergie)
	 */
	ENERGIE,
	/**
	 * Le moral de la créature (0 déprimée, 100 heureuse)
	 */
	MORAL,
	/**
	 * L'éveil de la créature (0 ennui total, 100 très active)
	 */
	EVEIL,
	/**
	 * Canal rapportant la satiété au cerveau (0 ventre vide, 100 ventre plein)
	 */
	SATIETE,
	/**
	 * Hydratation de la créature (0 la créature est déhydratée, 100 la créature
	 * est pleine hydratée)
	 */
	HYDRATATION,
	/**
	 * Niveau de solicitation des intestins (0 vides, 100 pleins)
	 */
	INTESTINS,
	/**
	 * Chaleur de la créature (0 frigorifiée, 100 en train de cuire)
	 */
	CHALEUR
}
