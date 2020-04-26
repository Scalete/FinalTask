<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="ticket" scope="request" type="db.entity.Ticket"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Билет</title>
    <link rel="stylesheet" type="text/css" href="../../style/ticket/style.css" />
</head>
<body>

<div class="container">
    <div class="pricing-table table1">
        <div class="pricing-header">
            <div class="price"><span>₴</span>${ticket.trip.price}</div>
            <div class="title">Ваш билет:</div>
        </div>
        <ul class="pricing-list">
            <li>Фамилия, имя <strong>${ticket.user.lastName} ${ticket.user.firstName}</strong></li>
            <div class="border"></div>
            <li>Отправление <strong>${ticket.trip.rout.departureStation.name}</strong> </li>
            <div class="border"></div>
            <li>Прибытие <strong>${ticket.trip.rout.destinationStation.name}</strong></li>
            <div class="border"></div>
            <li>Дата/время отправления <strong>${ticket.trip.rout.departureDateTime}</strong></li>
            <div class="border"></div>
            <li>Дата/время прибытия <strong>${ticket.trip.rout.destinationDateTime}</strong></li>
            <div class="border"></div>
            <li>Поезд <strong>${ticket.trip.train.numTrain}</strong></li>
            <div class="border"></div>
            <li>Вагон <strong>${ticket.carriage.placeType}</strong></li>
            <c:set var="noMoney" scope="request" value="${pageContext.request.getAttribute('not_enough_money')}"/>
            <c:if test = "${noMoney.equals(true)}">
                <div class="border"></div>
                <li><label style="color: red">У вас не хватает средств на балансе</label></li>
            </c:if>
        </ul>
        <form method="POST" action="buy_ticket">
            <input type="hidden" name="user_id" value=${ticket.user.id}>
            <input type="hidden" name="trip_id" value=${ticket.trip.id}>
            <input type="hidden" name="carriage_id" value=${ticket.carriage.id}>
            <input type="SUBMIT"  value="Купить билет"/>
        </form>
    </div>
</div>

</body>
</html>
