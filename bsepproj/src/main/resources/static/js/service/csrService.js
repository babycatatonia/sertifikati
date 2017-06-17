var csrService = angular.module('certApp.csrService', []);

csrService.factory('csrService', function($http) {
	
	
	var temp={};
	
	 temp.csr = function(data) {
			return $http.post('/csr', data);
		}
	 
	 return temp;
})