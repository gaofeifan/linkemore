package cn.linkmore.prefecture.entity;

import java.util.Date;

public class BluetoothData {
    private Long id;

    private Long userId;

    private String lockSn;

    private String search;

    private String matching;

    private String signaling;

    private String connect;

    private String send;

    private String success;

    private Integer count = 0;

    private Date createTime;

    private String disconnects;

    private String disconnect;
    
    private Long preId;
    
    private String preName;
    /**
     * 操作类型  1 降锁 2升锁
     */
    private Short operateFlag = 1;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getLockSn() {
        return lockSn;
    }

    public void setLockSn(String lockSn) {
        this.lockSn = lockSn == null ? null : lockSn.trim();
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search == null ? null : search.trim();
    }

    public String getMatching() {
		return matching;
	}

	public void setMatching(String matching) {
		this.matching = matching;
	}

	public String getSignaling() {
		return signaling;
	}

	public void setSignaling(String signaling) {
		this.signaling = signaling;
	}

	public String getConnect() {
        return connect;
    }

    public void setConnect(String connect) {
        this.connect = connect == null ? null : connect.trim();
    }

    public String getSend() {
        return send;
    }

    public void setSend(String send) {
        this.send = send == null ? null : send.trim();
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success == null ? null : success.trim();
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDisconnects() {
        return disconnects;
    }

    public void setDisconnects(String disconnects) {
        this.disconnects = disconnects == null ? null : disconnects.trim();
    }

    public String getDisconnect() {
        return disconnect;
    }

    public void setDisconnect(String disconnect) {
        this.disconnect = disconnect == null ? null : disconnect.trim();
    }

	public Long getPreId() {
		return preId;
	}

	public void setPreId(Long preId) {
		this.preId = preId;
	}

	public String getPreName() {
		return preName;
	}

	public void setPreName(String preName) {
		this.preName = preName;
	}

	public Short getOperateFlag() {
		return operateFlag;
	}

	public void setOperateFlag(Short operateFlag) {
		this.operateFlag = operateFlag;
	}
    
}