package org.treeleaf.web.spring.resovler.converter;

import org.apache.commons.lang3.StringUtils;
import org.treeleaf.web.Json;
import org.treeleaf.web.spring.CommonConstant;
import org.treeleaf.web.spring.resovler.ExceptionConverter;

/**
 * 异常转换的默认简单实现
 * <p/>
 * Created by yaoshuhong on 2015/5/30.
 */
public class DefaultExceptionConvert implements ExceptionConverter<Throwable> {

    /**
     * 当发生非LogicException异常时,默认的提示信息
     */
    private String defaultErrorMsg;

    /**
     * 当发生非logicException时默认的返回码
     */
    private String unkonwnErrorRetCode = CommonConstant.DEFAULT_UNKONWN_ERROR_RETCODE;

    @Override
    public boolean support(Object object) {
        return object instanceof Throwable;
    }

    @Override
    public Object convert(Throwable source) {
        String msg = StringUtils.defaultString(defaultErrorMsg, source.getMessage());
        Json json = new Json(unkonwnErrorRetCode, msg);
        return json;
    }

    public void setDefaultErrorMsg(String defaultErrorMsg) {
        this.defaultErrorMsg = defaultErrorMsg;
    }

    public void setUnkonwnErrorRetCode(String unkonwnErrorRetCode) {
        this.unkonwnErrorRetCode = unkonwnErrorRetCode;
    }
}
