<?xml version="1.0" encoding="GB2312"?>
<!DOCTYPE script PUBLIC
	"-//Apache Software Foundation//Tapestry Script Specification 3.0//EN"
	"http://jakarta.apache.org/tapestry/dtd/Script_3_0.dtd">
<script>
  <body>
<![CDATA[ 
		function showtime()
		{
			var now;
			var year, month, date, hour, minute, second
			now = new Date(); 
			year = now.getYear();
			month = now.getMonth()+1;
			date = now.getDate();
			hour = now.getHours();
			if(hour < 10) {
				hour = "0" + hour;
			}
			minute = now.getMinutes();
			if(minute < 10) {
				minute = "0" + minute;
			}
			second = now.getSeconds();
			if(second < 10) {
				second = "0" + second;
			}
			var vall = year + "年" + month + "月" + date + "日" + hour + ":" + minute + ":" + second; 
			time.innerHTML = vall;
			setTimeout("showtime()", 1000); 
		}

]]>
  </body>
  <initialization>
    showtime();
  </initialization>
</script>
