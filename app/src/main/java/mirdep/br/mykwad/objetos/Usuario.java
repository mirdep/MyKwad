package mirdep.br.mykwad.objetos;

import android.graphics.Bitmap;

public class Usuario {

    private Bitmap foto;
    private String id;
    private String email;
    private String nickname;
    private String nome;

    public Usuario() {
        foto = null;
        id = null;
        email = "";
        nickname = "";
        nome = "";
    }


    //============== GETTERS =============
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

    //============== SETTERS =============
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
