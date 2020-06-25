package com.rentalscayman.repository;

import com.rentalscayman.domain.Advertisment;
import com.rentalscayman.domain.enumeration.AreaDisctrict;
import com.rentalscayman.domain.enumeration.PropertyType;
import com.rentalscayman.domain.enumeration.TypeAdvertisment;
import com.rentalscayman.domain.enumeration.ViaType;
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

        Object construct = criteria.getValue();

        if (isEnumerador(criteria.getKey())) {
            construct = getWithEnum(criteria.getKey(), criteria.getValue().toString());
        }

        switch (criteria.getOperation()) {
            case EQUALITY:
                return builder.equal(expression, construct);
            case NEGATION:
                return builder.notEqual(expression, construct);
            case GREATER_THAN:
                return builder.greaterThan(expression, construct.toString());
            case LESS_THAN:
                return builder.lessThan(expression, construct.toString());
            case LIKE:
                return builder.like(expression, criteria.getValue().toString());
            case STARTS_WITH:
                return builder.like(expression, construct + "%");
            case ENDS_WITH:
                return builder.like(expression, "%" + construct);
            case CONTAINS:
                return builder.like(expression, "%" + construct + "%");
            default:
                return null;
        }
    }

    private Object getWithEnum(String key, String value) {
        switch (key) {
            case "typeAd":
                return TypeAdvertisment.valueOf(value);
            case "propertyType":
                return PropertyType.valueOf(value);
            case "viaType":
                return ViaType.valueOf(value);
            default:
                return AreaDisctrict.valueOf(value);
        }
    }

    private boolean isEnumerador(String key) {
        if (key.indexOf("ype") > -1 || key.indexOf("isctric") > -1) {
            return true;
        }
        return false;
    }
}
