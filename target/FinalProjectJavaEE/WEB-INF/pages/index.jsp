<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${not empty language ? language : pageContext.request.locale.language}"/>
<fmt:setBundle basename="localization.text" />
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="<c:url value="/assets/css/style.css" />" rel="stylesheet">
</head>
    <body>
        <jsp:include page="header.jsp"></jsp:include>
        <div class="center">
            <a href="/FinalProjectJavaEE"><img class="image-map" src="<c:url value="/assets/images/map.jpg"/>"></a>
        </div>
    </body>
</html>
