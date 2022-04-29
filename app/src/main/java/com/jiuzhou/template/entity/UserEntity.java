package com.jiuzhou.template.entity;

import lombok.Data;

/**
 * 用户
 *
 * @author lansongtao
 * @Date 2021/03/31
 */
@Data
public class UserEntity  {
	
	/** serialVersionUID */
    private static final long serialVersionUID = 1L;
	
	/** 用户ID */
    private Long id;
	
	/** 登录名 */
    private String loginName;
	
	/** 昵称 */
    private String nickName;
	
	/** 用户手机 */
    private String mobile;
	
	/** 身份证号 */
    private String identityCard;
	
	/** 是否启用 */
    private Boolean enable;
	
	/** 系统id */
    private String sysId;
	
	/** 用户类型，是客户还是商家 */
    private String typeId;
	
	/** 业务表的ID，比如客户ID或商家ID */
    private String typeCode;
	
	/** 最后一次登录时间 */
    private String lastLoginTime;
	
	/** 用户头像 */
    private String filePath;
	
	/** 扩展字段 */
    private String extend;

	/** 创建时间 */
	private String createdDate;

	/** 创建人ID */
	private Long createdUserId;

	/** 创建人名称 */
	private String createdUserName;

	/** 更新时间 */
	private String updatedDate;

	/** 更新人ID */
	private Long updatedUserId;

	/** 更新人名称 */
	private String updatedUserName;


	
}