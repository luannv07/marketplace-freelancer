package com.luannv.mf.validators;

import com.luannv.mf.validators.constraints.ConfirmPasswordConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.lang.reflect.Field;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConfirmPasswordConstraintValidator implements ConstraintValidator<ConfirmPasswordConstraint, Object> {
	String firstField;
	String secondField;

	@Override
	public void initialize(ConfirmPasswordConstraint constraintAnnotation) {
		firstField = constraintAnnotation.first();
		secondField = constraintAnnotation.second();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		boolean result = true;
		try {
			Field fF = value.getClass().getDeclaredField(firstField);
			Field sF = value.getClass().getDeclaredField(secondField);
			fF.setAccessible(true);
			sF.setAccessible(true);

			Object first = fF.get(value);
			Object second = sF.get(value);

			if (first == null || !first.equals(second)) {
				context.disableDefaultConstraintViolation();
				context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
								.addPropertyNode(secondField)
								.addConstraintViolation();
				result = false;
			}
		} catch (NoSuchFieldException | IllegalAccessException e) {
			result = false;
		}
		return result;
	}
}
