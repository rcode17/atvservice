package com.bancolombia.pocatv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bancolombia.pocatv.model.Atvffmd;
import com.bancolombia.pocatv.model.AtvffmdId;

import java.util.List;
import java.util.Optional;

@Repository
public interface AtvffmdRepository extends JpaRepository<Atvffmd, AtvffmdId> {
    Optional<Atvffmd> findById(AtvffmdId id);


    // Método para buscar por componentes de la clave primaria (usando campos directos)
    Optional<Atvffmd> findByMdcoprAndMdcodoAndMdanoAndMdmes(
            String mdcopr,
            String mdcodo,
            Integer mdano,
            Short mdmes);

    // Métodos alternativos con consultas JPQL (usando campos directos)
    @Query("SELECT a FROM Atvffmd a WHERE a.mdcopr = :mdcopr AND a.mdcodo = :mdcodo AND a.mdano = :mdano AND a.mdmes = :mdmes")
    Optional<Atvffmd> findByCompositeId(
            @Param("mdcopr") String mdcopr,
            @Param("mdcodo") String mdcodo,
            @Param("mdano") Integer mdano,
            @Param("mdmes") Short mdmes);

    @Query("SELECT a FROM Atvffmd a WHERE a.mdano = :mdano")
    List<Atvffmd> findByYear(@Param("mdano") Integer mdano);

    @Query("SELECT a FROM Atvffmd a WHERE a.mdcopr = :mdcopr AND a.mdcodo = :mdcodo")
    List<Atvffmd> findByOperationAndDocument(
            @Param("mdcopr") String mdcopr,
            @Param("mdcodo") String mdcodo);

    @Query("SELECT a FROM Atvffmd a WHERE a.mdcopr = :mdcopr AND a.mdcodo = :mdcodo " +
            "ORDER BY a.mdano DESC, a.mdmes DESC")
    List<Atvffmd> findLatestByOperationAndDocument(
            @Param("mdcopr") String mdcopr,
            @Param("mdcodo") String mdcodo);

    List<Object> findByMdcoprAndMdcodoAndMdano(String mdcopr, String mdcodo, Integer mdano);
}