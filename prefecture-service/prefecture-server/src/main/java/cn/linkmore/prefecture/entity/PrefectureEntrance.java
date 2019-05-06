package cn.linkmore.prefecture.entity;

public class PrefectureEntrance {
    private Long id;

    private Long preId;

    private String entranceType;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPreId() {
        return preId;
    }

    public void setPreId(Long preId) {
        this.preId = preId;
    }

    public String getEntranceType() {
        return entranceType;
    }

    public void setEntranceType(String entranceType) {
        this.entranceType = entranceType == null ? null : entranceType.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}