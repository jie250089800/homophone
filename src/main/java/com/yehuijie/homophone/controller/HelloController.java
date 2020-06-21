package com.yehuijie.homophone.controller;

import com.yehuijie.homophone.service.ExportService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: YeHuiJie
 * @Date: 2019/9/17 3:28 上午
 */
// 这个类的所有方法返回的数据直接写给浏览器(如果是对象转为json)
//@ResponseBody
//@Controller
@RestController
public class HelloController {
    @Autowired
    private ExportService exportService;

    @RequestMapping(path = "/hellp", method = RequestMethod.GET)
    @ApiOperation(value = "注册用户接口", notes = "注册用户名密码，成功后返回uid")
    public void export() throws Exception {
         exportService.export();
    }


}
