package com.bancolombia.pocatv.repository;

import com.bancolombia.pocatv.model.AtvffFre;
import com.bancolombia.pocatv.model.AtvffFreId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.bancolombia.pocatv.dto.AtvffFreResponseDto;
import java.util.List;
import java.util.Optional;

@Repository
public interface AtvffFreRepository extends JpaRepository<AtvffFre, AtvffFreId> {

	@Query(value = "SELECT p.xpcopr AS xpCopr, p.xpcodo AS xpCodo, p.xpdsdo AS xpDsdo, " +
			"p.xpcta AS xpCta, p.xpstdo AS xpStdo, p.xpfeca AS xpFeca, " +
			"f.fxfrar AS fxFrar, f.fxdifr AS fxDifr " +
			"FROM atvffpdo p LEFT JOIN atvfffre f ON p.xpcopr = f.fxcopr AND p.xpcodo = f.fxcodo " +
			"ORDER BY p.xpcopr ASC, p.xpcodo ASC",
			nativeQuery = true)
	Page<AtvffFreResponseDto> findAllAtvffResults(Pageable pageable);

	Optional<AtvffFre> findById(AtvffFreId id);

	// Opción 1: Usando la nomenclatura de Spring Data JPA para embedded IDs
	Optional<AtvffFre> findById_fxCoprAndId_fxCodo(String fxCopr, String fxCodo);

	// Opción 2: Usando una consulta JPQL explícita (alternativa)
	@Query("SELECT f FROM AtvffFre f WHERE f.id.fxCopr = :fxCopr AND f.id.fxCodo = :fxCodo")
	Optional<AtvffFre> findByFxCoprAndFxCodo(@Param("fxCopr") String fxCopr,
											 @Param("fxCodo") String fxCodo);

	AtvffFre findById_FxCoprAndId_FxCodo(String producto, String documento);
}
