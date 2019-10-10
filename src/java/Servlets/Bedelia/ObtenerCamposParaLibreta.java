/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets.Bedelia;

import Classes.Bedelia.Falta;
import Classes.Bedelia.Grupo;
import Classes.Bedelia.LibretaIndividual;
import Classes.FaltaSancion;
import Classes.Usuario;
import Manejadores.ManejadorBedelia;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
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
        request.setCharacterEncoding("UTF-8");
        Usuario u = (Usuario)sesion.getAttribute("usuario");
        if(u.isAdmin()|| u.isNotas() || u.isProfesor()){
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
                else {
                    if(request.getParameter("grilla")!=null){
                        int mes= Integer.valueOf(request.getParameter("grilla"));
                        int idLibreta = Integer.valueOf(request.getParameter("idLibreta"));
                        JsonObjectBuilder json = Json.createObjectBuilder(); 
                        Classes.Bedelia.Libreta libreta = mb.getLibretas().get(idLibreta);
                        JsonArrayBuilder jab1= Json.createArrayBuilder();
                        JsonArrayBuilder jab2= Json.createArrayBuilder();
                        FaltaSancion f;
                        int dia=1;
                        Iterator it;
                        for(LibretaIndividual li:libreta.getLibretasIndividuales().values()){
                            JsonArrayBuilder jab= Json.createArrayBuilder();
                            jab.add(Json.createObjectBuilder()
                                    .add("primerApellido",this.reemplazarCaracteresHtml(li.getAlumno().getPrimerApellido()))
                                    .add("primerNombre",this.reemplazarCaracteresHtml(li.getAlumno().getPrimerNombre()))
                                    .add("ci",li.getAlumno().getCi()));
                            String fecha="";
                            int id=-1;
                            String codigo="";
                            int canthoras=0;
                            int minutosTardes=0;
                            String observaciones;
                            if(li.getGrillaFaltasSanciones().get(mes)!=null){
                                for(LinkedList<FaltaSancion> lg:li.getGrillaFaltasSanciones().get(mes).values()){
                                    it= lg.iterator();
                                    while(it.hasNext()){
                                        f=(FaltaSancion)(it.next());
                                        if(f.getFalta()!=null){
                                            fecha=f.getFalta().getFecha();
                                            id= f.getFalta().getId();
                                            codigo = f.getFalta().getCodigoMotivo();
                                            canthoras=f.getFalta().getCanthoras();
                                            observaciones=f.getFalta().getObservaciones();
                                        }
                                        else{
                                            fecha=f.getSancion().getFecha();
                                            id= f.getSancion().getId();
                                            codigo="R";
                                            if(f.getSancion().getTipo()==1){
                                                codigo="M";
                                            }
                                            minutosTardes=f.getSancion().getMinutosTardes();
                                            observaciones=f.getSancion().getCausa();
                                        }
                                        dia=Integer.valueOf(fecha.split("-")[2]);
                                        jab2.add(Json.createObjectBuilder()
                                                .add("esFalta",(f.getFalta()!=null))
                                                .add("id", id)
                                                .add("codigo", codigo)
                                                .add("fecha", fecha)
                                                .add("cantHoras", canthoras)
                                                .add("minutosTardes", minutosTardes)
                                                .add("observaciones", observaciones)
                                                .add("idLibreta", idLibreta)
                                                .add("ciAlumno", li.getAlumno().getCi())
                                                .add("ciProfesor", libreta.getProfesor().getCi())
                                        );
                                    }
                                    jab.add(Json.createObjectBuilder()
                                        .add("dia", dia)
                                        .add("faltasSancionesxDia",jab2)
                                    );
                                }
                            }
                            jab1.add(Json.createObjectBuilder().add("alumno",jab));
                        }
                        json.add("alumnos", jab1);
                        Calendar fecha1 = java.util.Calendar.getInstance();
                        Calendar cal = new GregorianCalendar(fecha1.get(java.util.Calendar.YEAR), mes-1, 1); 
                        int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH); // 28
                        JsonArrayBuilder jab= Json.createArrayBuilder();
                            jab.add(Json.createObjectBuilder()
                                .add("cantDias", days)
                            );
                        json.add("cantDiasMes", jab);
                       // System.out.print(json.build());
                       out.print(json.build());
                    }
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
