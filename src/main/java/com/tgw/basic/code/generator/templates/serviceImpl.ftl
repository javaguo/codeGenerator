package ${package};


import com.tgw.basic.common.exception.PlatformException;
import com.tgw.basic.framework.service.impl.BaseServiceImpl;
import ${baseTargetPackage}.${modelNameSmallCamel}.service.${modelName}Service;
import ${baseTargetPackage}.${modelNameSmallCamel}.dao.${modelName}Mapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("${modelNameSmallCamel}Service")
public class ${modelName}ServiceImpl extends BaseServiceImpl implements ${modelName}Service {
    @Resource
    private ${modelName}Mapper ${modelNameSmallCamel}Mapper;

    @Override
    public void initMapper() {
        /**
        * 具体业务service层必须覆写此方法
        */
        if( null!=${modelNameSmallCamel}Mapper ){
            super.setBaseModelMapper( this.get${modelName}Mapper() );
        }
    }

    public ${modelName}Mapper get${modelName}Mapper() {
        return ${modelNameSmallCamel}Mapper;
    }

    public void set${modelName}Mapper(${modelName}Mapper ${modelNameSmallCamel}Mapper) {
        this.${modelNameSmallCamel}Mapper = ${modelNameSmallCamel}Mapper;
    }
}
