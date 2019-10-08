/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Classes.RecordCamposListar;
import Classes.Usuario;
import Manejadores.ManejadorPersonal;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Gisel
 */
public class Listar extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        HttpSession sesion = request.getSession();
        Usuario u = (Usuario)sesion.getAttribute("usuario");
        int tipoPersonal=Integer.valueOf(request.getParameter("tipoPersonal"));
        if(u.isAdmin()|| (u.getPermisosPersonal()!=null && u.getPermisosPersonal().getId()!=tipoPersonal) || u.isProfesor() || u.isNotas()){
            try (PrintWriter out = response.getWriter()) {
                /* TODO output your page here. You may use following sample code. */
                String[] lista= request.getParameterValues("List[]");
                ManejadorPersonal mp = ManejadorPersonal.getInstance();
                RecordCamposListar rl= new RecordCamposListar();
                rl.ficha=request.getParameter("fichas")!=null;
                if(!rl.ficha){
                    if(tipoPersonal==1){
                        rl.carrera= request.getParameter("carrera")!=null && request.getParameter("carrera").equals("on");
                        rl.numero= request.getParameter("numero")!=null && request.getParameter("numero").equals("on");
                        rl.ci= request.getParameter("ci")!=null && request.getParameter("ci").equals("on");
                        rl.grado= request.getParameter("grado")!=null && request.getParameter("grado").equals("on");
                        rl.primerNombre= request.getParameter("primerNombre")!=null && request.getParameter("primerNombre").equals("on");
                        rl.segundoNombre= request.getParameter("segundoNombre")!=null && request.getParameter("segundoNombre").equals("on");
                        rl.primerApellido= request.getParameter("primerApellido")!=null && request.getParameter("primerApellido").equals("on");
                        rl.segundoApellido= request.getParameter("segundoApellido")!=null && request.getParameter("segundoApellido").equals("on");
                        rl.curso= request.getParameter("curso")!=null && request.getParameter("curso").equals("on");
                        rl.arma= request.getParameter("arma")!=null && request.getParameter("arma").equals("on");
                        rl.lmga= request.getParameter("lmga")!=null && request.getParameter("lmga").equals("on");
                        rl.pd= request.getParameter("pd")!=null && request.getParameter("pd").equals("on");
                        rl.sexo= request.getParameter("sexo")!=null && request.getParameter("sexo").equals("on");
                        rl.dptoNac= request.getParameter("dptoNac")!=null && request.getParameter("dptoNac").equals("on");
                        rl.localidadNac= request.getParameter("localidadNac")!=null && request.getParameter("localidadNac").equals("on");
                        rl.dptoDom= request.getParameter("dptoDom")!=null && request.getParameter("dptoDom").equals("on");
                        rl.localidadDom= request.getParameter("localidadDom")!=null && request.getParameter("localidadDom").equals("on");
                        rl.repitiente= request.getParameter("repitiente")!=null && request.getParameter("repitiente").equals("on");
                        rl.cantHijos= request.getParameter("cantHijos")!=null && request.getParameter("cantHijos").equals("on");
                        rl.talleOperacional= request.getParameter("talleOperacional")!=null && request.getParameter("talleOperacional").equals("on");
                        rl.talleBotas= request.getParameter("talleBotas")!=null && request.getParameter("talleBotas").equals("on");
                        rl.talleQuepi= request.getParameter("talleQuepi")!=null && request.getParameter("talleQuepi").equals("on");
                    }
                    if(request.getParameter("filtro")!=null){
                        rl.filtro=request.getParameter("filtro");
                    }
                }
                mp.imprimirListado(lista,rl,out,request.getContextPath(),tipoPersonal);
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
