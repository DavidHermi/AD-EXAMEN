import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, XMLStreamException {
        File archivo  = new File("autores.xml");
        XMLOutputFactory obx = XMLOutputFactory.newInstance();
        XMLStreamWriter xml = obx.createXMLStreamWriter(new FileWriter(archivo));

 xml.writeStartDocument("1.0") ;
        xml.writeStartElement("autores");
       xml.writeStartElement("autor");
       xml.writeAttribute("codigo","t1");
       xml.writeStartElement("Nome");
       xml.writeCharacters("Alexander dumas");
       xml.writeEndElement();
        xml.writeStartElement("titulo");
       xml.writeCharacters("El conde de montecristo");
        xml.writeEndElement();
        xml.writeStartElement("titulo");
        xml.writeCharacters("Los miserables");
        xml.writeEndElement();
        xml.writeEndElement();
        xml.writeStartElement("autor");
        xml.writeAttribute("codigo","t2");
        xml.writeStartElement("Nome");
        xml.writeCharacters("Fiodor Dostoyevski");
        xml.writeEndElement();
        xml.writeStartElement("titulo");
        xml.writeCharacters("El idiota");
        xml.writeEndElement();
        xml.writeStartElement("titulo");
        xml.writeCharacters("Noches blancas");
        xml.writeEndElement();
        xml.writeEndElement();
        xml.writeEndElement();
        xml.close();





    }
}