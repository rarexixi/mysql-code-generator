<#include "/include/table/properties.ftl">
package ${basePackage}.controller;

import ${baseCommonPackage}.model.ResultVo;
import ${baseCommonPackage}.validation.*;
import ${basePackage}.condition.${className}Condition;
import ${basePackage}.entity.${className}Entity;
import ${basePackage}.parameter.${className}SelectParameter;
import ${basePackage}.service.${className}Service;
import ${basePackage}.vo.${className}Vo;

import com.github.pagehelper.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.*;
import java.util.List;

<#include "/include/java_copyright.ftl">
@RequestMapping("/${classNameLower}")
@RestController
public class ${className}Controller {

    @Autowired
    private ${className}Service ${classNameLower}Service;

    /**
     * 添加
     *
     * @param entity
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResultVo<${className}Entity> add(@Validated({DataAdd.class}) @RequestBody ${className}Entity entity, Errors errors, @RequestParam(value = "sessionId", required = false) String sessionId) {

        int count = ${classNameLower}Service.insert(entity);
        ResultVo<${className}Entity> result = new ResultVo<>(entity);
        return result;
    }

    /**
     * 添加列表
     *
     * @param list
     * @param sessionId
     * @return
    <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = "/addList", method = RequestMethod.POST)
    public ResultVo<Integer> addList(@Validated({DataAdd.class}) @RequestBody List<${className}Entity> list, Errors errors, @RequestParam(value = "sessionId", required = false) String sessionId) {

        int count = ${classNameLower}Service.insert(list);
        ResultVo<Integer> result = new ResultVo<>(count);
        return result;
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
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ResultVo<Integer> delete(<#include "/include/table/validate_primary_parameters.ftl">, @RequestParam(value = "sessionId", required = false) String sessionId) {

        ${className}Condition condition = getPkCondition(<#include "/include/table/primary_values.ftl">);
        int count = ${classNameLower}Service.delete(condition);
        ResultVo<Integer> result = new ResultVo<>(count);
        return result;
    }
    <#if (table.uniquePrimaryKey??)>

    /**
     * 根据主键列表物理删除
     *
     * @param ${table.uniquePrimaryKey.columnFieldName?uncap_first}List
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResultVo<Integer> delete(@RequestBody List<${table.uniquePrimaryKey.columnFieldType}> ${table.uniquePrimaryKey.columnFieldName?uncap_first}List, @RequestParam(value = "sessionId", required = false) String sessionId) {

        ${className}Condition condition = getPkListCondition(${table.uniquePrimaryKey.columnFieldName?uncap_first}List);
        int count = ${classNameLower}Service.delete(condition);
        ResultVo<Integer> result = new ResultVo<>(count);
        return result;
    }
    </#if>
    <#if table.validStatusColumn??>

    /**
     * 根据主键禁用
     *
     <#list primaryKey as column>
     * @param ${column.columnFieldNameFirstLower}
     </#list>
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = "/disable", method = RequestMethod.GET)
    public ResultVo<Integer> disable(<#include "/include/table/validate_primary_parameters.ftl">, @RequestParam(value = "sessionId", required = false) String sessionId) {

        ${className}Condition condition = getPkCondition(<#include "/include/table/primary_values.ftl">);
        ${className}Entity entity = new ${className}Entity();
        entity.set${table.validStatusColumn.columnFieldName}(${table.validStatusField.invalidValue});
        int count = ${classNameLower}Service.update(entity, condition);
        ResultVo<Integer> result = new ResultVo<>(count);
        return result;
    }

    /**
     * 根据主键启用
     *
     <#list primaryKey as column>
     * @param ${column.columnFieldNameFirstLower}
     </#list>
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = "/enable", method = RequestMethod.GET)
    public ResultVo<Integer> enable(<#include "/include/table/validate_primary_parameters.ftl">, @RequestParam(value = "sessionId", required = false) String sessionId) {

        ${className}Condition condition = getPkCondition(<#include "/include/table/primary_values.ftl">);
        ${className}Entity entity = new ${className}Entity();
        entity.set${table.validStatusColumn.columnFieldName}(${table.validStatusField.validValue});
        int count = ${classNameLower}Service.update(entity, condition);
        ResultVo<Integer> result = new ResultVo<>(count);
        return result;
    }
    <#if (table.uniquePrimaryKey??)>

    /**
     * 根据主键列表禁用
     *
     * @param ${table.uniquePrimaryKey.columnFieldName?uncap_first}List
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = "/disable", method = RequestMethod.POST)
    public ResultVo<Integer> disable(@RequestBody List<${table.uniquePrimaryKey.columnFieldType}> ${table.uniquePrimaryKey.columnFieldName?uncap_first}List, @RequestParam(value = "sessionId", required = false) String sessionId) {

        ${className}Condition condition = getPkListCondition(${table.uniquePrimaryKey.columnFieldName?uncap_first}List);
        ${className}Entity entity = new ${className}Entity();
        entity.set${table.validStatusColumn.columnFieldName}(${table.validStatusField.invalidValue});
        int count = ${classNameLower}Service.update(entity, condition);
        ResultVo<Integer> result = new ResultVo<>(count);
        return result;
    }

    /**
     * 根据主键列表启用
     *
     * @param ${table.uniquePrimaryKey.columnFieldName?uncap_first}List
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = "/enable", method = RequestMethod.POST)
    public ResultVo<Integer> enable(@RequestBody List<${table.uniquePrimaryKey.columnFieldType}> ${table.uniquePrimaryKey.columnFieldName?uncap_first}List, @RequestParam(value = "sessionId", required = false) String sessionId) {

        ${className}Condition condition = getPkListCondition(${table.uniquePrimaryKey.columnFieldName?uncap_first}List);
        ${className}Entity entity = new ${className}Entity();
        entity.set${table.validStatusColumn.columnFieldName}(${table.validStatusField.validValue});
        int count = ${classNameLower}Service.update(entity, condition);
        ResultVo<Integer> result = new ResultVo<>(count);
        return result;
    }
    </#if>
    </#if>

    /**
     * 根据主键更新
     *
     * @param entity
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResultVo<Integer> update(@Validated({DataEdit.class}) @RequestBody ${className}Entity entity, Errors errors<#if !table.hasAutoIncrementUniquePrimaryKey><#list primaryKey as column>, @RequestParam(value="old${column.columnFieldName}") ${column.columnFieldType} old${column.columnFieldName}</#list></#if>, @RequestParam(value = "sessionId", required = false) String sessionId) {

        <#if !table.hasAutoIncrementUniquePrimaryKey>
        ${className}Condition condition = getPkCondition(<#if !table.hasAutoIncrementUniquePrimaryKey><#list primaryKey as column><#if (column_index > 0)>, </#if>old${column.columnFieldName}</#list></#if>);
        <#else>
        ${className}Condition condition = getPkCondition(<#list primaryKey as column><#if (column_index > 0)>, </#if>entity.get${column.columnFieldName}()</#list>);
        </#if>
        int count = ${classNameLower}Service.update(entity, condition);
        ResultVo<Integer> result = new ResultVo<>(count);
        return result;
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
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResultVo<${className}Vo> get(<#include "/include/table/validate_primary_parameters.ftl">, @RequestParam(value = "sessionId", required = false) String sessionId) {

        ${className}Vo vo = ${classNameLower}Service.getByPk(<#include "/include/table/primary_values.ftl">);
        ResultVo<${className}Vo> result = new ResultVo<>(vo);
        return result;
    }
    </#if>

    /**
     * 分页查询
     *
     * @param parameter
     * @param sessionId
     * @return
     <#include "/include/author_info1.ftl">
     */
    @RequestMapping(value = "/getPageInfo", method = RequestMethod.POST)
    public ResultVo<PageInfo<${className}Vo>> getPageInfo(@RequestBody ${className}SelectParameter parameter, @RequestParam(value = "sessionId", required = false) String sessionId) {

        PageInfo<${className}Vo> paginationVo = ${classNameLower}Service.getPageList(parameter);
        ResultVo<PageInfo<${className}Vo>> result = new ResultVo<>(paginationVo);
        return result;
    }

    private ${className}Condition getPkCondition(<#include "/include/table/primary_parameters.ftl">) {

        ${className}Condition condition = new ${className}Condition();
        <#list primaryKey as column>
        condition.set${column.columnFieldName}(${column.columnFieldNameFirstLower});
        </#list>
        return condition;
    }
    <#if (table.uniquePrimaryKey??)>

    private ${className}Condition getPkListCondition(List<${table.uniquePrimaryKey.columnFieldType}> ${table.uniquePrimaryKey.columnFieldName?uncap_first}List) {

        ${className}Condition condition = new ${className}Condition();
        condition.set${table.uniquePrimaryKey.columnFieldName}List(${table.uniquePrimaryKey.columnFieldName?uncap_first}List);
        return condition;
    }
    </#if>

}
