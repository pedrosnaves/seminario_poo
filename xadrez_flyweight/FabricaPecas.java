// Classe responsável por gerenciar o repositório de peças de xadrez
// Usa o Flyweight para evitar a criação de múltiplas instâncias da mesma peça

import java.util.HashMap;
import java.util.Map;

public class FabricaPecas {
    private static Map<String, PecaXadrez> pool = new HashMap<>();

    public static PecaXadrez getPeca(String tipo, String cor) {
        String chave = tipo + cor;
        if (!pool.containsKey(chave)) {
            switch (tipo) {
                case "Peão": pool.put(chave, new Peao(cor)); break;
                case "Torre": pool.put(chave, new Torre(cor)); break;
                case "Cavalo": pool.put(chave, new Cavalo(cor)); break;
                case "Bispo": pool.put(chave, new Bispo(cor)); break;
                case "Dama": pool.put(chave, new Dama(cor)); break;
                case "Rei": pool.put(chave, new Rei(cor)); break;
                default: throw new IllegalArgumentException("Tipo de peça inválido: " + tipo);
            }
        }
        return pool.get(chave);
    }
}
