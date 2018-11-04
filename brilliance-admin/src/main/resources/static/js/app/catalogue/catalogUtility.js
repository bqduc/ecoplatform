var vnLanguageUrl = "//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Vietnamese.json";
var enLanguageUrl = "//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/English.json";

var currentLanguageUrl = vnLanguageUrl;

var globalTable;

function refreshTable(tableId, urlData){
	var dataParam = new Object();
	dataParam.objectName = '#search-term';
	dataParam.location = '#search-location';
	dataParam.radius = '#search-distance';
	dataParam.platform = '#search-platform';
	dataParam.fromDate = '#from-date';
	dataParam.toDate = '#to-date';
	$.ajax({
		url : urlData,
		method : 'POST',
		data : JSON.stringify(dataParam),
		contentType : 'application/json',
		success : function(data) {
			var parsedJSON = JSON.parse(data);
			//console.log('Response data: ' + parsedJSON.length);
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
	
	/*$.getJSON(urlData, dataParam, function( json ){
		alert('inside: ' + json);
	    var table = $(tableId).dataTable();
	    var oSettings = table.fnSettings();
	    
	    table.fnClearTable(this);
	
	    for (var i=0; i<json.length; i++){
	      table.oApi._fnAddData(oSettings, json[i]);
	    }
	
	    oSettings.aiDisplay = oSettings.aiDisplayMaster.slice();
	    table.fnDraw();
	});*/
}

function searchDepartments(){
	var url = '/department/search';
	if ($('#searchPattern').val() != '') {
		url = url + '/' + encodeURIComponent($('#searchPattern').val());
	}
	console.log('Loaded url: ' + url);
	$("#resultsBlock").load(url);
}

function searchCatalogues(){
	var url = '/catalog/search';
	if ($('#searchPattern').val() != '') {
		url = url + '/' + encodeURIComponent($('#search-term').val());
	}
	console.log('Catalog loaded url: ' + url);
	$("#cataloguesTable").load(url);
}

$(document).ready( function () {
	var dataTableObject = $('#cataloguesTable').dataTable({
	 	"language": {
	 		"url": currentLanguageUrl
     	},
		"sAjaxSource": "listCatalogues",
		"sAjaxDataProp": "",
		"order": [[ 0, "asc" ]],
		"aoColumns": [
		      { "mData": "id", "width": "2%"},
	          { "mData": "code",
				  "render": function (data, type, row, meta){
					  return '<a href="'+row.id+'">' + data +'</a>'; 
				  } 
	          },
	          { "mData": "name" },
			  { "mData": "translatedName" },
			  { "mData": "issueDate", 
				  render: function (d){
					  return moment(d).format( 'DD/MM/YYYYY HH:mm' );
				  }
			  }
		],
		"columns": [
		            { "width": "2%" },//Id
		            { "width": "5%" },//Code
		            { "width": "8%" },//Name
		            { "width": "10%" },//Translated name
		            { "width": "10%" }//Description
		          ]
	});

	$("#from-date").datepicker({autoclose: true});
	$("#to-date").datepicker({autoclose: true});
	$('#submit').on('click', function(e){
		e.preventDefault();

		var page = $('#page').val() || null;
		var size = $('#page-size').val() || null;
		refreshTable('#cataloguesTable', '/catalog/searchAction?size='+size+'&page='+page);
		/*var data = new Object();
		data.objectName = $('#search-term').val() || null;
		data.location = $('#search-location').val() || null;
		data.radius = $('#search-distance').val() || null;
		data.platform = $('#search-platform').val() || null;
		data.fromDate = $('#from-date').val() || null;
		data.toDate = $('#to-date').val() || null;
		var page = $('#page').val() || null;
		var size = $('#page-size').val() || null;
		$.ajax({
			url : '/catalog/searchAction?size='+size+'&page='+page,
			method : 'POST',
			data : JSON.stringify(data),
			contentType : 'application/json',
			success : function(data) {
				alert('Done');
				console.log(data);
				$('#result-table').replaceWith(data);
			}
		});	*/
	});
});