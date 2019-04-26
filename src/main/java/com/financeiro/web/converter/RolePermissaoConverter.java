package com.financeiro.web.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.financeiro.model.security.RolePermissaoId;

@Component
public class RolePermissaoConverter implements Converter<String, RolePermissaoId>{

	@Override
	public RolePermissaoId convert(String source) {
		return null;
	}

}
