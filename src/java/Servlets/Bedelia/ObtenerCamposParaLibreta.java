/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets.Bedelia;

import Classes.Bedelia.Grupo;
import Classes.Personal;
import Classes.Usuario;
import Manejadores.ManejadorBedelia;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;
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
public class ObtenerCamposParaLibreta extends HttpServlet {

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
        response.setContentType("UTF-8");
        Usuario u = (Usuario)sesion.getAttribute("usuario");
        if(u.isAdmin()||u.getPermisosPersonal().getId()==4){
            try (PrintWriter out = response.getWriter()) {
                /* TODO output your page here. You may use following sample code. */
                Manejadores.ManejadorBedelia mb= ManejadorBedelia.getInstance();
                if(request.getParameter("materiasGrupoCurso")!=null){
                    int idCurso= Integer.valueOf(request.getParameter("idCurso"));
                    HashMap<Integer,Classes.Bedelia.Materia> hm= mb.getCurso(idCurso).getMaterias();
                    JsonObjectBuilder json = Json.createObjectBuilder(); 
                    if(hm.isEmpty()){
                       json.add("materias", Json.createArrayBuilder().build());
                    }
                    else{
                        JsonArrayBuilder jab= Json.createArrayBuilder();
                        for (Classes.Bedelia.Materia m : hm.values()){
                            jab.add(Json.createObjectBuilder()
                                .add("id", m.getId())
                                .add("codigo", Manejadores.ManejadorPersonal.reemplazarTildes(m.getCodigo()))
                                .add("nombre", Manejadores.ManejadorPersonal.reemplazarTildes(m.getNombre()))
                            );
                        };
                        json.add("materias", jab);
                    }
                   // System.out.print(json.build());
                    LinkedList<Grupo> ll= mb.getCurso(idCurso).getGrupos();
                    if(ll.isEmpty()){
                       json.add("grupos", Json.createArrayBuilder().build());
                    }
                    else{
                        JsonArrayBuilder jab= Json.createArrayBuilder();
                        for (Grupo g : ll){
                            jab.add(Json.createObjectBuilder()
                                .add("anio", g.getAnio())
                                .add("nombre", g.getNombre())
                                .add("cantAlumnos", g.getAlumnos().size())
                            );
                        };
                        json.add("grupos", jab);
                    }
                   // System.out.print(json.build());
                    out.print(json.build());
                }
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
