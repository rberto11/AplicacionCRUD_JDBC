import models.Carta;
import models.Pedidos;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class PedidosDAO {
    // Conexión base de datos
    private static Connection con;

    private static String url = "jdbc:mysql://localhost:3307/gestion_productos";
    private static String user = "root";
    private static String password = "";

    // Consultas SQL
    private final  String selectAllCarta = "SELECT * FROM carta";
    private final  String selectAllPedidos = "SELECT * FROM pedidos";
    private final  String insertPedido = "INSERT INTO gestion_productos.pedidos (nombre_cliente, fecha, estado, producto_id) VALUES (?, ?, ?, ?)";
    private final  String deletePedido = "DELETE FROM gestion_productos.pedidos WHERE id = ?";
    private final  String updateEstado = "UPDATE gestion_productos.pedidos SET estado = '1' WHERE id = ?";
    private final  String pendientesHoy = "SELECT * FROM gestion_productos.pedidos WHERE fecha = CURDATE() and estado= 0 ";

    // Variables varias
    private  ArrayList<Carta> cartaCompleta = new ArrayList<>();

    static Date ahora = new Date();
    static java.sql.Date sqlFecha = new java.sql.Date(ahora.getTime());

    private  Carta productos = new Carta();
    private  Carta c;


    static {
        try {
            con = DriverManager.getConnection(url, user, password);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public PedidosDAO() {
        try (Statement st = con.createStatement()){

            ResultSet resultado = st.executeQuery(selectAllCarta);
            while (resultado.next()) {
                Carta c = new Carta();
                c.setId(resultado.getInt("id"));
                c.setNombre(resultado.getString("nombre"));
                c.setIngredientes(resultado.getString("ingredientes"));
                c.setPrecio(resultado.getString("precio"));

                cartaCompleta.add(c);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public  void listarCarta() {
        cartaCompleta.forEach(System.out::println);
    }

    public  void comandasDiariasPendientes() {
        try (Statement st = con.createStatement()) {
            ResultSet resultado = st.executeQuery(pendientesHoy);
            while (resultado.next()) {
                Pedidos p = new Pedidos();
                p.setId(resultado.getInt("id"));
                p.setNombre_cliente(resultado.getString("nombre_cliente"));
                p.setFecha(resultado.getDate("fecha"));
                p.setEstado(resultado.getBoolean("estado"));
                int id = resultado.getInt("producto_id");
                cartaCompleta.forEach(e -> {
                    if (e.getId()==id) c = e;
                });
                p.setProductos(c);
                System.out.println(p);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public  void marcarComoRecogido() {
        Scanner sc = new Scanner(System.in);
        System.out.println("/////////////////////////////////////////");
        comandasDiariasPendientes();
        System.out.println("/////////////////////////////////////////");
        System.out.println("Introduce el ID del pedido a cambiar de estado:");
        int idCambiar = sc.nextInt();
        try (PreparedStatement pst = con.prepareStatement(updateEstado)) {
            pst.setInt(1, idCambiar);
            if (pst.executeUpdate()==1) {
                System.out.println("El estado del pedido "+idCambiar+" ha sido cambiado.");
            } else {
                System.out.println("Selecciona un pedido existente.");
                marcarComoRecogido();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void eliminarPedido() {
        Scanner sc = new Scanner(System.in);
        System.out.println("/////////////////////////////////////////");
        comandasDiariasPendientes();
        System.out.println("/////////////////////////////////////////");
        System.out.println("Selecciona el ID de una tarea para eliminarlo:");
        int idEliminar = sc.nextInt();
        try (PreparedStatement pst = con.prepareStatement(deletePedido)) {
            pst.setInt(1, idEliminar);
            if (pst.executeUpdate()==1) {
                System.out.println("Su pedido con id "+idEliminar+" ha sido eliminado correctamente.");
            } else {
                System.out.println("Su pedido con id "+idEliminar+" no existe.\n" +
                        "Pruebe a eliminar otro.");
                eliminarPedido();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void crearPedido() {
        Scanner sc = new Scanner(System.in);
        Pedidos pedido = new Pedidos();
        System.out.println("Introduce nombre:");
        pedido.setNombre_cliente(sc.nextLine());
        pedido.setEstado(false);
        pedido.setProductos(añadirProductosPedido());
        try (PreparedStatement pst = con.prepareStatement(insertPedido)) {
            pst.setString(1, pedido.getNombre_cliente());
            pst.setDate(2, sqlFecha);
            pst.setBoolean(3, pedido.getEstado());
            pst.setInt(4, pedido.getProductos().getId());
            if (pst.executeUpdate()==1) {
                System.out.println("Su pedido ha sido creado correctamente.");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public  Carta añadirProductosPedido() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduce el ID de un producto de la carta\n" +
                "para añadirlo a tu pedido:");
        int idBuscar = sc.nextInt();
        cartaCompleta.forEach(e -> {
            if (e.getId()==idBuscar){
                productos = e;
            }
        });
        return productos;
    }
}