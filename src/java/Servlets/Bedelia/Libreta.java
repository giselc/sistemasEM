/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets.Bedelia;

import Classes.Cadete;
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
public class Libreta extends HttpServlet {

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
            try (PrintWriter out = response.getWriter()) {
                ManejadorBedelia mp = ManejadorBedelia.getInstance();
                if(request.getParameter("elim")!=null){
                    //baja
                }
                else{     
                    int id= Integer.valueOf(request.getParameter("id"));
                    if(id!=-1){
                         if(request.getParameter("pasarLista")!=null){
                            //pasarLista
                            Classes.Bedelia.Libreta l = mp.getLibreta(id);
                            for(Cadete c: l.getGrupo().getAlumnos().values()){
                                if(Integer.valueOf(request.getParameter("P-"+c.getCi()))!=1){
                                    if(request.getParameter("OBS-"+c.getCi()).equals("Observaciones")){
                                        mp.agregarFalta(l,c,request.getParameter("fecha"),request.getParameter("CF-"+c.getCi()),"");
                                    }
                                    else{
                                        mp.agregarFalta(l,c,request.getParameter("fecha"),request.getParameter("CF-"+c.getCi()),request.getParameter("OBS-"+c.getCi()));
                                    }
                                }
                            } 
                            sesion.setAttribute("Mensaje", "Datos agregados correctamente.");
                            response.sendRedirect("libreta.jsp?id="+id);
                        }
                        else{
                         }
                    }
                    else{
                        //agregar
                        String grupoAnioNombre=request.getParameter("grupo");
                        String salon= request.getParameter("salon");
                        int posicionSlash=grupoAnioNombre.indexOf("/");
                        int anio=Integer.valueOf(grupoAnioNombre.substring(0, posicionSlash));
                        String resto= grupoAnioNombre.substring(posicionSlash+1,grupoAnioNombre.length());
                        String nombre=resto.substring(0,resto.indexOf("/"));
                        Classes.Bedelia.Libreta l = mp.crearLibreta(Integer.valueOf(request.getParameter("curso")),anio,nombre,Integer.valueOf(request.getParameter("materia")),Integer.valueOf(request.getParameter("profesor")),salon);
                        if(l!=null){
                            mensaje="Libreta agregada sastisfactoriamente.";
                            redirect="/libreta.jsp?id="+l.getId();
                        }
                        else{
                            mensaje="ERROR al agregar la libreta.";
                            redirect="/libretas.jsp";
                        };
                    }
                    sesion.setAttribute("Mensaje", mensaje);
                    response.sendRedirect(redirect);
                }
            }
            catch(Exception ex){
                mensaje = "ERROR: " + ex.getMessage();
                sesion.setAttribute("Mensaje", mensaje);
                System.out.printf("Servlet-Libreta.java: "+mensaje);
                response.sendRedirect("/libretas.jsp");
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
