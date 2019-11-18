/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repositorios;

import entidades.ItensPedido;
import entidades.Pedido;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Gi
 */
public interface IItensPedidoRepositorio extends JpaRepository<ItensPedido, Integer> {
    @Query(value = "select * from itenspedido where idpedido = :id", nativeQuery = true)
    public List<ItensPedido> obterPorIdPedido(@Param("id")Integer id);
}
