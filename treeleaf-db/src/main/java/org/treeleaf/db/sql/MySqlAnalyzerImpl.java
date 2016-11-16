package org.treeleaf.db.sql;

import org.apache.commons.lang3.StringUtils;
import org.treeleaf.db.meta.DBTableMeta;
import org.treeleaf.db.meta.DBTableMetaFactory;
import org.treeleaf.db.model.example.Example;

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
        StringBuilder stringBuilder1 = new StringBuilder("select a.* from ");
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
