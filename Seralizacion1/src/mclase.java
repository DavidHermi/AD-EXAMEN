import java.io.Serializable;

public class mclase implements Serializable {

    private String nome ;
    private int numero1 ;
    private double numero2 ;

    public mclase(String nome, int numero1, double numero2) {
        this.nome = nome;
        this.numero1 = numero1;
        this.numero2 = numero2;
    }
}
