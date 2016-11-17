package org.treeleaf.db.handler;

import org.apache.commons.dbutils.RowProcessor;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.treeleaf.db.model.Model;

/**
 * @Author leaf
 * 2015/1/8 0008 22:13.
 */
public class AnnotationBeanListHandler<T extends Model> extends BeanListHandler<T> {

    private static final RowProcessor ROW_PROCESSOR = new AnnotationRowProcessor();

    public AnnotationBeanListHandler(Class<T> type) {
        super(type, ROW_PROCESSOR);
    }
}
