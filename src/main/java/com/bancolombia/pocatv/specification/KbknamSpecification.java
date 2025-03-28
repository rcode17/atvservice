package com.bancolombia.pocatv.specification;

import org.springframework.data.jpa.domain.Specification;

import com.bancolombia.pocatv.model.Xbknam;

import jakarta.persistence.criteria.Predicate;


public class KbknamSpecification {
	public static Specification<Xbknam> filterByName(String xnname) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("xnname")),
                    "%" + xnname.toLowerCase() + "%"
            );
            return predicate;
        };
    }

}
