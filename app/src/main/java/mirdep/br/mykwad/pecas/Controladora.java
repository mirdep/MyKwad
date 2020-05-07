package mirdep.br.mykwad.pecas;

import mirdep.br.mykwad.drones.Peca;

public class Controladora extends Peca {

    private String processador;

    public Controladora(String marca, String modelo, double preco, String processador) {
        super(modelo, marca, preco);
        this.processador = processador;
    }

    public String toString(){
        return getMarca()+" "+ getModelo()+" "+ getProcessador();
    }

    //========== GETTERS and SETTERS =============

    public String getProcessador() {
        return processador;
    }

    public void setProcessador(String processador) {
        this.processador = processador;
    }
}
