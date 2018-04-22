package com.tgw.basic.code.generator.utils;

import freemarker.cache.NullCacheStorage;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

import java.io.File;
import java.io.IOException;

/**
 * Created by zjg on 2017/11/15.
 */
public class FreeMarkerTemplateUtils {

    private FreeMarkerTemplateUtils(){}
    private static final Configuration CONFIGURATION = new Configuration(Configuration.VERSION_2_3_22);

    static{
        //这里比较重要，用来指定加载模板所在的路径
        //CONFIGURATION.setTemplateLoader(new ClassTemplateLoader(CodeGenerateExample.class, "templates/"));
        try {
            String templatesPath = ResourcesUtils.getProString( ResourcesUtils.loadCodeGenerate(),"templatesPath" );
            CONFIGURATION.setDirectoryForTemplateLoading( new File( templatesPath ) );
        }catch (Exception e){
            e.printStackTrace();
        }
        CONFIGURATION.setDefaultEncoding("UTF-8");
        CONFIGURATION.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        CONFIGURATION.setCacheStorage(NullCacheStorage.INSTANCE);
    }

    public static Template getTemplate(String templateName) throws IOException {
        try {
            return CONFIGURATION.getTemplate(templateName);
        } catch (IOException e) {
            throw e;
        }
    }

    public static void clearCache() {
        CONFIGURATION.clearTemplateCache();
    }
}

