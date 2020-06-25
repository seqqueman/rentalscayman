package com.rentalscayman.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsIn.in;
import static org.hamcrest.core.IsNot.not;

import com.rentalscayman.RentalscaymanApp;
import com.rentalscayman.domain.Address;
import com.rentalscayman.domain.Advertisment;
import com.rentalscayman.domain.Feature;
import com.rentalscayman.domain.enumeration.AreaDisctrict;
import com.rentalscayman.domain.enumeration.PropertyType;
import com.rentalscayman.domain.enumeration.TypeAdvertisment;
import com.rentalscayman.domain.enumeration.ViaType;
import com.rentalscayman.web.util.SearchOperation;
import com.rentalscayman.web.util.SpecSearchCriteria;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(classes = RentalscaymanApp.class)
@Transactional
public class JPASpecificationsTest {
    @Autowired
    private AdvertismentRepository advRepository;

    private Advertisment adv1, adv2, adv3;

    @BeforeEach
    public void init() {
        adv1 = new Advertisment();
        adv1.setDescription("the first ad");
        adv1.setActive(false);
        adv1.setPrice(BigDecimal.valueOf(2000));
        adv1.setTypeAd(TypeAdvertisment.FOR_RENT);
        adv1.setPropertyType(PropertyType.BUILDING);

        Feature feat1 = new Feature();
        feat1.setAirConditionair(true);
        feat1.setFullKitchen(true);
        feat1.setm2(100);
        feat1.setNumberBedrooms(3);
        feat1.setNumberBathroom(2);
        adv1.setFeature(feat1);
        Address direc1 = new Address();
        direc1.setAreaDisctrict(AreaDisctrict.BODDEN_TOWN);
        direc1.setName("road_1");
        direc1.setZipCode("01001");
        direc1.setTypeOfVia(ViaType.ROAD);
        adv1.setAddress(direc1);
        advRepository.save(adv1);

        adv2 = new Advertisment();
        adv2.setDescription("the second ad");
        adv2.setActive(false);
        adv2.setPrice(BigDecimal.valueOf(2000));
        adv2.setTypeAd(TypeAdvertisment.FOR_RENT);
        adv2.setPropertyType(PropertyType.BUILDING);

        Feature feat2 = new Feature();
        feat2.setAirConditionair(true);
        feat2.setFullKitchen(true);
        feat2.setm2(120);
        feat2.setNumberBedrooms(2);
        feat2.setNumberBathroom(1);
        adv2.setFeature(feat2);
        Address direc2 = new Address();
        direc2.setAreaDisctrict(AreaDisctrict.BODDEN_TOWN);
        direc2.setZipCode("02002");
        direc2.setName("road_1");
        direc2.setTypeOfVia(ViaType.STREET);
        adv2.setAddress(direc2);
        advRepository.save(adv2);

        adv3 = new Advertisment();
        adv3.setDescription("the first ad");
        adv3.setActive(false);
        adv3.setPrice(BigDecimal.valueOf(2000));
        adv3.setTypeAd(TypeAdvertisment.FOR_RENT);
        adv3.setPropertyType(PropertyType.BUILDING);

        Feature feat3 = new Feature();
        feat3.setAirConditionair(true);
        feat3.setFullKitchen(true);
        feat3.setm2(135);
        feat3.setNumberBedrooms(4);
        feat3.setNumberBathroom(2);
        adv3.setFeature(feat3);
        Address direc3 = new Address();
        direc3.setAreaDisctrict(AreaDisctrict.GEORGE_TOWN);
        direc3.setName("road_1");
        direc3.setZipCode("03003");
        direc3.setTypeOfVia(ViaType.ROAD);
        adv3.setAddress(direc3);
        advRepository.save(adv3);
    }

    @Test
    public void givenDescription_whenEqualsCheckInAndOut() {
        final AdvertismentSpecification spec = new AdvertismentSpecification(
            new SpecSearchCriteria("description", SearchOperation.EQUALITY, "the first ad")
        );
        //        final AdvertismentSpecification spec1 = new AdvertismentSpecification(new SpecSearchCriteria("lastName", SearchOperation.EQUALITY, "doe"));
        final List<Advertisment> results = advRepository.findAll(
            Specification.where(spec)
            //          .and(spec1)
        );

        assertThat(adv1, in(results));
        assertThat(adv2, not(in(results)));
    }

    @Test
    public void givenBedrooms_whenIsEqualOrBiggerNumberResults() {
        final AdvertismentSpecification spec = new AdvertismentSpecification(
            new SpecSearchCriteria("feature.numberBedrooms", SearchOperation.EQUALITY, 3)
        );
        final AdvertismentSpecification spec1 = new AdvertismentSpecification(
            new SpecSearchCriteria("feature.numberBedrooms", SearchOperation.GREATER_THAN, 3)
        );
        final List<Advertisment> results = advRepository.findAll(
            Specification.where(spec).or(spec1) //, null
        );

        assertThat(adv1, in(results));
        assertThat(adv2, not(in(results)));
        assertThat(results, hasSize(2));
    }

    @Test
    public void givenDistrictArea_whenIsEqual_CorrectResults() {
        final AdvertismentSpecification spec = new AdvertismentSpecification(
            new SpecSearchCriteria(
                "address.areaDisctrict",
                SearchOperation.EQUALITY,
                "GEORGE_TOWN"
                /*AreaDisctrict.valueOf( "GEORGE_TOWN")*/
            )
        );
        //        final AdvertismentSpecification spec1 = new AdvertismentSpecification(new SpecSearchCriteria("feature.numberBedrooms", SearchOperation.GREATER_THAN, 2));
        final List<Advertisment> results = advRepository.findAll(
            Specification.where(spec)
            //          .or(spec1)
            //          , null
        );

        assertThat(adv1, not(in(results)));
        assertThat(adv2, not(in(results)));
        assertThat(results, hasSize(1));
    }
}
