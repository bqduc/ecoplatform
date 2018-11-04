var vnLanguageUrl = "//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Vietnamese.json";
var enLanguageUrl = "//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/English.json";

var contextPrefix = '/backjob/';
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

function startJobSchedule(jobInfo){
	console.log('Start job schedule: ' + jobInfo);
}

function pauseJobSchedule(jobInfo){
	console.log('Pause job schedule: ' + jobInfo);
}

function resumeJobSchedule(jobInfo){
	console.log('Resume job schedule: ' + jobInfo);
}

function stopJobSchedule(jobInfo){
	console.log('Stop job schedule: ' + jobInfo);
}

function rescheduleJobSchedule(jobInfo){
	console.log('Reschedule job schedule: ' + jobInfo);
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
		      {"mData": "id", "width": "2%"},
	          {"mData": "code",
		    	  "width": "5%",
				  "render": function (data, type, row, meta){
					  return '<a href="'+row.id+'">' + data +'</a>'; 
				  } 
	          }, 
	          { "mData": "jobCategory",
		    	  "width": "15%",
				  "render": function (data, type, row, meta){
					  var displayData = 'N/A Category';
					  if (data != null && data.name != null){
						  displayData = data.name;
					  }
					  return '<a href="'+row.id+'">' + displayData +'</a>'; 
				  } 
	          }, 
	          { "mData": "jobExecutionClass",
		    	  "width": "15%",
				  "render": function (data, type, row, meta){
					  var startImage = '<img src="/dist/img/play.png" width="15px" class="img-circle" alt="Start schedule job" />';
					  var pauseImage = '<img src="/dist/img/pause.png" width="15px" class="img-circle" alt="Start schedule job" />';
					  var resumeImage = '<img src="/dist/img/resume-x.png" width="20px" height="20px" class="img-circle" alt="Start schedule job" />';
					  var shutdownImage = '<img src="/dist/img/shutdown.png" width="15px" class="img-circle" alt="Start schedule job" />';
					  var settingsImage = '<img src="/dist/img/settings.png" width="15px" class="img-circle" alt="Start schedule job" />';
					  var actions = 
						  '<a href="javascript:startJobSchedule('+row.id+')">' + startImage +'</a> ' +
						  '<a href="javascript:pauseJobSchedule('+row.id+')">' + pauseImage +'</a> '+
						  '<a href="javascript:resumeJobSchedule('+row.id+')">' + resumeImage +'</a>'+
						  '<a href="javascript:stopJobSchedule('+row.id+')">' + shutdownImage +'</a>'+
						  '<a href="javascript:rescheduleJobSchedule('+row.id+')">' + settingsImage +'</a>';
					  return actions; 
				  } 
	          }, 
	          {"mData": "triggerName"},
	          {"mData": "cronTimeExpression"},
	          //{"mData": "jobExecutionClass"},
	          /*{"mData": "started"},
	          {"mData": "finished"},
	          */
	          {"mData": "status"},
	          {"mData": "activated"}
		],
		"columns": [
		            { "width": "2%" },//Id
		            { "width": "8%" },//Code
		            { "width": "15%" },//Code
		            { "width": "30%" },//Trigger Name
		            { "width": "30%" },//Cron-time expression
		            { "width": "15%" },//Execution class
		            //{ "width": "5%" },//Started
		            //{ "width": "5%" },//Finished
		            { "width": "5%" },//Status
		            { "width": "5%" } //Activated
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