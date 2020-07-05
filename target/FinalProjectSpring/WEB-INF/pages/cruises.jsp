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
    <title>Cruises</title>
</head>
<body>
<jsp:include page="header.jsp"></jsp:include>
<c:if test="${user.role=='admin'}">
    <div class="center">
        <a href="/FinalProjectSpring/admin/create_cruise_page" class="margin-right"><fmt:message key="createCruise"/></a>
    </div>
    <br>
</c:if>
<table class="table center" style="width:50%">
    <thead>
    <th><fmt:message key="ship"/></th>
    <th><fmt:message key="route"/></th>
    <th><fmt:message key="startDate"/></th>
    <th><fmt:message key="endDate"/></th>
    <th><fmt:message key="price"/></th>
    <th></th>
    <c:if test="${user.role=='admin'}">
        <th></th>
    </c:if>
    </thead>
    <tbody>
    <c:choose>
        <c:when test="${user.role=='admin'}">
            <c:forEach items="${cruises}" var="cruise">
                <tr>
                    <td>${cruise.ship.model}</td>
                    <td>${cruise.routeString}</td>
                    <td>${cruise.startDate}</td>
                    <td>${cruise.endDate}</td>
                    <td>${cruise.price} $</td>
                    <td><a href='/FinalProjectSpring/cruise_info?id=${cruise.id}'>
                        <button class='w3-button w3-green w3-tiny w3-round'>
                            <fmt:message key="open"/>
                        </button>
                    </a>
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${cruise.isActive==true}">
                                <a href='/FinalProjectSpring/admin/deactivate_cruise?id=${cruise.id}'>
                                    <button class='w3-button w3-green w3-tiny w3-round'>
                                        <fmt:message key="deactivate"/>
                                    </button>
                                </a>

                            </c:when>
                            <c:otherwise>
                                <a href='/FinalProjectSpring/admin/activate_cruise?id=${cruise.id}'>
                                    <button class='w3-button w3-green w3-tiny w3-round'>
                                        <fmt:message key="activate"/>
                                    </button>
                                </a>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <c:forEach items="${cruises}" var="cruise">
                <c:choose>
                    <c:when test="${cruise.isActive==true}">
                        <tr>
                            <td>${cruise.ship.model}</td>
                            <td>${cruise.routeString}</td>
                            <td>${cruise.startDate}</td>
                            <td>${cruise.endDate}</td>
                            <td>${cruise.price} $</td>
                            <td><a href='/FinalProjectSpring/cruise_info?id=${cruise.id}'>
                                <button class='w3-button w3-green w3-tiny w3-round'>
                                    <fmt:message key="open"/>
                                </button>
                            </a>
                            </td>
                        </tr>
                    </c:when>
                    <c:otherwise>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </c:otherwise>
    </c:choose>

<%--    <c:forEach items="${cruises}" var="cruise">--%>
<%--                <tr>--%>
<%--                    <td>${cruise.ship.model}</td>--%>
<%--                    <td>${cruise.routeString}</td>--%>
<%--                    <td>${cruise.startDate}</td>--%>
<%--                    <td>${cruise.endDate}</td>--%>
<%--                    <td>${cruise.price} $</td>--%>
<%--                    <td><a href='/FinalProjectSpring/cruise_info?id=${cruise.id}'>--%>
<%--                        <button class='w3-button w3-green w3-tiny w3-round'>--%>
<%--                            <fmt:message key="open"/>--%>
<%--                        </button>--%>
<%--                    </a>--%>
<%--                    </td>--%>
<%--                </tr>--%>
<%--    </c:forEach>--%>

    </tbody>

</table>
</body>
</html>
