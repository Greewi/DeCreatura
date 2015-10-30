(function(){

if(!window.alfar)
	window.alfar={};

/**
 * Représente un programme shader WebGL.
 */
alfar.Programme = function(gl, sourceVertexShader, sourceFragmentShader){
	this.gl = gl;

	//Compilation du vertex shader
	this.vertexShader = gl.createShader(gl.VERTEX_SHADER);
	gl.shaderSource(this.vertexShader, sourceVertexShader);
	gl.compileShader(this.vertexShader);
	if (gl.getError() != gl.NO_ERROR)
	{
		console.log('VertexShader : ' +  gl.getShaderInfoLog(this.vertexShader));
	}

	//Compilation du fragment shader	
	this.fragmentShader = gl.createShader(gl.FRAGMENT_SHADER);
	gl.shaderSource(this.fragmentShader, sourceFragmentShader);
	gl.compileShader(this.fragmentShader);
	if (gl.getError() != gl.NO_ERROR)
	{
		console.log('FragmentShader : ' +  gl.getShaderInfoLog(this.fragmentShader));
	}
	//Construction du programme
	this.programme = gl.createProgram();
	gl.attachShader(this.programme, this.vertexShader);
	gl.attachShader(this.programme, this.fragmentShader);
	gl.linkProgram(this.programme);
	gl.validateProgram(this.programme);
	if (!gl.getProgramParameter(this.programme,gl.VALIDATE_STATUS))
	{
		console.log('VertexShader : ' +  gl.getShaderInfoLog(this.vertexShader));
		console.log('FragmentShader : ' +  gl.getShaderInfoLog(this.fragmentShader));
		console.log("Programe INVALIDE !");
		return;
	}

	//Extraction des variables (uniform et attribute) du programme
	this.attributs = {};
	gl.useProgram(this.programme);
	var motif = /\s*(uniform|attribute)\s*[a-zA-Z0-9]*\s*[a-zA-Z0-9]+\s+([a-zA-Z]+)/; //Regexp pour lire les déclarations dans la source du shader
	var ligneSource = (sourceVertexShader+'\n'+sourceFragmentShader).split('\n');
	for(var i=0; i<ligneSource.length; i++)
	{
		var resultat = ligneSource[i].match(motif);
		if(resultat)
		{
			var type = resultat[1];
			var nom = resultat[2];
			this[nom] = (type=="uniform") ? gl.getUniformLocation(this.programme, nom) : gl.getAttribLocation(this.programme, nom);
			if(type=="attribute")
				this.attributs[nom] = this[nom];
		}
	}
	
	/**
	 * Détruit ce programme
	 */
	this.detruit = function(){
		gl.deleteProgram(this.programme);
		gl.deleteShader(this.vertexShader);
		gl.deleteShader(this.fragmentShader);
	};

	/**
	 * Active le shader program
	 */
	this.utilise = function(){
		gl.useProgram(this.programme);
		for(var i in this.attributs)
			if(this.attributs[i]>=0)
				gl.enableVertexAttribArray(this.attributs[i]);
	};
};

})();