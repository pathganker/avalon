/**
 * 
 */
package org.saber.avalon.common.pojo;

import java.io.Serializable;

import org.saber.avalon.common.pojo.api.ApiCodeEnum;


/**   
 * @ClassName:  Result   
 * @Description:  APP接口返回对象
 * @author: lijunliang 
 * @date:   2019年1月20日 下午3:26:53   
 *     
 */
public class Result implements Serializable{
	/**
	 * @FieldsserialVersionUID
	 */
	private static final long serialVersionUID = 7883070263735616459L;
	/** 返回码 */
	private String code;
	/** 描述 */
	private String message;
	/** 数据 */
	private Object data;
	
	public String getCode() {
		return code;
	}
	public void setCode(ApiCodeEnum code) {
		this.code = code.getErrorCode();
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
}
