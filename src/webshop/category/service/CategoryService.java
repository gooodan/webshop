package webshop.category.service;

import org.springframework.transaction.annotation.Transactional;
import webshop.category.dao.CategoryDao;
import webshop.category.vo.Category;

import java.util.List;

/**
 * 一级分类业务层对象
 */
@Transactional
public class CategoryService {
    // 注入CategoryDao
    private CategoryDao categoryDao;

    public void setCategoryDao(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    // 业务层查询所有一级分类的方法
    public List<Category> findAll() {
        return categoryDao.findAll();
    }
}
