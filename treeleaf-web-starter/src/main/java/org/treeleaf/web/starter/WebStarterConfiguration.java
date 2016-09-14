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
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.treeleaf.common.json.Jsoner;
import org.treeleaf.web.spring.handler.ClientInfoHandlerMethodArgumentResolver;
import org.treeleaf.web.spring.handler.HtmlHandlerMethodReturnValueHandler;
import org.treeleaf.web.spring.handler.ParamHandlerMethodArgumentResolver;
import org.treeleaf.web.spring.handler.RedirectHandlerMethodReturnValueHandler;
import org.treeleaf.web.spring.handler.TextHandlerMethodReturnValueHandler;
import org.treeleaf.web.spring.resovler.ExHandlerExceptionResolver;
import org.treeleaf.web.spring.resovler.ExtDefaultExceptionHandler;

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
    public WebMvcConfigurerAdapter configStaticMapping() {

        log.info("配置url");

        return new WebMvcConfigurerAdapter() {
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
//                log.info("增加跳转");
//                //配置跳转
//                registry.addViewController("/nav").setViewName("forward:/nav.html");
            }

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
            public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {


                log.info("treeleaf-web-starter config:{}", Jsoner.toJson(webStarterConfigurationProperties));

                ExtDefaultExceptionHandler exExceptionHanlder = new ExtDefaultExceptionHandler();
                exExceptionHanlder.setTip(webStarterConfigurationProperties.getErrorTip());
                exExceptionHanlder.setErrorView(webStarterConfigurationProperties.getErrorView());
                exExceptionHanlder.setRedirect(webStarterConfigurationProperties.isErrorRedirect());

                ExHandlerExceptionResolver handler = new ExHandlerExceptionResolver();
                handler.setStatus(200);
                handler.setExExceptionHanlder(exExceptionHanlder);
                exceptionResolvers.add(0, handler);

                super.configureHandlerExceptionResolvers(exceptionResolvers);
            }
        };
    }
}
