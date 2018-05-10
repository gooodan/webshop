package webshop.cart.action;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import webshop.cart.vo.Cart;
import webshop.cart.vo.CartItem;
import webshop.product.service.ProductService;
import webshop.product.vo.Product;

public class CartAction extends ActionSupport {
    // 接收pid
    private Integer pid;
    // 接收数量count
    private Integer count;
    // 注入商品的Service
    private ProductService productService;

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    // 将购物项添加到购物车
    public String addCart() {
        // 封装一个CartItem对象
        CartItem cartItem = new CartItem();
        // 设置数量
        cartItem.setCount(count);
        // 根据pid进行商品查询
        Product product = productService.findByPid(pid);
        // 设置商品
        cartItem.setProduct(product);
        // 将购物项添加到购物车
        // 购物车应该存在session
        Cart cart = getCart();
        cart.addCart(cartItem);
        return "addCart";
    }

    // 我的购物车跳转方法
    public String myCart() {
        return "myCart";
    }

    /*
     *   获得购物车的方法: 从session中获得购物车
     */
    private Cart getCart() {
        Cart cart = (Cart) ServletActionContext.getRequest().getSession().getAttribute("cart");
        // 如果session中不存在购物车对象,则实例化一个放入session中
        if (cart == null) {
            cart = new Cart();
            ServletActionContext.getRequest().getSession().setAttribute("cart", cart);
        }
        return cart;
    }

    // 清空购物车的执行方法
    public String clearCart() {
        // 获得购物车的对象
        Cart cart = getCart();
        cart.clearCart();

        return "clearCart";
    }

    // 移除购物项的执行方法
    public String removeCart() {
        // 获得购物车的对象
        Cart cart = getCart();
        // 调用购物车中移除的方法
        cart.removeCart(pid);
        // 返回界面
        return "removeCart";
    }


}
