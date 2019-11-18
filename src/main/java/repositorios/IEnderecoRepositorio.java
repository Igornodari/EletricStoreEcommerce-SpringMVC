package repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import entidades.Endereco;

public interface IEnderecoRepositorio extends JpaRepository<Endereco, Integer> {
	@Query(value = "select * from endereco where idcliente = :idcliente", nativeQuery = true)
	List<Endereco> findByIdCliente(@Param("idcliente")Integer idcliente);
	
	@Query(value = "select * from endereco where idcliente = :idcliente and cep = :cep", nativeQuery = true)
	Endereco findByCepIdCliente(@Param("idcliente")Integer idcliente, @Param("cep") String cep);
}
