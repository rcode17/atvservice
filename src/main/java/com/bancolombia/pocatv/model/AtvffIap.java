package com.bancolombia.pocatv.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ATVFFIAP")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AtvffIap {
    
    @EmbeddedId
    private AtvffIapId id;
    
    @Column(name = "IPDOCUMENT", length = 25)
    private String ipDocument;
    
    @Column(name = "IPPROMA")
    private Integer ipProma;
    
    @Column(name = "IPENERO")
    private Integer ipEnero;
    
    @Column(name = "IPFEB")
    private Integer ipFeb;
    
    @Column(name = "IPMARZO")
    private Integer ipMarzo;
    
    @Column(name = "IPABRIL")
    private Integer ipAbril;
    
    @Column(name = "IPMAYO")
    private Integer ipMayo;
    
    @Column(name = "IPJUNIO")
    private Integer ipJunio;
    
    @Column(name = "IPJULIO")
    private Integer ipJulio;
    
    @Column(name = "IPAGOSTO")
    private Integer ipAgosto;
    
    @Column(name = "IPSEP")
    private Integer ipSep;
    
    @Column(name = "IPOCTUBRE")
    private Integer ipOctubre;
    
    @Column(name = "IPNOV")
    private Integer ipNov;
    
    @Column(name = "IPDIC")
    private Integer ipDic;
    
    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AtvffIapId {
        @Column(name = "IPANOS")
        private Integer ipAnos;
        
        @Column(name = "IPCODPRO")
        private Integer ipCodpro;
        
        @Column(name = "IPCODDOC")
        private Integer ipCoddoc;
    }
}

