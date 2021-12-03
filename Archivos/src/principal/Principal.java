package principal;

import entradaDatos.*; 

public class Principal { 
    
    
    public static void main(String[] args) {
        
        int op;

        MenuViajes mVia=new MenuViajes(); // mVia es un objeto de tipo MenuViajes
        MenuDestinos mDes=new MenuDestinos();
        
        do {
            System.out.println("Menu Principal");
            System.out.println("1.Actualizacion de Viajes");
            System.out.println("2.Actualizacion de Destinos");
           
            System.out.println("0.Salir");

            System.out.print("Ingrese opcion: ");
            op = Consola.leerEntero();
            System.out.println(" ");
            switch (op) {
                case 1:
                     mVia.menuPrincipal(); // Se llama a menuPrincipal de la clase MenuAticulo instanciado por mVia 
                    //MenuDestinos.cargarDestino(); // el metodo cargarDestino de MenuRubo al ser estatico no necesita ser instanciado por un objeto
                    break; // Los metodos o variables estaticas se las accede por medio de la clase 
                case 2:
                    mDes.menuPrincipal();
                    break;
                case 0:
                    ;
            }
        } while (op != 0);
    }
}