var logovanjeServis = angular.module('certApp.logovanjeServis', []);

logovanjeServis.factory('logovanjeServis', function($http) {
	
	var temp = {};
	
	temp.ulogujKorisnika = function(korisnik) {
		return $http.post('/contr/login', korisnik);
	}
	
	temp.koJeNaSesiji = function() {
		return $http.post('/contr/check');
	}
	
	return temp;
	
})