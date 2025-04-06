package com.luannv.mf.utils;

import com.luannv.mf.exceptions.ErrorCode;
import com.luannv.mf.exceptions.SingleErrorException;

import java.awt.*;

public class ItemUtils {
	public static<T extends Enum<T>> boolean isItemOfEnum(String value, Class<T> enumClass) {
		try {
			Enum.valueOf(enumClass, value);
		} catch (IllegalArgumentException iae) {
			return false;
		}
		return true;
	}
}
