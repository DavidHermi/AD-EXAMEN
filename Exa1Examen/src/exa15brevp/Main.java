package exa15brevp;

import exa15brevp.Platos;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.*;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
        File arquivo = new File("/home/oracle/Documentos/ExExame1/platoss");
        FileInputStream lec = new FileInputStream(arquivo);
        ObjectInputStream lectura = new ObjectInputStream(lec);
        Platos aux = new Platos();

        //BASE DE DATOS

        Statement speso = conexion().createStatement();
        Statement sgraxa = conexion().createStatement();

        ResultSet rsPeso;
        ResultSet rsGraxa;

        while ((aux = (Platos) lectura.readObject()) != null) {
            System.out.println("Código: " + aux.getCodigop() + " Nombre: " + aux.getNomep() + "\n");
            rsPeso = speso.executeQuery("SELECT * FROM composicion WHERE codp ='" + aux.getCodigop() + "'");
            int graxa_total = 0;
            int graxa_parcial = 0;

            while (rsPeso.next()) {

                String codc = rsPeso.getString("codc");
                int peso = rsPeso.getInt("peso");

                System.out.println("Peso Plato: " + peso + "g");

                rsGraxa = sgraxa.executeQuery("SELECT * FROM componentes where codc ='" + codc + "'");

                while (rsGraxa.next()) {

                    int graxa = rsGraxa.getInt("graxa");

                    System.out.println("Graxa Animal: " + graxa+ "%");

                    graxa_parcial = peso*graxa/100;

                    System.out.println("Graxa Parcial: " + graxa_parcial+"\n");
                }
                graxa_total = graxa_total + graxa_parcial;
            }
            System.out.println("Graxa Total: " + graxa_total + "\n");
        }
        conexion().close();
        lectura.close();
    }

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
}
