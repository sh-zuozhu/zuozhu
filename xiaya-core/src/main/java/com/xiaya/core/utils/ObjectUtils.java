package com.xiaya.core.utils;

import java.util.Collection;
import java.util.Map;

public abstract class ObjectUtils {
	
	public static boolean eq(Object source, Object target){
		return source == null ? target == null : source.equals(target);
	}
	
	public static boolean ne(Object source, Object target){
		return !eq(source, target);
	}
	
	public static <T extends Comparable<T>> boolean gt(T source, T target){
		if(null == source){
			return false;
		}
		if(null == target){
			return true;
		}
		return source.compareTo(target) > 0;
	}
	
	public static <T extends Comparable<T>> boolean gte(T source, T target){
		if(null == source){
			return false;
		}
		if(null == target){
			return true;
		}
		return source.compareTo(target) >= 0;
	}
	
	public static <T extends Comparable<T>> boolean lt(T source, T target){
		return !gte(source, target);
	}
	
	public static <T extends Comparable<T>> boolean lte(T source, T target){
		return !gt(source, target);
	}
	
	public static <T extends Comparable<T>> boolean Between(T source, T start, T end){
		return gte(start, source) && lte(source, end);
	}
	
	public static <T> boolean isEmpty(T[] values){
		return values == null || values.length < 1;
	}
	
	public static <T> boolean isNotEmpty(T[] values){
		return !isEmpty(values);
	}
	
	public static <T> boolean isEmpty(Collection<T> values){
		return values == null || values.size() < 1;
	}
	
	public static <T> boolean isNotEmpty(Collection<T> values){
		return !isEmpty(values);
	}
	
	public static <T, Q> boolean isEmpty(Map<T, Q> values){
		return null == values || values.size() < 1;
	}
	
	public static <T, Q> boolean isNotEmpty(Map<T, Q> values){
		return !isEmpty(values);
	}
	
	public static <T> boolean in(T value, T[] values){
		if(isEmpty(values)){
			return false;
		}
		for (T v : values) {
			if(eq(value, v)){
				return true;
			}
		}
		return false;
	}
	
	public static <T> boolean notIn(T value, T[] values){
		return !in(value, values);
	}
	
	public static <T> boolean in(T value, Collection<T> values){
		if(isEmpty(values)){
			return false;
		}
		for (T t : values) {
			if(eq(value, t)){
				return true;
			}
		}
		return false;
	}
	
	public static <T> boolean notIn(T value, Collection<T> values){
		return !in(value, values);
	}
	
	public static boolean isArray(Object value){
		if(null == value){
			return true;
		}
		return value.getClass().isArray();
	}
	
	
 
}
