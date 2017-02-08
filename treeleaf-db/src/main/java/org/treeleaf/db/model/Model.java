package org.treeleaf.db.model;


import org.treeleaf.common.json.Jsoner;

import java.io.Serializable;

/**
 * @Author leaf
 * 2015/1/8 0008 1:05.
 */
public abstract class Model implements Serializable {

    @Override
    public String toString() {
        return Jsoner.toJson(this);
    }
}
