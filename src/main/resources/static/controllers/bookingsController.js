app.controller('BookingsController', function BookingsController($scope, $http,
		$location, growl) {
	$scope.bookings = [];
	$scope.booking_id = -1;
    $scope.customer_name = "";
    $scope.contact_no = "";
    $scope.email = "";
    $scope.address = "";
    $scope.booking_date = "";
    $scope.departure_date = "";

	$scope.__init = function() {
		load();
	};

    $scope.editBooking = function(id, customer_name, email, contact_no, address, booking_date, departure_date) {
        $scope.booking_id = id;
        $scope.customer_name = customer_name;
        $scope.email = email;
        $scope.contact_no = contact_no;
        $scope.address = address;
        $scope.booking_date = booking_date;
        $scope.departure_date = departure_date;
    }

    $scope.saveBooking = function() {
        if ($scope.booking_id == "" || $scope.customer_name == ""
            || $scope.email == "" || $scope.address == ""
            || $scope.booking_date == "" || $scope.departure_date == ""
            || $scope.contact_no == "") {
            growl.error("Please Fill Form Details", config);
        } else {
            $http({
                method : 'POST',
                url : server_address + '/api/editBooking',
                headers : {
                    'Content-Type' : 'application/json'
                },
                data : {
                    booking_id : $scope.booking_id,
                    customer_name : $scope.customer_name,
                    contact_no : $scope.contact_no,
                    email : $scope.email,
                    address : $scope.address,
                    booking_date : $scope.booking_date,
                    departure_date : $scope.departure_date
                }
            }).then(function(res) {
                console.log(res);
                $('#editModal').modal('hide')
                growl.success("Booking Updated Successfully", config);
                load();
            }, function(error) {
                console.log(error);
                growl.error("Error Occured", config);
            });
        }
    }

	$scope.removeBooking = function(id) {
		$http({
			method : 'POST',
			url : server_address + '/api/removeBooking',
			headers : {
				'Content-Type' : 'application/json'
			},
			data : {
				booking_id : id
			}
		}).then(function(res) {
			console.log(res);
			load();
			growl.success("Booking Removed Successfully", config);
		}, function(error) {
			console.log(error);
			growl.error("Error Occured", config);
		});
	}


	function load() {
		$http({
			method : 'POST',
			url : server_address + '/api/getBookings',
			headers : {
				'Content-Type' : 'application/json'
			}
		}).then(function(res) {
			console.log(res);
			$scope.bookings = angular.copy(res.data);
		}, function(error) {
			console.log(error);
		});
	}

});