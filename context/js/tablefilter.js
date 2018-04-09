$(function() { 
  var theTable = $('#filterbody')

  theTable.find("tr").find("td:eq(1)").mousedown(function(){
    $(this).prev().find(":checkbox").click()
  });

  $("#filter").keyup(function() {
    $.uiTableFilter( theTable, this.value );
  })

  $('#filter-form').submit(function(){
    theTable.find("tr:visible > td:eq(1)").mousedown();
    return false;
  }).focus(); //Give focus to input field
});  
