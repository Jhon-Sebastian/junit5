package org.sebastian.junit5app.ejemplos;

import org.junit.jupiter.api.*;
import org.sebastian.junit5app.ejemplos.models.Cuenta;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author jhonc
 * @version 1.0
 * @since 16/06/2022
 */

class CuentaReportesTest {

    Cuenta cuenta;

    @BeforeEach
    void initMetodoTest(TestInfo testInfo, TestReporter testReporter) {
        this.cuenta = new Cuenta("Sebastian", new BigDecimal("1000.12345"));
        System.out.println("Iniciando el metodo.");
        System.out.println("ejecutando: [NombreDisplay]=" + testInfo.getDisplayName()
                + ", [NombreTest]=" + testInfo.getTestMethod().get().getName()
                + ", [NombreTags] " + testInfo.getTags());
    }

    /**
     * //@param testInfo
     * Tener un informe de los @DisplayName y @Tags de los test,
     * ademas de @getTestClass -> Nombre de clase
     * nombre del metodo -> @getTesMethod
     *
     * //@param testReporter
     * Para guardar en el log
     * Para tener un deporte detallado, fecha y estado de los test
     */

    //Si se quiere ejecutar el @TestInfo en todos los test se hace en el @BeforeEach

    @Tag("cuenta")
    @Test
    @DisplayName("El nombre")
    // void testNombreCuenta(TestInfo testInfo, TestReporter testReporter)
    void testNombreCuenta() {
//        System.out.println("ejecutando: [NombreDisplay]=" + testInfo.getDisplayName()
//                + ", [NombreTest]=" + testInfo.getTestMethod().get().getName()
//                + ", [NombreTags] " + testInfo.getTags());
        String valorEsperado = "Sebastian";
        String valorActual = cuenta.getPersona();
        assertEquals(valorEsperado, valorActual);
        assertTrue(valorActual.equals("Sebastian"));
    }

}
