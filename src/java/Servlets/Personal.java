/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Classes.RecordCadete;
import Classes.RecordPersonal;
import Classes.Usuario;
import Manejadores.ManejadorPersonal;
import Manejadores.ManejadorProfesores;
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
import javax.servlet.http.Part;

/**
 *
 * @author Gisel
 */
public class Personal extends HttpServlet {

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
        Usuario u = (Usuario)sesion.getAttribute("usuario");
        int tipo= Integer.valueOf(request.getParameter("tipo"));
        if(u.isAdmin()||u.getPermisosPersonal().getId()==tipo){
            response.setContentType("text/html;charset=UTF-8");
            String mensaje="";
            String redirect="";
           /* Enumeration<String> params = request.getParameterNames(); 
            while(params.hasMoreElements()){
                String paramName = params.nextElement();
                System.out.println("Parameter Name - "+paramName+", Value - "+request.getParameter(paramName));
            }*/

            try (PrintWriter out = response.getWriter()) {
                /* TODO output your page here. You may use following sample code. */
                ManejadorPersonal mp = ManejadorPersonal.getInstance();
                int ci= Integer.valueOf(request.getParameter("ci"));
                if(request.getParameter("elim")!=null){
                    //baja
                    if( mp.bajaPersonal(ci,tipo)){
                        mensaje="Personal eliminado sastisfactoriamente.";
                    }
                    else{
                        mensaje="ERROR al eliminar el Personal.";
                    }
                    redirect="/personal.jsp?tipo="+tipo;
                    sesion.setAttribute("Mensaje", mensaje);
                    response.sendRedirect(redirect);
                }
                else{
                    if(request.getParameter("existe")!=null){
                        JsonObjectBuilder json = Json.createObjectBuilder(); 
                        Boolean existe= mp.getPersonal(ci,2)!=null || mp.getPersonal(ci,3)!=null || mp.getPersonal(ci,1)!=null || ManejadorProfesores.getInstance().getProfesor(ci)!=null;
                        if(!existe){
                            json.add("Personal", Json.createArrayBuilder().build());
                        }
                        else{
                                JsonArrayBuilder jab= Json.createArrayBuilder();
                                jab.add(Json.createObjectBuilder()
                                    .add("existe", "0")
                                        //agregar resto
                                );
                                json.add("Personal", jab);
                            
                        }
                        out.print(json.build());
                    }
                    else {
                            RecordPersonal rp= new RecordPersonal();
                            rp.ci=ci;
                            rp.primerNombre = request.getParameter("primerNombre");
                            rp.segundoNombre = request.getParameter("segundoNombre");
                            rp.primerApellido = request.getParameter("primerApellido");
                            rp.segundoApellido = request.getParameter("segundoApellido");
                            rp.idArma = Integer.parseInt(request.getParameter("arma"));
                            rp.idGrado = Integer.parseInt(request.getParameter("grado"));
                            rp.fechaAltaSistema = request.getParameter("fechaAltaSistema");
                            rp.rc=null;
                            if(mp.agregarPersonal(rp,tipo)){
                                mensaje="Personal insertado sastisfactoriamente.";
                                redirect="/personal.jsp?tipo="+tipo;
                            }
                            else{
                                mensaje="ERROR al agregar el personal.";
                                redirect="/personal.jsp?tipo="+tipo;
                            };
                            sesion.setAttribute("Mensaje", mensaje);
                            response.sendRedirect(redirect);
                        }
                }
            }
            catch(Exception ex){
                mensaje = "ERROR: " + ex.getMessage();
                System.out.print(mensaje);
                response.sendRedirect("/personal.jsp");
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
