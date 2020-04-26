<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="list_trip" scope="request" type="java.util.List<db.entity.Trip>"/>
<html>
<head>
    <title>Результат поиска</title>
    <link rel="stylesheet" type="text/css" href="../../style/tables/style.css" />
    <link rel="stylesheet" type="text/css" href="../../style/background/style.css" />
    <link rel="stylesheet" type="text/css" href="../../style/form/navbar/style.css" />
    <link rel="stylesheet" type="text/css" href="../../style/button/style.css" />
</head>
<body>

<div class="topnav">
    <a href="../../main.jsp">Главная</a><br><br>
</div>

<c:set var="emptyList" scope="session" value="${pageContext.request.getAttribute('listIsEmpty')}"/>

<c:choose>
    <c:when test="${emptyList}">
        <label>Маршрутов не найдено</label>
    </c:when>
    <c:otherwise>
        <h1>Результат</h1>
        <table border="1px" class="content-table">
            <thead>
            <tr>
                <td>Номер поезда</td>
                <td>Начальная станиция</td>
                <td>Конечная станция</td>
                <td>Время отправления</td>
                <td>Время прибытия</td>
                <td>Время в пути</td>
                <td colspan="3">Свободных мест купе, плацкарт, общий</td>
                <td>Цена</td>
                <td>Информация</td>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="trip" items="${list_trip}">
                <tr>
                    <td>${trip.train.numTrain}</td>
                    <td>${trip.rout.departureStation.name}</td>
                    <td>${trip.rout.destinationStation.name}</td>
                    <td>${trip.rout.departureDateTime}</td>
                    <td>${trip.rout.destinationDateTime}</td>
                    <td>${trip.rout.timeDiff}</td>
                    <td>${trip.train.sumCoupe}</td>
                    <td>${trip.train.sumCommon}</td>
                    <td>${trip.train.sumReservedSeat}</td>
                    <td>${trip.price}</td>
                    <td>
                        <form method="GET" action="load_trip_info">
                            <input type="hidden" name="id" value="${trip.id}">
                            <input type="SUBMIT" value="Подробнее"/>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:otherwise>
</c:choose>



</body>
</html>
