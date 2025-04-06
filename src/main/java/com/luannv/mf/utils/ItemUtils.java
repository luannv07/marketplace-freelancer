package com.luannv.mf.utils;

public class ItemUtils {
	public static <T extends Enum<T>> boolean isItemOfEnum(String value, Class<T> enumClass) {
		try {
			Enum.valueOf(enumClass, value.trim().toUpperCase());
		} catch (IllegalArgumentException iae) {
			return false;
		}
		return true;
	}
}
