(function(){

if(!window.alfar)
	window.alfar={};

/**
 * Représente une texture WebGl
 */
alfar.Texture = function(gl, nomTexture, image){
	this.gl = gl;
	this.nom = nomTexture;
	
	// Création de la texture WebGL
	this.texture = gl.createTexture();
	gl.bindTexture(gl.TEXTURE_2D, this.texture);

	// Filtrage anisotropic si disponible
	var extTextureFilterAnisotropic = gl.getExtension('EXT_texture_filter_anisotropic') || gl.getExtension('MOZ_EXT_texture_filter_anisotropic') || gl.getExtension('WEBKIT_EXT_texture_filter_anisotropic');
	if (extTextureFilterAnisotropic)
	{
		var max = gl.getParameter(extTextureFilterAnisotropic.MAX_TEXTURE_MAX_ANISOTROPY_EXT);
		gl.texParameterf(gl.TEXTURE_2D, extTextureFilterAnisotropic.TEXTURE_MAX_ANISOTROPY_EXT, max);
	}

	// Remplissage de la texture
	gl.texImage2D(gl.TEXTURE_2D, 0, gl.RGBA, gl.RGBA, gl.UNSIGNED_BYTE, image);
	if (extTextureFilterAnisotropic)
		gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_MIN_FILTER, gl.LINEAR_MIPMAP_LINEAR);
	else
		gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_MIN_FILTER, gl.LINEAR);
	gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_MAG_FILTER, gl.LINEAR);
	gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_WRAP_S, gl.REPEAT);
 	gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_WRAP_T, gl.REPEAT);
	if (extTextureFilterAnisotropic)
		gl.generateMipmap(gl.TEXTURE_2D);

	// En cas d'erreur on le signal
	if (gl.getError() != gl.NO_ERROR)
	{
		if(ch.config.debug)
		{
			console.err("Erreur lors du chargement de la texture : " + nomTexture);
		}
	}

	gl.bindTexture(gl.TEXTURE_2D, null); //On unbind la texture pour que les opérations suivantes ne l'utilisent pas sans le vouloir

	/**
	 * Renvoie le nom de la texture
	 */
	this.getNom = function(){
		return this.nom;
	};
	
	/**
	 * Destruction de la texture
	 */
	this.detruit = function(){
		gl.deleteTexture(this.texture);
	};

	/**
	 * Utilise la texture
	 */
	this.utilise = function(programme, uniteTexture, uniform){
		if(uniteTexture === undefined)
			uniteTexture = 0;
		if(uniform === undefined)
			uniform = programme.uTexture;
		
		gl.activeTexture(gl["TEXTURE"+uniteTexture]);
		gl.bindTexture(gl.TEXTURE_2D, this.texture);
		gl.uniform1i(uniform, uniteTexture);
	};
};
})();

(function(){
alfar.BibliothequeTexture = function(gl){
	var that = this;
	this.textures = {}; //Stocke les textures sous la forme alfar.bibliothequeTexture.textures["nom"] = Texture
	
	/**
	 * Renvoie une texture chargée (ou null)
	 */
	this.getTexture = function(nom){
		if(this.textures.hasOwnProperty(nom))
			return this.textures[nom];
		return null;
	};
	
	/**
	 * Charge une liste de texture et appelle onLoad. Ne recharge pas les textures déjà chargées
	 */
	this.chargeTextures = function(texturesACharger, onLoad){
		var nombreACharger = 0;
		for(var nom in texturesACharger)
		{
			if(!this.textures.hasOwnProperty(nom))//On ne charge pas une texture déjà chargée
			{
				nombreACharger++;
				chargeTexture(gl, nom, texturesACharger[nom], function(texture){
					that.textures[texture.getNom()] = texture;
					nombreACharger--;
					if(nombreACharger == 0)
						onLoad();
				});
			}
		}
	};
		
	/**
	 * Charge une texture et appelle onLoad
	 */
	var chargeTexture = function(gl, nom, nomFichier, onLoad){
		var image = new Image();
		image.onload = function(){
			var texture = new alfar.Texture(gl, nom, this);
			onLoad(texture);
		};
		image.src = nomFichier;
	};
};

})();
