package mirdep.br.mykwad.drones;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;

import java.util.List;

import mirdep.br.mykwad.Pecas.Peca;
import mirdep.br.mykwad.usuario.Usuario;
import mirdep.br.mykwad.usuario.UsuarioAuthentication;
import mirdep.br.mykwad.usuario.UsuarioRepositorio;

public class Drone {
    
    private static String PECA_NAO_SELECIONADA = "0";

    private String id;
    private String titulo;
    private String descricao;
    private List<Bitmap> fotos;
    private String usuarioDonoId;

    //========= PECAS ============
    private Peca antena;
    private Peca bateria;
    private Peca camera;
    private Peca esc;
    private Peca controladora;
    private Peca frame;
    private Peca motor;
    private Peca helice;
    private Peca videoTransmissor;

    public Drone() {
        this.usuarioDonoId = UsuarioAuthentication.getInstance().getUsuarioAuth().getDisplayName();
        this.titulo = "";
        this.descricao = "";
        this.antena = null;
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

    //======= GETTERS ==================

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getUsuarioDonoId() {
        return usuarioDonoId != null ? usuarioDonoId : PECA_NAO_SELECIONADA;
    }



    public String getAntenaId(){
        return antena != null ? antena.getId() : PECA_NAO_SELECIONADA;
    }

    public String getBateriaId(){
        return bateria != null ? bateria.getId() : PECA_NAO_SELECIONADA;
    }

    public String getCameraId(){
        return camera != null ? camera.getId() : PECA_NAO_SELECIONADA;
    }

    public String getControladoraId(){
        return controladora != null ? controladora.getId() : PECA_NAO_SELECIONADA;
    }

    public String getEscId(){
        return esc != null ? esc.getId() : PECA_NAO_SELECIONADA;
    }

    public String getFrameId(){
        return frame != null ? frame.getId() : PECA_NAO_SELECIONADA;
    }

    public String getHeliceId(){
        return helice != null ? helice.getId() : PECA_NAO_SELECIONADA;
    }

    public String getMotorId(){
        return motor != null ? motor.getId() : PECA_NAO_SELECIONADA;
    }

    public String getVideoTransmissorId(){
        return videoTransmissor != null ? videoTransmissor.getId() : PECA_NAO_SELECIONADA;
    }


    //==================== SETTERS ===============


    public void setUsuarioDonoId(Fragment fragment) {

    }

    public void setPecas(List<Peca> pecas){
        for(int i = 0; i < pecas.size(); i++){
            switch (i){
                case 0:
                    setAntena(pecas.get(0));
                    break;
                case 1:
                    setBateria(pecas.get(1));
                    break;
                case 2:
                    setCamera(pecas.get(2));
                    break;
                case 3:
                    setEsc(pecas.get(3));
                    break;
                case 4:
                    setControladora(pecas.get(4));
                    break;
                case 5:
                    setFrame(pecas.get(5));
                    break;
                case 6:
                    setMotor(pecas.get(6));
                    break;
                case 7:
                    setHelice(pecas.get(7));
                    break;
                case 8:
                    setVideoTransmissor(pecas.get(8));
                    break;
            }
        }
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setFotos(List<Bitmap> fotos) {
        this.fotos = fotos;
    }

    public void setAntena(Peca antena) {
        this.antena = antena;
    }

    public void setBateria(Peca bateria) {
        this.bateria = bateria;
    }

    public void setCamera(Peca camera) {
        this.camera = camera;
    }

    public void setEsc(Peca esc) {
        this.esc = esc;
    }

    public void setControladora(Peca controladora) {
        this.controladora = controladora;
    }

    public void setFrame(Peca frame) {
        this.frame = frame;
    }

    public void setMotor(Peca motor) {
        this.motor = motor;
    }

    public void setHelice(Peca helice) {
        this.helice = helice;
    }

    public void setVideoTransmissor(Peca videoTransmissor) {
        this.videoTransmissor = videoTransmissor;
    }
}
