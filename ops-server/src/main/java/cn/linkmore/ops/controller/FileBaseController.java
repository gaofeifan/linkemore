package cn.linkmore.ops.controller;

import java.io.IOException;

import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;


public class FileBaseController {

  /**
   * 导出下载文件
   * 
   * @param fileName 文件名
   * @param workBook 文件内容
   * @param errMsg 如果发生异常，异常提示说明
   * @param response
   */
  protected void writeFile(String fileName, HSSFWorkbook workBook, String errMsg, HttpServletResponse response) {
    OutputStream outputStream = null;
    try {
      response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "utf-8"));
      response.setContentType("application/vnd.ms-excel");
      response.setCharacterEncoding("UTF-8");
      outputStream = response.getOutputStream();
      workBook.write(outputStream);
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (outputStream != null) {
        try {
          outputStream.flush();
        } catch (IOException e) {
          e.printStackTrace();
        }
        try {
          outputStream.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }
}
