/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Classes.Personal;
import Classes.RecordCadete;
import Classes.RecordPersonal;
import Manejadores.ManejadorPersonal;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
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
public class Cadete extends HttpServlet {

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
        String mensaje="";
        String redirect="";
       /* Enumeration<String> params = request.getParameterNames(); 
        while(params.hasMoreElements()){
            String paramName = params.nextElement();
            System.out.println("Parameter Name - "+paramName+", Value - "+request.getParameter(paramName));
        }*/
        HttpSession sesion= request.getSession();
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            ManejadorPersonal mp = ManejadorPersonal.getInstance();
            int ci= Integer.valueOf(request.getParameter("ci"));
            if(request.getParameter("baja")!=null){
                //baja
                String causa = request.getParameter("causa");

                if( mp.bajaCadete(ci, causa)){
                    mensaje="Cadete eliminado sastisfactoriamente.";
                    redirect="/cadetes.jsp";
                }
                else{
                    mensaje="ERROR al eliminar el cadete.";
                    redirect="/cadetes.jsp";
                };
                sesion.setAttribute("Mensaje", mensaje);
                response.sendRedirect(redirect);
            }
            else{
                if(request.getParameter("existe")!=null){
                    JsonObjectBuilder json = Json.createObjectBuilder(); 
                    boolean historial = mp.existeCadeteHistorial(ci);
                    boolean sistema = mp.getCadete(ci)!=null;
                    if(!historial && !sistema){
                        json.add("Cadete", Json.createArrayBuilder().build());
                    }
                    else{
                        if(historial){
                            JsonArrayBuilder jab= Json.createArrayBuilder();
                            jab.add(Json.createObjectBuilder()
                                .add("historial", "1")
                                    //agregar resto
                            );
                            json.add("Cadete", jab);
                        }
                        else{
                            JsonArrayBuilder jab= Json.createArrayBuilder();
                            jab.add(Json.createObjectBuilder()
                                .add("historial", "0")
                                    //agregar resto
                            );
                            json.add("Cadete", jab);
                        }
                    }
                    out.print(json.build());
                }
                else {
                    if(request.getParameter("crearDesdeHistorial")!=null){
                        if( mp.crearCadeteHistorial(ci)){
                            mensaje="Cadete agregado sastisfactoriamente.";
                            redirect="/cadete.jsp?id="+ci;
                        }
                        else{
                            mensaje="ERROR al agregar el cadete del historial. Contacte al administrador";
                            redirect="/cadetes.jsp";
                        };
                        sesion.setAttribute("Mensaje", mensaje);
                        response.sendRedirect(redirect);
                    }
                    else{
                        Part foto = request.getPart("foto");
                        if(foto.getSize()==0){
                            foto=null;
                        }
                        RecordCadete rc=new RecordCadete();
                        RecordPersonal rp= new RecordPersonal();
                        rp.ci=ci;
                        rc.derecha = Integer.valueOf(request.getParameter("derecha"));
                        rc.domicilio = request.getParameter("domicilio");
                        rc.email = request.getParameter("email");
                        rc.fechaNac = request.getParameter("fechaNac");
                        rp.primerNombre = request.getParameter("primerNombre");
                        rp.segundoNombre = request.getParameter("segundoNombre");
                        rp.primerApellido = request.getParameter("primerApellido");
                        rp.segundoApellido = request.getParameter("segundoApellido");
                        rc.sexo = request.getParameter("sexo[]");
                        if (request.getParameter("repitiente")!=null){
                             rc.repitiente = request.getParameter("repitiente").equals("on");
                        }
                        else{
                            rc.repitiente = false;
                        }
                        rp.idArma = Integer.parseInt(request.getParameter("arma"));
                        rp.idGrado = Integer.parseInt(request.getParameter("grado"));
                        rc.idcurso = Integer.parseInt(request.getParameter("curso"));
                        rc.idcarrera = Integer.parseInt(request.getParameter("carrera"));
                        rc.iddepartamentoNac = Integer.parseInt(request.getParameter("departamentoNac"));
                        rc.localidadNac = request.getParameter("localidadNac");
                        rc.cc = request.getParameter("cc");
                        rc.talleOperacional = request.getParameter("talleOperacional");
                        if (request.getParameter("talleBotas").equals("")){
                             rc.talleBotas = 0;
                        }
                        else{
                             rc.talleBotas = Integer.parseInt(request.getParameter("talleBotas"));
                        }
                        if (request.getParameter("talleQuepi").equals("")){
                             rc.talleQuepi = 0;
                        }
                        else{
                             rc.talleQuepi = Integer.parseInt(request.getParameter("talleQuepi"));
                        }
                        if (request.getParameter("CCNro").equals("")){
                             rc.ccNro = 0;
                        }
                        else{
                             rc.ccNro = Integer.parseInt(request.getParameter("CCNro"));
                        }
                        rc.idestadoCivil = Integer.parseInt(request.getParameter("estadoCivil"));
                        rc.domicilio = request.getParameter("domicilio");
                        rc.iddepartamento= Integer.parseInt(request.getParameter("departamento"));
                        rc.localidad= request.getParameter("localidad");
                        rc.telefono= request.getParameter("telefono");
                        rc.email = request.getParameter("email");
                        if (request.getParameter("lmga")!=null){
                             rc.lmga = request.getParameter("lmga").equals("on");
                        }
                        else{
                            rc.lmga = false;
                        }
                        if (request.getParameter("paseDirecto")!=null){
                                rc.paseDirecto = request.getParameter("paseDirecto").equals("on");
                        }
                        else{
                            rc.paseDirecto = false;
                        }
                        rc.notaPaseDirecto = Double.parseDouble(request.getParameter("notaPaseDirecto"));
                        rp.fechaAltaSistema = request.getParameter("fechaAltaSistema");
                        rp.rc=rc;
                        rp.observaciones =request.getParameter("observaciones");
                        if(request.getParameter("id")!=null){
                            if(mp.modificarCadete(rp, foto)){
                                mensaje="Cadete modificado correctamente.";
                            }
                            else{
                                mensaje="ERROR al modificar el cadete.";
                            };
                            redirect="/cadete.jsp?id="+request.getParameter("id");
                        }
                        else{
                            //agregar
                            if(mp.agregarCadete(rp, foto)){
                                mensaje="Cadete insertado sastisfactoriamente.";
                                redirect="/cadete.jsp?id="+request.getParameter("ci");
                            }
                            else{
                                mensaje="ERROR al agregar al cadete.";
                                redirect="/cadetes.jsp";
                            };
                        }
                        sesion.setAttribute("Mensaje", mensaje);
                        response.sendRedirect(redirect);
                    }
                }
            }
        }
        catch(Exception ex){
            mensaje = "ERROR: " + ex.getMessage();
            System.out.print(mensaje);
            response.sendRedirect("/cadetes.jsp");
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
