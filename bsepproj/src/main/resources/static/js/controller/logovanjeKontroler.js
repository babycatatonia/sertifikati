var logovanjeKontroler = angular.module('certApp.logovanjeKontroler', []);

logovanjeKontroler.controller('logovanjeKontroler', function($window, $location, $scope, logovanjeServis) {
	
	logovanjeServis.koJeNaSesiji().success(function(data) {
		if (data.message == "NekoNaSesiji") {
			$location.path('/opcije');
		} else {

		}
	});
	
		
	$scope.changeRoute = function(url, forceReload) {
        $scope = $scope || angular.element(document).scope();
        if(forceReload || $scope.$$phase) { // that's right TWO dollar signs: $$phase
            window.location = url;
        } else {
            $location.path(url);
            $scope.$apply();
        }
    };
	
	$scope.logovanje = function(){
		var korisnik = {
			email : $scope.email,
			sifra : $scope.sifra
		}
		
		var str = JSON.stringify(korisnik);
		
		logovanjeServis.ulogujKorisnika(str).success(function(data) {
			if(data.message != "NePostoji"){	
				console.log(data);
				$location.path('/opcije');
				
			}
			/*if(data.message != "NePostoji"){
				if(data.obj.tipKorisnika == 'GOST' && data.message == "Ulogovan"){
					$location.path('/gostGlavnaStrana');
				}else if(data.obj.tipKorisnika == 'GOST' && data.message == "NijeAktiviran"){
					alert("Da bi ste se ulogovali morate potvrditi aktivaciju na email-u.");
				}
				if(data.obj.tipKorisnika == 'PONUDJAC')
					$location.path('/ponudjac');
				if(data.obj.tipKorisnika == 'KUVAR')
					$location.path('/kuvar');
				if(data.obj.tipKorisnika == 'MENADZER_SISTEMA')
					$location.path('/menSistema');
				if(data.obj.tipKorisnika == 'MENADZER_RESTRORANA')
					$location.path('/menadzerRestorana');
				if(data.obj.tipKorisnika == 'KONOBAR')
					$location.path('/konobar');
				if(data.obj.tipKorisnika == 'SANKER')
					$location.path('/sanker');
			}else{
				alert("Korisnik ne postoji u bazi.");
				$scope.changeRoute('/');
			}*/
		});
		
	}
	
})