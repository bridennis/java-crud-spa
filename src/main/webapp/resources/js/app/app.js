var notifySettings = {
	type: 'info',
	delay: 2000,
	animate: { enter: 'animated rollIn', exit: 'animated fadeOutUp' }				
}

var app = angular.module('myApp', ['angularUtils.directives.dirPagination']);

app.constant("baseUrl", "http://localhost:8080/user/");

app.controller("ItemsController", ['$scope', '$http', 'baseUrl', function ($scope, $http, baseUrl) {
	
	$scope.perPage = 5;		// Максимальное число записей на странице
	
	$scope.users = [];
	$scope.totalItems = 0;			// Всего записей
	$scope.filteredItems = 0;		// Всего отфильтрованных записей
	$scope.itemsOnPage = 0;			// Текущее число записей на странице
	$scope.offset = 0;					// Смещение страницы
	$scope.sUser = {};
	$scope.pagination = { current: 1 };

	$scope.pageChanged = function(newPage) {
			getResultsPage(newPage);
	};

	function getResultsPage(pageNumber) {
		if (angular.isUndefined(pageNumber)) {
			pageNumber = $scope.pagination.current;
		}
		$scope.offset = (pageNumber - 1) * $scope.perPage;
		$http.get(baseUrl + "?offset=" + $scope.offset + "&limit=" + $scope.perPage + "&filter=" + encodeURIComponent(angular.toJson($scope.sUser)))
		.then(function successCallback(response) {
			
			$scope.users = response.data.items;
			$scope.itemsOnPage = response.data.items.length;
			$scope.totalItems = response.data.count;
			$scope.filteredItems = response.data.countFiltered;
			
		}, function errorCallback(response) {
			
		});
	};
	
	$scope.newSearch = function() {
		$scope.pagination.current = 1;
		getResultsPage();
	};
	
	/*
		Сбрасывает форму добавления/редактирования пользователя
	*/
  $scope.resetAddForm = function() {
		$scope.addFormSubmitButtonClass = 'glyphicon-plus';
		$scope.addForm.$setPristine();
		$scope.addForm.$setUntouched();
    $scope.curUser = angular.copy({ admin: false });
  }

	/*
		Сбрасывает форму фильтрации
	*/
  $scope.resetSearchForm = function(form) {
		$scope.searchForm.$setPristine();
		$scope.searchForm.$setUntouched();
    $scope.sUser = angular.copy({});
		$scope.pagination.current = 1;
		getResultsPage();
  }

	$scope.submitUser = function () {
		if ($scope.curUser.id == null) {
			$http.post(baseUrl, $scope.curUser).then(function successCallback(response) {
				$scope.resetAddForm();
				getResultsPage($scope.pagination.current);
				$.notify({ message: 'Данные успешно добавлены' }, notifySettings);
			}, function errorCallback(response) {
				$.notify({ message: 'Во время добавления данных произошла ошибка' }, notifySettings);
			});
		} else {
			$http.put(baseUrl + $scope.curUser.id, $scope.curUser).then(function successCallback(response) {
				$scope.resetAddForm();
				getResultsPage($scope.pagination.current);
				$.notify({ message: 'Данные успешно обновлены' }, notifySettings);
			}, function errorCallback(response) {
				$.notify({ message: 'Во время обновления данных произошла ошибка' }, notifySettings);
			});
		}
	}
	
	$scope.deleteUser = function (id) {
		$http.delete(baseUrl + id).then(function successCallback(response) {
			getResultsPage($scope.pagination.current);
			$.notify({ message: 'Данные успешно удалены' }, notifySettings);
		}, function errorCallback(response) {
				$.notify({ message: 'Во время удаления данных произошла ошибка' }, notifySettings);
		});
	}
	
	$scope.editUser = function (id) {
		$http.get(baseUrl + id).then(function successCallback(response) {
			$scope.curUser = { id: response.data.id, name: response.data.name, age: response.data.age, admin: response.data.admin };
			$scope.addFormSubmitButtonClass = 'glyphicon-floppy-disk';
		}, function errorCallback(response) {
		});
	}
	
	getResultsPage(1);
	
}]);

app.directive('addForm', function() {
  return {
    templateUrl: '/resources/tmpl/addForm.html'
  };
});

app.directive('searchForm', function() {
  return {
    templateUrl: '/resources/tmpl/searchForm.html'
  };
});
