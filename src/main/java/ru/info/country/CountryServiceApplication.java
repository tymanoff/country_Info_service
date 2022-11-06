package ru.info.country;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.info.country.configuration.CountryServiceProperties;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableConfigurationProperties(CountryServiceProperties.class)
public class CountryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CountryServiceApplication.class, args);
    }

}
