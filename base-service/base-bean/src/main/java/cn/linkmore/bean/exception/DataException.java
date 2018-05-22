package cn.linkmore.bean.exception;
/**
 * 通用 - 异常处理
 * @author liwenlong
 * @version 1.0
 *
 */
public class DataException extends RuntimeException {
    /**
     * 序列号
     */
    private static final long serialVersionUID = 8515393790439007314L;
    /**
     * 异常原因
     */
    private String message;
    
    /**
     * message get方法
     * @return message
     */
    public String getMessage() {
        return message;
    }
    
    /**
     * 构造方法
     * @param message
     *          原由
     */
    public DataException(String message){
        this.message = message;
    } 
}
