package cn.linkmore.util;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.linkmore.annotation.GColumn;

public class ObjectUtils {
	private static final Logger log = LoggerFactory.getLogger(ObjectUtils.class);
	/**
	 * @Description  
	 * @Author   GFF 
	 * @Date       2018年4月17日
	 * @Param    list 查询集合中某个字段的值
	 * @Param    fieldName   获取该字段的值
	 * @Param    properts 条件字段
	 * @Param    values 条件值
	 * @Return   List<String>
	 */
	public static final List<String> findFieldVlaue(List<?> list , String fieldName , String[] properts,Object[] values){
		if((properts != null && values != null) && (properts.length != values.length)){
			log.error("数据长度不一样");
			throw new RuntimeException("查询字段与值长度不一");
		}
		if(list.size() != 0){
			List<String> ids = new ArrayList<>(); 
			Class<? extends Object> clazz;
			try {
				for (Object t : list) {
					clazz = t.getClass();
					Field field = clazz.getDeclaredField(fieldName);
					Field[] fields = clazz.getDeclaredFields();
					boolean flag = true;
					if(properts != null){
						for (int i= 0 ; i < properts.length;i++) {
							for (Field f : fields) {
								f.setAccessible(true);
								if(f.getName().equals(properts[i])){
									if(!f.get(t).toString().equals(values[i].toString())){
										flag = false;
									}
								}
							}
						}
					}
					if(flag){
						if(field != null){
							field.setAccessible(true);
							Object object = field.get(t);
							if(object != null){
								ids.add(object.toString());
							}
						}
					}
				}
			} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			}
			return ids;
		}
		return null;
		
	}
	/**
	 * @Description  
	 * @Author   GFF 
	 * @Date       2018年4月17日
	 * @Param    list 查询集合中某个字段的值
	 * @Param    fieldName   获取该字段的值
	 * @Param    properts 条件字段
	 * @Param    values 条件值
	 * @Return   List<String>
	 */
	/*public static final List<String> findFieldVlaue(List<?> list , String fieldName ,String properts,Object values){
		if(list.size() != 0){
			List<String> ids = new ArrayList<>(); 
			Class<? extends Object> clazz;
			try {
				for (Object t : list) {
					clazz = t.getClass();
					Field field = clazz.getDeclaredField(fieldName);
					Field[] fields = clazz.getDeclaredFields();
					boolean flag = true;
					if(properts != null){
						for (Field f : fields) {
							f.setAccessible(true);
							if(f.getName().equals(properts)){
								if(!f.get(t).toString().equals(values.toString())){
									flag = false;
								}
							}
						}
					}
					if(flag){
						if(field != null){
							field.setAccessible(true);
							Object object = field.get(t);
							if(object != null){
								ids.add(object.toString());
							}
						}
					}
				}
			} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			}
			return ids;
		}
		return null;
		
	}*/
	
	public static <T> List<T> getListBean(List<Map<String,Object>> list,Class<T> clazz){
		List<T> resultLit = new ArrayList<>();
		try {
			for (Map<String, Object> map : list) {
				T t = clazz.newInstance();
				Field[] fields = clazz.getDeclaredFields();
				Set<Entry<String,Object>> set = map.entrySet();
				for (Entry<String, Object> entry : set) {
					for (Field field : fields) {
						field.setAccessible(true);
						if(field.isAnnotationPresent(GColumn.class)){
							GColumn column = field.getAnnotation(GColumn.class);
							if(StringUtils.isNotBlank(column.vlaue())){
								if(entry.getKey().equals(column.vlaue())){
									fieldSetValue(t,field,entry.getValue());
									continue;
								}
							}
						}
						if(DomainUtil.underlineToCamel(entry.getKey()).equals(field.getName())){
							fieldSetValue(t,field,entry.getValue());
						}
					}
				}
				resultLit.add(t);
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e){
			
		}
		return resultLit;
		
	}

	/**
	 * @Description  设置字段属性
	 * @Author   GFF 
	 * @Date       2018年5月11日
	 * @Param    ObjectUtils
	 * @Return   void
	 */
	private static <T> void fieldSetValue(T t, Field field,Object value) {
		try {
			String name = field.getType().getSimpleName();
			if(name.equals("Short")){
				field.set(t, Short.decode(value.toString()));
			}else if(name.equals("Date")){
				field.set(t, (Date)value);
			}else if(name.equals("Integer")){
				field.set(t, (Integer)value);
			}else if(name.equals("Long")){
				field.set(t, (Long)value);
			}else if(name.equals("String")){
				field.set(t, (String)value);
			}else if(name.equals("Byte")){
				field.set(t, (Byte)value);
			}else if(name.equals("Double")){
				field.set(t, (Double)value);
			}else if(name.equals("Boolean")){
				field.set(t, (Boolean)value);
			}else if(name.equals("Float")){
				field.set(t, (Float)value);
			}else if(name.equals("BigDecimal")){
				field.set(t, (BigDecimal)value);
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	public static Object  columnSetValue(Field field,Object value) {
		try {
			String name = field.getType().getSimpleName();
			if(name.equals("Date")){
				GColumn c = field.getAnnotation(GColumn.class);
				String format = c.dateFormat();
				return DateUtils.converter((Date)value, format);
			}else{
				return value;
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @Author   GFF 
	 * @Date       2018年5月11日
	 */
	public static <T,E> T copyObject(E source,T target) {
		return copyObject(source, target, null, null);
	}

	/**
	 * @Author   GFF 
	 * @Date       2018年5月11日
	 */
	public static <T,E> T copyObject(E source,T target,String[] fields,String[] replaceField) {
		return copyObject(source, target, fields, replaceField, null);
	}
	
	/**
	 * @Description  copy对象
	 * @Author   GFF 
	 * @Date       2018年5月11日
	 * @Param    ObjectUtils 
	 * @Return   source  原数据  target新数据  fields 原数据中的字段  replaceField 替换的字段
	 */
	public static <T,E> T copyObject(E source,T target,String[] fields,String[] replaceField,String[] excludeField) {
		if(source == null ) {
			return null;
		}
		if(fields != null && replaceField != null) {
			if(fields.length != replaceField.length) {
				throw new RuntimeException("元素长度不相等");
			}
		}
		Class<E> sourceC = (Class<E>) source.getClass();
		Class<T> sourceT = (Class<T>) target.getClass();
		Field[] fieldsC = sourceC.getDeclaredFields();
		Field[] fieldsT = sourceT.getDeclaredFields();
		try {
			for (Field sc : fieldsC) {
				if(excludeField != null && ArrayUtils.contains(excludeField, sc.getName())) {
					continue;
				}
				for (Field st : fieldsT) {
					if(sc.getName().equals(st.getName())){
						sc.setAccessible(true);
						Object object = sc.get(source);
						if(object != null) {
							st.setAccessible(true);
							fieldSetValue(target, st, object);
						}
						continue;
					}
				}
			}
			if(fields != null && fields.length != 0) {
				for (int i = 0; i < fields.length; i++) {
					Field fc = sourceC.getDeclaredField(fields[i]);
					Field ft = sourceT.getDeclaredField(replaceField[i]);
					fc.setAccessible(true);
					Object object = fc.get(source);
					if(object != null) {
						ft.setAccessible(true);
						fieldSetValue(target, ft, object);
					}
				}
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return target;
	}
	
	private static int findArrayIndex(Object[] obj , Object data) {
		for (int i = 0; i < obj.length; i++) {
			if(obj[i].equals(data)) {
				return i;
			}
		}
		return -1;
	}
	
	public static Map<String,Object> toMap(Class<?> clazz){
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
		}
		return null;
	}
	
	/**
	 * @Description  map转bean
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	public static <T> T toBean(Class<T> clazz , Map<String, Object> map){
		Field[] fields = clazz.getDeclaredFields();
		T t = null;
		try {
			t = clazz.newInstance();
			for (Field field : fields) {
				field.setAccessible(true);
				if(map.containsKey(field.getName())) {
					if(map.get(field.getName()) != null) {
						field.set(t, map.get(field.getName()));
					}else {
						field.set(t, null);
					}
					
				}
			}
		} catch (IllegalArgumentException | IllegalAccessException | InstantiationException e) {
		}
		return t;
	}
}
