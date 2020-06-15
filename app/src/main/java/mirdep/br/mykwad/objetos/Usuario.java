package mirdep.br.mykwad.objetos;

import android.graphics.Bitmap;

public class Usuario {

    private Bitmap foto;
    private String id;
    private String email;
    private String nickname;
    private String nome;
    private String tempoCriacao;

    public Usuario() {
        foto = null;
        id = null;
        email = "";
        nickname = "";
        nome = "";
    }


    //============== GETTERS =============
    public Bitmap retrieveFoto(){
        return foto;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getNickname() {
        return nickname;
    }

    public String getNome() {
        return nome;
    }

    public String getTempoCriacao() {
        return tempoCriacao;
    }

    //============== SETTERS =============
    public void setTempoCriacao(String tempoCriacao) {
        this.tempoCriacao = tempoCriacao;
    }

    public void setFoto(Bitmap foto){
        this.foto = foto;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
