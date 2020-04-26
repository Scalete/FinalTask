<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="user" scope="request" type="db.entity.User"/>
<jsp:useBean id="tickets" scope="request" type="java.util.List<db.entity.Ticket>"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Личный кабинет</title>
    <link rel="stylesheet" type="text/css" href="../../style/tables/style.css" />
    <link rel="stylesheet" type="text/css" href="../../style/background/style.css" />
    <link rel="stylesheet" type="text/css" href="../../style/form/navbar/style.css" />
    <link rel="stylesheet" type="text/css" href="../../style/button/style.css" />
</head>
<body>

<div class="topnav">
    <a href="../../main.jsp">Главная</a>
</div>


<h2>Личный кабинет</h2>

<label>Имя: ${user.firstName}</label><br>
<label>Фамилия: ${user.lastName}</label><br>
<label>Почта: ${user.email}</label><br>
<label>Телефон: ${user.telephone}</label><br>
<label>Ваш счет: ${user.count}</label><br><br>

<form action="edit_account" method="get">
    <input type="SUBMIT" value="Редактировать профиль"/>
</form>


<h3>Билеты: </h3>

<table border="1px" class="content-table">
    <thead>
    <tr>
        <td>Фамилия, имя</td>
        <td>Отправление</td>
        <td>Прибытие</td>
        <td>Дата/время отправления</td>
        <td>Дата/время прибытия</td>
        <td>Поезд</td>
        <td>Вагон</td>
        <td>Цена</td>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="ticket" items="${tickets}">
        <tr>
            <td>${ticket.user.lastName} ${ticket.user.firstName}</td>
            <td>${ticket.trip.rout.departureStation.name}</td>
            <td>${ticket.trip.rout.destinationStation.name}</td>
            <td>${ticket.trip.rout.departureDateTime}</td>
            <td>${ticket.trip.rout.destinationDateTime}</td>
            <td>${ticket.trip.train.numTrain}</td>
            <td>${ticket.carriage.placeType}</td>
            <td>${ticket.trip.price}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>

</body>
</html>
