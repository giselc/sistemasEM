/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manejadores;

import Classes.Profesor;
import Classes.RecordProfesores;
import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author Gisel
 */
public class ManejadorProfesores {
    private LinkedList<Profesor> profesores;
    private ManejadorProfesores() {
        ManejadorProfesoresBD mprof= new ManejadorProfesoresBD();
        profesores = mprof.obtenerProfesoresBD();
    }
    public static ManejadorProfesores getInstance() {
        return ManejadorProfesoresHolder.INSTANCE;
    }
    private static class ManejadorProfesoresHolder {
        private static final ManejadorProfesores INSTANCE = new ManejadorProfesores();
    }
    public Profesor getProfesor(int ci){
        Profesor p=null;
        boolean continuo=true;
        Iterator it = profesores.iterator();
        int i=0;
        while(it.hasNext() && continuo){
            i++;
            Profesor profActual = (Profesor)it.next();
            if(ci==profActual.getCi()){
                p=profActual;
                continuo=false;
            }
        }
        return p;
    }
    public synchronized boolean agregarProfesor( RecordProfesores rp ){
         ManejadorProfesoresBD mp= new ManejadorProfesoresBD();
         Profesor p= mp.agregarProfesor(rp);
         if(p!=null){
             if(profesores.isEmpty()){
                 profesores.add(p);
             }
             else{
                 int i=0;
                 boolean agregue=false;
                 Iterator it = profesores.iterator();
                 while(it.hasNext() && !agregue){
                     Profesor profActual = (Profesor)it.next();
                     if(p.getPrimerApellido().compareToIgnoreCase(profActual.getPrimerApellido())<= 0){
                         profesores.add(i, p);
                         agregue=true;
                     }
                     i++;
                 }
                 if(!agregue){
                     profesores.addLast(p);
                 }
             }
             return true;
         }
         return false;
     }
    public synchronized boolean modificarProfesor( RecordProfesores rp ){
         ManejadorProfesoresBD mp= new ManejadorProfesoresBD();
         ManejadorCodigos mc = ManejadorCodigos.getInstance();
         if(mp.modificarProfesor(rp)){
             boolean continuo=true;
             Iterator it = profesores.iterator();
             while(it.hasNext() && continuo){
                 Profesor profActual = (Profesor)it.next();
                 if(rp.ci==profActual.getCi()){
                     profActual.setGrado(mc.getGrado(rp.idGrado));
                     profActual.setArma(mc.getArma(rp.idArma));
                     profActual.setPrimerNombre(rp.primerNombre);
                     profActual.setSegundoNombre(rp.segundoNombre);
                     profActual.setPrimerApellido(rp.primerApellido);
                     profActual.setSegundoApellido(rp.segundoApellido);
                     profActual.setTelefono(rp.telefono);
                     profActual.setCorreo(rp.correo);
                     profActual.setNumeroCuenta(rp.numeroCuenta);
                     profActual.setDependenciaFinanciera(rp.dependenciaFinanciera);
                     profActual.setObservaciones(rp.observaciones);
                     profActual.setFechaIngreso(rp.fechaIngreso);
                     profActual.setCantHoras(rp.cantHoras);
                     profActual.setCategoria(rp.categoria);
                     profActual.setAntiguedad(rp.antiguedad);
                     profActual.setBedelia(rp.adminBedelia);
                     continuo=false;
                 }
             }
             return true;
         }
         return false;
     }
    public synchronized boolean eliminarProfesor( int ci ){
        ManejadorProfesoresBD mp= new ManejadorProfesoresBD();
        if(mp.eliminarProfesor(ci)){
            boolean continuo=true;
            Iterator it = profesores.iterator();
            while(it.hasNext() && continuo){
                Profesor profActual = (Profesor)it.next();
                if(ci==profActual.getCi()){
                    it.remove();
                    return true;
                }
            }
        }
        return false;
    }
    public LinkedList<Profesor> getProfesores(){
        return profesores;
    }
}
