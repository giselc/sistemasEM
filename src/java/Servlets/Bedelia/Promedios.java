/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets.Bedelia;

import Classes.Bedelia.LibretaIndividual;
import Classes.Bedelia.Promedio;
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
public class Promedios extends HttpServlet {

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
        request.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            int idLibreta= Integer.valueOf(request.getParameter("idLibreta"));
            Manejadores.ManejadorBedelia mb = ManejadorBedelia.getInstance();
            if(request.getParameter("cambiarGrilla")!=null){
                int mes = Integer.valueOf(request.getParameter("mes"));
                String html= mb.cambiarGrillaPromedio(idLibreta,mes,request.getContextPath());
                JsonObjectBuilder json = Json.createObjectBuilder(); 
                JsonArrayBuilder jab= Json.createArrayBuilder();
                jab.add(Json.createObjectBuilder()
                    .add("html",html)
                );
                json.add("msj", jab);
                out.print(json.build());
            }
            else{
                Classes.Bedelia.Libreta l = mb.getLibretas().get(idLibreta);
                Promedio promedio=null;
                double valorPromedio = 0;
                String juicio="";
                int mes = Integer.valueOf(request.getParameter("mesAgregarNota"));
                if(request.getParameter("guardarPromedios")!=null || request.getParameter("cerrarPromedios")!=null){
                   for(LibretaIndividual li : l.getLibretasIndividuales().values()){
                       if(l.getMateria().isSecundaria()){
                           valorPromedio = Double.valueOf(request.getParameter("PROMEDIO-"+li.getAlumno().getCi()));
                           if(request.getParameter("JUICIO-"+li.getAlumno().getCi())!=null){
                               juicio=request.getParameter("JUICIO-"+li.getAlumno().getCi());
                           }
                       }
                       else{
                           valorPromedio = mb.calculoPromedio(li, mes);
                       }
                       if(request.getParameter("guardarPromedios")!=null ){
                           mb.guardarPromedio(li,valorPromedio,mes,juicio,false);
                       }
                       else{
                           mb.guardarPromedio(li,valorPromedio,mes,juicio,true);
                       }
                   }
                   if(request.getParameter("JUICIOGRUPAL")!=null){
                       mb.guardarJuicioGrupal(request.getParameter("JUICIOGRUPAL"),mes);
                   }
                }
            }
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
