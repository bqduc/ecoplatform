var vnLanguageUrl = "//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Vietnamese.json";
var enLanguageUrl = "//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/English.json";

var contextPrefix = '/jobCategory/';
var currentLanguageUrl = vnLanguageUrl;
var dataTableObject;
var objectTableId = '#bizObjectDataTable';
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
	/*searchParamMap["searchName"] = 'Trần văn';
	searchParamMap["searchFromDate"] = $('#from-date').val();
	searchParamMap["searchToDate"] = $('#to-date').val();*/
	var data = {
			   "firstName" : $('#search-first-name').val() || '', 
			   "code" : $('#search-code').val() || '', 
			   "lastName" : $('#search-last-name').val() || '', 
			   //"phones" : "0",search-issue-date-from,search-issue-date-to
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
	$("#search-issue-date-from").datepicker({autoclose: true});
	$("#search-issue-date-to").datepicker({autoclose: true});
	
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
     	//"bServerSide": true,
		"sAjaxSource": listObjectsAction,
		"sAjaxDataProp": "",
		"order": [[ 0, "asc" ]],
		"aoColumns": [
				      {"mData": "id"},
			          {"mData": "name",
						  "render": function (data, type, row, meta){
							  return '<a href="'+row.id+'">' + data +'</a>'; 
						  } 
			          }, 
			          { "mData": "parent",
						  "render": function (data, type, row, meta){
							  var displayData = 'N/A Parent';
							  if (data != null && data.name != null){
								  displayData = data.name;
							  }
							  return '<a href="'+data.id+'">' + displayData +'</a>'; 
						  } 
			          }, 
			          {"mData": "info"}
		],
		"columns": [
		            { "width": "2%" },//Id
		            { "width": "30%" },//Name
		            { "width": "30%" },//Parent
		            { "width": "38%" }//Info
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