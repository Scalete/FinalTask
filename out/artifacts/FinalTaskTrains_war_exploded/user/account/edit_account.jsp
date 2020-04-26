<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="user" scope="request" type="db.entity.User"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Редактировать профиль</title>
    <link rel="stylesheet" type="text/css" href="../../style/background/style.css" />
    <link rel="stylesheet" type="text/css" href="../../style/form/navbar/style.css" />
    <link rel="stylesheet" type="text/css" href="../../style/button/style.css" />
    <link rel="stylesheet" type="text/css" href="../../style/form/style.css" />
</head>
<body>

<div class="topnav">
    <a href="load_account">Назад</a>
</div>

<form class="ui-form" action="${pageContext.request.contextPath}/edit_account" method="post">
    <input type="hidden" name="user_id" required value=${user.id}>

    <h3>Редактировать профиль</h3>

    <div class="form-row">
        <input type="text" id="first_name" name="first_name" required value=${user.firstName} />
        <label for="first_name">Имя</label>
    </div>
    <div class="form-row">
        <input type="text" id="last_name" name="last_name" required value=${user.lastName} />
        <label for="last_name">Фамилия</label>
    </div>
    <div class="form-row">
        <input type="email" id="email" name="email" required value=${user.email} />
        <label for="email">E-mail</label>
    </div>
    <div class="form-row">
        <input type="tel" id="tel" name="tel" required value=${user.telephone} />
        <label for="tel">Телефон</label>
    </div>


    <c:set var="income_duplicate" scope="request" value="${pageContext.request.getAttribute('user_is_alone')}"/>
    <c:if test = "${income_duplicate.equals(true)}">
        <label style="color: red">E-mail уже занят</label><br><br>
    </c:if>

    <c:set var="shortName" scope="request" value="${pageContext.request.getAttribute('shortName')}"/>
    <c:if test = "${shortName.equals(true)}">
        <label style="color: red">Имя и фамилия должны содержать минимум 2 буквы</label><br><br>
    </c:if>

    <c:set var="errorTel" scope="request" value="${pageContext.request.getAttribute('errorTel')}"/>
    <c:if test = "${errorTel.equals(true)}">
        <label style="color: red">Телефон должен быть в формате: "+XXXXXXXXXXXX"</label><br><br>
    </c:if>

    <c:set var="empty_email" scope="request" value="${pageContext.request.getAttribute('empty_email')}"/>
    <c:if test = "${empty_email.equals(true)}">
        <label style="color: red">Заполните поле E-mail</label><br><br>
    </c:if>

    <input type="hidden" name="edit_account" value=${true}>
    <input type="SUBMIT" value="Отправить"/>
</form>


</body>
</html>
