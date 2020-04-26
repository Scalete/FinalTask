<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="rout" scope="request" type="db.entity.Rout"/>
<jsp:useBean id="list_intermediate_station" scope="request" type="java.util.List<db.entity.IntermediateStation>"/>
<html>
<head>
    <title>О маршруте</title>
    <link rel="stylesheet" type="text/css" href="../../../style/tables/style.css" />
    <link rel="stylesheet" type="text/css" href="../../../style/button/style.css" />
    <link rel="stylesheet" type="text/css" href="../../../style/background/style.css" />
    <link rel="stylesheet" type="text/css" href="../../../style/form/navbar/style.css" />
</head>
<body>
<div class="topnav">
<a href="loadAdminTables">Назад</a>
</div>
<br>

<h1>Маршрут</h1>

<table border="1px" class="content-table">
    <thead>
    <tr>
        <td>Начальная станиция</td>
        <td>Конечная станция</td>
        <td>Время отправления</td>
        <td>Время прибытия</td>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td>${rout.departureStation.name}</td>
        <td>${rout.destinationStation.name}</td>
        <td>${rout.departureDateTime}</td>
        <td>${rout.destinationDateTime}</td>
    </tr>
    </tbody>
</table>

<br>

<h1>Промежуточные станции</h1>

<table border="1px" class="content-table">
    <thead>
    <tr>
        <td>Название</td>
        <td>Время прибытия</td>
        <td>Время отбытия</td>
        <td>Время остановки</td>
        <td>Редактировать</td>
        <td>Удалить</td>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="intermediate_station" items="${list_intermediate_station}">
        <tr>
            <td>${intermediate_station.station.name}</td>
            <td>${intermediate_station.destinationTime}</td>
            <td>${intermediate_station.departureTime}</td>
            <td>${intermediate_station.stopTime}</td>
            <td>
                <form action="edit_intermediate_station" method="get">
                    <input type="hidden" name="id_station" value="${intermediate_station.id}">
                    <input type="SUBMIT" value="Редактировать станцию"/>
                </form>
            </td>
            <td>
                <form method="POST" action="delete_intermediate_station">
                    <input type="hidden" name="id_station" value="${intermediate_station.id}">
                    <input type="SUBMIT" value="Удалить станцию"/>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

</body>
</html>
