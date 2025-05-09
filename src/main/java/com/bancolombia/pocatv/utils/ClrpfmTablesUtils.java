package com.bancolombia.pocatv.utils;

import com.bancolombia.pocatv.repository.AtvfftemRepository;
import org.springframework.stereotype.Component;

@Component
public class ClrpfmTablesUtils {

    private AtvfftemRepository atvfftemRepository;
	 

    public void clearAtvfftem() {

        atvfftemRepository.deleteAll();
    }
}
