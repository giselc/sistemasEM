/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Classes.Usuario;
import Manejadores.ManejadorCodigos;
import Manejadores.ManejadorPersonal;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 *
 * @author Gisel
 */
@MultipartConfig
public class Documento extends HttpServlet {

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
        if(u.isAdmin()||u.getPermisosPersonal().getId()==1){
            try (PrintWriter out = response.getWriter()) {
                /* TODO output your page here. You may use following sample code. */
                ManejadorPersonal mp = ManejadorPersonal.getInstance();
                ManejadorCodigos mc = ManejadorCodigos.getInstance();
                int ci= Integer.valueOf(request.getParameter("ci"));
                int idTipoPersonal= Integer.valueOf(request.getParameter("idTipoPersonal"));
                if(request.getParameter("id")!= null){ //alta
                    int id= Integer.valueOf(request.getParameter("id"));
                    Part documento = request.getPart("documento");
                    int tipoDocumento= Integer.valueOf(request.getParameter("tipoDocumento"));
                    if(id==-1){ //alta
                        String descripcion = request.getParameter("descipcion");
                        boolean exito = mp.altaDocumento(mc.getTipoDocumento(tipoDocumento), ci,mc.getTipoPersonal(idTipoPersonal), documento,descripcion);
                        if(exito){
                            sesion.setAttribute("Mensaje", "Documento agregado correctamente.");
                        }
                        else{
                            sesion.setAttribute("Mensaje", "ERROR al agregar el documento.");
                        }
                    }
                }
                else{
                    if(request.getParameter("elim")!= null){
                        int idDocumento=Integer.valueOf(request.getParameter("elim"));
                        if(mp.bajaDocumento(ci, mc.getTipoPersonal(idTipoPersonal), idDocumento)){
                            sesion.setAttribute("Mensaje", "Documento eliminado correctamente.");
                        }
                        else{
                            sesion.setAttribute("Mensaje", "ERROR al eliminar el documento.");
                        }
                    }
                }
                if(idTipoPersonal==1){
                    response.sendRedirect("cadete.jsp?id="+request.getParameter("ci"));
                }
                else{
                    response.sendRedirect("personal.jsp?ci="+request.getParameter("ci"));
                }
            }
        }
        else{
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
