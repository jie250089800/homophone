package com.yehuijie.homophone.service.Impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.yehuijie.homophone.entity.*;
import com.yehuijie.homophone.mapper.customer.CustomerHomophoneMapper;
import com.yehuijie.homophone.service.*;
import com.yehuijie.homophone.util.BlankUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Author: YeHuiJie
 * @Date: 2020/6/10 21:50
 */
@Service
public class ExportHomophoneImpl implements ExportService {


//    @Autowired
//    private CustomerHomoPhoneMapper customerHomoPhoneMapper;

    @Autowired
    CustomerHomophoneMapper customerHomophoneMapper;

    @Autowired
    YunMuSortService yunMuSortService;

    @Autowired
    ShengMuSortService shengMuSortService;

    @Autowired
    ShengDiaoSortService shengDiaoSortService;

    @Autowired
    OriginHomophoneService originHomophoneService;

    @Autowired
    HomophoneService homophoneService;


    @Override
    public void export() {
        List<Homophone> homophones = customerHomophoneMapper.getCustomerHomPhone();

        List<YunMuSort> yunMuSorts = getYunMuSorts("sort");
        List<ShengMuSort> shengMuSorts = getShengMuSorts("sort");
        List<ShengDiaoSort> shengDiaoSorts = getShengDiaoSorts("sort");


        Map<String, List<Homophone>> yunMuMap = homophones.stream().collect(Collectors.groupingBy(Homophone::getYunMu, LinkedHashMap::new, Collectors.toList()));

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("信息表");
        // 表头
        ;
        StringBuilder text = new StringBuilder();

        for (YunMuSort yunMuSort : yunMuSorts) {
            List<Homophone> yunMuList = yunMuMap.get(yunMuSort.getYunMu());
            if (BlankUtil.isNotEmpty(yunMuList)) {
                System.out.println();
//                System.out.println("[" + yunMuSort.getYunMu() + "]");
                text.append("\n").append("[").append(yunMuSort.getYunMu()).append("]").append("\n");
                Map<String, List<Homophone>> shengMuMap = yunMuList.stream().filter(homophone ->
                        homophone.getShengMu() != null
                ).collect(Collectors.groupingBy(Homophone::getShengMu));
                for (ShengMuSort shengMuSort : shengMuSorts) {
                    List<Homophone> shengMuList = shengMuMap.get(shengMuSort.getShengMu());
                    if (BlankUtil.isNotEmpty(shengMuList)) {
//                        System.out.print(shengMuSort.getShengMu() + " ");
                        text.append(shengMuSort.getShengMu()).append(" ");
                        Map<String, List<Homophone>> shengDiaoMap = shengMuList.stream().collect(Collectors.groupingBy(Homophone::getShengDiao));
                        for (ShengDiaoSort shengDiaoSort : shengDiaoSorts) {
                            List<Homophone> shengDiaoList = shengDiaoMap.get(shengDiaoSort.getShengDiao());
                            if (BlankUtil.isNotEmpty(shengDiaoList)) {
//                                System.out.print("[" + shengDiaoSort.getShengDiao() + "]");
                                text.append("[").append(shengDiaoSort.getShengDiao()).append("]");
                                String name = "";
                                for (Homophone homophone : shengDiaoList) {
                                    name = name + homophone.getName();
                                    text.append(homophone.getName());
                                }
                                name = name + " ";
//                                System.out.print(name);
                            }
                        }
                        text.append("\n");
                    }
                }
            }
        }
        System.out.println(text);

//
//        for (Homophone text : homophones) {
//            String num = "";
//            String alph = "";
//            char[] arr = text.getPin().toCharArray();
//            for (int i = 0; i < arr.length; i++) {
//                try {
//                    int a = Integer.parseInt(String.valueOf(arr[i]));
//                    num = num.concat(String.valueOf(arr[i]));
//                } catch (Exception e) {
//                    alph = alph.concat(String.valueOf(arr[i]));
//                }
//            }
//            text.setYun(num);
//
//        }
//        List<text> texts1 = new LinkedList<>();
//        Map<String, List<Text>> collect = texts.stream().collect(Collectors.groupingBy(Text::getPin));
//
//
//        List<List<cell>> sheets = new LinkedList<>();
//        for (Map.Entry<String, List<Text>> entry : collect.entrySet()) {
//
//            String num = "";
//            String alph = "";
//            char[] arr = entry.getKey().toCharArray();
//            for (int i = 0; i < arr.length; i++) {
//                try {
//                    int a = Integer.parseInt(String.valueOf(arr[i]));
//                    num = num.concat(String.valueOf(arr[i]));
//                } catch (Exception e) {
//                    alph = alph.concat(String.valueOf(arr[i]));
//                }
//            }
//            String s1;
//            String s2;
//            String s3 = "";
//            s2 = "[" + num + "]";
//            s1 = alph + ":";
//
//            for (Text text : entry.getValue()) {
//                s3 = s3 + text.getName();
//            }
//            text text = new text();
//            text.setName(s1);
//            text.setValue(s2 + s3 + "  ");
//            texts1.add(text);
//        }
//        Map<String, List<text>> collect1 = texts1.stream().collect(Collectors.groupingBy(text::getName));
//        for (Map.Entry<String, List<text>> entry : collect1.entrySet()) {
//            List<cell> bodys = new LinkedList<>();
//            cell c1 = new cell();
//            String s = "";
//            for (text text : entry.getValue()) {
//                s = text.getValue() + s;
//            }
//            String s1 = entry.getKey() + s;
//            c1.setValue(s1);
//            bodys.add(c1);
//            sheets.add(bodys);
//
//
//        }
//
//        HSSFWorkbook wb = new HSSFWorkbook();
//        ExcelExportUtils.exportExcel(httpServletResponse, "export", sheets, wb);
    }

    @Override
    public void updateHomophone() {
        List<YunMuSort> yunMuLengthSorts = getYunMuSorts("length_sort");
        List<ShengMuSort> shengMuLengthSorts = getShengMuSorts("length_sort");
        List<ShengDiaoSort> shengDiaoSorts = getShengDiaoSorts("sort");

        EntityWrapper<OriginHomophone> wrapper = new EntityWrapper<>();
        wrapper.isNotNull("ph");
        List<OriginHomophone> originHomophones = originHomophoneService.selectList(wrapper);
        List<Homophone> homophones = new LinkedList<>();
        for (OriginHomophone originHomophone : originHomophones) {
            Homophone homophone = new Homophone();
            homophone.setName(originHomophone.getPhrase());
            for (YunMuSort yunMu : yunMuLengthSorts) {
                if (isMatch(originHomophone.getPh(), yunMu.getYunMu().trim()) == null) {
                } else {
                    homophone.setYunMu(yunMu.getYunMu().trim());
                    break;
                }
            }
            for (ShengMuSort shengMuSort : shengMuLengthSorts) {
                if (isMatch(originHomophone.getPh(), shengMuSort.getShengMu().trim()) == null) {
                } else {
                    homophone.setShengMu(shengMuSort.getShengMu().trim());
                    break;
                }
            }
            for (ShengDiaoSort shengDiaoSort : shengDiaoSorts) {
                if (isMatch(originHomophone.getPh(), shengDiaoSort.getShengDiao().trim()) == null) {
                } else {
                    homophone.setShengDiao(shengDiaoSort.getShengDiao().trim());
                    break;
                }
            }

            homophones.add(homophone);
        }

        homophoneService.insertOrUpdateAllColumnBatch(homophones);
        homophones.forEach(System.out::println);
    }

    private String isMatch(String value, String regex) {

        if (!value.matches("(.*)" + regex + "(.*)")) {
            return null;
        }

        return regex;

    }

    private List<YunMuSort> getYunMuSorts(String column) {
        EntityWrapper<YunMuSort> e1 = new EntityWrapper<>();
        e1.orderBy(column);
        return yunMuSortService.selectList(e1);
    }

    private List<ShengMuSort> getShengMuSorts(String column) {
        EntityWrapper<ShengMuSort> e1 = new EntityWrapper<>();
        e1.orderBy(column);
        return shengMuSortService.selectList(e1);
    }

    private List<ShengDiaoSort> getShengDiaoSorts(String column) {
        EntityWrapper<ShengDiaoSort> e1 = new EntityWrapper<>();
        e1.orderBy(column);
        return shengDiaoSortService.selectList(e1);
    }

    public static void main(String[] args) {

    }
}
