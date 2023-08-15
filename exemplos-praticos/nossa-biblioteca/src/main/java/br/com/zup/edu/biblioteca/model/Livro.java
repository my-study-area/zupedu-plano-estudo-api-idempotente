package br.com.zup.edu.biblioteca.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    @Lob
    private String descricao;

    @Column(nullable = false)
    private String ISBN;

    @Enumerated(EnumType.STRING)
    private TipoCirculacao circulacao;

    @Column(nullable = false)
    private LocalDate dataPublicacao;

    public Livro(String titulo, String descricao, String ISBN, LocalDate dataPublicacao) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.ISBN = ISBN;
        this.dataPublicacao = dataPublicacao;
    }

    /**
     * @deprecated construtor para uso exclusivo do Hibernate
     */
    public Livro() {
    }

    public Long getId() {
        return id;
    }
}
