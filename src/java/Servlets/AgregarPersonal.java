/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Classes.Usuario;
import Manejadores.ManejadorPersonalBD;
import Manejadores.ManejadorProfesores;
import Manejadores.ManejadorProfesoresBD;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Gisel
 */
public class AgregarPersonal extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ManejadorPersonalBD mp = new ManejadorPersonalBD();
        HttpSession sesion = request.getSession();
        Usuario u = (Usuario)sesion.getAttribute("usuario");
        int tipo= Integer.valueOf(request.getParameter("tipo"));
        if(u.isAdmin()){
            switch(tipo){
                case 1: mp.AgregarCadetesTXT(); break;
                case 2: case 3: mp.AgregarPersonalTXT(tipo); break;
                case 4: 
                    ManejadorProfesoresBD mprof = new ManejadorProfesoresBD();
                    mprof.agregarProfesoresTXT(); break;
            }
        }
        else{
            sesion.setAttribute("Mensaje","Lo sentimos, no tiene permisos para acceder a esta p&aacute;gina. Contacte al administrador.");
                response.sendRedirect("");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
