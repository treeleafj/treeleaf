package org.treeleaf.db.handler;

import org.treeleaf.db.model.Model;
import org.apache.commons.dbutils.RowProcessor;
import org.apache.commons.dbutils.handlers.BeanHandler;

/**
 * @Author leaf
 * 2015/1/8 0008 21:37.
 */
public class AnnotationBeanHandler<T extends Model> extends BeanHandler<T> {

    private static final RowProcessor ROW_PROCESSOR = new AnnotationRowProcessor();

    public AnnotationBeanHandler(Class<T> type) {
        super(type, ROW_PROCESSOR);
    }
}
