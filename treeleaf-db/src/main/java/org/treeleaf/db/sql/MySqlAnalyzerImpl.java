package org.treeleaf.db.sql;

import org.treeleaf.db.meta.DBTableMeta;
import org.treeleaf.db.model.example.Criteria;
import org.treeleaf.db.model.example.Criterion;
import org.treeleaf.db.model.example.Example;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * MySql解析器
 *
 * @Author leaf
 * 2015/1/10 0010 19:42.
 */
public class MySqlAnalyzerImpl extends DefaultSqlAnalyzerImpl {

    @Override
    public AnalyzeResult analyzeSelectByExample(DBTableMeta dbTableMeta, Example example) {
        StringBuilder stringBuilder1 = new StringBuilder("select * from ");
        stringBuilder1.append(dbTableMeta.getName());

        StringBuilder stringBuilder2 = new StringBuilder();

        List<Object> params = new ArrayList<Object>();

        boolean isFirst = true;

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

        if (!isFirst) {
            stringBuilder1.append(" where ");
            stringBuilder1.append(stringBuilder2);
        }

        if (StringUtils.isNotBlank(example.getOrderByClause())) {
            stringBuilder1.append(" order by ");
            stringBuilder1.append(example.getOrderByClause());
        }
        //当limit小于0时将不进行分页查询
        if (example.getLimit() >= 0) {
            stringBuilder1.append(" limit ?,?");
            params.add(example.getStart());
            params.add(example.getLimit());
        }

        String sql = stringBuilder1.toString();
        return new AnalyzeResult(sql, params.toArray());
    }

}
