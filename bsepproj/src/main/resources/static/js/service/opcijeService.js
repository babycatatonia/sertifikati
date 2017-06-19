var opcijeService = angular.module('certApp.opcijeService', []);


opcijeService.factory('opcijeService', function($http) {

	var temp={};

	temp.ucitajPrivilegije = function() {
		return $http.get('/klijentKontroler/ucitajPrivilegije');
	}
	
	temp.logOut = function(){
		return $http.post('/contr/logout');
	}

	return temp;
	
})