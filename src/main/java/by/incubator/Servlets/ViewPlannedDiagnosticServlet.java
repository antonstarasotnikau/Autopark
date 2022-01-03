package by.incubator.Servlets;

import by.incubator.DTO.Facade.FacadeDto;
import by.incubator.Entity.Manager;
import by.incubator.Entity.VehicleCollection;
import by.incubator.Entity.Workroom;
import by.incubator.Order.Fixer;
import by.incubator.Test.MainApplication;
import by.incubator.infrastructure.threads.annotations.Schedule;
import lombok.SneakyThrows;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@WebServlet("/viewPlannedDiagnostic")
public class ViewPlannedDiagnosticServlet extends HttpServlet {
    FacadeDto vehicleTypeService;
    VehicleCollection vehicleCollection;
    Workroom workroom;
    Manager manager;
    private long startLastTime;

    @Override
    public void init() throws ServletException {
        super.init();
        vehicleTypeService = MainApplication.getContext().getObject(FacadeDto.class);
        vehicleCollection = MainApplication.getContext().getObject(VehicleCollection.class);
        workroom = MainApplication.getContext().getObject(Workroom.class);
        manager = MainApplication.getContext().getObject(Manager.class);
        startLastTime = manager.loadVehicleAndCheckServlet(workroom.getMechanic(), vehicleCollection);
    }

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<Integer, Boolean> wasBroken = workroom.getInfoAllStateVehicle(vehicleCollection.getVehicles());
        Map<Integer, Boolean> isRepaired = workroom.getInfoAllRepairVehicle(vehicleCollection.getVehicles());
ServiceLoader
        /*long delta = manager.getClass().getDeclaredMethod(
                "loadVehicleAndCheck", Fixer.class, VehicleCollection.class).getAnnotation(Schedule.class).delta();*/
        long delta = 300000;
        long lastStart = new Date().getTime() - startLastTime;

        request.setAttribute("delta", delta);
        request.setAttribute("lastStart", lastStart);

        request.setAttribute("cars", vehicleTypeService.getVehicles());
        request.setAttribute("wasBroken", wasBroken);
        request.setAttribute("isRepaired", isRepaired);
        RequestDispatcher dispatcher = this.getServletContext().
                getRequestDispatcher("/jsp/viewPlannedDiagnosticJSP.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
