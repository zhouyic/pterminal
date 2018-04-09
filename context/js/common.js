function gourl(pageurl){
parent.location.href=pageurl
}

function getWidth(){
	return document.body.clientWidth;    
}
function getHeight(){
	return document.body.clientHeight;
}

function check_null(check_obj){
	var str = '';
	for(i = 0; i < check_obj.length; i++) {
	    str = str + ' ';
	}
	return (str == check_obj);
}
function check_deviceID(check_obj){
	var tmp,j,strTemp;
	strTemp="1234567890abcdefABCDEF";
	if( check_obj.length != 12 ){
	  return false;
	}
	for (i=0;i<12;i++){
	  j=strTemp.indexOf(check_obj.charAt(i));
	  if(j==-1){
	       return false;
	  }
	}	   
	return true;
}
function check_mac(check_obj){
	var tmp,j,strTemp;
	strTemp="1234567890abcdefABCDEF";
	if( check_obj.length != 17 ){
	  return false;
	}
	for (i=0;i<17;i++){
	  if(i%3==2){    
	     if(check_obj.charAt(i) !=':'){
	        return false;
	     }
	  }else{
	    j=strTemp.indexOf(check_obj.charAt(i));
	    if(j==-1){
	       return false;
	    }
	  }	   
	}
	return true;
}

function check_same(check_obj1,check_obj2){
	return ( check_obj1 == check_obj2);
}
function check_num(check_obj){
    var i,j,strTemp,tmp;
    strTemp="0123456789";

    if ( check_obj.length== 0)
        return false;
	if(check_obj.charAt(0) == '-') {
		tmp = check_obj.substr(1);
	}else {
	    tmp = check_obj;
    }
    for (i=0;i<tmp.length;i++)
    {
     j=strTemp.indexOf(tmp.charAt(i));
     if (j==-1)
     {
         return false;
     }
    }
    return true;
}

function check_ab(check_obj){

    var i,j,strTemp;
    
    strTemp="abcdefghijklmnopqrstuvwxyz";
    if ( check_obj.length== 0)
        return false;
    for (i=0;i<check_obj.length;i++)
    {
     j=strTemp.indexOf(check_obj.charAt(i));
     if (j==-1)
     {
         return false;
     }
    }
    return true;
}

function check_chs(check_obj){
    if(check_obj.search(/[^\x00-\x80]/)>=0){
     return true;
    }else{
        return false;
    }
     
}

function check_bit(check_obj,bit_obj){
arry_str = bit_obj.split('_');
var obj_length = check_obj.length;

switch(arry_str[2]){
case ">":
        if(obj_length>arry_str[1]){
         return true;
        }
        break;
case "<":
        if(obj_length<arry_str[1]){
         return true;
        }
        break;
case ">=":
        if(obj_length>=arry_str[1]){
         return true;
        }
        break;
case "<=":
        if(obj_length<=arry_str[1]){
         return true; 
        }
        break;
}
return false;
}

function check_phone(check_obj){
	if(check_null(check_obj)){
		return true;
	}
	if(check_obj.search(/(\d)+(\-)*(\d)+/)>=0){
		return true;
	}else{
    		return false;
    	}
}

function check_email(check_obj){    
	if(check_null(check_obj)){
		return true;
	}
  if(check_obj.search(/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/)>=0){
        return true;
     }else{
        return false;
     }
}

function check_http(check_obj){
	if(check_null(check_obj)){
		return true;
	}
    if(check_obj.search(/^https?:\/\/((\w|-)+\.)+\w+\/?$/i)>=0){
        return true;
        }else{
        return false;
        }
}

function check_ip(check_obj){    
	if(check_null(check_obj)){
		return true;
	}
    if(check_obj.search(/(\d+)\.(\d+)\.(\d+)\.(\d+)/g)>=0){
        return true;
     }else{
        return false;
     }
}

function check_num_dot(check_obj){
    var i,j,strTemp;
    
    strTemp="1234567890.";
    if ( check_obj.length== 0)
        return false;
    for (i=0;i<check_obj.length;i++)
    {
     j=strTemp.indexOf(check_obj.charAt(i));
     if (j==-1)
     {
         return false;
     }
    }
    return true;
}
        
function onover(obj){
	obj.className = "onover";
}

function onout(obj){
	obj.className = "onout";
}

function checkAllBox(obj,name,parentId){
	if (obj.name == name) {//点击全部按钮
		var checked = obj.checked;
		var trElement = document.getElementById(parentId);
		var cbox = trElement.getElementsByTagName("input");
		for (var i = 0; i < cbox.length - 1; i++) {
			(document.getElementsByName(name+"$" + i))[0].checked = checked;
		}
	}
}

function checkAllProBox(obj,parentId){
	//alert("obj.checked="+obj.checked);
	var checked = obj.checked;
	var parent = document.getElementById(parentId);
	//alert("parent:"+parent.innerHTML);
	var cbox = parent.getElementsByTagName("input");
	//alert("cbox:"+cbox.length);
	for (var i = 0; i < cbox.length; i++) {
	//	alert(cbox[i].checked);
		if(cbox[i].type == "checkbox")
		cbox[i].checked = checked;
	}
}