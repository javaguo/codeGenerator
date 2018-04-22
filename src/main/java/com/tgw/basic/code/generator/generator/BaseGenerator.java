package com.tgw.basic.code.generator.generator;

import com.tgw.basic.code.generator.exception.PlatformException;
import com.tgw.basic.code.generator.utils.CodeGenerateUtils;
import com.tgw.basic.code.generator.utils.FreeMarkerTemplateUtils;
import com.tgw.basic.code.generator.utils.ResourcesUtils;
import freemarker.template.Template;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by zjg on 2017/11/25.
 */
public class BaseGenerator {

    private String tableName;//数据库表名
    private String modelName;//将数据库表名按驼峰规则转换后的名称，开头大写
    private String modelNameSmallCamel;//将数据库表名按驼峰规则转换后的名称，开头小写
    Properties codeGeneratePro;
    Properties tablePro;

    /**
     * 构造方法
     * @param tableName
     */
    public BaseGenerator(String tableName) {
        this.tableName = tableName;
        this.modelName = CodeGenerateUtils.underline2Camel( tableName,false );
        this.modelNameSmallCamel = CodeGenerateUtils.underline2Camel( tableName,true );

        Properties codeGeneratePro = ResourcesUtils.loadProByPath( "codeGenePlatformConfig.properties" );
        this.setCodeGeneratePro( codeGeneratePro );

        Properties tabPro = ResourcesUtils.loadProByPath( "codeGenePlatformConfig.properties" );
        this.setTablePro( tabPro );
    }

    /**
     * 返回项目的文件路径。
     * 例：D:\j2ee\java\workspace\idea\tgwBasic\basic-manager\basic-main-web
     * @return
     */
    public String getProjectPath(){
        String projectPath = ResourcesUtils.getProString( codeGeneratePro , "projectPath" );
        if(StringUtils.isBlank( projectPath )){
            throw new PlatformException("项目路径为空！");
        }
        return projectPath;
    }

    /**
     * 返回自定义的基本包名。不能以"."开头或结尾。
     * 例：com.tgw
     * @return
     */
    public String getBaseTargetPackage(){
        return ResourcesUtils.getProString( codeGeneratePro , "baseTargetPackage" );
    }

    /**
     * 返回框架自定义的基本包名。不能以"."开头或结尾。
     * 例：com.tgw
     * @return
     */
    public String getFrameworkBaseTargetPackage(){
        return ResourcesUtils.getProString( codeGeneratePro , "frameworkBaseTargetPackage" );
    }

    /**
     * 生成java文件时必须要覆写。
     * 用户自定义包名。不能以"."开头或结尾。
     * 例：service.impl
     * @return
     */
    public String getUserDefinePackage(){
        return "default";
    }

    /**
     * 返回完整的包名
     * 例：com.thw.user.model
     * @return
     */
    public String getTargetPackage(){
        String baseTargetPackage = getBaseTargetPackage();
        if( StringUtils.isBlank( baseTargetPackage ) ){
            throw new PlatformException("缺少包名配置！");
        }else{
            return getBaseTargetPackage()+"."+ this.getModelNameSmallCamel()+"."+this.getUserDefinePackage();
        }
    }

    /**
     * 返回完整的包路径
     * 例：com/thw/user/model
     * @return
     */
    public String getTargetPackagePath(){
        return getTargetPackage().replace(".",File.separator);
    }

    /**
     * 必须要覆写此路径
     *
     * 返回相对项目路径的路径
     * 例：src/main/java
     * @return
     */
    public String getRelativeProPath(){
        //"src"+File.separator+"main"+File.separator+"java"+File.separator+getTargetPackagePath()
        return "";
    }

    /**
     * 生成文件的全路径，不包含文件名。
     *
     * @return
     */
    public String getPath(){
        if( StringUtils.isBlank( getRelativeProPath() ) ){
            throw new PlatformException("缺少相对路径！");
        }
        File tempFile = new File( getProjectPath()+File.separator+getRelativeProPath() );
        if( !tempFile.exists() ){
            tempFile.mkdirs();
        }
        return getProjectPath()+File.separator+getRelativeProPath();
    }

    /**
     * 生成文件的前缀
     * @return
     */
    public String getPrefix(){
        return "";
    }

    /**
     * 必须要覆写
     *
     * 生成文件的后缀，包含文件类型
     * 例：xxx.java
     * @return
     */
    public String getSuffix(){
        return "";
    }

    /**
     * 生成文件的全路径，包含文件名。
     * @return
     */
    public String getFilePath(){
        if( StringUtils.isBlank( getSuffix() ) ){
            throw new PlatformException("未知的文件后缀！");
        }
        if( StringUtils.isBlank( getPrefix() ) ){
            return getPath()+File.separator+getModelName()+getSuffix();
        }else{
            return getPath()+File.separator+getPrefix()+getModelNameSmallCamel()+getSuffix();
        }
    }

    /**
     * 需要覆写此方法
     *
     * 模板文件名
     * @return
     */
    public String getTemplateName(){
        return "";
    }

    /**
     * 得到模板文件需要的数据
     * @return
     */
    public Map<String, Object> getDataMap() {
        Map<String,Object> dataMap=new HashMap<String,Object>();

        dataMap.put("tableName",this.getTableName());
        dataMap.put("modelName",this.getModelName());
        dataMap.put("modelNameSmallCamel",this.getModelNameSmallCamel());
        dataMap.put("baseTargetPackage",this.getBaseTargetPackage() );
        dataMap.put("frameworkBaseTargetPackage",this.getFrameworkBaseTargetPackage() );
        dataMap.put("package",this.getTargetPackage());

        return userDefineDataMap( dataMap );
    }

    /**
     * 将自定义的数据放入dataMap中，在模板文件中使用。
     *
     * @param dataMap
     * @return
     */
    public Map<String, Object> userDefineDataMap( Map<String,Object> dataMap ){
        return dataMap;
    }

    /**
     * 生成代码文件
     * @throws Exception
     */
    public void generateFileByTemplate( ){
        try {
            if( StringUtils.isBlank( getTableName() ) ){
                throw new PlatformException("未知的文件模板！");
            }
            Template template = FreeMarkerTemplateUtils.getTemplate( getTemplateName() );
            File file = new File( getFilePath() );
            if( !file.exists() ){
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file);
            Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"),10240);
            template.process( getDataMap(),out);
            System.out.println("成功生成文件："+getFilePath() );
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getModelNameSmallCamel() {
        return modelNameSmallCamel;
    }

    public void setModelNameSmallCamel(String modelNameSmallCamel) {
        this.modelNameSmallCamel = modelNameSmallCamel;
    }

    public Properties getCodeGeneratePro() {
        return codeGeneratePro;
    }

    public void setCodeGeneratePro(Properties codeGeneratePro) {
        this.codeGeneratePro = codeGeneratePro;
    }

    public Properties getTablePro() {
        return tablePro;
    }

    public void setTablePro(Properties tablePro) {
        this.tablePro = tablePro;
    }
}
