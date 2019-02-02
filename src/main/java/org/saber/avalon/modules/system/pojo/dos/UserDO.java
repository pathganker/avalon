/**
 * 
 */
package org.saber.avalon.modules.system.pojo.dos;

import java.io.Serializable;
import java.util.Date;


/**   
 * @ClassName:  UserDO   
 * @Description:TODO
 * @author: lijunliang 
 * @date:   2019年2月2日 上午12:26:45   
 *     
 */
public class UserDO implements Serializable{

	/**
	 * @FieldsserialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/** 员工ID */
	private String id;
	/** 登录名*/
	private String name;
	/** 用户名*/
	private String nickName;
	/** 密码 */
	private String password;
	/** 盐值 */
	private String salt;
	/** 联系电话 */
	private String telephone;
	/** 部门 */
	private String depart;
	/** 主页 */
	private String defaultUrl;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getDepart() {
		return depart;
	}
	public void setDepart(String depart) {
		this.depart = depart;
	}
	public String getDefaultUrl() {
		return defaultUrl;
	}
	public void setDefaultUrl(String defaultUrl) {
		this.defaultUrl = defaultUrl;
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
	/**   
	 * @Title getCredentialsSalt   
	 * @Description TODO
	 * @author lijunliang 
	 * @date   2019年2月2日 下午4:08:58   
	 * @param @return      
	 * @return byte []      
	 * @throws   
	 */
	public String getCredentialsSalt() {
		return this.id+this.salt;
	}
	
}
