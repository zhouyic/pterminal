
function dragZoomEnd(id){
			document.getElementById(id).style.cursor="default";
	    		document.getElementById(id).releaseCapture(); 
			window.event.cancelBubble = true;
			document.getElementById(id).onmousemove="";
			document.getElementById("gensui").style.display="none";
		}
		
	


function change(time){

		var hhour=time/3600;
	
		var  mminute=(time%3600)/60;
	
		var ssecond=(time%3600)%60;

		var tmp=parseInt(hhour,10)+":"+parseInt(mminute,10)+":"+parseInt(ssecond,10);
		return tmp;
}


function zoomout(){
	var tmpChild=document.getElementById("subTask").childNodes;
	for(var j=0;j<tmpChild.length;j++){
		var orginalWidth=parseInt(tmpChild[j].style.width,10);
	if(parseInt(tmpChild[j].style.width,10)<=86400){
		var tmp=parseInt(tmpChild[j].style.width,10)+3600;
			tmpChild[j].style.width=tmp>86400?86400:tmp;


			//子div的扩大
		var child=tmpChild[j].childNodes;
		for(var i=0;i<child.length;i++){
			var wid=(parseInt(child[i].style.width,10)/orginalWidth)*parseInt(tmpChild[j].currentStyle.width,10);
			child[i].style.width=wid;
			}
		for(var i=1;i<child.length;i++){
			document.getElementById(child[i].id).style.left=parseInt((document.getElementById(child[i-1].id).style.left).replace("px",""),10)+parseInt((document.getElementById(child[i-1].id).style.width).replace("px",""),10);
			}
		}
	}
	
	
}


function zoomin(){
		var tmpChild=document.getElementById("subTask").childNodes;
	for(var j=0;j<tmpChild.length;j++){
		var orginalWidth=parseInt(tmpChild[j].style.width);

	if(parseInt(tmpChild[j].style.width)>=3600){
			var tmp=parseInt(tmpChild[j].style.width)-3600;
			tmpChild[j].style.width=tmp<500?500:tmp;


			//子div的缩放
			var child=tmpChild[j].childNodes;
			for(var i=0;i<child.length;i++){
				var wid=(parseInt(child[i].style.width)/orginalWidth)*parseInt(tmpChild[j].currentStyle.width);
				child[i].style.width=wid;
				}
			for(var i=1;i<child.length;i++){
				document.getElementById(child[i].id).style.left=parseInt((document.getElementById(child[i-1].id).style.left).replace("px",""))+parseInt((document.getElementById(child[i-1].id).style.width).replace("px",""));
				}
		}
	
	}
	
	
}