import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
public class PrimitiveUTFChars {

    public static void main(String[] args) {

    }

    int i = 0;
    int wUTF = 0;
    int stringSize_UTF = 0;
    int stringSize_CHAR = 0;
    File file = new File("C:\\Users\\David\\PrimitiveWriteUTFChars\\texto6.txt");

    {
        try {

            FileOutputStream output = null;
            try {
                output = new FileOutputStream(file);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            FileInputStream input = null;
            try {
                input = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            DataOutputStream escribirArchivo = new DataOutputStream(output);
            DataInputStream lerArchivo = new DataInputStream(input);

            do {

                try {

                    switch (wUTF) {
                        case 0:
                            escribirArchivo.writeUTF("Está en casa");
                            if (stringSize_UTF == 0) {
                                stringSize_UTF = escribirArchivo.size();
                            }
                            System.out.println("Escribimos por writeUTF: " + escribirArchivo.size());
                            wUTF = 1;
                            break;
                        case 1:
                            escribirArchivo.writeChars("Está en casa");
                            if (stringSize_CHAR == 0) {
                                stringSize_CHAR = (escribirArchivo.size() - stringSize_UTF) / 2;
                            }
                            System.out.println("StringSizeChars: " + stringSize_CHAR);
                            System.out.println("Escribimos por writeChars: " + escribirArchivo.size());
                            wUTF = 0;
                            break;
                    }

                } catch (IOException ex) {
                    Logger.getLogger(PrimitiveUTFChars.class.getName()).log(Level.SEVERE, null, ex);
                }

                i++;

            } while (i < 3);
            wUTF = 0;

            try {
                while (lerArchivo.available() != 0) {

                    switch (wUTF) {
                        case 0:
                            System.out.println(lerArchivo.readUTF());
                            wUTF = 1;
                            break;
                        case 1:
                            int j = 0;
                            while (j < stringSize_CHAR) {
                                System.out.print(lerArchivo.readChar());
                                j++;
                            }
                            wUTF = 0;
                            break;
                    }
                    System.out.println("Quedan por leer " + lerArchivo.available() + " bytes");

                }

                System.out.println("Ya no queda nada por leer");

            } catch (IOException ex) {
                Logger.getLogger(PrimitiveUTFChars.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }


    }
}