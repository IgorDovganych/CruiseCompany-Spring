<%@page contentType="text/html" pageEncoding="UTF-8" %>

<%@page import="java.util.ArrayList" %>

<%@page import="com.model.pojo.Emp" %>

<%@page import="java.util.Date" %>

<%@page import="java.text.SimpleDateFormat" %>

<%@page import="com.dao.empDao" %>

<%@page import="java.util.List" %>

<jsp:useBean id="emp" class="com.model.pojo.Emp">

</jsp:useBean>

<jsp:setProperty property="*" name="emp"/>

<%@ taglib prefix="display" uri="http://www.displaytag.org/1.2" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

<head>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


    <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/themes/smoothness/jquery-ui.css">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js" type="text/javascript"></script>

    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"
            type="text/javascript"></script>

    <script type="text/javascript">
        $(document).ready(function () {

            $("#datepicker").datepicker({

                showButtonPanel: true,

                showOn: "button",

                buttonText: "Select date",

                dateFormat: 'yy-mm-dd',

                changeMonth: true,

                changeYear: true,

                yearRange: "-90:"


            });

        });
    </script>

    <title>Display tag Pagination and Sorting Example in JSP</title>

</head>


<%


    empDao dao = new empDao();

    List empList = dao.getAllEmployee();

    request.setAttribute("empList", empList);

    int count = dao.getAllEmployee().size();

    System.out.println("No of Record: " + count);

    String name = request.getParameter("ename");

    String sd = request.getParameter("sd");

    System.out.println(sd);

    System.out.println(name);

    if (name != null) {


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date dob = sdf.parse(sd);

        emp.setDob(dob);

        dao.add(emp);

        empList = dao.getAllEmployee();

        request.setAttribute("empList", empList);

    }

%>


<body>

<form name="f1" action="index.jsp" method="post">

    <table width="471" border="1">
        <tr>
            <th>Employee Name</th>
            <td><input name="ename" type="text"></td>

        </tr>
        <tr>
            <th>Salary</th>
            <td><input name="sal" type="text"></td>
        </tr>
        <tr>
            <th>Date of Birth</th>
            <td><input name="sd" type="text" id="datepicker"></td>
        </tr>
        <tr>
            <th colspan="2"><input type="submit" onclick=update(EmpTable) name="s1" value="Add">
            </th>
        </tr>
    </table>
</form>

<display:table id="EmpTable" name="empList" class="dataTable" export="true" pagesize="10" cellpadding="5px;" uid="row"
               cellspacing="5px;" style="margin-left:50px;margin-top:20px;" requestURI="">

    <display:column property="ename" title="Name" sortable="true"/>

    <display:column property="sal" title="Salary"/>

    <display:column property="dob" format="{0,date,dd-MMM-yyyy}" title="BirthDate"/>

    <display:column property="empno" format="Edit" href="updateEmp.jsp" paramId="empno" paramProperty="empno"
                    title="Action"/>

    <display:column property="empno" format="Delete" href="deleteEmp.jsp" paramId="empno" paramProperty="empno"
                    title="Action"/>

    <display:setProperty name="export.excel.filename" value="EmployeeDetails.xls"/>

    <display:setProperty name="export.pdf.filename" value="EmployeeDetails.pdf"/>

    <display:setProperty name="export.csv.filename" value="EmployeeDetails.csv"/>

</display:table>

</body>

</html>