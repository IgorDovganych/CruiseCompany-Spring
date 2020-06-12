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
    <title>Excursions</title>
</head>
<body>
<jsp:include page="header.jsp"></jsp:include>
<table class="table center" style="width:50%">
    <thead>
    <th><fmt:message key="port"/></th>
    <th><fmt:message key="excursions"/></th>
    <th></th>
    </thead>
    <tbody>
    <c:forEach items="${excursionsInPortContainers}" var="excursionsInPortContainer">
        <tr>
            <td>${excursionsInPortContainer.portName}</td>

            <td>
                <table class="table center">
                    <thead>
                    <th></th>
                    <th></th>
                    </thead>
                    <tbody>
                    <c:forEach items="${excursionsInPortContainer.excursions}" var="excursion">
                        <tr>
                            <td>
                                ${excursion.name}
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${excursion.name!=null}">
                                        <c:choose>
                                            <c:when test="${excursion.isActive==true}">
                                                <a href='/FinalProjectJavaEE/admin/deactivate_excursion?excursionId=${excursion.id}'>
                                                    <button class="dropbtn"><fmt:message key="deactivate"/></button>
                                                </a>
                                            </c:when>
                                            <c:otherwise>
                                                <a href='/FinalProjectJavaEE/admin/activate_excursion?excursionId=${excursion.id}'>
                                                    <button class="dropbtn"><fmt:message key="activate"/></button>
                                                </a>
                                            </c:otherwise>
                                        </c:choose>

                                    </c:when>
                                    <c:otherwise>
                                        <fmt:message key="noExcursions"/>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </td>
            <td>

            </td>
        </tr>
    </c:forEach>

    </tbody>

</table>
</body>
</html>
