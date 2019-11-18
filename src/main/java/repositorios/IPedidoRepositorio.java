/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repositorios;

import entidades.Pedido;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Gi
 */
public interface IPedidoRepositorio extends JpaRepository<Pedido, Integer> {
    @Query(value = "SELECT id FROM pedido order by id desc limit 1", nativeQuery = true)
    public Integer findByLastId();
    
    @Query(value = "select * from pedido where id = :id", nativeQuery = true)
    public Pedido obterPorId(@Param("id")Integer id);
    
    @Query(value = "select * from pedido where idcliente = :id", nativeQuery = true)
    public List<Pedido> obterPorIdCliente(@Param("id")Integer id);
}
