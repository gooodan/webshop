package webshop.index.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import webshop.category.service.CategoryService;
import webshop.category.vo.Category;
import webshop.product.service.ProductService;
import webshop.product.vo.Product;

import java.util.List;

/**
 * 首页访问的Action
 * @author Medivh
 */
public class IndexAction extends ActionSupport {
    // 注入一级分类的Service
    private CategoryService categoryService;
    // 注入商品的Service
    private ProductService productService;

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * 执行访问首页的方法
     * @return
     */
    public String execute() {
        // 查询一级分类的集合
        List<Category> cList = categoryService.findAll();
        // 将一级分类存入到Session的范围
        ActionContext.getContext().getSession().put("cList", cList);
        // 查询热门商品:
        List<Product> hList = productService.findHot();
        // 保存到值栈中:
        ActionContext.getContext().getValueStack().set("hList", hList);
        // 查询最新商品
        List<Product> nList = productService.findNew();
        // 保存到值栈中
        ActionContext.getContext().getValueStack().set("nList", nList);

        return "index";
    }

}
