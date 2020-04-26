<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core_1_1" %>
<html>
<head>
    <title>Главная</title>
    <link rel="stylesheet" type="text/css" href="auto_complete/css/style.css" />
    <script type="text/javascript" src="auto_complete/JS/jquery-1.4.2.min.js"></script>
    <script src="auto_complete/JS/jquery.autocomplete.js"></script>
    <link rel="stylesheet" type="text/css" href="style/background/style.css" />
    <link rel="stylesheet" type="text/css" href="style/form/navbar/style.css" />
    <link rel="stylesheet" type="text/css" href="style/form/style.css" />

    <script>
        jQuery(function(){
            $("#departure_station").autocomplete("autocomplete_stations.jsp");
            $("#destination_station").autocomplete("autocomplete_stations.jsp");
        });
    </script>
</head>
<body>


<c:set var="user" scope="session" value="${pageContext.session.getAttribute('active_user')}"/>

<div class="topnav">
    <a href="main.jsp">Главная</a>

    <c:if test="${user == null}">
        <a href="user/login.jsp">Войти</a>
        <a href="user/registration.jsp">Регистрация</a>
    </c:if>

    <c:if test="${user != null}">
        <a href="load_account">Личный кабинет</a>
        <input type="hidden" name="exit" value="true">

        <c:url value="main" var="startUrl">
            <c:param name="exit" value="${true}" />
        </c:url>

        <a href="${startUrl}">Выйти</a>
    </c:if>
</div>

<br>



<form class="ui-form" method="GET" action="loadSearchTrip">
    <h3>Поиск маршрута</h3>

    <div class="form-row">
        <input type="text" id="departure_station" name="departure_station" required/>
        <label for="departure_station">Начальная станция</label>
    </div>
    <div class="form-row">
        <input type="text" id="destination_station" name="destination_station" required/>
        <label for="destination_station">Конечная станция</label>
    </div>

    <strong><label style="color: #4a90e2">Дата и время отправления</label><br></strong>
    <input type="datetime-local"  name="departure_date" required/><br><br>

    <c:set var="nullStation" scope="request" value="${pageContext.request.getAttribute('nullStation')}"/>
    <c:if test = "${nullStation.equals(true)}">
        <label style="color: red">Одной из введёных станций нет в базе</label><br><br>
    </c:if>

    <input type="SUBMIT" value="Поиск"/>

</form>




</body>
</html>
