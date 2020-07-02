<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
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
    <c:forEach items="${users}" var="user" begin="0" end="10">
        <c:if test="${sessionScope.user.id!=user.id}">
            <tr>
                <td>${user.name}</td>
                <td>${user.email}</td>
                <td>${user.role}</td>
                <td>
                    <a href='/FinalProjectSpring/admin/update_user_page?id=${user.id}'>
                        <button class='w3-button w3-green w3-tiny w3-round'>
                            <fmt:message key="update"/>
                        </button>
                    </a>
                </td>
            </tr>
        </c:if>
    </c:forEach>
    <tr>
        <td colspan="4">
            <fmt:message key="currentPage"/> : ${page} |
            <fmt:message key="users"/> : ${numOfUsers} |
            <c:forEach var="page" begin="1" end="${numOfPages}">
                 <a href="/FinalProjectSpring/admin/users?page=${page}">${page}</a>
            </c:forEach>
        </td>
    </tr>
<%--    <tr>--%>
<%--        <td colspan="2"></td>--%>
<%--        <td colspan="2"><a href="${pageScope.myUrl}">nextPage</a></td>--%>
<%--    </tr>--%>

    </tbody>

</table>

<%--<display:table id="usersTable" name="users" class="dataTable" export="true" pagesize="10" cellpadding="5px;" uid="row"--%>
<%--               cellspacing="5px;" style="margin-left:50px;margin-top:20px;" requestURI="">--%>
<%--</display:table>--%>
</body>
</html>
