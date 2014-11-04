package com.nd.jisou.utils;

import java.lang.reflect.Field;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * 实体类工具
 * 
 * @author wujy
 */
public class EntityUtil {
	/**
	 * 实体类转化为ContentValues
	 * 
	 * @param bean
	 * @return
	 */
	public static <T> ContentValues entity2ContentValues(T bean) {
		ContentValues contentValues = new ContentValues();
		Field[] arrField = bean.getClass().getDeclaredFields();

		for (Field f : arrField) {
			if (f.isAccessible() == false) {
				f.setAccessible(true);
			}
			String name = f.getName();
			try {
				Object value = f.get(bean);
				if (value == null) {

				} else if (value instanceof Byte) {
					contentValues.put(name, (Byte) value);
				} else if (value instanceof Short) {
					contentValues.put(name, (Short) value);
				} else if (value instanceof Integer) {
					contentValues.put(name, (Integer) value);
				} else if (value instanceof Long) {
					contentValues.put(name, (Long) value);
				} else if (value instanceof String) {
					contentValues.put(name, (String) value);
				} else if (value instanceof byte[]) {
					contentValues.put(name, (byte[]) value);
				} else if (value instanceof Boolean) {
					contentValues.put(name, (Boolean) value);
				} else if (value instanceof Float) {
					contentValues.put(name, (Float) value);
				} else if (value instanceof Double) {
					contentValues.put(name, (Double) value);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return contentValues;
	}

	/**
	 * 将Cursor对象转化为具体实体
	 * 
	 * @param cursor
	 * @param cls
	 * @return
	 */
	public static <T> T cursor2Entity(Cursor cursor, Class<T> cls) {
		T bean = null;
		try {
			bean = cls.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
			return bean;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return bean;
		}
		if (cursor.isBeforeFirst()) {
			cursor.moveToFirst();
		}
		Field[] arrField = cls.getDeclaredFields();
		for (Field f : arrField) {
			String columnName = f.getName();
			int columnIdx = cursor.getColumnIndex(columnName);
			if (columnIdx != -1) {
				if (f.isAccessible()) {
					f.setAccessible(true);
				}
				Class<?> type = f.getType();
				try {
					if (type == byte.class) {
						f.set(bean, (byte) cursor.getShort(columnIdx));
					} else if (type == short.class) {
						f.set(bean, cursor.getShort(columnIdx));
					} else if (type == int.class) {
						f.set(bean, cursor.getInt(columnIdx));
					} else if (type == long.class) {
						f.set(bean, cursor.getLong(columnIdx));
					} else if (type == String.class) {
						f.set(bean, cursor.getString(columnIdx));
					} else if (type == byte[].class) {
						f.set(bean, cursor.getBlob(columnIdx));
					} else if (type == boolean.class) {
						f.set(bean, cursor.getInt(columnIdx) == 1);
					} else if (type == float.class) {
						f.set(bean, cursor.getFloat(columnIdx));
					} else if (type == double.class) {
						f.set(bean, cursor.getDouble(columnIdx));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return bean;
	}
}
