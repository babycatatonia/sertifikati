var userCertController = angular.module('certApp.userCertController', []);

userCertController.controller('userCertController', function($scope, $location,
						certService,genCaService,userCertService) {
	

	genCaService.getAliasesList().success(function(data) {
		for(var i=0; i<data.length; i++){
			
			$('#signer').append('<option value="' +data[i]+ '">'+data[i]+'</option>');
		}	
		
	})
	
	$scope.generateCert=function(){
		alert('generateCert funkcija u kontroleru');
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
				ca : 'false'
			}
			
			var str = JSON.stringify(data);
		
userCertService.generateEndNonCA(str).success(function(data) {
	alert('uspesno izdat CA sert');
    			
    			$location.path('/');
    			
    	
    		
		});
		
		
	}
	
	
	
})