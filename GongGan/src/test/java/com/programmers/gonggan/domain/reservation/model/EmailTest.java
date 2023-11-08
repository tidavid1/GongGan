package com.programmers.gonggan.domain.reservation.model;

import com.programmers.gonggan.common.exception.ErrorCode;
import com.programmers.gonggan.domain.reservation.exception.EmailTypeNotMatchException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Test Email VO")
class EmailTest {

    @Test
    void testFromSuccess() {
        // Arrange
        String expectedEmailValue = "test@gmail.com";
        // Act
        Email actualResult = Email.from(expectedEmailValue);
        // Assert
        assertThat(actualResult.getEmail()).isEqualTo(expectedEmailValue);
    }

    @Test
    void testFromFail() {
        // Arrange
        String expectedEmailValue = "notEmail";
        // Act & Assert
        Throwable actualResult = assertThrows(EmailTypeNotMatchException.class, () -> Email.from(expectedEmailValue));
        assertThat(actualResult).hasMessage(ErrorCode.EMAIL_TYPE_NOT_MATCH.getMessage());
    }

    @Test
    void testEquals() {
        // Arrange
        String expectedEmailValue = "test@gmail.com";
        // Act
        Email actualResult1 = Email.from(expectedEmailValue);
        Email actualResult2 = Email.from(expectedEmailValue);
        // Assert
        assertThat(actualResult1).isEqualTo(actualResult2);
    }
}