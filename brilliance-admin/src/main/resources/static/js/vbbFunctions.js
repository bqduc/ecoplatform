function searchForums() {
	var url = '/measureUnit/searchUnits';
	if ($('#searchPattern').val() != '') {
		url = url + '/' + $('#searchPattern').val();
	}
	//alert('Loaded url: ' + url);
	$("#resultsBlock").load(url);
}

function searchTopics(){
	alert('Come here. Searching topics !!!');
}

function searchThreads(){
	alert('Come here. Searching threads !!!');
}

function searchPosts(){
	alert('Come here. Searching posts !!!');
}