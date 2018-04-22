package com.tgw.basic.code.generator;

import com.tgw.basic.code.generator.generator.*;
import com.tgw.basic.code.generator.utils.ResourcesUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.Properties;

/**
 * 自动生成tgw框架项目代码工具
 *
 * Created by zjg on 2017/11/25.
 */
public class MainCodeGenePlatform {

    public static void main(String[] args) throws Exception{
        /**
         *
         * 步骤：
         * 1.先使用MainCodeGeneMybatis生成Mybatis相关代码。
         * 2.修改codeGenePlatformConfig.properties文件。
         *
         *
         * 生成代码后需要手动完成的工作：
         * 将生成的代码手动复制到项目中。（生成代码路径不要配置成项目代码最终路径，找一个临时目录，生成生手动复制，防止重复生成造成代码覆盖）
         * 1.生成model文件后，model需要extends AbstractBaseBean，修改包名。
         * 2.生成sqlMapper文件之后需要对包名做手动修正。主要修改namespace包路径，将mapper修改为dao。
         * 3.sqlMapper文件中增加searchData语句。
         */
        //获取要生成代码的表名称
        Properties codeGeneratePro = ResourcesUtils.loadProByPath( "codeGenePlatformConfig.properties" );
        String[] tableArray = ResourcesUtils.getProStringArray( codeGeneratePro,"table" );
        //循环处理
        if( null!=tableArray ){
            for( String tableName: tableArray ){
               //生成model文件
                createModel( codeGeneratePro,tableName );
                //生成sqlMapper文件、mapper接口。
                createMapper( codeGeneratePro,tableName );
                //生成service接口
                createService( tableName );
                //生成service实现类
                createServiceImpl( tableName );
                //生成controller
                createController( tableName );
            }
        }
    }

    /**
     * 将mybatis工具生成的文件复制到指定目录下
     * @param codeGeneratePro
     * @param tableName
     */
    public static void createModel( Properties codeGeneratePro,String tableName ){
        BaseGenerator baseGenerator = new BaseGenerator(tableName);

        //目标文件
        String tempPackage = baseGenerator.getBaseTargetPackage()+"."+ baseGenerator.getModelNameSmallCamel()+".model";
        String tempPackagePath = tempPackage.replace(".",File.separator);
        String destPath = baseGenerator.getProjectPath()+File.separator
                        +"src"+ File.separator+"main"+File.separator+"java"+File.separator
                        +tempPackagePath
                        +File.separator+baseGenerator.getModelName()+".java";
        File destFile = new File( destPath );

        //要复制的原文件
        String tempBasePathMyBatis = ResourcesUtils.getProString( codeGeneratePro,"tempBasePathMyBatis" );
        String tempJavaModelPathMyBatis = ResourcesUtils.getProString( codeGeneratePro,"tempJavaModelPathMyBatis" );
        String srcPath = tempBasePathMyBatis+ File.separator+tempJavaModelPathMyBatis+File.separator+baseGenerator.getModelName()+".java";
        File srcFile = new File(srcPath);

        try {
            FileUtils.copyFile( srcFile,destFile );
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     *
     * @param codeGeneratePro
     * @param tableName
     */
    public static void createMapperJava( Properties codeGeneratePro,String tableName ){
        MapperGenerator mapperGenerator = new MapperGenerator( tableName );
        mapperGenerator.generateFileByTemplate();

        /*BaseGenerator baseGenerator = new BaseGenerator(tableName);

        //目标文件
        String tempPackage = baseGenerator.getBaseTargetPackage()+"."+ baseGenerator.getModelNameSmallCamel()+".dao";
        String tempPackagePath = tempPackage.replace(".",File.separator);
        String destPath = baseGenerator.getProjectPath()+File.separator
                +"src"+ File.separator+"main"+File.separator+"java"+File.separator
                +tempPackagePath
                +File.separator+baseGenerator.getModelName()+"Mapper.java";
        File destFile = new File( destPath );

        //要复制的原文件
        String tempBasePathMyBatis = ResourcesUtils.getProString( codeGeneratePro,"tempBasePathMyBatis" );
        String tempJavaModelPathMyBatis = ResourcesUtils.getProString( codeGeneratePro,"tempJavaClientPathMyBatis" );
        String srcPath = tempBasePathMyBatis+ File.separator+tempJavaModelPathMyBatis+File.separator+baseGenerator.getModelName()+"Mapper.java";
        File srcFile = new File(srcPath);

        try {
            FileUtils.copyFile( srcFile,destFile );
        }catch (Exception e){
            e.printStackTrace();
        }*/
    }

    /**
     * 将mybatis工具生成的文件复制到指定目录下
     * @param codeGeneratePro
     * @param tableName
     */
    public static void createMapperXML( Properties codeGeneratePro,String tableName ){
        BaseGenerator baseGenerator = new BaseGenerator(tableName);

        //目标文件
        String tempPackage = baseGenerator.getBaseTargetPackage()+"."+ baseGenerator.getModelNameSmallCamel()+".dao";
        String tempPackagePath = tempPackage.replace(".",File.separator);
        String destPath = baseGenerator.getProjectPath()+File.separator
                +"src"+ File.separator+"main"+File.separator+"java"+File.separator
                +tempPackagePath
                +File.separator+baseGenerator.getModelName()+"Mapper.xml";
        File destFile = new File( destPath );

        //要复制的原文件
        String tempBasePathMyBatis = ResourcesUtils.getProString( codeGeneratePro,"tempBasePathMyBatis" );
        String tempJavaModelPathMyBatis = ResourcesUtils.getProString( codeGeneratePro,"tempSqlMapPathMyBatis" );
        String srcPath = tempBasePathMyBatis+ File.separator+tempJavaModelPathMyBatis+File.separator+baseGenerator.getModelName()+"Mapper.xml";
        File srcFile = new File(srcPath);

        try {
            FileUtils.copyFile( srcFile,destFile );
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 将mybatis工具生成的文件复制到指定目录下
     * @param codeGeneratePro
     * @param tableName
     */
    public static void createMapper( Properties codeGeneratePro,String tableName ){
        createMapperJava( codeGeneratePro,tableName);
        createMapperXML( codeGeneratePro,tableName );
    }

    /**
     * 使用freemaker模板生成service接口代码
     * @param tableName
     */
    public static void createService( String tableName ){
        ServiceGenerator serviceGenerator = new ServiceGenerator( tableName );
        serviceGenerator.generateFileByTemplate();
    }

    /**
     * 使用freemaker模板生成serviceImpl代码
     * @param tableName
     */
    public static void createServiceImpl( String tableName ){
        ServiceImplGenerator serviceImplGenerator = new ServiceImplGenerator( tableName );
        serviceImplGenerator.generateFileByTemplate();
    }

    /**
     * 使用freemaker模板生成controller代码
     * @param tableName
     */
    public static void createController( String tableName ){
        ControllerGenerator controllerGenerator = new ControllerGenerator( tableName );
        controllerGenerator.generateFileByTemplate();
    }
}
