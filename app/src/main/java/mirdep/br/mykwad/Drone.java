package mirdep.br.mykwad;

import mirdep.br.mykwad.pecas.*;

public class Drone {

    private int id;
    private String titulo;
    private double precoTotal;

    //========= PARTS ============
    private Bateria bateria;
    private Camera camera;
    private Esc esc;
    private Controladora controladora;
    private Frame frame;
    private Motor motor;
    private Helice helice;
    private VideoTransmissor videoTransmissor;

    public Drone() {
        this.titulo = null;
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

    public Drone(String titulo, Bateria bateria, Camera camera, Esc esc, Controladora controladora, Frame frame, Motor motor, Helice helice, VideoTransmissor videoTransmissor) {
        this.titulo = titulo;
        this.bateria = bateria;
        this.camera = camera;
        this.esc = esc;
        this.controladora = controladora;
        this.frame = frame;
        this.motor = motor;
        this.helice = helice;
        this.videoTransmissor = videoTransmissor;
        calculatePrice();
    }

    //==============================================

    private void calculatePrice(){
        precoTotal = 0;
        if(bateria != null){
            precoTotal += bateria.getPreco();
        }
        if(camera != null){
            precoTotal += camera.getPreco();
        }
        if(esc != null){
            precoTotal += esc.getPreco();
        }
        if(controladora != null){
            precoTotal += controladora.getPreco();
        }
        if(frame != null){
            precoTotal += frame.getPreco();
        }
        if(motor != null){
            precoTotal += motor.getPreco();
        }
        if(helice != null){
            precoTotal += helice.getPreco();
        }
        if(videoTransmissor != null){
            precoTotal += videoTransmissor.getPreco();
        }
    }

    //======= GETTERS AND SETTERS ==================

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public double getPrecoTotal() {
        return precoTotal;
    }

    public Bateria getBateria() {
        return bateria;
    }

    public void setBateria(Bateria bateria) {
        this.bateria = bateria;
        calculatePrice();
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
        calculatePrice();
    }

    public Esc getEsc() {
        return esc;
    }

    public void setEsc(Esc esc) {
        this.esc = esc;
        calculatePrice();
    }

    public Controladora getControladora() {
        return controladora;
    }

    public void setControladora(Controladora controladora) {
        this.controladora = controladora;
        calculatePrice();
    }

    public Frame getFrame() {
        return frame;
    }

    public void setFrame(Frame frame) {
        this.frame = frame;
        calculatePrice();
    }

    public Motor getMotor() {
        return motor;
    }

    public void setMotor(Motor motor) {
        this.motor = motor;
        calculatePrice();
    }

    public Helice getHelice() {
        return helice;
    }

    public void setHelice(Helice helice) {
        this.helice = helice;
        calculatePrice();
    }

    public VideoTransmissor getVideoTransmissor() {
        return videoTransmissor;
    }

    public void setVideoTransmissor(VideoTransmissor videoTransmissor) {
        this.videoTransmissor = videoTransmissor;
        calculatePrice();
    }
}
