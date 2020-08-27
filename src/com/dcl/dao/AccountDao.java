package com.dcl.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.dcl.domain.Account;
import com.dcl.provider.AccountProvider;

public interface AccountDao {

	@Insert("INSERT INTO "
			+ "t_account(account,password,securePassword,role,userName,image,remark,type) "
			+ "VALUES(#{account},#{password},#{securePassword},#{role},#{userName},#{image},#{remark},,#{type})")
	public int insert(Account account);
	
	@Delete("DELETE FROM t_account WHERE account = #{account}")
	public int delete(String account);
	
	@UpdateProvider(type=AccountProvider.class,method="update")
	public int update(Map<String,Object> params);
	
	@SelectProvider(type=AccountProvider.class,method="select")
	public List<Account> select(Map<String,Object> params);
	
}
