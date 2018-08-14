package cn.linkmore.ops.excel.entity;

import java.lang.reflect.ParameterizedType;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

/**
 * Excel 文件解析
 * 
 * @author pengwenlong
 */
public abstract class ExcelReader<T> {

  // 默认单元格内容为数字时格式
  private static DecimalFormat df = new DecimalFormat("0");

  // 默认单元格格式化日期字符串
  private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  // 格式化数字
  private static DecimalFormat nf = new DecimalFormat("0.00");



  /**
   * list 中最大存放数量
   */
  public static final int listSize = 1000;

  private Class<T> clazz;

  private boolean del = true; // 每次解析文件之前，是否删除历史数据

  private boolean autoSave = true; // 当 list 达到最大值时，是否自动保存
  
  private int firstData = 1;//从第几行开始为正式数据，默认为1 
  
  /**
   * 总共导入多少条
   * */
  protected int total;
  
  protected List<T> list;


  
  @SuppressWarnings("unchecked")
  public ExcelReader() {
    // 通过范型反射，取得在子类中定义的class.
    clazz = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
  }



  public List<T> execute(MultipartFile file) {

    if (file == null) {
      return null;
    }

    // 每次解析文件之前，是否删除历史数据
    if (del) {
      try {
        del();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    if (file.getOriginalFilename().endsWith("xlsx")) {
      // 处理ecxel2007
      return readExcel2007(file);
    } else {
      // 处理ecxel2003
      return readExcel2003(file);
    }

  }

  /**
   * 每次解析文件之前，是否删除历史数据
   */
  public abstract void del();


  /**
   * 将 excel 文件每列赋值给对象属性
   * @param code 从 0 开始计数
   */
  public abstract void putValue(int code, Object value, T obj);


  /**
   * 当 list 达到最大值时，是否自动保存
   */
  public abstract void autoSave(List<T> list);
  

  /**
   * 每次获取到一个对象之后，执行的动作，默认保存到 list 之中
   * 子类可以重写此方法
   * @return true 有效的数据 / false 无效的数据
   * */
  protected boolean afterGetObject(T obj) {
    list.add(obj);
    return true;
  }


  private List<T> readExcel2003(MultipartFile file) {
    try {
      HSSFWorkbook wb = new HSSFWorkbook(file.getInputStream());
      HSSFSheet sheet = wb.getSheetAt(0);

      if (this.isAutoSave()) {
        list = new ArrayList<T>(ExcelReader.listSize);
      } else {
        list = new ArrayList<T>(sheet.getPhysicalNumberOfRows());
      }
      // 读行
      for (int i = this.getFirstData(), rowCount = 0; rowCount < sheet.getPhysicalNumberOfRows(); i++) {
        HSSFRow row = sheet.getRow(i);
        if (row == null) {
          // 当读取行为空时
          break;
        } else {
          rowCount++;
        }

        if(afterGetObject(this.getOneBean2003(row))){
          total ++ ;
          if (this.isAutoSave()) {
            if (list.size() >= ExcelReader.listSize || rowCount == sheet.getPhysicalNumberOfRows() - 1) {
              try {
                this.autoSave(list);
              } catch (Exception e) {
                e.printStackTrace();
              }
              if (list.get(0) != null) {
                list.clear();
              }
            }
          }
        }
      }

      return list;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  private List<T> readExcel2007(MultipartFile file) {
    try {

      XSSFWorkbook wb = new XSSFWorkbook(file.getInputStream());
      XSSFSheet sheet = wb.getSheetAt(0);

      if (this.isAutoSave()) {
        list = new ArrayList<T>(ExcelReader.listSize);
      } else {
        list = new ArrayList<T>(sheet.getPhysicalNumberOfRows());
      }

      for (int i = this.getFirstData(), rowCount = 0; rowCount < sheet.getPhysicalNumberOfRows(); i++) {
        XSSFRow row = sheet.getRow(i);
        if (row == null) {
          // 当读取行为空时
          break;
        } else {
          rowCount++;
        }

        if(afterGetObject(this.getOneBean2007(row))){
          total ++ ;
          if (this.isAutoSave()) {
            if (list.size() >= ExcelReader.listSize || rowCount == sheet.getPhysicalNumberOfRows() - 1) {
              try {
                this.autoSave(list);
              } catch (Exception e) {
                e.printStackTrace();
              }
              if (list.get(0) != null) {
                list.clear();
              }            
            }
          }
        }
      }

      return list;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }


  /**
   * 处理ecxel2003
   */
  private T getOneBean2003(HSSFRow row) {

    T o = null;

    try {

      o = clazz.newInstance();
      
      // 读列
      for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {
        HSSFCell cell = null;
        try {
          cell = row.getCell(j);
        } catch (Exception e) {
          
        }
        if (cell == null || cell.getCellType() == HSSFCell.CELL_TYPE_BLANK) {
          // 当该单元格为空
          if (j != row.getLastCellNum()) {// 判断是否是该行中最后一个单元格
            ;
          }
          continue;
        }
        Object value = null;
        switch (cell.getCellType()) {
          case XSSFCell.CELL_TYPE_STRING:
            value = cell.getStringCellValue();
            break;
          case XSSFCell.CELL_TYPE_NUMERIC:
            if ("@".equals(cell.getCellStyle().getDataFormatString())) {
              value = df.format(cell.getNumericCellValue());
            } else if ("General".equals(cell.getCellStyle().getDataFormatString())) {
              value = nf.format(cell.getNumericCellValue());
            } else {
              value = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
            }
            break;
          case XSSFCell.CELL_TYPE_BOOLEAN:
            value = Boolean.valueOf(cell.getBooleanCellValue());
            break;
          case XSSFCell.CELL_TYPE_BLANK:
            value = "";
            break;
          default:
            value = cell.toString();
        }

        if (value != null) {
    
            putValue(j, value, o);

        }
      }
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }

    return o;
  }


  /**
   * ecxel2007
   */
  private T getOneBean2007(XSSFRow row) {

    T o = null;

    try {
      o = clazz.newInstance();
      // 读列
      for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {
        XSSFCell cell = null;
        try {
          cell = row.getCell(j);
        } catch (Exception e) {
        }
        if (cell == null || cell.getCellType() == HSSFCell.CELL_TYPE_BLANK) {
          // 当该单元格为空
          if (j != row.getLastCellNum()) {// 判断是否是该行中最后一个单元格
            ;
          }
          continue;
        }
        Object value = null;
        switch (cell.getCellType()) {
          case XSSFCell.CELL_TYPE_STRING:
            value = cell.getStringCellValue();
            break;
          case XSSFCell.CELL_TYPE_NUMERIC:
            if ("@".equals(cell.getCellStyle().getDataFormatString())) {
              value = df.format(cell.getNumericCellValue());
            } else if ("General".equals(cell.getCellStyle().getDataFormatString())) {
              value = nf.format(cell.getNumericCellValue());
            } else if ("0_ ".equals(cell.getCellStyle().getDataFormatString())) {
              value = nf.format(cell.getNumericCellValue());
            } else {
              value = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
            }
            break;
          case XSSFCell.CELL_TYPE_BOOLEAN:
            value = Boolean.valueOf(cell.getBooleanCellValue());
            break;
          case XSSFCell.CELL_TYPE_BLANK:
            value = "";
            break;
          default:
            value = cell.toString();
        }

        if (value != null) {
          putValue(j, value, o);
        }
      }

    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }

    return o;
  }



  public int getFirstData() {
    return firstData;
  }

  public void setFirstData(int firstData) {
    this.firstData = firstData;
  }

  public boolean isDel() {
    return del;
  }

  public void setDel(boolean del) {
    this.del = del;
  }

  public boolean isAutoSave() {
    return autoSave;
  }

  public void setAutoSave(boolean autoSave) {
    this.autoSave = autoSave;
  }

  public int getTotal() {
    return total;
  }

  public void setTotal(int total) {
    this.total = total;
  }


}
