package cn.linkmore.common.response;

import java.io.Serializable;

/**
 * 操作原因备注的Bean
 * @Version 2.0
 * @author  GFF
 * @Date     2018年5月10日
 */
public class ResCauseBean implements Serializable {

    private static final long serialVersionUID = 6333509164676661331L;
    private Long id ;
    private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

