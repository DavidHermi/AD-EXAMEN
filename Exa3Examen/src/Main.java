

import java.io.*;
import java.sql.*;

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


    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
        File archivo = new File("/home/oracle/Documentos/ExExame3/reservas");
        FileInputStream lec = new FileInputStream(archivo);
        ObjectInputStream lectura = new ObjectInputStream(lec);

        Reserva aux = new Reserva();

        ResultSet rs;
        ResultSet rsIda;
        ResultSet rsVolta;
        PreparedStatement ps;
        PreparedStatement psInserir;
        String nome = "";
        int prezoIda = 0;
        int prezoVolta = 0;

        while ((aux = (Reserva) lectura.readObject()) != null) {
            System.out.println(aux.toString());
            Statement st = conexion().createStatement();
            rs = st.executeQuery("SELECT * from pasaxeiros WHERE dni ='" + aux.getDni() + "'");
            while (rs.next()) {
                int nreservas = rs.getInt("nreservas");
                nome = rs.getString("nome");
                int reservaAñadida = nreservas + 1;
                ps = conexion().prepareStatement("UPDATE pasaxeiros SET nreservas =" + reservaAñadida + "WHERE dni ='" + aux.getDni() + "'");
                ps.executeUpdate();
            }

            Statement stIda = conexion().createStatement();
            rsIda = stIda.executeQuery("SELECT * from voos WHERE voo ='" + aux.getIdvooida() + "'");

            while (rsIda.next()) {
                prezoIda = rsIda.getInt("prezo");
            }

            Statement stVolta = conexion().createStatement();
            rsVolta = stVolta.executeQuery("SELECT * from voos WHERE voo ='" + aux.getIdvoovolta() + "'");

            while (rsVolta.next()) {
                prezoVolta = rsVolta.getInt("prezo");
            }

            int prezoReserva = prezoIda + prezoVolta;
            System.out.println("Prezo da reserva: " + prezoReserva);

            psInserir = conexion().prepareStatement("INSERT INTO reservasfeitas (codr, dni, nome, prezoreserva) VALUES (?, ?, ?, ?)");
            psInserir.setInt(1, aux.getCodr());
            psInserir.setString(2, aux.getDni());
            psInserir.setString(3, nome);
            psInserir.setInt(4, prezoReserva);
            psInserir.executeUpdate();
        }
        conexion().close();
    }
}