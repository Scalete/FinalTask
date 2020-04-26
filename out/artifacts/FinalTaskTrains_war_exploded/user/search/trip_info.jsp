<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="trip" scope="request" type="db.entity.Trip"/>
<jsp:useBean id="list_intermediate_station" scope="request" type="java.util.List<db.entity.IntermediateStation>"/>
<jsp:useBean id="list_carriages" scope="request" type="java.util.List<db.entity.Carriage>"/>
<html>
<head>
    <title>Информация о маршруте</title>
    <link rel="stylesheet" type="text/css" href="../../style/tables/style.css" />
    <link rel="stylesheet" type="text/css" href="../../style/background/style.css" />
    <link rel="stylesheet" type="text/css" href="../../style/form/navbar/style.css" />
    <link rel="stylesheet" type="text/css" href="../../style/button/style.css" />
</head>
<body>

<div class="topnav">
    <a href="../../main.jsp">Главная</a>
</div>

<br>

<h1>Маршрут</h1>

<table border="1px" class="content-table">
    <thead>
    <tr>
        <td>Номер поезда</td>
        <td>Начальная станиция</td>
        <td>Конечная станция</td>
        <td>Время отправления</td>
        <td>Время прибытия</td>
        <td>Время в пути</td>
        <td>Цена</td>
    </tr>
    </thead>
    <tbody>
        <tr>
            <td>${trip.train.numTrain}</td>
            <td>${trip.rout.departureStation.name}</td>
            <td>${trip.rout.destinationStation.name}</td>
            <td>${trip.rout.departureDateTime}</td>
            <td>${trip.rout.destinationDateTime}</td>
            <td>${trip.rout.timeDiff}</td>
            <td>${trip.price}</td>
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
    </tr>
    </thead>
    <tbody>
    <c:forEach var="intermediate_station" items="${list_intermediate_station}">
        <tr>
            <td>${intermediate_station.station.name}</td>
            <td>${intermediate_station.destinationTime}</td>
            <td>${intermediate_station.departureTime}</td>
            <td>${intermediate_station.stopTime}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<br>

<h1>Вагоны</h1>

<table border="1px" class="content-table">
    <thead>
    <tr>
        <td>Тип места</td>
        <td>Кол-во свободных мест</td>
        <td>Купить билет</td>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="carriage" items="${list_carriages}">
        <tr>
            <td>${carriage.placeType}</td>
            <td>${carriage.numSeats}</td>
            <td>
                <form method="POST" action="form_ticket">
                    <input type="hidden" name="id_trip" value="${trip.id}">
                    <input type="hidden" name="id_carriage" value="${carriage.id}">
                    <input type="SUBMIT"  value="Оформить билет"/>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

</body>
</html>
