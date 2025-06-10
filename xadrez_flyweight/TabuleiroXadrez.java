// Classe que irá instanciar o tabuleiro de xadrez (criação do tabuleiro, posicionamento das peças, regras do jogo)
// Possui os métodos necessários para o funcionamento de uma partida de xadrez (gerenciamento de turnos e movimentos de cada jogador)

import java.util.ArrayList;
import java.util.List;

public class TabuleiroXadrez {
    private List<PosicaoPeca> pecasTabuleiro = new ArrayList<>();
    private PecaXadrez[][] tabuleiro = new PecaXadrez[8][8];
    private String vezAtual = "Branco"; 
    
    

    public void montarTabuleiro() {
        String[] pecasBrancas = {"Torre", "Cavalo", "Bispo", "Dama", "Rei", "Bispo", "Cavalo", "Torre"};
        String[] pecasPretas = {"Torre", "Cavalo", "Bispo", "Dama", "Rei", "Bispo", "Cavalo", "Torre"};
    
        
        for (int i = 0; i < 8; i++) {
            String coluna = String.valueOf((char) ('a' + i));
            String posicao = coluna + "1";
            PecaXadrez peca = FabricaPecas.getPeca(pecasBrancas[i], "Branco"); // Aqui ele aciona a classe FabricaPecas, acessa o repositório de peças criando o objeto ou retornando a referência caso o objeto já exista
            pecasTabuleiro.add(new PosicaoPeca(posicao, peca)); // Aqui ele atribui uma posição inicial para cada instância de PecaXadrez, aplicando o atributo extrínseco.
            colocarPeca(posicao, pecasBrancas[i], "Branco");
        }
    
        
        for (int i = 0; i < 8; i++) {
            String coluna = String.valueOf((char) ('a' + i));
            String posicao = coluna + "2";
            PecaXadrez peca = FabricaPecas.getPeca("Peão", "Branco");
            pecasTabuleiro.add(new PosicaoPeca(posicao, peca));
            colocarPeca(posicao, "Peão", "Branco");
        }
    
        
        for (int i = 0; i < 8; i++) {
            String coluna = String.valueOf((char) ('a' + i));
            String posicao = coluna + "8";
            PecaXadrez peca = FabricaPecas.getPeca(pecasPretas[i], "Preto");
            pecasTabuleiro.add(new PosicaoPeca(posicao, peca));
            colocarPeca(posicao, pecasPretas[i], "Preto");
        }
    
        
        for (int i = 0; i < 8; i++) {
            String coluna = String.valueOf((char) ('a' + i));
            String posicao = coluna + "7";
            PecaXadrez peca = FabricaPecas.getPeca("Peão", "Preto");
            pecasTabuleiro.add(new PosicaoPeca(posicao, peca));
            colocarPeca(posicao, "Peão", "Preto");
        }
    }
    

    public void exibirTabuleiro() { // Exibe o todas as peças em suas respectivas posições
        for (PosicaoPeca posicao : pecasTabuleiro) {
            posicao.exibir();
        }
    }

    private int[] converterPosicao(String posicao) { // Converte a posição (passada como string) de cada peça para a posição funcional de um tabuleiro de xadrez
        int coluna = posicao.charAt(0) - 'a'; 
        int linha = 8 - Character.getNumericValue(posicao.charAt(1)); 
        return new int[]{linha, coluna};
    }

    private void colocarPeca(String posicao, String tipo, String cor) { // Posiciona cada peça em sua respectiva posição no tabuleiro de xadrez
        int[] indices = converterPosicao(posicao);
        tabuleiro[indices[0]][indices[1]] = FabricaPecas.getPeca(tipo, cor); // Acessa o repositório de peças para retornar a peça desejada para montar o tabuleiro corretamente
    }

    public void exibirTabuleiroVisual() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (tabuleiro[i][j] != null) {
                    System.out.print("[ " + tabuleiro[i][j].getTipo().charAt(0) + " ]");
                } else {
                    System.out.print("[   ]");
                }
            }
            System.out.println(" " + (8 - i));
        }
        System.out.println(" a  b  c  d  e  f  g  h");
    }
    

    public boolean moverPeca(String origem, String destino) {
        int[] origemIdx = converterPosicao(origem);
        int[] destinoIdx = converterPosicao(destino);
        PecaXadrez peca = tabuleiro[origemIdx[0]][origemIdx[1]];
        
        if (peca == null) {
            System.out.println("Nenhuma peça na posição " + origem);
            return false;
        }

        if (!peca.getCor().equals(vezAtual)) {
            System.out.println("Não é a vez da cor " + peca.getCor());
            return false;
        }

        if (!peca.movimentoValido(origemIdx, destinoIdx, tabuleiro)) {
            System.out.println("Movimento inválido de " + peca.getTipo());
            return false;
        }

        PecaXadrez capturada = tabuleiro[destinoIdx[0]][destinoIdx[1]];
        tabuleiro[destinoIdx[0]][destinoIdx[1]] = peca;
        tabuleiro[origemIdx[0]][origemIdx[1]] = null;

        if (capturada != null) {
            System.out.println(peca.getTipo() + " (" + peca.getCor() + ") capturou " +
                    capturada.getTipo() + " (" + capturada.getCor() + ")");
        }

        String corAdversaria = vezAtual.equals("Branco") ? "Preto" : "Branco";
        String jogadorAtual = vezAtual; 
        
        if (estaEmXeque(corAdversaria)) {
            System.out.println("Xeque ao " + corAdversaria + "!");
        }
        
        vezAtual = corAdversaria;
        System.out.println("Vez atual: " + vezAtual);
        
        if (estaEmXequeMate(corAdversaria)) {
            System.out.println("Xeque-mate! Vitória de " + jogadorAtual);
        }

        return true;
    }

    private boolean estaEmXeque(String corDoRei) {
        int[] posRei = localizarRei(corDoRei);
        if (posRei == null) {
            System.out.println("Rei " + corDoRei + " não encontrado.");
            return false;
        }

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                PecaXadrez peca = tabuleiro[i][j];
                if (peca != null && !peca.getCor().equals(corDoRei)) {
                    if (peca.movimentoValido(new int[]{i, j}, posRei, tabuleiro)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    

    private int[] localizarRei(String cor) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                PecaXadrez peca = tabuleiro[i][j];
                if (peca != null && peca.getTipo().equals("Rei") && peca.getCor().equals(cor)) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    private boolean estaEmXequeMate(String corDoJogador) {
        if (!estaEmXeque(corDoJogador)) {
            return false; 
        }
    
        
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                PecaXadrez peca = tabuleiro[i][j];
                if (peca != null && peca.getCor().equals(corDoJogador)) {
                    int[] origem = {i, j};
                    for (int x = 0; x < 8; x++) {
                        for (int y = 0; y < 8; y++) {
                            int[] destino = {x, y};
                            if (peca.movimentoValido(origem, destino, tabuleiro)) {
                                
                                PecaXadrez capturada = tabuleiro[x][y];
                                tabuleiro[x][y] = peca;
                                tabuleiro[i][j] = null;
    
                                boolean aindaEmXeque = estaEmXeque(corDoJogador);
    
                                
                                tabuleiro[i][j] = peca;
                                tabuleiro[x][y] = capturada;
    
                                if (!aindaEmXeque) {
                                    return false; 
                                }
                            }
                        }
                    }
                }
            }
        }
    
       
        return true;
    }
    

    // Simulação de um xeque-mate pastor para mostrar o funcionamento do código
    public static void main(String[] args) {
        TabuleiroXadrez tabuleiro = new TabuleiroXadrez();
        tabuleiro.montarTabuleiro();
        tabuleiro.exibirTabuleiro();
        tabuleiro.exibirTabuleiroVisual();

        tabuleiro.moverPeca("e2", "e4");
        tabuleiro.exibirTabuleiroVisual();

        tabuleiro.moverPeca("d1", "h5");
        tabuleiro.exibirTabuleiroVisual();

        tabuleiro.moverPeca("e7", "e5");
        tabuleiro.exibirTabuleiroVisual();

        tabuleiro.moverPeca("d1", "h5");
        tabuleiro.exibirTabuleiroVisual();

        tabuleiro.moverPeca("b8", "c6");
        tabuleiro.exibirTabuleiroVisual();

        tabuleiro.moverPeca("f1", "c4");
        tabuleiro.exibirTabuleiroVisual();

        tabuleiro.moverPeca("g8", "f6");
        tabuleiro.exibirTabuleiroVisual();

        tabuleiro.moverPeca("h5", "f7");
        tabuleiro.exibirTabuleiroVisual();
    }
}
