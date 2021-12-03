package datos;

/**
 * Representa un artículo a la venta en un comercio cualquiera, que podrá ser
 * usado dentro de un Registro para grabar en un Archivo
 *
 */
import entradaDatos.Consola;
import java.io.*;
import persistencia.*; 
import principal.*;

public class Viajes implements Grabable {

    private int cod_V; // 4 bytes 
    private String Fecha; // una cadena, que queremos sea de 8 caracteres máximo = 16 bytes en disco 
    private int cod_Dest; // 4
    private int tarifa; // 4
    private int hora; //  4
    private int cant_d; // 4

    private static int TAMARCHIVO = 100; // cantidad de registros que tendra el archivo
    private static int TAMAREG = 36; // cantidad de bytes que tendrá el registro
    // Fecha 100 bytes + codigo de artico 4 bytes + codigo de rubro 4 bytes + tarifa 4 bytes
    // este calculo debe realizarse para toda clase que pase a integrar un registro de archivo
 
    /**
     * Constructor por defecto. Los atributos quedan con valores por default
     */
    public Viajes() {
                cod_V           = 0;
	        Fecha     = "  ";                
	        cod_Dest       = 0;
	        tarifa          = 0;
                hora            =0;
	        cant_d          = 0;
    }



    /**
     * Constructor. Inicializa cada atributo de acuerdo a los parámetros
     */
    public Viajes(int cod_V, String Fecha, int cod_Dest, int tarifa, int hora, int cant_d) {
        this.cod_V = cod_V;
        this.Fecha = Fecha;
        this.cod_Dest = cod_Dest;
        this.tarifa = tarifa;
        this.hora = hora;
        this.cant_d = cant_d;
    }   
    
    /**
     * Accede al valor del cod_V
     *
     * @return el valor del atributo cod_V
     */
    public int getCod_V() {
        return cod_V;
    }

    /**
     * Accede al valor de la descripción
     *
     * @return el valor del atributo Fecha
     */
    public String getFecha() {
        return Fecha;
    }

    /**
     * Cambia el valor del cod_V
     *
     * @param c el nuevo valor del atributo cod_V
     */
    public void setCod_V(int c) {
        cod_V = c;
    }

    /**
     * Cambia el valor de la Fecha
     *
     * @param nom el nuevo valor del atributo Fecha
     */
    public void setFecha(String nom) {
        Fecha = nom;
    }

    /**
     * Accede al valor del codigo de Rubro
     *
     * @return el valor del atributo cod_Dest
     */
    public int getCod_Dest() {
        return cod_Dest;
    }

    /**
     * Cambia el valor del codigo de rubro
     *
     * @param cod_Dest el nuevo valor del atributo cod_Dest
     */
    public void setCod_Dest(int cod_Dest) {
        this.cod_Dest = cod_Dest;
    }

    /**
     * Accede al valor del tarifa
     *
     * @return el valor del atributo tarifa
     */
    public int getTarifa() {
        return tarifa;
    }

    /**
     * Cambia el valor de tarifa
     *
     * @param tarifa el nuevo valor del atributo tarifa
     */
    public void setTarifa(int tarifa) {
        this.tarifa = tarifa;
    }

    public int getHora() {
        return hora;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }

    public int getCant_d() {
        return cant_d;
    }

    public void setCant_d(int cant_d) {
        this.cant_d = cant_d;
    }

    
    /**
     * Calcula el tamaño en bytes del objeto, tal como será grabado. Pedido por
     * Grabable
     *
     * @return el tamaño en bytes del objeto
     */
    @Override
    public int tamRegistro() {
        return TAMAREG; 
    }

    /**
     * Devuelve la cantidad de registros que debe haber en el archivo. Pedido por
     * Grabable
     *
     * @return cantidad de registros
     */
    @Override
    public int tamArchivo() {
        return TAMARCHIVO; 
    }
    
    /**
     * Carga el codigo de articulo
     */
    public void cargarCodigo() {
        System.out.print("Ingrese el codigo: ");
        int codi = Consola.leerEntero();
        
        setCod_V(codi);
    }

    /**
     * Verifica el codigo de Rubro
     */
    public void cargarDestino() {
        boolean verificado = false;
        while (!verificado) {
            System.out.print("Ingrese el codigo de Destino: ");
            int codi = Consola.leerEntero();
            if (verifCodDestino(codi)) {
                setCod_Dest(codi);
                verificado = true;
            } else {
                System.out.println("El codigo de destino debe ser valido. . .");
            }
        }
    }
    
    /**
     * Verifica el codigo de rubro ingresado accediendo al archivo de rubros
     */
    public boolean verifCodDestino(int elDestino) {
        Registro regDestino;
        boolean resp = false;

        // Ahora verificamos si el archivo de Rubros existe
        // como el metodo es estatico no necesitamos declarar una instancia de ese tipo
        if (MenuViajes.existeArchivoDestinos()) {
            MenuDestinos.getArchiDest().abrirParaLectura();
            MenuDestinos.getArchiDest().irPrincipioArchivo();
            if (!MenuDestinos.getArchiDest().eof()) {
                MenuDestinos.getArchiDest().buscarRegistro(elDestino);
                if (!MenuDestinos.getArchiDest().eof()) {
                    regDestino = MenuDestinos.getArchiDest().leerRegistro();
                    resp = regDestino.getEstado();
                }
            }
            MenuDestinos.getArchiDest().cerrarArchivo();
        } else {
            System.out.println("El archivo de Destinos no existe. . .");
        }
        return resp;
    }

    /**
     * Carga la tarifa del viaje
     */
    public void cargarTarifa() {
        System.out.print("Ingrese la tarifa: ");
        int codi = Consola.leerEntero();
        setTarifa(codi);
    }

    /**
     * Carga la Fecha de articulo
     */
    public void cargarFecha() {
        System.out.print("Ingrese la fecha: ");
        String nombre = Consola.leerString();
        setFecha(nombre);
    }
    
    public void cargarHora() {
        System.out.print("Ingrese la hora: ");
        int hora = Consola.leerEntero();
        setHora(hora);
    }
    
    public void cargarCantDias() {
        System.out.print("Ingrese la cantidad de dias: ");
        int dias = Consola.leerEntero();
        setCant_d(dias);
    }
    
    
    
    /**
     * Carga la informacion de viaje
     * @param val
     */
    @Override
    public void cargarDatos(int val) {
        if(val == 0)
            cargarCodigo(); // si estamos en modificacion no cargamos el codigo que es la clave
        cargarDestino();
        cargarFecha();
        cargarHora();
        cargarTarifa();
        cargarCantDias();
        
    }    

    /**
     * Indica cómo grabar un objeto. Pedido por Grabable.
     *
     * @param a el archivo donde será grabado el objeto
     */
    @Override
    public void grabar(RandomAccessFile a) {
        try {
            a.writeInt(cod_V);
            Registro.writeString(a, Fecha, 8);
            // el metodo es estatico se convoca con el nombre de la clase
            a.writeInt(getCod_Dest());
            a.writeInt(getTarifa());
            a.writeInt(getHora());
            a.writeInt(getCant_d());
        } catch (IOException e) {
            System.out.println("Error al grabar el registro: " + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Indica cómo leer un objeto. Pedido por Grabable.
     *
     * @param a el archivo donde se hará la lectura
     */
    @Override
    public void leer(RandomAccessFile a, int val) {
        try {
            if(val == 0)
                cod_V = a.readInt();
            Fecha = Registro.leerString(a, 8).trim();
            setCod_Dest(a.readInt());
            setTarifa(a.readInt());
            setCant_d(a.readInt());
            setHora(a.readInt());
        } catch (IOException e) {
            System.out.println("Error al leer el registro: " + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Redefinición del heredado desde Object. Considera que dos Articulos son
     * iguales si sus códigos lo son
     *
     * @param x el objeto contra el cual se compara
     * @return true si los códigos son iguales, false en caso contrario
     */
    @Override
    public boolean equals(Object x) {
        if (x == null) {
            return false;
        }

        Viajes a = (Viajes) x;
        return (cod_V == a.cod_V);
    }

    /**
     * Redefinición del heredado desde Object. La convención es si equals() dice
     * que dos objetos son iguales, entonces hashCode() debería retornar el
     * mismo valor para ambos.
     *
     * @return el hashCode del Viajes. Se eligió el código para ese valor.
     */
    @Override
    public int hashCode() {
        return cod_V;
    }

    /**
     * Redefinición del heredado desde Object.
     * Puede redefinirse segun lo que se necesite en el problema
     *
     * @return una cadena representando el contenido del objeto.
     */
    @Override
    public String toString() {
//"           Cod.Art.    Descripcion                                                 Precio"
//"------------01--------012345678901234567890123456789------012345"
// la linea anterior es la guia de como organizar la salida
// 4 espacios al comienzo luego el rubro, luego cinco espacios, y la Fecha
        return "            " + cod_V + "             " + Fecha + "             " +getHora()+"            "+ getTarifa();
    }
    
    /**
     * Muestra un registro por pantalla
     */

    @Override
    public void mostrarRegistro(int val, boolean activo) {
        if (val == 0) {
            System.out.println("           Cod.Viaje         Fecha          Hora          Tarifa");
        }
        if (val == 1) {
            System.out.println(toString());
        }
        if (val == 2) {
            System.out.println("Viaje: ("+ getCod_V() + ", " + getFecha() + ")");
        }
    }
}