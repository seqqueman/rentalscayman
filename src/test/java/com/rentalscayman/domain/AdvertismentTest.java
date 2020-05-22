package com.rentalscayman.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.rentalscayman.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class AdvertismentTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Advertisment.class);
        Advertisment advertisment1 = new Advertisment();
        advertisment1.setId(1L);
        Advertisment advertisment2 = new Advertisment();
        advertisment2.setId(advertisment1.getId());
        assertThat(advertisment1).isEqualTo(advertisment2);
        advertisment2.setId(2L);
        assertThat(advertisment1).isNotEqualTo(advertisment2);
        advertisment1.setId(null);
        assertThat(advertisment1).isNotEqualTo(advertisment2);
    }
}
