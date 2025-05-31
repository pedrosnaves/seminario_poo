// Subclasse de PecaXadrez que instacia cada objeto torre criado

public class Torre extends PecaXadrez {
    public Torre(String cor) {
        super(cor);
    }

    
    public String getTipo() {
        return "Torre";
    }

    
    public boolean movimentoValido(int[] origem, int[] destino, PecaXadrez[][] tabuleiro) {
        int deltaLinha = destino[0] - origem[0];
        int deltaColuna = destino[1] - origem[1];

        
        if (deltaLinha != 0 && deltaColuna != 0) {
            return false; 
        }

    
        int passoLinha = Integer.compare(deltaLinha, 0); 
        int passoColuna = Integer.compare(deltaColuna, 0);

        int linha = origem[0] + passoLinha;
        int coluna = origem[1] + passoColuna;
        while (linha != destino[0] || coluna != destino[1]) {
            if (tabuleiro[linha][coluna] != null) {
                return false; 
            }
            linha += passoLinha;
            coluna += passoColuna;
        }

        
        return (tabuleiro[destino[0]][destino[1]] == null ||
                !tabuleiro[destino[0]][destino[1]].getCor().equals(this.cor));
    }
}
