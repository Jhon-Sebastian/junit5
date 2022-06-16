package org.sebastian.junit5app.ejemplos.models;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jhonc
 * @version 1.0
 * @since 17/12/2021
 */
public class Persona implements Comparable<Persona>{


    private Integer id;
    private String nombre;

    private static int contador;

    public Persona(){
        id = ++contador;
    }

    public Persona(String nombre){
        this();
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public int compareTo(Persona o) {
        return this.id.compareTo(o.getId());
    }

    @Override
    public String toString() {

        
        return "id=" + id +
                ", nombre='" + nombre + '\'';
    }
}
