<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="list_rout" scope="request" type="java.util.List<db.entity.Rout>"/>
<jsp:useBean id="list_station" scope="request" type="java.util.List<db.entity.Station>"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Панель админа</title>
    <link rel="stylesheet" type="text/css" href="../style/tables/style.css" />
    <link rel="stylesheet" type="text/css" href="../style/background/style.css" />
    <link rel="stylesheet" type="text/css" href="../style/form/navbar/style.css" />
    <link rel="stylesheet" type="text/css" href="../style/button/style.css" />
</head>
<body>

<div class="topnav">
    <a href="../main.jsp">Главная</a>
</div>
<br>

<h1>Маршруты</h1>

<input type="SUBMIT" value="Добавить маршрут" onClick='location.href="admin/rout/add_rout.jsp"'>

<table border="1px" class="content-table">
    <thead>
    <tr>
        <td>Начальная станиция</td>
        <td>Конечная станция</td>
        <td>Время отправления</td>
        <td>Время прибытия</td>
        <td>Удалить</td>
        <td>Редактировать</td>
        <td>Промежуточные станции</td>
        <td>Информация</td>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="rout" items="${list_rout}">
        <tr>
            <td>${rout.departureStation.name}</td>
            <td>${rout.destinationStation.name}</td>
            <td>${rout.departureDateTime}</td>
            <td>${rout.destinationDateTime}</td>
            <td>
                <form method="POST" action="delete_rout">
                    <input type="hidden" name="id" value="${rout.id}">
                    <input type="SUBMIT" value="Удалить маршрут"/>
                </form>
            </td>
            <td>
                <form action="edit_rout" method="get">
                    <input type="hidden" name="rout_id" value="${rout.id}">
                    <input type="SUBMIT" value="Редактировать маршрут"/>
                </form>
            </td>
            <td>
                <form action="${pageContext.request.contextPath}/admin/rout/add_intermediate_station.jsp">
                    <input type="hidden" name="id" value="${rout.id}">
                    <input type="SUBMIT" value="Добавить"/>
                </form>
            </td>
            <td>
                <form method="GET" action="load_rout_info">
                    <input type="hidden" name="id" value="${rout.id}">
                    <input type="SUBMIT" value="Подробнее"/>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<br>

<h1>Станции</h1>

<input type="SUBMIT" value="Добавить станцию" onClick='location.href="admin/station/add_station.jsp"'>

<table border="1px" class="content-table">
    <thead>
    <tr>
        <td>Станции</td>
        <td>Удалить</td>
        <td>Редактировать</td>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="station" items="${list_station}">
        <tr>
            <td>${station.name}</td>
            <td>
                <form method="POST" action="delete_station">
                    <input type="hidden" name="id" value="${station.id}">
                    <input type="SUBMIT" name="delete_row" value="Удалить станцию"/>
                </form>
            </td>
            <td>
                <form action="edit_station" method="get">
                    <input type="hidden" name="station_id" value="${station.id}">
                    <input type="SUBMIT" value="Редактировать станцию"/>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

</body>
</html>
