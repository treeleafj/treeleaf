package org.treeleaf.db.sql;

import org.apache.commons.lang3.StringUtils;
import org.treeleaf.common.bean.FastBeanUtils;
import org.treeleaf.db.meta.DBColumnMeta;
import org.treeleaf.db.meta.DBTableMeta;
import org.treeleaf.db.meta.DBTableMetaFactory;
import org.treeleaf.db.model.example.Criteria;
import org.treeleaf.db.model.example.Criterion;
import org.treeleaf.db.model.example.Example;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 默认Sql解析器
 * <p>基于SqlBuilder实现</p>
 * Created by leaf on 2015/1/4 0004.
 */
public abstract class DefaultSqlAnalyzerImpl implements SqlAnalyzer {

    @Override
    public String analyzeSelectByPrimaryKey(DBTableMeta dbTableMeta) {

        if (dbTableMeta.getPrimaryKeys().size() == 0) {
            throw new RuntimeException("类型" + dbTableMeta.getModelClass().getName() + "未声明主键");
        }
        StringBuilder sb = new StringBuilder("select * from ");
        sb.append(dbTableMeta.getName());
        sb.append(" where ");
        for (int i = 0; i < dbTableMeta.getPrimaryKeys().size(); i++) {
            if (i > 0) {
                sb.append(" and ");
            }
            DBColumnMeta dbColumnMeta = dbTableMeta.getPrimaryKeys().get(i);
            sb.append(dbColumnMeta.getName());
            sb.append(" = ?");
        }
        return sb.toString();
    }

    @Override
    public String analyzeDeleteByPrimaryKey(DBTableMeta dbTableMeta) {
        if (dbTableMeta.getPrimaryKeys().size() == 0) {
            throw new RuntimeException("类型" + dbTableMeta.getName() + "未声明主键");
        }
        StringBuilder sb = new StringBuilder("delete from ");
        sb.append(dbTableMeta.getName());
        sb.append(" where ");
        for (int i = 0; i < dbTableMeta.getPrimaryKeys().size(); i++) {
            if (i > 0) {
                sb.append(" and ");
            }
            DBColumnMeta dbColumnMeta = dbTableMeta.getPrimaryKeys().get(i);
            sb.append(dbColumnMeta.getName());
            sb.append(" = ?");
        }
        return sb.toString();
    }

    @Override
    public AnalyzeResult analyzeUpdateByPrimaryKey(DBTableMeta dbTableMeta, Object model) {

        if (dbTableMeta.getPrimaryKeys().size() == 0) {
            throw new RuntimeException("类型" + dbTableMeta.getName() + "未声明主键");
        }

        List<Object> params = new ArrayList<Object>();

        StringBuilder sb = new StringBuilder("update ");
        sb.append(dbTableMeta.getName());
        sb.append(" set ");
        int length = sb.length();

        for (int i = 0; i < dbTableMeta.getColumnMetas().size(); i++) {
            DBColumnMeta dbColumnMeta = dbTableMeta.getColumnMetas().get(i);
            if (!dbColumnMeta.isPrimaryKey()) {
                sb.append('`');
                sb.append(dbColumnMeta.getName());
                sb.append('`');
                sb.append(" = ?,");

                Field field = dbColumnMeta.getField();
                params.add(FastBeanUtils.getFieldValue(field, model));
            }
        }

        if (length == sb.length()) {
            throw new RuntimeException("类型" + dbTableMeta.getName() + "中未定义非主键字段");
        }

        sb.deleteCharAt(sb.length() - 1);
        sb.append(" where ");

        for (int i = 0; i < dbTableMeta.getPrimaryKeys().size(); i++) {
            if (i > 0) {
                sb.append(" and ");
            }
            DBColumnMeta dbColumnMeta = dbTableMeta.getPrimaryKeys().get(i);
            sb.append(dbColumnMeta.getName());
            sb.append(" = ?");

            Field field = dbColumnMeta.getField();
            Object v = FastBeanUtils.getFieldValue(field, model);
            if (v == null) {
                throw new RuntimeException("解析update语句失败,id值为null," + model);
            }
            params.add(v);
        }

        return new AnalyzeResult(sb.toString(), params.toArray());
    }

    @Override
    public AnalyzeResult analyzeInsert(DBTableMeta dbTableMeta, Object model) {

        if (dbTableMeta.getColumnMetas().size() == 0) {
            throw new RuntimeException("类型" + dbTableMeta.getName() + "未声明字段");
        }

        List<Object> params = new ArrayList<Object>();

        StringBuilder sb = new StringBuilder("insert into ");
        sb.append(dbTableMeta.getName());

        StringBuilder names = new StringBuilder(" ( ");
        StringBuilder values = new StringBuilder(" ( ");

        for (int i = 0; i < dbTableMeta.getColumnMetas().size(); i++) {
            DBColumnMeta dbColumnMeta = dbTableMeta.getColumnMetas().get(i);

            //对于自动递增的主键,不进行保存,由数据库自动生成
            if (dbColumnMeta.isPrimaryKey() && dbColumnMeta.isAutoIncremen()) {
                continue;
            }
            names.append('`');
            names.append(dbColumnMeta.getName());
            names.append('`');
            names.append(",");

            values.append("?,");

            Field field = dbColumnMeta.getField();
            params.add(FastBeanUtils.getFieldValue(field, model));

        }
        names.deleteCharAt(names.length() - 1);
        names.append(")");

        values.deleteCharAt(values.length() - 1);
        values.append(")");

        sb.append(names);
        sb.append(" values ");
        sb.append(values);

        return new AnalyzeResult(sb.toString(), params.toArray());
    }

    @Override
    public AnalyzeResult analyzeCountByExample(DBTableMeta dbTableMeta, Example example) {
        StringBuilder stringBuilder1 = new StringBuilder("select count(*) from ");
        stringBuilder1.append(dbTableMeta.getName());
        stringBuilder1.append(" as a ");
        if (example.getLeftJoin() != null) {
            DBTableMeta leftJoinDBTableMeta = DBTableMetaFactory.getDBTableMeta(example.getLeftJoin());
            if (leftJoinDBTableMeta != null) {
                stringBuilder1.append("left join ");
                stringBuilder1.append(leftJoinDBTableMeta.getName());
                stringBuilder1.append(" as b on ");
                stringBuilder1.append(example.getOnWhere());
            }
        }

        List<Object> params = new ArrayList<Object>();
        String where = buildWhere(example, params);

        if (StringUtils.isNotBlank(where)) {
            stringBuilder1.append(" where ");
            stringBuilder1.append(where);
        }

        String sql = stringBuilder1.toString();
        return new AnalyzeResult(sql, params.toArray());
    }

    protected String buildWhere(Example example, List<Object> params) {

        boolean isFirst = true;

        StringBuilder stringBuilder2 = new StringBuilder();

        for (int i = 0; i < example.getOredCriteria().size(); i++) {
            Criteria criteria = (Criteria) example.getOredCriteria().get(i);

            if (!criteria.isValid()) {
                continue;
            }

            if (!isFirst) {
                stringBuilder2.append(" or ");
            }

            isFirst = false;

            stringBuilder2.append("(");
            for (int j = 0; j < criteria.getAllCriteria().size(); j++) {
                Criterion criterion = criteria.getAllCriteria().get(j);
                if (j != 0) {
                    stringBuilder2.append(" and ");
                }

                if (criterion.isNoValue()) {

                    stringBuilder2.append(criterion.getCondition());

                } else if (criterion.isSingleValue()) {

                    stringBuilder2.append(criterion.getCondition());
                    stringBuilder2.append(' ');
                    stringBuilder2.append(" ? ");
                    params.add(criterion.getValue());

                } else if (criterion.isBetweenValue()) {

                    stringBuilder2.append(criterion.getCondition());
                    stringBuilder2.append(' ');
                    stringBuilder2.append(" ? ");
                    stringBuilder2.append(" and ");
                    stringBuilder2.append(' ');
                    stringBuilder2.append(" ? ");

                    params.add(criterion.getValue());
                    params.add(criterion.getSecondValue());

                } else if (criterion.isListValue()) {

                    stringBuilder2.append(criterion.getCondition());
                    stringBuilder2.append(' ');
                    stringBuilder2.append("(");

                    List<Object> list = (List<Object>) criterion.getValue();

                    for (Object o : list) {
                        stringBuilder2.append(" ? ");
                        stringBuilder2.append(",");
                        params.add(o);
                    }
                    if (list.size() > 0) {
                        stringBuilder2.deleteCharAt(stringBuilder2.length() - 1);
                    }
                    stringBuilder2.append(")");

                }
            }
            stringBuilder2.append(")");
        }
        return stringBuilder2.toString();
    }

    @Override
    public AnalyzeResult analyzeSumByExample(DBTableMeta dbTableMeta, Example example) {
        StringBuilder stringBuilder1 = new StringBuilder("select sum(" + example.getSumField() + ") from ");
        stringBuilder1.append(dbTableMeta.getName());
        stringBuilder1.append(" as a ");
        if (example.getLeftJoin() != null) {
            DBTableMeta leftJoinDBTableMeta = DBTableMetaFactory.getDBTableMeta(example.getLeftJoin());
            if (leftJoinDBTableMeta != null) {
                stringBuilder1.append("left join ");
                stringBuilder1.append(leftJoinDBTableMeta.getName());
                stringBuilder1.append(" as b on ");
                stringBuilder1.append(example.getOnWhere());
            }
        }

        List<Object> params = new ArrayList<Object>();
        String where = buildWhere(example, params);

        if (StringUtils.isNotBlank(where)) {
            stringBuilder1.append(" where ");
            stringBuilder1.append(where);
        }

        String sql = stringBuilder1.toString();
        return new AnalyzeResult(sql, params.toArray());
    }
}