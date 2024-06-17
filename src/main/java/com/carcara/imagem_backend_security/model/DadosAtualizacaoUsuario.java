package com.carcara.imagem_backend_security.model;

import jakarta.validation.constraints.NotNull;

public class DadosAtualizacaoUsuario {

        @NotNull
        private Integer userId;
        private String cpf;
        private String nome;
        private String telefone;
        private String foto;

        // Construtor
        public DadosAtualizacaoUsuario(@NotNull Integer userId, String cpf, String nome, String telefone, String foto) {
                this.userId = userId;
                this.cpf = cpf;
                this.nome = nome;
                this.telefone = telefone;
                this.foto = foto;
        }

        // Métodos de acesso (getters)
        public Integer userId() {
                return userId;
        }

        public String cpf() {
                return cpf;
        }

        public String nome() {
                return nome;
        }

        public String telefone() {
                return telefone;
        }

        public String foto() {
                return foto;
        }

        // Métodos de modificação (setters)
        public void setUserId(Integer userId) {
                this.userId = userId;
        }

        public void setCpf(String cpf) {
                this.cpf = cpf;
        }

        public void setNome(String nome) {
                this.nome = nome;
        }

        public void setTelefone(String telefone) {
                this.telefone = telefone;
        }

        public void setFoto(String foto) {
                this.foto = foto;
        }

        // toString
        @Override
        public String toString() {
                return "DadosAtualizacaoUsuario{" +
                        "userId=" + userId +
                        ", cpf='" + cpf + '\'' +
                        ", nome='" + nome + '\'' +
                        ", telefone='" + telefone + '\'' +
                        ", foto='" + foto + '\'' +
                        '}';
        }
}
