package webshop.order.vo;

import webshop.user.vo.User;

import java.util.HashSet;
import java.util.Set;

/**
 * 订单实体类
 */
public class Order {
    private Integer oid;
    private Double total;
    private String ordertime;
    private Integer state;
    private String name;
    private String addr;
    private String phone;
    // 订单所属用户:
    private User user;
    // 订单中所属的多个订单项
    private Set<OrderItem> orderItems = new HashSet<>();

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getOid() {
        return oid;
    }

    public void setOid(Integer oid) {
        this.oid = oid;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(String ordertime) {
        this.ordertime = ordertime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public User getUser() {
        return user;
    }

    public Set<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
