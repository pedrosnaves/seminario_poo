// Classe abstrata para instanciar as peças do xadrez

public abstract class PecaXadrez {
    protected String cor;

    public PecaXadrez(String cor) {
        this.cor = cor;
    }

    public String getCor() {
        return cor;
    }

    public abstract String getTipo();
    public abstract boolean movimentoValido(int[] origem, int[] destino, PecaXadrez[][] tabuleiro);
}
