package org.treeleaf.web.starter;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Created by leaf on 2016/12/2 0002.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({BasicExceptionHandler.class})
public @interface EnableExceptionHandler {
}
