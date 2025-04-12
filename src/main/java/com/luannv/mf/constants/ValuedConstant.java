package com.luannv.mf.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ValuedConstant {
	@Value("${jwt.jwtSecretKey}")
	public String jwtSecretKey;
}
