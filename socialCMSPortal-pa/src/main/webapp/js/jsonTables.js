function serializeJSON(partialUrl, paths, table, paginationSizes, paginationPage, currentPaginationSize) {
	var startFrom = (paginationPage - 1) *  currentPaginationSize;
		
	jQuery.ajax({
		url: '/socialCRMRest/' + partialUrl,
		type: 'GET',
		data: {'pagination': currentPaginationSize, 'startFrom': startFrom},
		dataType: "json",
		beforeSend: function() {
			getTableData(table).innerHTML = "<tr><td colspan='" + getTableCols (paths) + "' style='background-color: white; text-align: center;'><img src='/socialCMSPortal-pa/images/loading.gif'></img></td></tr>";
		},
		success: function (data) {
			var body = getTableData(table);
			// reset everything and remove the loading gif
			body.innerHTML = "";
			var tfoot = table.createTFoot(-1);
			tfoot.innerHTML = '';
			// callback to showing in html

			
			
			for(var dataCount = 0; dataCount < data.data.length; dataCount++) {
				var currentData = data.data[dataCount];
				serializationFunction(currentData, body, paths);
			}
			
			var totalObjects = data.totalObjects;
			var totalPages = Math.ceil(totalObjects/currentPaginationSize);
						
			addConnectionDataToTable(table, partialUrl, paths, paginationSizes, currentPaginationSize);
						
			createPagination(table, paginationSizes, paginationPage, totalPages, getTableCols (paths), currentPaginationSize);
			
		},
		failure: function (data) {
			getTableData(table).innerHTML = "<tr><td colspan='" + getTableCols (paths) + "' style='background-color: white; text-align: center;'>Unable to connect to the Social CRM Server.</td></tr>";
		}
	});
}

function createTableBody(table) {
	var body = table.createTBody();
	body.setAttribute('class', 'tableData');
	
	return body;
}

function getTableCols (paths) {
	return paths.length;
}

function getTableData(table) {
	return table.getElementsByClassName('tableData')[0];
}

// data in form:
// [Column Name 1, Column name 2]
function createTableHeader(columnNames, table) {
	var header = table.createTHead();
	var row = header.insertRow(0); 
	for (var columnId in columnNames) {
		var columnName = columnNames[columnId];
		var cell = row.insertCell(-1);
		cell.innerHTML = columnName;
	}
	
	
	return header;
}


// options:
// path: the path to parse
// obj: the object, from where we read the property
// defaultValue: if no value at the end of path, then use this value;
//
// example: getProperty({path: "company.name", obj: data.data[0], defaultValue: "N/A"}); 
function getProperty(options) {
	var default_args = {
			'defaultValue' : ""
	};
	
	for(var index in default_args) {
		if(typeof options[index] == "undefined") options[index] = default_args[index];
	}
	
	var path = options['path'];
	var obj = options['obj'];
	
	var splitted = path.split('.');
	var currentObject = obj;
	
	for(var splittedCounter = 0; splittedCounter < splitted.length; splittedCounter++) {
		var path = splitted[splittedCounter];
		if(typeof currentObject != 'undefined') {
			currentObject = currentObject[path];
		} else {
			return options['defaultValue'];
		}
	}

	return currentObject;
}

function serializationFunction(current, body, paths) {
	var row = body.insertRow(-1);

	for(var pathCounter = 0; pathCounter < paths.length; pathCounter++) {
		var path = paths[pathCounter];
		var cell = row.insertCell(-1);
		cell.innerHTML = getProperty({path: path, obj: current}); 
	}
}

// options
// table: id of the table
// headers: Headers such as ['First name', 'Last Name']
// uri: relative path to the rest service, such as 'person'
// pagination: the size of the pagination, default: 10
// paginationPage: the current page, starts from 1, default: 1
// paginationSizes: what pagination size options are allowed, default: [5,10,25,50]
// paths: what to take from each element, such as ['firstName', 'lastName', 'country.name']

function showTable(options) {
	console.log('Starting show tables');
	var default_args = {
			'pagination' : 10,
			'paginationPage' : 1,
			'paginationSizes' : [5,10,25,50]
	};
	
	for(var index in default_args) {
		if(typeof options[index] == "undefined") options[index] = default_args[index];
	}
	
	var tableId = options['table'];
	var headers = options['headers'];
	var uri = options['uri'];
	var paths = options['paths'];
	var currentPaginationSize = options['pagination'];
	var paginationPage = options['paginationPage'];
	var paginationSizes = options['paginationSizes'];
		
	var table = document.getElementById(tableId);
	
	createTableBody(table);
	
	table.classList.add('borderfull');
	
	createTableHeader(headers, table);
		
	serializeJSON(uri, paths, table, paginationSizes, paginationPage, currentPaginationSize);
}

function changePagination(paginationSize, table) {
	var uri = getData(table, 'uri');
	var paths = getData(table, 'paths');
	var paginationSizes = getData(table, 'paginationSizes');
	
	var currentPaginationSize = paginationSize;
	var paginationPage = 1;
	
	serializeJSON(uri, paths, table, paginationSizes, paginationPage, currentPaginationSize);
}

// pageNummber: number of the page, starting from 1
function gotoPage(pageNummber, table) {
	var uri = getData(table, 'uri');
	var paths = getData(table, 'paths');
	var paginationSizes = getData(table, 'paginationSizes');
	var currentPaginationSize = getData(table, 'currentPaginationSize');
	
	var paginationPage = pageNummber;
		
	serializeJSON(uri, paths, table, paginationSizes, paginationPage, currentPaginationSize);
}

function addConnectionDataToTable(table, uri, paths, paginationSizes, currentPaginationSize) {
	var tfoot = table.createTFoot(-1);
	var div = document.createElement('div');
	tfoot.appendChild(div);
	
	div.setAttribute('style', 'display: none;');
	
	addData(div, 'uri', uri);
	addData(div, 'paths', paths);
	addData(div, 'paginationSizes', paginationSizes);
	addData(div, 'currentPaginationSize', currentPaginationSize);
}



function addData(appendTo, key, value) {
	var type = "string";
	if(value instanceof Array) {
		type = 'array';
	}
	
	var div = document.createElement('div');
	div.setAttribute('class', key);
	div.setAttribute('type', type);
	div.innerHTML=value;
	appendTo.appendChild(div);
}

function getData(table, key) {
	console.log('getting key:' + key);
	var data = table.getElementsByClassName(key)[0];

	if(data.getAttribute('type') === 'array') {
		return data.innerHTML.split(",");
	} 
	
	return data.innerHTML;
}


function addPaginationSelector(table, paginationSizes, paginationPage, colspan, currentPaginationSize) {
	var tfoot = table.createTFoot(-1);
	var ftr = document.createElement('tr');
	tfoot.appendChild(ftr);
	var ftd = document.createElement('td');
	ftd.setAttribute("colspan", colspan);
	ftr.appendChild(ftd);
	var div = document.createElement('div');
	ftd.appendChild(div);
	div.classList.add('tablePager');
	var select = document.createElement('select');
	
	select.onchange = function() {
		var paginationSize = select.options[select.selectedIndex].value;
		changePagination(paginationSize, table);
	};
	
	for (var selectId in paginationSizes) {
		var paginationSize = paginationSizes[selectId];
		var option = new Option(paginationSize, paginationSize);
		
		if(paginationSize == currentPaginationSize) {
			option.setAttribute("selected", "selected");
		}
		
		option.onclick = function() {changePagination(paginationSize, table);};
		
		select.options.add(option);
	}
	
	var selectSpan = document.createElement('span');
	selectSpan.setAttribute("id", "selectSpan");
	selectSpan.appendChild(select);
	div.appendChild(selectSpan);
	
	return div;
}

function createPaginationElement(value, page, table, paginationSpan) {
	var paginationElement = document.createElement('input');
	paginationElement.value = value;
	paginationElement.classList.add('paginationElement');
	paginationElement.setAttribute("type", "button");
	
	paginationElement.onclick = function() {gotoPage(page, table);};
	
	paginationSpan.appendChild(paginationElement);
	
	return paginationElement;
}

function addPaginationPages(div, paginationPage, totalPages, table) {
	var paginationSpan = document.createElement('span');
	paginationSpan.setAttribute("id", "paginationSpan");
	paginationSpan.classList.add('paginationSpan');

	// dont show first button when on page 1
	if(paginationPage > 1) {
		createPaginationElement('<<', 1, table, paginationSpan);
		createPaginationElement('<', paginationPage - 1, table, paginationSpan);
	}

	for(var count = 1; count <= totalPages; count++) {
		var pag = createPaginationElement(count, count, table, paginationSpan);
		
		if(count == paginationPage) {
			pag.classList.add('paginationElementSelected');
		}
	}

	// dont show pagination on last page
	if(paginationPage != totalPages) {
		createPaginationElement('>', paginationPage+1, table, paginationSpan);
		createPaginationElement('>>', totalPages, table, paginationSpan);
	}


	div.appendChild(paginationSpan);
}

function createPagination(table, paginationSize, paginationPage, totalPages, tableRows, currentPaginationSize) {
	// add pagination selector
	var div = addPaginationSelector(table, paginationSize, paginationPage, tableRows, currentPaginationSize);


	// add pagination 
	addPaginationPages(div, paginationPage, totalPages, table);
}