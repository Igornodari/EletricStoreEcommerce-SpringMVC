package repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import entidades.CartaoCredito;

public interface ICartaoCreditoRepositorio extends JpaRepository<CartaoCredito, Integer> {
	@Query(value = "select * from cartaocredito where idcliente = :idcliente", nativeQuery = true)
	List<CartaoCredito> findByIdCliente(@Param("idcliente")Integer idcliente);
}
