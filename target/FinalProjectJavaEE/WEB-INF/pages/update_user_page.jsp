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
    <title>update-user</title>
</head>
<body>
<jsp:include page="header.jsp"></jsp:include>

<p class="center">
<h1><fmt:message key="updateUser"/></h1>


<form method="post" action="/FinalProjectJavaEE/admin/update_user">

    <input type="hidden" name="id" value="${param.id}">
    <br>
    <fmt:message key="name"/>:<br>
    <input type="text" name="name" value="${name}">
    <br>
    <fmt:message key="email"/>:<br>
    <input type="text" name="email" value="${param.email}">
    <br>
    <fmt:message key="password"/>:<br>
    <input type="text" name="password" value="">
    <br>
    <fmt:message key="role"/>:<br>
    <select size="1" name="role">
        <option>${param.role}</option>
        <option>
            <c:choose>
                <c:when test="${param.role == 'admin'}">user</c:when>
                <c:otherwise>admin</c:otherwise>
            </c:choose>
        </option>
    </select>
    <br>
    <br><br>
    <input type="submit" value="<fmt:message key="update"/>">
</form>
</p>
</body>
</html>
