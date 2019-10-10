/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets.Bedelia;

import Classes.Usuario;
import Manejadores.ManejadorBedelia;
import java.io.IOException;
import java.io.PrintWriter;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Gisel
 */
public class HistorialFaltas extends HttpServlet {

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
        HttpSession sesion = request.getSession();
        request.setCharacterEncoding("UTF-8");
        Usuario u = (Usuario)sesion.getAttribute("usuario");
        //.out.print("aca");
        if(u.isAdmin()|| u.isNotas() ||u.isProfesor()){
            response.setContentType("text/html;charset=UTF-8");
            String mensaje="";
            try (PrintWriter out = response.getWriter()) {
                ManejadorBedelia mp = ManejadorBedelia.getInstance();
                if(request.getParameter("imprimirFaltas")!=null){
                    mp.ImprmirFaltasxDia(out, request.getParameter("fecha"));
                }
                else{
                     if(request.getParameter("eliminar")!=null){
                        int id= Integer.valueOf(request.getParameter("eliminar"));
                        int idLibreta= Integer.valueOf(request.getParameter("idLibreta"));
                        int ciAlumno= Integer.valueOf(request.getParameter("ciAlumno"));
                        if(request.getParameter("sancion")!=null){
                            JsonObjectBuilder json = Json.createObjectBuilder(); 
                            JsonArrayBuilder jab= Json.createArrayBuilder();
                            if(mp.eliminarSancion(id,idLibreta,ciAlumno)){
                                jab.add(Json.createObjectBuilder()
                                    .add("mensaje","ok")
                                );
                                json.add("msj", jab);
                            }else{
                                jab.add(Json.createObjectBuilder()
                                    .add("mensaje","ERROR: contacte al administrador.")
                                );
                                json.add("msj", jab);
                            };
                            out.print(json.build());
                        }
                        else{
                            
                            int cantHorasFalta=mp.obtenerFalta(id,idLibreta,ciAlumno).getCanthoras();
                            JsonObjectBuilder json = Json.createObjectBuilder(); 
                            JsonArrayBuilder jab= Json.createArrayBuilder();
                            if(mp.eliminarFalta(id,idLibreta,ciAlumno)){
                                jab.add(Json.createObjectBuilder()
                                    .add("mensaje","ok")
                                        .add("cantHorasFalta",cantHorasFalta)
                                );
                                json.add("msj", jab);
                            }else{
                                jab.add(Json.createObjectBuilder()
                                    .add("mensaje","ERROR: contacte al administrador.")
                                );
                                json.add("msj", jab);
                            };
                            out.print(json.build());
                        }
                     }
                }
            }
            catch(Exception ex){
                mensaje = "ERROR: " + ex.getMessage();
                sesion.setAttribute("Mensaje", mensaje);
                System.out.print("Servlet-Libreta.java: "+mensaje);
                response.sendRedirect("libretas.jsp");
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
