package com.li.xiaomi.xiaomilibrary.utils;

import android.content.SharedPreferences;

import com.li.xiaomi.xiaomilibrary.config.AppConfigInIt;
import com.li.xiaomi.xiaomilibrary.config.AppConfigType;


/**
 * 类描述：SharedPreferences  工具类
 * 作  者：李清林
 * 时  间：2016/10/11.
 * 修改备注：
 * 使用说明：1、需要在Application中声明
 * 2、在PublicStatic 中有静态变量
 * <p>
 * <p>
 * SharedPreference提交数据时,尽量使用 Editor.apply(),而非Editor.commit()。
 * 一般来讲,仅当需要确定提交结果,并据此有后续操作时,才使用Editor.commit()。
 * 说明：
 * SharedPreference相关修改使用 apply 方法进行提交会先写入内存，然后异步写入
 * 磁盘,commit 方法是直接写入磁盘.如果频繁操作的话 apply 的性能会优于 commit
 * <p>
 * apply 会将最后修改内容写入磁盘。但是如果希望立刻获取存储操作的结果，并据此
 * 做相应的其他操作，应当使用 commit。
 */

public class PreferenceUtils {

    private static SharedPreferences getSharedPreferences() {
        SharedPreferences configuration = AppConfigInIt.getConfiguration(AppConfigType.SHARED_PREFERENCES);
        return configuration;
    }

    private static SharedPreferences.Editor getSharedPreferencesEditor() {
        SharedPreferences sharedPreferences = getSharedPreferences();
        if (sharedPreferences!= null) {
            return sharedPreferences.edit();
        } else {
            return null;
        }
    }
    /**
     * 取boolean
     *
     * @param key     键
     * @param devalue 默认值
     * @return
     */
    public static boolean getBoolean(String key, boolean devalue) {
        SharedPreferences sharedPreferences = getSharedPreferences();
        if (sharedPreferences != null) {
            return sharedPreferences.getBoolean(key, devalue);
        } else {
            return devalue;
        }
    }

    /**
     * 存boolean
     *
     * @param key   键
     * @param value 值
     */
    public static void setBoolean(String key, boolean value) {
        SharedPreferences.Editor sharedPreferencesEditor = getSharedPreferencesEditor();
        if (sharedPreferencesEditor != null) {
            sharedPreferencesEditor.putBoolean(key, value);
            sharedPreferencesEditor.commit();
        }
    }

    /**
     * 存boolean
     *
     * @param key   键
     * @param value 值
     */
    public static void setBooleanAsync(String key, boolean value) {
        SharedPreferences.Editor sharedPreferencesEditor = getSharedPreferencesEditor();
        if (sharedPreferencesEditor != null) {
            sharedPreferencesEditor.putBoolean(key, value);
            sharedPreferencesEditor.apply();
        }
    }


    /**
     * 存Float
     *
     * @param key   键
     * @param value 值
     */
    public static void setFloat(String key, float value) {
        SharedPreferences.Editor sharedPreferencesEditor = getSharedPreferencesEditor();
        if (sharedPreferencesEditor != null) {
            sharedPreferencesEditor.putFloat(key, value);
            sharedPreferencesEditor.commit();
        }
    }

    /**
     * 取Float
     *
     * @param key     键
     * @param devalue 默认值
     * @return
     */
    public static Float getFloat(String key, float devalue) {
        SharedPreferences sharedPreferences = getSharedPreferences();
        if (sharedPreferences != null) {
            return sharedPreferences.getFloat(key, devalue);
        } else {
            return null;
        }
    }

    /**
     * 存Float
     *
     * @param key   键
     * @param value 值
     */
    public static void setFloatAsync(String key, float value) {
        SharedPreferences.Editor sharedPreferencesEditor = getSharedPreferencesEditor();
        if (sharedPreferencesEditor != null) {
            sharedPreferencesEditor.putFloat(key, value);
            sharedPreferencesEditor.apply();
        }
    }

    /**
     * 存Long
     *
     * @param key   键
     * @param value 值
     */
    public static void setLong(String key, long value) {
        SharedPreferences.Editor sharedPreferencesEditor = getSharedPreferencesEditor();
        if (sharedPreferencesEditor != null) {
            sharedPreferencesEditor.putLong(key, value);
            sharedPreferencesEditor.commit();
        }
    }

    /**
     * 存Long
     *
     * @param key   键
     * @param value 值
     */
    public static void setLongAsync(String key, long value) {
        SharedPreferences.Editor sharedPreferencesEditor = getSharedPreferencesEditor();
        if (sharedPreferencesEditor != null) {
            sharedPreferencesEditor.putLong(key, value);
            sharedPreferencesEditor.apply();
        }
    }

    /**
     * 取Long
     *
     * @param key     键
     * @param devalue 默认值
     * @return
     */
    public static Long getLong(String key, long devalue) {
        SharedPreferences sharedPreferences = getSharedPreferences();
        if (sharedPreferences != null) {
            return sharedPreferences.getLong(key, devalue);
        } else {
            return null;
        }
    }

    /**
     * 存String
     *
     * @param key   键
     * @param value 值
     */
    public static void setString(String key, String value) {
        SharedPreferences.Editor sharedPreferencesEditor = getSharedPreferencesEditor();
        if (sharedPreferencesEditor != null) {
            sharedPreferencesEditor.putString(key, value);
            sharedPreferencesEditor.commit();
        }
    }

    /**
     * 存String
     *
     * @param key   键
     * @param value 值
     */
    public static void setStringAsync(String key, String value) {
        SharedPreferences.Editor sharedPreferencesEditor = getSharedPreferencesEditor();
        if (sharedPreferencesEditor != null) {
            sharedPreferencesEditor.putString(key, value);
            sharedPreferencesEditor.apply();
        }
    }

    /**
     * 取String
     *
     * @param key     键
     * @param devalue 默认值
     * @return
     */
    public static String getString(String key, String devalue) {
        SharedPreferences sharedPreferences = getSharedPreferences();
        if (sharedPreferences!= null) {
            return sharedPreferences.getString(key, devalue);
        } else {
            return null;
        }
    }



    /**
     * 取int
     *
     * @param key     键
     * @param devalue 默认值
     * @return
     */
    public static int getInt(String key, int devalue) {
        SharedPreferences sharedPreferences = getSharedPreferences();
        if (sharedPreferences != null) {
            return sharedPreferences.getInt(key, devalue);
        } else {
            return devalue;
        }
    }

    /**
     * 存int
     *
     * @param key   键
     * @param value 值
     */
    public static void setInt(String key, int value) {
        SharedPreferences.Editor sharedPreferencesEditor = getSharedPreferencesEditor();
        if (sharedPreferencesEditor != null) {
            sharedPreferencesEditor.putInt(key, value);
            sharedPreferencesEditor.commit();
        }
    }

    /**
     * 存int
     *
     * @param key   键
     * @param value 值
     */
    public static void setIntAsync(String key, int value) {
        SharedPreferences.Editor sharedPreferencesEditor = getSharedPreferencesEditor();
        if (sharedPreferencesEditor != null) {
            sharedPreferencesEditor.putInt(key, value);
            sharedPreferencesEditor.apply();
        }
    }

    /**
     * 清空一个
     *
     * @param key
     */
    public static void remove(String key) {
        SharedPreferences.Editor sharedPreferencesEditor = getSharedPreferencesEditor();
        if (sharedPreferencesEditor != null) {
            sharedPreferencesEditor.remove(key);
            sharedPreferencesEditor.commit();
        }
    }

    /**
     * 清空一个
     *
     * @param key
     */
    public static void removeAsync(String key) {
        SharedPreferences.Editor sharedPreferencesEditor = getSharedPreferencesEditor();
        if (sharedPreferencesEditor != null) {
            sharedPreferencesEditor.remove(key);
            sharedPreferencesEditor.apply();
        }
    }

}
