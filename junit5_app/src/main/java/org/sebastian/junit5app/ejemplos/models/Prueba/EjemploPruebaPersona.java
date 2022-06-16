package org.sebastian.junit5app.ejemplos.models.Prueba;

import org.sebastian.junit5app.ejemplos.models.Persona;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author jhonc
 * @version 1.0
 * @since 17/12/2021
 */
public class EjemploPruebaPersona {

    public static void main(String[] args) {

        List<Persona> personas = new ArrayList<>();
        personas.add(new Persona("Jose"));
        personas.add(new Persona("Andres"));
        personas.add(new Persona("Luisa"));
        personas.add(new Persona("Carlos"));
        personas.add(new Persona("Zrique"));

        System.out.println("Sort Normal");
        personas.sort(Persona::compareTo);
        personas.forEach(System.out::println);
        System.out.println();

//

        System.out.println("Sort Reverse");
        personas.sort(Collections.reverseOrder());
        personas.forEach(System.out::println);
        System.out.println();

//

        System.out.println("Sort Clase anonima");
        personas.sort(new Comparator<Persona>() {
            @Override
            public int compare(Persona p1, Persona p2) {
                return p1.getNombre().compareTo(p2.getNombre());
            }
        });
        personas.forEach(System.out::println);
        System.out.println();

        //


        System.out.println("Sort revers con lambda");
        personas.sort( (a,b) -> b.compareTo(a));
        personas.forEach(System.out::println);
        System.out.println();

    }

}
