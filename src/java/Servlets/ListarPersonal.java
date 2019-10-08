/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Classes.Cadete;
import Manejadores.ManejadorPersonal;
import Classes.Personal;
import Classes.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
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
public class ListarPersonal extends HttpServlet {

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
        int tipo = Integer.valueOf(request.getParameter("tipo"));
        if(u.isAdmin()|| (u.getPermisosPersonal()!=null && u.getPermisosPersonal().getId()!=tipo)){
            try (PrintWriter out = response.getWriter()) {
                /* TODO output your page here. You may use following sample code. */
                ManejadorPersonal mp = ManejadorPersonal.getInstance();
                int tipoListado = Integer.valueOf(request.getParameter("tipoListado"));
                boolean orden=false;
                if(request.getParameter("orden").equals("1")){
                    orden=true;
                };
                LinkedList<Personal> per=null;
                
               // System.out.print("tipo="+tipo+";orden="+orden);
                switch(tipoListado){
                    case 1: per=mp.getPersonalListarNro(tipo,orden); break ;
                    case 2: per=mp.getPersonalListarGrado(tipo,orden); break ;
                    case 3: per=mp.getPersonalListarNombre(tipo,orden); break ;
                    case 4: per=mp.getPersonalListarApellido(tipo,orden); break ;
                }
                JsonObjectBuilder json = Json.createObjectBuilder(); 
                if(per==null){
                    json.add("listadoPersonal", Json.createArrayBuilder().build());
                }
                else{
                    JsonArrayBuilder jab= Json.createArrayBuilder();
                    if(tipo==1){
                        for (Personal p : per){
                            jab.add(Json.createObjectBuilder()
                                .add("Nro", p.getNroInterno())
                                .add("grado", p.getGrado().getAbreviacion())
                                .add("primerNombre", p.getPrimerNombre())
                                .add("segundoNombre", p.getSegundoNombre())
                                .add("primerApellido", p.getPrimerApellido())
                                .add("segundoApellido", p.getSegundoApellido())
                                .add("ci", p.getCi())
                                .add("curso", ((Cadete)p).getCurso().getAbreviacion())
                            );
                        };
                    }
                    else{
                         for (Personal p : per){
                            jab.add(Json.createObjectBuilder()
                                .add("Nro", p.getNroInterno())
                                .add("grado", p.getGrado().getAbreviacion())
                                .add("primerNombre", p.getPrimerNombre())
                                .add("segundoNombre", p.getSegundoNombre())
                                .add("primerApellido", p.getPrimerApellido())
                                .add("segundoApellido", p.getSegundoApellido())
                                .add("ci", p.getCi())
                                .add("arma", p.getArma().getDescripcion())
                            );
                        };
                    }


                    json.add("listadoPersonal", jab);


                }
                out.print(json.build());
            }
            catch (Exception e){
                System.out.print("Servlet Listar: "+e.getMessage());
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
