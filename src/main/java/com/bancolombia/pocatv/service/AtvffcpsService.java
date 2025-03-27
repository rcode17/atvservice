package com.bancolombia.pocatv.service;

import java.util.List;

import com.bancolombia.pocatv.model.Atvffcps;

public interface AtvffcpsService {
	  List<Atvffcps> findByYearAndMonth(Integer year, Integer month);
}
