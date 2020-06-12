<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${not empty language ? language : pageContext.request.locale.language}"/>
<fmt:setBundle basename="localization.text" />
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="<c:url value="/assets/css/style.css" />" rel="stylesheet">
    <title>user_account_info</title>
</head>
<body>

<jsp:include page="header.jsp"></jsp:include>
<p class="center">
    <h1> <fmt:message key="welcome"/>, ${user.name} ! </h1>
</p>
<p class="center"> <fmt:message key="history"/> </p>


        <table class="table center" style="width:50%">
            <thead>
            <th><fmt:message key="cruise"/></th>
            <th><fmt:message key="ticketType"/></th>
            <th><fmt:message key="excursionsHistory"/></th>
            </thead>
            <tbody>
            <c:forEach items="${purchasedTicketsForUser}" var="purchasedTicket">
                <c:choose>
                    <c:when test="${purchasedTicket==null}">
                        <p class="center">--%>
                            no tickets yet
                        </p>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td>${purchasedTicket.cruise.ship.model} | ${purchasedTicket.cruise.routeString}
                                | ${purchasedTicket.cruise.startDate} | ${purchasedTicket.cruise.endDate}</td>
                            <td>${purchasedTicket.ticketType}</td>
                            <td>${purchasedTicket.purchasedExcursionsString}</td>
                        </tr>
                    </c:otherwise>
                </c:choose>

            </c:forEach>

            </tbody>

        </table>

<%--    <c:otherwise>--%>
<%--        <p class="center">--%>
<%--            no tickets yet--%>
<%--        </p>--%>
<%--    </c:otherwise>--%>


</body>
</html>
