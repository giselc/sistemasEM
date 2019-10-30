/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets.Bedelia;

import Classes.Bedelia.LibretaIndividual;
import Classes.Bedelia.Promedio;
import Manejadores.ManejadorBedelia;
import java.io.IOException;
import java.io.PrintWriter;
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
public class Promedios extends HttpServlet {

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
        String redirect;
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            int idLibreta= Integer.valueOf(request.getParameter("idLibreta"));
            Manejadores.ManejadorBedelia mb = ManejadorBedelia.getInstance();
            if(request.getParameter("cambiarGrilla")!=null){
                int mes = Integer.valueOf(request.getParameter("mes"));
                Classes.Bedelia.Libreta l = mb.getLibretas().get(idLibreta);
                String html= mb.cambiarGrillaPromedio(l,mes,request.getContextPath());
                String botonGuardarCerrar ="";
                if(mes<=6 && !l.getMesesCerrados().containsKey(mes) && ((l.getMateria().isSecundaria()&& !l.getMesesCerrados().containsKey(11) &&!l.getMesesCerrados().containsKey(12))|| (!l.getMateria().isSecundaria() && !l.getMesesCerrados().containsKey(13)))){ 
                    if(l.getMateria().isSecundaria()){
                            botonGuardarCerrar+="<input type='submit' name='boton1' onclick=\"this.form.action=form.action = 'Promedios?idLibreta="+l.getId()+"&guardarPromedios=1'\" value='GUARDAR CAMBIOS' style='background-color: #ff6600;border-radius: 15px; color: #ffffff;font-size: large'>";
                    }
                    botonGuardarCerrar+="<input type='submit' name='boton2' onclick=\"this.form.action=form.action = 'Promedios?idLibreta="+l.getId()+"&cerrarMes=1';\" value='CERRAR MES' style=\"background-color: #cd0a0a; border-radius: 15px; color: #ffffff;font-size: large\" >";
                }
                else if(mes>6 && mes<=11 && !l.getMesesCerrados().containsKey(mes) && ((l.getMateria().isSecundaria()&&!l.getMesesCerrados().containsKey(12))|| (!l.getMateria().isSecundaria() && !l.getMesesCerrados().containsKey(13)))){
                    if(l.getMateria().isSecundaria()){
                            botonGuardarCerrar+="<input type='submit' name='boton1' onclick=\"this.form.action=form.action = 'Promedios?idLibreta="+l.getId()+"&guardarPromedios=1'\" value='GUARDAR CAMBIOS' style='background-color: #ff6600;border-radius: 15px; color: #ffffff;font-size: large'>";
                    }
                    botonGuardarCerrar+="<input type='submit' name='boton2' onclick=\"this.form.action=form.action = 'Promedios?idLibreta="+l.getId()+"&cerrarMes=1';\" value='CERRAR MES' style=\"background-color: #cd0a0a; border-radius: 15px; color: #ffffff;font-size: large\" >";
                }
                else if(mes==12 && !l.getMesesCerrados().containsKey(mes)){
                    botonGuardarCerrar+="<input type='submit' name='boton1' onclick=\"this.form.action=form.action = 'Promedios?idLibreta="+l.getId()+"&guardarPromedios=1'\" value='GUARDAR CAMBIOS' style='background-color: #ff6600;border-radius: 15px; color: #ffffff;font-size: large'>";
                    botonGuardarCerrar+="<input type='submit' name='boton2' onclick=\"if(confirm('Se cerrará la libreta. Desea continuar?')){this.form.action=form.action = 'Promedios?idLibreta="+l.getId()+"&cerrarMes=1';}else{return false;};\" value='CERRAR MES' style=\"background-color: #cd0a0a; border-radius: 15px; color: #ffffff;font-size: large\" >";
                }
                else if(mes==13 && !l.getMesesCerrados().containsKey(mes)){
                    botonGuardarCerrar+="<input type='submit' name='boton2' onclick=\"if(confirm('Se cerrará la libreta. Desea continuar?')){this.form.action=form.action = 'Promedios?idLibreta="+l.getId()+"&cerrarMes=1';}else{return false;};\" value='CERRAR MES' style=\"background-color: #cd0a0a; border-radius: 15px; color: #ffffff;font-size: large\" >";
                }
                
                
                
                
                JsonObjectBuilder json = Json.createObjectBuilder(); 
                JsonArrayBuilder jab= Json.createArrayBuilder();
                jab.add(Json.createObjectBuilder()
                    .add("html",html)
                    .add("botonGuardarCerrar", botonGuardarCerrar)
                );
                json.add("msj", jab);
                out.print(json.build());
            }
            else{
                Classes.Bedelia.Libreta l = mb.getLibretas().get(idLibreta);
                double valorPromedio = 0;
                String juicio="";
                String mensaje = "";
                boolean ok=true;
                int mes = Integer.valueOf(request.getParameter("mesPromedio"));
                if(request.getParameter("guardarPromedios")!=null || request.getParameter("cerrarMes")!=null){
                   for(LibretaIndividual li : l.getLibretasIndividuales().values()){
                       if(l.getMateria().isSecundaria()){
                            if(!request.getParameter("PROMEDIO-"+li.getAlumno().getCi()).equals("")){
                                valorPromedio = Double.valueOf(request.getParameter("PROMEDIO-"+li.getAlumno().getCi()));
                            }
                            else{
                                valorPromedio=1;
                            }
                            if(request.getParameter("JUICIO-"+li.getAlumno().getCi())!=null){
                               juicio=request.getParameter("JUICIO-"+li.getAlumno().getCi());
                            }
                       }
                       else{
                           valorPromedio = mb.calculoPromedio(li, mes,l);
                       }
                        ok=ok&&mb.guardarPromedio(li,valorPromedio,mes,juicio);
                             
                    }
                    if(ok){
                        mensaje="Promedios guardados satisfactoriamente.";
                        if(request.getParameter("cerrarMes")!=null){
                            if(mb.cerrarPromedio(l, mes)){
                                mensaje="Promedios guardados y cerrados satisfactoriamente.";
                            }
                            else{
                                mensaje+=" ERROR al cerrar los promedios.";
                            }
                        }
                    }
                    else{
                        mensaje="ERROR al guardar los promedios. Contacte al administrador";
                    }
                    if(request.getParameter("JUICIOGRUPAL")!=null){
                        mb.guardarJuicioGrupal(request.getParameter("JUICIOGRUPAL"),mes,l);
                    }
                    sesion.setAttribute("Mensaje", mensaje);
                    response.sendRedirect("libreta.jsp?id="+l.getId());

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
