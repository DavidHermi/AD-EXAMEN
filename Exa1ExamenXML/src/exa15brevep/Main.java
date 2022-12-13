package exa15brevep;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.*;
import java.sql.*;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException, XMLStreamException {

        //archivo platos

        File arquivo = new File("/home/oracle/Documentos/ExExame1/platoss");
        FileInputStream lec = new FileInputStream(arquivo);
        ObjectInputStream lectura = new ObjectInputStream(lec);

        //archivo XML

        File arquivoXML = new File("/home/oracle/Documentos/ExExame1XML/totalgraxas.xml");
        XMLOutputFactory fc = XMLOutputFactory.newInstance();
        XMLStreamWriter xml = fc.createXMLStreamWriter(new FileWriter(arquivoXML));

        //objeto tipo plato

        Platos aux = new Platos();

        //archivo XML

        xml.writeStartDocument("1.0");
        xml.writeStartElement("Platos");

        //BASE DE DATOS

        Statement speso = conexion().createStatement();
        Statement sgraxa = conexion().createStatement();

        ResultSet rsPeso;
        ResultSet rsGraxa;

        while ((aux = (Platos) lectura.readObject()) != null) {
            System.out.println("CÃ³digo: " + aux.getCodigop() + " Nombre: " + aux.getNomep() + "\n");
            rsPeso = speso.executeQuery("SELECT * FROM composicion WHERE codp ='" + aux.getCodigop() + "'");

            //XML

            xml.writeStartElement("Plato");
            xml.writeAttribute("Codigop", aux.getCodigop());
            xml.writeStartElement("nomep");
            xml.writeCharacters(aux.getNomep());
            xml.writeEndElement();
            xml.writeStartElement("graxatotal");

            //

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

            //XML

            xml.writeCharacters(String.valueOf(graxa_total));
            xml.writeEndElement();
            xml.writeEndElement();
        }
        xml.writeEndElement();
        xml.writeEndDocument();
        xml.close();

        //

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
