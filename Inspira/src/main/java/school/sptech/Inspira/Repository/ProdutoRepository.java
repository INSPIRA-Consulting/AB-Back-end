package school.sptech.Inspira.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.Inspira.Entity.Produto;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
    List<Produto> findByNomeContainsIgnoreCase(String nome);

    Boolean existsByNomeIgnoreCaseAndProdutoIdNot(String nome, Integer id);

    Boolean existsByNomeIgnoreCase(String nome);
}
