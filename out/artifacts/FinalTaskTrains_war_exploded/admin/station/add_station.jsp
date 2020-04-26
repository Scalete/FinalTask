<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Добавление станции</title>
    <link rel="stylesheet" type="text/css" href="../../style/button/style.css" />
    <link rel="stylesheet" type="text/css" href="../../style/background/style.css" />
    <link rel="stylesheet" type="text/css" href="../../style/form/navbar/style.css" />
    <link rel="stylesheet" type="text/css" href="../../style/form/style.css" />
</head>
<body>

<div class="topnav">
<a href="${pageContext.request.contextPath}/loadAdminTables">Назад</a>
</div>

<form class="ui-form" method="post" action="${pageContext.request.contextPath}/add_station">
    <h3>Добавить станцию</h3>

    <div class="form-row">
        <input type="text" id="station" name="station" required autocomplete="off" />
        <label for="station">Новая станция</label>
    </div>

    <c:set var="error" scope="request" value="${pageContext.request.getAttribute('error_input')}"/>
    <c:if test = "${error.equals(true)}">
        <label style="color: red">Ошибка добавления станции</label><br><br>
    </c:if>

    <c:set var="success" scope="request" value="${pageContext.request.getAttribute('success_input')}"/>
    <c:if test = "${success.equals(true)}">
        <label style="color: green">Станция успешно добавлена</label><br><br>
    </c:if>

    <c:set var="isDuplicate" scope="request" value="${pageContext.request.getAttribute('isDuplicate')}"/>
    <c:if test = "${isDuplicate.equals(true)}">
        <label style="color: red">Такая станция уже существует</label><br><br>
    </c:if>

    <c:set var="shortName" scope="request" value="${pageContext.request.getAttribute('shortName')}"/>
    <c:if test = "${shortName.equals(true)}">
        <label style="color: red">Станция должна состоять минимум из 3 букв</label><br><br>
    </c:if>

    <input type="hidden" name="add_station" value="${true}">
    <input type="SUBMIT" value="Добавить станцию"/>

</form>

</body>
</html>
