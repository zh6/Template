package com.jiuzhou.template.entity;


/**
 * 用户
 *
 * @author lansongtao
 * @Date 2021/03/31
 */
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getIdentityCard() {
		return identityCard;
	}

	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	public String getSysId() {
		return sysId;
	}

	public void setSysId(String sysId) {
		this.sysId = sysId;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getExtend() {
		return extend;
	}

	public void setExtend(String extend) {
		this.extend = extend;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public Long getCreatedUserId() {
		return createdUserId;
	}

	public void setCreatedUserId(Long createdUserId) {
		this.createdUserId = createdUserId;
	}

	public String getCreatedUserName() {
		return createdUserName;
	}

	public void setCreatedUserName(String createdUserName) {
		this.createdUserName = createdUserName;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Long getUpdatedUserId() {
		return updatedUserId;
	}

	public void setUpdatedUserId(Long updatedUserId) {
		this.updatedUserId = updatedUserId;
	}

	public String getUpdatedUserName() {
		return updatedUserName;
	}

	public void setUpdatedUserName(String updatedUserName) {
		this.updatedUserName = updatedUserName;
	}
}