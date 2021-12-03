/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

import datos.Destinos;
import entradaDatos.Consola;
import java.util.Scanner;
import persistencia.Archivo;
import persistencia.Registro;


public class MenuDestinos {
    private static Archivo archiDest; // se crea una variable estatica "archiDest" de tipo archivo
    private static Registro reg;
    private static Destinos des;

    
    // Constructor
    MenuDestinos(){
        try {
            setArchiDest(new Archivo("Destinos.dat", new Destinos())); // colocar el camino correcto del archivo que usemos
        } catch (ClassNotFoundException e) {
            System.out.println("Error al crear los descriptores de archivos: " + e.getMessage());
            System.exit(1);
        }
        
        setReg(new Registro());
        setDes(new Destinos());
        getReg().setDatos(getDes());
    }
    
    public static Archivo getArchiDest() {
        return archiDest;
    }

    public static void setArchiDest(Archivo aArchiRubros) {
        archiDest = aArchiRubros;
    }

    public static Registro getReg() {
        return reg;
    }

    public static void setReg(Registro aReg) {
        reg = aReg;
    }

    /**
     *
     * Devuelve un Destinos
     */
    public static Destinos getDes() {
        return des;
    }

    public static void setDes(Destinos aDes) {
        des = aDes;
    }

    /**
     * Muestra el contenido de un archivo (incluidos los registros marcados como
     * borrados) es de utilidad para verificar el contenido del archivo a medida 
     * que vamos avanzando en la resolución de modo de controlar como estan 
     * cargados los registros incluyendo los vacios o los borrados
     */
    public static void mostrarTodo() {

        getArchiDest().abrirParaLeerEscribir();
        getArchiDest().irPrincipioArchivo();
        getReg().mostrarRegistro(0, true);
        while (!archiDest.eof()) {
            setReg(getArchiDest().leerRegistro());
            getReg().mostrarRegistro(1, false);
        }
        getArchiDest().cerrarArchivo();
    }

    /**
     * Muestra el contenido de un archivo (sin incluir los registros marcados como
     * borrados) 
     */
    public static void mostrarActivos() {

        getArchiDest().abrirParaLectura();
        getArchiDest().irPrincipioArchivo();
        getReg().mostrarRegistro(0, true);        
        while (!archiDest.eof()) {
            setReg(getArchiDest().leerRegistro());
            if(getReg().getEstado() == true)
            getReg().mostrarRegistro(1, true); /* En el caso de requerirse un modo determinado de impresión
            * por ejemplo en columnas, este método deberá organizar el titulo y encabezado del
            * listado y convocar a leerRegistro para luego mostrarlo de acuerdo a la solicitud
            * sin convocar al metodo mostrarRegistro */
        }
        getArchiDest().cerrarArchivo();
    }

    /**
     * Carga un registro de Destinos por teclado
     * @return 
     */
    public static Registro leerDestino() {
        Registro aux = new Registro();
        getDes().cargarDatos(0);
        aux.cargarNroOrden(getDes().getCodDest());
        aux.setDatos(getDes());
        aux.setEstado(true);
        return aux; /* Si utilizaramos la instancia reg, nos encontrariamos modificando
         * el espacio de memoria relacionado con la lectura del archivo y por lo tanto
         * cambiaria a medida que avanzaramos sobre el archivo. OJO CON ESTO
         */
    }
    
    /**
     *
     * @return
     */
    public static Registro traerDestino() {
        Registro aux = new Registro();
        System.out.println("Dar de alta Viajes para un Destino: ");
        getDes().cargarCodigoDestino();
        getReg().cargarNroOrden(getDes().getCodDest());
        getArchiDest().mostrarRegistro(getReg()); // Evitamos usar reg que ya está vinculado al archivo
//
        getArchiDest().abrirParaLectura();
        getArchiDest().irPrincipioArchivo();
        getReg().mostrarRegistro(0, true);
        while (!archiDest.eof()) {
            setReg(getArchiDest().leerRegistro());
            if (getReg().getEstado() == true) {
                getReg().mostrarRegistro(1, true); /* En el caso de requerirse un modo determinado de impresión
            * por ejemplo en columnas, este método deberá organizar el titulo y encabezado del
            * listado y convocar a leerRegistro para luego mostrarlo de acuerdo a la solicitud
            * sin convocar al metodo mostrarRegistro */
            }
        }
        getArchiDest().cerrarArchivo();

        return aux; /* Si utilizaramos la instancia reg, nos encontrariamos modificando
         * el espacio de memoria relacionado con la lectura del archivo y por lo tanto
         * cambiaria a medida que avanzaramos sobre el archivo. OJO CON ESTO
         */

    }
    
    /** Carga un Destinos (codigo y descripcion) hasta que el operador decida no continuar
     *
     */
    public static void cargarDestino() {
        Scanner entrada = new Scanner(System.in);
        String op;
        char opcion = 's';
        getArchiDest().abrirParaLeerEscribir(); 

        
        try {
            while (opcion != 'n') {
                setReg(leerDestino()); // se graba en el registro el cod cargado por teclado
                getArchiDest().altaRegistro(getReg()); // se agrega el registro nuevo en el archivo
                System.out.print("Continuar? s/n: ");
                op = entrada.next();
                op = (!op.equals("n")) ? "s" : op;
                opcion = op.charAt(0);
            }
        } catch (Exception e) {
            System.out.println("Error al cargar el archivo: " + e.getMessage());
            System.exit(1);
        }

        getArchiDest().cerrarArchivo();
    }

    /**
     * Crea el archivo de Rubros, el metodo es estatico
     */
        public static void crearArchivoDestinos() {
        try {
            String dir = "C:\\Users\\carod\\Documents\\NetBeansProjects\\";
            setArchiDest(new Archivo(dir + "Archivos-VIAJES", new Destinos())); // colocar el camino correcto del archivo que usemos
        } catch (ClassNotFoundException e) {
            System.out.println("Error al crear los descriptores de archivos: " + e.getMessage());
            System.exit(1);
        }
    }    
    
    public  void menuPrincipal() {
        int op;
        
        MenuDestinos y;
        y = new MenuDestinos();
        getArchiDest().abrirParaLeerEscribir();
        long cuantos = getArchiDest().cantidadRegistros();
        getArchiDest().cerrarArchivo();
        
        if(cuantos == 0)
            getArchiDest().crearArchivoVacio(new Registro(getDes(), 0));
        
        do {
            System.out.println("Actualizacion de Destinos");
            System.out.println("1. Altas");
            System.out.println("2. Bajas");
            System.out.println("3. Modificaciones");
            System.out.println("4. Listado");
  
            System.out.println("0. Salir");

            System.out.print("Ingrese opcion: ");
            op = Consola.leerEntero();
            switch (op) {
                case 1:
                    System.out.println("Ingrese los datos del registro de Destinos: ");
                    setReg(leerDestino());
                    getArchiDest().cargarUnRegistro(getReg());
                    break;

        
                case 2:
                    System.out.println("Ingrese el destino a borrar: ");
                    getDes().cargarCodigoDestino();    
                    getReg().cargarNroOrden(getDes().getCodDest());
                    getArchiDest().bajaRegistro(getReg());
                    break;

                case 3:
                    System.out.println("Ingrese el Rubro a modificar: ");
                    getDes().cargarCodigoDestino();    
                    getReg().cargarNroOrden(getDes().getCodDest());
                    getArchiDest().modificarRegistro(getReg()); // Evitamos usar reg que ya está vinculado al archivo
                    break;

                case 4:
                    System.out.println("Se muestra el archivo de Destinos (sin incluir 'borrados')...");
                    mostrarActivos();
                    break;

                case 0:
                    ;
            }
        } while (op != 0);
    }
}
