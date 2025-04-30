package com.bancolombia.pocatv.service;

import com.bancolombia.pocatv.dto.*;
import com.bancolombia.pocatv.model.Atvffarq;
import com.bancolombia.pocatv.model.Atvffcrd;
import com.bancolombia.pocatv.model.Atvfffrein;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AtvfffreinService {
	List<Atvfffrein> getAllFuncionarios();

	List<Atvfffrein> getFuncionariosByPage(int page, int size);

	Atvfffrein getFuncionarioById(String resp);

	Atvfffrein saveFuncionario(Atvfffrein funcionario);

	Atvfffrein updateFuncionario(String resp, Atvfffrein funcionarioDetails);

	void deleteFuncionario(String resp);

	List<Atvfffrein> findByAreaContaining(String area);

	List<Atvfffrein> findByMesAno(Integer mes, Integer ano);
	    
}
