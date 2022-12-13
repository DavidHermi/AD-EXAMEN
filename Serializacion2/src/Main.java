import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        Producto prod1 = new Producto( "p1","parafusos",3.00);
        Producto prod2 = new Producto( "p3","cravos",4.00);
        Producto prod3 = new Producto( "p3","tachas",5.00);


        File archivo = new File("seralizacion");
        ObjectOutputStream escribirArchivo = new ObjectOutputStream(new FileOutputStream(archivo));
        ObjectInputStream lerArquivo = new ObjectInputStream(new FileInputStream(archivo));


        System.out.println("Prod1 : " );
        escribirArchivo.writeObject(prod1);
        System.out.println("PECHA O ARCHIVO");


        System.out.println("Prod2 : " );
        escribirArchivo.writeObject(prod2);
        System.out.println("PECHA O ARCHIVO");


        System.out.println("Prod3 : " );
        escribirArchivo.writeObject(prod3);
        System.out.println("PECHA O ARCHIVO");
        escribirArchivo.close();


     int i = 0 ;

while (lerArquivo.available()!=0) {

    Producto aux = new Producto();




      aux= (Producto) lerArquivo.readObject();

    System.out.println(aux.toString());


}




        String[] cod={"p1","p2","p3"};
        String[] desc ={"parafusos","cravos ","tachas"};
        int[] prezo ={3,4,5};






    }
}