package com.yehuijie.homophone;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.PostgreSqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yhj on 2018/6/27.
 */
public class MybatisplusGenerator {

    @Test
    public void generateCode() {
        String packageName = "com.yehuijie.homophone";
        boolean serviceNameStartWithI = false;//user -> UserService, 设置成true: user -> IUserService
        generateByTables(serviceNameStartWithI,
                packageName
//                , "origin_homophone"
//                , "yun_mu_sort"
//                , "sheng_mu_sort"
                , "homophone"
//                , "sheng_diao_sort"


        );
    }

    private void generateByTables(boolean serviceNameStartWithI, String packageName, String... tableNames) {
        GlobalConfig config = new GlobalConfig();
        String dbUrl = "jdbc:mysql://localhost:33306/homophone_table";
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL)
                .setUrl(dbUrl)
                .setUsername("root")
                .setPassword("123456")
                .setDriverName("com.mysql.cj.jdbc.Driver");

//                .setSchemaname("poit_cloud");

        //类型转换
        dataSourceConfig.setTypeConvert(new PostgreSqlTypeConvert() {
            // 自定义数据库表字段类型转换【可选】
            @Override
            public DbColumnType processTypeConvert(String fieldType) {
                System.out.println("转换类型：" + fieldType);
                // 注意！！processTypeConvert 存在默认类型转换，如果不是你要的效果请自定义返回、非如下直接返回。
                if (fieldType.contains("numeric")) {
                    return DbColumnType.DOUBLE;
                } else {
                    return super.processTypeConvert(fieldType);
                }
            }
        });

        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig
                .setCapitalMode(true)
                .setEntityLombokModel(false)
                .setDbColumnUnderline(true)
                .setNaming(NamingStrategy.underline_to_camel)
                .setInclude(tableNames);//修改替换成你需要的表名，多个表名传数组
        config.setActiveRecord(true)
                .setAuthor("yhj")
                .setOutputDir(System.getProperty("user.dir")+"/src/main/java")
                .setEnableCache(false)
                .setOpen(false)
                .setBaseColumnList(false)
                .setIdType(IdType.UUID)//主键类型
                .setFileOverride(true);
        if (!serviceNameStartWithI) {
            config.setServiceName("%sService");
        }


        InjectionConfig ic = new InjectionConfig() {
            @Override
            public void initMap() {
                this.setMap(getMap());
            }
        };

        List<FileOutConfig> foc = new ArrayList<>();
        foc.add(new FileOutConfig("/templates/mapper.xml.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return "src/main/resources/mapper/" + tableInfo.getEntityName() + ".xml";
            }
        });
        ic.setFileOutConfigList(foc);


        TemplateConfig tc = new TemplateConfig();
        tc.setXml(null);
        tc.setController(null);

        new AutoGenerator()
                .setGlobalConfig(config)
                .setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
                .setTemplate(tc)
                .setCfg(ic)
                .setPackageInfo(
                        new PackageConfig()
                                .setParent(packageName)
                                .setController("controller")
                                .setEntity("entity")
                ).execute();
    }

    private void generateByTables(String packageName, String... tableNames) {
        generateByTables(true, packageName, tableNames);
    }



}
