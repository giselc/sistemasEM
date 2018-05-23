/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.util.ArrayList;
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
    private ManejadorCodigos() {
        ManejadorCodigoBD mc= new ManejadorCodigoBD();
        departamentos =  mc.getDepartamentos();
        estadosCiviles = mc.getEstadosCiviles();
        cursos = mc.getCursos();
        armas = mc.getArmas();
        carreras = mc.getCarreras();
    }
    
    public static ManejadorCodigos getInstance() {
        return ManejadorCodigosHolder.INSTANCE;
    }
    
    private static class ManejadorCodigosHolder {

        private static final ManejadorCodigos INSTANCE = new ManejadorCodigos();
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
}
