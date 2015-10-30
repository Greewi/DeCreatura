(function(){

if(!window.creatura)
	window.creatura={};

/**
 * Représente une action réalisable par une créature dans le monde.
 * Les actions comporteront toujours une cible.
 * Cette classe est destinée à être héritée par les vraies
 * implémentations. Elle ne devrait pas être utilisée directement.
 * @param nom : le nom de l'action (pour affichage)
 * @param type : le type dl'action (TODO : Enum ?)
 * @param creature : la créature réalisant l'action
 * @param cible : l'entité cible de l'action
 * @param debut : la frame de début de l'action
 */
creatura.Action = function(nom, type, creature, cible, debut){
	this.nom = nom;
	this.type = type;
	this.creature = creature;
	this.cible = cible;
	this.debut = debut;
	
	/**
	 * Renvoie le type de l'action
	 */
	this.getType = function(){
		return this.type;
	};
	
	/**
	 * Met à jour l'action, pouvant potentiellement la terminer.
	 * Renvoie true si et seulement si l'action n'est pas terminée.
	 */
	this.metAJour = function(frame){
		return false;
		//Cette implémentation ne fait strictement rien...
	};
};

/**
 * Représente l'action se déplacer vers un objet.
 * @param creature : la créature réalisant l'action
 * @param cible : l'entité cible de l'action
 * @param debut : la frame de début de l'action
 * @param actionAEnchainer : l'action à effectuer une fois arrivé sur place. Si null, rien ne sera fait.
 */
creatura.ActionSeDeplacer = function(creature, cible, debut, actionAEnchainer){
	creatura.Action.call(this, "deplacer", "SeDeplace", creature, cible, debut);
	
	this.vitesse = 0.5;
	
	/**
	 * Met à jour l'action, pouvant potentiellement la terminer
	 * Renvoie true si et seulement si l'action n'est pas terminée.
	 */
	this.metAJour = function(frame){
		//On avance d'une frame
		var arrive = this.avance(frame);
		
		//Si on n'est pas encore arrivé il faudra continuer à la prochaine frame
		if(arrive)
			return true;
		//Si on est arrivé, on se place bien sur le point d'arrivée et on effectuer l'action en attente
		else
		{
			creature.x = cible.x;
			creature.y = cible.y;
			if(actionAEnchainer !== null)
			{
				creature.setActionActuelle(new actionAEnchainer(creature, cible, frame));
				return true;
			}
			else
				return false;
		}
	};
	
	this.avance = function(frame){
		//Si la cible n'existe plus on arrête
		if(!cible.existe())
			return false;
			
		var vx = cible.x - creature.x;
		var vy = cible.y - creature.y;		
		var distance = Math.sqrt(vx*vx + vy*vy);

		//Si on est arrivé, on s'arrête
		if(distance < 0.0001)
			return false;

		var vitesse = (this.vitesse<distance)? this.vitesse : distance;
		creature.x += vitesse*vx/distance;
		creature.y += vitesse*vy/distance;
		
		return true;
	};
};

/**
 * Représente l'action manger.
 * @param creature : la créature réalisant l'action
 * @param cible : l'entité cible de l'action
 * @param debut : la frame de début de l'action
 */
creatura.ActionManger = function(creature, cible, debut){
	creatura.Action.call(this, "manger", "Mange", creature, cible, debut);

	/**
	 * Met à jour l'action, pouvant potentiellement la terminer
	 * Renvoie true si et seulement si l'action n'est pas terminée.
	 */
	this.metAJour = function(frame){
		//Si la cible n'existe plus on arrête
		if(!cible.existe())
			return false;
		
		//Si la créature n'a pas fini de manger on continue
		if(frame<this.debut+60)
			return true;
		
		//Sinon on applique les effets
		if(cible.getType()=="nourriture")
		{
			cible.detruit();
			creature.getVariableVitale("sucresDigestion").ajoute(cible.getSucres());
			creature.getVariableVitale("proteinesDigestion").ajoute(cible.getProteines());
			creature.getVariableVitale("grasDigestion").ajoute(cible.getGras());
			creature.getVariableVitale("eauDigestion").ajoute(cible.getEau());
		}
		return false;
	};
};

/**
 * Représente l'action manger.
 * @param creature : la créature réalisant l'action
 * @param cible : l'entité cible de l'action
 * @param debut : la frame de début de l'action
 */
creatura.ActionPopo = function(creature, cible, debut){
	creatura.Action.call(this, "popo", "Fait Popo", creature, cible, debut);
	
	/**
	 * Met à jour l'action, pouvant potentiellement la terminer
	 * Renvoie true si et seulement si l'action n'est pas terminée.
	 */
	this.metAJour = function(frame){
		//Si la cible n'existe plus on arrête
		if(!cible.existe())
			return false;
			
		//Si la créature n'a pas fini de faire ses besoins on continue
		if(frame<this.debut+120)
			return true;
		
		//On créé des déchets dans le monde
		if(cible.getType == "litiere")
			cible.ajouteDechets(creature.getVariableVitale("dechets").get());
		else
			monde.ajouteEntite(new creatura.Dechet(monde, creature.getVariableVitale("dechets").get(), cible.x, cible.y));
		
		//On "vide" la créature
		creature.getVariableVitale("dechets").set(0);
		
		return false;
	};
};


/**
 * Représente l'action dormir.
 * @param creature : la créature réalisant l'action
 * @param cible : l'entité cible de l'action
 * @param debut : la frame de début de l'action
 */
creatura.ActionDormir = function(creature, cible, debut){
	creatura.Action.call(this, "dormir", "Dort", creature, cible, debut);

	/**
	 * Met à jour l'action, pouvant potentiellement la terminer
	 * Renvoie true si et seulement si l'action n'est pas terminée.
	 */
	this.metAJour = function(frame){
		//Si la cible n'existe plus on arrête
		if(!cible.existe())
			return false;
		
		var confort = 1.0;
		var sommeilGagnee = 1 + (frame-this.debut)/10;
		creature.getVariableVitale("fatigue").ajoute(sommeilGagnee);
		
		//Si la créature n'a plus sommeil on la réveille
		if(creature.getVariableVitale("fatigue").get() == creature.getVariableVitale("fatigue").getIdeal())
			return false;
		return true;
	};
};

})();