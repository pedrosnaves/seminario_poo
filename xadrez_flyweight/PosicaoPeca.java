//Classe para atribuir o atributo posicao a pecas do mesmo tipo e implementar o uso do Flyweight

public class PosicaoPeca {
    private PecaXadrez peca; // Atributo intrínseco -> Objeto peça, que possui atributos iguais para peças do mesmo tipo
    private String posicao; // Atributo extrínseco -> Atributo que irá diferenciar os objetos do mesmo tipo (Flyweight)

    public PosicaoPeca(String posicao, PecaXadrez peca) {
        this.posicao = posicao;
        this.peca = peca;
    }

    public void exibir() {
        System.out.println(peca.getTipo() + " (" + peca.getCor() + ") em " + posicao);
    }
    
}
