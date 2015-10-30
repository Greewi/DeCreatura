
var Et = new nnet.CoucheNeuronnes([[-150.0, 100.0, 100.0]]);
var NonEt = new nnet.CoucheNeuronnes([[150.0, -100.0, -100.0]]);
var Ou = new nnet.CoucheNeuronnes([[-50.0, 100.0, 100.0]]);

console.log(Et.toString());
console.log(numeric.prettyPrint(Et.calculeActivation([0, 0])));
console.log(numeric.prettyPrint(Et.calculeActivation([0, 1])));
console.log(numeric.prettyPrint(Et.calculeActivation([1, 0])));
console.log(numeric.prettyPrint(Et.calculeActivation([1, 1])));

console.log(NonEt.toString());
console.log(numeric.prettyPrint(NonEt.calculeActivation([0, 0])));
console.log(numeric.prettyPrint(NonEt.calculeActivation([0, 1])));
console.log(numeric.prettyPrint(NonEt.calculeActivation([1, 0])));
console.log(numeric.prettyPrint(NonEt.calculeActivation([1, 1])));

console.log(Ou.toString());
console.log(numeric.prettyPrint(Ou.calculeActivation([0, 0])));
console.log(numeric.prettyPrint(Ou.calculeActivation([0, 1])));
console.log(numeric.prettyPrint(Ou.calculeActivation([1, 0])));
console.log(numeric.prettyPrint(Ou.calculeActivation([1, 1])));

var XOr = new nnet.ReseauNeuronnes([
	new nnet.CoucheNeuronnes([
		[-50.0, 100.0, 100.0],
		[150.0, -100.0, -100.0]
	]),
	new nnet.CoucheNeuronnes([
		[-150.0, 100.0, 100.0]
	]),
]);

console.log(XOr.toString());
console.log(numeric.prettyPrint(XOr.calculeActivation([0, 0])));
console.log(numeric.prettyPrint(XOr.calculeActivation([0, 1])));
console.log(numeric.prettyPrint(XOr.calculeActivation([1, 0])));
console.log(numeric.prettyPrint(XOr.calculeActivation([1, 1])));

