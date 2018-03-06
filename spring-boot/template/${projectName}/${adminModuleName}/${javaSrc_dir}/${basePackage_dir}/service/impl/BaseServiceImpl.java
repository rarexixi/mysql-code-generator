package ${basePackage}.service.impl;

import ${baseCommonPackage}.model.SearchPage;
import ${basePackage}.condition.order.OrderCondition;
import ${basePackage}.service.BaseService;
import ${basePackage}.mapper.BaseMapper;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

@Transactional
public class BaseServiceImpl<T extends Serializable, C extends Serializable> implements BaseService<T, C> {

    @Autowired
    protected BaseMapper<T, C> mapper;

    /**
     * 添加
     *
     * @param model
     * @return
     */
    @Override
    public int insert(T model) {
        return mapper.insert(model);
    }

    /**
     * 添加
     *
     * @param models
     * @return
     */
    @Override
    public int insertList(List<T> models) {
        return mapper.insertList(models);
    }

    /**
     * 根据条件删除
     *
     * @param condition
     * @return
     */
    @Override
    public int deleteByCondition(C condition) {
        return mapper.deleteByCondition(condition);
    }

    /**
     * 根据条件删除
     *
     * @param conditionList
     * @return
     */
    @Override
    public int deleteByConditionList(List<C> conditionList) {
        return mapper.deleteByConditionList(conditionList);
    }

    /**
     * 根据条件更新
     *
     * @param model
     * @param condition
     * @return
     */
    @Override
    public int updateByCondition(T model, C condition) {
        return mapper.updateByCondition(model, condition);
    }

    /**
     * 根据条件更新
     *
     * @param model
     * @param conditionList
     * @return
     */
    @Override
    public int updateByConditionList(T model, List<C> conditionList) {
        return mapper.updateByConditionList(model, conditionList);
    }

    /**
     * 查询
     *
     * @param condition
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public T getByCondition(C condition) {
        return mapper.getByCondition(condition);
    }

    /**
     * 查询
     *
     * @param condition
     * @param order
     * @param page
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public PageInfo<T> findByCondition(C condition, OrderCondition order, SearchPage page) {

        if (page == null) {
            PageHelper.startPage(SearchPage.DEFAULT_PAGE_INDEX, SearchPage.DEFAULT_PAGE_SIZE);
        } else {
            PageHelper.startPage(page.getPageIndex(), page.getPageSize());
        }
        List<T> list = mapper.findByCondition(condition, order);
        PageInfo<T> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 查询
     *
     * @param conditionList
     * @param order
     * @param page
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public PageInfo<T> findByConditionList(List<C> conditionList, OrderCondition order, SearchPage page) {

        if (page == null) {
            PageHelper.startPage(SearchPage.DEFAULT_PAGE_INDEX, SearchPage.DEFAULT_PAGE_SIZE);
        } else {
            PageHelper.startPage(page.getPageIndex(), page.getPageSize());
        }
        List<T> list = mapper.findByConditionList(conditionList, order);
        PageInfo<T> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

}
