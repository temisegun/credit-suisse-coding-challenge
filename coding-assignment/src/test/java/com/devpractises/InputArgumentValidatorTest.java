package com.devpractises;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class InputArgumentValidatorTest {

    private InputArgumentValidator underTest;

    @ParameterizedTest
    @CsvSource({
            "le.txt,true",
            "http://xyz.com//static/le.txt?v=e9b34, true",
            "+4474512343300, false"
    })

    public void itShouldValidateInputArgument(String inputArgs, boolean expected){

        underTest = new InputArgumentValidator();
        boolean isValid = underTest.test(inputArgs);
        assertThat(isValid).isEqualTo(expected);

    }




}
