package repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import entidades.Cliente;

import java.util.List;

@Repository
public interface IClienteRepositorio extends JpaRepository<Cliente, Integer> {

	public Cliente findByEmail(String email);

	public Cliente findByCpf(String cpf);

	public List<Cliente> findByAtivo(boolean ativo);

	@Query(value = "SELECT * FROM cliente WHERE email = :email and senha = :senha", nativeQuery = true)
	public Cliente findByEmailAndSenha(@Param("email")String email, @Param("senha")String senha);
	
	@Query(value = "SELECT * FROM cliente WHERE senha = :senha and id = :id", nativeQuery = true)
	public Cliente comparaSenha(@Param("senha")String senha, @Param("id")Integer id);
	
    @Query(value = "select * from cliente where id = :id", nativeQuery = true)
    public Cliente obterPorId(@Param("id")Integer id);
}
