package com.yehuijie.homophone.service.Impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.sun.tools.corba.se.idl.constExpr.Or;
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

    private final String EMPTY_VALUE = "*";

    @Override
    public void export() {
        List<Homophone> homophones = customerHomophoneMapper.getCustomerHomPhone();

        List<YunMuSort> yunMuSorts = getYunMuSorts("sort");
        List<ShengMuSort> shengMuSorts = getShengMuSorts("sort");
        List<ShengDiaoSort> shengDiaoSorts = getShengDiaoSorts("sort");
        Long num = 0L;

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
                text.append("\n").append("[").append(yunMuSort.getYunMu()).append("]").append("\n");
                Map<String, List<Homophone>> shengMuMap = yunMuList.stream().filter(homophone ->
                        homophone.getShengMu() != null
                ).collect(Collectors.groupingBy(Homophone::getShengMu));
                for (ShengMuSort shengMuSort : shengMuSorts) {
                    List<Homophone> shengMuList = shengMuMap.get(shengMuSort.getShengMu());
                    if (BlankUtil.isNotEmpty(shengMuList)) {
                        text.append(shengMuSort.getShengMu()).append(" ");
                        Map<String, List<Homophone>> shengDiaoMap = shengMuList.stream().filter(homophone ->
                                homophone.getShengDiao() != null
                        ).collect(Collectors.groupingBy(Homophone::getShengDiao));
                        for (ShengDiaoSort shengDiaoSort : shengDiaoSorts) {
                            List<Homophone> shengDiaoList = shengDiaoMap.get(shengDiaoSort.getShengDiao());
                            if (BlankUtil.isNotEmpty(shengDiaoList)) {
                                text.append("[").append(shengDiaoSort.getShengDiao()).append("]");
                                for (Homophone homophone : shengDiaoList) {
                                    num++;
                                    text.append(homophone.getName());
                                }
                            }
                        }
                        text.append("\n");
                    }
                }
            }
        }
        System.out.println(num);
        System.out.println(text);

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
            if (isMatch(originHomophone.getPh().trim(), "；") || isMatch(originHomophone.getPh().trim(), ";")) {
                String[] split;
                split = originHomophone.getPh().split("；");
                if (split.length <= 1) {
                    split = originHomophone.getPh().split(";");
                }
                for (String s : split) {
                    originHomophone.setPh(s);
                    Homophone homophone = getHomophone(yunMuLengthSorts, shengMuLengthSorts, shengDiaoSorts, originHomophone);
                    homophones.add(setNotNullValue(homophone));
                }
            } else {
                Homophone homophone = getHomophone(yunMuLengthSorts, shengMuLengthSorts, shengDiaoSorts, originHomophone);
                homophones.add(setNotNullValue(homophone));
            }

        }

        List<Long> ids = homophoneService.selectList(null).stream().map(Homophone::getId).collect(Collectors.toList());
        if (BlankUtil.isNotEmpty(ids)) {
            homophoneService.deleteBatchIds(ids);
        }
        homophoneService.insertBatch(homophones);
        homophones.forEach(System.out::println);
    }

    @Override
    public void verify() {
        List<Homophone> homophones = homophoneService.selectList(null);
        List<ShengDiaoSort> shengDiaoSorts = getShengDiaoSorts("id");
        Map<String, List<ShengDiaoSort>> shengDiaoMap = shengDiaoSorts.stream().collect(Collectors.groupingBy(ShengDiaoSort::getShengDiao));
        List<Homophone> incorrectList = homophones.stream().filter(homophone -> {

            List<ShengDiaoSort> diaoSorts = shengDiaoMap.get(homophone.getShengDiao());
            if (diaoSorts.size() <= 0) {
                return true;
            } else if (!(homophone.getShengMu() + homophone.getYunMu() + diaoSorts.get(0).getOriginValue()).equals(homophone.getOriginValue())) {
                return true;
            }
            return false;
        }).collect(Collectors.toList());

        incorrectList.forEach(System.out::println);

    }

    private Homophone getHomophone(List<YunMuSort> yunMuLengthSorts, List<ShengMuSort> shengMuLengthSorts, List<ShengDiaoSort> shengDiaoSorts, OriginHomophone originHomophone) {
        Homophone homophone = new Homophone();
        homophone.setName(originHomophone.getPhrase());
        homophone.setOriginValue(originHomophone.getPh());
        for (YunMuSort yunMu : yunMuLengthSorts) {
            if (isMatch(originHomophone.getPh().trim(), yunMu.getYunMu().trim())) {
                homophone.setYunMu(yunMu.getYunMu().trim());
                refreshOriginValue(originHomophone, yunMu.getYunMu().trim());
                break;
            }
        }
        for (ShengMuSort shengMuSort : shengMuLengthSorts) {
            if (isMatch(originHomophone.getPh().trim(), shengMuSort.getShengMu().trim())) {
                homophone.setShengMu(shengMuSort.getShengMu().trim());
                refreshOriginValue(originHomophone, shengMuSort.getShengMu().trim());
                break;
            }
        }
        for (ShengDiaoSort shengDiaoSort : shengDiaoSorts) {
            if (isMatch(originHomophone.getPh().trim(), shengDiaoSort.getOriginValue().trim())) {
                homophone.setShengDiao(shengDiaoSort.getShengDiao().trim());
                break;
            }
        }
        return homophone;
    }

    private boolean isMatch(String value, String regex) {

        return value.matches("(.*)" + regex + "(.*)");

    }

    private void refreshOriginValue(OriginHomophone originHomophone, String regex) {

        if (BlankUtil.isEmpty(regex) || EMPTY_VALUE.equals(regex)) {
            return;
        }
        String s = originHomophone.getPh().replaceAll(regex, "");
        originHomophone.setPh(s);

    }

    private Homophone setNotNullValue(Homophone homophone) {

        if (BlankUtil.isEmpty(homophone.getYunMu())) {
            homophone.setYunMu(EMPTY_VALUE);
        }
        if (BlankUtil.isEmpty(homophone.getShengMu())) {
            homophone.setShengMu(EMPTY_VALUE);
        }
        if (BlankUtil.isEmpty(homophone.getShengDiao())) {
            homophone.setShengDiao(EMPTY_VALUE);
        }
        return homophone;
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

}
