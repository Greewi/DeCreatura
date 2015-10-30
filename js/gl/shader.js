function genererVertexShaderSimple(){
	var source = "";
	//Entree
  	source += "uniform mat4 uCameraMatrix;\n";
	source += "attribute vec3 aVertexPosition;\n";
	source += "attribute vec3 aVertexNormal;\n";
	source += "attribute vec2 aTextureCoordinate;\n";
	//Sortie
	source += "varying vec3 vVertexNormal;\n";
	source += "varying vec2 vTextureCoordinate;\n";
	//Fonction principale
	source += "void main(void) {\n";
    source += "    gl_Position = uCameraMatrix * vec4(aVertexPosition, 1.0);\n";
    source += "    vVertexNormal = aVertexNormal;\n";
    source += "    vTextureCoordinate = aTextureCoordinate;\n";
  	source += "}\n";
	return source;
}

function genererFragmentShaderLambert(){
	var source = "";
	//Entrées
	source += "uniform sampler2D uTexture;\n";
	source += "varying mediump vec2 vTextureCoordinate;\n";
	source += "varying mediump vec3 vVertexNormal;\n";
	//Fonction principale
	source += "void main(void) {\n";
	source += "    lowp vec4 color = texture2D(uTexture, vTextureCoordinate);\n";
	source += "    if(color.a < 0.5)\n";
	source += "        discard;\n";
	source += "    gl_FragColor = vec4(color.xyz * (clamp(dot(vec3(-1.0, 0.0, 1.0), vVertexNormal), 0.0, 1.0) + 0.3), 1.0);\n";
  	source += "}\n";
	return source;
}

function genererVertexShaderFur(){
	var source = "";
	//Entree
  	source += "uniform lowp float uCouche;\n";
  	source += "uniform mat4 uCameraMatrix;\n";
  	source += "uniform lowp vec3 uEchellePoils;\n";
	source += "attribute vec3 aVertexPosition;\n";
	source += "attribute vec3 aVertexEnd;\n";
	source += "attribute vec3 aVertexNormal;\n";
	source += "attribute vec2 aTextureCoordinate;\n";
	//Sortie
	source += "varying vec3 vVertexNormal;\n";
	source += "varying vec2 vTextureCoordinate;\n";
	//Fonction principale
	source += "void main(void) {\n";
    source += "    gl_Position = uCameraMatrix * vec4(mix(aVertexPosition, aVertexEnd, uCouche*uEchellePoils.z), 1.0);\n";
    source += "    vVertexNormal = aVertexNormal;\n";
    source += "    vTextureCoordinate = aTextureCoordinate;\n";
  	source += "}\n";
	return source;
}

function genererFragmentShaderFur(){
	var source = "";
	//Entrées
  	source += "uniform lowp vec3 uEchellePoils;\n";
  	source += "uniform lowp float uCouche;\n";
	source += "uniform sampler2D uTexture;\n";
	source += "uniform sampler2D uFourrure;\n";
	source += "varying mediump vec2 vTextureCoordinate;\n";
	source += "varying mediump vec3 vVertexNormal;\n";
	//Fonction principale
	source += "void main(void) {\n";
	source += "    lowp vec4 poil = texture2D(uFourrure, vTextureCoordinate*uEchellePoils.xy);\n";
	source += "    if(poil.a < uCouche*uCouche)\n";
	source += "        discard;\n";
	source += "    lowp vec3 color = texture2D(uTexture, vec2(vTextureCoordinate.s, vTextureCoordinate.t)).xyz;\n";
	source += "    color = color*(uCouche*uCouche*0.5+0.5);\n";
	source += "    color = color*(clamp(dot(vec3(-1.0, 0.0, 1.0), vVertexNormal), 0.0, 1.0)+0.3);\n";
	source += "    gl_FragColor = vec4(color, 1.0);\n";
  	source += "}\n";
	return source;
}
