package com.bancolombia.pocatv.repository;

import com.bancolombia.pocatv.model.Atvffran;
import com.bancolombia.pocatv.model.AtvffranId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AtvffranRepository extends JpaRepository<Atvffran, AtvffranId> {

	/**
	 * Busca un rango por su frecuencia y cantidad de días
	 * @param rnfrec Código de frecuencia (T: Trimestral, S: Semestral, A: Anual, etc.)
	 * @param rncant Cantidad de días de frecuencia
	 * @return Objeto Optional con el rango si existe
	 */
	Optional<Atvffran> findByRnfrecAndRncant(String rnfrec, Integer rncant);

	/**
	 * Busca todos los rangos para una frecuencia específica
	 * @param rnfrec Código de frecuencia
	 * @return Lista de rangos para la frecuencia especificada
	 */
	List<Atvffran> findByRnfrec(String rnfrec);

	/**
	 * Busca rangos donde el valor especificado esté dentro del rango (entre RNRAIN y RNRAFN)
	 * @param valor Valor a verificar
	 * @return Lista de rangos que contienen el valor especificado
	 */
	@Query("SELECT r FROM Atvffran r WHERE :valor BETWEEN r.rnrain AND r.rnrafn")
	List<Atvffran> findRangosConteniendo(@Param("valor") Integer valor);

	/**
	 * Busca rangos para una frecuencia específica donde el valor esté dentro del rango
	 * @param rnfrec Código de frecuencia
	 * @param valor Valor a verificar
	 * @return Lista de rangos que coinciden con los criterios
	 */
	@Query("SELECT r FROM Atvffran r WHERE r.rnfrec = :rnfrec AND :valor BETWEEN r.rnrain AND r.rnrafn")
	List<Atvffran> findRangosPorFrecuenciaConteniendo(@Param("rnfrec") String rnfrec, @Param("valor") Integer valor);

	/**
	 * Verifica si un valor está dentro de algún rango para una frecuencia y cantidad específicas
	 * @param rnfrec Código de frecuencia
	 * @param rncant Cantidad de días de frecuencia
	 * @param valor Valor a verificar
	 * @return true si el valor está dentro del rango, false en caso contrario
	 */
	@Query("SELECT COUNT(r) > 0 FROM Atvffran r WHERE r.rnfrec = :rnfrec AND r.rncant = :rncant AND :valor BETWEEN r.rnrain AND r.rnrafn")
	boolean isValorEnRango(@Param("rnfrec") String rnfrec, @Param("rncant") Integer rncant, @Param("valor") Integer valor);
}
