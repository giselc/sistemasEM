/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Manejadores.ManejadorCodigoBD;
import Classes.Usuario;
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
public class login extends HttpServlet {

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
            throws ServletException, IOException{
        response.setContentType("text/html;charset=UTF-8");
        try{
            HttpSession sesion = request.getSession();
            sesion.setAttribute("usuarioID", null);
            String usu, pass;
            usu = request.getParameter("user");
            pass = request.getParameter("password");
            //deberíamos buscar el usuario en la base de datos, pero dado que se escapa de este tema, ponemos un ejemplo en el mismo código
            ManejadorCodigoBD mc = new ManejadorCodigoBD();
            int id= mc.loginCorrecto(usu, pass);
            if (id != -1){
                sesion.setAttribute("usuarioID", id);
                Usuario u = mc.getUsuario(id);
                sesion.setAttribute("usuario", u);
                if(u.isAdmin()){
                    sesion.setAttribute("inicio", "sistemas.jsp");
                    response.sendRedirect("sistemas.jsp");
                }
                else{
                    if(u.getPermisosPersonal()!=null){
                        sesion.setAttribute("inicio", "personal.jsp?id="+u.getPermisosPersonal().getId());
                        response.sendRedirect("personal.jsp");
                    }
                    else{
                        if(u.getPermisosDescuento()!=null){
                            sesion.setAttribute("inicio", "descuentos.jsp?id="+u.getPermisosDescuento().getId());
                            response.sendRedirect("descuentos.jsp");
                        }
                        else{
                            if(u.isNotas()){
                                sesion.setAttribute("inicio", "notas.jsp");
                                response.sendRedirect("notas.jsp");
                            }
                            else{
                                if(u.isHabilitacion()){
                                    sesion.setAttribute("inicio", "habilitacion.jsp");
                                    response.sendRedirect("habilitacion.jsp");
                                }
                                else{
                                    sesion.setAttribute("usuarioID", null);
                                    sesion.setAttribute("usuario", null);
                                    sesion.setAttribute("login", "sinpermisos");
                                    response.sendRedirect("");
                                }
                            }
                        }
                    }
                }
            }
            else{
                sesion.setAttribute("login", "incorrecto");
                response.sendRedirect("");
            }
        }
        catch(Exception e){
            
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
