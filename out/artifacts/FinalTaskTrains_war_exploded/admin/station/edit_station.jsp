<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="station" scope="request" type="db.entity.Station"/>
<html>
<head>
    <title>Редактировать станцию</title>
    <link rel="stylesheet" type="text/css" href="../../style/button/style.css" />
    <link rel="stylesheet" type="text/css" href="../../style/background/style.css" />
    <link rel="stylesheet" type="text/css" href="../../style/form/navbar/style.css" />
    <link rel="stylesheet" type="text/css" href="../../style/form/style.css" />
</head>
<body>

<div class="topnav">
    <a href="loadAdminTables">Назад</a>
</div>

<form class="ui-form" method="post" action="edit_station">
    <h3>Редактировать станцию</h3>

    <div class="form-row">
        <input type="text" id="station" name="station" required autocomplete="off" />
        <label for="station">Текущая станция: ${station.name}</label>
    </div>

    <c:set var="error" scope="request" value="${pageContext.request.getAttribute('error_input')}"/>
    <c:if test = "${error.equals(true)}">
        <label style="color: red">Ошибка редактирования станции</label><br><br>
    </c:if>

    <c:set var="isDuplicate" scope="request" value="${pageContext.request.getAttribute('isDuplicate')}"/>
    <c:if test = "${isDuplicate.equals(true)}">
        <label style="color: red">Такая станция уже существует</label><br><br>
    </c:if>

    <c:set var="shortName" scope="request" value="${pageContext.request.getAttribute('shortName')}"/>
    <c:if test = "${shortName.equals(true)}">
        <label style="color: red">Станция должна состоять минимум из 3 букв</label><br><br>
    </c:if>

    <input type="hidden" name="station_id" value="${station.id}">
    <input type="hidden" name="edit_station" value="${true}">
    <input type="SUBMIT" value="Сохранить"/>

</form>

</body>
</html>
