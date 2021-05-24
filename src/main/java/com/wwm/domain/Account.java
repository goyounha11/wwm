package com.wwm.domain;

import java.util.List;
import java.util.Map;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class Account {
	private String id;
	private String userNm;
	private String password;
	private String role;
	private String email;
	
	@Getter(AccessLevel.NONE)
	private boolean admin;
	
	private int authGroupId;
	private String accStatus;
	private int loginFailCnt;
	private String lastLoginDt;
	private String lastLogoutDt;
	private String pwChgDt;
	private List<Map<String, Object>> menuList;
	private Map<String, Object> menuMap;
	private Map<String, Object> accountAuthMap;
	private List<Map<String, Object>> locationList;
	private String loginIp;
	private String organizationId;
	private String companyNm;
	private String agencyNm;
	private String deptNm;
	private String positionNm;
	private String applyAuthGroupId;
	private String loginType;
	private String pathLoc;
	private String telNum;
	
	@Override
	public String toString() {
		return "Account{" + "id=" + id + ", username='" + userNm + '\'' + ", password='" + password + '\'' + '}';
	}
	
	public boolean isAdmin() {
		return "0".equals(this.role);
	}
}
