<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Регистрация</title>
    <link rel="stylesheet" href="../style/login_registration/style.css">
</head>
<body>

<form class="ui-form" method="post" action="${pageContext.request.contextPath}/login_registration_user">
    <h3>Регистрация</h3>
    <div class="form-row">
        <input name="email" id="mail" type="email" required autocomplete="off"/><label for="mail">Email</label>
    </div>
    <div class="form-row">
        <input name="password" id="password" type="password" required autocomplete="off"/><label for="password">Пароль</label>
    </div>
    <div class="form-row">
        <input name="last_name" id="last_name" type="text" required autocomplete="off"/><label for="password">Имя</label>
    </div>
    <div class="form-row">
        <input name="first_name" id="first_name"  type="text" required autocomplete="off"/><label for="password">Фамилия</label>
    </div>
    <div class="form-row">
        <input name="tel" type="tel" id="tel"  required autocomplete="off"/><label for="password">Телефон</label>
    </div>

    <c:set var="income_duplicate" scope="request" value="${pageContext.request.getAttribute('user_is_alone')}"/>
    <c:if test = "${income_duplicate.equals(true)}">
        <label style="color: red">Такой пользователь уже существует</label><br><br>
    </c:if>

    <c:set var="shortName" scope="request" value="${pageContext.request.getAttribute('shortName')}"/>
    <c:if test = "${shortName.equals(true)}">
        <label style="color: red">Имя и фамилия должны содержать минимум 2 буквы</label><br><br>
    </c:if>

    <c:set var="shortPassword" scope="request" value="${pageContext.request.getAttribute('shortPassword')}"/>
    <c:if test = "${shortPassword.equals(true)}">
        <label style="color: red">Пароль должен содержать минимум 8 символов</label><br><br>
    </c:if>

    <c:set var="errorTel" scope="request" value="${pageContext.request.getAttribute('errorTel')}"/>
    <c:if test = "${errorTel.equals(true)}">
        <label style="color: red">Телефон должен быть в формате: "+XXXXXXXXXXXX"</label><br><br>
    </c:if>

    <c:set var="empty_email" scope="request" value="${pageContext.request.getAttribute('empty_email')}"/>
    <c:if test = "${empty_email.equals(true)}">
        <label style="color: red">Заполните поле E-mail</label><br><br>
    </c:if>

    <p><input type="submit" value="Регистрация"></p>
</form>

</body>
</html>
