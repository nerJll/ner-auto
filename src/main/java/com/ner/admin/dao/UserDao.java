package com.ner.admin.dao;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ner.admin.entity.Role;
import com.ner.admin.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.Map;
import java.util.Set;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author jll
 * @since 2017-10-31
 */
public interface UserDao extends BaseMapper<User> {
	User selectUserByMap(Map<String, Object> map);

	void saveUserRoles(@Param("userId")Long id, @Param("roleIds")Set<Role> roles);

	void dropUserRolesByUserId(@Param("userId")Long userId);

	Map selectUserMenuCount();
}