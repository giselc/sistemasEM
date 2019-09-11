/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes.Bedelia;

import Classes.Cadete;
import Classes.FaltaSancion;
import Manejadores.ManejadorBedelia;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
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
    private double promedioSegundaReunion;//solo para caso aspitantes
    private double notaFinal; //calculado en base al promedioAnual y nota de Examen
    HashMap<Integer, Falta> faltas;
    HashMap<Integer,HashMap<Integer,LinkedList<FaltaSancion>>> grillaFaltasSanciones; //mes*dia* listaFaltas del dia
    HashMap<Integer, LinkedList <Nota>> notasOrales; //mes*listaNotas del mes
    HashMap<Integer, LinkedList <Nota>> notasEscritos; //mes*listaNotas del mes
    HashMap<Integer,Promedio> promedios; // key es el mes del promedio
    HashMap<Integer,Sancion> sanciones;

    public LibretaIndividual(int idLibreta, Cadete alumno, HashMap<Integer, Falta> faltas, HashMap<Integer,HashMap<Integer,LinkedList<FaltaSancion>>> grillaFaltasSanciones, HashMap<Integer, LinkedList<Nota>> notasOrales, HashMap<Integer, LinkedList<Nota>> notasEscritos, HashMap<Integer, Promedio> promedios, HashMap<Integer, Sancion> sanciones, double promedioAnual, double notaFinal, boolean activo,double promedioPrimeraReunion,double promedioSegundaReunion) {
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
        Libreta l = mb.getLibreta(idLibreta);
        if (!l.getMateria().isSemestral()||(l.getMateria().isSemestral()&& l.getMateria().getSemestre()==1)){ 
            imprimirGrillaNotasMes(out, 3, l);
            imprimirGrillaNotasMes(out, 4, l);
            imprimirGrillaNotasMes(out, 5, l);
            imprimirGrillaNotasMes(out, 6, l);
        }
        if (!l.getMateria().isSemestral()||(l.getMateria().isSemestral()&& l.getMateria().getSemestre()==2)){
            imprimirGrillaNotasMes(out, 7, l);
            imprimirGrillaNotasMes(out, 8, l);
            imprimirGrillaNotasMes(out, 9, l);
            imprimirGrillaNotasMes(out, 10, l);
        }
    }
    private void imprimirGrillaNotasMes(JspWriter out, int mes,Libreta l){
        try{
            out.print("<td id="+alumno.getCi()+"-O-"+mes+">");
                if(notasOrales.containsKey(mes) && !notasOrales.get(mes).isEmpty()){
                    for(Nota n:notasOrales.get(mes)){
                        if(!l.getMesesCerrados().containsKey(mes)){
                            out.print("<b title='Fecha alta: "+n.getFecha()+"&#10;Mes correspondiente: 3&#10;Nota: "+n.getNota()+"&#10;Observaciones: "+n.getObservacion()+"'><p id='NOTA-"+n.getId()+"' onMouseleave=\"eliminarNota(false,"+n.getId()+",'"+n.getNota()+"','"+l.getId()+"','"+alumno.getCi()+"','"+l.getProfesor().getCi()+"');\" onMouseEnter=\"eliminar(true,"+n.getId()+",'"+n.getNota()+"','"+l.getId()+"','"+alumno.getCi()+"','"+l.getProfesor().getCi()+"');\">"+n.getNota()+"</p></b>");
                        }
                        else{
                            out.print("<b title='Fecha alta: "+n.getFecha()+"&#10;Mes correspondiente: 3&#10;Nota: "+n.getNota()+"&#10;Observaciones: "+n.getObservacion()+"'><p id='NOTA-"+n.getId()+"' >"+n.getNota()+"</p></b>");
                        }
                    }
                }
            out.print("</td>"
                    + "<td id="+alumno.getCi()+"-E-"+mes+">");
                if(notasEscritos.containsKey(mes) && !notasEscritos.get(mes).isEmpty()){
                    for(Nota n:notasEscritos.get(mes)){
                        if(!l.getMesesCerrados().containsKey(mes)){
                            out.print("<b title='Fecha alta: "+n.getFecha()+"&#10;Mes correspondiente: 3&#10;Nota: "+n.getNota()+"&#10;Observaciones: "+n.getObservacion()+"'><p id='NOTA-"+n.getId()+"' onMouseleave=\"eliminarNota(false,"+n.getId()+",'"+n.getNota()+"','"+l.getId()+"','"+alumno.getCi()+"','"+l.getProfesor().getCi()+"');\" onMouseEnter=\"eliminar(true,"+n.getId()+",'"+n.getNota()+"','"+l.getId()+"','"+alumno.getCi()+"','"+l.getProfesor().getCi()+"');\">"+n.getNota()+"</p></b>");
                        }
                        else{
                            out.print("<b title='Fecha alta: "+n.getFecha()+"&#10;Mes correspondiente: 3&#10;Nota: "+n.getNota()+"&#10;Observaciones: "+n.getObservacion()+"'><p id='NOTA-"+n.getId()+"' >"+n.getNota()+"</p></b>");
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
    
}
