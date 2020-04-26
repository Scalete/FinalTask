<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Промежуточные станции</title>
    <link rel="stylesheet" type="text/css" href="../../auto_complete/css/style.css" />
    <script type="text/javascript" src="../../auto_complete/JS/jquery-1.4.2.min.js"></script>
    <script src="../../auto_complete/JS/jquery.autocomplete.js"></script>
    <link rel="stylesheet" type="text/css" href="../../style/button/style.css" />
    <link rel="stylesheet" type="text/css" href="../../style/background/style.css" />
    <link rel="stylesheet" type="text/css" href="../../style/form/navbar/style.css" />
    <link rel="stylesheet" type="text/css" href="../../style/form/style.css" />

    <script>
        jQuery(function(){
            $("#intermediate_station").autocomplete("/autocomplete_stations.jsp");
        });
    </script>
</head>
<body>

<div class="topnav">
<a href="${pageContext.request.contextPath}/loadAdminTables">Назад</a>
</div>

<form class="ui-form" method="post" action="${pageContext.request.contextPath}/add_intermediate_station">

    <div class="form-row">
        <input type="text" id="intermediate_station" name="intermediate_station" required autocomplete="off" />
        <label for="intermediate_station">Промежуточная станция</label>
    </div>

    <label style="color: #4a90e2">Дата прибытия</label><br>
    <input type="datetime-local"  name="destination_date" required/><br><br>

    <label style="color: #4a90e2">Дата отправления</label><br>
    <input type="datetime-local"  name="departure_date" required/><br><br>

    <label style="color: #4a90e2">Время остановки</label><br>
    <input type="time"  name="stop_time" required/><br><br>

    <c:set var="error" scope="request" value="${pageContext.request.getAttribute('error_input')}"/>
    <c:if test = "${error.equals(true)}">
        <label style="color: red">Ошибка добавления станции</label><br><br>
    </c:if>

    <c:set var="success" scope="request" value="${pageContext.request.getAttribute('success_input')}"/>
    <c:if test = "${success.equals(true)}">
        <label style="color: green">Станция успешно добавлена в маршрут</label><br><br>
    </c:if>

    <c:set var="isRoutMainStation" scope="request" value="${pageContext.request.getAttribute('isRoutMainStation')}"/>
    <c:if test = "${isRoutMainStation.equals(true)}">
        <label style="color: red">Промежуточная станция не может быть начальной или конечной</label><br><br>
    </c:if>

    <c:set var="isDuplicateStation" scope="request" value="${pageContext.request.getAttribute('isDuplicateStation')}"/>
    <c:if test = "${isDuplicateStation.equals(true)}">
        <label style="color: red">Такая станция уже есть в маршруте</label><br><br>
    </c:if>

    <c:set var="nullStation" scope="request" value="${pageContext.request.getAttribute('nullStation')}"/>
    <c:if test = "${nullStation.equals(true)}">
        <label style="color: red">Такой станции нет в базе</label><br><br>
    </c:if>

    <input type="hidden" name="id_rout" value="${(param.id == null)? pageContext.request.getAttribute("id_rout"):param.id}"/>
    <input type="hidden" name="add_intermediate_station" value="${true}"/>
    <input type="SUBMIT" value="Добавить станцию в маршрут"/>

</form>

</body>
</html>
