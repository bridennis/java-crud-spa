<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>

<html lang="ru" ng-app="myApp">
<head>
    <title>Список пользователей</title>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>

    <link rel="stylesheet" href="/resources/css/bootstrap.min.css">
    <link rel="stylesheet" href="/resources/css/animate.min.css">

    <script type="text/javascript" src="/resources/js/angular.min.js"></script>
    <script type="text/javascript" src="/resources/js/jquery-3.1.1.min.js"></script>
    <script type="text/javascript" src="/resources/js/bootstrap-notify.min.js"></script>

    <script type="text/javascript" src="/resources/js/app/app.js"></script>
    <script type="text/javascript" src="/resources/js/app/dirPagination.js"></script>
</head>
<body>

<div class="container">

    <div class="header clearfix">
        <nav class="navbar navbar-default">
            <div class="container-fluid">
                <a class="navbar-brand" href="/">Single Page Application: CRUD</a>
                <ul class="nav nav-pills pull-right">
                </ul>
            </div>
        </nav>
    </div>

    <div class="jumbotron">
        <h2>Тестовое задание для онлайн стажировки <a href="http://javarush.ru/" target="_blank">javarush.ru</a></h2>
        <p class="lead">Стек технологий: Maven, Spring, Tomcat, Hibernate, MySQL, AngularJS, Bootstrap, REST</p>
    </div>

    <div id="contents" ng-controller="ItemsController">

        <div class="panel panel-primary">

            <div class="panel-heading">
                Список пользователей: Всего <span class="badge">{{ totalItems }}</span> / показано <span class="badge">{{ itemsOnPage }}</span> из <span class="badge">{{ filteredItems }}</span>
            </div>

            <div class="panel-body">
                <table class="table table-bordered">

                    <thead>
                    <tr>
                        <th>Имя пользователя</th>
                        <th>Возраст</th>
                        <th>Администратор</th>
                        <th>Дата создания/обновления</th>
                        <th>Операции</th>
                    </tr>
                    </thead>

                    <tbody>

                    <tr ng-form="searchForm" name="searchForm" search-form novalidate ng-show="totalItems > 0"></tr>

                    <tr dir-paginate="user in users | itemsPerPage: perPage" total-items="filteredItems" current-page="pagination.current" pagination-id="usersPagination">
                        <td>{{ user.name }}</td>
                        <td>{{ user.age }}</td>
                        <td>{{ user.admin == true ? "Да" : "Нет" }}</td>
                        <td>{{ user.createdDate | date : "yyyy-MM-dd HH:mm:ss" }}</td>
                        <td>
                            <button class="btn btn-default btn-sm" type="button" ng-click="editUser(user.id)"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></button>
                            <button class="btn btn-default btn-sm" type="button" ng-click="deleteUser(user.id)"><span class="glyphicon glyphicon-trash" aria-hidden="true"></span></button>
                        </td>
                    </tr>

                    <tr ng-if="totalItems == 0">
                        <td colspan="5" class="text-center"><b>Список пуст.<br />Начните добавлять пользователей.</b></td>
                    </tr>

                    <tr ng-form="addForm" name="addForm" add-form novalidate></tr>

                    </tbody>

                </table>

                <!-- https://github.com/michaelbromley/angularUtils/tree/master/src/directives/pagination -->
                <div class="text-center">
                    <dir-pagination-controls on-page-change="pageChanged(newPageNumber)" template-url="/resources/tmpl/dirPagination.tpl.html" pagination-id="usersPagination"></dir-pagination-controls>
                </div>

            </div>

        </div>
    </div>

    <footer class="footer">
        <p>&copy; 2017</p>
    </footer>

</div>

</body>
</html>
