package mirdep.br.mykwad.pecas;

import mirdep.br.mykwad.drones.Peca;

public class Frame extends Peca {

    public int espessuraBraco, tamanhoHelice, distanciaEixos;

    public Frame(String marca, String modelo, double preco, int tamanhoHelice, int distanciaEixos, int espessuraBraco) {
        super(modelo, marca, preco);
        this.espessuraBraco = espessuraBraco;
        this.tamanhoHelice = tamanhoHelice;
        this.distanciaEixos = distanciaEixos;
    }

    public String toString(){
        return getMarca()+" "+ getModelo()+" "+ getDistanciaEixos()+"mm "+ getTamanhoHelice()+"\" "+ getEspessuraBraco()+"mm";
    }

    //========== GETTERS and SETTERS =============

    public int getEspessuraBraco() {
        return espessuraBraco;
    }

    public void setEspessuraBraco(int espessuraBraco) {
        this.espessuraBraco = espessuraBraco;
    }

    public int getTamanhoHelice() {
        return tamanhoHelice;
    }

    public void setTamanhoHelice(int tamanhoHelice) {
        this.tamanhoHelice = tamanhoHelice;
    }

    public int getDistanciaEixos() {
        return distanciaEixos;
    }

    public void setDistanciaEixos(int distanciaEixos) {
        this.distanciaEixos = distanciaEixos;
    }
}
