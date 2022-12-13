import javax.xml.stream.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, XMLStreamException {

        File archivo = new File("C:\\Users\\David\\Desktop\\AD\\xmlproba0\\autores.xml");
        XMLInputFactory lectura = XMLInputFactory.newInstance();
        XMLStreamReader xml = lectura.createXMLStreamReader(new FileReader(archivo));


        while (xml.hasNext()) {

            xml.next();
            String aux = "";

            if (xml.getEventType() == XMLStreamConstants.START_ELEMENT) {


                if (xml.getLocalName() == "autor") {
                    aux = xml.getAttributeValue(0);
                }

                else if (xml.getLocalName().equals("Nome")  || xml.getLocalName().equals("titulo") ) {

                    aux = xml.getElementText();
                }
            }
            System.out.println(aux);
        }


    }


}
