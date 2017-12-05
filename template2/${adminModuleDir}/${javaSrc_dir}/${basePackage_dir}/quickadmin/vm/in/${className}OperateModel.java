<#assign className = table.tableClassName>
<#assign classNameLower = table.tableClassNameFirstLower>
package ${basePackage}.quickadmin.vm.in;

import ${basePackage}.entity.${className}Entity;

import java.math.BigDecimal;
import java.util.Date;
import java.io.Serializable;

public class ${className}OperateModel implements Serializable {

    <#list table.columns as column>
    /**
     * ${column.columnComment}
     */
    private ${column.columnFieldType} ${column.columnFieldNameFirstLower};

    </#list>

    <#list table.columns as column>
    /**
    * 设置${column.columnComment}
    */
    public void set${column.columnFieldName}(${column.columnFieldType} ${column.columnFieldNameFirstLower}) {
        this.${column.columnFieldNameFirstLower} = ${column.columnFieldNameFirstLower};
    }

    /**
    * 获取${column.columnComment}
    */
    public ${column.columnFieldType} get${column.columnFieldName}() {
        return ${column.columnFieldNameFirstLower};
    }

    </#list>
    /**
     * 获取数据表实体
     */
    public ${className}Entity get${className}Entity() {
        ${className}Entity entity = new ${className}Entity();
        <#list table.columns as column>
        entity.set${column.columnFieldName}(${column.columnFieldNameFirstLower});
        </#list>
        return entity;
    }

}