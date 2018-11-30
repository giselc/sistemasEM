/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

/**
 *
 * @author Gisel
 */
public class Familiar {
    private String NombreComp;
    private String FechaNac;
    private Departamento DepartamentoNac;
    private String LocalidadNac;
    private EstadoCivil EstadoCivil;
    private String Domicilio;
    private Departamento Departamento;
    private String Localidad;
    private String telefono;
    private String Profesion;
    private String LugarTrabajo;

    public Familiar(String NombreComp, String FechaNac, Departamento DepartamentoNac, String LocalidadNac, EstadoCivil EstadoCivil, String Domicilio, Departamento Departamento, String Localidad, String telefono, String Profesion, String LugarTrabajo) {
        this.NombreComp = NombreComp;
        this.FechaNac = FechaNac;
        this.DepartamentoNac = DepartamentoNac;
        this.LocalidadNac = LocalidadNac;
        this.EstadoCivil = EstadoCivil;
        this.Domicilio = Domicilio;
        this.Departamento = Departamento;
        this.Localidad = Localidad;
        this.telefono = telefono;
        this.Profesion = Profesion;
        this.LugarTrabajo = LugarTrabajo;
    }

    public String getNombreComp() {
        return NombreComp;
    }

    public String getFechaNac() {
        return FechaNac;
    }

    public Departamento getDepartamentoNac() {
        return DepartamentoNac;
    }

    public String getLocalidadNac() {
        return LocalidadNac;
    }

    public EstadoCivil getEstadoCivil() {
        return EstadoCivil;
    }

    public String getDomicilio() {
        return Domicilio;
    }

    public Departamento getDepartamento() {
        return Departamento;
    }

    public String getLocalidad() {
        return Localidad;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getProfesion() {
        return Profesion;
    }

    public String getLugarTrabajo() {
        return LugarTrabajo;
    }
    
    
    
}
