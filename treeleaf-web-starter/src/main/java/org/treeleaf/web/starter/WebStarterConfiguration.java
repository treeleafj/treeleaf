package org.treeleaf.web.starter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.treeleaf.web.spring.handler.*;
import org.treeleaf.web.spring.interceptor.MultipleHandlerInerceptor;
import org.treeleaf.web.spring.interceptor.PrintLogHandlerInerceptor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yaoshuhong
 * @date 2016-09-14 12:47
 */
@Configuration
@ComponentScan(basePackages = "org.treeleaf.web.starter")
@EnableConfigurationProperties(WebStarterConfigurationProperties.class)
public class WebStarterConfiguration {

    private static Logger log = LoggerFactory.getLogger(WebStarterConfiguration.class);

    @Autowired
    private WebStarterConfigurationProperties webStarterConfigurationProperties;

    @Bean
    public PrintLogHandlerInerceptor printLogHandlerInerceptor() {
        return new PrintLogHandlerInerceptor();
    }

    @Bean
    public ParamHandlerMethodArgumentResolver paramHandlerMethodArgumentResolver() {
        return new ParamHandlerMethodArgumentResolver();
    }

    @Bean
    public ClientInfoHandlerMethodArgumentResolver clientInfoHandlerMethodArgumentResolver() {
        return new ClientInfoHandlerMethodArgumentResolver();
    }

    @Bean
    public TextHandlerMethodReturnValueHandler textHandlerMethodReturnValueHandler() {
        return new TextHandlerMethodReturnValueHandler();
    }

    @Bean
    public HtmlHandlerMethodReturnValueHandler htmlHandlerMethodReturnValueHandler() {
        return new HtmlHandlerMethodReturnValueHandler();
    }

    @Bean
    public RedirectHandlerMethodReturnValueHandler redirectHandlerMethodReturnValueHandler() {
        return new RedirectHandlerMethodReturnValueHandler();
    }

    @Bean
    public WebMvcConfigurerAdapter configStaticMapping() {

        log.info("配置treeleaf spring-mvc扩展");

        return new WebMvcConfigurerAdapter() {

            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
            }

            @Override
            public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
                argumentResolvers.add(new ParamHandlerMethodArgumentResolver());
                argumentResolvers.add(new ClientInfoHandlerMethodArgumentResolver());
                super.addArgumentResolvers(argumentResolvers);
            }

            @Override
            public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
                returnValueHandlers.add(new TextHandlerMethodReturnValueHandler());
                returnValueHandlers.add(new HtmlHandlerMethodReturnValueHandler());
                returnValueHandlers.add(new RedirectHandlerMethodReturnValueHandler());
                super.addReturnValueHandlers(returnValueHandlers);
            }

            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                List list = new ArrayList<>(2);
                list.add(printLogHandlerInerceptor());
                MultipleHandlerInerceptor multipleHandlerInerceptor = new MultipleHandlerInerceptor();
                multipleHandlerInerceptor.setHandlers(list);
                registry.addInterceptor(multipleHandlerInerceptor);
                super.addInterceptors(registry);
            }
        };
    }
}
