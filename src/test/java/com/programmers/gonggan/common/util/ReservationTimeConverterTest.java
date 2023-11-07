package com.programmers.gonggan.common.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Test Reservation Time Converter")
class ReservationTimeConverterTest {

    @Test
    void testConvertWhenNotOnTime(){
        // Arrange
        LocalDateTime expectedResult = LocalDateTime.parse("2007-12-03T10:15:30");
        // Act
        LocalDateTime actualResult = ReservationTimeConverter.convert(expectedResult);
        // Assert
        assertThat(actualResult).hasMinute(0);
        assertThat(actualResult).hasSecond(0);
        assertThat(actualResult).hasNano(0);
        assertThat(actualResult).hasYear(expectedResult.getYear());
        assertThat(actualResult).hasMonth(expectedResult.getMonth());
        assertThat(actualResult).hasDayOfMonth(expectedResult.getDayOfMonth());
        assertThat(actualResult).hasHour(expectedResult.getHour());
    }
}