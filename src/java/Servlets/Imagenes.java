/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Classes.Personal;
import Classes.Usuario;
import Manejadores.ManejadorPersonal;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
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
public class Imagenes extends HttpServlet {

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
        
            /* TODO output your page here. You may use following sample code. */
            HttpSession sesion = request.getSession();
            Usuario u = (Usuario)sesion.getAttribute("usuario");
            if(u.isAdmin()||u.getPermisosPersonal().getId()!=0){
                String path="";
                ManejadorPersonal mp = ManejadorPersonal.getInstance();
                if(request.getParameter("foto")!=null){
                    int ci = Integer.valueOf(request.getParameter("foto"));
                    Classes.Cadete c = mp.getCadete(ci);
                    path= "c:/SEM-Documentos/Fotos/"+c.getFoto();
                    response.setContentType("image/*");
                }
                else{
                    int ci = Integer.valueOf(request.getParameter("ci"));
                    String ext = request.getParameter("ext");
                    int idDoc = Integer.valueOf(request.getParameter("idDoc"));
                    switch (ext) {
                        case ".doc":
                            response.setContentType("application/msword");
                            break;
                        case ".docx":
                            response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
                            break;
                        case ".xls":
                            response.setContentType("application/vnd.ms-excel");
                            break;
                        case ".xlsx":
                            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                            break;
                        case ".ppt":
                            response.setContentType("application/vnd.ms-powerpoint");
                            break;
                        case ".pptx":
                            response.setContentType("application/vnd.openxmlformats-officedocument.presentationml.presentation");
                            break;
                        case ".pdf":
                            response.setContentType("application/pdf");
                            break;
                        default:
                            response.setContentType("image/"+ext.substring(1));
                            break;
                    }
                    System.out.print(response.getContentType());
                    path= "c:/SEM-Documentos/"+ci+"-"+idDoc+ext;
                }
                File f=new File(path);
                int   size=(int) f.length();
                byte[] resultado=new byte[size];
                BufferedInputStream   stream = new BufferedInputStream(new FileInputStream(f));
                stream.read(resultado);

                try (OutputStream sos = response.getOutputStream()) {
                    sos.write(resultado);
                    sos.flush();
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
