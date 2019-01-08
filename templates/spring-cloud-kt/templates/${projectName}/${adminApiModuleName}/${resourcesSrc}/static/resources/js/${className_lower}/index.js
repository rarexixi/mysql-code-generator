<#include "/include/table/properties.ftl">
<#macro mapperEl value>${r"${"}${value}}</#macro>
var app = new Vue({
    el: '#app',
    data: {
        <#if (table.validStatusColumn??)>
        ${table.validStatusColumn.targetName?uncap_first}SelectList: [{
            value: ${table.validStatusColumn.validStatusOption.valid}, text: '有效'
        }, {
            value: ${table.validStatusColumn.validStatusOption.invalid}, text: '无效'
        }],
        </#if>
        <#list table.selectColumns as column>
        <#include "/include/column/properties.ftl">
        ${fieldName?replace('Id', '')?replace('Key', '')?replace('Code', '')}SelectList: [<#list column.selectOptions as option>{
            value: ${option.value}, text: '${option.text}'
        }<#if option_has_next>,</#if></#list>],
        </#list>
        <#list table.fkSelectColumns as column>
        <#include "/include/column/properties.ftl">
        ${fieldName?replace('Id', '')?replace('Key', '')?replace('Code', '')}SelectList: [],
        </#list>
        <#if !table.hasAutoIncUniPk>
        <#list pks as column>
        <#include "/include/column/properties.ftl">
        ${fieldName}: '',
        </#list>
        </#if>
        searchParams: {
            <#list table.columns as column>
            <#include "/include/column/properties.ftl">
            <#if column.ignoreSearch>
            <#elseif (column.validStatus)>
            ${fieldName}: ${table.validStatusColumn.validStatusOption.valid},
            <#elseif (column.select || column.fkSelect || column.pk || isInteger)>
            ${fieldName}: '',
            <#elseif (isDecimal)>
            ${fieldName}Min: '',
            ${fieldName}Max: '',
            <#elseif (isString)>
            ${fieldName}StartWith: '',
            <#elseif (isDate || isTime || isDateTime)>
            ${fieldName}Range: [],
            <#elseif (isContent)>
            <#else>
            </#if>
            </#list>

            sortEnums: [2],
            pageIndex: 1,
            pageSize: 10
        },
        multipleSelection: [],
        pageInfo: {},
        addOrEditParams: {
            <#list table.requiredColumns as column>
            <#include "/include/column/properties.ftl">
            <#if (column.validStatus)>
            <#-- ${fieldName}: ${table.validStatusColumn.validStatusOption.valid}<#if column_has_next>,</#if> -->
            <#else>
            ${fieldName}: ''<#if column_has_next>,</#if>
            </#if>
            </#list>
        },
        detail: {
            <#list table.columns as column>
            <#include "/include/column/properties.ftl">
            <#if (column.validStatus)>
            ${fieldName}: ${table.validStatusColumn.validStatusOption.valid}<#if column_has_next>,</#if>
            <#elseif (column.fkSelect || column.select)>
            ${fieldName}: <#if (column.dataType?ends_with("char"))>''<#else>0</#if><#if column_has_next>,</#if>
            <#else>
            ${fieldName}: ''<#if column_has_next>,</#if>
            </#if>
            </#list>
        },
        addOrEditTitle: '',
        detailDialogVisible: false,
        addOrEditDialogVisible: false
    },
    mounted: function () {
        var self = this;
        self.search();
        <#list table.fkSelectColumns as column>
        <#include "/include/column/properties.ftl">
        self.init${propertyName?replace('Id', '')?replace('Key', '')?replace('Code', '')}();
        </#list>
    },
    methods: {
        <#list table.fkSelectColumns as column>
        <#include "/include/column/properties.ftl">
        init${propertyName?replace('Id', '')?replace('Key', '')?replace('Code', '')}: function () {
            var self = this;
            var url = appConfig.baseApiPath + '/${column.fkSelectColumn.foreignClassName?lower_case}/list';
            var params = {};
            self.ajaxPost(url, params, '获取${columnComment}列表失败！', function(response) {
                self.${fieldName}SelectList = response.result;
            });
        },
        </#list>
        changePage: function(pageIndex) {
            if (this.searchParams.pageIndex == pageIndex) {
                return;
            }
            this.searchParams.pageIndex = pageIndex;
            this.search();
        },
        changePageSize: function(pageSize) {
            if (this.searchParams.pageSize == pageSize) {
                return;
            }
            this.searchParams.pageSize = pageSize;
            this.searchParams.pageIndex = 1;
            this.search();
        },
        search: function () {
            var self = this;
            self.checkedList = [];

            var url = appConfig.baseApiPath + '/${classNameFirstLower}/search';
            self.ajaxPost(url, self.searchParams, '获取${tableComment}列表失败！', function(response) {
                self.pageInfo = response.result;
            });
        },
        <#if table.validStatusColumn??>
        changeValidSearch: function(valid) {
            var self = this;
            if (self.searchParams.${table.validStatusColumn.targetName?uncap_first} === valid) {
                return;
            }
            self.resetSearch();
            self.searchParams.${table.validStatusColumn.targetName?uncap_first} = valid;
            self.search();
        },
        </#if>
        resetSearch: function() {
            <#list table.columns as column>
            <#include "/include/column/properties.ftl">
            <#if column.ignoreSearch>
            <#elseif (column.validStatus)>
            this.searchParams.${fieldName} = ${table.validStatusColumn.validStatusOption.valid};
            <#elseif (column.select || column.fkSelect || column.pk || isInteger)>
            this.searchParams.${fieldName} = '';
            <#elseif (isDecimal)>
            this.searchParams.${fieldName}Min = '';
            this.searchParams.${fieldName}Max = '';
            <#elseif (isString)>
            this.searchParams.${fieldName}StartWith = '';
            <#elseif (isDate || isTime || isDateTime)>
            this.searchParams.${fieldName}Range = [];
            <#elseif (isContent)>
            <#else>
            </#if>
            </#list>

            this.searchParams.pageIndex = 1;
            this.searchParams.pageSize = 10;
        },
        add: function() {
            var self = this;
            self.resetSave();
            self.addOrEditDialogVisible = true;
            self.addOrEditTitle = '添加${tableComment}';
        },
        edit: function (<#include "/include/table/pk_values.ftl">) {
            var self = this;
            self.resetSave();
            self.addOrEditDialogVisible = true;
            self.addOrEditTitle = '编辑${tableComment}';
            <#if !table.hasAutoIncUniPk>
            <#list pks as column>
            <#include "/include/column/properties.ftl">
            self.${fieldName} = ${fieldName};
            </#list>
            </#if>

            var url = appConfig.baseApiPath + '/${classNameFirstLower}/getDetail';
            var params = {
                <#list pks as column>
                <#include "/include/column/properties.ftl">
                ${fieldName}: ${fieldName}<#if (column_has_next)>,</#if>
                </#list>
            };
            self.ajaxGet(url, params, '获取${tableComment}详情失败！', function(response) {
                <#list table.columns as column>
                <#include "/include/column/properties.ftl">
                <#if column.notRequired>
                <#else>
                self.addOrEditParams.${fieldName} = response.result.${fieldName};
                </#if>
                </#list>
            });
        },
        save: function () {
            var self = this;
            var ajaxUrl;
            <#if table.hasAutoIncUniPk>
            if (<#list pks as column><#include "/include/column/properties.ftl"><#if (column_index > 0)> && </#if>self.addOrEditParams.${fieldName} == ''</#list>) {
                ajaxUrl = appConfig.baseApiPath + '/${classNameFirstLower}/add';
            } else {
                ajaxUrl = appConfig.baseApiPath + '/${classNameFirstLower}/edit';
            }
            <#else>
            if (<#list pks as column><#include "/include/column/properties.ftl"><#if (column_index > 0)> && </#if>self.${fieldName} == ''</#list>) {
                ajaxUrl = appConfig.baseApiPath + '/${classNameFirstLower}/add';
            } else {
                ajaxUrl = appConfig.baseApiPath + '/${classNameFirstLower}/edit?'<#list pks as column><#if (column_index > 0)> + '&'</#if> + '${fieldName}=' + self.${fieldName}</#list>;
            }
            </#if>

            self.ajaxPost(ajaxUrl, self.addOrEditParams, '操作失败！', function(response) {
                self.$notify({
                    message: '操作成功！',
                    type: 'success'
                });
                self.resetSave();
                setTimeout(self.search, 1000);
            });
        },
        resetSave: function() {
            var self = this;
            <#list table.requiredColumns as column>
            <#include "/include/column/properties.ftl">
            <#if (column.validStatus)>
            <#-- self.addOrEditParams.${fieldName} = ${table.validStatusColumn.validStatusOption.valid}; -->
            <#else>
            self.addOrEditParams.${fieldName} = '';
            </#if>
            </#list>
            self.closeDialog();
        },
        get: function (<#include "/include/table/pk_values.ftl">) {
            var self = this;
            self.detailDialogVisible = true;

            var url = appConfig.baseApiPath + '/${classNameFirstLower}/getDetail';
            var params = {
                <#list pks as column>
                ${fieldName}: ${fieldName}<#if (column_has_next)>,</#if>
                </#list>
            };
            self.ajaxGet(url, params, '获取详情失败！', function(response) {
                self.detail = response.result;
            });
        },
        closeDialog: function () {
            var self = this;
            self.detailDialogVisible = false;
            self.addOrEditDialogVisible = false;
        },
        <#if (table.hasUniPk)>
        handleSelectionChange: function(val) {
            this.multipleSelection = val;
        },
        <#if table.validStatusColumn??>
        enableSelected: function () {
            this.execSelected("确定启用吗？", appConfig.baseApiPath + '/${classNameFirstLower}/enableList', "启用成功！", "启用失败！");
        },
        disableSelected: function () {
            this.execSelected("确定禁用吗？", appConfig.baseApiPath + '/${classNameFirstLower}/disableList', "禁用成功！", "禁用失败！");
        },
        </#if>
        delSelected: function () {
            this.execSelected("确定删除吗？", appConfig.baseApiPath + '/${classNameFirstLower}/deleteList', "删除成功！", "删除失败！");
        },
        execSelected: function (confirmMsg, url, successMsg, failMsg) {
            var self = this;
            var checkedList = [];
            for (var i = 0; i < self.multipleSelection.length; i++) {
                var item = self.multipleSelection[i];
                checkedList.push(item.${table.uniPk.targetName?uncap_first});
            }
            self.$confirm(confirmMsg, '', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(function () {
                self.ajaxPost(url, checkedList, failMsg, function(response) {
                    self.$notify({
                        type: 'success',
                        message: successMsg
                    });
                    self.search();
                });
            });
        },
        </#if>
        <#if table.validStatusColumn??>
        enable: function (<#include "/include/table/pk_values.ftl">) {
            var self = this;
            var params = self.getPkParams(<#include "/include/table/pk_values.ftl">);
            var url = appConfig.baseApiPath + '/${classNameFirstLower}/enable';
            this.exec("确定启用吗？", url, params, "启用成功！", "启用失败！");
        },
        disable: function (<#include "/include/table/pk_values.ftl">) {
            var self = this;
            var params = self.getPkParams(<#include "/include/table/pk_values.ftl">);
            var url = appConfig.baseApiPath + '/${classNameFirstLower}/disable';
            this.exec("确定禁用吗？", url, params, "禁用成功！", "禁用失败！");
        },
        </#if>
        del: function (<#include "/include/table/pk_values.ftl">) {
            var self = this;
            var params = self.getPkParams(<#include "/include/table/pk_values.ftl">);
            var url = appConfig.baseApiPath + '/${classNameFirstLower}/delete';
            this.exec("确定删除吗？", url, params, "删除成功！", "删除失败！");
        },
        getPkParams: function (<#include "/include/table/pk_values.ftl">) {
            var params = {
                <#list pks as column>
                <#include "/include/column/properties.ftl">
                ${fieldName}: ${fieldName}<#if (column_has_next)>,</#if>
                </#list>
            }
            return params;
        }
        exec: function (confirmMsg, url, params, successMsg, failMsg) {
            var self = this;
            self.$confirm(confirmMsg, '', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(function () {
                self.ajaxGet(url, params, failMsg, function(response) {
                    self.$notify({
                        type: 'success',
                        message: successMsg
                    });
                    self.search();
                });
            });
        },
        <#if (table.validStatusColumn??)>
        get${table.validStatusColumn.targetName}Text: function (value) {
            var self = this;
            var entity = self.${table.validStatusColumn.targetName?uncap_first}SelectList.find(function (item) {
                return item.value == value;
            });
            return entity ? entity.text : '';
        },
        </#if>
        <#list table.selectColumns as column>
        <#include "/include/column/properties.ftl">
        get${propertyName}Text: function (value) {
            var self = this;
            var entity = self.${fieldName}SelectList.find(function (item) {
                return item.value == value;
            });
            return entity ? entity.text : '';
        },
        </#list>
        <#list table.fkSelectColumns as column>
        <#include "/include/column/properties.ftl">
        get${propertyName}Text: function (${column.fkSelectColumn.valueName?uncap_first}) {
            var self = this;
            var entity = self.${fieldName}SelectList.find(function (item) {
                return item.${column.fkSelectColumn.valueName?uncap_first} == ${column.fkSelectColumn.valueName?uncap_first};
            });
            return entity ? entity.${column.fkSelectColumn.textName?uncap_first} : '';
        },
        </#list>
        exportExcel: function() {
            var self = this;
            var params = JSON.stringify(self.searchParams, function (key, value) {
                if (value) return value;
                return undefined;
            });
            window.open(appConfig.baseApiPath + '/${classNameFirstLower}/export?params=' + encodeURIComponent(params));
        }
    }
});