/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Classes.Personal;
import Classes.RecordFiltro;
import Classes.Usuario;
import Manejadores.ManejadorPersonal;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
public class Filtro extends HttpServlet {

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
        int tipoPersonal= Integer.valueOf(request.getParameter("tipoPersonal"));
        if(tipoPersonal==1){
            if(u.isAdmin()|| (u.getPermisosPersonal()!=null && u.getPermisosPersonal().getId()==1)){
                try (PrintWriter out = response.getWriter()) {
                    /* TODO output your page here. You may use following sample code. */
                    ManejadorPersonal mp = ManejadorPersonal.getInstance(); 
                    RecordFiltro rf =  new RecordFiltro();
                    rf.lmga = request.getParameter("lmga");
                    rf.pd = request.getParameter("pd");
                    rf.sexo = request.getParameter("sexo");
                    rf.canthijos = request.getParameterValues("canthijos");
                    rf.depDom = request.getParameterValues("depDom");
                    rf.depNac = request.getParameterValues("depNac");
                    rf.carrera = request.getParameter("carrera");
                    rf.repitiente = request.getParameter("repitiente");
                    rf.armas = request.getParameterValues("armas");
                    rf.grados = request.getParameterValues("grados");
                    rf.cursos = request.getParameterValues("cursos");
                    ArrayList<Personal> ap = mp.getCadeteFiltro(rf);
                    JsonObjectBuilder json = Json.createObjectBuilder(); 
                    json.add("filtroTexto",reemplazarCaracteresHtml(rf.filtroMostrar));
                    if(ap.isEmpty()){
                       json.add("listadoPersonal", Json.createArrayBuilder().build());
                    }
                    else{
                        JsonArrayBuilder jab= Json.createArrayBuilder();
                        for (Personal p : ap){
                            jab.add(Json.createObjectBuilder()
                                .add("ci", p.getCi())
                                .add("numero", p.getNroInterno())
                                .add("primerNombre", reemplazarCaracteresHtml(p.getPrimerNombre()))
                                .add("segundoNombre", reemplazarCaracteresHtml(p.getSegundoNombre()))
                                .add("primerApellido",reemplazarCaracteresHtml(p.getPrimerApellido()))
                                .add("segundoApellido",reemplazarCaracteresHtml(p.getSegundoApellido()))
                                .add("grado",reemplazarCaracteresHtml(p.getGrado().getAbreviacion()))
                                .add("curso", reemplazarCaracteresHtml(((Classes.Cadete)p).getCurso().getAbreviacion()))
                            );
                        };
                        json.add("listadoPersonal", jab);
                    }
                    out.print(json.build());
                }
                catch (Exception e){
                    System.out.print(e.getMessage());
                }
            }
            else{
                    response.sendRedirect("");
            }
        }
        else{
            if(u.isAdmin()|| (u.getPermisosPersonal()!=null && u.getPermisosPersonal().getId()==tipoPersonal)){
                try (PrintWriter out = response.getWriter()) {
                    /* TODO output your page here. You may use following sample code. */
                    ManejadorPersonal mp = ManejadorPersonal.getInstance(); 
                    RecordFiltro rf =  new RecordFiltro();
                    rf.armas = request.getParameterValues("armas");
                    rf.grados = request.getParameterValues("grados");
                    ArrayList<Personal> ap = mp.getPersonalFiltro(rf,tipoPersonal);
                    JsonObjectBuilder json = Json.createObjectBuilder(); 
                    json.add("filtroTexto",reemplazarCaracteresHtml(rf.filtroMostrar));
                    if(ap.isEmpty()){
                       json.add("listadoPersonal", Json.createArrayBuilder().build());
                    }
                    else{
                        JsonArrayBuilder jab= Json.createArrayBuilder();
                        for (Personal p : ap){
                            jab.add(Json.createObjectBuilder()
                                .add("ci", p.getCi())
                                .add("numero", p.getNroInterno())
                                .add("primerNombre", reemplazarCaracteresHtml(p.getPrimerNombre()))
                                .add("segundoNombre", reemplazarCaracteresHtml(p.getSegundoNombre()))
                                .add("primerApellido",reemplazarCaracteresHtml(p.getPrimerApellido()))
                                .add("segundoApellido",reemplazarCaracteresHtml(p.getSegundoApellido()))
                                .add("grado",reemplazarCaracteresHtml(p.getGrado().getAbreviacion()))
                                .add("arma", reemplazarCaracteresHtml(p.getArma().getDescripcion()))
                            );
                        };
                        json.add("listadoPersonal", jab);
                    }
                    out.print(json.build());
                }
                catch (Exception e){
                    System.out.print(e.getMessage());
                }
            }
            else{
                    response.sendRedirect("");
            }
        }
    }
    private String reemplazarCaracteresHtml(String input){
        CharSequence[] origen={"á", "à", "Á", "À","é", "è", "É", "È","í", "ì", "Í", "Ì","ó", "ò", "Ó", "Ò","ú", "ù", "Ú", "Ù","ñ","Ñ","ç","Ç","°","ª"};//,""
        CharSequence[] destino= {"&aacute;", "&aacute;", "&Aacute;", "&Aacute;","&eacute;", "&eacute;", "&Eacute;", "&Eacute;","&iacute;", "&iacute;", "&Iacute;", "&Iacute;","&oacute;", "&oacute;", "&Oacute;", "&Oacute;","&uacute;", "&uacute;", "&Uacute;", "&Uacute;","&ntilde;","&Ntilde;","&ccedil;","&Ccedil;","&deg;","&ordf;"} ;//
        int i=0;
        for(CharSequence o:origen){
            input=input.replace(o, destino[i++]);
        }
        return input;
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
