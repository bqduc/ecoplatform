var vnLanguageUrl = "//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Vietnamese.json";
var enLanguageUrl = "//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/English.json";

var contextPrefix = '/adminDashboard/';
var currentLanguageUrl = vnLanguageUrl;
var dataTableObject;
var objectTableId = '#dashboardsTable';
var searchContextUri = contextPrefix + 'search';
var listObjectsAction = contextPrefix + 'list';
function refreshTable(tableId, urlData){
	var dataParam = new Object();
	dataParam.objectName = $('#search-term').val() || '';
	dataParam.location = $('#search-location').val() || '';
	dataParam.radius = $('#search-distance').val() || '';
	dataParam.platform = $('#search-platform').val() || '';
	dataParam.fromDate = $('#from-date').val() || '';
	dataParam.toDate = $('#to-date').val() || '';
	
	var searchParamMap = {};
	var data = {
			   "firstName" : $('#search-first-name').val() || '', 
			   "code" : $('#search-code').val() || '', 
			   "lastName" : $('#search-last-name').val() || '', 
			   "dateOfBirthFrom": $('#from-date').val(),
			   "dateOfBirthTo": $('#to-date').val()
			};
	dataParam.parameterMap = data;
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
	$('#app-locale-english, #app-locale-english-info').click(function (){
		if (dataTableObject != null){
			dataTableObject.fnDestroy();
			dataTableObject = null;
		}
	    dataTableObject = $(objectTableId).dataTable( {"url": enLanguageUrl} );
	});

	$('#app-locale-vietnamese, #app-locale-vietnamese-info').click(function (){
		if (dataTableObject != null){
			dataTableObject.fnDestroy();
			dataTableObject = null;
		}
	    dataTableObject = $(objectTableId).dataTable( {"url": vnLanguageUrl} );
	});
	$('#app-locale-french, #app-locale-french-info').click(function (){
		alert('Change locale to French!!');
	});

	dataTableObject = $(objectTableId).dataTable({
	 	"language": {
	 		"url": currentLanguageUrl
     	},
     	"pageLength":"50",
		"sAjaxSource": listObjectsAction,
		"sAjaxDataProp": "",
		"order": [[ 0, "asc" ]],
		"aoColumns": [
		      {"mData": "id", "width": "2%"},
	          {"mData": "serial",
		    	  "width": "5%",
				  "render": function (data, type, row, meta){
					  return '<a href="'+row.id+'">' + data +'</a>'; 
				  } 
	          }, 
	          {"mData": "name"},
	          {"mData": "translatedName"},
	          {"mData": "activated"},
	          {"mData": "visible"}
		],
		"columns": [
		            { "width": "2%" },//Id
		            { "width": "8%" },//Code
		            { "width": "30%" },//Name
		            { "width": "30%" },//Translated name
		            { "width": "5%" },//Activated
		            { "width": "5%" } //Visible
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
		refreshTable(objectTableId, searchContextUri + '?size='+size+'&page='+page);
	});
});