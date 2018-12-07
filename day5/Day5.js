var fs = require("fs");

fs.readFile("input_day5.txt", (err, data) =>{
	if(err) throw err;
	console.log("Start length");
	console.log(data.toString().length-1);
	
	// Part 1
	var input = data.toString();
	var prev = 0;
	while(input.length != prev){
		prev = input.length;
		'abcdefghijklmnopqrstuvwxyz'.split('').forEach( (e) => {
			input = input.replace(e + e.toUpperCase(), "");
			input = input.replace(e.toUpperCase() + e, "");
		});
	}
	console.log("Part 1:");
	console.log(input.length-1);
	
	// Part 2
	var min = -1;
	'abcdefghijklmnopqrstuvwxyz'.split('').forEach( (e1) => {
		input = data.toString().replaceAll(e1,"").replaceAll(e1.toUpperCase(),"");
		prev = 0;
		while(input.length != prev){
			prev = input.length;
			'abcdefghijklmnopqrstuvwxyz'.split('').forEach( (e) => {
				input = input.replace(e + e.toUpperCase(), "");
				input = input.replace(e.toUpperCase() + e, "");
			});
		}
		if(min === -1 || min > input.length-1){
			min = input.length-1;
		}
	});
	console.log("Part 2:");
	console.log(min);
});

String.prototype.replaceAll = function(search, replacement) {
    var target = this;
    return target.split(search).join(replacement);
};