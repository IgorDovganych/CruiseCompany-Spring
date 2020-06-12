<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${not empty language ? language : pageContext.request.locale.language}"/>
<fmt:setBundle basename="localization.text"/>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="<c:url value="/assets/css/style.css" />" rel="stylesheet">
    <title>Cruise Info</title>
    <script src="<c:url value="/assets/js/cruise_info_functions.js"/>"></script>
</head>
<body>
<jsp:include page="header.jsp"></jsp:include>
<div class="margin-left" value="${cruise.price}">
    <fmt:message key="cruiseInfo"/>:
    <input type="hidden" id="cruiseId" value="${cruise.id}">
    <br><fmt:message key="shipModel"/>: "${cruise.ship.model}"
    <br><fmt:message key="route"/>: ${cruise.routeString}
    <br><fmt:message key="startDate"/>: ${cruise.startDate}
    <br><fmt:message key="endDate"/>: ${cruise.endDate}
    <br><fmt:message key="basePrice"/>: ${cruise.price}$
    <br>
</div>
<c:choose>
    <c:when test="${noTicketsMessage!=null}">
        <p class="center red">
            <fmt:message key="noTicketsMessage"/>
                ${noTicketsMessage}
        </p>
    </c:when>
    <c:otherwise>
        <table class="table center" style="width:50%">
            <thead>
            <th></th>
            <th><fmt:message key="ticketType"/></th>
            <th><fmt:message key="bonuses"/></th>

            <th><fmt:message key="price"/></th>
            </thead>
            <tbody>
            <c:forEach items="${ticketTypes}" var="ticketType">
                <tr value="${ticketType.price_multiplier}">
                    <c:choose>
                        <c:when test="${ticketType.type == 'Standart'}">
                            <td><input type="radio" name="selectedTicket" value="${ticketType.id}"
                                       checked="true" onclick="getCruiseFinalPrice()"/></td>
                        </c:when>
                        <c:otherwise>
                            <td><input type="radio" name="selectedTicket" value="${ticketType.id}"
                                       onclick="getCruiseFinalPrice()"/></td>
                        </c:otherwise>
                    </c:choose>
                    <td>${ticketType.type}</td>
                    <td>
                        <c:choose>
                            <c:when test="${ticketType.bonusesString!='null'}">
                                ${ticketType.bonusesString}
                            </c:when>
                            <c:otherwise>
                                <fmt:message key="noBonuses"/>
                            </c:otherwise>
                        </c:choose>

                    </td>
                    <td>${cruise.price*ticketType.price_multiplier} $</td>
                </tr>
            </c:forEach>
            </tbody>

        </table>

        <p class="center">
            <fmt:message key="excursionsTitle"/>
        </p>
        <table class="table center" style="width:50%">
            <thead>
            <th></th>
            <th><fmt:message key="port"/></th>
            <th><fmt:message key="excursionName"/></th>
            <th><fmt:message key="price"/></th>
            <th><fmt:message key="description"/></th>
            </thead>
            <tbody>
            <c:forEach items="${excursions}" var="excursion">
                <c:if test="${excursion.isActive==true}">
                <tr value="${excursion.price}">
                    <td><input type="checkbox" name="excursion" value="${excursion.id}"
                               onclick="getCruiseFinalPrice()"/></td>
                    <td>${excursion.portName}</td>
                    <td>${excursion.name}</td>
                    <td>${excursion.price} $</td>
                    <td>${excursion.description}</td>
                </tr>
                </c:if>
            </c:forEach>
            </tbody>

        </table>

        <div id="finalPrice" align="center"><fmt:message key="finalPrice"/> = ${cruise.price} $</div>
        <p class="center">  <fmt:message key="numOfAvTickets"/>  ${availableTicketsAmount}</p>

        <c:choose>
            <c:when test="${user == null}">
                <p class="center red">
                    <fmt:message key="loginToBuy"/>
                </p>

            </c:when>
            <c:otherwise>
                <c:choose>
                    <c:when test="${availableTicketsAmount >0}">
                        <p class="center">
                            <button class='w3-button w3-green w3-tiny w3-round'
                                    onclick="sendRequest('/FinalProjectJavaEE/purchase_ticket', method='post')">Purchase
                            </button>
                        </p>
                    </c:when>
                    <c:otherwise>
                        <p class="center red">
                            <fmt:message key="soldOut"/>
                        </p>
                    </c:otherwise>
                </c:choose>

            </c:otherwise>
        </c:choose>
    </c:otherwise>
</c:choose>


</body>
</html>
