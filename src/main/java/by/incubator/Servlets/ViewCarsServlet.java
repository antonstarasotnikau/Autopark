package by.incubator.Servlets;

import by.incubator.DTO.Facade.FacadeDto;
import by.incubator.DTO.VehicleTypeDto;
import by.incubator.Entity.VehicleCollection;
import by.incubator.Order.Fixer;
import by.incubator.Order.SQLMechanicService;
import by.incubator.Test.MainApplication;
import by.incubator.WorkWithFiles.Parser;
import by.incubator.WorkWithFiles.ParserBD.ParserVehicleFromBD;
import by.incubator.infrastructure.core.annotations.Autowired;
import by.incubator.infrastructure.core.impl.ApplicationContext;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/viewCars")
public class ViewCarsServlet extends HttpServlet {

    FacadeDto vehicleTypeService;

    @Override
    public void init() throws ServletException {
        super.init();
        vehicleTypeService = MainApplication.getContext().getObject(FacadeDto.class);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("cars", vehicleTypeService.getVehicles());
        RequestDispatcher dispatcher = this.getServletContext().
                getRequestDispatcher("/jsp/viewCarsJSP.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
