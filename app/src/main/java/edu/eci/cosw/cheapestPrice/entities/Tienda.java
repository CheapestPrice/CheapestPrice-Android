package edu.eci.cosw.cheapestPrice.entities;



import java.sql.Blob;

import java.sql.Timestamp;
import java.util.*;

/**
 * Clase tienda
 *Created by Daniela.
 */

public class Tienda implements java.io.Serializable {


    private int id;
    private String nit;
    private double x;
    private double y;
    private String direccion;
    private String nombre;
    private String telefono;
    private boolean disponible;
    private Blob logo;
    private List<Horario> horarios;
    private Tendero tendero;
    private List<Opinion> opiniones;
    private List<Item> items;

    public Tienda(){}

    public Tienda(String direccion,int id,String nombre,String telefono, boolean disponible,double x,double y,String nit) {
        this.horarios=new ArrayList<Horario>();
        this.opiniones=new ArrayList<Opinion>();
        this.items=new ArrayList<>();
        this.direccion = direccion;
        this.setId(id);
        this.nombre = nombre;
        this.telefono = telefono;
        this.disponible = disponible;
        this.x = x;
        this.y = y;
        this.nit = nit;

    }

    public Tienda(String direccion,int id, String nombre,String telefono, boolean disponible,Blob logo,double x,double y,String nit) {
        this.horarios=new ArrayList<Horario>();
        this.opiniones=new ArrayList<Opinion>();
        this.items=new ArrayList<>();
        this.direccion = direccion;
        this.setId(id);
        this.nombre = nombre;
        this.telefono = telefono;
        this.disponible = disponible;
        this.logo = logo;
        this.x = x;
        this.y = y;
        this.nit = nit;

    }

    public Tienda(String direccion,int id,String nombre,String telefono, Tendero tendero,double x,double y,String nit){
        this.horarios=new ArrayList<Horario>();
        this.opiniones=new ArrayList<Opinion>();
        this.items=new ArrayList<>();
        this.direccion = direccion;
        this.nombre = nombre;
        this.setId(id);
        this.telefono = telefono;
        this.tendero=tendero;
        this.disponible=true;
        this.x = x;
        this.y = y;
        this.nit = nit;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public Blob getLogo() {
        return logo;
    }

    public void setLogo(Blob logo) {
        this.logo = logo;
    }

    @Override
    public boolean equals(Object o){
        Tienda ot=(Tienda) o;
        return getId()==ot.getId() && direccion.equals(ot.getDireccion()) && nombre.equals(ot.getNombre()) && telefono.equals(ot.getTelefono()) && x==ot.getX() && y==ot.getY() && nit.equals(ot.getNit());
    }

    @Override
    public String toString(){
        return "nombre: "+nombre+" direccion: "+direccion+" telefono: "+telefono+" NIT: "+nit;
    }

    public Tendero getTendero() {
        return tendero;
    }

    public void setTendero(Tendero tendero) {
        this.tendero = tendero;
    }

    public List<Horario> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<Horario> horarios) {
        this.horarios = horarios;
    }

    public List<Opinion> getOpiniones() {
        return opiniones;
    }

    public void setOpiniones(List<Opinion> opiniones) {
        this.opiniones = opiniones;
    }

    /*
     * Agregar una opinion a la tienda
     * @param opinion
     */
    public void addOpinion(Opinion opinion){
        opiniones.add(opinion);
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    /*
     * La tienda se encuentra abierta en la fecha estipualada
     * @param time
     */
    public boolean isOpen(Timestamp time){
        HashMap<Integer, String> diasemana=new HashMap<Integer,String>();
        java.util.GregorianCalendar cal=(GregorianCalendar) Calendar.getInstance();
        diasemana.put(cal.MONDAY,"Lunes");
        diasemana.put(cal.TUESDAY,"Martes");
        diasemana.put(cal.WEDNESDAY,"Miercoles");
        diasemana.put(cal.THURSDAY,"Jueves");
        diasemana.put(cal.FRIDAY,"Viernes");
        diasemana.put(cal.SATURDAY,"Sabado");
        diasemana.put(cal.SUNDAY,"Domingo");
        cal.setTime(time);
        String dia = diasemana.get(cal.get(Calendar.DAY_OF_WEEK));
        boolean ans=false;
        for (int i = 0; i < horarios.size() && !ans; i++) {
            Horario tmp=horarios.get(i);
            if(tmp.getDia().equals(dia)){
                if(tmp.getHoraInicio()<cal.HOUR_OF_DAY && tmp.getHoraFin()>cal.HOUR_OF_DAY && tmp.getMinutosInicio()<cal.MINUTE && tmp.getMinutoFin()>cal.MINUTE){
                    ans=true;
                }
            }
        }
        return ans;
    }

    /**
     * Modificar horario de un dia de la tienda
     * @param  dia
     * @param horario
     */
    public void modifyHorary(String dia, Horario horario){
        for (int j = 0; j < horarios.size(); j++) {
            if(horarios.get(j).getDia().equals(dia)){
                horarios.remove(j);
                horarios.add(j,horario);
            }
        }
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}