/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manejadores;

import Classes.Bedelia.CursoBedelia;
import Classes.Bedelia.Falta;
import Classes.Bedelia.Grupo;
import Classes.Bedelia.Libreta;
import Classes.Bedelia.LibretaIndividual;
import Classes.Bedelia.Materia;
import Classes.Bedelia.Nota;
import Classes.Bedelia.Notificacion;
import Classes.Bedelia.Profesor;
import Classes.Bedelia.Promedio;
import Classes.Bedelia.RecordFalta;
import Classes.Bedelia.RecordPromedios;
import Classes.Bedelia.RecordSancion;
import Classes.Bedelia.Sancion;
import Classes.Cadete;
import Classes.FaltaSancion;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author Gisel
 */
public class ManejadorBedelia {
    private HashMap<Integer, CursoBedelia> cursos;
    private HashMap<Integer, Materia> materias;
    private HashMap<Integer,Libreta> libretas; //HashMap<idLibreta,Libreta>
    private LinkedList<Notificacion> notificacionesNuevas;
    private LinkedList<Notificacion> notificacionesLeidas;
    static final double VALOR_EXONERACION = 7.5;
    
    private ManejadorBedelia() {
        ManejadorBedeliaBD mb= new ManejadorBedeliaBD();
        materias = mb.obtenerMaterias();
        cursos = mb.obtenerCursos(materias);
        libretas = mb.obtenerLibretas(materias,cursos);
        notificacionesNuevas = mb.obtenerNotificaciones(libretas,1);
        notificacionesLeidas = mb.obtenerNotificaciones(libretas,2);
    }
    public synchronized static ManejadorBedelia getInstance() {
        return ManejadorBedeliaHolder.INSTANCE;
    }
    public synchronized boolean asociarMateriasCurso(String[] idMaterias, String idCurso) {
        ManejadorBedeliaBD mb= new ManejadorBedeliaBD();
        if(mb.asociarMateriasCurso(idMaterias,idCurso)){
            HashMap mateCurso= cursos.get(Integer.valueOf(idCurso)).getMaterias();
            for(String m: idMaterias){
               mateCurso.put(Integer.valueOf(m), materias.get(Integer.valueOf(m)));
            }
            return true;
        };
        return false;
    }
    public LinkedList<Notificacion> getNotificacionesNuevas() {
        return notificacionesNuevas;
    }
    public LinkedList<Notificacion> getNotificacionesLeidas() {
        return notificacionesLeidas;
    }
    public synchronized boolean asociarAlumnosGrupo(LinkedList<Cadete> alumnos, Grupo g) {
       //si existen libretas asociadas, crear las libretas individuales para los nuevos alumnos a asociar
       ManejadorBedeliaBD mb= new ManejadorBedeliaBD();
       LinkedList<Libreta> listaL= this.getLibretasAsociadasAGrupo(g);
       if(mb.asociarAlumnosGrupo(alumnos,listaL,g)){
            for(Cadete c:alumnos){
                if(!g.getAlumnos().containsKey(c.getCi())){
                    g.getAlumnos().put(c.getCi(), c);
                    for(Libreta l:listaL){
                        LibretaIndividual li=mb.obtenerLibretaIndividual(l.getId(),c.getCi());
                        if(li!=null){
                            l.getLibretasIndividuales().put(c.getCi(),li);
                        }
                        else{
                            l.getLibretasIndividuales().put(c.getCi(), new LibretaIndividual(l.getId(), c));
                        }
                    }
                }
            } 
            return true;
       }
       return false;
    }
    private LinkedList<Libreta> getLibretasAsociadasAGrupo(Grupo g) {
        LinkedList<Libreta> l=new LinkedList<>();
        for(Libreta libreta:libretas.values()){
            if(libreta.getGrupo().getCusoBedelia().getId()==g.getCusoBedelia().getId() && libreta.getGrupo().getNombre().equals(g.getNombre()) && libreta.getGrupo().getAnio()==g.getAnio()){
                l.add(libreta);
            }
        }
        return l;
    }
    public synchronized void desasociarAlumnoGrupos(Integer ciAlumno) {
        ManejadorBedeliaBD mb = new ManejadorBedeliaBD();
        LinkedList<Libreta> listL;
        for(CursoBedelia cb : cursos.values()){
            for(Grupo g:cb.getGrupos()){
                if(g.getAlumnos().containsKey(ciAlumno)){
                    listL = this.getLibretasAsociadasAGrupo(g);
                    if(mb.desasociarAlumnoGrupo(ciAlumno,g,listL)){
                        g.getAlumnos().remove(ciAlumno);
                        for(Libreta l: listL){
                            l.getLibretasIndividuales().remove(ciAlumno); //baja pero no elimino las notas que ya estaban
                        }
                    }
                }
            }
        }
    }
    public synchronized boolean desasociarAlumnoGrupo(Integer ciAlumno, Grupo grupo) {
        LinkedList<Libreta> listL = this.getLibretasAsociadasAGrupo(grupo);
        ManejadorBedeliaBD mb = new ManejadorBedeliaBD();
        if(mb.desasociarAlumnoGrupo(ciAlumno,grupo,listL)){
            grupo.getAlumnos().remove(ciAlumno);
            for(Libreta l: listL){
                l.getLibretasIndividuales().remove(ciAlumno); //baja pero no elimino las notas que ya estaban
            }
            return true;
        }
        return false;
    }
    public synchronized boolean desasociarAlumnosGrupo(String[] listaAlumnos, Grupo grupo) {
        LinkedList<Libreta> listL = this.getLibretasAsociadasAGrupo(grupo);
        ManejadorBedeliaBD mb = new ManejadorBedeliaBD();
        if(mb.desasociarAlumnosGrupo(listaAlumnos,grupo,listL)){
            for (String s:listaAlumnos){
                grupo.getAlumnos().remove(Integer.valueOf(s));
                for(Libreta l: listL){
                    l.getLibretasIndividuales().get(Integer.valueOf(s)).setActivo(false); //baja pero no elimino las notas que ya estaban
                }
            }
            return true;
        }
        return false;
    }
    public synchronized Libreta crearLibreta(int idCurso, int anioGrupo, String nombreGrupo, int idMateria, int ciProfesor, String salon) {
        ManejadorBedeliaBD mb = new ManejadorBedeliaBD();
        ManejadorProfesores mp = ManejadorProfesores.getInstance();
        Grupo g= cursos.get(idCurso).getGrupo(anioGrupo, nombreGrupo);
        int idLibreta = mb.crearLibreta(idCurso,anioGrupo,nombreGrupo,idMateria,ciProfesor,salon,g.getAlumnos());
        if(idLibreta!=-1){
            HashMap<Integer,LibretaIndividual> libretasIndividuales= new HashMap<>();
            for(Cadete c: g.getAlumnos().values()){
                libretasIndividuales.put(c.getCi(), new LibretaIndividual(idLibreta, c));
            }
            Profesor prof= mp.getProfesor(ciProfesor);
            Libreta l = new Libreta(idLibreta,materias.get(idMateria),g,prof,salon,libretasIndividuales,new LinkedList<>(),false,false,new HashMap<>(),"","");
            libretas.put(idLibreta, l);
            prof.getLibretas().put(idLibreta, l);
            return l;
        }
        return null;
    }
    public synchronized void agregarFalta(Libreta l, Cadete c, String fecha, String codigoFalta,int cantHoras, String observaciones) {
        ManejadorBedeliaBD mb= new ManejadorBedeliaBD();
        int id= mb.agregarFalta(l,c,fecha,codigoFalta,cantHoras,observaciones);
        if(id!=-1){
            Falta f = new Falta(id, fecha, cantHoras, codigoFalta, observaciones);
            LibretaIndividual li=l.getLibretasIndividuales().get(c.getCi());
            li.getFaltas().put(id,f);
            int mesFalta= Integer.valueOf(f.getFecha().split("-")[1]);
            int diaFalta = Integer.valueOf(f.getFecha().split("-")[2]);
            if(li.getGrillaFaltasSanciones().get(mesFalta)==null){
                li.getGrillaFaltasSanciones().put(mesFalta, new HashMap<>());
            }
            if(li.getGrillaFaltasSanciones().get(mesFalta).get(diaFalta)==null){
                li.getGrillaFaltasSanciones().get(mesFalta).put(diaFalta,new LinkedList<>());
            }
            li.getGrillaFaltasSanciones().get(mesFalta).get(diaFalta).add(new FaltaSancion(f, null));
            RecordFalta rf= new RecordFalta();
            rf.idFalta=f.getId();
            rf.cantHoras=f.getCanthoras();
            rf.codigoMotivo=f.getCodigoMotivo();
            rf.observaciones=f.getObservaciones();
            int j= mb.agregarNotificacion(l,c,rf,null,fecha,false);
            if(j!=-1){
                boolean agregue=false;
                Iterator it = notificacionesNuevas.iterator();
                int i=0;
                while(it.hasNext() && !agregue){
                    String fechaNotificacionActual=((Notificacion)it.next()).getFecha();
                    if(fechaNotificacionActual.compareTo(fecha)<=0){ 
                        notificacionesNuevas.add(i,new Notificacion(j,l,c,rf,null,1,fecha,false));
                        agregue=true;
                    }
                    i++;
                }
                if(!agregue)
                    notificacionesNuevas.add(new Notificacion(j,l,c,rf,null,1,fecha,false));
            };
        }
    }
    public synchronized boolean marcarLeidoNotificacion(int id, boolean aLeido) {
        ManejadorBedeliaBD mb = new ManejadorBedeliaBD();
        if(mb. marcarLeidoNotificacion(id,aLeido)){
            Iterator it;
                Iterator it1;
            if(aLeido){//es nuevo pasa a Leido
                it=notificacionesNuevas.iterator();
                it1=notificacionesLeidas.iterator();
            }
            else{
                it=notificacionesLeidas.iterator();
                it1=notificacionesNuevas.iterator();
            }
            Notificacion notActual;
            boolean encontre = false;
            while(it.hasNext() && !encontre){
                notActual=(Notificacion)it.next();
                if(notActual.getId()==id){
                    encontre=true;
                    //agrego a la lista destino por orden de fecha;
                    int i=0;
                    boolean agregue=false;
                    while(it1.hasNext()&&!agregue){
                        if(notActual.getFecha().compareTo(((Notificacion)it1.next()).getFecha())>=0){
                            if(aLeido){
                                notificacionesLeidas.add(i,notActual);
                            }
                            else{
                                notificacionesNuevas.add(i,notActual);  
                            };
                            agregue=true;
                        }
                        i++;
                    }
                    if(!agregue){
                        if(aLeido){
                            notificacionesLeidas.addLast(notActual);
                        }
                        else{
                            notificacionesNuevas.addLast(notActual);  
                        };
                    }
                    //elimino de la lista origen
                    it.remove();
                }
            }
            return true;
        }
        else{
            return false;
        }
    }
    public synchronized boolean eliminarNotificacion(int id, boolean esLeido) {
        ManejadorBedeliaBD mb = new ManejadorBedeliaBD();
        if(mb.eliminarNotificacion(id)){
            Iterator it;
            if(esLeido){
                it=notificacionesLeidas.iterator();
            }
            else{
                it=notificacionesNuevas.iterator();
            }
            Notificacion notActual;
            boolean encontre = false;
            while(it.hasNext() && !encontre){
                notActual=(Notificacion)it.next();
                if(notActual.getId()==id){
                    encontre=true;
                    //elimino de la lista origen
                    it.remove();
                }
            }
            return true;
        }
        else{
            return false;
        }
    }
    public synchronized boolean eliminarFalta(int idFalta, int idLibreta, int ciAlumno) {
        ManejadorBedeliaBD mb = new ManejadorBedeliaBD();
        if(mb. eliminarFalta(idFalta)){
            LibretaIndividual li=libretas.get(idLibreta).getLibretasIndividuales().get(ciAlumno);
            String fechaFalta= li.getFaltas().get(idFalta).getFecha();
            int mesFalta= Integer.valueOf(fechaFalta.split("-")[1]);
            int diaFalta= Integer.valueOf(fechaFalta.split("-")[2]);
            Iterator it=li.getGrillaFaltasSanciones().get(mesFalta).get(diaFalta).iterator();
            FaltaSancion faltaActual;
            while(it.hasNext()){
                faltaActual=(FaltaSancion)it.next();
                if(faltaActual.getFalta()!=null && faltaActual.getFalta().getId()==idFalta){
                    RecordFalta rf= new RecordFalta();
                    rf.idFalta=faltaActual.getFalta().getId();
                    rf.cantHoras=faltaActual.getFalta().getCanthoras();
                    rf.codigoMotivo=faltaActual.getFalta().getCodigoMotivo();
                    rf.observaciones=faltaActual.getFalta().getObservaciones();
                    Libreta l=libretas.get(idLibreta);
                    Cadete c=ManejadorPersonal.getInstance().getCadete(ciAlumno);
                    int id= mb.agregarNotificacion(l, c, rf, null, fechaFalta,true);
                    if(id!=-1){
                        notificacionesNuevas.add(new Notificacion(id, libretas.get(idLibreta),c,rf, null,1, fechaFalta, true));
                    }
                    it.remove();
                    li.getFaltas().remove(idFalta);
                    return true;
                }
            }
        }
        return false;
    }
    public Falta obtenerFalta(int idFalta, int idLibreta, int ciAlumno) {
        return libretas.get(idLibreta).getLibretasIndividuales().get(ciAlumno).getFaltas().get(idFalta);
    }
    public synchronized void agregarSancion(Libreta l, Cadete c, String fecha, String codigoSancion, Integer minutosTardes, String causa) {
        ManejadorBedeliaBD mb= new ManejadorBedeliaBD();
        int codigo=2;
        if(codigoSancion.equals("M")){
            codigo=1;
        }
        int id= mb.agregarSancion(l,c,fecha,codigo,minutosTardes,causa);
        if(id!=-1){
            Sancion s = new Sancion(id, codigo, minutosTardes, causa, fecha);
            LibretaIndividual li=l.getLibretasIndividuales().get(c.getCi());
            li.getSanciones().put(id,s);
            int mes= Integer.valueOf(fecha.split("-")[1]);
            int dia = Integer.valueOf(fecha.split("-")[2]);
            if(li.getGrillaFaltasSanciones().get(mes)==null){
                li.getGrillaFaltasSanciones().put(mes, new HashMap<>());
            }
            if(li.getGrillaFaltasSanciones().get(mes).get(dia)==null){
                li.getGrillaFaltasSanciones().get(mes).put(dia,new LinkedList<>());
            }
            li.getGrillaFaltasSanciones().get(mes).get(dia).add(new FaltaSancion(null, s));
            RecordSancion rf= new RecordSancion();
            rf.idSancion=id;
            rf.minutosTardes=minutosTardes;
            rf.tipo=codigo;
            rf.causa=causa;
            int j= mb.agregarNotificacion(l,c,null,rf,fecha,false);
            if(j!=-1){
                boolean agregue=false;
                Iterator it = notificacionesNuevas.iterator();
                int i=0;
                while(it.hasNext() && !agregue){
                    String fechaNotificacionActual=((Notificacion)it.next()).getFecha();
                    if(fechaNotificacionActual.compareTo(fecha)<=0){ 
                        notificacionesNuevas.add(i,new Notificacion(j,l,c,null,rf,1,fecha,false));
                        agregue=true;
                    }
                    i++;
                }
                if(!agregue)
                    notificacionesNuevas.add(new Notificacion(j,l,c,null,rf,1,fecha,false));
            };
        }
    }
    public synchronized boolean eliminarSancion(int id, int idLibreta, int ciAlumno) {
        ManejadorBedeliaBD mb = new ManejadorBedeliaBD();
        if(mb.eliminarSancion(id)){
            LibretaIndividual li=libretas.get(idLibreta).getLibretasIndividuales().get(ciAlumno);
            String fecha= li.getSanciones().get(id).getFecha();
            int mes= Integer.valueOf(fecha.split("-")[1]);
            int dia= Integer.valueOf(fecha.split("-")[2]);
            Iterator it=li.getGrillaFaltasSanciones().get(mes).get(dia).iterator();
            FaltaSancion faltaActual;
            while(it.hasNext()){
                faltaActual=(FaltaSancion)it.next();
                if(faltaActual.getSancion()!=null && faltaActual.getSancion().getId()==id){
                    RecordSancion rf= new RecordSancion();
                    rf.idSancion=faltaActual.getSancion().getId();
                    rf.minutosTardes=faltaActual.getSancion().getMinutosTardes();
                    rf.tipo=faltaActual.getSancion().getTipo();
                    rf.causa=faltaActual.getSancion().getCausa();
                    Libreta l=libretas.get(idLibreta);
                    Cadete c=ManejadorPersonal.getInstance().getCadete(ciAlumno);
                    int idNot= mb.agregarNotificacion(l, c, null, rf, fecha,true);
                    if(idNot!=-1){
                        notificacionesNuevas.add(new Notificacion(id, libretas.get(idLibreta),c,null, rf,1, fecha, true));
                    }
                    it.remove();
                    li.getSanciones().remove(id);
                    return true;
                }
            }
        }
        return false;
    }
    //retorna el id de la nota agregada;
    public synchronized int agregarNota(int ciAlumno, int ciProfesor, int idLibreta, int tipo, int mes, double valor, String obs, String fecha) {
        ManejadorBedeliaBD mb= new ManejadorBedeliaBD();
        int id = mb.agregarNota(ciAlumno,ciProfesor,idLibreta,tipo,mes,valor,obs,fecha);
        if(id!=-1){
            LibretaIndividual li = libretas.get(idLibreta).getLibretasIndividuales().get(ciAlumno);
            switch (tipo){
                case 2:     
                    if(!li.getNotasOrales().containsKey(mes)){
                        li.getNotasOrales().put(mes, new LinkedList<>());
                    }
                    li.getNotasOrales().get(mes).add(new Nota(id,fecha,tipo,obs,valor));
                    break;
                case 1:
                    if(!li.getNotasEscritos().containsKey(mes)){
                        li.getNotasEscritos().put(mes, new LinkedList<>());
                    }
                    li.getNotasEscritos().get(mes).add(new Nota(id,fecha,tipo,obs,valor)); 
                    break;
                case 3:
                    li.setPrimerParcial(new Nota(id, fecha, tipo, obs, valor));
                    break;
                case 4:
                    li.setSegundoParcial(new Nota(id, fecha, tipo, obs, valor));
                    break;
            }
        }
        return id;
    }
    public boolean eliminarNota(int idNota, int idLibreta, int ciProfesor, int ciAlumno, int tipo,int mes) {
        ManejadorBedeliaBD mb = new ManejadorBedeliaBD();
        LinkedList<Nota> listaRemover=null;
        LibretaIndividual li=libretas.get(idLibreta).getLibretasIndividuales().get(ciAlumno);
        if(mb.eliminarNota(idNota)){
            if(tipo==1||tipo==2){
                if(tipo==1){
                    listaRemover=li.getNotasEscritos().get(mes);
                }
                else{
                    listaRemover=li.getNotasOrales().get(mes);
                }
                Iterator it=listaRemover.iterator();
                Nota n;
                while(it.hasNext()){
                    n=(Nota)it.next();
                    if(n.getId()==idNota){
                        it.remove();
                        break;
                    }
                }
            }
            else{
                if(tipo==3){
                    li.setPrimerParcial(null);
                }
                else{
                    li.setSegundoParcial(null);
                }
            }
            return true;
        }
        return false;
    }
    public synchronized String cambiarGrillaPromedio(Libreta l, int mes, String contextPath) {
        String fila="<tr style='background-color:#ffcc66;padding:0px'><td></td><td></td>";
        String html = "<tr style='background-color:#ffcc66;padding:0px'>"
                + "     <td></td>"
                + "     <td>NOMBRE Y APELLIDO</td>";
                switch(mes){
                    case 3: html+="<td colspan='2'>MARZO</td>"; fila+="<td>O</td><td>E</td>"; break;
                    case 4 : html+="<td colspan='2'>ABRIL</td>"; fila+="<td>O</td><td>E</td>"; break;
                    case 5: html+="<td colspan='2'>MAYO</td>"; fila+="<td>O</td><td>E</td>"; break;
                    case 6: html+="<td colspan='2'>JUNIO</td>"; fila+="<td>O</td><td>E</td>"; break;
                    case 7: html+="<td colspan='2'>JULIO</td>"; fila+="<td>O</td><td>E</td>"; break;
                    case 8: html+="<td colspan='2'>AGOSTO</td>"; fila+="<td>O</td><td>E</td>"; break;
                    case 9: html+="<td colspan='2'>SETIEMBRE</td>"; fila+="<td>O</td><td>E</td>"; break;
                    case 10: html+="<td colspan='2'>OCTUBRE</td>"; fila+="<td>O</td><td>E</td>"; break;
                    case 11:
                        html+="<td colspan='3'>MARZO</td>"; fila+="<td>O</td><td>E</td><td>P</td>"; 
                        html+="<td colspan='3'>ABRIL</td>"; fila+="<td>O</td><td>E</td><td>P</td>";
                        html+="<td colspan='3'>MAYO</td>"; fila+="<td>O</td><td>E</td><td>P</td>"; 
                        html+="<td colspan='3'>JUNIO</td>"; fila+="<td>O</td><td>E</td><td>P</td>"; 
                        html+="<td>PRIMER PARCIAL</b></td>"; fila+="<td>E</td>";
                        break;
                    case 13:
                        if(l.getMateria().isSemestral()&&l.getMateria().getSemestre()==1){
                            html+="<td>MARZO</td>"; fila+="<td>P</td>";
                            html+="<td>ABRIL</td>"; fila+="<td>P</td>";
                            html+= "<td>MAYO</td>"; fila+="<td>P</td>";
                            html+= "<td>JUNIO</td>"; fila+="<td>P</td>";
                        }
                        else{
                            if(!l.getMateria().isSemestral()){
                                html+="<td>MARZO</td>"; fila+="<td>P</td>";
                                html+="<td>ABRIL</td>"; fila+="<td>P</td>";
                                html+= "<td>MAYO</td>"; fila+="<td>P</td>";
                                html+= "<td>JUNIO</td>"; fila+="<td>P</td>";
                            }
                            html+= "<td>JULIO</td>"; fila+="<td>P</td>";
                            html+= "<td>AGOSTO</td>"; fila+="<td>P</td>";
                            html+="<td>SETIEMBRE</td>"; fila+="<td>P</td>";
                            html+= "<td>OCTUBRE</td>"; fila+="<td>P</td>";
                        }
                            break;
                       
                    case 12: 
                        html+="<td>PRIMERA REUNI&Oacute;N</b></td>"; fila+="<td>P</td>";
                        html+= "<td colspan='3'>JULIO</td>"; fila+="<td>O</td><td>E</td><td>P</td>";
                        html+= "<td colspan='3'>AGOSTO</td>"; fila+="<td>O</td><td>E</td><td>P</td>";
                        html+="<td colspan='3'>SETIEMBRE</td>"; fila+="<td>O</td><td>E</td><td>P</td>";
                        html+= "<td colspan='3'>OCTUBRE</td>"; fila+="<td>O</td><td>E</td><td>P</td>";
                        html+="<td>SEGUNDO PARCIAL</b></td>"; fila+="<td>E</td>";
                        break;
                    
                        
                }
                    
                
            
                    html+= "<td>PROMEDIO</td>";fila+="<td></td>";
                    if(mes==11||mes==12){
                         html+="<td>JUICIO</b></td>"; fila+="<td></td>";
                    }
        html+= "</tr>";
        html+=fila;
        int i=0;
        String color="";
        for(LibretaIndividual li: l.getLibretasIndividuales().values()){
            if ((i%2)==0){
                color=" #ccccff";
            }
            else{
                color=" #ffff99";
            }
            i++;
            html+="<tr style='background-color:"+color+"'>"
                    + "<td>";
            if(li.getAlumno().getFoto()!=""){
                html+="<a href=\"Listar?List[]="+li.getAlumno().getCi()+"&fichas=1&tipoPersonal=1\" target=\"_blank\">   <p align=\"center\"><label for=\"uploadImage\" ><img src=\""+contextPath+"/Imagenes?foto="+li.getAlumno().getCi()+"\" id=\"uploadPreview\" style=\"width: 50px; height: 65px;\"/></label></p></a>";
            }
            else{
                html+="<a href=\"Listar?List[]="+li.getAlumno().getCi()+"&fichas=1&tipoPersonal=1\" target=\"_blank\">   <p align=\"center\"><label for=\"uploadImage\" ><img src=\"images/silueta.jpg\" id=\"uploadPreview\" style=\"width: 50px; height: 65px;\"/></label></p></a>";
            }
            html+="</td>"
                + "<td>";
            html+= li.getAlumno().getPrimerApellido()+ " " + li.getAlumno().getPrimerNombre();
            html+="</td>";
            switch(mes){
                case 11:
                    html+=notasMesHTML(li, 3);
                    html+=promedioMesHTML(li, 3);
                    html+=notasMesHTML(li, 4);
                    html+=promedioMesHTML(li, 4);
                    html+=notasMesHTML(li, 5);
                    html+=promedioMesHTML(li, 5);
                    html+=notasMesHTML(li, 6);
                    html+=promedioMesHTML(li, 6);
                    if(li.getPrimerParcial()!=null){
                        html+="<td><b title='PRIMER PARCIAL&#10;Fecha alta: "+li.getPrimerParcial().getFecha()+"&#10;Nota: "+li.getPrimerParcial().getNota()+"&#10;Observaciones: "+li.getPrimerParcial().getObservacion()+"'><p>"+li.getPrimerParcial().getNota()+"</p></b></td>";
                    }
                    else{
                        html+="<td></td>";
                    }
                    break;
                case 13:
                    if(l.getMateria().isSemestral()&&l.getMateria().getSemestre()==1){
                        html+=promedioMesHTML(li, 3);
                        html+=promedioMesHTML(li, 4);
                        html+=promedioMesHTML(li, 5);
                        html+=promedioMesHTML(li, 6);
                    }
                    else{
                        if(!l.getMateria().isSemestral()){
                            html+=promedioMesHTML(li, 3);
                            html+=promedioMesHTML(li, 4);
                            html+=promedioMesHTML(li, 5);
                            html+=promedioMesHTML(li, 6);
                        }
                        html+=promedioMesHTML(li, 7);
                        html+=promedioMesHTML(li, 8);
                        html+=promedioMesHTML(li, 9);
                        html+=promedioMesHTML(li, 10);
                    }
                    break;
                case 12: 
                    html+="<td><b><p>"+li.getPromedios().get(11).getNota()+"</p></b></td>"; //primeraReunion
                    html+=notasMesHTML(li, 7);
                    html+=promedioMesHTML(li, 7);
                    html+=notasMesHTML(li, 8);
                    html+=promedioMesHTML(li, 8);
                    html+=notasMesHTML(li, 9);
                    html+=promedioMesHTML(li, 9);
                    html+=notasMesHTML(li, 10);
                    html+=promedioMesHTML(li, 10);
                    if(li.getSegundoParcial()!=null){
                        html+="<td><b title='SEGUNDO PARCIAL&#10;Fecha alta: "+li.getSegundoParcial().getFecha()+"&#10;Nota: "+li.getSegundoParcial().getNota()+"&#10;Observaciones: "+li.getSegundoParcial().getObservacion()+"'><p>"+li.getSegundoParcial().getNota()+"</p></b></td>";
                    }
                    else{
                        html+="<td></td>";
                    }
                    break;                    
                default : html+=notasMesHTML(li, mes); break;
            }
            html+= "<td style='color:#1e2eef'><b>";
            if(l.getMateria().isSecundaria()){
                if(li.getPromedios().containsKey(mes)){
                        html+="<input type='number' min = '1' step='0.01' max=12 value='"+li.getPromedios().get(mes).getNota()+"' name='PROMEDIO-"+li.getAlumno().getCi()+"'/>";

                }
                else{
                    html+="<input type='number' min = '1' step='0.01' max=12 value='' name='PROMEDIO-"+li.getAlumno().getCi()+"'/>";
                }
            }
            else{
                html+=this.calculoPromedio(li,mes,l);
            }
            html+="</b></td>";
            if(mes==11){
                
                html+="<td><input type='text' value='"+li.getJuicioPrimeraReunion()+"' name='JUICIO-"+li.getAlumno().getCi()+"'/></td>";
            }
            if(mes==12){
                html+="<td><input type='text' value='"+li.getJuicioSegundaReunion()+"' name='JUICIO-"+li.getAlumno().getCi()+"'/></td>";
            }
            
            html+="</tr>";
        }
        if(mes==11){
            html+="<tr><td>JUICIO GRUPAL:</td><td><input type='text' value='"+l.getJuicioGrupalPrimeraReunion()+"' name='JUICIOGRUPAL'/></td></tr>";
        }
        if(mes==12){
            html+="<tr><td>JUICIO GRUPAL:</td><td><input type='text' value='"+l.getJuicioGrupalSegundaReunion()+"' name='JUICIOGRUPAL'/></td></tr>";
        }
        return html;
    }
    public double calculoPromedio(LibretaIndividual li, int mes,Libreta l){ //mensual, semestral y anual 
        if(mes<=10){
            double suma= 0;
            int sumacoef = 0;
            if(li.getNotasOrales().containsKey(mes)){
                for(Nota n:li.getNotasOrales().get(mes)){
                    suma+=n.getNota()*2;
                    sumacoef+=2;
                }
            }
            if(li.getNotasEscritos().containsKey(mes)){
                for(Nota n:li.getNotasEscritos().get(mes)){
                    suma+=n.getNota()*3;
                    sumacoef+=3;
                }
            }
            if(sumacoef>0){
                return (double)Math.round((suma/sumacoef) * 100d) / 100d;
            }
            else{
                return 1.00;
            }
        }
        else{
            if(mes==13){ 
                if(l.getMateria().isSemestral() && l.getMateria().getSemestre()==1){//PROMEDIO ANUAL materia semestral primer semestre
                    double suma = 0;
                    for(int i=3;i<=6;i++){
                        suma+=li.getPromedios().get(i).getNota();
                    }
                    return (double)Math.round((suma/4) * 100d) / 100d;
                }
                else{
                    if(l.getMateria().isSemestral() && l.getMateria().getSemestre()==2){ //PROMEDIO ANUAL materia semestral segundo semestre
                        double suma = 0;
                        for(int i=7;i<=10;i++){
                            suma+=li.getPromedios().get(i).getNota();
                        }
                        return (double)Math.round((suma/4) * 100d) / 100d;
                    }
                    else{
                        //PROMEDIO ANUAL materia anual
                        double suma = 0;
                        for(int i=3;i<=10;i++){
                            suma+=li.getPromedios().get(i).getNota();
                        }
                        return (double)Math.round((suma/8) * 100d) / 100d;
                    }
                }
            }
        }
        return 1.0;
    }
    private String promedioMesHTML(LibretaIndividual li,int mes){
        String html="<td>";
        if(li.getPromedios().containsKey(mes)){
            html+=li.getPromedios().get(mes).getNota();
        }
        html+="</td>";
        return html;
    }
    private String notasMesHTML(LibretaIndividual li,int mes){
        String html="<td>";
        if(li.getNotasOrales().containsKey(mes) && !li.getNotasOrales().get(mes).isEmpty()){
            for(Nota n:li.getNotasOrales().get(mes)){
                html+="<b title='Fecha alta: "+n.getFecha()+"&#10;Mes correspondiente: "+mes+"&#10;Nota: "+n.getNota()+"&#10;Observaciones: "+n.getObservacion()+"'><p>"+n.getNota()+"</p></b>";
            }
        }
        html+="</td>"
                + "<td>";
        if(li.getNotasEscritos().containsKey(mes) && !li.getNotasEscritos().get(mes).isEmpty()){
            for(Nota n:li.getNotasEscritos().get(mes)){
                html+="<b title='Fecha alta: "+n.getFecha()+"&#10;Mes correspondiente: "+mes+"&#10;Nota: "+n.getNota()+"&#10;Observaciones: "+n.getObservacion()+"'><p>"+n.getNota()+"</p></b>";
            }
        }
        html+="</td>";
        return html;
    }
    public boolean modificarLibreta(Libreta l, int ciProfesor, String salon) {
        ManejadorBedeliaBD mb= new ManejadorBedeliaBD();
        if(mb.modificarLibreta(l.getId(),ciProfesor,salon)){
            if(l.getProfesor().getCi()!=ciProfesor){
                ManejadorProfesores mp = ManejadorProfesores.getInstance();
                Profesor profNuevo = mp.getProfesor(ciProfesor);
                l.getProfesor().getLibretas().remove(l.getId());
                l.setProfesor(profNuevo);
                profNuevo.getLibretas().put(l.getId(), l);
            }
            l.setSalon(salon);
            return true;
        }
        return false;
    }
     public boolean guardarCerrarPromedio(Libreta l,LinkedList<RecordPromedios> lrp, int mes, boolean cerrado) {
        ManejadorBedeliaBD mb= new ManejadorBedeliaBD();
        if(mb.guardarCerrarPromedio(l,lrp,mes,cerrado)){
            for(RecordPromedios rp:lrp){
                if(mes==11){
                    rp.li.setJuicioPrimeraReunion(rp.juicio);
                }
                else{
                    if(mes==12){
                        rp.li.setJuicioSegundaReunion(rp.juicio);
                    }
                }
                if(rp.li.getPromedios().containsKey(mes)){
                    rp.li.getPromedios().get(mes).setNota(rp.valorPromedio);
                }
                else{
                    rp.li.getPromedios().put(mes, new Promedio(rp.valorPromedio, mes));
                }
            }
            if(cerrado){
                if(!l.getMesesCerrados().containsKey(mes)){
                    l.getMesesCerrados().put(mes, true);
                }
            }
            return true;
        }
        return false;
    }
//    public boolean guardarPromedio(LibretaIndividual li, double valorPromedio, int mes, String juicio) {
//        ManejadorBedeliaBD mb= new ManejadorBedeliaBD();
//        boolean ok=false;
//        if(li.getPromedios().containsKey(mes)){
//            ok= mb.modificarPromedio(li,valorPromedio,mes,juicio);
//        }
//        else{
//            ok= mb.guardarPromedio(li,valorPromedio,mes,juicio);
//        }
//        if(ok){
//            if(mes==11){
//                li.setJuicioPrimeraReunion(juicio);
//            }
//            else{
//                if(mes==12){
//                    li.setJuicioSegundaReunion(juicio);
//                }
//            }
//            if(li.getPromedios().containsKey(mes)){
//                li.getPromedios().get(mes).setNota(valorPromedio);
//            }
//            else{
//                li.getPromedios().put(mes, new Promedio(valorPromedio, mes));
//            }
//        }
//        return ok;
//    }
//    public boolean cerrarPromedio(Libreta libreta,int mes){
//        ManejadorBedeliaBD mb= new ManejadorBedeliaBD();
//        if(!libreta.getMesesCerrados().containsKey(mes)){
//            if(mb.cerrarPromedio(libreta, mes)){
//                libreta.getMesesCerrados().put(mes, true);
//                return true;
//            }
//            return false;
//        }
//        return true;
//    }
     static public String obtenerCategoria(boolean especifica,double promedio){
         if(especifica){
            if(promedio<4){ //1 2 y 3
                return "D";
            }
            else if(promedio<7){ // 4 5 y 6
                    return "C";
            }
            else if(promedio<8){ //7
                return "B";
            }
            else{
                return "A";
            }
        }
        else{
            if(promedio<3){ //1 y 2
                return "D";
            }
            else if(promedio<5){ //3 y 4
                    return "C";

            }
            else if(promedio<6){ //5
                    return "B";
            }
            else{
                return "A";
            }
        }
     }
    public void guardarJuicioGrupal(String juicio, int mes,Libreta l) {
        ManejadorBedeliaBD mb= new ManejadorBedeliaBD();
        if(mb.guardarJuicioGrupal(juicio,mes,l)){
            if (mes==11){
                l.setJuicioGrupalPrimeraReunion(juicio);
            }
            else{
                if(mes==12){
                    l.setJuicioGrupalSegundaReunion(juicio);
                }
            }
        }
    }

   
    private static class ManejadorBedeliaHolder {

        private static final ManejadorBedelia INSTANCE = new ManejadorBedelia();
    }
    public HashMap<Integer, CursoBedelia> getCursos() {
        return cursos;
    }
    public CursoBedelia getCurso(int id) {
        return cursos.get(id);
    }
    public HashMap<Integer, Materia> getMaterias() {
        return materias;
    }
    public synchronized boolean agregarMateria(Materia m,int idCurso){
        ManejadorBedeliaBD mb= new ManejadorBedeliaBD();
        if(mb.agregarMateria(m)){
            materias.put(m.getId(), m);
            if(idCurso!=0){
                asociarMateriaCurso(m.getId(), idCurso);
            }
            return true;
        };
        return false;
    }
    public synchronized boolean asociarMateriaCurso(int idMateria, int idCurso){
        ManejadorBedeliaBD mb= new ManejadorBedeliaBD();
        if(mb.asociarMateriaCurso(idMateria,idCurso)){
            cursos.get(idCurso).getMaterias().put(idMateria, materias.get(idMateria));
            return true;
        };
        return false;
    }
    public synchronized boolean desasociarMateriaCurso(int idMateria,int idCurso){
        ManejadorBedeliaBD mb= new ManejadorBedeliaBD();
        if(cursoMateriaConLibretasAsociadas(idCurso, idMateria)){
            return false; //no se puede desasociar una materia de un curso que tenga libretas asociadas.
        }
        if(mb.desasociarMateriaCurso(idMateria,idCurso)){
            cursos.get(idCurso).getMaterias().remove(idMateria);
            return true;
        };
        return false;
    }
    public synchronized boolean modificarMateria(Materia m) {
        ManejadorBedeliaBD mb= new ManejadorBedeliaBD();
        if(mb.modificarMateria(m)){
            Materia mat = materias.get(m.getId());
            mat.setCoeficiente(m.getCoeficiente());
            mat.setNombre(m.getNombre());
            mat.setSecundaria(m.isSecundaria());
            mat.setSemestre(m.getSemestre());
            mat.setSemestral(m.isSemestral());
            mat.setActivo(m.isActivo());
            mat.setEspecifica(m.isEspecifica());
            return true;
        }
        return false;//error BD
    }
    public synchronized boolean eliminarMateria(int idMateria){
        ManejadorBedeliaBD mb= new ManejadorBedeliaBD();
        if(materiaConLibretasAsociadas(idMateria)){
            return false; //no se puede eliminar una materia que tenga libretas asociadas.
        }
        if(eliminarMateriaDeCursos(idMateria) && mb.eliminarMateria(idMateria)){
            materias.remove(idMateria);
            return true;
        };
        return false;
    }
    public synchronized boolean eliminarMateriaDeCursos(int idMateria){
        Iterator itCursos = cursos.values().iterator();
        ManejadorBedeliaBD mb= new ManejadorBedeliaBD();
        if(!mb.eliminarMateriaDeCursos(idMateria)){
            return false;
        }
        while(itCursos.hasNext()){
            ((CursoBedelia)itCursos.next()).getMaterias().remove(idMateria);
        }
        return true;
    }
    public synchronized boolean agregarCurso(CursoBedelia cb){
        ManejadorBedeliaBD mb= new ManejadorBedeliaBD();
        if(mb.agregarCurso(cb)){
            cursos.put(cb.getId(), cb);
            return true;
        };
        return false;
    }
    private int sePuedeModificarEliminarCurso(int id){
        CursoBedelia c = cursos.get(id);
        if(c.getMaterias().size()>0){
            return 1; //no se puede eliminar un curso que tenga materias asociadas
        }
        if(this.cursoConLibretasAsociadas(id)){
            return 2; //no se puede eliminar un curso que tenga asociadas libretas
        }
        return 0; //se puede eliminar
    }
    public synchronized int modificarCurso(CursoBedelia cb){
        ManejadorBedeliaBD mb= new ManejadorBedeliaBD();
        int i = sePuedeModificarEliminarCurso(cb.getId());
        if(i!=0){
            return i; //no se puede modificar un curso que tenga asociadas materias o libretas 
        }
        if(mb.modificarCurso(cb)){
            CursoBedelia c = cursos.get(cb.getId());
            c.setAnioCurricular(cb.getAnioCurricular());
            c.setJefatura(cb.isJefatura());
            c.setNombre(cb.getNombre());
            c.setActivo(cb.isActivo());
            return 0;
        }
        return -1;//error BD
    }
    public synchronized int eliminarCurso(int id){
        ManejadorBedeliaBD mb= new ManejadorBedeliaBD();
        CursoBedelia c = cursos.get(id);
        int i = sePuedeModificarEliminarCurso(id);
        if(i!=0){
            return i; //no se puede eliminar un curso que tenga asociadas materias o libretas 
        }
        if(mb.eliminarCurso(c)){
            cursos.remove(id);
            return 0;
        }
        return -1; //error BD
    }
    public boolean materiaConLibretasAsociadas(int idMateria){
        Libreta l;
        HashMap<Integer,Libreta> lp;
        Iterator it =libretas.values().iterator();
        Iterator it1;
        while(it.hasNext()){
            lp=(HashMap<Integer,Libreta>)it.next();
            it1=lp.values().iterator();
            while(it1.hasNext()){
                l=(Libreta)it1.next();
                if(l.getMateria().getId()==idMateria){
                    return true;
                }
            }
        }
        return false;
    }
    //retorna true si hay alguna libreta de la materia idMateria asociada al curso idCurso
    public boolean cursoMateriaConLibretasAsociadas(int idCurso, int idMateria){
        Libreta l;
        HashMap<Integer,Libreta> lp;
        Iterator it =libretas.values().iterator();
        Iterator it1;
        while(it.hasNext()){
            lp=(HashMap<Integer,Libreta>)it.next();
            it1=lp.values().iterator();
            while(it1.hasNext()){
                l=(Libreta)it1.next();
                if(l.getGrupo().getCusoBedelia().getId()==idCurso && l.getMateria().getId()==idMateria){
                    return true;
                }
            }
        }
        return false;
    }
    //retorna true si hay alguna libreta asociada al curso idCurso
    public boolean cursoConLibretasAsociadas(int idCurso){
        Libreta l;
        Iterator it =libretas.values().iterator();
        while(it.hasNext()){
            l=(Libreta)it.next();
            if(l.getGrupo().getCusoBedelia().getId()==idCurso){
                return true;
            }
        }
        return false;
    }
    public HashMap<Integer, Libreta> getLibretas() {
        return libretas;
    }
    public HashMap<Integer, CursoBedelia> getCursosMateria(int idMateria){
        HashMap<Integer, CursoBedelia> hcb= new HashMap<>();
        for(CursoBedelia cb: cursos.values()){
            if(cb.getMaterias().containsKey(idMateria)){
                hcb.put(cb.getId(), cb);
            }
        }
        return hcb;
    }
    public synchronized boolean agregarGrupoCurso(int idCurso, int anio, String nombre){
        ManejadorBedeliaBD mb= new ManejadorBedeliaBD();
        if(mb.agregarGrupoCurso(idCurso,anio,nombre)){
            CursoBedelia c = cursos.get(idCurso);
            Grupo g = new Grupo(c, anio, nombre, new HashMap<>());
            c.agregarGrupoEnOrden(g);
            return true;
        }
        return false;
    }
    public void ImprmirFaltasxDia(PrintWriter out,String fecha){
        HashMap<Integer,HashMap<Integer,HashMap<Integer,LinkedList<Falta>>>> faltasxMateriaxAlumnoxGrado = new HashMap<>();
        HashMap<Integer, HashMap<Integer, LinkedList<Falta>>> faltasxMateriaxAlumno;
        HashMap<Integer,HashMap<Integer,Materia>> materiasEncontradas= new HashMap<>();
        for(Libreta l:libretas.values()){
            for(LibretaIndividual li:l.getLibretasIndividuales().values()){
                for(Falta f:li.getFaltas().values()){
                    if(f.getFecha().equals(fecha)){
                        if(!faltasxMateriaxAlumnoxGrado.containsKey(li.getAlumno().getGrado().getId())){
                            faltasxMateriaxAlumnoxGrado.put(li.getAlumno().getGrado().getId(),new HashMap<>());
                        }
                        faltasxMateriaxAlumno = faltasxMateriaxAlumnoxGrado.get(li.getAlumno().getGrado().getId());
                        if(!faltasxMateriaxAlumno.containsKey(li.getAlumno().getCi())){
                            faltasxMateriaxAlumno.put(li.getAlumno().getCi(), new HashMap<>());
                        }
                        if(!faltasxMateriaxAlumno.get(li.getAlumno().getCi()).containsKey(l.getMateria().getId())){
                            faltasxMateriaxAlumno.get(li.getAlumno().getCi()).put(l.getMateria().getId(), new LinkedList<>());
                        }
                        faltasxMateriaxAlumno.get(li.getAlumno().getCi()).get(l.getMateria().getId()).add(f);
                        if(!materiasEncontradas.containsKey(li.getAlumno().getGrado().getId())){
                            materiasEncontradas.put(li.getAlumno().getGrado().getId(),new HashMap<>());
                        }
                        materiasEncontradas.get(li.getAlumno().getGrado().getId()).put(l.getMateria().getId(),l.getMateria());                                

                    }
                    else{
                        if(f.getFecha().compareTo(fecha)>0){
                            break;
                        }
                    }
                }
            }
        }
        
        Calendar ahoraCal = java.util.Calendar.getInstance();
        int anio= ahoraCal.get(Calendar.YEAR);
        int mes=ahoraCal.get(Calendar.MONTH);
        int dia=ahoraCal.get(Calendar.DATE);
        out.print("<style>"
                + ".contenido {"
                +   "border: 1px solid #000;"
                +   "border-spacing: 0px;"
                +   "text-align: center;"
                +   "font-family: arial;"
                + "}"
                + ".contenido tr, .contenido tr td{"
                +       "border: 1px solid #000;\n" +
                        "border-collapse: collapse;"
                +       "padding: 0px;"
                + "}"
                + "</style>");
    out.print("<table style=\"width: 100%; margin:auto; font-family: arial;\">");
    out.print("     <tr>\n" +
        "                <td colspan=\"3\">\n" +
        "                    <p align=\"left\">ESCUELA MILITAR</p>\n" +
        "                </td>\n" +
        "                <td colspan=\"3\">\n" +
        "                     <p align=\"right\">JEFATURA DE ESTUDIOS</p>\n" +
        "                </td>\n" +
        "            </tr>\n");
    out.print("     <tr>\n" +
        "                <td colspan=\"3\">\n" +
        "                </td>\n" +
        "                <td colspan=\"3\">\n" +
        "                     <p align=\"right\">TOLEDO, "+dia+" DE ");
    switch(mes){
        case 0: out.print("ENERO");break;
        case 1: out.print("FEBRERO");break;
        case 2: out.print("MARZO");break;
        case 3: out.print("ABRIL");break;
        case 4: out.print("MAYO");break;
        case 5: out.print("JUNIO");break;
        case 6: out.print("JULIO");break;
        case 7: out.print("AGOSTO");break;
        case 8: out.print("SETIEMBRE");break;
        case 9: out.print("OCTUBRE");break;
        case 10: out.print("NOVIEMBRE");break;
        case 11: out.print("DICIEMBRE");break;
                                
    }
    out.print(" DE "+anio+"</p>\n" +
        "                </td>\n" +
        "            </tr>\n");
out.print("          <tr>\n" +
        "                <td colspan=\"6\" style=\"text-align: center;\">\n");
            out.print("       <h3>FALTAS A CLASE</h3>\n"+
        "                </td>\n" +
        "            </tr>\n"
        + "</table>");        
       
        ManejadorCodigos mc= ManejadorCodigos.getInstance();
        ManejadorPersonal mp = ManejadorPersonal.getInstance();
       for(Integer gradoID:faltasxMateriaxAlumnoxGrado.keySet()){
           out.print("<h4 style='font-family:arial;'>");
           switch(gradoID){
                case 22: out.print("<u>ASPIRANTES</u>");break;
                case 21: out.print("<u>CADETES DE 1ER. A&Ntilde;O</u>");break;
                case 20: out.print("<u>CADETES DE 2DO. A&Ntilde;O</u>");break;
                case 19: out.print("<u>CADETES DE 3ER. A&Ntilde;O</u>");break;
                case 18: out.print("<u>CABOS HONORARIOS</u>");break;
                case 17: out.print("<u>SARGENTOS HONORARIOS</u>");break;
                case 16: out.print("<u>SARGENTOS 1ROs. HONORARIOS</u>");break;
           }
            out.print( "</h4>"
                    + "<table width='100%' class=\"contenido\" >"
                   + "        <tr>"
                   + "          <td><b> NOMBRE</b> </td>");
           for(Materia materia:materiasEncontradas.get(gradoID).values()){
                    out.print("  <td><b>");
                    out.print(materia.getNombre());
                    out.print("  </b></td>");
           }
           out.print("           <td><b>FECHA</b></td>"
                   + "<td><b>ANOTACIONES</b></td>"
                   + "      </tr>");
           Cadete c;
           for(Integer ciAlumno: faltasxMateriaxAlumnoxGrado.get(gradoID).keySet()){
               c=mp.getCadete(ciAlumno);
               out.print("<tr>"
                       + "    <td>"+c.getPrimerNombre()+" "+c.getPrimerApellido());
               if(c.getArma()!=null){
                   //Apoyo
                   out.print("("+c.getArma().getAbreviacion()+")");
               }
                   
               out.print(       "</td>");
               for(Integer materiaID:materiasEncontradas.get(gradoID).keySet()){
                   out.print("  <td>");
                   boolean primero=true;
                   for(Falta f: faltasxMateriaxAlumnoxGrado.get(gradoID).get(ciAlumno).get(materiaID)){
                       if(primero){
                        out.print(f.getCanthoras()+"("+f.getCodigoMotivo()+")");
                        primero=false;
                       }else{
                        out.print(" - "+f.getCanthoras()+"("+f.getCodigoMotivo()+")");   
                       }
                   }
                   out.print("  </td>");
               }
               out.print("      <td>"+fecha+"</td>"
                       + "<td></td>");
               out.print("  </tr>");
           }
           out.print( "</table>");
           
       }     
            
           out.print( "<table class='contenido' style='font-family:arial;margin-top: 30px;' align='center'>"
                   +    "<tr>"
                   + "      <td>"
                   + "C&Oacute;DIGO:"
                   + "      </td>"
                   + "      <td>"
                   + "DESCRIPCI&Oacute;N:"
                   + "      </td>"
                   +   "</tr>"
                   +    "<tr>"
                   + "      <td>"
                   + "F1:"
                   + "      </td>"
                   + "      <td>"
                   + "Guardia"
                   + "      </td>"
                   +   "</tr>"
                   +    "<tr>"
                    + "      <td>"
                   + "F2:"
                   + "      </td>"
                   + "      <td>"
                   + "Enfermer&iacute;a"
                   + "      </td>"
                   +   "</tr>"
                   +   "<tr>"
                    + "      <td>"
                   + "F3:"
                   + "      </td>"
                   + "      <td>"
                   + "Comisi&oacute;n"
                   + "      </td>"
                   +   "</tr>"
                   +   "<tr>"
                    + "      <td>"
                   + "F4:"
                   + "      </td>"
                   + "      <td>"
                   + "Internado en H.C.FF.AA."
                   + "      </td>"
                   +   "</tr>"
                   +   "<tr>"
                    + "      <td>"
                   + "F5:"
                   + "      </td>"
                   + "      <td>"
                   + "Eximido"
                   + "      </td>"
                   +   "</tr>"
                   +   "<tr>"
                    + "      <td>"
                   + "F6:"
                   + "      </td>"
                   + "      <td>"
                   + "Consejero Acad&eacute;mico"
                   + "      </td>"
                   +   "</tr>"
                   + "</table>");
           
           out.print("<table style='font-family:arial;margin-top: 30px;' align='right'>"
                   + "<tr>"
                   + "<td>"
                   + "</td>"
                   + "<td>"
                   + "<input type='text' style=\"text-align:left;border: 0;\"/>"
                   + "</td>"
                   + "</tr>"
                   + "<tr>"
                        + "<td style='text-align:right;'>"
                        + "<image src='images/selloJE.png' width='50%'/>"
                        + "</td>"
                        + "<td>"
                         + "<table>"
                          + "    <tr>"
                                   + "<td style='text-align:left;'>"
                                   +"El Jefe de Estudios de la Escuela Militar"
                                   + "</td>"
                        + "      </tr>"
                        + "      <tr>"
                                   + "<td style='text-align:left;'>"
                                   + "<input type='text' style=\"text-align:left;border: 0;\"/>"
                                   + "</td>"
                        + "      </tr>"
                        + "      <tr>"
                                   + "<td style='text-align:center;'>"
                                   + "<input type='text' style=\"text-align:center; border: 0; border-top:solid 1px #000;\"/>"
                                   + "</td>"
                        + "      </tr>"
                         + "</table>"
                        + "</td>"
                   + "</tr>"
                   + "</table>");     
            
            
    }
        
    
}
