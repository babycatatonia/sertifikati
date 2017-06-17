angular.module('read', [])
	.controller('con', function($scope, $http){
		$http.get('http://localhost:8080/restaurantManager/restaurantProfile').success(function(data) {
		    $scope.restaurant = data;
		})
		
		$http.get('http://localhost:8080/restaurantManager/restaurantSegments').success(function(data){
			var range = [];
			for(var i=0;i<data.length;i++) {
			  range.push(i);
			}
			$scope.rangeRestaurantSegments = range;
			$scope.restaurantSegments = data;
		})
		
		$http.get('http://localhost:8080/restaurantManager/menu').success(function(data){
			var rangeMenu = [];
			for(var i=0;i<data.length;i++) {
			  rangeMenu.push(i);
			}
			$scope.rangeMenu = rangeMenu;
			$scope.menu = data;
		})
		
		$http.get('http://localhost:8080/restaurantManager/menuMeals').success(function(data){
			var rangeMeals = [];
			for(var i=0;i<data.length;i++) {
			  rangeMeals.push(i);
			}
			$scope.rangeMeals = rangeMeals;
			$scope.meals = data;
		})
		
		$http.get('http://localhost:8080/restaurantManager/drinkCard').success(function(data){
			var rangeDrinkCard = [];
			for(var i=0;i<data.length;i++) {
			  rangeDrinkCard.push(i);
			}
			$scope.rangeDrinkCard = rangeDrinkCard;
			$scope.drinkCard = data;
		})
		
		$http.get('http://localhost:8080/restaurantManager/drinks').success(function(data){
			var rangeDrinks = [];
			for(var i=0;i<data.length;i++) {
			  rangeDrinks.push(i);
			}
			$scope.rangeDrinks = rangeDrinks;
			$scope.drinks = data;
		})
		
		
		
		
		
});

/*
angular.module('read', [])
	.controller('home', function($scope, $http){
		$http.get('http://localhost:8080').success(function(data) {
		    $scope.greeting = "shaq";
		});
});

angular.module("read", ["ngRoute"])
	.config(function($routeProvider) {
		$routeProvider

		.when("/restaurantManager/restaurantProfilePage", {
			templateUrl : "modules/restaurant_profile.html"
    });
});

angular.module("read", [])
	.controller("restaurantProfileController", function($scope) {
    $scope.greeting = "Greeting";
});
*/

