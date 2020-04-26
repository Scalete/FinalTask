<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="intermediate_station" scope="request" type="db.entity.IntermediateStation"/>
<html>
<head>
    <title>Редактирование промежуточной станции</title>
    <link rel="stylesheet" type="text/css" href="../../../../auto_complete/css/style.css" />
    <script type="text/javascript" src="../../../../auto_complete/JS/jquery-1.4.2.min.js"></script>
    <script src="../../../../auto_complete/JS/jquery.autocomplete.js"></script>
    <link rel="stylesheet" type="text/css" href="../../../../style/button/style.css" />
    <link rel="stylesheet" type="text/css" href="../../../../style/background/style.css" />
    <link rel="stylesheet" type="text/css" href="../../../../style/form/style.css" />

    <script>
        jQuery(function(){
            $("#intermediate_station").autocomplete("/autocomplete_stations.jsp");
        });
    </script>
</head>
<body>

<form method="GET" action="load_rout_info">
    <input type="hidden" name="id" value="${intermediate_station.rout.id}">
    <input type="SUBMIT" value="Назад"/>
</form>

<form class="ui-form" method="post" action="edit_intermediate_station">

    <div class="form-row">
        <input type="text" id="intermediate_station" name="intermediate_station" required autocomplete="off"
               value=${intermediate_station.station.name} />
        <label for="intermediate_station">Промежуточная станция</label>
    </div>

    <label style="color: #4a90e2">Дата прибытия</label><br>
    <input type="datetime-local"  name="destination_date" value=${intermediate_station.destinationTime.replace(" ", "T")} required/><br><br>

    <label style="color: #4a90e2">Дата отправления</label><br>
    <input type="datetime-local"  name="departure_date" value=${intermediate_station.departureTime.replace(" ", "T")} required/><br><br>

    <label style="color: #4a90e2">Время остановки</label><br>
    <input type="time"  name="stop_time" value=${intermediate_station.stopTime.replace(" ", "T")} required/><br><br>

    <c:set var="error" scope="request" value="${pageContext.request.getAttribute('error_input')}"/>
    <c:if test = "${error.equals(true)}">
        <label style="color: red">Ошибка редактирования</label><br><br>
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

    <input type="hidden" name="edit_intermediate_station" value="${true}">
    <input type="hidden" name="id_station" value="${intermediate_station.id}">
    <input type="hidden" name="id_rout" value="${intermediate_station.rout.id}">
    <input type="hidden" name="order" value="${intermediate_station.order}">
    <input type="hidden" name="old_station_name" value="${intermediate_station.station.name}">
    <input type="SUBMIT" value="Редактировать станцию"/>

</form>

</body>
</html>
