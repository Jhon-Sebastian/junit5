package org.sebastian.junit5app.ejemplos;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.sebastian.junit5app.ejemplos.exceptions.DineroInsuficienteException;
import org.sebastian.junit5app.ejemplos.models.Banco;
import org.sebastian.junit5app.ejemplos.models.Cuenta;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

/**
 * @author jhonc
 * @version 1.0
 * @since 1/12/2021
 */

/*
    TODO: @TestInstance(TestInstance.Lifecycle.PER_METHOD)
    No es recomendable, debido a que va a alterar el comportamiento
    de los otros metodos test de alguno u otra manera, y esto va en contra
    de lo que se busca con los @Test que esten desacoplados de los otros
    metodos y que no quede uno encima del otro, todoo esto con una unica instancia

    * Una sola instancia de la clase Test, para todos los metodos, ya no son
    necesarios los static
 */

//@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class CuentaTest {

    /*
        TODO: Metodos y clases deben ser default

        Para que el contenedor de junit jupiter reconozca al
        metodo como un test se le tiene que asignar la
        anotación
        TODO @Test

        Es mucho mejor pasar el BigDecimal como cadena, debido
        a que si pasa como double tiene limitantes en
        tamaño y capacidad.

        Se puede agregar un mensaje de error personalizado como ultimo parametro,
        siendo este un String pero este se va a ejecutar siempre, ocurra o no el error,
        ocupando memoria, por eso se usa expresiones lamba
     */

    /*
        TODO: @DisplayName
        Anotacion que permite pasar un String con un nombre o descripcion del test
     */

    /*
        TODO: @Disabled
        Se falta el test, quedaba desabilitado
     */

    /*
        TODO: @BeforeEach
        Se utiliza para indicar que el método anotado debe ejecutarse
        antes de cada @Test método en la clase de prueba actual,
        se ejecuta antes de cada metodo, de todos los metodos
     */

    /*
        TODO: @AfterEach
        Se utiliza para indicar que el método anotado debe ejecutarse
        despues de cada @Test método en la clase de prueba actual
     */

    /*
        TODO: @BeforeAll
        Se indica que se ejecuta antes de iniciarlizarse la clase,
        o se cree la instancia de la clase,
        ademas es estatico, entoces esta asociado a la clase
        y no a la instancia
     */

    /*
        TODO: @AfterAll
        Se ejecuta una ves se alla ejecutado todos los test
     */


    /*
        No es atributo de la clase que mantega el estado
        si no que es independiente para cada metodo
    */
    Cuenta cuenta;

    @BeforeEach
    void initMetodoTest() {
        this.cuenta = new Cuenta("Sebastian", new BigDecimal("1000.12345"));
        System.out.println("Iniciando el metodo.");
    }

    @AfterEach
    void tearDown() {
        System.out.println("Finalizando metodo");
    }

    @BeforeAll
    static void beforeAll() {
        System.out.println("Inicializando el Test, antes de inicializar la clase");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("Finalizando el test, despues de que se ejecute todos los test");
    }


    @Nested
    @DisplayName("Probando atributos de la cuente corriente")
    class CuentaTestNombreSaldo {

        @Test
        @DisplayName("El nombre")
        void testNombreCuenta() {

            //cuenta.setPersona("Sebastian");

            String valorEsperado = "Sebastian";
            String valorActual = cuenta.getPersona();

            assertEquals(valorEsperado, valorActual);
            assertTrue(valorActual.equals("Sebastian"));
        }


        @Test
        @DisplayName("El saldo, diferente de null, mayor a cero, valor esperado")
        void testSaldoCuenta() {
            assertEquals(1000.12345, cuenta.getSaldo().doubleValue());
            assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0);
            assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
        }

        @Test
        @DisplayName("Testeando referencias de instancias que sean iguales")
        void testReferenciaCuenta() {
            //Valor actual
            Cuenta actual = new Cuenta("Jhon Doe", new BigDecimal("8900.9997"));
            //Valor esperado
            Cuenta esperado = new Cuenta("Jhon Doe", new BigDecimal("8900.9997"));

            //Compara por referencia
            //assertNotEquals(cuenta2, cuenta);

            /*
                Compara por valor por el metodo equals, por lo tanto se debe
                sobreescribir el metodo equals de acorde a que valores
                usar para comparar
            */
            assertEquals(esperado, actual);
        }
    }

    @Nested
    class CuentaOperacionesTest {
        /*
            TODO: TDD = Test Driven Development
            Primero se crear los test y luego el codigo
        */
        @Test
        void testDebitoCuenta() {
            cuenta.debito(new BigDecimal(100));
            assertNotNull(cuenta.getSaldo(), () -> "¡La Cuenta no puede ser nula!");
            assertEquals(900, cuenta.getSaldo().intValue(), () -> "¡El Monto de la cuenta no es el que se esperaba!");
            //toPlainString() -> Regresa el valor de BigDecimal en String, es mas recomendado que toString()
            assertEquals("900.12345", cuenta.getSaldo().toPlainString(), () -> "Esperado debe ser igual al actual");
        }

        @Test
        void testCreditoCuenta() {
            cuenta.credito(new BigDecimal(100));
            assertNotNull(cuenta.getSaldo());
            assertEquals(1100, cuenta.getSaldo().intValue(), () -> "¡El Monto en la cuenta no es el esperado!");
            assertEquals("1100.12345", cuenta.getSaldo().toPlainString());
        }

        @Test
        void testTransferirDineroCuentas() {
            Cuenta cuenta1 = new Cuenta("Jhon Doe", new BigDecimal("2500"));
            Cuenta cuenta2 = new Cuenta("Sebastian", new BigDecimal("1500.8989"));

            Banco banco = new Banco();
            banco.setNombre("Banco de Bogota");
            //Cuenta origen, Cuenta destino
            banco.transferir(cuenta2, cuenta1, new BigDecimal(500));
            assertEquals("1000.8989", cuenta2.getSaldo().toPlainString());
            assertEquals("3000", cuenta1.getSaldo().toPlainString());
        }
    }


    @Test
    void testDineroInsuficienteException() {
        //Primer argumento => exception a lanzar, Segundo argumento metodo donde ocurre la expepction, con lambda
        Exception exception = assertThrows(DineroInsuficienteException.class, () -> {
            cuenta.debito(new BigDecimal("1500"));
        });
        String actual = exception.getMessage();
        String esperado = "Dinero Insuficiente";
        assertEquals(esperado, actual);
    }




    /*
        Uso de assertAll -> Indica que podemos tener varios
        metodos de la clase assert anidados, para concer mas detalle,
        errores y que se ejecuten todos independientemente si funcionan
        o no, todoo eso con expresiones lamda aniadadas separados por coma
     */

    @Test
    //@Disabled
    @DisplayName("Probando relaciones entre las cuentas y el banco con assertAll.")
    void testRelacionBancoCuentas() {

        //fail(), metodo que hace que el test falle
        //fail();

        Cuenta cuenta1 = new Cuenta("Jhon Doe", new BigDecimal("2500"));
        Cuenta cuenta2 = new Cuenta("Sebastian", new BigDecimal("1500.8989"));

        Banco banco = new Banco();
        banco.agregarCuenta(cuenta1);
        banco.agregarCuenta(cuenta2);

        banco.setNombre("Banco de Bogota");

        banco.transferir(cuenta2, cuenta1, new BigDecimal(500));

        //TODO: assertAll
        assertAll(
                () -> {
                    assertEquals("1000.8989", cuenta2.getSaldo().toPlainString());
                },
                () -> {
                    assertEquals("3000", cuenta1.getSaldo().toPlainString());
                },
                () -> {
                    assertEquals("Banco de Bogota", cuenta1.getBanco().getNombre());
                },
                () -> {
                    assertEquals("Sebastian", banco.getCuentas().stream()
                            .filter(c -> c.getPersona().equals("Sebastian"))
                            .findFirst()
                            .get().getPersona()
                    );
                },
                () -> {
                    assertTrue(banco.getCuentas().stream()
                            .filter(c -> c.getPersona().equals("Sebastian"))
                            .findFirst().isPresent()
                    );
                },
                () -> {
                    assertTrue(banco.getCuentas().stream()
                            .anyMatch(c -> c.getPersona().equals("Sebastian"))
                    );
                }
        );

    }

    /*
        TODO -> @Nested
        Indica que es una Inner Class de Test -> Clase de prueba anidada

        **
        * @BeforeAll || @AfterAll no se puede utilizar en Inner Class
        * ya que no se heredan estas anotaciones
        *
     */

    @Nested
    class SistemaOperativoTest {

        /*
        TODO: TEMA CONDICIONALES
        Cuando se crean Pruebas unitarias en ocasiones no se quieren que se ejecuten
        para ciertos sistemas operativos, versiones de java entre entre otros factores
        como si existe cierta propiedad en el sistema,
        todoo que se le quiere dar un control y uso.

        TODO: @EnableOnOs
        -> Indicamos el SO que queremos que se puedan ejecutar estas pruebas
        de lo contrario no lo hara

        TODO: @DisabLleOnOs
        -> Se indica que no se ejcute el Test cuando este en el SO designado
     */

        @Test
        @EnabledOnOs(OS.WINDOWS)
        void testSoloWindows() {
        }

        @Test
        @EnabledOnOs({OS.MAC, OS.LINUX})
        void testSoloMacYLinux() {
        }

        @Test
        @DisabledOnOs(OS.WINDOWS)
        void testNoWindows() {
            Cuenta venitoJuares = new Cuenta("Jose Luis", new BigDecimal("124.122"));
        }
    }


    class JavaVersionJDKTest {
        /*
        TODO: @EnableOnJre
        -> Indicamos la version de java que queremos que se puedan ejecutar en
        estas pruebas, de lo contrario no lo hara

        TODO: @DisabledOnJre
        -> Se indica que no se ejecute el Test cuando la version de java sea la
        que se paso como valor en la anotación
     */

        @Test
        @EnabledOnJre(JRE.JAVA_8)
        void testSoloJDK8() {
        }

        @Test
        @EnabledOnJre(JRE.JAVA_11)
        void testSoloJDK11() {
        }

        @Test
        @DisabledOnJre(JRE.JAVA_11)
        void testNoJdk11() {
        }
    }

    class SystemPropertiesTest {

        @Test
        void testVisualizarPropiedadesDelSistema() {
            Properties properties = System.getProperties();
            properties.forEach((key, value) -> {
                System.out.println(key + "= " + value);
            });
        }


    /*
        TODO: @EnabledIfSystemProperty
        -> Indicamos la propiedad especifica con el valor esperado
        named = "propiedad.a.buscar"
        matches = "Valor a buscar, tambien se puede usar REGEX"

        matches = "11.0.1",
        matches = ".*11.*" -> Que inici por 11 sin importar la version exacta

     */

        @Test
        @EnabledIfSystemProperty(named = "java.version", matches = ".*11.*")
        void testJavaVersion() {
        }

        @Test
        @DisabledIfSystemProperty(named = "os.arch", matches = ".*32.*")
        void testSoloArqui64Bits() {
        }

        @Test
        @EnabledIfSystemProperty(named = "os.arch", matches = ".*32.*")
        void testNoArqui64Bits() {
        }

        @Test
        @EnabledIfSystemProperty(named = "user.name", matches = "jhonc")
        void testNombreUsuario() {
        }

    /*
        Propiedad nueva del sistema que agrege y la verifico con @EnableIfSystemProperty
     */

        @Test
        @EnabledIfSystemProperty(named = "ENV", matches = "dev")
        void testDev() {
        }

        @Test
        void imprimirVariablesAmbiente() {
            Map<String, String> variablesAmbiente = System.getenv();
            variablesAmbiente.forEach((key, value) -> System.out.println(key + " ==  " + value));
        }

    }


    class VariableAmbienteTest {

        /*
        todo: @EnabledIfEnvironmentVariable
        Se ejecute el test cuando se cumpla la condicion, de lo contrario no lo hara

        @EnabledIfEnvironmentVariable(named = "Nombre de la propiedad",matches = "Valor esperado con REGEX")

        Cualquiera de las 2 formas funcionan
        -> @EnabledIfEnvironmentVariable(named = "JAVA_HOME",matches = "C:\\Java\\openJDK\\openjdk-11.0.1_windows-x64_bin\\jdk-11.0.1")
        -> @EnabledIfEnvironmentVariable(named = "JAVA_HOME",matches = ".*jdk-11.0.1.*")

        todo: @EnabledIfEnvironmentVariable
        No se ejecuta el test si se cumpla la condicion,
        pero si no cumple la condicion si se va a ejecutar
     */

        @Test
        @EnabledIfEnvironmentVariable(named = "JAVA_HOME", matches = ".*jdk-11.0.1.*")
        void testJavaHome() {
        }

        @Test
        @EnabledIfEnvironmentVariable(named = "NUMBER_OF_PROCESSORS", matches = "12")
        void testNumProcesadores() {
        }

    /*
        Tambien podemos crear nuestras propiedades

        Se dirige a donde esta el martillo verde en la esquina superior derecha,
        donde dice <>ClaseEjecutable (Flecha abajo) y se da click en editar configuracion

        Luego en Environment variables se coloca el nombre de la variable y su valor
        de esta manera -> ENVIRONMENT=dev

     */

        @Test
        @EnabledIfEnvironmentVariable(named = "ENVIRONMENT", matches = "dev")
        void testEnv() {
        }

        @Test
        @DisabledIfEnvironmentVariable(named = "ENVIRONMENT", matches = "dev")
        void testEnvProdDisable() {
        }

    }




    /*
        todo -> Assumptions
         import static org.junit.jupiter.api.Assumptions.*;
         
        Es parecido a las anotaciones @Enable @Disable
        que valida con un true o flase, pero en codigo
        de forma programatica.

        Si se cumple todoo bien, si no se desabilida
        para evitar errores.

        Se puede desabilitar el método, o parte del método
        dependiendo si se cumple o no una condición.
        
        Para que funcione se tiene que importar el paquete de
        
        import static org.junit.jupiter.api.Assumptions.*;
     */

    @Test
    @Disabled
    @DisplayName("AssumeTrue, Probando saldo cuenta ")
    void testSaldoCuentaDev() {
        /*
            todo-> AssumeTrue
            Si se cumple, se ejecuta todoo nuestro test o lo que esta
            por dejabo del assumeTrue, de lo contrario no lo hara y
            se desabilitará
         */
        boolean esDev = "dev".equals(System.getProperty("ENV"));
        assumeTrue(esDev);

        assertEquals(1000.12345, cuenta.getSaldo().doubleValue());
        assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0);
        assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
    }

    @Test
    @DisplayName("AssumingThat, Probando saldo cuenta 2")
    void testSaldoCuentaDev2() {
        /*
            todo -> AssumingThat
            Con el assumingThat siempre se va a ejecutar el metodo
            independientemente el assumingThat arroja true o false,
            lo que cambie es que si es true, la expresion lambda se ejecutará
            y lo valida, pero si es falso, solo se salta el assumingThat
            pero el resto del metodo se va a ejecutar.
         */
        boolean esDev = "dev2".equals(System.getProperty("ENV"));
        assumingThat(esDev, () -> {
            assertEquals(1000.12345, cuenta.getSaldo().doubleValue());

        });
        assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0);
        assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
    }

    /*
        @RepeatedTest -> Repetir un test un # de veces

        1. Argumento, [value] -> Numero de veces que se repite el test
        2. Argumento [name] -> Mensaje que se quiere mostrar por cada repeticion
                             adememas de ello tiene variables para saber en que # va
                             con [currentRepetition] [totalRepetitions]

        Si queremos obtener esas variables en el test, se pasa por argumento
        automaticamente con ineyction de dependencias
     */

    @DisplayName(value = "Probando Debido Cuenta Repetir ")
    @RepeatedTest(value = 5, name = "Repetición N° {currentRepetition} de {totalRepetitions}")
    void testRepetirDebitoCuenta(RepetitionInfo info) {

        if (info.getCurrentRepetition() == 3) {
            System.out.println("Estamos en la repetición " + info.getCurrentRepetition());
        }

        cuenta.debito(new BigDecimal(100));
        assertNotNull(cuenta.getSaldo(), () -> "¡La Cuenta no puede ser nula!");
        assertEquals(900, cuenta.getSaldo().intValue(), () -> "¡El Monto de la cuenta no es el que se esperaba!");
        //toPlainString() -> Regresa el valor de BigDecimal en String, es mas recomendado que toString()
        assertEquals("900.12345", cuenta.getSaldo().toPlainString(), () -> "Esperado debe ser igual al actual");
    }

    /*

        @ParameterizedTest ->
        Repite el test pero con una lijera diferencia
        Para ir cambiando los valore del test dinamicamente,
        pero para ello tiene que ir acompañado de otras anotaciones que
        terminen en source de las cuales provengan los datos

        @CvsSource
        @ValueSource

        Por cada valor de @ValueSource se va a inyectar como argumento para testearse

        NOTA = No son acumulativos, se hace el test con un argumento y finaliza,
        luego se reinicia el test pero con el nuevo argumento

        * Para cambiar los mensajes de muestra que arroja por cada iteración se hace lo siguiente
        @ParameterizedTest(name = {index} con {default_display_name})

        Donde index -> Es el indice de la iteracion
        Donde argumentsWithNames -> Es el valor que se pasa como argumento
        Donde argumentsWithNames = {0} -> El cual refleja el valor contenido en esa posicion

     */

    @ParameterizedTest(name = "numero {index} ejecutando con valor {0} -> {argumentsWithNames}")
    @DisplayName(value = "Pasando parametros por argumento")
    @ValueSource(strings = {"100", "200", "300", "500", "700", "1000"})
    void testDebitoCuenta(String monto) {
        cuenta.debito(new BigDecimal(monto));
        assertNotNull(cuenta.getSaldo(), "¡La Cuenta no puede ser nula!");
        assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
    }


}