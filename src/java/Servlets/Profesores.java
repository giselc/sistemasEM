/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Classes.RecordProfesores;
import Classes.Usuario;
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

/**
 *
 * @author Gisel
 */
public class Profesores extends HttpServlet {

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
        if(u.isAdmin()||u.getPermisosPersonal().getId()==4){
            response.setContentType("text/html;charset=UTF-8");
            String mensaje;
            String redirect;
           /* Enumeration<String> params = request.getParameterNames(); 
            while(params.hasMoreElements()){
                String paramName = params.nextElement();
                System.out.println("Parameter Name - "+paramName+", Value - "+request.getParameter(paramName));
            }*/

            try (PrintWriter out = response.getWriter()) {
                /* TODO output your page here. You may use following sample code. */
                ManejadorProfesores mp = ManejadorProfesores.getInstance();
                int ci= Integer.valueOf(request.getParameter("ci"));
                if(request.getParameter("elim")!=null){
                    //baja
                    if( mp.eliminarProfesor(ci)){
                        mensaje="Profesor eliminado sastisfactoriamente.";
                        redirect="/profesores.jsp";
                    }
                    else{
                        mensaje="ERROR al eliminar el cadete.";
                        redirect="/profesores.jsp";
                    };
                    sesion.setAttribute("Mensaje", mensaje);
                    response.sendRedirect(redirect);
                }
                else{
                    if(request.getParameter("existe")!=null){
                        JsonObjectBuilder json = Json.createObjectBuilder(); 
                        boolean sistema = mp.getProfesor(ci)!=null;
                        if(!sistema){
                            json.add("Profesor", Json.createArrayBuilder().build());
                        }
                        else{
                            JsonArrayBuilder jab= Json.createArrayBuilder();
                            jab.add(Json.createObjectBuilder()
                                .add("existe", 1)
                                    //agregar resto
                            );
                            json.add("Profesor", jab);
                        }
                        
                        out.print(json.build());
                    }
                    else{
                        RecordProfesores rp= new RecordProfesores();
                        rp.ci=ci;
                        rp.correo = request.getParameter("correo");
                        rp.fechaIngreso = request.getParameter("fechaIng");
                        rp.primerNombre = request.getParameter("primerNombre");
                        rp.segundoNombre = request.getParameter("segundoNombre");
                        rp.primerApellido = request.getParameter("primerApellido");
                        rp.segundoApellido = request.getParameter("segundoApellido");
                        rp.idArma = Integer.parseInt(request.getParameter("arma"));
                        rp.idGrado = Integer.parseInt(request.getParameter("grado"));
                        if(!request.getParameter("cantHoras").equals("")){
                            rp.cantHoras = Integer.parseInt(request.getParameter("cantHoras"));
                        }
                        if(!request.getParameter("categoria").equals("")){
                            rp.categoria = Integer.parseInt(request.getParameter("categoria"));
                        }
                        if(!request.getParameter("antiguedad").equals("")){
                            rp.antiguedad = Integer.parseInt(request.getParameter("antiguedad"));
                        }
                        rp.telefono= request.getParameter("telefono");
                        rp.numeroCuenta = request.getParameter("nroCuenta");
                        rp.dependenciaFinanciera = request.getParameter("depFinanciera");
                        if (request.getParameter("bedelia")!=null){
                             rp.adminBedelia = request.getParameter("bedelia").equals("on");
                        }
                        else{
                            rp.adminBedelia = false;
                        }
                        rp.observaciones =request.getParameter("observaciones");
                        if(request.getParameter("id")!=null){
                            if(mp.modificarProfesor(rp)){
                                mensaje="Profesor modificado correctamente.";
                            }
                            else{
                                mensaje="ERROR al modificar el cadete.";
                            };
                            redirect="/profesor.jsp?id="+request.getParameter("id");
                        }
                        else{
                            //agregar
                            if(mp.agregarProfesor(rp)){
                                mensaje="Profesor agregado sastisfactoriamente.";
                                redirect="/profesor.jsp?id="+request.getParameter("ci");
                            }
                            else{
                                mensaje="ERROR al agregar al profesor.";
                                redirect="/profesores.jsp";
                            };
                        }
                        sesion.setAttribute("Mensaje", mensaje);
                        response.sendRedirect(redirect);
                    }
                }
            }
            catch(Exception ex){
                mensaje = "ERROR: " + ex.getMessage();
                sesion.setAttribute("Mensaje", mensaje);
                response.sendRedirect("/profesores.jsp");
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
