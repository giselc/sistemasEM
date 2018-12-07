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
public class Profesor {
    private final int ci;
    private Grado grado;
    private Arma arma;
    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;
    private String observaciones;
    private String fechaIngreso;
    private String telefono;
    private String correo;
    private String numeroCuenta;
    private String dependenciaFinanciera;
    private int cantHoras;
    private int categoria;
    private int antiguedad;
    private boolean adminBedelia;

    public Profesor(int ci, Grado grado, Arma arma, String primerNombre, String segundoNombre, String primerApellido, String segundoApellido, String observaciones, String fechaIngreso, String telefono, String correo, String numeroCuenta, String dependenciaFinanciera, int cantHoras, int categoria, int antiguedad, boolean adminBedelia) {
        this.ci = ci;
        this.grado = grado;
        this.arma = arma;
        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.observaciones = observaciones;
        this.fechaIngreso = fechaIngreso;
        this.telefono = telefono;
        this.correo = correo;
        this.numeroCuenta = numeroCuenta;
        this.dependenciaFinanciera = dependenciaFinanciera;
        this.cantHoras = cantHoras;
        this.categoria = categoria;
        this.antiguedad = antiguedad;
        this.adminBedelia = adminBedelia;
    }

    public int getCi() {
        return ci;
    }

    public Grado getGrado() {
        return grado;
    }

    public void setGrado(Grado grado) {
        this.grado = grado;
    }

    public Arma getArma() {
        return arma;
    }

    public void setArma(Arma arma) {
        this.arma = arma;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(String fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getDependenciaFinanciera() {
        return dependenciaFinanciera;
    }

    public void setDependenciaFinanciera(String dependenciaFinanciera) {
        this.dependenciaFinanciera = dependenciaFinanciera;
    }

    public int getCantHoras() {
        return cantHoras;
    }

    public void setCantHoras(int cantHoras) {
        this.cantHoras = cantHoras;
    }

    public int getCategoria() {
        return categoria;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }

    public int getAntiguedad() {
        return antiguedad;
    }

    public void setAntiguedad(int antiguedad) {
        this.antiguedad = antiguedad;
    }

    public boolean isAdminBedelia() {
        return adminBedelia;
    }

    public void setBedelia(boolean adminBedelia) {
        this.adminBedelia = adminBedelia;
    }

    
    
    
}
