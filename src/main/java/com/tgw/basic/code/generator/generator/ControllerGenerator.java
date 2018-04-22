package com.tgw.basic.code.generator.generator;

import java.io.File;
import java.util.Map;

/**
 * Created by zjg on 2017/12/6.
 */
public class ControllerGenerator extends BaseGenerator {

    public ControllerGenerator(String tableName){
        super( tableName );
    }

    @Override
    public String getUserDefinePackage(){
        return "controller";
    }

    @Override
    public String getRelativeProPath(){
        return "src"+ File.separator+"main"+File.separator+"java"+File.separator+getTargetPackagePath();
    }

    @Override
    public String getSuffix(){
        return "Controller.java";
    }

    @Override
    public String getTemplateName(){
        return "controller.ftl";
    }

    @Override
    public Map<String, Object> userDefineDataMap(Map<String,Object> dataMap ){
        return dataMap;
    }
}
