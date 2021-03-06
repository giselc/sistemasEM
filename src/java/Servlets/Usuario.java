/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Manejadores.ManejadorCodigoBD;
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
public class Usuario extends HttpServlet {

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
        HttpSession sesion = request.getSession();
        Classes.Usuario u = (Classes.Usuario) sesion.getAttribute("usuario");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            ManejadorCodigoBD mc = new ManejadorCodigoBD();
            if(request.getParameter("id")!= null){ //alta o modficacion
                int id= Integer.valueOf(request.getParameter("id"));
                String usuario = request.getParameter("usuario");
                String nombreMostrar = request.getParameter("nombreMostrar");
                boolean admin = false;
                if(usuario.equals("admin")){
                    admin=true;
                }
                else{
                    if (request.getParameter("admin")!=null){
                        admin = request.getParameter("admin").equals("on");
                    }
                }
                boolean permisoPersonal = false;
                if (request.getParameter("permisoPersonal")!=null){
                    permisoPersonal = request.getParameter("permisoPersonal").equals("on");
                }
                int tipoPermisoPersonal=0;
                if(permisoPersonal){
                    tipoPermisoPersonal = Integer.valueOf(request.getParameter("tipoPermisoPersonal"));
                }
                boolean permisoDescuentos = false;
                if (request.getParameter("permisoDescuentos")!=null){
                    permisoDescuentos = request.getParameter("permisoDescuentos").equals("on");
                }
                int tipoPermisoDescuento=0;
                if(permisoDescuentos){
                    tipoPermisoDescuento = Integer.valueOf(request.getParameter("tipoPermisoDescuento"));
                }
                boolean permisoSistemaNotas = false;
                if (request.getParameter("permisoSistemaNotas")!=null){
                    permisoSistemaNotas = request.getParameter("permisoSistemaNotas").equals("on");
                }
                boolean permisoSistemaHabilitacion = false;
                if (request.getParameter("permisoSistemaHabilitacion")!=null){
                    permisoSistemaHabilitacion = request.getParameter("permisoSistemaHabilitacion").equals("on");
                }
                boolean esProfesor = false;
                if (request.getParameter("esProfesor")!=null){
                    esProfesor = request.getParameter("esProfesor").equals("on");
                }
                int ciProfesor=0;
                if(esProfesor){
                    ciProfesor = Integer.valueOf(request.getParameter("ciProfesor"));
                }
                if(id==-1){ //alta
                    String url="usuarios.jsp";
                    if(!mc.existeUsuario(usuario)){
                        String pass = request.getParameter("pass");
                        boolean cambiarContra = false;
                        if (request.getParameter("cambiarContra")!=null){
                            cambiarContra = request.getParameter("cambiarContra").equals("on");
                        }
                        if(mc.crarUsuario(u,usuario,nombreMostrar,pass,cambiarContra,admin,tipoPermisoPersonal,tipoPermisoDescuento,permisoSistemaNotas,permisoSistemaHabilitacion,esProfesor,ciProfesor)){
                            sesion.setAttribute("Mensaje", "Usuario creado satisfactoriamente.");
                        }
                        else{
                            sesion.setAttribute("Mensaje", "ERROR al crear el usuario.");
                        }
                        
                    }
                    else{
                        url="usuario.jsp";
                        sesion.setAttribute("Mensaje", "El usuario: "+usuario+" ya existente en el sistema.");
                    }
                    response.sendRedirect(url);
                    
                }
                else{ //modificacion
                    if( mc.ModificarUsuario(u, id, nombreMostrar,admin,tipoPermisoPersonal,tipoPermisoDescuento,permisoSistemaNotas,permisoSistemaHabilitacion,esProfesor,ciProfesor)){
                       sesion.setAttribute("Mensaje", "Usuario modificado satisfactoriamente.");
                    }
                    else{
                        sesion.setAttribute("Mensaje", "ERROR al modificar el usuario.");
                    }
                    response.sendRedirect("usuarios.jsp");
                }
            }
            else{
                if(request.getParameter("pass")!= null){
                    int id= Integer.valueOf(request.getParameter("pass"));
                    String contraAnt = "";
                    if(request.getParameter("contraAnt")!=null){
                        contraAnt= request.getParameter("contraAnt");
                    }
                    String contraNue= request.getParameter("contraNue");
                    boolean cambiarContra = false;
                    if (request.getParameter("cambiarContra")!=null){
                        cambiarContra = request.getParameter("cambiarContra").equals("on");
                    }
                    if(mc.cambiarContrasena(u, id, contraNue, contraAnt,cambiarContra)){
                        sesion.setAttribute("Mensaje", "Contraseña modificada satisfactoriamente.");
                        if(request.getParameter("contraAnt")!=null){
                            response.sendRedirect(""); 
                        }
                        else{
                            response.sendRedirect("usuarios.jsp"); 
                        }
                    }
                    else{
                        sesion.setAttribute("Mensaje", "ERROR al modificar la contraseña.");
                        response.sendRedirect("cambiarContrasena.jsp?id="+id);
                    }
                    
                    
                }
                else{
                    if(request.getParameter("elim")!= null){
                        int id=Integer.valueOf(request.getParameter("elim"));
                        if(mc.eliminarUsuario(u, id)){
                            sesion.setAttribute("Mensaje", "Usuario eliminado satisfactoriamente.");
                        }
                        else{
                            sesion.setAttribute("Mensaje", "ERROR al eliminar el usuario. Puede que el usuario este asociado a algún postulante.");
                        }
                        response.sendRedirect("usuarios.jsp"); 
                    }
                    else{
                        if(request.getParameter("listar")!= null){
                            if(request.getParameter("listar")!= "usuarios"){
                                mc.imprimirUsuarios(out, u);
                            }
                        }
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
