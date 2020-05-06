package mirdep.br.mykwad.pecas;

import mirdep.br.mykwad.Peca;

public class Motor extends Peca {

    private int tamanho, rotacao;

    public Motor(String marca, String modelo, double preco, int tamanho, int rotacao) {
        super(modelo, marca, preco);
        this.tamanho = tamanho;
        this.rotacao = rotacao;
    }

    public String toString(){
        return getMarca()+" "+ getModelo()+" "+ getTamanho()+" "+ getRotacao()+"KV";
    }

    //========== GETTERS and SETTERS =============

    public int getTamanho() {
        return tamanho;
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }

    public int getRotacao() {
        return rotacao;
    }

    public void setRotacao(int rotacao) {
        this.rotacao = rotacao;
    }
}
