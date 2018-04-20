
var comm = new Common();

function Common(){
	
	this.call = function(){
		alert('callFunc');
		callService('call!');
	}
}

function callback(result){
	alert(result);
}