package com.rentalscayman.repository;

import com.rentalscayman.domain.Advertisment;
import com.rentalscayman.web.util.SpecSearchCriteria;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class AdvertismentSpecification implements Specification<Advertisment> {
    private SpecSearchCriteria criteria;

    public SpecSearchCriteria getCriteria() {
        return criteria;
    }

    public AdvertismentSpecification(final SpecSearchCriteria criteria) {
        super();
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Advertisment> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        Path expression = null;
        if (criteria.getKey().contains(".")) {
            String[] names = StringUtils.split(criteria.getKey(), ".");
            expression = root.get(names[0]);
            for (int i = 1; i < names.length; i++) {
                expression = expression.get(names[i]);
            }
        } else {
            expression = root.get(criteria.getKey());
        }

        switch (criteria.getOperation()) {
            case EQUALITY:
                return builder.equal(expression, criteria.getValue());
            case NEGATION:
                return builder.notEqual(expression, criteria.getValue());
            case GREATER_THAN:
                return builder.greaterThan(expression, criteria.getValue().toString());
            case LESS_THAN:
                return builder.lessThan(expression, criteria.getValue().toString());
            case LIKE:
                return builder.like(expression, criteria.getValue().toString());
            case STARTS_WITH:
                return builder.like(expression, criteria.getValue() + "%");
            case ENDS_WITH:
                return builder.like(expression, "%" + criteria.getValue());
            case CONTAINS:
                return builder.like(expression, "%" + criteria.getValue() + "%");
            default:
                return null;
        }
    }
}
