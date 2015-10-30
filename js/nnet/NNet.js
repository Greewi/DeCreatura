(function(){

if(!window.nnet)
	window.nnet={};

/**
 * La fonction sigmoid
 */
nnet.sigmoid = function(x){
	return 1.0 / (1.0 + Math.exp(-x));
};

})();