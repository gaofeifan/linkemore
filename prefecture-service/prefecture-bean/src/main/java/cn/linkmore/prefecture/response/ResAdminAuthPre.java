package cn.linkmore.prefecture.response;

public class ResAdminAuthPre {

	 private Long id;

	    private Long authId;

	    private Long preId;
	    
	    private Long cityId;

	    public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }

	    public Long getAuthId() {
	        return authId;
	    }

	    public void setAuthId(Long authId) {
	        this.authId = authId;
	    }

	    public Long getPreId() {
	        return preId;
	    }

	    public void setPreId(Long preId) {
	        this.preId = preId;
	    }

		public Long getCityId() {
			return cityId;
		}

		public void setCityId(Long cityId) {
			this.cityId = cityId;
		}
}
