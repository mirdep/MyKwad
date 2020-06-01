package mirdep.br.mykwad.usuario;

public class Usuario {

    private String id;
    private String email;
    private String nickname;
    private String nome;

    public Usuario() {
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
