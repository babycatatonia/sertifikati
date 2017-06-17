var certService = angular.module('certApp.certService', []);


certService.factory('certService', function($http) {
	
	var temp={};
	
	 temp.selfSign = function(data) {
		return $http.post('/selfsign', data);
	}
	 
	 temp.getAliasesList = function(data) {
			return $http.post('/certvalidlist', data);
		}
	 
	 temp.sendSerial = function(data) {
		 
		 var config = {headers:  {
		        
		        'Accept': 'application/pkix-cert'
		        
		    }
		};
		 
			return $http.get('/serialinfo/'+data, config);
		}
	 
	 
	 temp.povuciSerial = function(data) {
			return $http.get('/povuciserial/'+data);
		}
	 
	 
	 temp.ocspStatus = function(data) {
			return $http.get('/ocspstatus/'+data);
		}
	
	return temp;
	
})
