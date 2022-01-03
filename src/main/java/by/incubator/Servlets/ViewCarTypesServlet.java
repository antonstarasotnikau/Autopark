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

@WebServlet("/viewTypes")
public class ViewCarTypesServlet extends HttpServlet {
    FacadeDto vehicleTypeService;

    @Override
    public void init() throws ServletException {
        super.init();
        vehicleTypeService = vehicleTypeService = MainApplication.createContext().getObject(FacadeDto.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("types", vehicleTypeService.getVehicleTypes());
        //req.setAttribute("types", vehicleTypeService.getTypes());
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/jsp/viewTypesJSP.jsp");
        dispatcher.forward(req,resp);
    }
}
