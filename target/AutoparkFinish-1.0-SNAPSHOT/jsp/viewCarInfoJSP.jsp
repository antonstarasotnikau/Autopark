<%@ page import="by.incubator.DTO.VehicleDto" %>
<%@ page import="java.util.List" %>
<%@ page import="by.incubator.DTO.RentDto" %><%--
  Created by IntelliJ IDEA.
  User: Anton
  Date: 19.11.2021
  Time: 8:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
    <meta charset="UTF-8">
    <title>Look car info</title>
    <link href="/resources/css/style.css" rel="stylesheet">
</head>
<body>
<div class="center flex full-vh">
    <div class="vertical-center">
        <a class="ml-20" href="/">Main</a>
        <a class="ml-20" href="/viewCars">Back</a>
        <br />
        <hr />
        <br />

        <%
            List<VehicleDto> vehicleDtoList = (List<VehicleDto>) request.getAttribute("cars");
            List<RentDto> rentDtoList = (List<RentDto>) request.getAttribute("rents");
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
                <th>Per 100 km</th>
                <th>Tax Coefficient</th>
                <th>Km on full tank</th>
            </tr>
            <%if (vehicleDtoList.size() == 0) {%>
            <tr><td colspan="12">No car according to parameters</td></tr>
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
                <td><%=dto.getPer100km()%></td>
                <td><%=dto.getTaxCoefficient()%></td>
                <td><%=dto.getMaxKm()%></td>
            </tr>
            <%}%>
        </table>
        <br />
        <%="Tax for month: " + vehicleDtoList.get(0).getTax()%>
        <br />
        <hr />
        <br />
        <table>
            <caption>Car</caption>
            <tr>
                <th>Date</th>
                <th>Income</th>
            </tr>
                <%if (rentDtoList.size() == 0) {%>
                <tr><td colspan="2">No rents according to parameters</td></tr>
                <%}%>
                <%
                for(RentDto dto : rentDtoList) {
            %>
            <tr>
                <td><%=dto.getDateRent()%></td>
                <td><%=dto.getIncomeRent()%></td>
            </tr>
            <%}%>
        </table>
        <br />
        <%="Sum: " + vehicleDtoList.get(0).getIncome()%>
        <%="Income: " + (vehicleDtoList.get(0).getIncome() - vehicleDtoList.get(0).getTax())%>
    </div>
</div>
</body>
</html>
