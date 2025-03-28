package com.bancolombia.pocatv.repository;

import com.bancolombia.pocatv.model.AtvffFre;
import com.bancolombia.pocatv.model.AtvffFreId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.bancolombia.pocatv.dto.AtvffFreResponseDto;
import java.util.List;

@Repository
public interface AtvffFreRepository extends JpaRepository<AtvffFre, AtvffFreId>{
	@Query(value = "select p.xpcopr, p.xpcodo, p.xpdsdo, f.fxfrar, f.fxdifr from atvffpdo p left join atvfffre f ON p.xpcopr = f.fxcopr AND p.xpcodo = f.fxcodo ORDER BY p.xpcopr ASC, p.xpcodo ASC", nativeQuery = true)
	List<AtvffFreResponseDto> findAllAtvffResults();
}
