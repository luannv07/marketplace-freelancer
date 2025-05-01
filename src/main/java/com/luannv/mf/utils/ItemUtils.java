package com.luannv.mf.utils;

import com.luannv.mf.dto.response.SkillResponse;
import com.luannv.mf.exceptions.ErrorCode;
import com.luannv.mf.exceptions.SingleErrorException;
import com.luannv.mf.models.Skill;

import java.util.Set;
import java.util.stream.Collectors;

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
