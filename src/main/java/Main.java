import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        PedidosDAO dao = new PedidosDAO();
        Scanner sc = new Scanner(System.in);
        int opcion = 0;
        while (opcion!=6) {
            System.out.println("ELIGE UNA DE LAS SIGUIENTES OPCIONES:");
            System.out.println("1. Crear nuevo pedido.");
            System.out.println("2. Eliminar un pedido existente.");
            System.out.println("3. Marcar pedido como recogido.");
            System.out.println("4. Comandas pendientes para hoy.");
            System.out.println("5. Ver la carta.");
            System.out.println("/////////////////////////////////////");
            System.out.println("6. Salir del programa.");
            opcion = sc.nextInt();
            switch (opcion) {
                case 1-> {
                    dao.crearPedido();
                    break;
                }
                case 2-> {
                    dao.eliminarPedido();
                    break;
                }
                case 3-> {
                    dao.marcarComoRecogido();
                    break;
                }
                case 4-> {
                    dao.comandasDiariasPendientes();
                    break;
                }
                case 5-> {
                    dao.listarCarta();
                    System.out.println();
                    break;
                }
                case 6-> System.exit(0);
            }
        }
    }


}
