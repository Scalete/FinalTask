<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Добавление маршрута</title>
    <link rel="stylesheet" type="text/css" href="../../auto_complete/css/style.css" />
    <script type="text/javascript" src="../../auto_complete/JS/jquery-1.4.2.min.js"></script>
    <script src="../../auto_complete/JS/jquery.autocomplete.js"></script>
    <link rel="stylesheet" type="text/css" href="../../style/button/style.css"/>
    <link rel="stylesheet" type="text/css" href="../../style/background/style.css" />
    <link rel="stylesheet" type="text/css" href="../../style/form/navbar/style.css" />
    <link rel="stylesheet" type="text/css" href="../../style/form/style.css" />

    <script>
        jQuery(function(){
            $("#departure_station").autocomplete("/autocomplete_stations.jsp");
            $("#destination_station").autocomplete("/autocomplete_stations.jsp");
        });
    </script>
</head>
<body>

<div class="topnav">
<a href="${pageContext.request.contextPath}/loadAdminTables">Назад</a>
</div>

<form class="ui-form" method="post" action="${pageContext.request.contextPath}/add_rout">
    <h3>Добавить маршрут</h3>

    <div class="form-row">
        <input type="text" id="departure_station" name="departure_station" required autocomplete="off" />
        <label for="departure_station">Начальная станция</label>
    </div>

    <div class="form-row">
        <input type="text" id="destination_station" name="destination_station" required autocomplete="off" />
        <label for="destination_station">Конечная станция</label>
    </div>

    <label style="color: #4a90e2">Дата отправления</label><br>
    <input type="datetime-local"  name="departure_date" required/><br><br>
    <label style="color: #4a90e2">Дата прибытия</label><br>
    <input type="datetime-local"  name="destination_date" required/><br><br>

    <c:set var="error" scope="request" value="${pageContext.request.getAttribute('error_input')}"/>
    <c:if test = "${error.equals(true)}">
        <label style="color: red">Ошибка добавления маршрута</label><br><br>
    </c:if>

    <c:set var="success" scope="request" value="${pageContext.request.getAttribute('success_input')}"/>
    <c:if test = "${success.equals(true)}">
        <label style="color: green">Маршрут успешно добавлен</label><br><br>
    </c:if>

    <c:set var="nullStation" scope="request" value="${pageContext.request.getAttribute('nullStation')}"/>
    <c:if test = "${nullStation.equals(true)}">
        <label style="color: red">Одной из введёных станций нет в базе</label><br><br>
    </c:if>

    <c:set var="DuplicateStation" scope="request" value="${pageContext.request.getAttribute('DuplicateStation')}"/>
    <c:if test = "${DuplicateStation.equals(true)}">
        <label style="color: red">Начальная станция не может быть одинаковой с конечной</label><br><br>
    </c:if>

    <input type="hidden" name="add_rout" value="${true}">
    <input type="SUBMIT" value="Добавить маршрут"/>

</form>

</body>
</html>
