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
public class Cadete extends Personal{
    private Curso curso;
    private Date fechaNac;
    private String sexo;
    private Departamento departamentoNac;
    private String localidadNac;
    private String cc;
    private int ccNro;
    private EstadoCivil estadoCivil;
    private String domicilio;
    private Departamento departamento;
    private String localidad;
    private String telefono;
    private String email;
    private int derecha;
    private int hijos;
    private boolean repitiente;
    private boolean lmga;
    private boolean paseDirecto;
    private double notaPaseDirecto;
    private ArrayList<Familiar> familiares;

    public Cadete(Curso curso, Date fechaNac, String sexo, Departamento departamentoNac, String localidadNac, String cc, int ccNro, EstadoCivil estadoCivil, String domicilio, Departamento departamento, String localidad, String telefono, String email, int derecha, int hijos, boolean repitiente, boolean lmga, boolean paseDirecto, double notaPaseDirecto, ArrayList<Familiar> familiares, int nroInterno, int ci, Grado grado, Arma arma, String primerNombre, String segundoNombre, String primerApellido, String segundoApellido, ArrayList<Documento> documentos, String observaciones, boolean profesor) {
        super(nroInterno, ci, grado, arma, primerNombre, segundoNombre, primerApellido, segundoApellido, documentos, observaciones, profesor);
        this.curso = curso;
        this.fechaNac = fechaNac;
        this.sexo = sexo;
        this.departamentoNac = departamentoNac;
        this.localidadNac = localidadNac;
        this.cc = cc;
        this.ccNro = ccNro;
        this.estadoCivil = estadoCivil;
        this.domicilio = domicilio;
        this.departamento = departamento;
        this.localidad = localidad;
        this.telefono = telefono;
        this.email = email;
        this.derecha = derecha;
        this.hijos = hijos;
        this.repitiente = repitiente;
        this.lmga = lmga;
        this.paseDirecto = paseDirecto;
        this.notaPaseDirecto = notaPaseDirecto;
        this.familiares = familiares;
    }

    

    public ArrayList<Familiar> getFamiliares() {
        return familiares;
    }

    public Curso getCurso() {
        return curso;
    }

    public Date getFechaNac() {
        return fechaNac;
    }

    public String getSexo() {
        return sexo;
    }

    public Departamento getDepartamentoNac() {
        return departamentoNac;
    }

    public String getLocalidadNac() {
        return localidadNac;
    }

    public String getCc() {
        return cc;
    }

    public int getCcNro() {
        return ccNro;
    }

    public EstadoCivil getEstadoCivil() {
        return estadoCivil;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public String getLocalidad() {
        return localidad;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
    }

    public int getDerecha() {
        return derecha;
    }

    public int getHijos() {
        return hijos;
    }

    public boolean isRepitiente() {
        return repitiente;
    }

    public boolean isLmga() {
        return lmga;
    }

    public boolean isPaseDirecto() {
        return paseDirecto;
    }

    public double getNotaPaseDirecto() {
        return notaPaseDirecto;
    }
    
    
    
}
