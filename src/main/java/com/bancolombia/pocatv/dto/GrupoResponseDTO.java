package com.bancolombia.pocatv.dto;

import java.math.BigDecimal;
import java.util.List;

import com.bancolombia.pocatv.dto.MesResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GrupoResponseDTO {
   private Integer oaxnnmky;
   private Double promedioAnualCum;
   private Double promedioAnualCal;
   private List<MesResponseDTO> meses;
}
