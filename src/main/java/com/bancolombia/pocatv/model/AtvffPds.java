package com.bancolombia.pocatv.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "atvffpds")
public class AtvffPds {
	
	@EmbeddedId
    private AtvffPdsId id;
	
}
