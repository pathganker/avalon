/**
 * 
 */
package org.saber.avalon.modules.system.pojo.dtos;

/**   
 * @ClassName:  PermissionDO   
 * @Description: 权限DO
 * @author: lijunliang 
 * @date:   2019年2月2日 下午2:04:10   
 *     
 */
public class PermissionDTO {
	/** 主键 */
	private String id;
	/** 名称 */
	private String name;
	/** 路径 */
	private String url;
	/** */
	private String permission;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getPermission() {
		return permission;
	}
	public void setPermission(String permission) {
		this.permission = permission;
	}
	
}
