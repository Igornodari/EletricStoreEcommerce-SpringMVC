package repositorios;

import entidades.Imagem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IImagemRepositorio extends JpaRepository<Imagem, Integer> {

    @Query(value = "SELECT * FROM IMAGEM WHERE idproduto = :idproduto", nativeQuery = true)
    public List<Imagem> findByIdProduto(@Param("idproduto") Integer idProduto);
}
