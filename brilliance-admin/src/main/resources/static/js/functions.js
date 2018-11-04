function retrieveMeasureUnits() {
	var url = '/measureUnit/searchUnits';
	if ($('#searchPattern').val() != '') {
		url = url + '/' + $('#searchPattern').val();
	}
	//alert('Loaded url: ' + url);
	$("#resultsBlock").load(url);
}
