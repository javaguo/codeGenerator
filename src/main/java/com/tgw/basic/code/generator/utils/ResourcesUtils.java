package com.tgw.basic.code.generator.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by zjg on 2017/11/17.
 */
public class ResourcesUtils {

    public static Properties loadCodeGenerate(){
        return ResourcesUtils.loadProByPath( "codeGenePlatformConfig.properties" );
    }

    /**
     * 加载指定路径资源文件
     * @param path   示例：tabProConf/basicCodeGenerator.properties
     * @return
     */
    public static Properties loadProByPath( String path ){
        Properties p = new Properties();
        try {
            InputStream in = ResourcesUtils.class.getClassLoader().getResourceAsStream(path);
            p.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return p;
    }

    /**
     * 加载指定属性的值
     * @param properties
     * @param key
     * @return
     */
    public static String getProString( Properties properties,String key ){
        Object res = properties.get( key );
        if( null!=res ){
            return res.toString().trim();
        }
        return null;
    }

    /**
     * 加载指定属性的值，并将结果以数组形式返回。
     * @param properties
     * @param key
     * @param splitRegex  分割符
     * @return
     */
    public static String[] getProStringArray( Properties properties,String key,String splitRegex ){
        String[] strArray ={};
        String keyVal = getProString(properties,key);
        if(StringUtils.isNotBlank( keyVal )){
            strArray = keyVal.split( splitRegex );
        }
        return strArray;
    }

    /**
     * 加载指定属性的值，并将结果以数组形式返回。
     * 默认按照“,”对字符串进行分割。
     * @param properties
     * @param key
     * @return
     */
    public static String[] getProStringArray( Properties properties,String key ){
        return getProStringArray(properties,key,",");
    }

    /**
     * 加载指定属性的值
     * @param properties
     * @param key
     * @return
     */
    public static boolean getProBoolean( Properties properties,String key ){
        boolean res = false;
        String keyVal = getProString(properties,key);
        if( StringUtils.isNotBlank( keyVal ) ){
            res = Boolean.parseBoolean( keyVal );
        }
        return res;
    }
}
