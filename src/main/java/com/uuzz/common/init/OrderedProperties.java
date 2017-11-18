package com.uuzz.common.init;

import java.util.*;

/**
 * 可以顺序加载的属性类
 * @author zj
 * createTime: 2017/11/6
 */
public class OrderedProperties extends Properties {

    private static final long serialVersionUID = 1L;

    private final LinkedHashSet<Object> keys = new LinkedHashSet<Object>();

    public Enumeration<Object> keys() {
        return Collections.<Object> enumeration(keys);
    }

    public Object put(Object key, Object value) {
        keys.add(key);
        return super.put(key, value);
    }

    public Set<Object> keySet() {
        return keys;
    }

    public Set<String> stringPropertyNames() {
        Set<String> set = new LinkedHashSet<String>();

        for (Object key : this.keys) {
            set.add((String) key);
        }
        return set;
    }
}
