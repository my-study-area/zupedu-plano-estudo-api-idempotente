package br.com.zup.edu.biblioteca.repository;

import br.com.zup.edu.biblioteca.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutorRepository extends JpaRepository<Autor,Long> {
}
