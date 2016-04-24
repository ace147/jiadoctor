package com.jiadoctor.vo;

import java.util.List;

import com.jiadoctor.entity.doctor.JydAuthorities;
import com.jiadoctor.entity.doctor.JydRoles;



public class JydRolesVo {

	private JydRoles jydRoles;
	
	private List<JydAuthorities> jydAuthorities;

	public JydRoles getJydRoles() {
		return jydRoles;
	}

	public void setJydRoles(JydRoles jydRoles) {
		this.jydRoles = jydRoles;
	}

	public List<JydAuthorities> getJydAuthorities() {
		return jydAuthorities;
	}

	public void setJydAuthorities(List<JydAuthorities> jydAuthorities) {
		this.jydAuthorities = jydAuthorities;
	}

	
}
