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
public class Materia extends HttpServlet {

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
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        HttpSession sesion = request.getSession();
        Usuario u = (Usuario)sesion.getAttribute("usuario");
        //System.out.print("aca");
        if(u.isAdmin()|| (u.getPermisosPersonal()!=null && u.getPermisosPersonal().getId()==4)){
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
                    if(mp.eliminarMateria(Integer.valueOf(request.getParameter("elim")))){
                            mensaje="Materia eliminado sastisfactoriamente.";
                            redirect="materias.jsp";
                    }
                    else{
                            mensaje="ERROR al eliminar la materia. Puede estar asociada a algún curso";
                            redirect="materias.jsp";
                         
                    }
                    sesion.setAttribute("Mensaje", mensaje);
                    response.sendRedirect(redirect);
                }
                else{ 
                    int id= Integer.valueOf(request.getParameter("id"));
                    String nombre=request.getParameter("nombre");
                    String codigo=request.getParameter("codigo");
                    boolean secundaria=false;
                    if (request.getParameter("secundaria")!=null){
                        secundaria = request.getParameter("secundaria").equals("on");
                    }
                    boolean semestral=false;
                    if (request.getParameter("semestral")!=null){
                        semestral = request.getParameter("semestral").equals("on");
                    }
                    boolean activo=false;
                    if (request.getParameter("activo")!=null){
                        activo = request.getParameter("activo").equals("on");
                   }
                    int semestre=0;
                    if(semestral && request.getParameter("semestre")!=null){
                        semestre=Integer.valueOf(request.getParameter("semestre"));
                    }
                    double coeficiente=Double.valueOf(request.getParameter("coeficiente"));
                    Classes.Bedelia.Materia m = new Classes.Bedelia.Materia(id, nombre, codigo,semestral, semestre, secundaria, coeficiente,activo);
                    if(id!=-1){
                        if(mp.modificarMateria(m)){
                            mensaje="Materia modificada sastisfactoriamente.";
                        }
                        else{
                            mensaje="ERROR al modificar la materia.";
                        }
                        redirect="materias.jsp";
                    }
                    else{
                        //agregar
                        int idCurso = 0;
                        if(request.getParameter("idCurso")!=null){
                            idCurso= Integer.valueOf(request.getParameter("idCurso"));
                        }
                        if(mp.agregarMateria(m,idCurso)){
                            mensaje="Materia agregada sastisfactoriamente.";
                            redirect="materias.jsp";
                        }
                        else{
                            mensaje="ERROR al agregar la materia.";
                            redirect="materias.jsp";
                        }
                    }
                    sesion.setAttribute("Mensaje", mensaje);
                    response.sendRedirect(redirect);
                }
            }
            catch(Exception ex){
                mensaje = "ERROR: " + ex.getMessage();
                sesion.setAttribute("Mensaje", mensaje);
                response.sendRedirect("materias.jsp");
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
