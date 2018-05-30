package cn.linkmore.util;

import java.lang.reflect.Field;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description  bean操作工具类
 * @author  GFF
 * @Date     2018年3月17日
 *
 */
public class BeanUtils {

	public static Field findField(Class<?> clazz, String name) {
		try {
			return clazz.getField(name);
		} catch (NoSuchFieldException ex) {
			return findDeclaredField(clazz, name);
		}
	}

	public static Field findDeclaredField(Class<?> clazz, String name) {
		try {
			return clazz.getDeclaredField(name);
		} catch (NoSuchFieldException ex) {
			if (clazz.getSuperclass() != null) {
				return findDeclaredField(clazz.getSuperclass(), name);
			}
			return null;
		}
	}

	public static Method findMethod(Class<?> clazz, String methodName, Class<?>... paramTypes) {
		try {
			return clazz.getMethod(methodName, paramTypes);
		} catch (NoSuchMethodException ex) {
			return findDeclaredMethod(clazz, methodName, paramTypes);
		}
	}

	public static Method findDeclaredMethod(Class<?> clazz, String methodName, Class<?>... paramTypes) {
		try {
			return clazz.getDeclaredMethod(methodName, paramTypes);
		} catch (NoSuchMethodException ex) {
			if (clazz.getSuperclass() != null) {
				return findDeclaredMethod(clazz.getSuperclass(), methodName, paramTypes);
			}
			return null;
		}
	}

	public static Object getProperty(Object obj, String name) throws NoSuchFieldException {
		Object value = null;
		Field field = findDeclaredField(obj.getClass(), name);
		if (field == null) {
			throw new NoSuchFieldException("no such field [" + name + "]");
		}
		boolean accessible = field.isAccessible();
		field.setAccessible(true);
		try {
			value = field.get(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		field.setAccessible(accessible);
		return value;
	}

	public static void setProperty(Object obj, String name, Object value) throws NoSuchFieldException {
		Field field = findField(obj.getClass(), name);
		if (field == null) {
			throw new NoSuchFieldException("no such field [" + name + "]");
		}
		boolean accessible = field.isAccessible();
		field.setAccessible(true);
		try {
			field.set(obj, value);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		field.setAccessible(accessible);
	}

	public static Map<String, Object> obj2Map(Object obj, Map<String, Object> map) {
		if (map == null) {
			map = new HashMap<String, Object>();
		}
		if (obj != null) {
			try {
				Class<?> clazz = obj.getClass();
				do {
					Field[] fields = clazz.getDeclaredFields();
					for (Field field : fields) {
						int mod = field.getModifiers();
						if (Modifier.isStatic(mod)) {
							continue;
						}
						boolean accessible = field.isAccessible();
						field.setAccessible(true);
						map.put(field.getName(), field.get(obj));
						field.setAccessible(accessible);
					}
					clazz = clazz.getSuperclass();
				} while (clazz != null);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return map;
	}

	/**
	 * @Description  获得父类集合，包含当前class
	 * @Author   GFF 
	 * @Date       2018年3月17日
	 * @Param    BeanUtils
	 * @Return   List<Class<?>>
	 */
	public static List<Class<?>> getSuperclassList(Class<?> clazz) {
		List<Class<?>> clazzes = new ArrayList<Class<?>>(3);
		clazzes.add(clazz);
		clazz = clazz.getSuperclass();
		while (clazz != null) {
			clazzes.add(clazz);
			clazz = clazz.getSuperclass();
		}
		return Collections.unmodifiableList(clazzes);
	}
	
	/**
	 * @Description  通过注释获取字段的value
	 * @Author   GFF 
	 * @Date       2018年3月17日
	 * @Param    BeanUtils
	 * @Return   Object
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object getAnnottionFieldValue(Object obj,Class Annottion){
		Class<? extends Object> clazz = obj.getClass();
		Field[] fields = clazz.getDeclaredFields();
		try {
			for (Field field : fields) {
				boolean b = field.isAnnotationPresent(Annottion);
				if(b){
					field.setAccessible(true);
					Object object = field.get(obj);
					return object;
				}
			}
		} catch (IllegalAccessException | IllegalArgumentException e) {
			e.printStackTrace();
		}
		return fields;
	}
}
