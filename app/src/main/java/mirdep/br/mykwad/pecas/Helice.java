package mirdep.br.mykwad.pecas;

import mirdep.br.mykwad.drones.Peca;

public class Helice extends Peca {

    private String tamanho;

    public Helice(String marca, String modelo, double preco, String tamanho) {
        super(modelo, marca, preco);
        this.tamanho = tamanho;
    }

    public String toString(){
        return getMarca()+" "+ getModelo()+" "+ getTamanho();
    }

    //========== GETTERS and SETTERS =============

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }
}
