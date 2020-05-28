package mirdep.br.mykwad.drones;

import mirdep.br.mykwad.Pecas.Peca;

public class Drone {

    private int id;
    private String titulo;
    private String descricao;
    private double precoTotal;

    //========= PECAS ============
    private Peca bateria;
    private Peca camera;
    private Peca esc;
    private Peca controladora;
    private Peca frame;
    private Peca motor;
    private Peca helice;
    private Peca videoTransmissor;

    public Drone() {
        this.titulo = "";
        this.precoTotal = 0.0;
        this.bateria = null;
        this.camera = null;
        this.esc = null;
        this.controladora = null;
        this.frame = null;
        this.motor = null;
        this.helice = null;
        this.videoTransmissor = null;
    }

    //==============================================

    //======= GETTERS AND SETTERS ==================


}
