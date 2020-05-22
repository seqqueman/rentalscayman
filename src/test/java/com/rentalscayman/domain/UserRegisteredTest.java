package com.rentalscayman.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.rentalscayman.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class UserRegisteredTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserRegistered.class);
        UserRegistered userRegistered1 = new UserRegistered();
        userRegistered1.setId(1L);
        UserRegistered userRegistered2 = new UserRegistered();
        userRegistered2.setId(userRegistered1.getId());
        assertThat(userRegistered1).isEqualTo(userRegistered2);
        userRegistered2.setId(2L);
        assertThat(userRegistered1).isNotEqualTo(userRegistered2);
        userRegistered1.setId(null);
        assertThat(userRegistered1).isNotEqualTo(userRegistered2);
    }
}
