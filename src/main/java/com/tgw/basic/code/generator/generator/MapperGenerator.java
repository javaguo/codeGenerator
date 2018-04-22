package com.tgw.basic.code.generator.generator;

import java.io.File;
import java.util.Map;

/**
 * Created by zjg on 2017/12/6.
 */
public class MapperGenerator extends BaseGenerator {

    public MapperGenerator(String tableName){
        super( tableName );
    }

    @Override
    public String getUserDefinePackage(){
        return "dao";
    }

    @Override
    public String getRelativeProPath(){
        return "src"+ File.separator+"main"+File.separator+"java"+File.separator+getTargetPackagePath();
    }

    @Override
    public String getSuffix(){
        return "Mapper.java";
    }

    @Override
    public String getTemplateName(){
        return "mapper.ftl";
    }

    @Override
    public Map<String, Object> userDefineDataMap(Map<String,Object> dataMap ){
        return dataMap;
    }
}
