package mirdep.br.mykwad.drones;

import mirdep.br.mykwad.comum.MetodosGerais;

public abstract class Peca {

    private String id;
    private String marca, modelo;
    private double preco;

    public Peca(String marca, String modelo, double preco) {
        this.marca = marca;
        this.modelo = modelo;
        this.preco = preco;
    }

    //============== METHODS ================================



    //============== GETTERS and SETTERS ====================


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }
}
