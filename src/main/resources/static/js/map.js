var OPENBALLOON_URI = "";

// Funktionen
var heatmap = false;
var trajektorien = false;
var eventname = "none";

// Trajektorien
var set_trajek_ajf = false;
var set_trajek_hochzeit = false;
var set_trajek_infotag = false;
var set_trajek_infotag2 = false;
var set_trajek_sommerfest = false;
var set_trajek_wima1 = false;
var set_trajek_wima2 = false;

// Map Events View
// Biblis ==> setView([49.688137, 8.453191], 8)
// Mitte Deutschland ==> setView([51.163375, 10.447683], 7)
var MAP_LAT = 49.688137;
var MAP_LON = 8.453191;
var MAP_ZOOM = 8;

// Layer
var markers_events;
var marker_event;
var markers_finds;
var heatmapFinds;

var map;
var zoomControl;
var measureControl;
var markerIconEvent;
var markerIconFind;
var markerIconFind2;
var events_control;
var heatmap_control;
var balloons_control;
var trajektorien_control;
var trajektorien_control_legend;
var trajektorien_control_legend2;
var trajektorien_control_legend3;

function search() {
	if (document.getElementById('ballooncode').value == ""
			|| document.getElementById('ballooncode').value == null) {
		alert("Bitte Balloon Code eingeben!");
	} else {
		window.location.href = OPENBALLOON_URI + "?ballooncode="
				+ document.getElementById('ballooncode').value;
	}
}

function createMap() {
	// Leaflet Map
	map = L.map('map', {
		zoomControl : false
	}).setView([ MAP_LAT, MAP_LON ], MAP_ZOOM);

	// Add our zoom control manually where we want to
	zoomControl = L.control.zoom({
		position : 'topleft'
	});
	map.addControl(zoomControl);

	// measure tool
	measureControl = L.control.measure({
		position : 'topleft',
		activeColor : '#FF0000',
		completedColor : '#FF0000',
		primaryLengthUnit : 'kilometers',
		primaryAreaUnit : 'sqmeters'
	});
	measureControl.addTo(map);

	// OpenStreetMap Humanitarian
	L
			.tileLayer(
					'http://{s}.tile.openstreetmap.fr/hot/{z}/{x}/{y}.png',
					{
						maxZoom : 17,
						attribution : '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>, Tiles courtesy of <a href="http://hot.openstreetmap.org/" target="_blank">Humanitarian OpenStreetMap Team</a>'
					}).addTo(map);

	// add scale and coordinates
	L.control.scale().addTo(map);
	L.control.mousePosition().addTo(map);

	// OpenStreetMap normal
	/*
	 * L.tileLayer('http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
	 * maxZoom: 17, attribution: 'Map data &copy; OpenStreetMap' }).addTo(map);
	 */

	// OpenStreetMap BlackAndWhite
	/*
	 * L.tileLayer('http://{s}.tiles.wmflabs.org/bw-mapnik/{z}/{x}/{y}.png', {
	 * attribution: '&copy; <a
	 * href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
	 * }).addTo(map);
	 */

	// Marker
	markerIconEvent = L.icon({
		iconUrl : 'img/balloon_blue.png',
		iconAnchor : [ 15, 40 ],
		popupAnchor : [ 0, 0 ]
	});
	markerIconFind = L.icon({
		iconUrl : 'img/balloon_red.png',
		iconAnchor : [ 15, 40 ],
		popupAnchor : [ 0, 0 ]
	});
	markerIconFind2 = L.icon({
		iconUrl : 'img/balloon_green.png',
		iconAnchor : [ 15, 40 ],
		popupAnchor : [ 0, 0 ]
	});

	events_control = L.control({
		position : 'topright'
	});

	events_control.onAdd = function(map) {
		var div = L.DomUtil.create('div', 'info legenddd');
		div.innerHTML += '<a href="javascript:showEvents()"><img src="img/bt_events.png" width="100" alt=""></a>';
		return div;
	};

	heatmap_control = L.control({
		position : 'topright'
	});

	heatmap_control.onAdd = function(map) {
		var div = L.DomUtil.create('div', 'info legenddd');
		div.innerHTML += "<a href=\"javascript:showHeatmap()\"><img src=\"img/bt_heatmap.png\" width=\"100\" alt=\"\"></a>";
		return div;
	};

	balloons_control = L.control({
		position : 'topright'
	});

	balloons_control.onAdd = function(map) {
		var div = L.DomUtil.create('div', 'info legenddd');
		div.innerHTML += "<a href=\"javascript:showFinds('',true)\"><img src=\"img/bt_balloons.png\" width=\"100\" alt=\"\"></a>";
		return div;
	};

	trajektorien_control = L.control({
		position : 'topright'
	});

	trajektorien_control.onAdd = function(map) {
		var div = L.DomUtil.create('div', 'info legenddd');
		div.innerHTML += "<a href=\"javascript:showTrajektorien('',true)\"><img src=\"img/bt_trajektorien.png\" width=\"100\" alt=\"\"></a>";
		return div;
	};

	trajektorien_control_legend = L.control({
		position : 'topright'
	});

	trajektorien_control_legend.onAdd = function(map) {
		var div = L.DomUtil.create('div', 'info legenddd');
		div.innerHTML += "<img src=\"img/map_trajektorien1.png\" width=\"100\" alt=\"\">";
		return div;
	};

	trajektorien_control_legend2 = L.control({
		position : 'topright'
	});

	trajektorien_control_legend2.onAdd = function(map) {
		var div = L.DomUtil.create('div', 'info legenddd');
		div.innerHTML += "<img src=\"img/map_trajektorien2.png\" width=\"100\" alt=\"\">";
		return div;
	};

	trajektorien_control_legend3 = L.control({
		position : 'topright'
	});

	trajektorien_control_legend3.onAdd = function(map) {
		var div = L.DomUtil.create('div', 'info legenddd');
		div.innerHTML += "<img src=\"img/map_trajektorien3.png\" width=\"100\" alt=\"\">";
		return div;
	};

	map.closePopup();

	if (GET_FUNCTION == "showBalloon") {
		document.getElementById('ballooncode').style.backgroundColor = '#BCEE68';
		document.getElementById('ballooncode').value = "Balloon gefunden";
		showBalloon(GET_EVENT);
	} else if (GET_FUNCTION == "noBalloon") {
		document.getElementById('ballooncode').style.backgroundColor = '#FF3030';
		document.getElementById('ballooncode').value = "nicht gefunden";
		readEventsGeoJSON(OPENBALLOON_URI
				+ "/layer?bereich=openballoon&layer=event", setEvents);
	} else if (GET_FUNCTION == "showEvent") {
		document.getElementById('ballooncode').style.backgroundColor = '#BCEE68';
		document.getElementById('ballooncode').value = "Event gefunden";
		showFinds(GET_EVENT);
	} else if (GET_FUNCTION == "noEvent") {
		document.getElementById('ballooncode').style.backgroundColor = '#FF3030';
		document.getElementById('ballooncode').value = "nicht gefunden";
		readEventsGeoJSON(OPENBALLOON_URI
				+ "/layer?bereich=openballoon&layer=event", setEvents);
	} else {
		// Get Events
		readEventsGeoJSON(OPENBALLOON_URI
				+ "/layer?bereich=openballoon&layer=event", setEvents);
	}
}