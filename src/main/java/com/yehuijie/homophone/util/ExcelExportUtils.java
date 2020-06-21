package com.yehuijie.homophone.util;

import com.yehuijie.homophone.model.cell;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by cent on 2017/8/22.
 */
public enum ExcelExportUtils {
    ;
    private static final Logger logger = LoggerFactory.getLogger(ExcelExportUtils.class);

    /**
     * 导出excel（设置response输出流）
     *
     * @param wb
     * @param resp
     * @param filename
     */
    public static void exportExcel(HSSFWorkbook wb, HttpServletResponse resp, String filename) {
        OutputStream out = null;
        try {
            resp.setHeader("content-type", "application/vnd.ms-excel");
            resp.setHeader("Content-Disposition", "attachment; filename="
                    + URLEncoder.encode(filename,"UTF8"));
            out = resp.getOutputStream();
            wb.write(out);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            ApiCommonUtil.closeOutput(out);
        }
    }

    /**
     * 导出excel（设置response输出流）
     *
     * @param wb
     * @param resp
     * @param filename
     */
    public static void exportExcel(HSSFWorkbook wb, HttpServletRequest request, HttpServletResponse resp, String filename) {
        OutputStream out = null;
        final String userAgent = request.getHeader("USER-AGENT");
        try {

            String finalFileName = null;
            if(BlankUtil.isNotEmpty(userAgent)&&userAgent.contains( "MSIE")){//IE浏览器
                finalFileName = URLEncoder.encode(filename,"UTF8");
            }else if(BlankUtil.isNotEmpty(userAgent)&&userAgent.contains("Mozilla")){//google,火狐浏览器
                finalFileName = new String(filename.getBytes(), "ISO8859-1");
            }else{
                finalFileName = URLEncoder.encode(filename,"UTF8");//其他浏览器
            }
            resp.setHeader("content-type", "application/vnd.ms-excel");
            resp.setHeader("Content-Disposition", "attachment; filename="
                    + finalFileName);
            out = resp.getOutputStream();
            wb.write(out);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            ApiCommonUtil.closeOutput(out);
        }
    }


    public static void exportExcel(HttpServletResponse httpResp, String flieName, List<List<cell>> sheets, HSSFWorkbook wb) {
        //创建表格
        HSSFSheet sheet = wb.createSheet(flieName);
        //初始化样式对象
        HSSFCellStyle contentStyle = createContentCellStype(wb);
        HSSFCellStyle titleStyle = createTitleCellStype(wb);

        for (int i=0;i<sheets.size();i++){
            HSSFRow row = sheet.createRow(i);
            for (int j=0;j<sheets.get(i).size();j++){
                HSSFCell cell = row.createCell(j);
                cell.setCellStyle(contentStyle);
                cell.setCellValue(sheets.get(i).get(j).getValue());
            }
        }
        //设置下载输出
        ExcelExportUtils.exportExcel(wb, httpResp, flieName+".xls");
    }


    /**
     * 生成表格内容样式
     *
     * @param wb
     * @return
     */
    public static HSSFCellStyle createContentCellStype(HSSFWorkbook wb) {
        HSSFCellStyle contentStyle = wb.createCellStyle();
        contentStyle.setAlignment(HorizontalAlignment.CENTER);
        contentStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        contentStyle.setWrapText(true);
        contentStyle.setBorderTop(BorderStyle.THIN); //下边框
        contentStyle.setBorderLeft(BorderStyle.THIN);//左边框
        contentStyle.setBorderRight(BorderStyle.THIN);//上边框
        contentStyle.setBorderBottom(BorderStyle.THIN);//右边框

        HSSFDataFormat dataFormat = wb.createDataFormat();
        contentStyle.setDataFormat(dataFormat.getFormat("@"));
        return contentStyle;
    }

    /**
     * 生成表格标题样式
     *
     * @param wb
     * @return
     */
    public static HSSFCellStyle createTitleCellStype(HSSFWorkbook wb) {
        HSSFCellStyle titleStyle = wb.createCellStyle();
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        titleStyle.setWrapText(true);
        titleStyle.setBorderTop(BorderStyle.THIN); //下边框
        titleStyle.setBorderLeft(BorderStyle.THIN);//左边框
        titleStyle.setBorderRight(BorderStyle.THIN);//上边框
        titleStyle.setBorderBottom(BorderStyle.THIN);//右边框

        HSSFFont font = wb.createFont();
        font.setBold(true);
        titleStyle.setFont(font);

        return titleStyle;
    }
}
