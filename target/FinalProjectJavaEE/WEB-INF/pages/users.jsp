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
    <title>Users</title>
</head>
<body>
<jsp:include page="header.jsp"></jsp:include>
<table class="table center">
    <thead>
    <th><fmt:message key="name"/></th>
    <th><fmt:message key="email"/></th>
    <th><fmt:message key="role"/></th>
    <th></th>
    </thead>
    <tbody>
    <c:forEach items="${users}" var="user">
        <c:if test="${sessionScope.user.id!=user.id}">
            <tr>
                <td>${user.name}</td>
                <td>${user.email}</td>
                <td>${user.role}</td>
                <td>
                    <a href='/FinalProjectJavaEE/admin/update_user_page?id=${user.id}&name=${user.name}&email=${user.email}&role=${user.role}'>
                        <button class='w3-button w3-green w3-tiny w3-round'>
                            <fmt:message key="update"/>
                        </button>
                    </a>
                </td>
            </tr>
        </c:if>
    </c:forEach>

    </tbody>

</table>
</body>
</html>
