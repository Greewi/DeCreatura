(function(){

if(!window.alfar)
	window.alfar={};

/**
 * Représente la géométrie d'un objet de la scène
 */
alfar.Mesh = function(gl, source){
	this.gl = gl;
	this.source = source;
	this.nombreSommets = source.vertices.length/3;
	//Initialision des buffers
	this.vertexBuffer = gl.createBuffer();
	gl.bindBuffer(gl.ARRAY_BUFFER, this.vertexBuffer);
	gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(source.vertices), gl.DYNAMIC_DRAW);
	this.normalBuffer = gl.createBuffer();
	gl.bindBuffer(gl.ARRAY_BUFFER, this.normalBuffer);
	gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(source.normals), gl.DYNAMIC_DRAW);
	this.texCoordBuffer = gl.createBuffer();
	gl.bindBuffer(gl.ARRAY_BUFFER, this.texCoordBuffer);
	gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(source.texCoords), gl.DYNAMIC_DRAW);

	/**
	 * Détruit le mesh
	 */
	this.detruit = function(){
		gl.deleteBuffer(this.vertexBuffer);
		gl.deleteBuffer(this.normalBuffer);
		gl.deleteBuffer(this.texCoordBuffer);
	};
	
	/**
	 * Dessine le mesh
	 */
	this.dessine = function(programme){
		this.attacheGeometrie(programme);
		this.dessineTriangles();
	};
	
	this.attacheGeometrie = function(programme){
		if(programme.aVertexPosition>=0)
  		{
			gl.bindBuffer(gl.ARRAY_BUFFER, this.vertexBuffer);
	  		gl.vertexAttribPointer(programme.aVertexPosition, 3, gl.FLOAT, false, 0, 0);
  		}
  		if(programme.aVertexNormal>=0)
  		{
			gl.bindBuffer(gl.ARRAY_BUFFER, this.normalBuffer);
	  		gl.vertexAttribPointer(programme.aVertexNormal, 3, gl.FLOAT, false, 0, 0);  			
  		}
  		if(programme.aTextureCoordinate>=0)
  		{
			gl.bindBuffer(gl.ARRAY_BUFFER, this.texCoordBuffer);
	  		gl.vertexAttribPointer(programme.aTextureCoordinate, 2, gl.FLOAT, false, 0, 0);
	  	}
	};
	
	this.dessineTriangles = function(){
		gl.drawArrays(gl.TRIANGLES, 0, this.nombreSommets);
	};
};

/**
 * Bibliothèque de générateur de meshes
 */
alfar.meshes = {
	/**
	 * Créé une sphère UV
	 */
	creerSphereUV : function(gl, x, y, z, rayon, subdivisions){
		var source = {vertices : [], normals : [], texCoords : []};
		for(var i=0; i<subdivisions*2; i++)
			for(var j=0; j<subdivisions; j++)
			{
				var x0 = Math.cos(i/subdivisions * Math.PI) * Math.sin(j/subdivisions * Math.PI);
				var y0 = Math.sin(i/subdivisions * Math.PI) * Math.sin(j/subdivisions * Math.PI);
				var z0 = Math.cos(j/subdivisions * Math.PI);
				var x1 = Math.cos((i+1)/subdivisions * Math.PI) * Math.sin(j/subdivisions * Math.PI);
				var y1 = Math.sin((i+1)/subdivisions * Math.PI) * Math.sin(j/subdivisions * Math.PI);
				var z1 = Math.cos(j/subdivisions * Math.PI);
				var x2 = Math.cos((i+1)/subdivisions * Math.PI) * Math.sin((j+1)/subdivisions * Math.PI);
				var y2 = Math.sin((i+1)/subdivisions * Math.PI) * Math.sin((j+1)/subdivisions * Math.PI);
				var z2 = Math.cos((j+1)/subdivisions * Math.PI);
				var x3 = Math.cos(i/subdivisions * Math.PI) * Math.sin((j+1)/subdivisions * Math.PI);
				var y3 = Math.sin(i/subdivisions * Math.PI) * Math.sin((j+1)/subdivisions * Math.PI);
				var z3 = Math.cos((j+1)/subdivisions * Math.PI);
				
				source.vertices.push(x+x0*rayon); source.vertices.push(y+y0*rayon); source.vertices.push(z+z0*rayon);
				source.vertices.push(x+x1*rayon); source.vertices.push(y+y1*rayon); source.vertices.push(z+z1*rayon);
				source.vertices.push(x+x2*rayon); source.vertices.push(y+y2*rayon); source.vertices.push(z+z2*rayon);
				source.vertices.push(x+x0*rayon); source.vertices.push(y+y0*rayon); source.vertices.push(z+z0*rayon);
				source.vertices.push(x+x2*rayon); source.vertices.push(y+y2*rayon); source.vertices.push(z+z2*rayon);
				source.vertices.push(x+x3*rayon); source.vertices.push(y+y3*rayon); source.vertices.push(z+z3*rayon);
				
				source.normals.push(x0); source.normals.push(y0); source.normals.push(z0);
				source.normals.push(x1); source.normals.push(y1); source.normals.push(z1);
				source.normals.push(x2); source.normals.push(y2); source.normals.push(z2);
				source.normals.push(x0); source.normals.push(y0); source.normals.push(z0);
				source.normals.push(x2); source.normals.push(y2); source.normals.push(z2);
				source.normals.push(x3); source.normals.push(y3); source.normals.push(z3);
				
				source.texCoords.push(i/subdivisions*2);     source.texCoords.push(j/subdivisions);
				source.texCoords.push((i+1)/subdivisions*2); source.texCoords.push(j/subdivisions);
				source.texCoords.push((i+1)/subdivisions*2); source.texCoords.push((j+1)/subdivisions);
				source.texCoords.push(i/subdivisions*2);     source.texCoords.push(j/subdivisions);
				source.texCoords.push((i+1)/subdivisions*2); source.texCoords.push((j+1)/subdivisions);
				source.texCoords.push(i/subdivisions*2);     source.texCoords.push((j+1)/subdivisions);
			}
		return new alfar.Mesh(gl, source);
	},
	
	/**
	 * Charge un mesh depuis un json de threejs
	 */
	creerDepuisJSONThreeJS : function(gl, json){
		var source = {vertices : [], normals : [], texCoords : []};
		for(var face=0; face<json.faces.length; face+=10)
		{
			if(json.faces[face]!=40)
				console.err("Json non valide : 40 attendu pour le format des faces");
			source.vertices.push(json.vertices[json.faces[face+1]*3 + 0]);
			source.vertices.push(json.vertices[json.faces[face+1]*3 + 2]);
			source.vertices.push(json.vertices[json.faces[face+1]*3 + 1]);
			source.vertices.push(json.vertices[json.faces[face+2]*3 + 0]);
			source.vertices.push(json.vertices[json.faces[face+2]*3 + 2]);
			source.vertices.push(json.vertices[json.faces[face+2]*3 + 1]);
			source.vertices.push(json.vertices[json.faces[face+3]*3 + 0]);
			source.vertices.push(json.vertices[json.faces[face+3]*3 + 2]);
			source.vertices.push(json.vertices[json.faces[face+3]*3 + 1]);
			
			source.texCoords.push(json.uvs[0][json.faces[face+4]*2 + 0]);
			source.texCoords.push(1-json.uvs[0][json.faces[face+4]*2 + 1]);
			source.texCoords.push(json.uvs[0][json.faces[face+5]*2 + 0]);
			source.texCoords.push(1-json.uvs[0][json.faces[face+5]*2 + 1]);
			source.texCoords.push(json.uvs[0][json.faces[face+6]*2 + 0]);
			source.texCoords.push(1-json.uvs[0][json.faces[face+6]*2 + 1]);
			
			source.normals.push(json.normals[json.faces[face+7]*3 + 0]);
			source.normals.push(json.normals[json.faces[face+7]*3 + 2]);
			source.normals.push(json.normals[json.faces[face+7]*3 + 1]);
			source.normals.push(json.normals[json.faces[face+8]*3 + 0]);
			source.normals.push(json.normals[json.faces[face+8]*3 + 2]);
			source.normals.push(json.normals[json.faces[face+8]*3 + 1]);
			source.normals.push(json.normals[json.faces[face+9]*3 + 0]);
			source.normals.push(json.normals[json.faces[face+9]*3 + 2]);
			source.normals.push(json.normals[json.faces[face+9]*3 + 1]);
		}
		return new alfar.Mesh(gl, source);
	}
};
}());