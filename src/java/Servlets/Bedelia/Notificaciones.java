/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets.Bedelia;

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

/**
 *
 * @author Gisel
 */
public class Notificaciones extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            Manejadores.ManejadorBedelia mb = ManejadorBedelia.getInstance();
            JsonObjectBuilder json = Json.createObjectBuilder(); 
            JsonArrayBuilder jab= Json.createArrayBuilder();
            if(request.getParameter("hayNotificaciones")!=null){
                jab.add(Json.createObjectBuilder()
                    .add("cantNotificaciones", mb.getNotificacionesNuevas().size())
                );
                json.add("notificaciones", jab);
            }
            else{
                if(request.getParameter("marcarLeido")!=null){
                    //System.out.print(request.getParameter("marcarLeido"));
                    boolean aLeido=request.getParameter("marcarLeido").equals("true");
                    int id= Integer.valueOf(request.getParameter("id"));
                    if(mb.marcarLeidoNotificacion(id,aLeido)){
                        jab.add(Json.createObjectBuilder()
                            .add("mensaje","ok")
                        );
                        json.add("msj", jab);
                    }
                    else{
                        jab.add(Json.createObjectBuilder()
                            .add("mensaje","ERROR: contacte al administrador.")
                        );
                        json.add("msj", jab);
                    };
                }
                else{
                    if(request.getParameter("eliminar")!=null){
                        boolean esLeido=request.getParameter("eliminar").equals("true");
                        int id= Integer.valueOf(request.getParameter("id"));
                        if(mb.eliminarNotificacion(id,esLeido)){
                            jab.add(Json.createObjectBuilder()
                                .add("mensaje","ok")
                            );
                            json.add("msj", jab);
                        }
                        else{
                            jab.add(Json.createObjectBuilder()
                                .add("mensaje","ERROR: contacte al administrador.")
                            );
                            json.add("msj", jab);
                        };
                    }
                }
            }
            out.print(json.build());

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
