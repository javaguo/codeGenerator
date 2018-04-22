package ${baseTargetPackage}.${modelNameSmallCamel}.controller;

import com.tgw.basic.common.exception.PlatformException;
import com.tgw.basic.common.utils.collections.PlatformCollectionsUtils;
import com.tgw.basic.common.utils.config.PlatformSysConstant;
import com.tgw.basic.common.utils.string.PlatformStringUtils;
import com.tgw.basic.framework.controller.BaseController;
import com.tgw.basic.framework.model.controller.SysEnController;
import com.tgw.basic.framework.model.controller.SysEnControllerField;
import com.tgw.basic.framework.model.controller.SysEnControllerFunction;

import ${baseTargetPackage}.${modelNameSmallCamel}.model.${modelName};
import ${baseTargetPackage}.${modelNameSmallCamel}.service.${modelName}Service;

import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/${modelNameSmallCamel}")
public class ${modelName}Controller extends BaseController<${modelName}> {

    @Resource
    private ${modelName}Service ${modelNameSmallCamel}Service;

    @Override
    public void initControllerBaseInfo(SysEnController controller) throws PlatformException {
        controller.setIdentifier( "${modelName}List" );// 每一个列表页面的唯一身份id
        controller.setLoadDataUrl( "${modelNameSmallCamel}/searchData.do" );//加载列表页面数据的方法
        controller.setControllerBaseUrl( "${modelNameSmallCamel}/" );//控制器的请求地址
    }

    @Override
    public void beforeSearch(HttpServletRequest request, HttpServletResponse response, ${modelName} bean) throws PlatformException{
        //可手动调用父类的方法
        super.beforeSearch(request,response,bean);
    }

    @Override
    public void afterSearch(HttpServletRequest request, HttpServletResponse response, ${modelName} bean) throws PlatformException{
        //可手动调用父类的方法
        super.afterSearch(request,response,bean);
    }

    @Override
    public void initSearch(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView, SysEnController controller, ${modelName} bean ) throws PlatformException {
        /**
        * 将具体的业务的service对象赋值给baseservice，必须的操作。
        * service层需要将具体业务的mapper赋值给BaseModelMapper
        *
        * 此操作主要解决的问题是BaseModelMapper无法注入到BaseServiceImpl中的问题。手动赋值。
        *
        * 要点：
        * 1.BaseController会调用统一的searchData()接口查询具体的业务数据。
        * 2.具体业务的mapper文件中实现searchData查询语句
        */
        if( null!=this.get${modelName}Service() ){
            super.initService(  this.get${modelName}Service()  );
        }else{

        }

        /**
        *此处的配置会覆盖jsp页面中默认的配置。
        * 此处配置也可以写在   initControllerBaseInfo()或initField()或initFunction()方法中
        */
        //String addWindowConfigs = "title: '添加窗口',width:800";
        //String editWindowConfigs = "title: '编辑窗口',width:800";
        //String viewWindowConfigs = "title: '查看详情窗口',width:600";
        //controller.addWindowConfig( addWindowConfigs,editWindowConfigs,viewWindowConfigs );
    }

    @Override
    public void initField( SysEnController controller ) throws PlatformException {
        /**
        * 注意事项：
        * 1.定义的变量中不要包含SavePathHidden。SavePathHidden被框架使用。用来存储上传附件的路径。
        */
        //构造字段
        controller.addFieldId("id","ID",null);

        controller.addFieldDatetime("addTime","添加时间",true,false,false,false,false,null);
        controller.addFieldDatetime("updateTime","更新时间",true,false,false,false,false,null);
    }

    @Override
    public void initFunction(SysEnController controller) throws PlatformException {

    }

    @Override
    public void beforeSaveBean(HttpServletRequest request, HttpServletResponse response, ${modelName} bean) throws PlatformException{
        Date date = new Date();
        bean.setAddTime( date );
        bean.setUpdateTime( date );
    }

    @Override
    public void beforeUpdateBean(HttpServletRequest request, HttpServletResponse response,Object bean  ) throws PlatformException{
        ${modelName} tempBean = (${modelName})bean;
        tempBean.setUpdateTime( new Date() );
    }

    @Override
    public void beforeMenuAjaxUpdateBean(HttpServletRequest request, HttpServletResponse response, Object bean  ) throws PlatformException{
        ${modelName} tempBean = (${modelName})bean;
        tempBean.setUpdateTime( new Date() );
    }

    @Override
    public List<Map<String,Object>> loadComboBoxDataMap(HttpServletRequest request, HttpServletResponse response, ${modelName} bean, String parentId) throws PlatformException{
        /**
        * 查询下拉框数据
        *
        * Controller中所有获取下拉框数据的方法都在此方法中实现
        */
        String comboBoxFlag = request.getParameter("comboBoxFlag");
        List<Map<String,Object>> res = null;

        /**if( "loadDistrict".equals( comboBoxFlag ) ){
            res = this.get${modelName}Service().queryDistrictComboBoxMap( parentId );
        }*/

        return res;
    }

    @Override
    public List<Map<String,Object>> loadTreeNodeDataMap(HttpServletRequest request, HttpServletResponse response, ${modelName} bean) throws PlatformException{
        /**
        * 加载树结点需要的数据
        *
        * Controller中所有获取树节点数据的方法都在此方法中实现。
        * 除非自定义了url
        */
        String treeFlag = request.getParameter("treeFlag");
        List<Map<String,Object>> res = null;

        if( "treeFlag".equals( treeFlag ) ){
            //res = get${modelName}Service().queryDistrictTreeMap();
        }else{
            throw new PlatformException("获取树节点信息时没有找到匹配的查询方法！");
        }

        return res;
    }

    public ${modelName}Service get${modelName}Service() {
        return ${modelNameSmallCamel}Service;
    }

    public void set${modelName}Service(${modelName}Service ${modelNameSmallCamel}Service) {
        this.${modelNameSmallCamel}Service = ${modelNameSmallCamel}Service;
    }
}
