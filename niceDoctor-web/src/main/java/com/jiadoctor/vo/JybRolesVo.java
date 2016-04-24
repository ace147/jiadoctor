package com.jiadoctor.vo;

import java.util.List;

import com.jiadoctor.entity.patient.JybAuthorities;
import com.jiadoctor.entity.patient.JybRoles;


public class JybRolesVo {

	private JybRoles jybRoles;
	
	private List<JybAuthorities> jybAuthorities;

	public JybRoles getJybRoles() {
		return jybRoles;
	}

	public void setJybRoles(JybRoles jybRoles) {
		this.jybRoles = jybRoles;
	}

	public List<JybAuthorities> getJybAuthorities() {
		return jybAuthorities;
	}

	public void setJybAuthorities(List<JybAuthorities> jybAuthorities) {
		this.jybAuthorities = jybAuthorities;
	}

	
}
