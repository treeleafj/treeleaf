package org.treeleaf.web.spring.resovler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yaoshuhong on 2015/5/30.
 */
public abstract class AbstractBaseExceptionExtHandler implements ExceptionExtHandler {

    /**
     * 异常转换器,用于将错误信息转义为别的格式输出
     */
    private List<Converter> exceptionConverters = new ArrayList<Converter>();

    public void setExceptionClassConverters(List<Class> exceptionConverterClasss) {

        try {
            for (Class<? extends Converter> classz : exceptionConverterClasss) {
                this.exceptionConverters.add(classz.newInstance());
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public List<Converter> getExceptionConverters() {
        return exceptionConverters;
    }

    public void setExceptionConverters(List<Converter> exceptionConverters) {
        this.exceptionConverters = exceptionConverters;
    }
}
