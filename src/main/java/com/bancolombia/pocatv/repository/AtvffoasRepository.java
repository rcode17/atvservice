package com.bancolombia.pocatv.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bancolombia.pocatv.model.Atvffoas;
import com.bancolombia.pocatv.model.AtvffoasId;

@Repository
public interface AtvffoasRepository extends JpaRepository<Atvffoas, AtvffoasId> {

	// Filtrar por año (oaano) y mes (oames) y oaxnnmky
	Page<Atvffoas> findByIdOaanoAndIdOamesAndIdOaxnnmky(Integer oaano, Integer oames, Integer oaxnnmky,
			Pageable pageable);

	// Filtrar por año (oaano) y mes (oames) solamente
	Page<Atvffoas> findByIdOaanoAndIdOames(Integer oaano, Integer oames, Pageable pageable);

	@Query("SELECT a FROM AtvffUser u JOIN u.xuArea x, Atvffoas a " +
	           "WHERE u.xuUser = :usuario " +
	           "AND a.id.oaxnnmky = x.xnnmky " +
	           "AND a.id.oaano = :ano " +
	           "AND a.id.oames = :mes")
	    Page<Atvffoas> findPorUsuarioAnoMes(@Param("usuario") String usuario,
	                                         @Param("ano") Integer ano,
	                                         @Param("mes") Integer mes,
	                                         Pageable pageable);
	
	// Filtrar por año (oaano) solamente
	@Query(value = "select * from atvffoas oas where oas.oaano = :oaano", nativeQuery = true)
	List<Atvffoas> findAnno(Integer oaano);
	
	
	// Filtrar por año (anno) (usuario) solamente
	@Query(value = "select oas.* from atvffuser_xbknam ax join atvffoas oas on ax.xnnmky = oas.oaxnnmky where ax.xuuser = :fuser and oas.oaano = :anno", nativeQuery = true)
	List<Atvffoas> findAnnoUser(@Param("anno") Integer anno, @Param("fuser") String usuario);
	
	List<Atvffoas> findByIdOaanoAndIdOames(Integer oaano,Integer oames);
	
    void deleteByIdOaanoAndIdOames(Integer oaano,Integer oames);
    
    List<Atvffoas> findByIdOamesAndIdOaano(Integer oames, Integer oaano);

}
