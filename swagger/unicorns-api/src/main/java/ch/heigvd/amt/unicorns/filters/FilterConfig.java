package ch.heigvd.amt.unicorns.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Autowired
    TokenFilter tokenFilter;

    @Bean
    public FilterRegistrationBean tokenFilterConfig() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();

        registrationBean.setFilter(tokenFilter);
        registrationBean.addUrlPatterns("/unicorns/*", "/unicorns", "/magics", "/magics/*");

        return registrationBean;
    }
}
