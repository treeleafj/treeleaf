package org.treeleaf.web.spring.resovler;

import org.treeleaf.web.Result;

/**
 * @Author leaf
 * 2015/8/28 0028 20:53.
 */
public interface ExExceptionHanlder {

    Result invoke(ErrorInfo errorInfo);

}
