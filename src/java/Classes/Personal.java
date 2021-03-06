/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.util.HashMap;

/**
 *
 * @author Gisel
 */
public class Personal {
    private int nroInterno;
    private final int ci;
    private Grado grado;
    private Arma arma;
    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;
    private HashMap<Integer,Documento> documentos;
    private String fechaAltaSistema;
    
    public Personal(int nroInterno, int ci, Grado grado, Arma arma, String primerNombre, String segundoNombre, String primerApellido, String segundoApellido, HashMap<Integer,Documento> documentos, String fechaAltaSistema) {
        this.nroInterno = nroInterno;
        this.ci = ci;
        this.grado = grado;
        this.arma = arma;
        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.documentos = documentos;
        this.fechaAltaSistema = fechaAltaSistema;
    }

    public String getFechaAltaSistema() {
        return fechaAltaSistema;
    }

    public void setFechaAltaSistema(String fechaAltaSistema) {
        this.fechaAltaSistema = fechaAltaSistema;
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

    public HashMap<Integer,Documento> getDocumentos() {
        return documentos;
    }

   

    public void setNroInterno(int nroInterno) {
        this.nroInterno = nroInterno;
    }

    public void setGrado(Grado grado) {
        this.grado = grado;
    }

    public void setArma(Arma arma) {
        this.arma = arma;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public void setDocumentos(HashMap<Integer,Documento> documentos) {
        this.documentos = documentos;
    }

   

    public void agregarDocumento(Documento doc) {
        documentos.put(doc.getId(), doc);
    }
    public void eliminarDocumento(int idDoc) {
        documentos.remove(idDoc);
    }

    
    
    
    

   
    
    
}
