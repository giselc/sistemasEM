/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes.Bedelia;

import Classes.Cadete;
import Classes.FaltaSancion;
import Manejadores.ManejadorBedelia;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspWriter;

/**
 *
 * @author Gisel
 */
public class LibretaIndividual {
    private int idLibreta;
    private Cadete alumno;
    private boolean activo;
    private double promedioAnual;
    private double promedioPrimeraReunion;//solo para caso aspirantes
    private String juicioPrimeraReunion;
    private Nota primerParcial;//solo para caso aspirantes
    private double promedioSegundaReunion;//solo para caso aspitantes
    private String juicioSegundaReunion;
    private Nota segundoParcial;//solo para caso aspirantes
    private double notaFinal; //calculado en base al promedioAnual y nota de Examen
    HashMap<Integer, Falta> faltas;
    HashMap<Integer,HashMap<Integer,LinkedList<FaltaSancion>>> grillaFaltasSanciones; //mes*dia* listaFaltas del dia
    HashMap<Integer, LinkedList <Nota>> notasOrales; //mes*listaNotas del mes
    HashMap<Integer, LinkedList <Nota>> notasEscritos; //mes*listaNotas del mes
    HashMap<Integer,Promedio> promedios; // key es el mes del promedio
    HashMap<Integer,Sancion> sanciones;

    public LibretaIndividual(int idLibreta, Cadete alumno, HashMap<Integer, Falta> faltas, HashMap<Integer,HashMap<Integer,LinkedList<FaltaSancion>>> grillaFaltasSanciones, HashMap<Integer, LinkedList<Nota>> notasOrales, HashMap<Integer, LinkedList<Nota>> notasEscritos, HashMap<Integer, Promedio> promedios, HashMap<Integer, Sancion> sanciones, double promedioAnual, double notaFinal, boolean activo,double promedioPrimeraReunion,double promedioSegundaReunion, Nota primerParcial,Nota segundoParcial,String juicioPrimeraReunion, String juicioSegundaReunion) {
        this.idLibreta = idLibreta;
        this.alumno = alumno;
        this.faltas = faltas;
        this.notasOrales = notasOrales;
        this.notasEscritos=notasEscritos;
        this.promedios = promedios;
        this.sanciones = sanciones;
        this.promedioAnual = promedioAnual;
        this.notaFinal = notaFinal;
        this.activo= activo;
        this.grillaFaltasSanciones = grillaFaltasSanciones;
        this.promedioPrimeraReunion=promedioPrimeraReunion;
        this.promedioSegundaReunion=promedioSegundaReunion;
        this.primerParcial=primerParcial;
        this.segundoParcial = segundoParcial;
        this.juicioPrimeraReunion = juicioPrimeraReunion;
        this.juicioSegundaReunion = juicioSegundaReunion;
                
    }
    public LibretaIndividual(int idLibreta, Cadete alumno) {
        this.idLibreta = idLibreta;
        this.alumno = alumno;
        this.faltas = new HashMap<>();
        this.grillaFaltasSanciones = new HashMap<>();
        this.notasOrales = new HashMap<>();
        this.notasEscritos = new HashMap<>();
        this.promedios = new HashMap<>();
        this.sanciones = new HashMap<>();
        this.promedioAnual = 0;
        this.notaFinal = 0;
        this.activo=true;
    }

    public boolean isActivo() {
        return activo;
    }

    public String getJuicioPrimeraReunion() {
        return juicioPrimeraReunion;
    }

    public void setJuicioPrimeraReunion(String juicioPrimeraReunion) {
        this.juicioPrimeraReunion = juicioPrimeraReunion;
    }

    public String getJuicioSegundaReunion() {
        return juicioSegundaReunion;
    }

    public void setJuicioSegundaReunion(String juicioSegundaReunion) {
        this.juicioSegundaReunion = juicioSegundaReunion;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
    public int getIdLibreta() {
        return idLibreta;
    }

    public void setIdLibreta(int idLibreta) {
        this.idLibreta = idLibreta;
    }

    public double getPromedioAnual() {
        return promedioAnual;
    }

    public void setPromedioAnual(double promedioAnual) {
        this.promedioAnual = promedioAnual;
    }

    public double getNotaFinal() {
        return notaFinal;
    }

    public void setNotaFinal(double notaFinal) {
        this.notaFinal = notaFinal;
    }
    
    public int getLibreta() {
        return idLibreta;
    }

    public void setLibreta(int idLibreta) {
        this.idLibreta = idLibreta;
    }

    public Cadete getAlumno() {
        return alumno;
    }

    public void setAlumno(Cadete alumno) {
        this.alumno = alumno;
    }

    public HashMap<Integer, Falta> getFaltas() {
        return faltas;
    }

    public void setFaltas(HashMap<Integer, Falta> faltas) {
        this.faltas = faltas;
    }

    public HashMap<Integer, LinkedList<Nota>> getNotasEscritos() {
        return notasEscritos;
    }

    public void setNotasEscritos(HashMap<Integer, LinkedList<Nota>> notasEscritos) {
        this.notasEscritos = notasEscritos;
    }

    public HashMap<Integer, LinkedList<Nota>> getNotasOrales() {
        return notasOrales;
    }

    public void setNotasOrales(HashMap<Integer, LinkedList<Nota>> notas) {
        this.notasOrales = notas;
    }

    public HashMap<Integer, Promedio> getPromedios() {
        return promedios;
    }

    public void setPromedios(HashMap<Integer, Promedio> promedios) {
        this.promedios = promedios;
    }

    public HashMap<Integer, Sancion> getSanciones() {
        return sanciones;
    }

    public void setSanciones(HashMap<Integer, Sancion> sanciones) {
        this.sanciones = sanciones;
    }

    public HashMap<Integer,HashMap<Integer,LinkedList<FaltaSancion>>> getGrillaFaltasSanciones() {
        return grillaFaltasSanciones;
    }

    public double getPromedioPrimeraReunion() {
        return promedioPrimeraReunion;
    }

    public double getPromedioSegundaReunion() {
        return promedioSegundaReunion;
    }

    public void setPromedioPrimeraReunion(double promedioPrimeraReunion) {
        this.promedioPrimeraReunion = promedioPrimeraReunion;
    }

    public void setPromedioSegundaReunion(double promedioSegundaReunion) {
        this.promedioSegundaReunion = promedioSegundaReunion;
    }

    public void imprimirGrillaNotas(JspWriter out){
        Manejadores.ManejadorBedelia mb= ManejadorBedelia.getInstance();
        Libreta l = mb.getLibretas().get(idLibreta);
        if (!l.getMateria().isSemestral()||(l.getMateria().isSemestral()&& l.getMateria().getSemestre()==1)){ 
            imprimirGrillaNotasMes(out, 3, l);
            imprimirGrillaNotasMes(out, 4, l);
            imprimirGrillaNotasMes(out, 5, l);
            imprimirGrillaNotasMes(out, 6, l);
            if(l.getMateria().isSecundaria()){
                try {
                    if(primerParcial==null){
                        out.print("<td id="+alumno.getCi()+"-PP></td>");
                    }
                    else{
                    out.print("<td id="+alumno.getCi()+"-PP><b title='PRIMER PARCIAL&#10;Fecha alta: "+primerParcial.getFecha()+"&#10;Nota: "+primerParcial.getNota()+"&#10;Observaciones: "+primerParcial.getObservacion()+"'><p id='NOTA-"+primerParcial.getId()+"' onMouseleave=\"eliminarNota(false,"+primerParcial.getId()+",'"+primerParcial.getNota()+"','"+l.getId()+"','"+alumno.getCi()+"','"+l.getProfesor().getCi()+"',3,0);\" onMouseEnter=\"eliminarNota(true,"+primerParcial.getId()+",'"+primerParcial.getNota()+"','"+l.getId()+"','"+alumno.getCi()+"','"+l.getProfesor().getCi()+"',3,0);\">"+primerParcial.getNota()+"</p></b></td>");
                    }
                   if(promedioPrimeraReunion==0){
                        out.print("<td></td>");
                    }
                    else{
                    out.print("<td><b title='JUICIO:"+juicioPrimeraReunion+"'>"+this.promedioPrimeraReunion+"</b></td>");
                    }
                } catch (IOException ex) {
                    Logger.getLogger(LibretaIndividual.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        if (!l.getMateria().isSemestral()||(l.getMateria().isSemestral()&& l.getMateria().getSemestre()==2)){
            imprimirGrillaNotasMes(out, 7, l);
            imprimirGrillaNotasMes(out, 8, l);
            imprimirGrillaNotasMes(out, 9, l);
            imprimirGrillaNotasMes(out, 10, l);
            if(l.getMateria().isSecundaria()){
                try {
                    if(segundoParcial==null){
                        out.print("<td id="+alumno.getCi()+"-SP></td>");
                    }
                    else{
                    out.print("<td id="+alumno.getCi()+"-SP><b title='SEGUNDO PARCIAL&#10;Fecha alta: "+segundoParcial.getFecha()+"&#10;Nota: "+segundoParcial.getNota()+"&#10;Observaciones: "+segundoParcial.getObservacion()+"'><p id='NOTA-"+segundoParcial.getId()+"' onMouseleave=\"eliminarNota(false,"+segundoParcial.getId()+",'"+segundoParcial.getNota()+"','"+l.getId()+"','"+alumno.getCi()+"','"+l.getProfesor().getCi()+"',4,0);\" onMouseEnter=\"eliminarNota(true,"+segundoParcial.getId()+",'"+segundoParcial.getNota()+"','"+l.getId()+"','"+alumno.getCi()+"','"+l.getProfesor().getCi()+"',4,0);\">"+segundoParcial.getNota()+"</p></b></td>");
                    }
                    if(promedioSegundaReunion==0){
                        out.print("<td></td>");
                    }
                    else{
                        out.print("<td><b title='JUICIO:"+juicioSegundaReunion+"'>"+this.promedioSegundaReunion+"</b></td>");
                    }
                } catch (IOException ex) {
                    Logger.getLogger(LibretaIndividual.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        if(!l.getMateria().isSecundaria()){
            try {
                if(this.promedioAnual!=0){
                    out.print("<td><b>"+this.promedioAnual+"</b></td>");
                }
                else{
                    out.print("<td></td>");
                }
            } catch (IOException ex) {
                Logger.getLogger(LibretaIndividual.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    private void imprimirGrillaNotasMes(JspWriter out, int mes,Libreta l){
        try{
            out.print("<td id="+alumno.getCi()+"-O-"+mes+">");
                if(notasOrales.containsKey(mes) && !notasOrales.get(mes).isEmpty()){
                    for(Nota n:notasOrales.get(mes)){
                        if(!l.getMesesCerrados().containsKey(mes)){
                            out.print("<b title='Fecha alta: "+n.getFecha()+"&#10;Mes correspondiente: "+mes+"&#10;Nota: "+n.getNota()+"&#10;Observaciones: "+n.getObservacion()+"'><p id='NOTA-"+n.getId()+"' onMouseleave=\"eliminarNota(false,"+n.getId()+",'"+n.getNota()+"','"+l.getId()+"','"+alumno.getCi()+"','"+l.getProfesor().getCi()+"',2,"+mes+");\" onMouseEnter=\"eliminarNota(true,"+n.getId()+",'"+n.getNota()+"','"+l.getId()+"','"+alumno.getCi()+"','"+l.getProfesor().getCi()+"',2,"+mes+");\">"+n.getNota()+"</p></b>");
                        }
                        else{
                            out.print("<b title='Fecha alta: "+n.getFecha()+"&#10;Mes correspondiente: "+mes+"&#10;Nota: "+n.getNota()+"&#10;Observaciones: "+n.getObservacion()+"'><p id='NOTA-"+n.getId()+"' >"+n.getNota()+"</p></b>");
                        }
                    }
                }
            out.print("</td>"
                    + "<td id="+alumno.getCi()+"-E-"+mes+">");
                if(notasEscritos.containsKey(mes) && !notasEscritos.get(mes).isEmpty()){
                    for(Nota n:notasEscritos.get(mes)){
                        if(!l.getMesesCerrados().containsKey(mes)){
                            out.print("<b title='Fecha alta: "+n.getFecha()+"&#10;Mes correspondiente: "+mes+"&#10;Nota: "+n.getNota()+"&#10;Observaciones: "+n.getObservacion()+"'><p id='NOTA-"+n.getId()+"' onMouseleave=\"eliminarNota(false,"+n.getId()+",'"+n.getNota()+"','"+l.getId()+"','"+alumno.getCi()+"','"+l.getProfesor().getCi()+"',1,"+mes+");\" onMouseEnter=\"eliminarNota(true,"+n.getId()+",'"+n.getNota()+"','"+l.getId()+"','"+alumno.getCi()+"','"+l.getProfesor().getCi()+"',1,"+mes+");\">"+n.getNota()+"</p></b>");
                        }
                        else{
                            out.print("<b title='Fecha alta: "+n.getFecha()+"&#10;Mes correspondiente: "+mes+"&#10;Nota: "+n.getNota()+"&#10;Observaciones: "+n.getObservacion()+"'><p id='NOTA-"+n.getId()+"' >"+n.getNota()+"</p></b>");
                        }
                    }
                }
            out.print("</td>"
                    + "<td>");
            if(promedios.containsKey(mes)){
                out.print(promedios.get(mes));
            }
                out.print("</td>");
        }
        catch(Exception e){
            System.out.print("imprimirGrillaNotas: "+e.getMessage());
        }
        
    }

    public Nota getPrimerParcial() {
        return primerParcial;
    }

    public void setPrimerParcial(Nota primerParcial) {
        this.primerParcial = primerParcial;
    }

    public Nota getSegundoParcial() {
        return segundoParcial;
    }

    public void setSegundoParcial(Nota segundoParcial) {
        this.segundoParcial = segundoParcial;
    }

    
}
