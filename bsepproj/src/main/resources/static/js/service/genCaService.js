var genCaService = angular.module('certApp.genCaService', []);



genCaService.factory('genCaService', function($http) {
	
	var temp={};
	 
	 temp.getAliasesList = function(data) {
			return $http.post('/certvalidlist', data);
		}
	 
	 temp.generateCAS=function(data){
		 return $http.post('/generateCAS', data);
	 }
	
	return temp;
	
})
