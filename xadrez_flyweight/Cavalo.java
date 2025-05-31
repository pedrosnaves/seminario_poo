// Subclasse de PecaXadrez que instacia cada objeto cavalo criado

public class Cavalo extends PecaXadrez {
    public Cavalo(String cor) {
        super(cor);
    }

    
    public String getTipo() {
        return "Cavalo";
    }

    
    public boolean movimentoValido(int[] origem, int[] destino, PecaXadrez[][] tabuleiro) {
        int deltaLinha = Math.abs(destino[0] - origem[0]);
        int deltaColuna = Math.abs(destino[1] - origem[1]);

        
        boolean movimentoValido = (deltaLinha == 2 && deltaColuna == 1) || (deltaLinha == 1 && deltaColuna == 2);

        if (!movimentoValido) {
            return false;
        }

        
        return (tabuleiro[destino[0]][destino[1]] == null ||
                !tabuleiro[destino[0]][destino[1]].getCor().equals(this.cor));
    }
}
