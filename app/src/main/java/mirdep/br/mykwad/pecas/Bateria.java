package mirdep.br.mykwad.pecas;

import mirdep.br.mykwad.drones.Peca;

public class Bateria extends Peca {

    private int capacidade, taxaDescarga, numeroCelulas;

    public Bateria(String marca, String modelo, double preco, int numeroCelulas, int capacidade, int taxaDescarga) {
        super(modelo, marca, preco);
        this.capacidade = capacidade;
        this.taxaDescarga = taxaDescarga;
        this.numeroCelulas = numeroCelulas;
    }

    public String toString(){
        return getMarca()+" "+ getModelo()+" "+ getNumeroCelulas()+"s "+ getCapacidade()+"mah "+ getTaxaDescarga()+"C";
    }

    //========== GETTERS and SETTERS =============

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public int getTaxaDescarga() {
        return taxaDescarga;
    }

    public void setTaxaDescarga(int taxaDescarga) {
        this.taxaDescarga = taxaDescarga;
    }

    public int getNumeroCelulas() {
        return numeroCelulas;
    }

    public void setNumeroCelulas(int numeroCelulas) {
        this.numeroCelulas = numeroCelulas;
    }
}
