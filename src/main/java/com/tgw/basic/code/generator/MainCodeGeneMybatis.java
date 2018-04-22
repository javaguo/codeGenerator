package com.tgw.basic.code.generator;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * 自动生成mybatis相关代码工具
 *
 * Created by zjg on 2017/11/13.
 */
public class MainCodeGeneMybatis {

    /**
     * 步骤：
     * 修改/generator/codeGeneMybatisConfig.xml配置文件。
     * 1.修改数据库连接信息。
     * 2.修改javaModelGenerator、sqlMapGenerator、javaClientGenerator三个节点中的targetPackage属性値。
     *   targetPackage属性值为包路径，targetProject为生成代码的临时位置，一般不需要改动。
     * 3.修改table节点的表名。
     * 4.执行生成mybatis代码方法。
     */
    public static void main(String[] args){
        try{
            List<String> warnings = new ArrayList<String>();
            boolean overwrite = true;
            ConfigurationParser cp = new ConfigurationParser(warnings);
            Configuration config = cp.parseConfiguration(
                    MainCodeGeneMybatis.class.getResourceAsStream("/generator/codeGeneMybatisConfig.xml"));
            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
            myBatisGenerator.generate(null);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
