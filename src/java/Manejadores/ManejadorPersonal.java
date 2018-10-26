/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manejadores;

import Classes.Cadete;
import Classes.Documento;
import Classes.Personal;
import Classes.Tipo;
import Classes.TipoDocumento;
import Classes.TipoPersonal;
import java.util.ArrayList;
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
        return personal.get(tipoPersonal).get(ci).getDocumentos().get(id);
    }
    //path -> Path context Servlet
    public boolean altaDocumento(Tipo tipoDocumento, int ci, Tipo tipoPersonal,Part archivo){
        ManejadorDocumentosBD md = new ManejadorDocumentosBD();
        Documento d = md.crearDocumento(tipoDocumento, ci, archivo); //sube el archivo y lo agrega a la base de datos
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
           
    public synchronized boolean agregarCadete(){
        
        
        return true;
    }
            
    
}
