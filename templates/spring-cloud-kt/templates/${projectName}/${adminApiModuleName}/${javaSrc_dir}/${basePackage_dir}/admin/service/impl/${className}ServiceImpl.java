<#include "/include/table/properties.ftl">
package ${basePackage}.admin.service.impl;

import ${baseCommonPackage}.model.OrderSearchPage;
import ${baseCommonPackage}.model.PageInfoVo;
import ${baseCommonPackage}.model.ResponseVo;
import ${baseCommonPackage}.model.ResultVo;
import ${basePackage}.admin.cloudservice.${className}CloudService;
import ${basePackage}.admin.service.${className}Service;
import ${basePackage}.admin.vm.addoredit.${className}AddOrEditVm;
import ${basePackage}.admin.vm.detail.${className}DetailVm;
import ${basePackage}.admin.vm.search.${className}SearchVm;
import ${basePackage}.models.condition.${className}Condition;
import ${basePackage}.models.condition.order.${className}OrderCondition;
import ${basePackage}.models.entity.${className}Entity;
import ${basePackage}.models.condition.extension.${className}ConditionExtension;
import ${basePackage}.models.entity.extension.${className}EntityExtension;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.stream.Collectors;

<#include "/include/java_copyright.ftl">
public class ${className}ServiceImpl implements ${className}Service {

    @Value("${r'${spring.application.name}'}")
    private String applicationName;

    @Autowired
    private ${className}CloudService ${classNameFirstLower}CloudService;

    /**
     * 添加${tableComment}
     *
     * @param vm
     * @return
     <#include "/include/author_info1.ftl">
     */
    @Override
    public ResponseVo<${className}AddOrEditVm> add(${className}AddOrEditVm vm) {

        ResponseVo<${className}AddOrEditVm> responseVo;
        ${className}Entity entity = vm.get${className}Entity();
        ResultVo<${className}Entity> apiResult = ${classNameFirstLower}CloudService.add(entity, getSessionId());
        if (apiResult.isSuccess()) {
            vm.set${className}Entity(apiResult.getResult());
            responseVo = new ResponseVo<>(vm);
        } else {
            responseVo = new ResponseVo<>(false, apiResult.getMsg());
        }

        return responseVo;
    }

    /**
     * 添加${tableComment}列表
     *
     * @param list
     * @return
     <#include "/include/author_info1.ftl">
     */
    @Override
    public ResponseVo<Integer> addList(List<${className}AddOrEditVm> list) {

        ResponseVo<Integer> responseVo;
        List<${className}Entity> entityList = list.stream().map(o -> o.get${className}Entity()).collect(Collectors.toList());
        ResultVo<Integer> apiResult = ${classNameFirstLower}CloudService.addList(entityList, getSessionId());
        if (apiResult.isSuccess()) {
            responseVo = new ResponseVo<>(apiResult.getResult());
        } else {
            responseVo = new ResponseVo<>(false, apiResult.getMsg());
        }

        return responseVo;
    }

    /**
     * 根据条件删除${tableComment}
     *
     * @param condition
     * @return
     <#include "/include/author_info1.ftl">
     */
    @Override
    public ResponseVo<Integer> delete(${className}SearchVm condition) {

         ResponseVo<Integer> responseVo;
         ResultVo<Integer> apiResult = ${classNameFirstLower}CloudService.delete(condition, getSessionId());
         if (apiResult.isSuccess()) {
             responseVo = new ResponseVo<>(apiResult.getResult());
         } else {
             responseVo = new ResponseVo<>(false, apiResult.getMsg());
         }

         return responseVo;
    }
    <#if table.validStatusColumn??>

    /**
     * 根据条件禁用${tableComment}
     *
     * @param condition
     * @return
     <#include "/include/author_info1.ftl">
     */
    @Override
    public ResponseVo<Integer> disable(${className}SearchVm condition) {

        ResponseVo<Integer> responseVo;
        ResultVo<Integer> apiResult = ${classNameFirstLower}CloudService.disable(<#include "/include/table/pk_values.ftl">, getSessionId());
        if (apiResult.isSuccess()) {
            responseVo = new ResponseVo<>(apiResult.getResult());
        } else {
            responseVo = new ResponseVo<>(false, apiResult.getMsg());
        }

        return responseVo;
    }

    /**
     * 根据条件启用${tableComment}
     *
     * @param condition
     * @return
     <#include "/include/author_info1.ftl">
     */
    @Override
    public ResponseVo<Integer> enable(${className}SearchVm condition) {

        ResponseVo<Integer> responseVo;
        ResultVo<Integer> apiResult = ${classNameFirstLower}CloudService.enable(<#include "/include/table/pk_values.ftl">, getSessionId());
        if (apiResult.isSuccess()) {
            responseVo = new ResponseVo<>(apiResult.getResult());
        } else {
            responseVo = new ResponseVo<>(false, apiResult.getMsg());
        }

        return responseVo;
    }
    </#if>

    /**
     * 根据条件获取${tableComment}实体
     *
     * @param condition
     * @return
     <#include "/include/author_info1.ftl">
     */
    @Override
    public ResponseVo<${className}DetailVm> get(${className}SearchVm condition) {

        ResponseVo<${className}DetailVm> responseVo;
        ResultVo<${className}EntityExtension> apiResult = ${classNameFirstLower}CloudService.get(condition, getSessionId());
        if (apiResult.isSuccess()) {
            responseVo = new ResponseVo<>();
            responseVo.setSuccess(true);
            ${className}EntityExtension entity;
            if ((entity = apiResult.getResult()) != null) {
                ${className}DetailVm vm = new ${className}DetailVm(entity);
                responseVo.setResult(vm);
            }
        } else {
            responseVo = new ResponseVo<>(false, apiResult.getMsg());
        }

        return responseVo;
    }
    <#if (table.hasPk)>

    /**
     * 根据主键更新${tableComment}
     *
     * @param vm
     <#if !table.hasAutoIncUniPk>
     <#list pks as column>
     <#include "/include/column/properties.ftl">
     * @param ${fieldName}
     </#list>
     </#if>
     * @return
     <#include "/include/author_info1.ftl">
     */
    @Override
    public ResponseVo<Integer> update(${className}AddOrEditVm vm<#if !table.hasAutoIncUniPk>, <#include "/include/table/pk_params.ftl"></#if>) {

        ResponseVo<Integer> responseVo;
        ResultVo<Integer> apiResult = ${classNameFirstLower}CloudService.update(vm.get${className}Entity()<#if !table.hasAutoIncUniPk>, <#include "/include/table/pk_values.ftl"></#if>, getSessionId());
        if (apiResult.isSuccess()) {
            responseVo = new ResponseVo<>(apiResult.getResult());
        } else {
            responseVo = new ResponseVo<>(false, apiResult.getMsg());
        }

        return responseVo;
    }

    /**
     * 根据主键获取${tableComment}详情
     *
     <#list pks as column>
     <#include "/include/column/properties.ftl">
     * @param ${fieldName}
     </#list>
     * @return
     <#include "/include/author_info1.ftl">
     */
    @Override
    public ResponseVo<${className}DetailVm> getDetail(<#include "/include/table/pk_params.ftl">) {

        ResponseVo<${className}DetailVm> responseVo;
        ResultVo<${className}EntityExtension> apiResult = ${classNameFirstLower}CloudService.getDetail(<#include "/include/table/pk_values.ftl">, getSessionId());
        if (apiResult.isSuccess()) {
            responseVo = new ResponseVo<>();
            responseVo.setSuccess(true);
            ${className}EntityExtension entity;
            if ((entity = apiResult.getResult()) != null) {
                ${className}DetailVm vm = new ${className}DetailVm(entity);
                responseVo.setResult(vm);
            }
        } else {
            responseVo = new ResponseVo<>(false, apiResult.getMsg());
        }

        return responseVo;
    }
    </#if>

    /**
     * 获取${tableComment}列表
     *
     * @param condition
     * @return
     <#include "/include/author_info1.ftl">
     */
    @Override
    public ResponseVo<List<${className}DetailVm>> getList(${className}SearchVm condition) {

        ResponseVo<List<${className}DetailVm>> responseVo;
        ${className}ConditionExtension parameter = searchVm.get${className}ConditionExtension();
        ResultVo<List<${className}EntityExtension>> apiResult = ${classNameFirstLower}CloudService.getList(condition, getSessionId());
        if (apiResult.isSuccess()) {
            List<${className}DetailVm> list = apiResult.getResult();
            responseVo = new ResponseVo<>(list);
        } else {
            responseVo = new ResponseVo<>(false, apiResult.getMsg());
        }

        return responseVo;
    }

    /**
     * 分页查询${tableComment}
     *
     * @param searchPage
     * @return
     <#include "/include/author_info1.ftl">
     */
    @Override
    public ResponseVo<PageInfoVo<${className}DetailVm>> getPageInfo(SearchPage<${className}SearchVm> searchPage) {

        ResponseVo<PageInfoVo<${className}DetailVm>> responseVo;
        ${className}ConditionExtension parameter = searchVm.get${className}ConditionExtension();
        ResultVo<PageInfoVo<${className}EntityExtension>> apiResult = ${classNameFirstLower}CloudService.getPageInfo(parameter, getSessionId());
        if (apiResult.isSuccess()) {
            PageInfoVo<${className}EntityExtension> pageInfo = apiResult.getResult();
            responseVo = new ResponseVo<>(pageInfo);
        } else {
            responseVo = new ResponseVo<>(false, apiResult.getMsg());
        }

        return responseVo;
    }

    private String getSessionId() {
        return applicationName + "-" + UUID.randomUUID();
    }
}
