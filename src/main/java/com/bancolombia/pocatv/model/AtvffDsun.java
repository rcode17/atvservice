package com.bancolombia.pocatv.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "atvffdsun")
@IdClass(AtvffDsunId.class)
public class AtvffDsun {
    
    @Id
    @Column(name = "dnano", nullable = false)
    private Integer dnano;
    
    @Id
    @Column(name = "dnmes", nullable = false)
    private Integer dnmes;
    
    @Id
    @Column(name = "dnxnnmky", nullable = false)
    private Integer dnxnnmky;
    
    @Column(name = "dncumplit")
    private Integer dncumplit;
    
    @Column(name = "dncalidt")
    private Integer dncalidt;
    
    @Column(name = "dnnombre", length = 30)
    private String dnnombre;
    
    // Campos de cumplimiento y calidad (1-70)
    @Column(name = "DNCUMPLI1")
    private String dncumpli1;
    
    @Column(name = "DNCALID1")
    private String dncalid1;
    
    @Column(name = "DNCUMPLI2")
    private String dncumpli2;
    
    @Column(name = "DNCALID2")
    private String dncalid2;
    
    @Column(name = "DNCUMPLI3")
    private String dncumpli3;
    
    @Column(name = "DNCALID3")
    private String dncalid3;
    
    @Column(name = "DNCUMPLI4")
    private String dncumpli4;
    
    @Column(name = "DNCALID4")
    private String dncalid4;
    
    @Column(name = "DNCUMPLI5")
    private String dncumpli5;
    
    @Column(name = "DNCALID5")
    private String dncalid5;
    
    @Column(name = "DNCUMPLI6")
    private String dncumpli6;
    
    @Column(name = "DNCALID6")
    private String dncalid6;
    
    @Column(name = "DNCUMPLI7")
    private String dncumpli7;
    
    @Column(name = "DNCALID7")
    private String dncalid7;
    
    @Column(name = "DNCUMPLI8")
    private String dncumpli8;
    
    @Column(name = "DNCALID8")
    private String dncalid8;
    
    @Column(name = "DNCUMPLI9")
    private String dncumpli9;
    
    @Column(name = "DNCALID9")
    private String dncalid9;
    
    @Column(name = "DNCUMPLI10")
    private String dncumpli10;
    
    @Column(name = "DNCALID10")
    private String dncalid10;
    
    @Column(name = "DNCUMPLI11")
    private String dncumpli11;
    
    @Column(name = "DNCALID11")
    private String dncalid11;
    
    @Column(name = "DNCUMPLI12")
    private String dncumpli12;
    
    @Column(name = "DNCALID12")
    private String dncalid12;
    
    @Column(name = "DNCUMPLI13")
    private String dncumpli13;
    
    @Column(name = "DNCALID13")
    private String dncalid13;
    
    @Column(name = "DNCUMPLI14")
    private String dncumpli14;
    
    @Column(name = "DNCALID14")
    private String dncalid14;
    
    @Column(name = "DNCUMPLI15")
    private String dncumpli15;
    
    @Column(name = "DNCALID15")
    private String dncalid15;
    
    @Column(name = "DNCUMPLI16")
    private String dncumpli16;
    
    @Column(name = "DNCALID16")
    private String dncalid16;
    
    @Column(name = "DNCUMPLI17")
    private String dncumpli17;
    
    @Column(name = "DNCALID17")
    private String dncalid17;
    
    @Column(name = "DNCUMPLI18")
    private String dncumpli18;
    
    @Column(name = "DNCALID18")
    private String dncalid18;
    
    @Column(name = "DNCUMPLI19")
    private String dncumpli19;
    
    @Column(name = "DNCALID19")
    private String dncalid19;
    
    @Column(name = "DNCUMPLI20")
    private String dncumpli20;
    
    @Column(name = "DNCALID20")
    private String dncalid20;
    
    @Column(name = "DNCUMPLI21")
    private String dncumpli21;
    
    @Column(name = "DNCALID21")
    private String dncalid21;
    
    @Column(name = "DNCUMPLI22")
    private String dncumpli22;
    
    @Column(name = "DNCALID22")
    private String dncalid22;
    
    @Column(name = "DNCUMPLI23")
    private String dncumpli23;
    
    @Column(name = "DNCALID23")
    private String dncalid23;
    
    @Column(name = "DNCUMPLI24")
    private String dncumpli24;
    
    @Column(name = "DNCALID24")
    private String dncalid24;
    
    @Column(name = "DNCUMPLI25")
    private String dncumpli25;
    
    @Column(name = "DNCALID25")
    private String dncalid25;
    
    @Column(name = "DNCUMPLI26")
    private String dncumpli26;
    
    @Column(name = "DNCALID26")
    private String dncalid26;
    
    @Column(name = "DNCUMPLI27")
    private String dncumpli27;
    
    @Column(name = "DNCALID27")
    private String dncalid27;
    
    @Column(name = "DNCUMPLI28")
    private String dncumpli28;
    
    @Column(name = "DNCALID28")
    private String dncalid28;
    
    @Column(name = "DNCUMPLI29")
    private String dncumpli29;
    
    @Column(name = "DNCALID29")
    private String dncalid29;
    
    @Column(name = "DNCUMPLI30")
    private String dncumpli30;
    
    @Column(name = "DNCALID30")
    private String dncalid30;
    
    @Column(name = "DNCUMPLI31")
    private String dncumpli31;
    
    @Column(name = "DNCALID31")
    private String dncalid31;
    
    @Column(name = "DNCUMPLI32")
    private String dncumpli32;
    
    @Column(name = "DNCALID32")
    private String dncalid32;
    
    @Column(name = "DNCUMPLI33")
    private String dncumpli33;
    
    @Column(name = "DNCALID33")
    private String dncalid33;
    
    @Column(name = "DNCUMPLI34")
    private String dncumpli34;
    
    @Column(name = "DNCALID34")
    private String dncalid34;
    
    @Column(name = "DNCUMPLI35")
    private String dncumpli35;
    
    @Column(name = "DNCALID35")
    private String dncalid35;
    
    @Column(name = "DNCUMPLI36")
    private String dncumpli36;
    
    @Column(name = "DNCALID36")
    private String dncalid36;
    
    @Column(name = "DNCUMPLI37")
    private String dncumpli37;
    
    @Column(name = "DNCALID37")
    private String dncalid37;
    
    @Column(name = "DNCUMPLI38")
    private String dncumpli38;
    
    @Column(name = "DNCALID38")
    private String dncalid38;
    
    @Column(name = "DNCUMPLI39")
    private String dncumpli39;
    
    @Column(name = "DNCALID39")
    private String dncalid39;
    
    @Column(name = "DNCUMPLI40")
    private String dncumpli40;
    
    @Column(name = "DNCALID40")
    private String dncalid40;
    
    @Column(name = "DNCUMPLI41")
    private String dncumpli41;
    
    @Column(name = "DNCALID41")
    private String dncalid41;
    
    @Column(name = "DNCUMPLI42")
    private String dncumpli42;
    
    @Column(name = "DNCALID42")
    private String dncalid42;
    
    @Column(name = "DNCUMPLI43")
    private String dncumpli43;
    
    @Column(name = "DNCALID43")
    private String dncalid43;
    
    @Column(name = "DNCUMPLI44")
    private String dncumpli44;
    
    @Column(name = "DNCALID44")
    private String dncalid44;
    
    @Column(name = "DNCUMPLI45")
    private String dncumpli45;
    
    @Column(name = "DNCALID45")
    private String dncalid45;
    
    @Column(name = "DNCUMPLI46")
    private String dncumpli46;
    
    @Column(name = "DNCALID46")
    private String dncalid46;
    
    @Column(name = "DNCUMPLI47")
    private String dncumpli47;
    
    @Column(name = "DNCALID47")
    private String dncalid47;
    
    @Column(name = "DNCUMPLI48")
    private String dncumpli48;
    
    @Column(name = "DNCALID48")
    private String dncalid48;
    
    @Column(name = "DNCUMPLI49")
    private String dncumpli49;
    
    @Column(name = "DNCALID49")
    private String dncalid49;
    
    @Column(name = "DNCUMPLI50")
    private String dncumpli50;
    
    @Column(name = "DNCALID50")
    private String dncalid50;
    
    @Column(name = "DNCUMPLI51")
    private String dncumpli51;
    
    @Column(name = "DNCALID51")
    private String dncalid51;
    
    @Column(name = "DNCUMPLI52")
    private String dncumpli52;
    
    @Column(name = "DNCALID52")
    private String dncalid52;
    
    @Column(name = "DNCUMPLI53")
    private String dncumpli53;
    
    @Column(name = "DNCALID53")
    private String dncalid53;
    
    @Column(name = "DNCUMPLI54")
    private String dncumpli54;
    
    @Column(name = "DNCALID54")
    private String dncalid54;
    
    @Column(name = "DNCUMPLI55")
    private String dncumpli55;
    
    @Column(name = "DNCALID55")
    private String dncalid55;
    
    @Column(name = "DNCUMPLI56")
    private String dncumpli56;
    
    @Column(name = "DNCALID56")
    private String dncalid56;
    
    @Column(name = "DNCUMPLI57")
    private String dncumpli57;
    
    @Column(name = "DNCALID57")
    private String dncalid57;
    
    @Column(name = "DNCUMPLI58")
    private String dncumpli58;
    
    @Column(name = "DNCALID58")
    private String dncalid58;
    
    @Column(name = "DNCUMPLI59")
    private String dncumpli59;
    
    @Column(name = "DNCALID59")
    private String dncalid59;
    
    @Column(name = "DNCUMPLI60")
    private String dncumpli60;
    
    @Column(name = "DNCALID60")
    private String dncalid60;
    
    @Column(name = "DNCUMPLI61")
    private String dncumpli61;
    
    @Column(name = "DNCALID61")
    private String dncalid61;
    
    @Column(name = "DNCUMPLI62")
    private String dncumpli62;
    
    @Column(name = "DNCALID62")
    private String dncalid62;
    
    @Column(name = "DNCUMPLI63")
    private String dncumpli63;
    
    @Column(name = "DNCALID63")
    private String dncalid63;
    
    @Column(name = "DNCUMPLI64")
    private String dncumpli64;
    
    @Column(name = "DNCALID64")
    private String dncalid64;
    
    @Column(name = "DNCUMPLI65")
    private String dncumpli65;
    
    @Column(name = "DNCALID65")
    private String dncalid65;
    
    @Column(name = "DNCUMPLI66")
    private String dncumpli66;
    
    @Column(name = "DNCALID66")
    private String dncalid66;
    
    @Column(name = "DNCUMPLI67")
    private String dncumpli67;
    
    @Column(name = "DNCALID67")
    private String dncalid67;
    
    @Column(name = "DNCUMPLI68")
    private String dncumpli68;
    
    @Column(name = "DNCALID68")
    private String dncalid68;
    
    @Column(name = "DNCUMPLI69")
    private String dncumpli69;
    
    @Column(name = "DNCALID69")
    private String dncalid69;
    
    @Column(name = "DNCUMPLI70")
    private String dncumpli70;
    
    @Column(name = "DNCALID70")
    private String dncalid70;
    
    // Método para verificar si un campo de calidad es menor o igual a 50 y no es "NA"
    public boolean isCalidadBaja(String calidad) {
        if (calidad == null || "NA ".equals(calidad.trim())) {
            return false;
        }
        try {
            int valor = Integer.parseInt(calidad.trim());
            return valor <= 50;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * Método para asignar un objeto AtvffDsunId a esta instancia.
     *
     * @param id El objeto AtvffDsunId que contiene los valores de la clave primaria.
     */
    public void setId(AtvffDsunId id) {
        if (id != null) {
            this.dnano = id.getDnano();
            this.dnmes = id.getDnmes();
            this.dnxnnmky = id.getDnxnnmky();
        }
    }
    
    /**
     * Método para obtener un objeto AtvffDsunId a partir de esta instancia.
     *
     * @return Un objeto AtvffDsunId con los valores de la clave primaria.
     */
    public AtvffDsunId getId() {
        return new AtvffDsunId(this.dnano, this.dnmes, this.dnxnnmky);
    }
}