/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manejadores;

import Classes.Arma;
import Classes.Carrera;
import Classes.Curso;
import Classes.Departamento;
import Classes.EstadoCivil;
import Classes.Grado;
import Classes.Tipo;
import java.util.HashMap;

/**
 *
 * @author Gisel
 */
public class ManejadorCodigos {
    private HashMap<Integer,Departamento> departamentos;
    private HashMap<Integer,EstadoCivil> estadosCiviles;
    private HashMap<Integer,Curso> cursos;
    private HashMap<Integer,Arma> armas;
    private HashMap<Integer,Carrera> carreras;
    private HashMap<Integer,Grado> grados;
    private HashMap<Integer,Tipo> tipoPersonal;
    private HashMap<Integer,Tipo> tipoDocumentos;
    private ManejadorCodigos() {
        ManejadorCodigoBD mc= new ManejadorCodigoBD();
        departamentos =  mc.getDepartamentos();
        estadosCiviles = mc.getEstadosCiviles();
        cursos = mc.getCursos();
        armas = mc.getArmas();
        tipoPersonal = mc.getTipoPersonal();
        grados = mc.getGrados();
        carreras = mc.getCarreras();
        tipoDocumentos = mc.getTipoDocumentos();
        
    }
    
    public static ManejadorCodigos getInstance() {
        return ManejadorCodigosHolder.INSTANCE;
    }
    
    private static class ManejadorCodigosHolder {

        private static final ManejadorCodigos INSTANCE = new ManejadorCodigos();
    }

    public static int ConvertirDepartamento(int deptoViejo){
        int dptoNuevo =0;
        switch(deptoViejo){
            case 1: dptoNuevo = 2; break; 
            case 2: dptoNuevo = 9; break;
            case 3: dptoNuevo = 14; break; 
            case 4: dptoNuevo = 19; break;
            case 5: dptoNuevo = 3; break; 
            case 6: dptoNuevo = 13; break;
            case 7: dptoNuevo = 1; break; 
            case 8: dptoNuevo = 15; break; 
            case 9: dptoNuevo = 11; break; 
            case 10: dptoNuevo = 12; break; 
            case 11: dptoNuevo = 17; break; 
            case 12: dptoNuevo = 4; break;     
            case 13: dptoNuevo = 16; break; 
            case 14: dptoNuevo = 6; break; 
            case 15: dptoNuevo = 7; break;   
            case 16: dptoNuevo = 8; break;     
            case 17: dptoNuevo = 5; break; 
            case 18: dptoNuevo = 18; break; 
            case 19: dptoNuevo = 10; break; 
        }
        return dptoNuevo;
    }
    public static int convertirEstadoCivil(int estadoCivilViejo){
        int estadoCivilNuevo=0;
        switch(estadoCivilViejo){
            case 1: estadoCivilNuevo=1; break;
            case 2: estadoCivilNuevo=2; break;
            case 3: estadoCivilNuevo=5; break;
            case 5: estadoCivilNuevo=4; break;
            case 6: estadoCivilNuevo=3; break;
        }
        return estadoCivilNuevo;
    }
    public static String convertirFecha(String fechaVieja){
        String [] campos= fechaVieja.split("/");
        return campos[2]+"-"+campos[1]+"-"+campos[0];
    } 
    
    public HashMap<Integer, Departamento> getDepartamentos() {
        return departamentos;
    }

    public HashMap<Integer, EstadoCivil> getEstadosCiviles() {
        return estadosCiviles;
    }

    public HashMap<Integer, Curso> getCursos() {
        return cursos;
    }

    public HashMap<Integer, Arma> getArmas() {
        return armas;
    }

    public HashMap<Integer, Grado> getGrados() {
        return grados;
    }
    public HashMap<Integer, Carrera> getCarreras() {
        return carreras;
    }

    public HashMap<Integer, Tipo> getTipoDocumentos() {
        return tipoDocumentos;
    }
    
    public Departamento getDepartamento(int codigo){
        return departamentos.get(codigo);
    }
    public EstadoCivil getEstadoCivil(int codigo){
        return estadosCiviles.get(codigo);
    }
    public Curso getCurso(int codigo){
        return cursos.get(codigo);
    }
    public Arma getArma(int codigo){
        return armas.get(codigo);
    }
    public Carrera getCarrera(int codigo){
        return carreras.get(codigo);
    }
    public Grado getGrado(int codigo){
        return grados.get(codigo);
    }
    public Tipo getTipoDocumento(int codigo){
        return tipoDocumentos.get(codigo);
    }
    public Tipo getTipoPersonal(int codigo){
        return tipoPersonal.get(codigo);
    }
}
