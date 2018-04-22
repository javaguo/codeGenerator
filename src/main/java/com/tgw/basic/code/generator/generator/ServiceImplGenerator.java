package com.tgw.basic.code.generator.generator;

import java.io.File;
import java.util.Map;

/**
 * Created by zjg on 2017/12/3.
 */
public class ServiceImplGenerator extends BaseGenerator {

    public ServiceImplGenerator(String tableName){
        super( tableName );
    }

    @Override
    public String getUserDefinePackage(){
        return "service.impl";
    }

    @Override
    public String getRelativeProPath(){
        return "src"+ File.separator+"main"+File.separator+"java"+File.separator+getTargetPackagePath();
    }

    @Override
    public String getSuffix(){
        return "ServiceImpl.java";
    }

    @Override
    public String getTemplateName(){
        return "serviceImpl.ftl";
    }

    @Override
    public Map<String, Object> userDefineDataMap(Map<String,Object> dataMap ){
        return dataMap;
    }
}
