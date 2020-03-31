/**
 * 
 */
var tokent;
$(document).ready(function() {
	tokent = getCookie("jwtToken");
	if (tokent == "") {
		if (window.location.href != "http://localhost:8080/login")
			window.location.href = "/login.html";
	} else {
		if (window.location.href == "http://localhost:8080/login") {
		} else {
			loadAllStocks();
		}
	}

});

window.onload = function() {
	document.getElementById('loginFormBtn').onclick = function() {
		authenticate();
	};
};
function authenticate() {
	var xhttp = new XMLHttpRequest();
	var username_name = document.getElementById("username").value;
	var password = document.getElementById("password").value;
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			prod = JSON.parse(this.responseText);
			tokent = "Bearer " + prod.jwt;
			setCookie("jwtToken", tokent);
			window.location.href = '../';
		}
	};
	xhttp.open("POST", "http://localhost:8080/authenticate", true);
	xhttp.setRequestHeader("Content-type", "application/json");
	xhttp.send(JSON.stringify({
		"username" : username_name,
		"password" : password
	}));
}

function getStockDetails(event) {
	var stockId = event.target.id;
	fetchEventById(stockId);
}

function serviceSuccessClose() {
	loadAllStocks();
	document.getElementById("stockname").innerHTML = "";
	document.getElementById("stockTime").innerHTML = "";
	document.getElementsByClassName("stockAmountdtl")[0].value = "";
	document.getElementById("addNewDetalsDialog").style.display = "none";
	document.getElementById("stockDetailsDialog").style.display = "none";
	document.getElementById("historyDetalsDialog").style.display = "none";
	document.getElementById("stockPriceUpdate").innerHTML = "";
	document.getElementById("created").innerHTML = "";
}

function fetchEventById(stockId) {
	var url = "http://localhost:8080/api/stocks/"
	url = url + stockId;
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			prod = JSON.parse(this.responseText);
			document.getElementById("stockDetailsDialog").style.display = "block";
			var details = prod;
			var d = new Date(details.timestamp);
			document.getElementById("stockname").innerHTML = details.name;
			document.getElementById("stockTime").innerHTML = d;
			document.getElementsByClassName("stockAmountdtl")[0].value = details.amount;
			document.getElementsByClassName("stockAmountdtl")[0].id = "amt"
					+ details.id;
		}
	};
	xhttp.open("GET", url, true);
	xhttp.setRequestHeader("Content-type", "application/json");
	xhttp.setRequestHeader('Authorization', tokent);
	xhttp.send();

}

function loadAllStocks() {
	var url = "http://localhost:8080/api/stocks";
	var xhttp = new XMLHttpRequest();
	var ulList = document.getElementById("allStocksUL");
	ulList.innerHTML = "";
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			prod = JSON.parse(this.responseText);
			for (var i = 0; i < prod.length; i++) {
				var each = prod[i];
				var li = document.createElement("li");
				var aTag = document.createElement('a');
				aTag.setAttribute('id', each.id);
				aTag.setAttribute('href', 'javascript:void(0);');
				aTag.innerText = each.name;
				aTag.setAttribute('class', 'stockLink')
				aTag.onclick = function(e) {
					getStockDetails(e);
				};
				li.appendChild(aTag);
				var hTag = document.createElement('a');
				hTag.setAttribute('id', each.id);
				hTag.setAttribute('href', 'javascript:void(0);');
				hTag.innerText = "Show history";
				hTag.setAttribute('class', 'historyLink')
				hTag.onclick = function(e) {
					fetchHistoryById(e);
				};
				li.appendChild(hTag);
				ulList.appendChild(li);
			}
		}
	};

	xhttp.open("GET", url, true);
	xhttp.setRequestHeader("Content-type", "application/json");
	xhttp.setRequestHeader('Authorization', tokent);
	xhttp.send();
}

function serviceUpdate() {
	var url = "http://localhost:8080/api/stocks/"
	var stockId = document.getElementsByClassName("stockAmountdtl")[0].id;
	var amount = document.getElementsByClassName("stockAmountdtl")[0].value;
	stockId = stockId.split("amt")[1];
	url = url + stockId;
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 204) {
			document.getElementById("stockPriceUpdate").innerHTML = "Stock Price updated successfully";
		} else if (status != 204) {
			document.getElementById("stockPriceUpdate").innerHTML = "Failed to update stock price.Please try again after some times";
		}
	};
	xhttp.open("PUT", url, true);
	xhttp.setRequestHeader("Content-type", "application/json");
	xhttp.setRequestHeader('Authorization', tokent);
	xhttp.send(JSON.stringify({
		"amount" : amount
	}));

}

function createStock() {
	var xhttp = new XMLHttpRequest();
	var stock_name = document.getElementById("stocknameedit").value;
	var amount = document.getElementById("stockAmountEdit").value
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 201) {
			document.getElementById("created").innerHTML = "The stock has been created";
		}
	};
	xhttp.open("POST", "http://localhost:8080/api/stocks", true);
	xhttp.setRequestHeader("Content-type", "application/json");
	xhttp.setRequestHeader('Authorization', tokent);
	xhttp.send(JSON.stringify({
		"name" : stock_name,
		"amount" : amount
	}));
}

function addNewStock() {
	document.getElementById("addNewDetalsDialog").style.display = "block";
	document.getElementById("stocknameedit").value = "";
	document.getElementById("stockAmountEdit").value = "";

}

function fetchHistoryById(event) {
	var url = "http://localhost:8080/api/stocks/"
	url = url + event.target.id + "/history";
	var xhttp = new XMLHttpRequest();
	var detailsTable = document.getElementsByClassName("detailsTable")[0];
	var rowCount = detailsTable.rows.length;
	for (var x = rowCount - 1; x > 0; x--) {
		detailsTable.deleteRow(x);
	}
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			var prod = JSON.parse(this.responseText);
			document.getElementById("historyDetalsDialog").style.display = "block";
			if (prod.length == 0) {
				document.getElementsByClassName("detailsTable")[0].display = "none";
				document.getElementById("created").innerHTML = "No historical data";
				return;
			}
			for (var i = 0; i < prod.length; i++) {
				var details = prod[i];
				var tr = document.createElement('tr');
				var td1 = document.createElement('td');
				var td2 = document.createElement('td');
				var d = new Date(details.timestamp);
				td1.innerHTML = details.amount;
				td2.innerHTML = d;
				tr.append(td1);
				tr.append(td2);
				detailsTable.append(tr);
			}
		}
	};
	xhttp.open("GET", url, true);
	xhttp.setRequestHeader("Content-type", "application/json");
	xhttp.setRequestHeader('Authorization', tokent);
	xhttp.send();

}

function setCookie(cname, cvalue) {
	var d = new Date();
	d.setTime(d.getTime() + (1000 * 60 * 60 * 10));
	var expires = "expires=" + d.toUTCString();
	document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
}

function getCookie(cname) {
	var name = cname + "=";
	var decodedCookie = decodeURIComponent(document.cookie);
	var ca = decodedCookie.split(';');
	for (var i = 0; i < ca.length; i++) {
		var c = ca[i];
		while (c.charAt(0) == ' ') {
			c = c.substring(1);
		}
		if (c.indexOf(name) == 0) {
			return c.substring(name.length, c.length);
		}
	}
	return "";
}
