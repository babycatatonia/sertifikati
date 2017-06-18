var opcijeController = angular.module('certApp.opcijeController', []);

opcijeController.controller('opcijeController', function($scope, $location,
						opcijeService) {
	
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
	
	$scope.logout = function(){
		opcijeService.logout();
		$location.path('/');
	};
})