var userCertService = angular.module('certApp.userCertService', []);



genCaService.factory('userCertService', function($http) {
	
	var temp={};
	 
	temp.generateEndNonCA=function(data){
		 return $http.post('/generateEndCert', data);
	 }
	
	return temp;
	
})
