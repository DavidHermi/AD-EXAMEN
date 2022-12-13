package Exa17brevep;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Main {

    public static Connection conexion() throws SQLException {
        String driver = "jdbc:postgresql:";
        String host = "//localhost:";
        String porto = "5432";
        String sid = "postgres";
        String usuario = "postgres";
        String password = "postgres";
        String url = driver + host + porto + "/" + sid;
        Connection conn = DriverManager.getConnection(url, usuario, password);
        return conn;
    }

    public static void main(String[] args) throws FileNotFoundException, XMLStreamException, SQLException {
        File arquivoXML = new File("/home/oracle/Documentos/ExExame2/pedidos.xml");
        XMLInputFactory fc = XMLInputFactory.newInstance();
        XMLStreamReader xml = fc.createXMLStreamReader(new FileReader(arquivoXML));


        ResultSet rsStock;
        ResultSet rsGasto;
        PreparedStatement psStock;
        PreparedStatement psGasto;
        PreparedStatement psInserir;
        String codp = "";
        String codc = "";
        int gastoCliente = 0;

        while (xml.hasNext()) {
            xml.next();
            if (xml.getEventType() == XMLStreamConstants.START_ELEMENT) {

                String etiqueta = xml.getLocalName();

                if (etiqueta.equals("Pedido")) {

                    System.out.println("Código de cliente" + " --> " + xml.getAttributeValue(0));
                    codc = xml.getAttributeValue(0);
                    System.out.println("Código de producto" + " --> " + xml.getAttributeValue(1));
                    codp = xml.getAttributeValue(1);

                } else if (etiqueta.equals("Cantidade")) {

                    int cantidad = Integer.parseInt(xml.getElementText());
                    System.out.println(etiqueta + " --> " + cantidad);
                    Statement stStock = conexion().createStatement();
                    rsStock = stStock.executeQuery("SELECT * from produtos WHERE codigop ='" + codp + "'");
                    while (rsStock.next()) {

                        //Diminuir stock

                        int stock = rsStock.getInt("stock");
                        int nuevoStock = stock - cantidad;
                        psStock = conexion().prepareStatement("UPDATE produtos SET stock =" + nuevoStock + "WHERE codigop ='" + codp + "'");
                        psStock.executeUpdate();
                        System.out.println("Stock: " + stock);
                        System.out.println("Nuevo Stock: " + nuevoStock);

                        //Aumentar o gasto

                        int prezo = rsStock.getInt("prezo");
                        gastoCliente = prezo * cantidad; //gasto del cliente (precio por cantidad)
                        System.out.println("Gasto cliente: " + gastoCliente);
                    }

                    Statement stGasto = conexion().createStatement();
                    rsGasto = stGasto.executeQuery("SELECT * from clientes WHERE codigoc ='" + codc + "'");
                    while (rsGasto.next()) {
                        int gastoActual = rsGasto.getInt("gasto"); //gasto que hay ahora mismo en la tabla
                        int gastoActualizado = gastoActual + gastoCliente; //gasto que hay en la tabla sumado al del cliente
                        psGasto = conexion().prepareStatement("UPDATE clientes SET gasto = " + gastoActualizado + " WHERE codigoc = '" + codc + "'");
                        psGasto.executeUpdate();
                        System.out.println("Gasto actual: " + gastoActual);
                        System.out.println("Nuevo gasto: " + gastoActualizado);
                    }


                } else if (etiqueta.equals("Data")) {

                    String data = xml.getElementText();
                    System.out.println(etiqueta + " --> " + data);

                    psInserir = conexion().prepareStatement("INSERT INTO vendas (codigoc, codigop, data, total) VALUES (?, ?, ?, ?)");

                    psInserir.setString(1, codc);
                    psInserir.setString(2, codp);



                    psInserir.setString(3, data);
                    psInserir.setInt(4, gastoCliente);
                    psInserir.executeUpdate();

                    System.out.println("\n");

                }
            }
        }
        xml.close();
        conexion().close();
    }
}