/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manejadores;

import Classes.Cadete;
import Classes.Documento;
import Classes.Personal;
import Classes.RecordCadete;
import Classes.RecordCadetesFiltro;
import Classes.RecordPersonal;
import Classes.Tipo;
import Classes.TipoDocumento;
import Classes.TipoPersonal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import javax.servlet.http.Part;
import sun.swing.SwingUtilities2;

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
    public void setFiltroMostrar(RecordCadetesFiltro rf){
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
    public ArrayList<Personal> getPersonalFiltro(RecordCadetesFiltro rf) {
        boolean cumpleFiltro;
        ArrayList<Personal> ap= new ArrayList<>();
        //int i =0;
        for (Personal p : personal.get(1)) {
            Cadete c= (Cadete)p;
            cumpleFiltro=true;
            if(rf.armas!=null){
                cumpleFiltro=cumpleFiltro && Arrays.toString(rf.armas).contains(String.valueOf(c.getArma().getId()));
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
    public LinkedList<Personal> getCadetesListarNro(Boolean asc){//0=des,1=asc
        LinkedList<Personal> cadetes= new LinkedList();
        if(asc){
            for(Personal c : personal.get(1)){
                c=(Cadete)c;
                if(cadetes.isEmpty()){
                    cadetes.add(c);
                }
                else{
                    if(c.getNroInterno() >= ((Cadete)cadetes.getLast()).getNroInterno()){
                        cadetes.addLast(c);
                    }
                    else{
                        int i=0;
                        boolean agregue=false;
                        Iterator it = cadetes.iterator();
                        while(it.hasNext() && !agregue){
                            Cadete cadActual = (Cadete)it.next();
                            if(c.getNroInterno()<= cadActual.getNroInterno()){
                                cadetes.add(i, c);
                                agregue=true;
                            }
                            i++;
                        }
                    }
                }
            }
        }
        else{
            for(Personal c : personal.get(1)){
                c=(Cadete)c;
                if(cadetes.isEmpty()){
                    cadetes.add(c);
                }
                else{
                    if(c.getNroInterno() <= ((Cadete)cadetes.getLast()).getNroInterno()){
                        cadetes.addLast(c);
                    }
                    else{
                        int i=0;
                        boolean agregue=false;
                        Iterator it = cadetes.iterator();
                        while(it.hasNext() && !agregue){
                            Cadete cadActual = (Cadete)it.next();
                            if(c.getNroInterno()>= cadActual.getNroInterno()){
                                cadetes.add(i, c);
                                agregue=true;
                            }
                            i++;
                        }
                    }
                }
            }
        }
        return cadetes;
    }
    public LinkedList<Personal> getCadetesListarGrado(Boolean asc){//0=des,1=asc
        LinkedList<Personal> cadetes= new LinkedList();
        if(asc){
            Cadete c= null;
            for(Personal p : personal.get(1)){
                c=(Cadete)p;
                if(cadetes.isEmpty()){
                    cadetes.add(c);
                }
                else{
                    int i=0;
                    boolean agregue=false;
                    Iterator it = cadetes.iterator();
                    while(it.hasNext() && !agregue){
                        Cadete cadActual = (Cadete)it.next();
                        if(c.getGrado().getId()> cadActual.getGrado().getId()){
                            cadetes.add(i, c);
                            agregue=true;
                        }
                        else{
                            if(c.getGrado().getId()== cadActual.getGrado().getId()){
                                if(c.getCurso().getId()< cadActual.getCurso().getId()){
                                    cadetes.add(i, c);
                                    agregue=true;
                                }
                                else{
                                    if(c.getCurso().getId()==cadActual.getCurso().getId()){
                                        if(c.getPrimerApellido().compareToIgnoreCase(cadActual.getPrimerApellido())<= 0){
                                            cadetes.add(i, c);
                                            agregue=true;
                                        }
                                    }
                                }
                            }
                        }
                        i++;
                    }
                    if(!agregue){
                        cadetes.addLast(c);
                    }
                    
                    
                }
            }
        }
        else{
            Cadete c=null;
            for(Personal p : personal.get(1)){
                c=(Cadete)p;
                if(cadetes.isEmpty()){
                    cadetes.add(c);
                }
                else{
                    int i=0;
                    boolean agregue=false;
                    Iterator it = cadetes.iterator();
                    while(it.hasNext() && !agregue){
                        Cadete cadActual = (Cadete)it.next();
                        if(c.getGrado().getId()< cadActual.getGrado().getId()){
                            cadetes.add(i, c);
                            agregue=true;
                        }
                        else{
                            if(c.getGrado().getId()== cadActual.getGrado().getId()){
                                if(c.getCurso().getId()< cadActual.getCurso().getId()){
                                    cadetes.add(i, c);
                                    agregue=true;
                                }
                                else{
                                    if(c.getCurso().getId()==cadActual.getCurso().getId()){
                                        if(c.getPrimerApellido().compareToIgnoreCase(cadActual.getPrimerApellido())<= 0){
                                            cadetes.add(i, c);
                                            agregue=true;
                                        }
                                    }
                                }
                            }
                        }
                        i++;
                    }
                    if(!agregue){
                        cadetes.addLast(c);
                    }
                }
            }
        }
        return cadetes;
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
    public LinkedList<Personal> getCadetesListarNombre(Boolean asc){//0=des,1=asc
        LinkedList<Personal> cadetes= new LinkedList();
        if(asc){
            for(Personal c : personal.get(1)){
                c=(Cadete)c;
                String nombreC=reemplazarTildes(c.getPrimerNombre());
                if(cadetes.isEmpty()){
                    cadetes.add(c);
                }
                else{
                    String nombre=reemplazarTildes(((Cadete)cadetes.getLast()).getPrimerNombre());
                    if(nombreC.compareToIgnoreCase(nombre)>0){
                        cadetes.addLast(c);
                    }
                    else{
                        int i=0;
                        boolean agregue=false;
                        Iterator it = cadetes.iterator();
                        while(it.hasNext() && !agregue){
                            nombre=reemplazarTildes(((Cadete)it.next()).getPrimerNombre());
                            if(nombreC.compareToIgnoreCase(nombre)<=0){
                                cadetes.add(i, c);
                                agregue=true;
                            }
                            i++;
                        }
                    }
                }
            }
        }
        else{
            for(Personal c : personal.get(1)){
                c=(Cadete)c;
                String nombreC=reemplazarTildes(c.getPrimerNombre());
                if(cadetes.isEmpty()){
                    cadetes.add(c);
                }
                else{
                    String nombre=reemplazarTildes(((Cadete)cadetes.getLast()).getPrimerNombre());
                    if(nombreC.compareToIgnoreCase(nombre)<=0){
                        cadetes.addLast(c);
                    }
                    else{
                        int i=0;
                        boolean agregue=false;
                        Iterator it = cadetes.iterator();
                        while(it.hasNext() && !agregue){
                            nombre=reemplazarTildes(((Cadete)it.next()).getPrimerNombre());
                            if(nombreC.compareToIgnoreCase(nombre)>=0){
                                cadetes.add(i, c);
                                agregue=true;
                            }
                            i++;
                        }
                    }
                }
            }
        }
        return cadetes;
    }
    public LinkedList<Personal> getCadetesListarApellido(Boolean asc){//0=des,1=asc
        LinkedList<Personal> cadetes= new LinkedList();
        if(asc){
            for(Personal c : personal.get(1)){
                c=(Cadete)c;
                String nombreC=reemplazarTildes(c.getPrimerApellido());
                if(cadetes.isEmpty()){
                    cadetes.add(c);
                }
                else{
                    String nombre=reemplazarTildes(((Cadete)cadetes.getLast()).getPrimerApellido());
                    if(nombreC.compareToIgnoreCase(nombre)>0){
                        cadetes.addLast(c);
                    }
                    else{
                        int i=0;
                        boolean agregue=false;
                        Iterator it = cadetes.iterator();
                        while(it.hasNext() && !agregue){
                            nombre=reemplazarTildes(((Cadete)it.next()).getPrimerApellido());
                            if(nombreC.compareToIgnoreCase(nombre)<=0){
                                cadetes.add(i, c);
                                agregue=true;
                            }
                            i++;
                        }
                    }
                }
            }
        }
        else{
            for(Personal c : personal.get(1)){
                c=(Cadete)c;
                String nombreC=reemplazarTildes(c.getPrimerApellido());
                if(cadetes.isEmpty()){
                    cadetes.add(c);
                }
                else{
                    String nombre=reemplazarTildes(((Cadete)cadetes.getLast()).getPrimerApellido());
                    if(nombreC.compareToIgnoreCase(nombre)<=0){
                        cadetes.addLast(c);
                    }
                    else{
                        int i=0;
                        boolean agregue=false;
                        Iterator it = cadetes.iterator();
                        while(it.hasNext() && !agregue){
                            nombre=reemplazarTildes(((Cadete)it.next()).getPrimerApellido());
                            if(nombreC.compareToIgnoreCase(nombre)>=0){
                                cadetes.add(i, c);
                                agregue=true;
                            }
                            i++;
                        }
                    }
                }
            }
        }
        return cadetes;
    }
    public Documento getDocumento(int ci, int tipoPersonal, int id){
        return this.getPersonal(ci, tipoPersonal).getDocumentos().get(id);
    }
    //path -> Path context Servlet
    public boolean altaDocumento(Tipo tipoDocumento, int ci, Tipo tipoPersonal,Part archivo,String descripcion){
        ManejadorDocumentosBD md = new ManejadorDocumentosBD();
        Documento d = md.crearDocumento(tipoDocumento, ci, archivo,descripcion); //sube el archivo y lo agrega a la base de datos
        if(d!=null){
            this.getPersonal(ci, tipoPersonal.getId()).agregarDocumento(d); //lo agrega a memoria
            return true;
        }
        return false;
    }
    //path -> Path context Servlet
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
                    cadActual.setObservaciones(rp.observaciones);
                    cadActual.setPaseDirecto(rp.rc.paseDirecto);
                    cadActual.setPrimerApellido(rp.primerApellido);
                    cadActual.setPrimerNombre(rp.primerNombre);
                    cadActual.setProfesor(rp.profesor);
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
