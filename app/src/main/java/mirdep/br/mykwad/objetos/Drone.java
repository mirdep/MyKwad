package mirdep.br.mykwad.objetos;

import android.graphics.Bitmap;

import com.google.firebase.Timestamp;

import java.util.List;

public class Drone {
    
    private static String PECA_NAO_SELECIONADA = "0";
    public static final int QTD_MAX_FOTOS = 6;

    private String id;
    private String titulo;
    private String descricao;
    private Bitmap[] fotos;
    private String usuarioDonoId;
    private String tempoCriacao;

    //========= PECAS ============
    private String antenaId;
    private String bateriaId;
    private String cameraId;
    private String escId;
    private String controladoraId;
    private String frameId;
    private String motorId;
    private String heliceId;
    private String videoTransmissorId;

    public Drone() {
    }

    //==============================================

    //======= GETTERS ==================
    public Bitmap[] retrieveImagens(){
        return fotos;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getUsuarioDonoId() {
        return usuarioDonoId != null ? usuarioDonoId : PECA_NAO_SELECIONADA;
    }

    public String getId() {
        return id;
    }

    public String getTempoCriacao() {
        return tempoCriacao;
    }

    public String criadoEm(){
        String criadoEm;
        long segundos = Timestamp.now().getSeconds()-Long.parseLong(getTempoCriacao());
        long minutos = segundos/60;
        long horas = minutos/60;
        long dias = horas/24;
        if(segundos == 1){
            criadoEm = "criado há "+1+" segundo";
        } else if(segundos <= 60){
            criadoEm = "criado há "+segundos+" segundos";
        } else if(minutos == 1){
            criadoEm = "criado há "+1+" minuto";
        } else if (minutos < 60){
            criadoEm = "criado há "+minutos+" minutos";
        } else if (horas == 1){
            criadoEm = "criado há "+1+" hora";
        } else if (horas <= 23){
            criadoEm = "criado há "+horas+" horas";
        } else if(dias == 1){
            criadoEm = "criado há "+1+" dia";
        } else {
            criadoEm = "criado há "+dias+" dias";
        }
        return criadoEm;
    }

    //==================== SETTERS ===============


    public void setTempoCriacao(String tempoCriacao) {
        this.tempoCriacao = tempoCriacao;
    }

    public void setPecas(List<Peca> pecas){
        for(int i = 0; i < pecas.size(); i++){
            Peca peca = pecas.get(i);
            if(peca != null){
                switch (i){
                    case 0:
                        setAntenaId(pecas.get(0).getId());
                        break;
                    case 1:
                        setBateriaId(pecas.get(1).getId());
                        break;
                    case 2:
                        setCameraId(pecas.get(2).getId());
                        break;
                    case 3:
                        setEscId(pecas.get(3).getId());
                        break;
                    case 4:
                        setControladoraId(pecas.get(4).getId());
                        break;
                    case 5:
                        setFrameId(pecas.get(5).getId());
                        break;
                    case 6:
                        setMotorId(pecas.get(6).getId());
                        break;
                    case 7:
                        setHeliceId(pecas.get(7).getId());
                        break;
                    case 8:
                        setVideoTransmissorId(pecas.get(8).getId());
                        break;
                }
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

    public void setFotos(List<Bitmap> listFotos) {
        fotos = new Bitmap[listFotos.size()];
        for(int i = 0; i < this.fotos.length && i < listFotos.size(); i++){
            this.fotos[i] = listFotos.get(i);
        }
    }

    public void setUsuarioDonoId(String usuarioDonoId) {
        this.usuarioDonoId = usuarioDonoId;
    }



    //===================== PEÇAS =======================


    public String getAntenaId() {
        return antenaId;
    }

    public void setAntenaId(String antenaId) {
        this.antenaId = antenaId;
    }

    public String getBateriaId() {
        return bateriaId;
    }

    public void setBateriaId(String bateriaId) {
        this.bateriaId = bateriaId;
    }

    public String getCameraId() {
        return cameraId;
    }

    public void setCameraId(String cameraId) {
        this.cameraId = cameraId;
    }

    public String getEscId() {
        return escId;
    }

    public void setEscId(String escId) {
        this.escId = escId;
    }

    public String getControladoraId() {
        return controladoraId;
    }

    public void setControladoraId(String controladoraId) {
        this.controladoraId = controladoraId;
    }

    public String getFrameId() {
        return frameId;
    }

    public void setFrameId(String frameId) {
        this.frameId = frameId;
    }

    public String getMotorId() {
        return motorId;
    }

    public void setMotorId(String motorId) {
        this.motorId = motorId;
    }

    public String getHeliceId() {
        return heliceId;
    }

    public void setHeliceId(String heliceId) {
        this.heliceId = heliceId;
    }

    public String getVideoTransmissorId() {
        return videoTransmissorId;
    }

    public void setVideoTransmissorId(String videoTransmissorId) {
        this.videoTransmissorId = videoTransmissorId;
    }
}
