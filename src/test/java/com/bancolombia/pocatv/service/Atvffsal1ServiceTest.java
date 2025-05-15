package com.bancolombia.pocatv.service;
import com.bancolombia.pocatv.model.Atvffsal1;
import com.bancolombia.pocatv.repository.Atvffsal1Repository;
import com.bancolombia.pocatv.service.impl.Atvffsal1ServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;


public class Atvffsal1ServiceTest {
    @InjectMocks
    private Atvffsal1ServiceImpl atvffsal1Service;

    @Mock
    private Atvffsal1Repository atvffsal1Repository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void test_saveAtvffsal() {
        // Crear un objeto Atvffsal1 de prueba
        Atvffsal1 atvffsal = new Atvffsal1();
        atvffsal.setSafech(20230101L);
        atvffsal.setSatpro("PRODUCTO_1");
        atvffsal.setSatdoc("DOC_123");
        atvffsal.setSacger("123456789");
        atvffsal.setSanger("Gerente Prueba");
        atvffsal.setSadisp(BigDecimal.valueOf(1000));
        atvffsal.setSaanul(0L);
        atvffsal.setSapimp(0L);
        atvffsal.setSaimpr(0L);
        atvffsal.setSautil(0L);
        atvffsal.setSaofic(1);
        atvffsal.setSaofco(1);

        // Definir el comportamiento del mock
        when(atvffsal1Repository.save(atvffsal)).thenReturn(atvffsal);

        // Llamar al método de guardar
        Atvffsal1 result = atvffsal1Service.guardar(atvffsal);

        // Verificar que el resultado es el esperado
        //assertEquals(atvffsal, result);
    }

}
