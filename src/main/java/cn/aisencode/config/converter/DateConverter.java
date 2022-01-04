package cn.aisencode.config.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 日期转换
 * @author Aisen
 * @time 2021.6.3 10:06
 */

@Component
public class DateConverter implements Converter<String, LocalDateTime> {
    @Override
    public LocalDateTime convert(String source) {
        try {
            return LocalDateTime.parse(source, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
