package com.li.xiaomi.xiaomilibrary.net;

/**
 * 作者：dell or Xiaomi Li
 * 时间： 2018/3/31
 * 内容：
 * 最后修改：
 */

public final class HeadBean {
    private final String HeadKey;
    private final String HeadValue;

    public HeadBean(String headKey, String headValue) {
        HeadKey = headKey;
        HeadValue = headValue;
    }

    public String getHeadKey() {
        return HeadKey;
    }

    public String getHeadValue() {
        return HeadValue;
    }
}
