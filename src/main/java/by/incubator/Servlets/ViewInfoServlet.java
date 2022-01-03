package by.incubator.Servlets;

import by.incubator.DTO.Facade.FacadeDto;
import by.incubator.Entity.VehicleCollection;
import by.incubator.Order.Fixer;
import by.incubator.Order.SQLMechanicService;
import by.incubator.Test.MainApplication;
import by.incubator.WorkWithFiles.Parser;
import by.incubator.WorkWithFiles.ParserBD.ParserVehicleFromBD;
import by.incubator.infrastructure.core.annotations.Autowired;
import by.incubator.infrastructure.core.impl.ApplicationContext;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet("/info")
public class ViewInfoServlet extends HttpServlet {
    FacadeDto vehicleTypeService;

    @Override
    public void init() throws ServletException {
        super.init();
        vehicleTypeService = vehicleTypeService = MainApplication.createContext().getObject(FacadeDto.class);;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        request.setAttribute("cars", vehicleTypeService.getVehicles().
                stream().filter(vehicleDTO -> id == vehicleDTO.getId()).
                collect(Collectors.toList()));
        request.setAttribute("rents", vehicleTypeService.getRent(id));
        //request.setAttribute("rents", vehicleTypeService.getRent(id));
        RequestDispatcher dispatcher = this.getServletContext().
                getRequestDispatcher("/jsp/viewCarInfoJSP.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
