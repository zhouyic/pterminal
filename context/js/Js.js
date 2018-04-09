function getParameter(argName){
//	alert(document.location.search);
	var tmp=document.location.search;
	var value="";
	if(tmp==null){
		return value;
	}
	
	var tmp1=tmp.substring(1);
	var tmp2=tmp1.split("&");
	
	for(var i=0;i<tmp2.length;i++){
		var tmp3=tmp2[i].split("=");
		if(tmp3.length<=1){
			continue;
		}
		if(tmp3[0]==argName){
			value=tmp3[1];
		}
		
	}
	return value;
}