package com.bancolombia.pocatv.specification;

import org.springframework.data.jpa.domain.Specification;

import com.bancolombia.pocatv.model.AtvffPdo;

import jakarta.persistence.criteria.Predicate;

public class AtvffPdoSpecification {
	public static Specification<AtvffPdo> filterByNameProduct(String xpDsdo) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("xpDsdo")),
                    "%" + xpDsdo.toLowerCase() + "%"
            );
            return predicate;
        };
    }

}
