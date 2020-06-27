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
    <script src="<c:url value="/assets/js/create_route.js"/>"></script>
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
    <br>
    <fmt:message key="addPortsToRoute"/>
</p>
<div style="background: green; margin-left: 550px" class="center">

    <div style="background: antiquewhite" class="left-float">

        <table >
            <thead>
            <th><fmt:message key="port"/></th>
            <th></th>
            </thead>
            <tbody>
            <c:forEach items="${ports}" var="port">
                <tr>
                    <td hidden>${port.id}</td>
                    <td>${port.name}</td>
                    <td>
                        <button onclick="addPortToRoute(${port.id})">+</button>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <div style="background: brown" class="left-float">
        <table id="route"></table>
    </div>
    <br>
    <br>
    <br>
    <br>
    <br>
    <br>
    <br>
    <br>
</div>

<div style="background: aliceblue" class="center">
    <p class="center">
        <label for="start">Start date:</label>

        <input type="date" id="start" name="trip-start"
               value="2018-07-22"
               min="2020-01-01" max="2023-12-31">
        <br>

        <label for="end">End date:</label>

        <input type="date" id="end" name="trip-end"
               value="2018-07-22"
               min="2020-01-01" max="2023-12-31">
    </p>
</div>
<p class="center">
<fmt:message key="price"/>:
<br>
<div class="slidecontainer center" >
    <input type="range" min="50" max="1000" value="${price}" class="slider" id="basePrice" name="price">
    <br>
    <fmt:message key="value"/>: <span id="demo"></span> $
    <br>
    <button onclick="sendRequest('/FinalProjectSpring/admin/create_cruise', method='post')">
        <fmt:message key="createCruise"/>
    </button>
</div>
<script>
    var slider = document.getElementById("basePrice");
    var output = document.getElementById("demo");
    output.innerHTML = slider.value;

    slider.oninput = function () {
        output.innerHTML = this.value;
    }
</script>
</p>
</body>
</html>
