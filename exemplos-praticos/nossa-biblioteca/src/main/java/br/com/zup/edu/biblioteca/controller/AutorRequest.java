package br.com.zup.edu.biblioteca.controller;

import br.com.zup.edu.biblioteca.model.Autor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

public class AutorRequest {
    @NotBlank
    private String nome;

    @NotBlank
    private String email;

    @NotBlank
    @Length(max = 2500)
    private String descricao;

    @NotBlank
    private String cpf;

    public AutorRequest(String nome, String email, String descricao, String cpf) {
        this.nome = nome;
        this.email = email;
        this.descricao = descricao;
        this.cpf = cpf;
    }

    public AutorRequest() {
    }

    public Autor paraAutor() {
        return new Autor(nome, email, descricao, cpf);
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getCpf() {
        return cpf;
    }
}
