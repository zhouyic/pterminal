<?xml version="1.0" encoding="GB2312"?>
<!DOCTYPE script PUBLIC
	"-//Apache Software Foundation//Tapestry Script Specification 3.0//EN"
	"http://jakarta.apache.org/tapestry/dtd/Script_3_0.dtd">
<script>
  <body>
<![CDATA[ 
		function layout()
		{
			var mainTable=document.getElementById("mainTable");
			if(mainTable!=null){
				mainTable.cssText="height=1000";
 				//window.alert("ddd");
 			}
		}

]]>
  </body>
  <initialization>
    layout();
  </initialization>
</script>
