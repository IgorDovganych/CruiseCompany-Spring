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
<form action="/FinalProjectSpring/admin/create_excursion" method="post">
<p class="center">
    <div class="center">
    <c:if test="${error_message!=null}">
         <p class="center red"><fmt:message key="${error_message}"/></p>
    </c:if>

        <label for="ports"><fmt:message key="selectPort"/></label>

        <select id="ports" name="port">
            <c:forEach items="${ports}" var="port">
                <option value="${port.id}" >${port.name}</option>
            </c:forEach>
        </select>
        <br>
        <fmt:message key="excursionName"/>:
        <br>
        <input type="text" name="name" value="${name}">
        <br>
        <fmt:message key="addDescription"/>:
        <br>
        <input type="text" name="description" value="${description}">
        <br>
        <fmt:message key="price"/>:
        <br>
        <div class="slidecontainer">
            <input type="range" min="1" max="100" value="${price}" class="slider" id="myRange" name="price">
            <br>
        <fmt:message key="value"/>: <span id="demo"></span> $
        </div>
        <script>
            var slider = document.getElementById("myRange");
            var output = document.getElementById("demo");
            output.innerHTML = slider.value;

            slider.oninput = function () {
                output.innerHTML = this.value;
            }
        </script>
        <input type="submit" value="<fmt:message key="createExcursion"/>">
    </div>
    </p>
    </form>



</body>
</html>
