package GoGetters.GoGetter.config;

import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.format.DateTimeFormatter;

//public class DateTimeFormatConfiguration implements WebMvcConfigurer {
//    @Override
//    public void addFormatters(FormatterRegistry registry) {
//        DateTimeFormatterRegistrar registrar=new DateTimeFormatterRegistrar();
//        registrar.setDateFormatter(DateTimeFormatter.ISO_DATE);
//        registrar.setTimeFormatter(DateTimeFormatter.ISO_TIME);
//    }
//
//}
