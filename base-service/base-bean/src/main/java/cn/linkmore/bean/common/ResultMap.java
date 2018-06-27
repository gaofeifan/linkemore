package cn.linkmore.bean.common;

public class ResultMap <T>{
	
	private String msg;  
    
    private int code;  
      
    private T data;  
      
    private int count;  
    
    public ResultMap() {  
        super();  
    }  
      
    public ResultMap(int code,String message) {  
        super();  
        this.code=code;  
        this.msg=message;  
    }  
      
    public ResultMap(int code,String message,T data,int count) {  
        super();  
        this.code=code;  
        this.msg=message;  
        this.data=data;  
        this.count=count;  
    } 
      
      
    public String getMsg() {  
        return msg;  
    }  
  
    public void setMsg(String msg) {  
        this.msg = msg;  
    }  
  
    public int getCount() {  
        return count;  
    }  
  
    public void setCount(int count) {  
        this.count = count;  
    }  
  
    public int getCode() {  
        return code;  
    }  
  
    public void setCode(int code) {  
        this.code = code;  
    }  
  
    public T getData() {  
        return data;  
    }  
  
    public void setData(T data) {  
        this.data = data;  
    }  
}
