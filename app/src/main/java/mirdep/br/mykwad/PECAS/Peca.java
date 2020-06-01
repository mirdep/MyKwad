package mirdep.br.mykwad.PECAS;

import android.net.Uri;

import androidx.annotation.NonNull;

public class Peca {

    private String id;
    private String tipo;
    private String marca;
    private String modelo;

    private Uri storage_imagem_uri;

    public Peca(String tipo, String marca, String modelo) {
        this.tipo = tipo;
        this.marca = marca;
        this.modelo = modelo;
    }

    public Peca(String id, String tipo, String marca, String modelo) {
        this.id = id;
        this.tipo = tipo;
        this.marca = marca;
        this.modelo = modelo;
    }

    public Peca() {
        id = "";
        tipo = "";
        marca = "";
        modelo = "";
        storage_imagem_uri = null;
    }

    //============== METHODS ================================



    //============== GETTERS and SETTERS ====================


    @NonNull
    @Override
    public String toString() {
        return getTipo()+" "+getMarca()+" "+getModelo();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Uri getStorage_imagem_uri() {
        return storage_imagem_uri;
    }

    public void setStorage_imagem_uri(Uri storage_imagem_uri) {
        this.storage_imagem_uri = storage_imagem_uri;
    }
}
