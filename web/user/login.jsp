<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Логин</title>
    <link rel="stylesheet" href="../style/login_registration/style.css">
</head>
<body>

<form class="ui-form" method="post" action="${pageContext.request.contextPath}/login">
    <h3>Войти на сайт</h3>
    <div class="form-row">
        <input name="mail" id="mail" type="email" required autocomplete="off"/>
        <label for="mail">Email</label>
    </div>
    <div class="form-row">
        <input name="password" id="password" type="password" required autocomplete="off"/>
        <label for="password">Пароль</label>
    </div>
    <c:set var="error_mail" scope="request" value="${pageContext.request.getAttribute('error_mail')}"/>
    <c:if test = "${error_mail.equals(true)}">
        <label style="color: red">Неправильный e-mail или пароль</label><br><br>
    </c:if>

    <c:set var="active_user" scope="request" value="${pageContext.request.getAttribute('active_user')}"/>
    <c:if test = "${active_user.equals(false)}">
        <label style="color: red">Войдите, чтоб купить билет</label><br><br>
    </c:if>

    <p><input type="submit" value="Войти"></p>
</form>




</body>
</html>
