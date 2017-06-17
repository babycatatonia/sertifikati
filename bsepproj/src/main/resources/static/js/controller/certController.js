var certController = angular.module('certApp.certController', []);

certController.controller('certController', function($scope, $location,
						certService) {
	

	$scope.sendSerial = function(){
		var serial = $scope.serial;
		certService.sendSerial(serial).success(function(data) {
		
		});
	}
	
	$scope.povuciSerial = function(){
		var serial = $scope.serial;
		certService.povuciSerial(serial).success(function(data) {
			alert(data.message);
		});
	}
	
	$scope.ocspstatus = function(){
		var serial = $scope.serial;
		certService.ocspStatus(serial).success(function(data){
			alert(data.message);
		});
	}

	$scope.selfsign = function(){
		
	var pass1 = $scope.certpass1;
	var pass2 = $scope.certpass2;
		
	if(pass1.localeCompare(pass2)==0){
		
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
			signer :$scope.signer,
			certpass: pass1
		}
		
		var str = JSON.stringify(data);
		
		certService.selfSign(str).success(function(data) {
			
    		if(data.ok == true){
    			alert("samopotpisan!");
    			
    			$location.path('/');
    			
    		}
    		
		});
				}else {
					alert('sifre se ne poklapaju!');
				}
					}
	
	
	

})