var opcijeController = angular.module('certApp.opcijeController', []);

opcijeController.controller('opcijeController', function($scope, $location,
						opcijeService, $window) {
	
	$scope.privileges = [];
	
	opcijeService.ucitajPrivilegije().success(function(data){
		$scope.privileges = data;	
	})
	.error(function(data){
		alert("Korisnik nema nikakvih privilegija.");
	});
	
	$scope.hasPrivilege = function(privilege){
		if($scope.privileges.indexOf(privilege) > -1)
			return true;
		else
			return false;
	};
	
	$scope.logOut = function(){

		opcijeService.logOut().success(function(data) {
			if(data.message == "Izlogovan"){
				$window.location.href = '/';
			}else{
			}
		});
	}
})