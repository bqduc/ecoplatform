function searchDepartments(){
	var url = '/department/search';
	if ($('#searchPattern').val() != '') {
		url = url + '/' + encodeURIComponent($('#searchPattern').val());
	}
	//alert('Loaded url: ' + url);
	$("#resultsBlock").load(url);
}