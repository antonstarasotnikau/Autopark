<%@ page import="java.util.Comparator" %>
<%@ page import="by.incubator.DTO.VehicleTypeDto" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: Anton
  Date: 16.11.2021
  Time: 12:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
  <meta charset="UTF-8">
    <title>Look vehicle types</title>
  <link href="/resources/css/style.css" rel="stylesheet">
</head>
<body>
  <div class="center flex full-vh">
    <div class="vertical-center">
      <a href="/" class="ml-20">Main</a>
      <br />
      <br />
      <hr />
      <br />
      <%
        String sortKey = null;
        String order = null;
        if (request.getParameter("name") != null) sortKey = "name";
        if (request.getParameter("tax") != null) sortKey = "tax";
        if (request.getParameter("asc") != null) order = "asc";
        if (request.getParameter("desc") != null) order = "desc";
      %>
      <%if (sortKey != null) {%>
        <%
          String clearPath = "/viewTypes";
          String ascPath = "?" + sortKey + "&asc";
          String descPath = "?" + sortKey + "&desc";
        %>
        <div>
          <a class="ml-20" href="<%=descPath%>">Sort by decrement</a>
          <a class="ml-20" href="<%=ascPath%>">Sort ascending</a>
          <a class="ml-20" href="<%=clearPath%>">Clear parameters searching</a>
        </div>
        <br />
        <hr />
        <br />
      <%}%>
      <table>
        <caption>Type vehicle</caption>
        <tr>
          <th>Name</th>
          <th>Coefficient Tax</th>
        </tr>
        <%
          List<VehicleTypeDto> dtoList = (List<VehicleTypeDto>) request.getAttribute("types");
          Comparator<VehicleTypeDto> comporator = null;
          if (sortKey != null && sortKey.equals("name")) {
            comporator = Comparator.comparing(
                    vehicleTypeDto -> vehicleTypeDto.getName());
          }
          if (sortKey != null && sortKey.equals("tax")) {
            comporator = Comparator.comparing(
                    vehicleTypeDto -> vehicleTypeDto.getTaxCoefficient());
          }
          if (comporator != null) {
            dtoList.sort(comporator);
          }
          for(VehicleTypeDto dto :dtoList) {
        %>
        <tr>
          <td><%=dto.getName()%></td>
          <td><%=dto.getTaxCoefficient()%></td>
        </tr>
        <%}%>
      </table>
      <%if(dtoList.size() > 0) {%>
      <p>Min coefficient:
        <strong><%=dtoList.stream().map(VehicleTypeDto::getTaxCoefficient).min(Double::compare).get()%></strong>
      </p>
      <p>Max coefficient:
        <strong><%=dtoList.stream().map(VehicleTypeDto::getTaxCoefficient).max(Double::compare).get()%></strong>
      </p>
      <%}%>
      <br />
      <hr />
      <br />
      <div>
        <% if (request.getParameter("name") == null) {%>
          <a class="ml-20" href="/viewTypes?name">Sort by name</a><%}%>
        <% if (request.getParameter("tax") == null) {%>
        <a class="ml-20" href="/viewTypes?tax">Sort by coefficient</a><%}%>
      </div>
    </div>
  </div>
</body>
</html>
