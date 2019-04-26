package com.financeiro.web.converter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.financeiro.model.security.Role;
import com.financeiro.service.RoleService;

@Component
public class RoleConverter implements Converter<String, Role>{
	
	@Autowired
	private RoleService roleService;

	@Override
	public Role convert(String string) {
        if (string.isEmpty()) {
        	return null;
        }
        Integer id = Integer.valueOf(string);
		return roleService.findById(id);
	}

}
