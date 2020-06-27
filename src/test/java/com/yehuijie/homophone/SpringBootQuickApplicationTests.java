package com.yehuijie.homophone;

import com.yehuijie.homophone.entity.Homophone;
import com.yehuijie.homophone.entity.ShengDiaoSort;
import com.yehuijie.homophone.mapper.ShengDiaoSortMapper;
import com.yehuijie.homophone.mapper.customer.CustomerHomophoneMapper;
import com.yehuijie.homophone.service.ExportService;
import com.yehuijie.homophone.service.HomophoneService;
import com.yehuijie.homophone.service.ShengDiaoSortService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.net.ssl.HostnameVerifier;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootQuickApplicationTests {

    Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    ShengDiaoSortMapper shengDiaoSortMapper;
    @Autowired
    CustomerHomophoneMapper customerHomophoneMapper;

    @Autowired
    ExportService exportService;

    @Test
    public void contextLoads() {
        List<Homophone> customerHomPhone = customerHomophoneMapper.getCustomerHomPhone();
        customerHomPhone.forEach(System.out::println);
    }


    @Test
    public void export() {
        exportService.export();
    }


    @Test
    public void updateHomophoneTable() {
        exportService.updateHomophone();
    }


    @Test
    public void verify() {
        exportService.verify();
    }
}
