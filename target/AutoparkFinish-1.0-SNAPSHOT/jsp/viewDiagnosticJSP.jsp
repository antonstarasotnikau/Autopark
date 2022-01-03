<%@ page import="by.incubator.DTO.VehicleDto" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %><%--
  Created by IntelliJ IDEA.
  User: Anton
  Date: 19.11.2021
  Time: 9:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
    <meta charset="UTF-8">
    <title>Month report</title>
    <link href="/resources/css/style.css" rel="stylesheet">
</head>
<body>
<div class="center flex full-vh">
    <div class="vertical-center">
        <a class="ml-20" href="/">Main</a>
        <br />
        <hr />
        <br />

        <%
            List<VehicleDto> vehicleDtoList = (List<VehicleDto>) request.getAttribute("cars");
            Map<Integer, Boolean> wasBroken = (Map<Integer, Boolean>) request.getAttribute("wasBroken");
            Map<Integer, Boolean> isRepaired = (Map<Integer, Boolean>) request.getAttribute("isRepaired");
        %>
        <table>
            <caption>Car</caption>
            <tr>
                <th>Type</th>
                <th>Model</th>
                <th>Registration Number</th>
                <th>Weight</th>
                <th>Manufacture Year</th>
                <th>Color</th>
                <th>Engine model</th>
                <th>Mileage</th>
                <th>Tank</th>
                <th>Was broken</th>
                <th>Repaired</th>
            </tr>
            <%if (vehicleDtoList.size() == 0) {%>
            <tr><td colspan="11">No car according to parameters</td></tr>
            <%}%>
            <%
                for(VehicleDto dto : vehicleDtoList) {
            %>
            <tr>
                <td><%=dto.getTypename()%></td>
                <td><%=dto.getModelName()%></td>
                <td><%=dto.getRegistrationNumber()%></td>
                <td><%=dto.getWeight()%></td>
                <td><%=dto.getManufactureYear()%></td>
                <td><%=dto.getColor()%></td>
                <td><%=dto.getEngineName()%></td>
                <td><%=dto.getMileage()%></td>
                <td><%=dto.getTankVolume()%></td>
                <td><%=wasBroken.get(dto.getId())%></td>
                <td><%=isRepaired.get(dto.getId())%></td>
            </tr>
            <%}%>
        </table>
        <br />
    </div>
</div>
</body>
</html>
