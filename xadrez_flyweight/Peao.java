// Subclasse de PecaXadrez que instacia cada objeto peão criado

public class Peao extends PecaXadrez {
    public Peao(String cor) {
        super(cor);
    }

    
    public String getTipo() {
        return "Peão";
    }

    
    public boolean movimentoValido(int[] origem, int[] destino, PecaXadrez[][] tabuleiro) {
        int direcao = cor.equals("Branco") ? -1 : 1;
        int deltaLinha = destino[0] - origem[0];
        int deltaColuna = destino[1] - origem[1];
        int linhaInicial = cor.equals("Branco") ? 6 : 1;

        if (deltaColuna == 0 && deltaLinha == direcao && tabuleiro[destino[0]][destino[1]] == null) {
            return true;
        }
        if (deltaColuna == 0 && deltaLinha == 2 * direcao && origem[0] == linhaInicial &&
            tabuleiro[origem[0] + direcao][origem[1]] == null && tabuleiro[destino[0]][destino[1]] == null) {
            return true;
        }
        if (Math.abs(deltaColuna) == 1 && deltaLinha == direcao && tabuleiro[destino[0]][destino[1]] != null &&
            !tabuleiro[destino[0]][destino[1]].getCor().equals(cor)) {
            return true;
        }
        return false;
    }
}
