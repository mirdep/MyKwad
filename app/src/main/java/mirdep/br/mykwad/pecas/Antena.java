package mirdep.br.mykwad.pecas;

import mirdep.br.mykwad.drones.Peca;

public class Antena extends Peca {

    private String tipo;

    public Antena(String marca, String modelo, double preco, String tipo) {
        super(marca, modelo, preco);
        this.tipo = tipo;
    }

    public Antena(String[] variaveis){
        super(variaveis[0], variaveis[1], Double.parseDouble(variaveis[2]));
        this.tipo = variaveis[4];
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
