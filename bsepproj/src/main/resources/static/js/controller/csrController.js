var csrController = angular.module('certApp.csrController', []);


					

csrController.controller('csrController', function($scope, $location,
		csrService,genCaService) {
	

	genCaService.getAliasesList().success(function(data) {
		for(var i=0; i<data.length; i++){
			
			if(data[i].ca!=false)
			$('#signer').append('<option value="' +data[i]+ '">'+data[i]+'</option>');
		}	
		
	})
	
	$scope.csrAction = function(){
		var req={};
		req.signer=$scope.signer;
		req.csr=$scope.csr_req;
		req.days = $scope.days;
		var data = JSON.stringify(req);
		alert(data);
		
	
		csrService.csr(data).success(function() {
			
			
		});
	}
	
	
	
})