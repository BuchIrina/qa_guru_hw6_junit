package com.buchneva;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SimpleJUnitExamplesTest {
    @Disabled
    @Test
    void SimpleTest0() {
        Assertions.assertTrue(2 < 3);
    }

    @DisplayName("Check that profile page will be open ...")
    @Test
    void profilePageShouldBeOpenAfterClickByButton() {
        Assertions.assertTrue(2 < 3);
    }

    @Test
    void SimpleTest2() {
        Assertions.assertTrue(2 < 3);
    }

}
