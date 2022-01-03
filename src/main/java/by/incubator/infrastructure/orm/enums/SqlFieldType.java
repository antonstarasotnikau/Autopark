package by.incubator.infrastructure.orm.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@Getter
public enum SqlFieldType {
    INTEGER(Integer.class, "integer", "%s"),
    LONG(Long.class,"integer","%s"),
    BIGDECIMAL(BigDecimal.class,"decimal","%s"),
    STRING(String.class,"varchar(255)","'%s'"),
    DATE(Date.class,"date","'%s'");

    private final Class<?> type;
    private final String sqlType;
    private final String insertPattern;
}
