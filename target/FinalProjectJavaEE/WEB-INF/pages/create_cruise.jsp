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
    <title>Create Cruise</title>
    <script src="<c:url value="/assets/js/cruise_info_functions.js"/>"></script>
</head>
<body>
<jsp:include page="header.jsp"></jsp:include>
<p class="center">

    <label for="ships"><fmt:message key="selectShip"/></label>

    <select id="ships">
        <c:forEach items="${ships}" var="ship">
            <option value="${ship.id}">${ship.model}</option>
        </c:forEach>
    </select>


    <c:forEach items="${ports}" var="port">
        <br>${port.name}
    </c:forEach>
</p>
</body>
</html>
