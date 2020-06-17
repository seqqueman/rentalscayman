package com.rentalscayman.repository;

import com.rentalscayman.domain.Advertisment;
import com.rentalscayman.web.util.SearchOperation;
import com.rentalscayman.web.util.SpecSearchCriteria;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

public class AdvertismentSpecificationsBuilder {
    private final List<SpecSearchCriteria> params;

    public AdvertismentSpecificationsBuilder() {
        params = new ArrayList<>();
    }

    public final AdvertismentSpecificationsBuilder with(
        final String orPredicate,
        final String key,
        final String operation,
        final Object value,
        final String prefix,
        final String suffix
    ) {
        SearchOperation op = SearchOperation.getSimpleOperation(operation.charAt(0));
        if (op != null) {
            if (op == SearchOperation.EQUALITY) { // the operation may be complex operation
                final boolean startWithAsterisk = prefix != null && prefix.contains(SearchOperation.ZERO_OR_MORE_REGEX);
                final boolean endWithAsterisk = suffix != null && suffix.contains(SearchOperation.ZERO_OR_MORE_REGEX);

                if (startWithAsterisk && endWithAsterisk) {
                    op = SearchOperation.CONTAINS;
                } else if (startWithAsterisk) {
                    op = SearchOperation.ENDS_WITH;
                } else if (endWithAsterisk) {
                    op = SearchOperation.STARTS_WITH;
                }
            }
            params.add(new SpecSearchCriteria(orPredicate, key, op, value));
        }
        return this;
    }

    public Specification<Advertisment> build() {
        if (params.size() == 0) return null;

        Specification<Advertisment> result = new AdvertismentSpecification(params.get(0));

        for (int i = 1; i < params.size(); i++) {
            result =
                params.get(i).isOrPredicate()
                    ? Specification.where(result).or(new AdvertismentSpecification(params.get(i)))
                    : Specification.where(result).and(new AdvertismentSpecification(params.get(i)));
        }

        return result;
    }

    public final AdvertismentSpecificationsBuilder with(AdvertismentSpecification spec) {
        params.add(spec.getCriteria());
        return this;
    }

    public final AdvertismentSpecificationsBuilder with(SpecSearchCriteria criteria) {
        params.add(criteria);
        return this;
    }
}
