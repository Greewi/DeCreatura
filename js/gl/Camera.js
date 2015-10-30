(function(){

if(!window.alfar)
	window.alfar={};
	
/**
 * Représente une caméra, définissant le cadre du rendu
 */
alfar.Camera = function(gl){
	this.gl = gl;
	this.modelViewMatrix = makeLookAt(0.0, 0.0, 0.0,    1.0, 0.0, 0.0,    0.0, 0.0, 1.0);
	this.projectionMatrix = makeFrustum(-0.1, 0.1,    -0.1, 0.1,    0.1, 10.0);
	this.hx = this.hy = this.hz = 0.0;
	this.vx = this.vy = this.vz = 0.0;

	/**
	 * Détruit la caméra
	 */
	this.detruit = function(){
		//Rien de particulier détruire ici
	};

	/**
	 * Défini la position de la caméra.
	 * @param x, y, z : coordonnées de la position de la vue de la camera
	 * @param xCible, yCible, zCible [opt] : coordonnées du point de visé de la camera
	 * @param xhaut, yHaut, zhaut [opt] : coordonnées du vecteur de la direction "haut" de la camera
	 */
	this.setPosition = function(x, y, z, xCible, yCible, zCible, xHaut, yHaut, zHaut){
		xCible = (xCible!==undefined) ? xCible : 1.0;
		yCible = (yCible!==undefined) ? yCible : 0.0;
		zCible = (zCible!==undefined) ? zCible : 0.0;
		xHaut = (xHaut!==undefined) ? xHaut : 0.0;
		yHaut = (yHaut!==undefined) ? yHaut : 0.0;
		zHaut = (zHaut!==undefined) ? zHaut : 1.0;

		//Construction de la matrice model view
		this.modelViewMatrix = makeLookAt(
			x, y, z,
			xCible, yCible, zCible,
			xHaut, yHaut, zHaut
		);

		//Construction des vecteurs horizontaux et verticaux
		var dx = x - xCible;
		var dy = y - yCible;
		var dz = z - zCible;
		var norm = Math.sqrt(dx*dx + dy*dy + dz*dz);
		dx/=norm;dy/=norm;dz/=norm;
		this.hx = yHaut*dz - zHaut*dy;
		this.hy = zHaut*dx - xHaut*dz;
		this.hz = xHaut*dy - yHaut*dx;
		this.vx = xHaut;
		this.vy = yHaut;
		this.vz = zHaut;

		this._recalculeMatrice();
	};

	/**
	 * Défini le volume de projection de la caméra.
	 * @param fov : angle d'ouverture vertical de la camera (en degré)
	 * @param aspect : ration hauteur/largeur de l'image
	 * @param near : distance d'affichage minimal (clipping)
	 * @param far : distance d'affichage maximale (clipping)
	 * @param deltah [opt] : décalage horizontal du volume (en proportion de la largeur)
	 * @param deltav [opt] : décalage vertical du volume (en proportion de la hauteur)
	 */
	this.setProjection = function(fov, aspect, near, far, deltah, deltav){
		var ymax = near * Math.tan(fov * Math.PI / 360.0);
    	var ymin = -ymax;
    	var xmin = ymin / aspect;
    	var xmax = ymax / aspect;
    	deltah = deltah || 0.0;
    	deltav = deltav || 0.0;

    	//Construction de la matrice projection
		this.projectionMatrix = makeFrustum(
				xmin + xmax*deltah, xmax + xmax*deltah,
				ymin + ymax*deltav, ymax + ymax*deltav,
				near, far
		);

		this._recalculeMatrice();
	};

	/**
	 * Active la caméra pour le rendu
	 */
	this.utilise = function(programme){
		gl.uniformMatrix4fv(programme.uCameraMatrix, false, this.matriceFinale);
	};

	/**
	 * Recalcule la matrice webGL utilisée pour le shader
	 */
	this._recalculeMatrice = function(){
		this.matriceFinale = new Float32Array(this.projectionMatrix.multiply(this.modelViewMatrix).flatten());
	};

	//On effectue le relacule de la matrice initiale
	this._recalculeMatrice();
};

}());