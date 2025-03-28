package com.bancolombia.pocatv.specification;

import org.springframework.data.jpa.domain.Specification;

import com.bancolombia.pocatv.model.AtvffUser;

import jakarta.persistence.criteria.Predicate;


public class AtvffUserSpecification {
	public static Specification<AtvffUser> filterByUsername(String xuUser) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("xuUser")),
                    "%" + xuUser.toLowerCase() + "%"
            );
            return predicate;
        };
    }

}
