package com.carcara.imagem_backend_security.model;

public class RegisterDTO {
    private String login;
    private String password;
    private String email;
    private String cpf;
    private String nome;
    private String telefone;
    private String foto;

    // Construtor
    public RegisterDTO(String login, String password, String email, String cpf, String nome, String telefone, String foto) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.cpf = cpf;
        this.nome = nome;
        this.telefone = telefone;
        this.foto = foto;
    }

    public RegisterDTO(User user) {
        this.login = user.getUsername();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.cpf = user.getCpf();
        this.nome = user.getNome();
        this.telefone = user.getTelefone();
        this.foto = user.getFoto();
    }

    // Getters e Setters
    public String login() { return login; }
    public void setLogin(String login) { this.login = login; }

    public String password() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String email() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String cpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String nome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String telefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String foto() { return foto; }
    public void setFoto(String foto) { this.foto = foto; }
}
