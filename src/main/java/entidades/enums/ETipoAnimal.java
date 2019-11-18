package entidades.enums;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Gi
 */
public enum ETipoAnimal {
	 Xiaomi(1), Iphone(2), Sansung(3), Hawaii(4);

    private final int opcao;

    ETipoAnimal(int opcao) {
        this.opcao = opcao;
    }

    public int getOpcao() {
        return this.opcao;
    }

    public static ETipoAnimal fromInt(int x) {
        switch (x) {
            case 1:
                return Xiaomi;
            case 2:
                return Iphone;
            case 3:
                return Sansung;
            case 4:
                return  Hawaii;
         
        }
        return null;
    }

    public static Map getValues() {
        Map<String, String> lista = new HashMap<>();
        for(ETipoAnimal i : ETipoAnimal.values()){
            lista.put(String.valueOf(i.getOpcao()), i.toString());
        }
        return lista;
    }
}
