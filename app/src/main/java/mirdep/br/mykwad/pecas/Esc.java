package mirdep.br.mykwad.pecas;

import mirdep.br.mykwad.drones.Peca;

public class Esc extends Peca {

    private int amperagem;

    public Esc(String marca, String modelo, double preco, int amperagem) {
        super(modelo, marca, preco);
        this.amperagem = amperagem;
    }

    public String toString(){
        return getMarca()+" "+ getModelo()+" "+ getAmperagem()+"A";
    }

    //========== GETTERS and SETTERS =============

    public int getAmperagem() {
        return amperagem;
    }

    public void setAmperagem(int amperagem) {
        this.amperagem = amperagem;
    }
}
