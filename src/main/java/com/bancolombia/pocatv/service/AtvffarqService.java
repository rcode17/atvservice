package com.bancolombia.pocatv.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.bancolombia.pocatv.model.Atvffarq;

public interface AtvffarqService {
	Optional<Atvffarq> findByFields(Integer aqcdsu, String aqcopr, String aqcodo, String aqfear);
	List<Atvffarq> buscarRangosInconsistentes(Integer aqcdsu, String aqcopr, String aqcodo, String aqfear, String aqcedan);
}
