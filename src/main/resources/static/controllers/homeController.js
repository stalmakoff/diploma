app.controller('HomeController', function HomeController($scope, $http,
		$location, growl) {
	$scope.flights = [];
	$scope.customer_name = "";
	$scope.contact_no = "";
	$scope.email = "";
	$scope.address = "";
	$scope.booking_date = "";
	$scope.departure_date = "";

	$scope.new_flight_code = "";
	$scope.new_flight_category = "";
	$scope.new_flight_airline = "";
	$scope.new_flight_seats = 0;
	$scope.new_flight_source = "";
	$scope.new_flight_destination = "";

	$scope.flightID = -1;

	$scope.__init = function() {
		load();
	};
	
	function load(){
		$http({
			method : 'POST',
			url : server_address + '/api/getFlights',
			headers : {
				'Content-Type' : 'application/json'
			}
		}).then(function(res) {
			console.log(res);
			$scope.flights = angular.copy(res.data);
		}, function(error) {
			console.log(error);
		});
	}

	$scope.openForm = function(id) {
		$scope.flightID = id;
	}


	$scope.save = function() {
		if ($scope.customer_name == "" || $scope.contact_no == ""
				|| $scope.email == "" || $scope.address == ""
				|| $scope.booking_date == "" || $scope.departure_date == ""
				|| $scope.flightID == -1) {
			growl.error("Please Fill Form Details", config);
		} else {
			$http({
				method : 'POST',
				url : server_address + '/api/saveBooking',
				headers : {
					'Content-Type' : 'application/json'
				},
				data : {
					customer_name : $scope.customer_name,
					contact_no : $scope.contact_no,
					email : $scope.email,
					address : $scope.address,
					booking_date : $scope.booking_date,
					departure_date : $scope.departure_date,
					flight_id : $scope.flightID
				}
			}).then(function(res) {
				console.log(res);
				$('#exampleModal').modal('hide')
				growl.success("Booking Successfully", config);
			}, function(error) {
				console.log(error);
				growl.error("Error Occured", config);
			});
		}
	}


	$scope.saveNewFlight = function() {
		if ($scope.new_flight_code == "" || $scope.new_flight_category == ""
				|| $scope.new_flight_airline == ""
				|| $scope.new_flight_seats == ""
				|| $scope.new_flight_source == ""
				|| $scope.new_flight_destination == "") {
			growl.error("Please Fill Form Details", config);
		} else {
			$http({
				method : 'POST',
				url : server_address + '/api/saveFlight',
				headers : {
					'Content-Type' : 'application/json'
				},
				data : {
					code : $scope.new_flight_code,
					category : $scope.new_flight_category,
					airline : $scope.new_flight_airline,
					seats : $scope.new_flight_seats,
					source : $scope.new_flight_source,
					destination : $scope.new_flight_destination
				}
			}).then(function(res) {
				console.log(res);
				$('#newModal').modal('hide')
				growl.success("Flight Save Successfully", config);
				load();
			}, function(error) {
				console.log(error);
				growl.error("Error Occured", config);
			});
		}
	}

    $scope.editFlight = function(id, code, category, airline, seats, source, destination) {
        $scope.flightID = id;
        $scope.new_flight_code = code;
        $scope.new_flight_category = category;
        $scope.new_flight_airline = airline;
        $scope.new_flight_seats = seats;
        $scope.new_flight_source = source;
        $scope.new_flight_destination = destination;
    }

    $scope.update = function() {
        if ($scope.flightID == -1) {
            growl.error("Error Occured", config);
        } else {
            $http({
                method : 'POST',
                url : server_address + '/api/editFlight',
                headers : {
                    'Content-Type' : 'application/json'
                },
                data : {
                    flightID : $scope.flightID,
                    new_flight_code : $scope.new_flight_code,
                    new_flight_category : $scope.new_flight_category,
                    new_flight_airline : $scope.new_flight_airline,
                    new_flight_seats : $scope.new_flight_seats,
                    new_flight_source : $scope.new_flight_source,
                    new_flight_destination : $scope.new_flight_destination
                }
            }).then(function(res) {
                console.log(res);
                $('#editModal').modal('hide')
                growl.success("Flight Updated Successfully", config);
                load();
            }, function(error) {
                console.log(error);
                growl.error("Error Occured", config);
            });
        }
    }

    $scope.removeFlight = function(id) {
        $http({
            method : 'POST',
            url : server_address + '/api/removeFlight',
            headers : {
                'Content-Type' : 'application/json'
            },
            data : {
                flight_id : id
            }
        }).then(function(res) {
            console.log(res);
            load();
            growl.success("Flight Removed Successfully", config);
        }, function(error) {
            console.log(error);
            growl.error("Error Occured", config);
        });
    }
});