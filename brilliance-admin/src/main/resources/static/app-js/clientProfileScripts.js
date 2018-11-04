function performSearchObjects() {
	console.log('Search client profiles. ');
	var url = '/client/search';
	if ($('#searchPattern').val() != '') {
		url = url + '/' + encodeURIComponent($('#searchPattern').val());
	}
	$("#resultsBlock").load(url);
	console.log('Leave client profiles');
}