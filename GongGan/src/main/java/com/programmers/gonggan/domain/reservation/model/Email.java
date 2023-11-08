package com.programmers.gonggan.domain.reservation.model;

import com.programmers.gonggan.domain.reservation.exception.EmailTypeNotMatchException;
import lombok.Getter;

import java.util.Objects;
import java.util.regex.Pattern;

@Getter
public class Email {
    private static final Pattern PATTERN = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
    private final String email;

    private Email(String email) {
        this.email = email;
    }

    public static Email from(String value) {
        if (!PATTERN.matcher(value).matches()) {
            throw new EmailTypeNotMatchException();
        }
        return new Email(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email1 = (Email) o;
        return Objects.equals(email, email1.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
