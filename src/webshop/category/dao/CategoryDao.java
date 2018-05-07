package webshop.category.dao;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import webshop.category.vo.Category;

import java.util.List;

/**
 * 一级分类持久层对象
 */
public class CategoryDao extends HibernateDaoSupport {

    // 持久层查询所有一级分类的方法
    public List<Category> findAll() {
        String hql = "from Category";
        List<Category> list = this.getHibernateTemplate().find(hql);
        if (list != null && list.size() > 0) {
            return list;
        }
        return null;
    }
}
