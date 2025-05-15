package com.bancolombia.pocatv.service.impl;

import com.bancolombia.pocatv.dto.*;
import com.bancolombia.pocatv.model.*;
import com.bancolombia.pocatv.repository.*;
import com.bancolombia.pocatv.service.AtvffarqService;
import com.bancolombia.pocatv.service.AtvfffreinService;
import com.bancolombia.pocatv.utils.CalcularPorcentajes;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AtvfffreinServiceImpl implements AtvfffreinService {

	@Autowired
	private AtvfffreinRepository funcionarioRepository;

	@Override
	public List<Atvfffrein> getAllFuncionarios() {
		return funcionarioRepository.findAll();
	}

	@Override
	public Page<Atvfffrein> getFuncionariosByPage(Pageable pageable) {
		//Pageable pageable = PageRequest.of(page, size);
		Page<Atvfffrein> pageResult = funcionarioRepository.findAll(pageable);
		return pageResult;
	}

	@Override
	public Atvfffrein getFuncionarioById(String resp) {
		return funcionarioRepository.findById(resp)
				.orElseThrow(() ->  new IllegalArgumentException("No se encontró funcionario con ID: " + resp));
	}

	@Override
	public Atvfffrein saveFuncionario(Atvfffrein funcionario) {
		try {
			return funcionarioRepository.save(funcionario);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Atvfffrein updateFuncionario(String resp, Atvfffrein funcionarioDetails) {
		Atvfffrein funcionario = getFuncionarioById(resp);

		funcionario.setCod(funcionarioDetails.getCod());
		funcionario.setArea(funcionarioDetails.getArea());
		funcionario.setProd(funcionarioDetails.getProd());
		funcionario.setDoc(funcionarioDetails.getDoc());
		funcionario.setCal(funcionarioDetails.getCal());
		funcionario.setMessubf(funcionarioDetails.getMessubf());
		funcionario.setAnosubf(funcionarioDetails.getAnosubf());
		funcionario.setDresp(funcionarioDetails.getDresp());
		funcionario.setDdoc(funcionarioDetails.getDdoc());

		return funcionarioRepository.save(funcionario);
	}

	@Override
	public void deleteFuncionario(String resp) {
		Atvfffrein funcionario = getFuncionarioById(resp);
		funcionarioRepository.delete(funcionario);
	}

	@Override
	public List<Atvfffrein> findByAreaContaining(String area) {
		return funcionarioRepository.findByAreaContaining(area);
	}

	@Override
	public List<Atvfffrein> findByMesAno(Integer mes, Integer ano) {
		return funcionarioRepository.findByMessubfAndAnosubf(mes, ano);
	}
}