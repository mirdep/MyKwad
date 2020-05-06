package mirdep.br.mykwad.pecas;

import mirdep.br.mykwad.Peca;

public class VideoTransmissor extends Peca {

    private int potencia;

    public VideoTransmissor(String marca, String modelo, double preco, int potencia) {
        super(modelo, marca, preco);
        this.potencia = potencia;
    }

    public String toString(){
        return getMarca()+" "+ getModelo()+" "+ getPotencia()+"mW";
    }

    //========== GETTERS and SETTERS =============

    public int getPotencia() {
        return potencia;
    }

    public void setPotencia(int potencia) {
        this.potencia = potencia;
    }
}
