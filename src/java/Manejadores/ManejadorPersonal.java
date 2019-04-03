/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manejadores;

import Classes.Cadete;
import Classes.Documento;
import Classes.Personal;
import Classes.Bedelia.Profesor;
import Classes.RecordFiltro;
import Classes.RecordCamposListar;
import Classes.RecordPersonal;
import Classes.Tipo;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import javax.servlet.http.Part;

/**
 *
 * @author Gisel
 */
public class ManejadorPersonal {
    
    private HashMap<Integer,LinkedList<Personal>> personal;//hashmap idTipoPersoanal -- 1-Cadetes,2-PSubalterno,3-Oficiales,4-Profesores
    //private HashMap<Integer,Personal>personalTodos;
    private ManejadorPersonal() {
        ManejadorPersonalBD mp = new ManejadorPersonalBD();
        personal= mp.obtenerPersonalEM();
        /*LinkedList<Personal> lp = null;
        personalTodos = new HashMap<>();
        for(int i=1 ; i<=4;i++){
           lp=personal.get(i);
           for(Personal p:lp){
               personalTodos.put(p.getCi(), p);
           }
        }*/
    }
    public static ManejadorPersonal getInstance() {
        return ManejadorPersonalHolder.INSTANCE;
    }
    public synchronized boolean actualizarGrados(String[] parameterValues) {
        //System.out.print(Arrays.toString(parameterValues));
        boolean sinError=true;
        ManejadorPersonalBD mp= new ManejadorPersonalBD();
        ManejadorCodigos mc= ManejadorCodigos.getInstance();
        int i=0;
        for(Personal p:personal.get(1)){
            if((parameterValues==null) || (!Arrays.toString(parameterValues).contains(String.valueOf(p.getCi())))){
                switch(p.getGrado().getId()){
                    case 16:case 17:case 18:case 19: //sgto hnrio 1ro, sgto hnrio,cabo hnrio, cad 3ro
                        sinError=sinError&&this.bajaCadetePosicion(p.getCi(), "Graduado.",i);
                    break;
                    case 20: //cad 2do pasa a cabo hrio NO a cad 3ro
                        if(((Cadete)p).getCarrera().getId()==2){ //cad 2do apoyo (por degradacion)
                            sinError=sinError&&this.bajaCadetePosicion(p.getCi(), "Graduado.",i);
                        }
                        else{
                            p.setGrado(mc.getGrado(p.getGrado().getId()-2));
                        }
                    break;
                    case 21: case 22://cad 1ro y asp
                        p.setGrado(mc.getGrado(p.getGrado().getId()-1));
                    break;
                }
            }
            else{
                ((Cadete)p).setRepitiente(true);
            }
            i++;
        }
        return sinError&&mp.actualizarGrados(parameterValues);
            
    }
    public void setFiltroMostrar(RecordFiltro rf){
        String filtroMostrar = "";
        ManejadorCodigos mc = ManejadorCodigos.getInstance();
        boolean guion=false;
        if(rf.armas!=null){
            filtroMostrar+="Armas:";
            for(String s:rf.armas){
                filtroMostrar+=" "+mc.getArma(Integer.valueOf(s)).getDescripcion();
            }
            guion=true;
        }
        if(rf.grados!=null){
            if(guion){
                filtroMostrar+=" - ";
            }
            filtroMostrar+="Grados:";
            for(String s:rf.grados){
                filtroMostrar+=" "+mc.getGrado(Integer.valueOf(s)).getDescripcion();
            }
            guion=true;
        }
        if(rf.cursos!=null){
            if(guion){
                filtroMostrar+=" - ";
            }
            filtroMostrar+="Cursos:";
            for(String s:rf.cursos){
                filtroMostrar+=" "+mc.getCurso(Integer.valueOf(s)).getDescripcion();
            }
            guion=true;
        }
        if(!rf.carrera.equals("TODOS")){
            if(guion){
                filtroMostrar+=" - ";
            }
            filtroMostrar+="Carrera:";
            if(rf.carrera.equals("C")){
                filtroMostrar+=" Comando";
            }
            else{
                filtroMostrar+=" Apoyo de S. y C.";
            }
            guion=true;
        }
        if(!rf.lmga.equals("TODOS")){
            if(guion){
                filtroMostrar+=" - ";
            }
            filtroMostrar+="LMGA:";
            if(rf.lmga.equals("S")){
                filtroMostrar+=" SI";
            }
            else{
                filtroMostrar+=" NO";
            }
            guion=true;
        }
        if(!rf.pd.equals("TODOS")){
            if(guion){
                filtroMostrar+=" - ";
            }
            filtroMostrar+="Pase Directo:";
            if(rf.pd.equals("S")){
                filtroMostrar+=" SI";
            }
            else{
                filtroMostrar+=" NO";
            }
            guion=true;
        }
        if(!rf.sexo.equals("TODOS")){
            if(guion){
                filtroMostrar+=" - ";
            }
            filtroMostrar+="Sexo:";
            filtroMostrar+=" "+rf.sexo;
            guion=true;
        }
        if(!rf.repitiente.equals("TODOS")){
            if(guion){
                filtroMostrar+=" - ";
            }
            filtroMostrar+="Repitiente:";
            if(rf.repitiente.equals("S")){
                filtroMostrar+=" SI";
            }
            else{
                filtroMostrar+=" NO";
            }
            guion=true;
        }
        if(rf.depNac!=null){
            if(guion){
                filtroMostrar+=" - ";
            }
            filtroMostrar+="Dpto. Nac.:";
            for(String s:rf.grados){
                filtroMostrar+=" "+mc.getDepartamento(Integer.valueOf(s)).getDescripcion();
            }
            guion=true;
        }
        if(rf.depDom!=null){
            if(guion){
                filtroMostrar+=" - ";
            }
            filtroMostrar+="Dpto. Dom.:";
            for(String s:rf.grados){
                filtroMostrar+=" "+mc.getDepartamento(Integer.valueOf(s)).getDescripcion();
            }
            guion=true;
        }
        if(rf.canthijos!=null){
            if(guion){
                filtroMostrar+=" - ";
            }
            filtroMostrar+="Dpto. Dom.:";
            for(String s:rf.canthijos){
                if(s.equals("4")){
                    filtroMostrar+=" + de 3";
                }
                else{
                    filtroMostrar+=" "+rf.canthijos;
                }
            }
        }
        rf.filtroMostrar=filtroMostrar;
    }
     public void setFiltroMostrarPersonal(RecordFiltro rf){
        String filtroMostrar = "";
        ManejadorCodigos mc = ManejadorCodigos.getInstance();
        boolean guion=false;
        if(rf.armas!=null){
            filtroMostrar+="Armas:";
            for(String s:rf.armas){
                filtroMostrar+=" "+mc.getArma(Integer.valueOf(s)).getDescripcion();
            }
            guion=true;
        }
        if(rf.grados!=null){
            if(guion){
                filtroMostrar+=" - ";
            }
            filtroMostrar+="Grados:";
            for(String s:rf.grados){
                filtroMostrar+=" "+mc.getGrado(Integer.valueOf(s)).getDescripcion();
            }
        }
        rf.filtroMostrar=filtroMostrar;
     }
    public ArrayList<Personal> getCadeteFiltro(RecordFiltro rf) {
        boolean cumpleFiltro;
        ArrayList<Personal> ap= new ArrayList<>();
        //int i =0;
        for (Personal p : personal.get(1)) {
            Cadete c= (Cadete)p;
            cumpleFiltro=true;
            if(rf.armas!=null){
                if(c.getArma()!=null){
                    cumpleFiltro=cumpleFiltro && Arrays.toString(rf.armas).contains(String.valueOf(c.getArma().getId()));
                }
                else{
                    cumpleFiltro=false;
                }
            }
            if(cumpleFiltro && rf.grados!=null){
                cumpleFiltro=cumpleFiltro && Arrays.toString(rf.grados).contains(String.valueOf(c.getGrado().getId()));
            }
            if(cumpleFiltro && rf.cursos!=null){
                cumpleFiltro=cumpleFiltro && Arrays.toString(rf.cursos).contains(String.valueOf(c.getCurso().getId()));
            }
            if(cumpleFiltro && !rf.carrera.equals("TODOS")){
                if(rf.carrera.equals("C")){
                    cumpleFiltro= c.getCarrera().getId()==1;
                }
                else{
                    cumpleFiltro= c.getCarrera().getId()==2;
                }
            }
            if(cumpleFiltro && !rf.lmga.equals("TODOS")){
                if(rf.lmga.equals("S")){
                    cumpleFiltro= (c.isLmga()==true);
                }
                else{
                     cumpleFiltro= (c.isLmga()==false);
                }
            }
            if(cumpleFiltro && !rf.pd.equals("TODOS")){
                if(rf.pd.equals("S")){
                    cumpleFiltro= (c.isPaseDirecto()==true);
                }
                else{
                     cumpleFiltro= (c.isPaseDirecto()==false);
                }
            }
            if(cumpleFiltro && !rf.sexo.equals("TODOS")){
                cumpleFiltro= (rf.sexo.equals(c.getSexo()));
            }
            if(cumpleFiltro && !rf.repitiente.equals("TODOS")){
                if(rf.repitiente.equals("S")){
                    cumpleFiltro= (c.isRepitiente()==true);
                }
                else{
                     cumpleFiltro= (c.isRepitiente()==false);
                }
            }
            if(cumpleFiltro && rf.depNac!=null){
                cumpleFiltro=cumpleFiltro && Arrays.toString(rf.depNac).contains(String.valueOf(c.getDepartamentoNac().getCodigo()));
            }
            if(cumpleFiltro && rf.depDom!=null){
                cumpleFiltro=cumpleFiltro && Arrays.toString(rf.depDom).contains(String.valueOf(c.getDepartamento().getCodigo()));
            }
            if(cumpleFiltro && rf.canthijos!=null){
                cumpleFiltro=cumpleFiltro && Arrays.toString(rf.canthijos).contains(String.valueOf(c.getHijos()));
            }
            if(cumpleFiltro){
                ap.add(p);
            }
        }
        setFiltroMostrar(rf);
        return ap;
    }
    private void imprimirEncabezado(PrintWriter out,RecordCamposListar rl,int tipoPersonal){
    out.print("<table style=\"width: 70%; margin:auto;\">");
    out.print("     <tr>\n" +
        "                <td colspan=\"3\">\n" +
        "                    <p align=\"left\">Escuela Militar</p>\n" +
        "                </td>\n" +
        "                <td colspan=\"3\">\n" +
        "                     <p align=\"right\">Jefatura de Estudios</p>\n" +
        "                </td>\n" +
        "            </tr>\n");
out.print("          <tr>\n" +
        "                <td colspan=\"6\" style=\"text-align: center;\">\n");
switch(tipoPersonal){
    case 1:out.print("       <h1>CADETES</h1>\n");break;
    case 2:out.print("       <h1>PERSONAL SUBALTERNO</h1>\n");break;
    case 3:out.print("       <h1>SEÑORES OFICIALES</h1>\n");break;
    case 4:out.print("       <h1>PROFESORES</h1>\n");break;
                    
}
switch(tipoPersonal){
    case 1:
out.print("                </td>\n" +
        "            </tr>\n");
        out.println("<tr>\n" +
                "        <td colspan=\"6\">\n" +
                "            <p>\n <b>FILTROS: </b> " +
                                rl.filtro +
                "            </p>\n" +
                "        </td>\n" +
                "    </tr>");
         out.print("<tr style='background-color:#ffcc66'>");
                out.print("<td style='width: 5%' align='center'><h3 style='margin:2%;'></h3></td>");
                if(rl.carrera){
                    out.print("<td align='center'><h3 style='margin:2%;'>Carrera</h3></td>");
                }
                if(rl.numero){
                    out.print("<td align='center'><h3 style='margin:2%;'>Número</h3></td>");
                }
                if(rl.ci){
                    out.print("<td align='center'><h3 style='margin:2%;'>C.I.</h3></td>");
                }
                if(rl.grado){
                    out.print("<td align='center'><h3 style='margin:2%;'>Grado</h3></td>");
                }
                if(rl.primerNombre){
                    out.print("<td align='center'><h3 style='margin:2%;'>Primer nombre</h3></td>");
                }
                if(rl.segundoNombre){
                    out.print("<td align='center'><h3 style='margin:2%;'>Segundo nombre</h3></td>");
                }
                if(rl.primerApellido){
                    out.print("<td align='center'><h3 style='margin:2%;'>Primer apellido</h3></td>");
                }
                if(rl.segundoApellido){
                    out.print("<td align='center'><h3 style='margin:2%;'>Segundo apellido</h3></td>");
                }
                if(rl.curso){
                    out.print("<td align='center'><h3 style='margin:2%;'>Curso</h3></td>");
                }
                if(rl.arma){
                    out.print("<td align='center'><h3 style='margin:2%;'>Arma</h3></td>");
                }
                if(rl.lmga){
                    out.print("<td align='center'><h3 style='margin:2%;'>LMGA</h3></td>");
                }
                if(rl.pd){
                    out.print("<td align='center'><h3 style='margin:2%;'>PD</h3></td>");
                }
                if(rl.sexo){
                    out.print("<td align='center'><h3 style='margin:2%;'>Sexo</h3></td>");
                }
                if(rl.dptoNac){
                    out.print("<td align='center'><h3 style='margin:2%;'>Dpto.Nac.</h3></td>");
                }
                if(rl.localidadNac){
                    out.print("<td align='center'><h3 style='margin:2%;'>Loc.Nac.</h3></td>");
                }
                if(rl.dptoDom){
                    out.print("<td align='center'><h3 style='margin:2%;'>Dpto.Dom.</h3></td>");
                }
                if(rl.localidadDom){
                    out.print("<td align='center'><h3 style='margin:2%;'>Loc.Dom.</h3></td>");
                }
                if(rl.repitiente){
                    out.print("<td align='center'><h3 style='margin:2%;'>Repite</h3></td>");
                }
                if(rl.cantHijos){
                    out.print("<td align='center'><h3 style='margin:2%;'>Cant. Hijos</h3></td>");
                }
                if(rl.talleOperacional){
                    out.print("<td align='center'><h3 style='margin:2%;'>T.O.</h3></td>");
                }
                if(rl.talleBotas){
                    out.print("<td align='center'><h3 style='margin:2%;'>T.B.</h3></td>");
                }
                if(rl.talleQuepi){
                    out.print("<td align='center'><h3 style='margin:2%;'>T.Q.</h3></td>");
                }
                 out.print("</tr>");
        break;
    case 2: case 3: case 4:
        out.print("                </td>\n" +
        "            </tr>\n");
        out.println("<tr>\n" +
                "        <td colspan=\"6\">\n" +
                "            <p>\n <b>FILTROS: </b> " +
                                rl.filtro +
                "            </p>\n" +
                "        </td>\n" +
                "    </tr>");
        out.print("<tr style='background-color:#ffcc66'>");
        out.print("<td align='center'><h3 style='margin:2%;'>Número</h3></td>");
        out.print("<td align='center'><h3 style='margin:2%;'>C.I.</h3></td>");
        out.print("<td align='center'><h3 style='margin:2%;'>Grado</h3></td>");
        out.print("<td align='center'><h3 style='margin:2%;'>Primer nombre</h3></td>");
        out.print("<td align='center'><h3 style='margin:2%;'>Segundo nombre</h3></td>");
        out.print("<td align='center'><h3 style='margin:2%;'>Primer apellido</h3></td>");
        out.print("<td align='center'><h3 style='margin:2%;'>Segundo apellido</h3></td>");
        out.print("<td align='center'><h3 style='margin:2%;'>Arma</h3></td>");
         break;       
        }
                
    }
    public void imprimirListado(String[] lista, RecordCamposListar rl, PrintWriter out,String Context,int tipoPersonal) {
        if(rl.ficha){
            for(Personal p: personal.get(tipoPersonal)){
                if(Arrays.toString(lista).contains(String.valueOf(p.getCi()))){
                   ((Cadete)p).imprimirFicha(out,Context);
                   out.print("        </table> <h1 style='page-break-after:always' > </h1>");
                }
            }
        }
        else{
            if(tipoPersonal!=4){
                imprimirEncabezado(out,rl,tipoPersonal);
                Cadete c;
                int i=0;
                String color;
                for(Personal p: personal.get(tipoPersonal)){
                    if ((i%2)==0){
                        color=" #ccccff";
                    }
                    else{
                        color=" #ffff99";
                    }
                    i++;
                    if(Arrays.toString(lista).contains(String.valueOf(p.getCi()))){
                        out.print("<tr style='background-color:"+color+"'>");
                        switch(tipoPersonal){
                            case 1:
                                c = (Cadete)p;
                                out.print("<td style='width: 5%' align='center'><h3 style='margin:2%;'></h3></td>");
                                if(rl.carrera){
                                    out.print("<td align='center'><h3 style='margin:2%;'>"+c.getCarrera().getDescripcion()+"</h3></td>");
                                }
                                if(rl.numero){
                                    out.print("<td align='center'><h3 style='margin:2%;'>"+c.getNroInterno()+"</h3></td>");
                                }
                                if(rl.ci){
                                    out.print("<td align='center'><h3 style='margin:2%;'>"+c.getCi()+"</h3></td>");
                                }
                                if(rl.grado){
                                    out.print("<td align='center'><h3 style='margin:2%;'>"+c.getGrado().getAbreviacion()+"</h3></td>");
                                }
                                if(rl.primerNombre){
                                    out.print("<td align='center'><h3 style='margin:2%;'>"+c.getPrimerNombre()+"</h3></td>");
                                }
                                if(rl.segundoNombre){
                                    out.print("<td align='center'><h3 style='margin:2%;'>"+c.getSegundoNombre()+"</h3></td>");
                                }
                                if(rl.primerApellido){
                                    out.print("<td align='center'><h3 style='margin:2%;'>"+c.getPrimerApellido()+"</h3></td>");
                                }
                                if(rl.segundoApellido){
                                    out.print("<td align='center'><h3 style='margin:2%;'>"+c.getSegundoApellido()+"</h3></td>");
                                }
                                if(rl.curso){
                                    out.print("<td align='center'><h3 style='margin:2%;'>"+c.getCurso().getAbreviacion()+"</h3></td>");
                                }
                                if(rl.arma){
                                    out.print("<td align='center'><h3 style='margin:2%;'>"+c.getArma().getDescripcion()+"</h3></td>");
                                }
                                if(rl.lmga){
                                    out.print("<td align='center'><h3 style='margin:2%;'>"+c.isLmga()+"</h3></td>");
                                }
                                if(rl.pd){
                                    out.print("<td align='center'><h3 style='margin:2%;'>"+c.isPaseDirecto()+"</h3></td>");
                                }
                                if(rl.sexo){
                                    out.print("<td align='center'><h3 style='margin:2%;'>"+c.getSexo()+"</h3></td>");
                                }
                                if(rl.dptoNac){
                                    out.print("<td align='center'><h3 style='margin:2%;'>"+c.getDepartamentoNac().getDescripcion()+"</h3></td>");
                                }
                                if(rl.localidadNac){
                                    out.print("<td align='center'><h3 style='margin:2%;'>"+c.getLocalidadNac()+"</h3></td>");
                                }
                                if(rl.dptoDom){
                                    out.print("<td align='center'><h3 style='margin:2%;'>"+c.getDepartamento().getDescripcion()+"</h3></td>");
                                }
                                if(rl.localidadDom){
                                    out.print("<td align='center'><h3 style='margin:2%;'>"+c.getLocalidad()+"</h3></td>");
                                }
                                if(rl.repitiente){
                                    out.print("<td align='center'><h3 style='margin:2%;'>"+c.isRepitiente()+"</h3></td>");
                                }
                                if(rl.cantHijos){
                                    out.print("<td align='center'><h3 style='margin:2%;'>"+c.getHijos()+"</h3></td>");
                                }
                                if(rl.talleOperacional){
                                    out.print("<td align='center'><h3 style='margin:2%;'>"+c.getTalleOperacional()+"</h3></td>");
                                }
                                if(rl.talleBotas){
                                    out.print("<td align='center'><h3 style='margin:2%;'>"+c.getTalleBotas()+"</h3></td>");
                                }
                                if(rl.talleQuepi){
                                    out.print("<td align='center'><h3 style='margin:2%;'>"+c.getTalleQuepi()+"</h3></td>");
                                }
                            break;
                            case 2: case 3: case 4:
                                out.print("<td align='center'><h3 style='margin:2%;'>"+p.getNroInterno()+"</h3></td>");
                                out.print("<td align='center'><h3 style='margin:2%;'>"+p.getCi()+"</h3></td>");
                                out.print("<td align='center'><h3 style='margin:2%;'>"+p.getGrado().getAbreviacion()+"</h3></td>");
                                out.print("<td align='center'><h3 style='margin:2%;'>"+p.getPrimerNombre()+"</h3></td>");
                                out.print("<td align='center'><h3 style='margin:2%;'>"+p.getSegundoNombre()+"</h3></td>");
                                out.print("<td align='center'><h3 style='margin:2%;'>"+p.getPrimerApellido()+"</h3></td>");
                                out.print("<td align='center'><h3 style='margin:2%;'>"+p.getSegundoApellido()+"</h3></td>");
                                out.print("<td align='center'><h3 style='margin:2%;'>"+p.getArma().getDescripcion()+"</h3></td>");
                            break;
                        }
                     out.print("</tr>");
                    }
                }
                out.print("</table>");
            }
            else{
                imprimirEncabezado(out,rl,tipoPersonal);
                Profesor c;
                int i=0;
                String color;
                for(Profesor p: ManejadorProfesores.getInstance().getProfesores()){
                    if ((i%2)==0){
                        color=" #ccccff";
                    }
                    else{
                        color=" #ffff99";
                    }
                    i++;
                    if(Arrays.toString(lista).contains(String.valueOf(p.getCi()))){
                        out.print("<tr style='background-color:"+color+"'>");
                                out.print("<td align='center'><h3 style='margin:2%;'>"+p.getCi()+"</h3></td>");
                                if(p.getGrado()!=null){
                                    out.print("<td align='center'><h3 style='margin:2%;'>"+p.getGrado().getAbreviacion()+"</h3></td>");
                                }
                                else{
                                     out.print("<td align='center'><h3 style='margin:2%;'></h3></td>");
                                }
                                out.print("<td align='center'><h3 style='margin:2%;'>"+p.getPrimerNombre()+"</h3></td>");
                                out.print("<td align='center'><h3 style='margin:2%;'>"+p.getSegundoNombre()+"</h3></td>");
                                out.print("<td align='center'><h3 style='margin:2%;'>"+p.getPrimerApellido()+"</h3></td>");
                                out.print("<td align='center'><h3 style='margin:2%;'>"+p.getSegundoApellido()+"</h3></td>");
                     out.print("</tr>");
                    }
                }
                out.print("</table>");
            }
        }
    }
    public ArrayList<Personal> getPersonalFiltro(RecordFiltro rf,int tipoPersonal) {
       boolean cumpleFiltro;
        ArrayList<Personal> ap= new ArrayList<>();
        //int i =0;
        for (Personal p : personal.get(tipoPersonal)) {
            cumpleFiltro=true;
            if(rf.armas!=null){
                if(p.getArma()!=null){
                    cumpleFiltro=cumpleFiltro && Arrays.toString(rf.armas).contains(String.valueOf(p.getArma().getId()));
                }
                else{
                    cumpleFiltro=false;
                }
            }
            if(cumpleFiltro && rf.grados!=null){
                cumpleFiltro=cumpleFiltro && Arrays.toString(rf.grados).contains(String.valueOf(p.getGrado().getId()));
            }
            if(cumpleFiltro){
                ap.add(p);
            }
        }
        setFiltroMostrarPersonal(rf);
        return ap;
    }

    public boolean bajaPersonal(int ci,int tipo) {
        ManejadorPersonalBD mp= new ManejadorPersonalBD();
        if(mp.eliminarPersonal(ci)){
            boolean continuo=true;
            Iterator it = personal.get(tipo).iterator();
            Personal profActual;
            while(it.hasNext() && continuo){
                profActual = (Personal)it.next();
                if(ci==profActual.getCi()){
                    it.remove();
                    return true;
                }
            }
        }
        return false;
    }

    public boolean agregarPersonal(RecordPersonal rp, int tipo) {
        ManejadorPersonalBD mp= new ManejadorPersonalBD();
        Personal p= mp.agregarPersonal(rp,null);
        if(p!=null){
            if(personal.get(tipo).isEmpty()){
                personal.get(tipo).add(p);
            }
            else{
                int i=0;
                boolean agregue=false;
                Iterator it = personal.get(tipo).iterator();
                Personal profActual;
                while(it.hasNext() && !agregue){
                    profActual = (Personal)it.next();
                    if(p.getGrado().getId()> profActual.getGrado().getId()){
                       personal.get(tipo).add(i, p);
                       agregue=true;
                   }
                   i++;
                }
                if(!agregue){
                    personal.get(tipo).addLast(p);
                }
            }
            return true;
        }
        return false;
    }
    private static class ManejadorPersonalHolder {

        private static final ManejadorPersonal INSTANCE = new ManejadorPersonal();
    }
    public LinkedList<Personal> obtenerCadetes(){
        return personal.get(1);
    }
    public LinkedList<Personal> obtenerPersonalSubalterno(){
        return personal.get(2);
    }
    public LinkedList<Personal> obtenerOficiales(){
        return personal.get(3);
    }
    public LinkedList<Personal> obtenerProfesores(){
        return personal.get(4);
    }
    public Personal getPersonal(int ci, int tipoPersonal){
        boolean encontre=false;
        int i=0;
        int size= personal.get(tipoPersonal).size();
        while(!encontre && i<size){
            if(personal.get(tipoPersonal).get(i).getCi()==ci){
                return personal.get(tipoPersonal).get(i);
            }
            else{
                i++;
            }
        }
        return null;
    }
    public LinkedList<Personal> getPersonalListarNro(int tipo,Boolean asc){//0=des,1=asc
        LinkedList<Personal> personalLista= new LinkedList();
        if(asc){
            for(Personal c : personal.get(tipo)){
                if(personalLista.isEmpty()){
                    personalLista.add(c);
                }
                else{
                    if(c.getNroInterno() >= (personalLista.getLast()).getNroInterno()){
                        personalLista.addLast(c);
                    }
                    else{
                        int i=0;
                        boolean agregue=false;
                        Iterator it = personalLista.iterator();
                        while(it.hasNext() && !agregue){
                            Personal perActual = (Personal)it.next();
                            if(c.getNroInterno()<= perActual.getNroInterno()){
                                personalLista.add(i, c);
                                agregue=true;
                            }
                            i++;
                        }
                    }
                }
            }
        }
        else{
            for(Personal c : personal.get(tipo)){
                if(personalLista.isEmpty()){
                    personalLista.add(c);
                }
                else{
                    if(c.getNroInterno() <= (personalLista.getLast()).getNroInterno()){
                        personalLista.addLast(c);
                    }
                    else{
                        int i=0;
                        boolean agregue=false;
                        Iterator it = personalLista.iterator();
                        while(it.hasNext() && !agregue){
                            Personal perActual = (Personal)it.next();
                            if(c.getNroInterno()>= perActual.getNroInterno()){
                                personalLista.add(i, c);
                                agregue=true;
                            }
                            i++;
                        }
                    }
                }
            }
        }
        return personalLista;
    }
    public LinkedList<Personal> getPersonalListarGrado(int tipo,Boolean asc){//0=des,1=asc
        LinkedList<Personal> personalLista= new LinkedList();
        System.out.print(tipo);
        if(asc){
            for(Personal c : personal.get(tipo)){
                if(personalLista.isEmpty()){
                    personalLista.add(c);
                }
                else{
                    int i=0;
                    boolean agregue=false;
                    Iterator it = personalLista.iterator();
                    Personal personalActual;
                    Cadete cadActual;
                    while(it.hasNext() && !agregue){
                        personalActual=(Personal)it.next();
                        if(c.getGrado().getId()> personalActual.getGrado().getId()){
                            personalLista.add(i, c);
                            agregue=true;
                        }
                        else{
                            if(tipo==1){
                                cadActual = (Cadete)personalActual;
                                if(c.getGrado().getId()== personalActual.getGrado().getId()){
                                    if(((Cadete)c).getCurso().getId()< cadActual.getCurso().getId()){
                                        personalLista.add(i, c);
                                        agregue=true;
                                    }
                                    else{
                                        if(((Cadete)c).getCurso().getId()==cadActual.getCurso().getId()){
                                            if(c.getPrimerApellido().compareToIgnoreCase(cadActual.getPrimerApellido())<= 0){
                                                personalLista.add(i, c);
                                                agregue=true;
                                            }
                                        }
                                    }
                                }
                            }
                            else{
                                if(c.getGrado().getId()== personalActual.getGrado().getId()){
                                    if(c.getPrimerApellido().compareToIgnoreCase(personalActual.getPrimerApellido())<= 0){
                                        personalLista.add(i, c);
                                        agregue=true;
                                    }
                                }
                            }
                        }
                        i++;
                    }
                    if(!agregue){
                        personalLista.addLast(c);
                    }
                }
            }
        }
        else{
            for(Personal c : personal.get(tipo)){
                if(personalLista.isEmpty()){
                    personalLista.add(c);
                }
                else{
                    int i=0;
                    boolean agregue=false;
                    Iterator it = personalLista.iterator();
                    Personal personalActual;
                    Cadete cadActual;
                    while(it.hasNext() && !agregue){
                        personalActual=(Personal)it.next();
                        if(c.getGrado().getId()< personalActual.getGrado().getId()){
                            personalLista.add(i, c);
                            agregue=true;
                        }
                        else{
                            if(tipo==1){
                                cadActual = (Cadete)personalActual;
                                if(c.getGrado().getId()== cadActual.getGrado().getId()){
                                    if(((Cadete)c).getCurso().getId()< cadActual.getCurso().getId()){
                                        personalLista.add(i, c);
                                        agregue=true;
                                    }
                                    else{
                                        if(((Cadete)c).getCurso().getId()==cadActual.getCurso().getId()){
                                            if(c.getPrimerApellido().compareToIgnoreCase(cadActual.getPrimerApellido())<= 0){
                                                personalLista.add(i, c);
                                                agregue=true;
                                            }
                                        }
                                    }
                                }
                            }
                            else{
                                if(c.getGrado().getId()== personalActual.getGrado().getId()){
                                    if(c.getPrimerApellido().compareToIgnoreCase(personalActual.getPrimerApellido())<= 0){
                                        personalLista.add(i, c);
                                        agregue=true;
                                    }
                                }
                            }
                        }
                        i++;
                    }
                    if(!agregue){
                        personalLista.addLast(c);
                    }
                }
            }
        }
        return personalLista;
    }
    private String reemplazarTildes(String s){
        String original = "ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖØÙÚÛÜÝßàáâãäåæçèéêëìíîïðñòóôõöøùúûüýÿ";
        String ascii = "AAAAAAACEEEEIIIIDNOOOOOOUUUUYBaaaaaaaceeeeiiiionoooooouuuuyy";
        String output = s;
        for (int i=0; i<original.length(); i++) {
    // Reemplazamos los caracteres especiales.

        output = output.replace(original.charAt(i), ascii.charAt(i));

        }
        return output;
    }
    public LinkedList<Personal> getPersonalListarNombre(int tipo,Boolean asc){//0=des,1=asc
        LinkedList<Personal> personalListar= new LinkedList();
        if(asc){
            for(Personal c : personal.get(tipo)){
                String nombreC=reemplazarTildes(c.getPrimerNombre());
                if(personalListar.isEmpty()){
                    personalListar.add(c);
                }
                else{
                    String nombre=reemplazarTildes((personalListar.getLast()).getPrimerNombre());
                    if(nombreC.compareToIgnoreCase(nombre)>0){
                        personalListar.addLast(c);
                    }
                    else{
                        int i=0;
                        boolean agregue=false;
                        Iterator it = personalListar.iterator();
                        Personal personalActual;
                        while(it.hasNext() && !agregue){
                            personalActual=((Personal)it.next());
                            nombre=reemplazarTildes(personalActual.getPrimerNombre());
                            if(nombreC.compareToIgnoreCase(nombre)<=0){
                                personalListar.add(i, c);
                                agregue=true;
                            }
                            i++;
                        }
                    }
                }
            }
        }
        else{
            for(Personal c : personal.get(tipo)){
                String nombreC=reemplazarTildes(c.getPrimerNombre());
                if(personalListar.isEmpty()){
                    personalListar.add(c);
                }
                else{
                    String nombre=reemplazarTildes((personalListar.getLast()).getPrimerNombre());
                    if(nombreC.compareToIgnoreCase(nombre)<=0){
                        personalListar.addLast(c);
                    }
                    else{
                        int i=0;
                        boolean agregue=false;
                        Iterator it = personalListar.iterator();
                        Personal personalActual;
                        while(it.hasNext() && !agregue){
                            personalActual=((Personal)it.next());
                            nombre=reemplazarTildes(personalActual.getPrimerNombre());
                            if(nombreC.compareToIgnoreCase(nombre)>=0){
                                personalListar.add(i, c);
                                agregue=true;
                            }
                            i++;
                        }
                    }
                }
            }
        }
        return personalListar;
    }
    public LinkedList<Personal> getPersonalListarApellido(int tipo,Boolean asc){//0=des,1=asc
        LinkedList<Personal> personalListar= new LinkedList();
        if(asc){
            for(Personal c : personal.get(tipo)){
                String nombreC=reemplazarTildes(c.getPrimerApellido());
                if(personalListar.isEmpty()){
                    personalListar.add(c);
                }
                else{
                    String nombre=reemplazarTildes((personalListar.getLast()).getPrimerApellido());
                    if(nombreC.compareToIgnoreCase(nombre)>0){
                        personalListar.addLast(c);
                    }
                    else{
                        int i=0;
                        boolean agregue=false;
                        Iterator it = personalListar.iterator();
                        Personal personalActual;
                        while(it.hasNext() && !agregue){
                            personalActual=((Personal)it.next());
                            nombre=reemplazarTildes(personalActual.getPrimerApellido());
                            if(nombreC.compareToIgnoreCase(nombre)<=0){
                                personalListar.add(i, c);
                                agregue=true;
                            }
                            i++;
                        }
                    }
                }
            }
        }
        else{
            for(Personal c : personal.get(tipo)){
                String nombreC=reemplazarTildes(c.getPrimerApellido());
                if(personalListar.isEmpty()){
                    personalListar.add(c);
                }
                else{
                    String nombre=reemplazarTildes((personalListar.getLast()).getPrimerApellido());
                    if(nombreC.compareToIgnoreCase(nombre)<=0){
                        personalListar.addLast(c);
                    }
                    else{
                        int i=0;
                        boolean agregue=false;
                        Iterator it = personalListar.iterator();
                        Personal personalActual;
                        while(it.hasNext() && !agregue){
                            personalActual=((Personal)it.next());
                            nombre=reemplazarTildes(personalActual.getPrimerApellido());
                            if(nombreC.compareToIgnoreCase(nombre)>=0){
                                personalListar.add(i, c);
                                agregue=true;
                            }
                            i++;
                        }
                    }
                }
            }
        }
        return personalListar;
    }
    public Documento getDocumento(int ci, int tipoPersonal, int id){
        return this.getPersonal(ci, tipoPersonal).getDocumentos().get(id);
    }
    public boolean altaDocumento(Tipo tipoDocumento, int ci, Tipo tipoPersonal,Part archivo,String descripcion){
        ManejadorDocumentosBD md = new ManejadorDocumentosBD();
        Documento d = md.crearDocumento(tipoDocumento, ci, archivo,descripcion); //sube el archivo y lo agrega a la base de datos
        if(d!=null){
            this.getPersonal(ci, tipoPersonal.getId()).agregarDocumento(d); //lo agrega a memoria
            return true;
        }
        return false;
    }
    public boolean bajaDocumento(int ci, Tipo tipoPersonal,int idDocumento){
        ManejadorDocumentosBD md = new ManejadorDocumentosBD();
        Documento d = this.getPersonal(ci, tipoPersonal.getId()).getDocumentos().get(idDocumento);
        if(d!=null){
            if(md.eliminarDocumento(d, ci)){ //lo elimina del disco duro y de la base de datos
                this.getPersonal(ci, tipoPersonal.getId()).eliminarDocumento(idDocumento); //lo elimina de la memoria
                return true;
            }
        }
        return false;
    }
    public Cadete getCadete(int ci){
        Cadete c= null;
        for(Personal p : personal.get(1)){
                if(p.getCi()==ci){
                    c=(Cadete)p;
                    break;
                }
        }
        return c;
    }
    private void agregarEnOrdenCadete(Cadete c){
        if(c!=null){
            if(personal.get(1).isEmpty()){
                personal.get(1).add(c);
            }
            else{
                int i=0;
                boolean agregue=false;
                Iterator it = personal.get(1).iterator();
                while(it.hasNext() && !agregue){
                    Cadete cadActual = (Cadete)it.next();
                    if(c.getGrado().getId()> cadActual.getGrado().getId()){
                        personal.get(1).add(i, c);
                        agregue=true;
                    }
                    else{
                        if(c.getGrado().getId()== cadActual.getGrado().getId()){
                            if(c.getCurso().getId()< cadActual.getCurso().getId()){
                                personal.get(1).add(i, c);
                                agregue=true;
                            }
                            else{
                                if(c.getCurso().getId()==cadActual.getCurso().getId()){
                                    if(c.getPrimerApellido().compareToIgnoreCase(cadActual.getPrimerApellido())<= 0){
                                        personal.get(1).add(i, c);
                                        agregue=true;
                                    }
                                }
                            }
                        }
                    }
                    i++;
                }
                if(!agregue){
                    personal.get(1).addLast(c);
                }
            }
        }
    }
    public synchronized boolean agregarCadete(RecordPersonal rc,Part foto){//grado.codigo asc, idCurso asc, idArma asc
        ManejadorPersonalBD mp= new ManejadorPersonalBD();
        Cadete c = (Cadete)mp.agregarPersonal(rc,foto);
        if(c!=null){
            this.agregarEnOrdenCadete(c);
            return true;
        }
        return false;
    }
    public synchronized boolean modificarCadete(RecordPersonal rp, Part foto) { 
        ManejadorPersonalBD mp= new ManejadorPersonalBD();
        if( mp.modificarPersonal(rp,foto)){
            Iterator it = personal.get(1).iterator();
            boolean continuo=true;
            ManejadorCodigos mc = ManejadorCodigos.getInstance();
            while(it.hasNext() && continuo ){
                Cadete cadActual = (Cadete)it.next();
                if(cadActual.getCi()==rp.ci){
                    if(foto!=null){
                        cadActual.setFoto(rp.ci+ManejadorDocumentosBD.getFileName(foto).substring(ManejadorDocumentosBD.getFileName(foto).lastIndexOf(".")));
                    }
                    cadActual.setArma(mc.getArma(rp.idArma));
                    cadActual.setCarrera(mc.getCarrera(rp.rc.idcarrera));
                    cadActual.setCc(rp.rc.cc);
                    cadActual.setCcNro(rp.rc.ccNro);
                    cadActual.setCurso(mc.getCurso(rp.rc.idcurso));
                    cadActual.setDepartamento(mc.getDepartamento(rp.rc.iddepartamento));
                    cadActual.setDepartamentoNac(mc.getDepartamento(rp.rc.iddepartamentoNac));
                    cadActual.setDerecha(rp.rc.derecha);
                    cadActual.setDomicilio(rp.rc.domicilio);
                    cadActual.setEmail(rp.rc.email);
                    cadActual.setEstadoCivil(mc.getEstadoCivil(rp.rc.idestadoCivil));
                    cadActual.setFechaNac(rp.rc.fechaNac);
                    cadActual.setGrado(mc.getGrado(rp.idGrado));
                    cadActual.setHijos(rp.rc.hijos);
                    cadActual.setLmga(rp.rc.lmga);
                    cadActual.setLocalidad(rp.rc.localidad);
                    cadActual.setLocalidadNac(rp.rc.localidadNac);
                    cadActual.setNotaPaseDirecto(rp.rc.notaPaseDirecto);
                    cadActual.setObservaciones(rp.rc.observaciones);
                    cadActual.setPaseDirecto(rp.rc.paseDirecto);
                    cadActual.setPrimerApellido(rp.primerApellido);
                    cadActual.setPrimerNombre(rp.primerNombre);
                    cadActual.setRepitiente(rp.rc.repitiente);
                    cadActual.setSegundoApellido(rp.segundoApellido);
                    cadActual.setSegundoNombre(rp.segundoNombre);
                    cadActual.setSexo(rp.rc.sexo);
                    cadActual.setTalleBotas(rp.rc.talleBotas);
                    cadActual.setTalleOperacional(rp.rc.talleOperacional);
                    cadActual.setTalleQuepi(rp.rc.talleQuepi);
                    cadActual.setTelefono(rp.rc.telefono);
                    continuo=false;
                }
            }
            return true;
       };
       return false;
    }
    public synchronized boolean bajaCadete(int ci, String causa){
        ManejadorPersonalBD mp= new ManejadorPersonalBD();
        if( mp.bajaCadete(ci,causa)){
            Iterator it = personal.get(1).iterator();
            boolean continuo=true;
            ManejadorCodigos mc = ManejadorCodigos.getInstance();
            while(it.hasNext() && continuo ){
                Cadete cadActual = (Cadete)it.next();
                if(cadActual.getCi()==ci){
                    HashMap<Integer,Documento> hd=cadActual.getDocumentos();
                    hd.values().stream().forEach((d) -> {
                        ManejadorDocumentosBD.eliminarArchivo(ci, d);
                    });
                    it.remove();
                    continuo=false;
                }
            }
            return !continuo;
        }
        return false;
    }
    public boolean bajaCadetePosicion(int ci, String causa,int pos){
        ManejadorPersonalBD mp= new ManejadorPersonalBD();
        if( mp.bajaCadete(ci,causa)){
            Personal p = personal.get(1).get(pos);
            HashMap<Integer,Documento> hd=p.getDocumentos();
            hd.values().stream().forEach((d) -> {
                ManejadorDocumentosBD.eliminarArchivo(ci, d);
            });
            personal.get(1).remove(pos);
            return true;
        }
        return false;
    }
    public synchronized Boolean crearCadeteHistorial(int ci){
        ManejadorPersonalBD mp = new ManejadorPersonalBD();
        Cadete c= mp.crearCadeteHistorial(ci);
        if(c!=null){
            this.agregarEnOrdenCadete(c);
            return true;
        }
        return false;        
    }
    public Boolean existeCadeteHistorial(int ci){
        ManejadorPersonalBD mp = new ManejadorPersonalBD();
        return mp.existeCadeteDesdeHistorial(ci);
    }
    
}
