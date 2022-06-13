app.controller('UsersController', function UsersController($scope, $http,
		$location, growl) {
	$scope.users = [];
	$scope.user_id = -1;


	$scope.__init = function() {
		load();
	};


	$scope.editUser = function(id) {
		$scope.user_id = id;
	}


	$scope.update = function() {
		if ($scope.user_id == -1) {
			growl.error("Error Occured", config);
		} else {
			$http({
				method : 'POST',
				url : server_address + '/api/editUser',
				headers : {
					'Content-Type' : 'application/json'
				},
				data : {
					user_id : $scope.user_id
				}
			}).then(function(res) {
				console.log(res);
				$('#exampleModal').modal('hide')
				growl.success("User Updated Successfully", config);
				load();
			}, function(error) {
				console.log(error);
				growl.error("Error Occured", config);
			});
		}
	}

	function load() {
		$http({
			method : 'POST',
			url : server_address + '/api/getAllUsers',
			headers : {
				'Content-Type' : 'application/json'
			}
		}).then(function(res) {
			console.log(res);
			$scope.users = angular.copy(res.data);
		}, function(error) {
			console.log(error);
		});
	}

});