package mirdep.br.mykwad.usuario;

public class Usuario {

    private String email;
    private String nickname;
    private String nome;
    private String imagem_url;

    public Usuario() {
    }

    public Usuario(String email, String nickname, String nome, String imagem_url) {
        this.email = email;
        this.nickname = nickname;
        this.nome = nome;
        this.imagem_url = imagem_url;
    }

    public Usuario(String email, String nickname, String nome) {
        this.email = email;
        this.nickname = nickname;
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getImagem_url() {
        return imagem_url;
    }

    public void setImagem_url(String imagem_url) {
        this.imagem_url = imagem_url;
    }
}
