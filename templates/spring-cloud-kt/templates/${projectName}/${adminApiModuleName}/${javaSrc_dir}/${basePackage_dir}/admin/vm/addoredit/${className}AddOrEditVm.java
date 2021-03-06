<#include "/include/table/properties.ftl">
package ${basePackage}.admin.vm.addoredit;

import ${baseCommonPackage}.validation.*;
import ${basePackage}.models.entity.${className}Entity;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

<#include "/include/java_copyright.ftl">
public class ${className}AddOrEditVm implements Serializable {
    <#list table.requiredColumns as column>
    <#include "/include/column/properties.ftl">

    /**
     * ${columnFullComment}
     */
    <#if (column.pk)>
    <#assign annotationName = ((fieldType == 'String') ? string('NotBlank', 'NotNull'))>
    @${annotationName}(groups = {<#if column.autoIncrement>DataEdit.class<#else>DataAdd.class</#if>}, message = "${fieldName} (${columnComment})不能为空")
    <#elseif (!column.notRequired && !column.nullable && (column.columnDefault!"") == "")>
    @${annotationName}(groups = {DataAdd.class, DataEdit.class}, message = "${fieldName} (${columnComment})不能为空")
    </#if>
    private ${fieldType} ${fieldName};
    </#list>
    <#list table.requiredColumns as column>
    <#include "/include/column/properties.ftl">

    /**
     * 获取${columnComment}
     */
    public ${fieldType} get${propertyName}() {
        return ${fieldName};
    }

    /**
     * 设置${columnComment}
     */
    public void set${propertyName}(${fieldType} ${fieldName}) {
        this.${fieldName} = ${fieldName};
    }
    </#list>

    public ${className}Entity get${className}Entity() {

        ${className}Entity entity = new ${className}Entity();
        <#list table.requiredColumns as column>
        <#include "/include/column/properties.ftl">
        entity.set${propertyName}(${fieldName});
        </#list>

        return entity;
    }

    public void set${className}Entity(${className}Entity entity) {

        <#list table.requiredColumns as column>
        <#include "/include/column/properties.ftl">
        this.set${propertyName}(entity.get${propertyName}());
        </#list>
    }
}
