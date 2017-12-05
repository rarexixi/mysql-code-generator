<#assign className = table.tableClassName>
<#assign classNameLower = table.tableClassNameFirstLower>
<#assign primaryKey = table.primaryKey>
<#assign primaryKeyParameters = table.primaryKeyParameters>
<#assign primaryKeyParameterValues = table.primaryKeyParameterValues>
package ${basePackage}.quickapi.service.hystric;

import org.xi.common.constant.OperationConstants;
import org.xi.common.model.Result;
import ${basePackage}.entity.${className}Entity;
import ${basePackage}.parameter.${className}SelectParameter;
import ${basePackage}.vo.${className}Vo;
import ${basePackage}.quickapi.service.${className}Service;

import com.github.pagehelper.PageInfo;

import org.springframework.stereotype.Service;

import java.util.List;

<#include "/include/java_copyright.ftl">
@Service("${classNameLower}Service")
public class ${className}ServiceHystric implements ${className}Service {

    /**
     * 添加
     *
     * @param ${classNameLower}
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @Override
    public Result<${className}Entity> add${className}(${className}Entity ${classNameLower}, String sessionId) {

        return new Result<>(OperationConstants.SYSTEM_ERROR);
    }

    /**
     * 添加列表
     *
     * @param ${classNameLower}List
     * @param sessionId
     * @return
    <#include "/include/author_info1.ftl">
     */
    @Override
    public Result<${className}Entity> add${className}List(List<${className}Entity> ${classNameLower}List, String sessionId) {

        return new Result<>(OperationConstants.SYSTEM_ERROR);
    }
    <#if table.hasPrimaryKey>

    /**
     * 根据主键物理删除
     *
     <#list primaryKey as column>
     * @param ${column.columnFieldNameFirstLower}
     </#list>
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @Override
    public Result<${className}Entity> delete${className}ByPk(${primaryKeyParameters}, String sessionId) {

        return new Result<>(OperationConstants.SYSTEM_ERROR);
    }
    <#if table.validStatusColumn??>

    /**
     * 根据主键冻结
     *
     <#list primaryKey as column>
     * @param ${column.columnFieldNameFirstLower}
     </#list>
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @Override
    public Result<${className}Entity> disable${className}ByPk(${primaryKeyParameters}, String sessionId) {

        return new Result<>(OperationConstants.SYSTEM_ERROR);
    }

    /**
     * 根据主键激活
     *
     <#list primaryKey as column>
     * @param ${column.columnFieldNameFirstLower}
     </#list>
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @Override
    public Result<${className}Entity> enable${className}ByPk(${primaryKeyParameters}, String sessionId) {

        return new Result<>(OperationConstants.SYSTEM_ERROR);
    }
    </#if>

    /**
     * 根据主键更新
     *
     * @param ${classNameLower}
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @Override
    public Result<${className}Entity> update${className}ByPk(${className}Entity ${classNameLower}, String sessionId) {

        return new Result<>(OperationConstants.SYSTEM_ERROR);
    }

    /**
     * 根据主键获取
     *
     <#list primaryKey as column>
     * @param ${column.columnFieldNameFirstLower}
     </#list>
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @Override
    public Result<${className}Vo> get${className}ByPk(${primaryKeyParameters}, String sessionId) {

        return new Result<>(OperationConstants.SYSTEM_ERROR);
    }
    </#if>

    /**
     * 分页查询
     *
     * @param parameter
     * @param page
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @Override
    public Result<PageInfo<${className}Vo>> find${className}PageList(${className}SelectParameter parameter, PageInfo page, String sessionId) {

        return new Result<>(OperationConstants.SYSTEM_ERROR);
    }

}