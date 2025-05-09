package com.bancolombia.pocatv.repository;

import com.bancolombia.pocatv.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    /**
     * Encuentra todos los códigos de producto y documento distintos
     * @return Lista de arrays con [codigoProducto, codigoDocumento]
     */
    @Query(value = "SELECT DISTINCT p.codigo_producto, p.codigo_documento FROM producto p", nativeQuery = true)
    List<Object[]> findAllProductosDocumentos();

    /**
     * Busca un producto por su código de producto y código de documento
     * @param codigoProducto Código del producto
     * @param codigoDocumento Código del documento
     * @return El producto encontrado o null si no existe
     */
    Producto findByCodigoProductoAndCodigoDocumento(String codigoProducto, String codigoDocumento);
}