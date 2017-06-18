var app = angular.module("certApp.route", [ "ngRoute" ]);


app.config(function($routeProvider) {
	$routeProvider.when("/", {
        templateUrl : "html/logovanje.html"
    }).when("/opcije", {
		templateUrl : "html/opcije.html"
	}).when("/selfsigncert",{
    	templateUrl : "html/selfcert.html"
    }).when("/newcacert",{
    	templateUrl : "html/gencacert.html"
    }).when("/genusercert",{
    	templateUrl : "html/genusercert.html"
    }).when("/certinfo",{
    	templateUrl : "html/infoserial.html"
    }).when("/povuci",{
    	templateUrl : "html/povlacenje.html"
    }).when("/ocsp",{
    	templateUrl : "html/ocsp.html"
    }).when("/csr",{
    	templateUrl : "html/csr.html"
    });
});