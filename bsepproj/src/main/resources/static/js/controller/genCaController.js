var genCaController = angular.module('certApp.genCaController', []);

genCaController.controller('genCaController', function($scope, $location,
						   genCaService) {

	
	genCaService.getAliasesList().success(function(data) {
		for(var i=0; i<data.length; i++){
			
			if(data[i].ca!=false)
			$('#signer').append('<option value="' +data[i]+ '">'+data[i]+'</option>');
		}	
		
	})
	
	$scope.generateCert=function(){
	
		var data = {
				ime : $scope.ime,
				prezime : $scope.prezime,
				email : $scope.email,
				kompanija : $scope.kompanija,
				idKorisnika : $scope.idKorisnika,
				startDate : $scope.startDate,
				endDate : $scope.endDate,
				orgUnit : $scope.orgUnit,
				keystorepass : $scope.keystorepass,
				alias : $scope.alias,
				signer: $scope.signer,
				ca : 'true'
			}
			
			var str = JSON.stringify(data);
		
genCaService.generateCAS(str).success(function(data) {
	alert('uspesno izdat CA sert');
    			
    			$location.path('/');
    			
    	
    		
		});
		
		
	}
	

})