package datos;

/**
 * Representa un artículo a la venta en un comercio cualquiera, que podrá ser
 * usado dentro de un Registro para grabar en un Archivo
 *
 */
import entradaDatos.Consola;
import java.io.*;
import persistencia.*;

public class Destinos implements Grabable {

    private int codDest; // 4 bytes 
    private String Den; //  una cadena, que queremos sea de 30 caracteres máximo = 60 bytes en disco 


    private static int TAMARCHIVO = 100; // cantidad de registros que tendra el archivo
    private static int TAMAREG = 64; // cantidad de bytes que tendrá el registro
    // Den 100 bytes + codigo de artico 4 bytes + codigo de rubro 4 bytes + precio 4 bytes
    // este calculo debe realizarse para toda clase que pase a integrar un registro de archivo
 
    /**
     * Constructor por defecto. Los atributos quedan con valores por default
     */
    public Destinos() {
	        codDest       = 0;
	        Den     = "  ";
	      
    }


    /**
     * Constructor. Inicializa cada atributo de acuerdo a los parámetros
     */
    public Destinos(int codDest, String Den) {
        this.codDest = codDest;
        this.Den = Den;
    }
 
    

    /**
     * Accede al valor de la descripción
     *
     * @return el valor del atributo Den
     */
    public String getDen() {
        return Den;
    }


    /**
     * Cambia el valor de la Den
     *
     * @param nom el nuevo valor del atributo Den
     */
    public void setDen(String nom) {
        Den = nom;
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
     * Carga el codigo de un Destinos
     */
    public void cargarCodigoDestino() {
        System.out.println("Ingrese el codigo del Destino: ");
        int codi = Consola.leerEntero();
        setCodDest(codi);
    }

   

    /**
     * Carga la Den de un Destinos
     */
    public void cargarDescripcion() {
        System.out.println("Ingrese la descripción del Destino: ");
        String nombre = Consola.leerString();
        setDen(nombre);
    }
    
    /**
     * Carga la informacion de un Rub
     */
    @Override
    public void cargarDatos(int val) {
        if(val == 0)
            cargarCodigoDestino();
        cargarDescripcion();
     
    }    

    /**
     * Indica cómo grabar un objeto. Pedido por Grabable.
     *
     * @param a el archivo donde será grabado el objeto
     */
    @Override
    public void grabar(RandomAccessFile a) {
        try {
            a.writeInt(codDest);
            Registro.writeString(a, Den, 30);
            // el metodo es estatico se convoca con el nombre de la clase
            
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
                codDest= a.readInt();
            Den = Registro.leerString(a, 30).trim();
         
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

        Destinos a = (Destinos) x;
        return (codDest == a.codDest);
    }

    /**
     * Redefinición del heredado desde Object. La convención es si equals() dice
     * que dos objetos son iguales, entonces hashCode() debería retornar el
     * mismo valor para ambos.
     *
     * @return el hashCode del Articulo. Se eligió el código para ese valor.
     */
    @Override
    public int hashCode() {
        return codDest;
    }

    /**
     * Redefinición del heredado desde Object.
     * Puede redefinirse segun lo que se necesite en el problema
     *
     * @return una cadena representando el contenido del objeto.
     */
    @Override
    public String toString() {
//"      Cod.Destinos    Descripcion                                          "
//"------------01--------012345678901234567890123456789"
// la linea anterior es la guia de como organizar la salida
// 4 espacios al comienzo luego el rubro, luego cinco espacios, y la Den
        return "            " + codDest + "        " + Den;
    }
    
    /**
     * Muestra un registro por pantalla
     */

    @Override
    public void mostrarRegistro(int val, boolean activo) {
        if (val == 0) {
            System.out.println("      Cod.Destino    Descripcion                                          ");
        }
        if (val == 1 && activo) {
            System.out.println(toString());
        }
        if (val == 2 && activo) {
            System.out.println("Destino: ("+ getCodDest() + ", " + getDen() + ")");
        }
    }

    /**
     * Devuelve el codigo de Destinos 
     */
    public int getCodDest() {
        return codDest;
    }

    /**
     * @param codDest the codDest to set
     */
    public void setCodDest(int codDest) {
        this.codDest = codDest;
    }
}