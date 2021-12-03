package principal;

import datos.Viajes;
import datos.Destinos;
import entradaDatos.Consola;
import java.util.Scanner;
import persistencia.Archivo;
import persistencia.Registro;

public class MenuViajes {

    private static Archivo archiViajes;
    private static Registro reg;
    private static Viajes via;
    
    //Constructor
    MenuViajes() {
        try {
            setArchiViajes(new Archivo("Viajes.dat", new Viajes())); // colocar el camino correcto del archivo que usemos
        } catch (ClassNotFoundException e) {
            System.out.println("Error al crear los descriptores de archivos: " + e.getMessage());
            System.exit(1);
        }

        setReg(new Registro());
        setVia(new Viajes());
        getReg().setDatos(getVia());

    }
    
    public static Archivo getArchiViajes() {
        return archiViajes;
    }

    public static void setArchiViajes(Archivo aArchiViajes) {
        archiViajes = aArchiViajes;
    }

    public static Registro getReg() {
        return reg;
    }

    public static void setReg(Registro aReg) {
        reg = aReg;
    }

    public static Viajes getVia() {
        return via;
    }

    public static void setVia(Viajes aVia) {
        via = aVia;
    }

    /**
     * Muestra el contenido de un archivo (incluidos los registros marcados como
     * borrados) es de utilidad para verificar el contenido del archivo a medida
     * que vamos avanzando en la resolución de modo de controlar como estan
     * cargados los registros incluyendo los vacios o los borrados
     */
    public static void mostrarTodo() {

        getArchiViajes().abrirParaLeerEscribir();
        getArchiViajes().irPrincipioArchivo();
        getReg().mostrarRegistro(0, true);
        while (!archiViajes.eof()) {
            setReg(getArchiViajes().leerRegistro());
            getReg().mostrarRegistro(1, false);
        }
        getArchiViajes().cerrarArchivo();
    }

    /**
     * Muestra el contenido de un archivo (sin incluir los registros marcados
     * como borrados)
     */
    public static void mostrarActivos() {

        getArchiViajes().abrirParaLectura();
        getArchiViajes().irPrincipioArchivo();
        getReg().mostrarRegistro(0, true);
        while (!archiViajes.eof()) {
            setReg(getArchiViajes().leerRegistro());
            if (getReg().getEstado() == true) {
                getReg().mostrarRegistro(1, true); /* En el caso de requerirse un modo determinado de impresión
            * por ejemplo en columnas, este método deberá organizar el titulo y encabezado del
            * listado y convocar a leerRegistro para luego mostrarlo de acuerdo a la solicitud
            * sin convocar al metodo mostrarRegistro */
            }
        }
        getArchiViajes().cerrarArchivo();
    }
    /**
     * Verificar si el archivo existe
     *
     * @return valor booleano indicando si existe la instancia de file
     * archivo
     */
    public static boolean existeArchivoDestinos() {
        boolean estaVivo = false;
        try {
            estaVivo = MenuDestinos.getArchiDest().getFd().exists();
        }
        catch (Exception e) {
            System.out.println("Error al verificar existencia del archivo Destinos: " + e.getMessage());
            System.exit(1);
        }
                
        if(!estaVivo)
            MenuDestinos.crearArchivoDestinos();
        return true;
    }

    /**
     * Muestra el contenido de un archivo (sin incluir los registros marcados
     * como borrados)
     */
    public static void mostrarArtPorDestinos() {
        int totalViajes = 0;
        Registro regDestino;
        
        // Ahora verificamos si el archivo de Rubros existe
        // como el metodo es estatico no necesitamos declarar una instancia de ese tipo
        if (existeArchivoDestinos()) {
            MenuDestinos.getArchiDest().abrirParaLectura();
            MenuDestinos.getArchiDest().irPrincipioArchivo();
            while (!MenuDestinos.getArchiDest().eof()) {
                regDestino = MenuDestinos.getArchiDest().leerRegistro();
                if (regDestino.getEstado() == true) {
                    regDestino.mostrarRegistro(2, true); /* En el caso de requerirse un modo determinado de impresión
                     * por ejemplo en columnas, este método deberá organizar el titulo y encabezado del
                     * listado y convocar a leerRegistro para luego mostrarlo de acuerdo a la solicitud
                     * sin convocar al metodo mostrarRegistro */
                    Destinos este = (Destinos) regDestino.getDatos();
                    // devuelve Grabable y es necesario indicar el tipo Destinos para asignar
                    totalViajes = mostrarViaDelDestino(este.getCodDest()) + totalViajes;
                }
            }
            MenuDestinos.getArchiDest().cerrarArchivo();
            System.out.println("                                                                Total General: " + totalViajes);
        } else {
            System.out.println("El archivo de Destinos no existe bolin. . .");
        }
    }

    /**
     * Muestra el contenido de un archivo (sin incluir los registros marcados
     * como borrados)
     */
    public static int mostrarViaDelDestino(int esteDestino) {
        int totPorDestino = 0;
        getArchiViajes().abrirParaLectura();
        getArchiViajes().irPrincipioArchivo();
        getReg().mostrarRegistro(0, true);
        while (!archiViajes.eof()) {
            setReg(getArchiViajes().leerRegistro());
            Viajes esteVia = (Viajes)getReg().getDatos();

            //System.out.println(getReg().getEstado() + "----" + getReg().getNroOrden() + "----" + esteDestino);
            if (getReg().getEstado() == true && esteVia.getCod_Dest() == esteDestino) {
                getReg().mostrarRegistro(1, true); /*
                 * En el caso de requerirse un modo determinado de impresión por
                 * ejemplo en columnas, este método deberá organizar el titulo y
                 * encabezado del listado y convocar a leerRegistro para luego
                 * mostrarlo de acuerdo a la solicitud sin convocar al metodo
                 * mostrarRegistro
                 */
                totPorDestino++;
            }
        }
        getArchiViajes().cerrarArchivo();
        if (totPorDestino == 0) {
            System.out.println("                      No hay Vijes para este Destino");
            System.out.println("----------------------------------------------------------");
        } else {
            System.out.println("                                 Total de Viajes por Destino: " + totPorDestino);
            System.out.println("----------------------------------------------------------");
        }
        return totPorDestino;
    }

    /**
     * Carga un registro de Viajes por teclado
     */
    public static Registro leerViaje(int val) {
        Registro aux = new Registro();
        getVia().cargarDatos(val);
        aux.cargarNroOrden(getVia().getCod_V());
        aux.setDatos(getVia());
        aux.setEstado(true);
        return aux; /* Si utilizaramos la instancia reg, nos encontrariamos modificando
         * el espacio de memoria relacionado con la lectura del archivo y por lo tanto
         * cambiaria a medida que avanzaramos sobre el archivo. OJO CON ESTO
         */
    }

    public static void cargarViajes() {
        Scanner entrada = new Scanner(System.in);
        String op;
        char opcion = 's';
        getArchiViajes().abrirParaLeerEscribir();

        try {
            while (opcion != 'n') {
                setReg(leerViaje(0));
                getArchiViajes().altaRegistro(getReg());
                System.out.print("Continuar s/n: ");
                op = entrada.next();
                op = (!op.equals("n")) ? "s" : op;
                opcion = op.charAt(0);
            }
        } catch (Exception e) {
            System.out.println("Error al cargar el archivo: " + e.getMessage());
            System.exit(1);
        }

        getArchiViajes().cerrarArchivo();
    }
/*
    public static void cargarViajes() {
        Scanner entrada = new Scanner(System.in);
        String op;
        char opcion = 's';
        getArchiArticulos().abrirParaLeerEscribir();

        try {
            while (opcion != 'n') {
                setReg(leerViaje(0));
                getArchiArticulos().altaRegistro(getReg());
                System.out.print("Continuar Enter/n: ");
                op = entrada.next();
                op = (!op.equals("n")) ? "s" : op;
                opcion = op.charAt(0);
            }
        } catch (Exception e) {
            System.out.println("Error al cargar el archivo: " + e.getMessage());
            System.exit(1);
        }

        getArchiArticulos().cerrarArchivo();
    }
*/
    /**
     *
     */
    public static void bajasViajes() {
        Scanner entrada = new Scanner(System.in);
        String op;
        char opcion = 's';
        getArchiViajes().abrirParaLeerEscribir();

        try {
            while (opcion != 'n') {
                System.out.println("Ingrese el viaje a borrar: ");
                getVia().cargarCodigo();
                getReg().cargarNroOrden(getVia().getCod_V());
                getArchiViajes().bajaRegistro(getReg());
                System.out.print("Continuar Enter/n: ");
                op = entrada.next();
                op = (!op.equals("n")) ? "s" : op;
                opcion = op.charAt(0);
            }
        } catch (Exception e) {
            System.out.println("Error al dar de baja en el archivo: " + e.getMessage());
            System.exit(1);
        }

        getArchiViajes().cerrarArchivo();
    }
    


    public void menuPrincipal() {
        int op;

        MenuViajes x;
        x = new MenuViajes();
        getArchiViajes().abrirParaLeerEscribir();
        long cuantos = getArchiViajes().cantidadRegistros();
        getArchiViajes().cerrarArchivo();

        if (cuantos == 0) {
            getArchiViajes().crearArchivoVacio(new Registro(getVia(), 0));
        }

        do {
            System.out.println("Actulizacion de Viajes");
            System.out.println("1. Altas");
            System.out.println("2. Bajas");
            System.out.println("3. Modificaciones");
        //  System.out.println("4. Listado de Articulos");
       /*     System.out.println("6. Listado de todos los Articulos incluyendo borrados");
            System.out.println("7. Depuración de Artículos (bajas físicas)");
            System.out.println("8. Borrar contenido del archivo de Articulos");*/
            System.out.println("4. Listado de viajes por Destino");
            System.out.println("0. Salir");

            System.out.print("Ingrese opcion: ");
            op = Consola.leerEntero();
            System.out.println("");
            switch (op) {
                case 1:
                    System.out.println("Altas de viajes: ");
                    cargarViajes();
                    break;

                case 2:
                    System.out.println("Bajas de Viajes: ");
                    bajasViajes();
                    break;

                case 3:
                    System.out.println("Ingrese el viaje a modificar: ");
                    getVia().cargarCodigo();
                    getReg().cargarNroOrden(getVia().getCod_V());
                    getArchiViajes().modificarRegistro(getReg()); // Evitamos usar reg que ya está vinculado al archivo
                    break;
/*
                case 4:
                    System.out.println("Se muestra el archivo de Articulos (sin incluir 'borrados')...");
                    mostrarActivos();
                    break;
*/
/*                case 6:
                    System.out.println("Se muestra el archivo de Articulos (incluyendo 'borrados')...");
                    mostrarTodo();
                    break;

                case 7:
                    System.out.println("Se eliminan registros de Articulos marcados...");
                    getArchiArticulos().depurar();
                    System.out.println("\nOperacion terminada...");
                    break;

                case 8:
                    System.out.println("Borrar el contenido del Archivo de Articulos...");
                    getArchiArticulos().crearArchivoVacio(new Registro(getArt(), 0));
                    System.out.println("\nOperacion terminada..."); 
                    break; */

                case 4:
                    System.out.println("Listado de viajes por Destino");
                    mostrarArtPorDestinos();
                    break;

                case 0:
                    ;
            }
        } while (op != 0);
    }
}
