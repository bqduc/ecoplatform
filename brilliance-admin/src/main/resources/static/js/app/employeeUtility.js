var vnLanguageUrl = "//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Vietnamese.json";
var enLanguageUrl = "//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/English.json";

var currentLanguageUrl = vnLanguageUrl;
var dataTableObject;

function refreshTable(tableId, urlData){
	var dataParam = new Object();
	dataParam.objectName = $('#search-term').val() || '';
	dataParam.location = $('#search-location').val() || '';
	dataParam.radius = $('#search-distance').val() || '';
	dataParam.platform = $('#search-platform').val() || '';
	dataParam.fromDate = $('#from-date').val() || '';
	dataParam.toDate = $('#to-date').val() || '';
	$.ajax({
		url : urlData,
		method : 'POST',
		data : JSON.stringify(dataParam),
		contentType : 'application/json',
		success : function(data) {
			var parsedJSON = JSON.parse(data);
		    var table = $(tableId).dataTable();
		    var oSettings = table.fnSettings();
		    
		    table.fnClearTable(this);
		
		    for (var i=0; i < parsedJSON.length; i++){
		      table.oApi._fnAddData(oSettings, parsedJSON[i]);
		    }
		
		    oSettings.aiDisplay = oSettings.aiDisplayMaster.slice();
		    table.fnDraw();
		}
	});	
}

$(document).ready( function () {
	$('.pull-left').click(function (){
		alert('pull-leftpull-leftpull-leftpull-leftpull-left!!');
	});
	$('#app-locale-english, #app-locale-english-info').click(function (){
		if (dataTableObject != null){
			dataTableObject.fnDestroy();
			dataTableObject = null;
		}
	    dataTableObject = $('#employeesTable').dataTable( {"url": enLanguageUrl} );
	});

	$('#app-locale-vietnamese, #app-locale-vietnamese-info').click(function (){
		if (dataTableObject != null){
			dataTableObject.fnDestroy();
			dataTableObject = null;
		}
	    dataTableObject = $('#employeesTable').dataTable( {"url": vnLanguageUrl} );
	});
	$('#app-locale-french, #app-locale-french-info').click(function (){
		alert('Change locale to French!!');
	});


	dataTableObject = $('#employeesTable').dataTable({
	 	"language": {
	 		"url": currentLanguageUrl
     	},
     	"pageLength":"50",
		"sAjaxSource": "/listEmployees",
		"sAjaxDataProp": "",
		"order": [[ 0, "asc" ]],
		"aoColumns": [
		      { "mData": "id", "width": "2%"},
	          { "mData": "code",
		    	  "width": "5%",
				  "render": function (data, type, row, meta){
					  return '<a href="'+row.id+'">' + data +'</a>'; 
				  } 
	          },
	          { "mData": "firstName" },
			  { "mData": "lastName" },
			  { "mData": "email" },
			  { "mData": "phone" },
			  { "mData": "active"}
		],
		"columns": [
		            { "width": "2%" },//Id
		            { "width": "5%" },//Code
		            { "width": "8%" },//First name
		            { "width": "10%" },//Last name
		            { "width": "5%" },//Email
		            { "width": "5%" },//Phone
		            { "width": "5%" }//Status
		          ]
	});
	
	$(".spoiler-trigger").click(function() {
		$(this).parent().next().collapse('toggle');
	});

	$("#from-date").datepicker({autoclose: true});
	$("#to-date").datepicker({autoclose: true});
	$('#searchObjects').on('click', function(e){
		e.preventDefault();

		var page = $('#page').val() || null;
		var size = $('#page-size').val() || null;
		refreshTable('#employeesTable', '/employee/searchEmployee?size='+size+'&page='+page);
	});
});