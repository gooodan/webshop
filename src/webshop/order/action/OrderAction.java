package webshop.order.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.struts2.ServletActionContext;
import webshop.cart.vo.Cart;
import webshop.cart.vo.CartItem;
import webshop.order.service.OrderService;
import webshop.order.vo.Order;
import webshop.order.vo.OrderItem;
import webshop.user.vo.User;
import webshop.utils.PageBean;
import webshop.utils.PaymentUtil;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderAction extends ActionSupport implements ModelDriven<Order> {
    // 模型驱动使用的对象
    private Order order = new Order();
    // 注入OrderService
    private OrderService orderService;
    // 接收page参数
    private Integer page;
    // 接收支付通道编码
    private String pd_FrpId;

    public void setPd_FrpId(String pd_FrpId) {
        this.pd_FrpId = pd_FrpId;
    }

    // 接收付款成功后的参数:
    private String r3_Amt;   // 付款金额
    private String r6_Order; //  付款的订单编号

    public void setR3_Amt(String r3_Amt) {
        this.r3_Amt = r3_Amt;
    }

    public void setR6_Order(String r6_Order) {
        this.r6_Order = r6_Order;
    }

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    @Override
    public Order getModel() {
        return order;
    }


    // 生成订单的方法:
    public String save() {
        // 1.保存数据到数据库
        // 订单数据补全:
        //设置日期格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 按格式修改当前时间
        String dateTime = df.format(new Date());
        // 设置格式化的时间进订单
        order.setOrdertime(dateTime);
        order.setState(1); // 1:未付款  2:已经付款,但是没有发货   3: 已经发货,但是没有确定收货  4:交易完成
        // 总计是购物车中的信息
        Cart cart = (Cart) ServletActionContext.getRequest().getSession().getAttribute("cart");
        if (cart == null) {
            this.addActionError("亲!您的购物车为空,请先添加商品进入购物车");
            return "msg";
        }
        order.setTotal(cart.getTotal());
        // 设置订单中的订单项
        for (CartItem cartItem : cart.getCartItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setCount(cartItem.getCount());
            orderItem.setSubtotal(cartItem.getSubtotal());
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setOrder(order);

            order.getOrderItems().add(orderItem);
        }
        // 设置订单所属的用户
        User existUser = (User) ServletActionContext.getRequest().getSession().getAttribute("existUser");
        if (existUser == null) {
            this.addActionError("亲, 您还没有登录哦! 请您先登录");
            return "login";
        }
        order.setUser(existUser);
        orderService.save(order);
        // 清空购物车
        cart.clearCart();
        // 2.将订单对象显示到页面上
        // 通过值栈的方式:因为Order
        return "saveSuccess";
    }

    public String findByUid() {
        // 根据用户的id进行查询
        User user = (User) ServletActionContext.getRequest().getSession().getAttribute("existUser");
        // 调用Service:
        PageBean<Order> pageBean = orderService.findByPageUid(user.getUid(), page);
        // 将分页的数据显示到页面上:
        ActionContext.getContext().getValueStack().set("pageBean", pageBean);
        return "findByUid";
    }

    // 根据订单的id查询订单的方法
    public String findByOid() {
        // 模型驱动直接接收到order
        order = orderService.findByOid(order.getOid());
        return "findByOid";
    }

    // 为订单付款的方法
    public String payOrder() throws IOException {
        // 设置数据进数据库
        Order currOrder = orderService.findByOid(order.getOid());
        currOrder.setAddr(order.getAddr());
        currOrder.setName(order.getName());
        currOrder.setPhone(order.getPhone());
        // 更新订单数据库数据
        orderService.update(currOrder);

        // 为订单付款
        String p0_Cmd = "Buy";     // 业务类型
        String p1_MerId = "10001126856";      // 商户的编号
        String p2_Order = order.getOid().toString();
        String p3_Amt = "0.01"; // 付款金额:
        String p4_Cur = "CNY"; // 交易币种:
        String p5_Pid = ""; // 商品名称:
        String p6_Pcat = ""; // 商品种类:
        String p7_Pdesc = ""; // 商品描述:
        String p8_Url = "http://192.168.0.106:8080/webshop/order_callBack.action"; // 商户接收支付成功数据的地址:
        String p9_SAF = ""; // 送货地址:
        String pa_MP = ""; // 商户扩展信息:
        String pd_FrpId = this.pd_FrpId;// 支付通道编码:
        String pr_NeedResponse = "1"; // 应答机制:
        String keyValue = "69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl"; // 秘钥
        String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt,
                p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP,
                pd_FrpId, pr_NeedResponse, keyValue); // hmac
        // 向易宝发送请求:
        StringBuffer sb = new StringBuffer("https://www.yeepay.com/app-merchant-proxy/node?");
        sb.append("p0_Cmd=").append(p0_Cmd).append("&");
        sb.append("p1_MerId=").append(p1_MerId).append("&");
        sb.append("p2_Order=").append(p2_Order).append("&");
        sb.append("p3_Amt=").append(p3_Amt).append("&");
        sb.append("p4_Cur=").append(p4_Cur).append("&");
        sb.append("p5_Pid=").append(p5_Pid).append("&");
        sb.append("p6_Pcat=").append(p6_Pcat).append("&");
        sb.append("p7_Pdesc=").append(p7_Pdesc).append("&");
        sb.append("p8_Url=").append(p8_Url).append("&");
        sb.append("p9_SAF=").append(p9_SAF).append("&");
        sb.append("pa_MP=").append(pa_MP).append("&");
        sb.append("pd_FrpId=").append(pd_FrpId).append("&");
        sb.append("pr_NeedResponse=").append(pr_NeedResponse).append("&");
        sb.append("hmac=").append(hmac);

        // 重定向:向易宝出发:
        ServletActionContext.getResponse().sendRedirect(sb.toString());

        return NONE;
    }

    // 付款成功后跳转回来的路径:
    public String callBack() {
        // 修改订单的状态:
        Order currOrder = orderService.findByOid(Integer.parseInt(r6_Order));
        // 修改订单状态为2:已经付款:
        currOrder.setState(2);
        orderService.update(currOrder);
        this.addActionMessage("支付成功!订单编号为: " + r6_Order + " 付款金额为: " + r3_Amt);
        return "msg";
    }

}
