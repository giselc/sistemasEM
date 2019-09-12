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
public class Notas extends HttpServlet {

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
            int idLibreta =Integer.valueOf(request.getParameter("idLibreta"));
            int ciProfesor = Integer.valueOf(request.getParameter("ciProfesor"));
            int ciAlumno= Integer.valueOf(request.getParameter("ciAlumno"));
            java.util.Calendar fechaAux = java.util.Calendar.getInstance();
            int mesFecha = fechaAux.get(java.util.Calendar.MONTH)+1;
            int dia = fechaAux.get(java.util.Calendar.DATE);
            String cero="",cerod="";
            if(mesFecha<10){
                cero="0";
            }
            if(dia<10){
                cerod="0";
            }
            String fecha =  fechaAux.get(java.util.Calendar.YEAR)+"-"+cero+mesFecha+"-"+cerod+dia;
            ManejadorBedelia mb =Manejadores.ManejadorBedelia.getInstance();
            if(request.getParameter("eliminar")!=null){
                int idNota=Integer.valueOf(request.getParameter("eliminar"));
                int tipo=Integer.valueOf(request.getParameter("tipo"));
                int mes=Integer.valueOf(request.getParameter("mes"));
                JsonObjectBuilder json = Json.createObjectBuilder(); 
                JsonArrayBuilder jab= Json.createArrayBuilder();
                if(mb.eliminarNota(idNota,idLibreta,ciProfesor,ciAlumno,tipo,mes)){
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
            else{ //agregar
                String obs = request.getParameter("obs");
                int tipo = Integer.valueOf(request.getParameter("tipo"));
                int mes = Integer.valueOf(request.getParameter("mes"));
                double valor = Double.valueOf(request.getParameter("valor"));
                int id = mb.agregarNota(ciAlumno,ciProfesor,idLibreta,tipo,mes,valor,obs,fecha);
                JsonObjectBuilder json = Json.createObjectBuilder(); 
                JsonArrayBuilder jab= Json.createArrayBuilder();
                if(id!=-1){
                    jab.add(Json.createObjectBuilder()
                        .add("mensaje","ok")
                        .add("id",id)
                        .add("fecha",fecha)
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
