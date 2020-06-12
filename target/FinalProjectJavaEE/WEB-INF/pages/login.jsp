<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${not empty language ? language : pageContext.request.locale.language}"/>
<fmt:setBundle basename="localization.text" />
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="<c:url value="/assets/css/style.css" />" rel="stylesheet">
    <title>Login</title>
</head>
<body>
<jsp:include page="header.jsp"></jsp:include>
<form action="/FinalProjectJavaEE/login" method="post">
    <table class="login-form center">
        <tr><td><fmt:message key="email"/>:</td><td><input type="text" name="email" pattern="^[-\w.]+@([A-z0-9][-A-z0-9]+\.)+[A-z]{2,4}$" required
                                      value="${email}"/></td></tr>
        <tr><td><fmt:message key="password"/>:</td><td><input type="password" name="password" pattern="^[а-яА-ЯёЁіІїЇa-zA-Z0-9]+$" required /></td></tr>
        <tr> <p class = "red">${error_message}</p></tr>
        <tr><td><input type="submit" value="<fmt:message key="login"/>"/></td></tr>
    </table>

</form>
</body>
</html>
