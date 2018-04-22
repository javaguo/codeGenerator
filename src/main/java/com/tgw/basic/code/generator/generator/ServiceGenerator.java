package com.tgw.basic.code.generator.generator;

import java.io.File;
import java.util.Map;

/**
 * Created by zjg on 2017/12/3.
 */
public class ServiceGenerator extends BaseGenerator {

    public ServiceGenerator(String tableName){
        super( tableName );
    }

    @Override
    public String getUserDefinePackage(){
        return "service";
    }

    @Override
    public String getRelativeProPath(){
        return "src"+ File.separator+"main"+File.separator+"java"+File.separator+getTargetPackagePath();
    }

    @Override
    public String getSuffix(){
        return "Service.java";
    }

    @Override
    public String getTemplateName(){
        return "service.ftl";
    }

    @Override
    public Map<String, Object> userDefineDataMap(Map<String,Object> dataMap ){
        return dataMap;
    }
}
