package mirdep.br.mykwad.pecas;

import mirdep.br.mykwad.drones.Peca;

public class Camera extends Peca {

    private int resolucao;
    private double anguloLente;

    public Camera(String modelo, String marca, double price, int resolucao, double anguloLente) {
        super(modelo, modelo, price);
        this.resolucao = resolucao;
        this.anguloLente = anguloLente;
    }

    public String toString(){
        return getMarca()+" "+ getModelo()+" "+ getResolucao()+"TVL +"+ getAnguloLente()+"mm lens";
    }

    //========== GETTERS and SETTERS =============

    public int getResolucao() {
        return resolucao;
    }

    public void setResolucao(int resolucao) {
        this.resolucao = resolucao;
    }

    public double getAnguloLente() {
        return anguloLente;
    }

    public void setAnguloLente(double anguloLente) {
        this.anguloLente = anguloLente;
    }
}
