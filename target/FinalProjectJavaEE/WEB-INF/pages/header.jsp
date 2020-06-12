
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${not empty language ? language : pageContext.request.locale.language}"/>
<fmt:setBundle basename="localization.text" />
<html>
<head>
    <title>Header</title>
</head>
<body>
<div class="header menu">
    <div class="left-float ">
        <div class="left-float">
            <a href="/FinalProjectJavaEE" class="margin-right"><img class="main-img" src="<c:url value="/assets/images/cruise_logo.jpg"/>"></a>
        </div>
        <c:if test="${user.role=='admin'}">
            <div class="left-float margin-top">
                    <a href="/FinalProjectJavaEE/admin/users" class="margin-right"><fmt:message key="users"/></a>
            </div>
            <div class="left-float margin-top">
                <a href="/FinalProjectJavaEE/admin/tickets" class="margin-right"><fmt:message key="tickets"/></a>
            </div>
            <div class="left-float margin-top">
                <a href="/FinalProjectJavaEE/admin/excursions" class="margin-right"><fmt:message key="excursions"/></a>
            </div>
        </c:if>
        <div class="left-float margin-top">
            <a href="/FinalProjectJavaEE/cruises" class="margin-right"><fmt:message key="cruises"/></a>
        </div>
    </div>
    <div class="right-float margin-top" style=" margin-right: 10% ">

        <c:choose>
            <c:when test="${user == null}">
                <a href="/FinalProjectJavaEE/login_page" class="margin-right"> <fmt:message key="login"/></a>
            </c:when>
            <c:otherwise>
                <a href="/FinalProjectJavaEE/user_account_info" class="margin-right">${sessionScope.user.email}</a>
            </c:otherwise>
        </c:choose>
        <c:choose>
            <c:when test="${user == null}">
                <a href="/FinalProjectJavaEE/registration_page" class="margin-right"> <fmt:message key="register"/></a>
            </c:when>
            <c:otherwise>
                <a href="/FinalProjectJavaEE/logout" class="margin-right"><fmt:message key="logout"/> </a>
            </c:otherwise>
        </c:choose>
        <div class="dropdown">
            <button class="dropbtn"><fmt:message key="language"/></button>
            <div class="dropdown-content">
                <a href="/FinalProjectJavaEE/change_language?lang=en&link=${requestScope['javax.servlet.forward.request_uri']}&parameters=${pageContext.request.queryString}">English</a>
                <a href="/FinalProjectJavaEE/change_language?lang=ukr&link=${requestScope['javax.servlet.forward.request_uri']}&parameters=${pageContext.request.queryString}">Українська</a>
                <a href="/FinalProjectJavaEE/change_language?lang=ru&link=${requestScope['javax.servlet.forward.request_uri']}&parameters=${pageContext.request.queryString}">Русский</a>
            </div>
        </div>
    </div>
</div>
</body>
</html>
