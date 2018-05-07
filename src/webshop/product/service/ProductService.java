package webshop.product.service;

import org.springframework.transaction.annotation.Transactional;
import webshop.product.dao.ProductDao;
import webshop.product.vo.Product;
import webshop.utils.PageBean;

import java.util.List;

/**
 * 业务层代码
 */
@Transactional
public class ProductService {
    // 注入ProductDao
    private ProductDao productDao;

    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }

    // 首页热门商品查询
    public List<Product> findHot() {
        return productDao.findHot();
    }

    // 首页查询最新商品
    public List<Product> findNew() {
        return productDao.findNew();
    }

    public Product findByPid(Integer pid) {
        return productDao.findByPid(pid);
    }

    // 根据一级分类的cid带有分页查询商品
    public PageBean<Product> findByPageCid(Integer cid, int page) {
        PageBean<Product> pageBean = new PageBean<>();
        // 设置当前页数
        pageBean.setPage(page);
        // 设置每页显示记录数
        int limit = 8;
        pageBean.setLimit(limit);
        // 设置总的记录数:
        int totalCount = 0;
        totalCount = productDao.findCountCid(cid);
        pageBean.setTotalCount(totalCount);
        // 设置总页数
        int totalPage = 0;
        totalPage = (int) Math.ceil(totalCount / limit);
//        if (totalPage % limit == 0) {
//            totalPage = totalCount / limit;
//        } else {
//            totalPage = totalCount / limit + 1;
//        }
        pageBean.setTotalPage(totalPage);
        // 每页显示的数据集合
        int begin = (page - 1) * limit;
        List<Product> list = productDao.findByPageCid(cid, begin, limit);
        pageBean.setList(list);
        return pageBean;
    }
}
