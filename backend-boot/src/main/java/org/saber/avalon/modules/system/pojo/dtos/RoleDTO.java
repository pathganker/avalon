/**
 * 
 */
package org.saber.avalon.modules.system.pojo.dtos;

import java.util.Date;

/**   
 * @ClassName  RoleDO   
 * @Description 角色DO
 * @author  lijunliang 
 * @date   2019年2月2日 下午1:53:41   
 *     
 */
public class RoleDTO {
	/** 主键 */
	private String id;
	/** 角色 */
	private String role;
	/** 描述 */
	private String name;
	/** 创建时间 */
	private Date createTime;
	/** 修改时间 */
	private Date modifyTime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	
}
