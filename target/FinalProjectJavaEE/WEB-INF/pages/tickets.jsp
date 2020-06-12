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
    <title>Tickets</title>
</head>
<body>
<jsp:include page="header.jsp"></jsp:include>
<table class="table center" style="width:50%">
    <thead>
    <th><fmt:message key="ticketType"/></th>
    <th><fmt:message key="bonuses"/></th>
    <th></th>
    </thead>
    <tbody>
    <c:forEach items="${ticketTypes}" var="ticketType">
        <tr>
            <td>${ticketType.type}</td>
            <td>
                <table class="table center">
                    <thead>
                    <th></th>
                    <th></th>
                    </thead>
                    <tbody>
                    <c:forEach items="${ticketType.bonuses}" var="bonus">
                        <tr>
                            <td>
                                <c:out value="${bonus}"/>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${bonus!=null}">
                                        <a href='/FinalProjectJavaEE/admin/delete_bonus?bonusName=${bonus}'>
                                            <button class="dropbtn"><fmt:message key="delete"/></button>
                                        </a>
                                    </c:when>
                                    <c:otherwise>
                                        <fmt:message key="noBonuses"/>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </td>
            <td>
                <form action="/FinalProjectJavaEE/admin/add_bonus" method="post">
                    <input type="hidden" name="ticketTypeId" value="${ticketType.id}">
                    <input type="submit" class="dropbtn" value="<fmt:message key="add"/>">
                    <input type="text" name="bonusName" value="Type bonus name.." pattern="[а-яА-ЯёЁіІїЇa-A-Za-z-Z0-9]{3,40}" required title="3-40 letters">
                </form>
            </td>
        </tr>
    </c:forEach>

    </tbody>

</table>

</body>
</html>
