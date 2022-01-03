<%@ page import="java.util.List" %>
<%@ page import="by.incubator.DTO.VehicleDto" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.stream.Collectors" %>
<%@ page import="java.util.concurrent.atomic.AtomicReference" %>
<%@ page import="java.util.function.Predicate" %>
<%@ page import="java.util.Optional" %><%--
  Created by IntelliJ IDEA.
  User: Anton
  Date: 19.11.2021
  Time: 7:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
    <meta charset="UTF-8">
    <title>Look vehicles</title>
    <link href="/resources/css/style.css" rel="stylesheet">
</head>
<body>
    <div class="center flex full-vh">
        <div class="vertical-center">
            <%
                List<VehicleDto> dtoList = (List<VehicleDto>) request.getAttribute("cars");

                Set<String> uniqTypes = dtoList.stream().map(VehicleDto::getTypename).collect(Collectors.toSet());
                Set<String> uniqModels = dtoList.stream().map(VehicleDto::getModelName).collect(Collectors.toSet());
                Set<String> uniqColors = dtoList.stream().map(VehicleDto::getColor).collect(Collectors.toSet());
                Set<String> uniqEngineNames = dtoList.stream().map(VehicleDto::getEngineName).collect(Collectors.toSet());

                AtomicReference<Predicate<VehicleDto>> filter = new AtomicReference<>(vehicleDto -> true);

                Optional.ofNullable(request.getParameter("type")).filter(s -> !s.isEmpty()).ifPresent(s -> filter.set(filter.get().and(vehicleDto -> vehicleDto.getTypename().equals(s))));
                Optional.ofNullable(request.getParameter("model")).filter(s -> !s.isEmpty()).ifPresent(s -> filter.set(filter.get().and(vehicleDto -> vehicleDto.getModelName().equals(s))));
                Optional.ofNullable(request.getParameter("color")).filter(s -> !s.isEmpty()).ifPresent(s -> filter.set(filter.get().and(vehicleDto -> vehicleDto.getColor().equals(s))));
                Optional.ofNullable(request.getParameter("engine")).filter(s -> !s.isEmpty()).ifPresent(s -> filter.set(filter.get().and(vehicleDto -> vehicleDto.getEngineName().equals(s))));

                dtoList = dtoList.stream().filter(filter.get()).collect(Collectors.toList());
            %>
            <a class="ml-20" href="/">Main</a>
            <a class="ml-20" href="/viewCars">Clear filter</a>
            <br />
            <br />
            <hr />
            <br />
            <table>
                <caption>Cars</caption>
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
                    <th>&#x1F4CB;</th>
                </tr>
                <%if (dtoList.size() == 0) {%>
                <tr><td colspan="10">No cars according to parameters</td></tr>
                <%}%>
                <%
                    for(VehicleDto dto : dtoList) {
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
                    <td><a href="<%="info?id=" + dto.getId()%>">&#x1F4C3;</a></td>
                </tr>
                <%}%>
            </table>
            <br />
            <div>
                <hr />
                <br />
                <form method="get" action="/viewCars" class="flex">
                    <div>
                        <p>Type</p>
                        <select name="type">
                            <option value="" <%=request.getParameter("type")==null?"selected":""%>>Not choose</option>
                            <%for (String s : uniqTypes) {%>
                            <option value="<%=s%>" <%=(request.getParameter("type")!=null && s.equals(request.getParameter("type"))?"selected":"")%>><%=s%></option>
                            <%}%>
                        </select>
                    </div>
                    <div class="ml-20">
                        <p>Model</p>
                        <select name="model">
                            <option value="" <%=request.getParameter("model")==null?"selected":""%>>Not choose</option>
                            <%for (String s : uniqModels) {%>
                            <option value="<%=s%>" <%=(request.getParameter("model")!=null && s.equals(request.getParameter("model"))?"selected":"")%>><%=s%></option>
                            <%}%>
                        </select>
                    </div>
                    <div class="ml-20">
                        <p>Engine</p>
                        <select name="engine">
                            <option value="" <%=request.getParameter("engine")==null?"selected":""%>>Not choose</option>
                            <%for (String s : uniqEngineNames) {%>
                            <option value="<%=s%>" <%=(request.getParameter("engine")!=null && s.equals(request.getParameter("engine"))?"selected":"")%>><%=s%></option>
                            <%}%>
                        </select>
                    </div>
                    <div class="ml-20">
                        <p>Model</p>
                        <select name="color">
                            <option value="" <%=request.getParameter("color")==null?"selected":""%>>Not choose</option>
                            <%for (String s : uniqColors) {%>
                            <option value="<%=s%>" <%=(request.getParameter("color")!=null && s.equals(request.getParameter("color"))?"selected":"")%>><%=s%></option>
                            <%}%>
                        </select>
                    </div>
                    <button class="ml-20" type="submit">Choose</button>
                </form>
                <br />
                <hr />
            </div>
        </div>
    </div>
</body>
</html>
