/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets.Bedelia;

import Classes.Cadete;
import Manejadores.ManejadorBedelia;
import Manejadores.ManejadorPersonal;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Gisel
 */
public class VincularAlumnosGrupo extends HttpServlet {

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
        if (sesion.getAttribute("usuarioID")!=null){
            try (PrintWriter out = response.getWriter()) {
                ManejadorBedelia mb= ManejadorBedelia.getInstance();
                if(request.getParameter("desvincular")!=null){
                    if(Integer.valueOf(request.getParameter("desvincular"))!=-1){
                        if(mb.desasociarAlumnoGrupo(Integer.valueOf(request.getParameter("desvincular")),mb.getCurso(Integer.valueOf(request.getParameter("idCurso"))).getGrupo(Integer.valueOf(request.getParameter("anioGrupo")), request.getParameter("nombreGrupo")))){
                            sesion.setAttribute("Mensaje", "Alumno desvinculado con éxito.");
                            response.sendRedirect("/grupo.jsp?idCurso="+request.getParameter("idCurso")+"&nombreGrupo="+request.getParameter("nombreGrupo")+"&anioGrupo="+request.getParameter("anioGrupo")); 
                        }  
                    }
                    else{
                        if(request.getParameterValues("List[]").length!=0){
                            if(mb.desasociarAlumnosGrupo(request.getParameterValues("List[]"),mb.getCurso(Integer.valueOf(request.getParameter("idCurso"))).getGrupo(Integer.valueOf(request.getParameter("anioGrupo")), request.getParameter("nombreGrupo")))){
                                sesion.setAttribute("Mensaje", "Alumno desvinculado con éxito.");
                                response.sendRedirect("/grupo.jsp?idCurso="+request.getParameter("idCurso")+"&nombreGrupo="+request.getParameter("nombreGrupo")+"&anioGrupo="+request.getParameter("anioGrupo")); 
                            }   
                        }
                        else{
                            sesion.setAttribute("Mensaje", "Ningún alumno seleccionado.");
                            response.sendRedirect("/grupo.jsp?idCurso="+request.getParameter("idCurso")+"&nombreGrupo="+request.getParameter("nombreGrupo")+"&anioGrupo="+request.getParameter("anioGrupo")); 
                        }
                    }
                }
                else{
                    if(request.getParameterValues("List[]")!=null){
                        //System.out.print(request.getParameterValues("List[]").length);
                        ManejadorPersonal mp= ManejadorPersonal.getInstance();

                        LinkedList<Cadete> cadetes = new LinkedList();
                        int ci;
                        for(String s:request.getParameterValues("List[]")){
                            ci=Integer.valueOf(s);
                            cadetes.add(mp.getCadete(ci));
                        }
                        if(mb.asociarAlumnosGrupo(cadetes,mb.getCurso(Integer.valueOf(request.getParameter("idCurso"))).getGrupo(Integer.valueOf(request.getParameter("anioGrupo")),request.getParameter("nombreGrupo")))){
                            sesion.setAttribute("Mensaje", "Alumnos vinculados con éxito.");
                            response.sendRedirect("/grupo.jsp?idCurso="+request.getParameter("idCurso")+"&nombreGrupo="+request.getParameter("nombreGrupo")+"&anioGrupo="+request.getParameter("anioGrupo"));
                        };
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
