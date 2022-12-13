import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        Product producto = new Product("cod1", "parafusos", 3.00) ;
        Product productodous = new Product("cod2", "cravos", 4.00) ;
        Product po3 = new Product("cod3","martillo",6.00) ;
        File archivo = new File("C:\\Users\\David\\producto") ;
        DataOutputStream esribirArchivo = new DataOutputStream (new FileOutputStream(archivo)) ;
        DataInputStream lerArquivo = new DataInputStream( new FileInputStream(archivo));

    System.out.println("Escribiendo datos: gardando codigo y descripcion  ");
    esribirArchivo.writeUTF(producto.getCodigo());
    esribirArchivo.writeUTF(producto.getDescricion());
    esribirArchivo.writeDouble(producto.getPrezo());
    esribirArchivo.writeUTF(productodous.getCodigo());
    esribirArchivo.writeUTF(productodous.getDescricion());
    esribirArchivo.writeDouble(productodous.getPrezo());
        esribirArchivo.writeUTF(po3.getCodigo());
        esribirArchivo.writeUTF(po3.getDescricion());
        esribirArchivo.writeDouble(po3.getPrezo());
    System.out.println("Tamaño do ficheiro"+ esribirArchivo.size());
    esribirArchivo.close();


int i ;
while((i = lerArquivo.available())!= 0) {

    System.out.println("quedan por ler" + i + "bytes"  );
    String cadea = lerArquivo.readUTF();
    String cadea2 = lerArquivo.readUTF();
    Double leerdouble2 = lerArquivo.readDouble();
    System.out.println(cadea);
    System.out.println(cadea2);
    System.out.println(leerdouble2);

}
System.out.println("Xa no queda nada por ler");
lerArquivo.close();

    }
}