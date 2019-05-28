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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Gisel
 */
public class CursoBedelia extends HttpServlet {
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
        if(u.isAdmin()||u.getPermisosPersonal().getId()==4){
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
                ManejadorBedelia mp = ManejadorBedelia.getInstance();
                //String mensaje;
                if(request.getParameter("elim")!=null){
                    //baja
                    int id= Integer.valueOf(request.getParameter("id"));
                    switch(mp.eliminarCurso(id)){
                        case 0:
                            mensaje="Curso eliminado sastisfactoriamente.";
                            redirect="/cursos.jsp";
                            break;
                        case 1:
                            mensaje="ERROR al eliminar el curso. Tiene materias asociadas";
                            redirect="/cursos.jsp";
                            break;
                        case 2:
                            mensaje="ERROR al eliminar el curso. Tiene libretas asociadas";
                            redirect="/cursos.jsp";
                            break;
                    }
                    sesion.setAttribute("Mensaje", mensaje);
                    response.sendRedirect(redirect);
                }
                else{     
                    if(request.getParameter("desvincular")!=null){
                        if( !mp.desasociarMateriaCurso(Integer.parseInt(request.getParameter("desvincular")), Integer.parseInt(request.getParameter("idCurso")))){
                            sesion.setAttribute("Mensaje", "No se puede desvincular materia, puede que existan libretas creadas.");
                        }
                        else{
                            sesion.setAttribute("Mensaje", "Materia desvinculada correctamente.");
                        };
                        response.sendRedirect("curso.jsp?id="+request.getParameter("idCurso"));
                    }
                    else{
                        int id= Integer.valueOf(request.getParameter("id"));
                        if(request.getParameter("grupo")!=null){
                            if(Integer.valueOf(request.getParameter("grupo"))==-1){
                               if(mp.agregarGrupoCurso(id, Integer.valueOf(request.getParameter("anio")), request.getParameter("nombre"))){
                                    sesion.setAttribute("Mensaje", "Grupo agregado correctamente.");
                               }
                               else{
                                   sesion.setAttribute("Mensaje", "ERROR al agregar al grupo. Contáctese con el administrador.");
                               }
                               response.sendRedirect("curso.jsp?id="+id);

                            }
                            else{
                                //eliminar
                            }
                        }
                        else{
                            boolean activo = false;
                            if (request.getParameter("activo")!=null){
                                activo = request.getParameter("activo").equals("on");
                            }
                            Classes.Bedelia.CursoBedelia cb = new Classes.Bedelia.CursoBedelia(id, request.getParameter("codigo"), request.getParameter("nombre"), Integer.parseInt(request.getParameter("anioCurricular")), request.getParameter("jefatura").equals("JE"),activo);
                            if(id!=-1){
                                switch(mp.modificarCurso(cb)){
                                    case 0:
                                        mensaje="Curso modificado sastisfactoriamente.";
                                        break;
                                    case 1:
                                        mensaje="ERROR al modificar el curso. Tiene materias asociadas";
                                        break;
                                    case 2:
                                        mensaje="ERROR al modificar el curso. Tiene libretas asociadas";
                                        break;
                                }
                                redirect="/curso.jsp?id="+request.getParameter("id");
                            }
                            else{
                                //agregar
                                if(mp.agregarCurso(cb)){
                                    mensaje="Curso agregado sastisfactoriamente.";
                                    redirect="/cursos.jsp";
                                }
                                else{
                                    mensaje="ERROR al agregar al curso.";
                                    redirect="/cursos.jsp";
                                };
                            }
                        }
                        sesion.setAttribute("Mensaje", mensaje);
                        response.sendRedirect(redirect);
                    }
                }
                
            }
            catch(Exception ex){
                mensaje = "ERROR: " + ex.getMessage();
                sesion.setAttribute("Mensaje", mensaje);
                response.sendRedirect("/cursos.jsp");
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
