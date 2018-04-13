/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author Gisel
 */
public class Personal {
    private int nroInterno;
    private int ci;
    private Grado grado;
    private Arma arma;
    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;
    private ArrayList<Documento> documentos;
    private String observaciones;
    private boolean profesor;

    public Personal(int nroInterno, int ci, Grado grado, Arma arma, String primerNombre, String segundoNombre, String primerApellido, String segundoApellido, ArrayList<Documento> documentos, String observaciones, boolean profesor) {
        this.nroInterno = nroInterno;
        this.ci = ci;
        this.grado = grado;
        this.arma = arma;
        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.documentos = documentos;
        this.observaciones = observaciones;
        this.profesor = profesor;
    }

    public boolean isProfesor() {
        return profesor;
    }

    public Arma getArma() {
        return arma;
    }

    public int getNroInterno() {
        return nroInterno;
    }

    public int getCi() {
        return ci;
    }

    public Grado getGrado() {
        return grado;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public ArrayList<Documento> getDocumentos() {
        return documentos;
    }

    public String getObservaciones() {
        return observaciones;
    }

    

    
    

   
    
    
}
