package com.bancolombia.pocatv.repository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bancolombia.pocatv.model.Atvffarq;
import com.bancolombia.pocatv.model.AtvffarqId;

@Repository
public interface AtvffarqRepository extends JpaRepository<Atvffarq, AtvffarqId> {
    // Método para consultar un registro por los campos aqcdsu, aqcopr, aqcodo y aqfear
    Optional<Atvffarq> findByAqcdsuAndAqcoprAndAqcodoAndAqfear(
            Integer aqcdsu,
            String aqcopr,
            String aqcodo,
            String aqfear);

    List<Atvffarq> findByAqcdsuAndAqcoprAndAqcodoAndAqfearAndAqcedan(
            Integer aqcdsu,
            String aqcopr,
            String aqcodo,
            String aqfear,
            String aqcedan);

    @Query(value = "SELECT * FROM atvffarq a WHERE a.aqres = :resultado AND a.aqfear >= :fechaInicio", nativeQuery = true)
    List<Atvffarq> findByAqresAndAqfearGreaterThanEqual(@Param("resultado") String resultado, @Param("fechaInicio") String fechaInicio);

	/**
	 * Busca arqueos por mes y año para una sucursal, producto y documento
	 * específicos
	 */
	@Query(value = "SELECT * FROM Atvffarq a WHERE a.aqcdsu = :sucursal AND a.aqcopr = :producto AND a.aqcodo = :documento AND a.aqfear = :fecha", nativeQuery = true)
	List<Atvffarq> findArqueosPorMes(@Param("sucursal") Integer sucursal, @Param("producto") String producto,
			@Param("documento") String documento, @Param("fecha") String fecha

	);

	/**
	 * Verifica si existe un arqueo para una fecha específica
	 */
	boolean existsByAqcdsuAndAqcoprAndAqcodoAndAqfear(Integer aqcdsu, String aqcopr, String aqcodo, String aqfear);

	List<Atvffarq> findByAqcdsuAndAqcoprAndAqcodoAndAqfearBetween(Integer aqcdsu, String aqcopr, String aqcodo,
			String fechaInicio, String fechaFin);

	@Query("SELECT a FROM Atvffarq a WHERE a.aqcdsu = :sucursal AND a.aqcopr = :producto AND a.aqcodo = :documento AND a.aqfear BETWEEN :fechaInicio AND :fechaFin")
	List<Atvffarq> findArqueosByProductoDocumentoAndPeriodo(@Param("sucursal") Integer sucursal,
			@Param("producto") String producto, @Param("documento") String documento,
			@Param("fechaInicio") String fechaInicio, @Param("fechaFin") String fechaFin);

	@Query("SELECT COUNT(a) FROM Atvffarq a " + "WHERE a.aqcdsu = :sucursal " + "  AND a.aqcopr = :producto "
			+ "  AND a.aqcodo = :documento " + "  AND a.aqfear BETWEEN :fechaInicio AND :fechaFin "
			+ "  AND (a.aqres = 'C' OR a.aqjust = 'S')")
	Integer countArqueosCuadradosByProductoDocumentoAndPeriodo(@Param("sucursal") Integer sucursal,
			@Param("producto") String producto, @Param("documento") String documento,
			@Param("fechaInicio") String fechaInicio, @Param("fechaFin") String fechaFin);

	@Query("SELECT a FROM Atvffarq a WHERE SUBSTRING(a.aqfear, 1, 4) = :year")
	List<Atvffarq> findByYear(@Param("year") String year);

	@Query("SELECT a FROM Atvffarq a WHERE a.aqcdsu = :sucursal AND a.aqcopr = :copr AND a.aqcodo = :codo ORDER BY a.aqfear")
	List<Atvffarq> findBySucursalAndProductoAndDocumentoOrderByFecha(@Param("sucursal") Integer sucursal,
			@Param("copr") String copr, @Param("codo") String codo);

	@Query(value = "SELECT * FROM Atvffarq a WHERE a.aqcdsu = :sucursal AND a.aqcopr = :copr AND a.aqcodo = :codo AND a.aqfear < :fecha ORDER BY a.aqfear DESC", nativeQuery = true)
	List<Atvffarq> findArqueosAnteriores(@Param("sucursal") Integer sucursal, @Param("copr") String copr,
			@Param("codo") String codo, @Param("fecha") LocalDate fecha);

	default Optional<Atvffarq> findArqueoAnterior(Integer sucursal, String copr, String codo, LocalDate fecha) {
		List<Atvffarq> arqueosAnteriores = findArqueosAnteriores(sucursal, copr, codo, fecha);
		return arqueosAnteriores.isEmpty() ? Optional.empty() : Optional.of(arqueosAnteriores.get(0));
	}

	// Consulta para buscar por año y mes usando cadenas
	@Query("SELECT a FROM Atvffarq a WHERE a.aqfear LIKE :yearMonth%")
	List<Atvffarq> findByYearMonthString(@Param("yearMonth") String yearMonth);

	// Método que convierte año y mes a formato de cadena para la consulta
	default List<Atvffarq> findByYearAndMonth(int year, int month) {
		String monthStr = month < 10 ? "0" + month : String.valueOf(month);
		return findByYearMonthString(year + "-" + monthStr);
	}
	
    
    @Query(value ="SELECT * FROM atvffarq a WHERE SUBSTRING(a.aqfear, 1, 4) = CAST(:anno AS TEXT) AND SUBSTRING(a.aqfear, 6, 2) = LPAD(CAST(:mes AS TEXT), 2, '0') AND a.aqres = 'D'", nativeQuery = true)
    List<Atvffarq> findArqueosDescuadradosByAnnoAndMes(@Param("anno") String anno, @Param("mes") String mes);

	@Query("SELECT a FROM Atvffarq a WHERE a.aqcopr = :copr AND a.aqcodo = :codo AND SUBSTRING(a.aqfear, 1, 4) = :ano")
	List<Atvffarq> findByAqcoprAndAqcodoAndAno(@Param("copr") String copr, @Param("codo") String codo,
			@Param("ano") String ano);

	@Query("SELECT a FROM Atvffarq a WHERE a.aqcdsu = :sucursal AND a.aqcopr = :copr AND a.aqcodo = :codo")
	List<Atvffarq> findBySucursalAndCoprAndCodo(@Param("sucursal") Integer sucursal, @Param("copr") String copr,
			@Param("codo") String codo);

	@Query("SELECT a FROM Atvffarq a WHERE a.aqcdsu = :sucursal AND a.aqcopr = :copr AND a.aqcodo = :codo AND SUBSTRING(a.aqfear, 6, 2) = :mes AND SUBSTRING(a.aqfear, 1, 4) = :ano")
	List<Atvffarq> findBySucursalAndCoprAndCodoAndMesAno(@Param("sucursal") Integer sucursal,
			@Param("copr") String copr, @Param("codo") String codo, @Param("mes") String mes, @Param("ano") String ano);

	List<Atvffarq> findByAqcoprAndAqcodoAndAqfearBetween(String aqcopr, String aqcodo, String aqfearAfter,
			String aqfearBefore);

	@Query("SELECT a FROM Atvffarq a WHERE a.aqcopr = :aqcopr AND a.aqcodo = :aqcodo AND SUBSTRING(a.aqfear, 1, 4) = :year AND SUBSTRING(a.aqfear, 6, 2) = :month")
	List<Atvffarq> findByAqcoprAndAqcodoAndYearAndMonth(@Param("aqcopr") String aqcopr, @Param("aqcodo") String aqcodo,
			@Param("year") String year, @Param("month") String month);

	@Query("SELECT COUNT(a) FROM Atvffarq a WHERE a.aqcopr = :aqcopr AND a.aqcodo = :aqcodo AND SUBSTRING(a.aqfear, 1, 4) = :year AND SUBSTRING(a.aqfear, 6, 2) = :month AND a.aqres = 'C'")
	Integer countCuadradosByAqcoprAndAqcodoAndYearAndMonth(@Param("aqcopr") String aqcopr,
			@Param("aqcodo") String aqcodo, @Param("year") String year, @Param("month") String month);


	  @Query("SELECT a FROM Atvffarq a WHERE CONCAT(SUBSTRING(a.aqfear, 1, 4), SUBSTRING(a.aqfear, 6, 7)) >= :fechaInicio AND CONCAT(SUBSTRING(a.aqfear, 1, 4), SUBSTRING(a.aqfear, 6, 7)) <= :fechaFin")
	    List<Atvffarq> findByFechaRange(@Param("fechaInicio") String fechaInicio, @Param("fechaFin") String fechaFin);



	@Query("SELECT a FROM Atvffarq a WHERE a.aqcdsu = :sucursal AND a.aqcopr = :producto AND a.aqcodo = :documento AND SUBSTRING(a.aqfear, 1, 4) = :ano AND SUBSTRING(a.aqfear, 6, 2) = :mes")
	List<Atvffarq> findArqueosxMes(
			@Param("sucursal") Integer sucursal,
			@Param("producto") String producto,
			@Param("documento") String documento,
			@Param("ano") String ano,
			@Param("mes") String mes
	);
	
	// Consulta nativa para extraer registros por año, mes y resultado
    @Query(value = "SELECT * FROM Atvffarq a " +
                   "WHERE SUBSTRING(a.aqfear, 1, 4) = :year " +
                   "AND SUBSTRING(a.aqfear, 6, 2) = LPAD(:month, 2, '0') " +
                   "AND (a.aqres = 'RC' OR a.aqres = 'RD')", 
           nativeQuery = true)
    List<Atvffarq> findByYearAndMonthAndResult(@Param("year") String year, @Param("month") String month);
    
    
    
    @Query("SELECT a FROM Atvffarq a WHERE a.id.aqcdsu = ?1 AND a.id.aqcopr = ?2 AND a.id.aqcodo = ?3 AND SUBSTRING(a.aqfear, 1, 4) = ?4")
    List<Atvffarq> findBySucursalProductoDocumentoAndYear(Integer sucursal, String codigoProducto, String codigoDocumento, Integer year);
    
    @Query("SELECT a FROM Atvffarq a WHERE a.id.aqcdsu = ?1 AND a.id.aqcopr = ?2 AND a.id.aqcodo = ?3 AND SUBSTRING(a.aqfear, 1, 4) = ?4 AND SUBSTRING(a.aqfear, 5, 2) = ?5")
    List<Atvffarq> findBySucursalProductoDocumentoYearAndMonth(Integer sucursal, String codigoProducto, String codigoDocumento, Integer year, Integer month);
    
    @Query(value = "SELECT COUNT(*) FROM atvffarq a " +
    	       "WHERE a.aqcdsu = ?1 " +
    	       "AND a.aqcopr = ?2 " +
    	       "AND a.aqcodo = ?3 " +
    	       "AND EXTRACT(YEAR FROM TO_DATE(a.aqfear, 'YYYY-MM-DD')) = ?4 " +
    	       "AND EXTRACT(MONTH FROM TO_DATE(a.aqfear, 'YYYY-MM-DD')) = ?5 " +
    	       "AND a.aqres = 'C'", nativeQuery = true)
    Integer countCuadradosBySucursalProductoDocumentoYearAndMonth(Integer sucursal, String codigoProducto, String codigoDocumento, Integer year, Integer month);
    
    @Query("SELECT a FROM Atvffarq a WHERE a.aqcopr = :codigoProducto AND a.aqcodo = :codigoDocumento AND a.aqfear BETWEEN :fechaInicio AND :fechaFin")
    List<Atvffarq> findByProductoAndDocumentoAndFechaBetween(
            @Param("codigoProducto") String codigoProducto,
            @Param("codigoDocumento") String codigoDocumento,
            @Param("fechaInicio") String fechaInicio,
            @Param("fechaFin") String fechaFin);
    
    @Query("SELECT a FROM Atvffarq a WHERE a.aqcdsu = :suc AND a.aqcopr = :codigoProducto AND a.aqcodo = :codigoDocumento AND a.aqfear BETWEEN :fechaInicio AND :fechaFin")
    List<Atvffarq> findBySucursalAndProductoAndDocumentoAndFechaBetween(
    		@Param("suc") Integer suc,
    		@Param("codigoProducto") String codigoProducto,
            @Param("codigoDocumento") String codigoDocumento,
            @Param("fechaInicio") String fechaInicio,
            @Param("fechaFin") String fechaFin);
    
}