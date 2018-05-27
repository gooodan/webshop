package webshop.order.dao;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import webshop.order.vo.Order;
import webshop.utils.PageHibernateCallback;

import java.util.List;

/**
 * 订单模块的Dao层代码
 */
public class OrderDao extends HibernateDaoSupport {

    // DAO层保存订单的方法
    public void save(Order order) {
        this.getHibernateTemplate().save(order);
    }

    // DAO层我的订单的个数统计
    public Integer findByCountUid(Integer uid) {
        String hql = "select count(*) from Order o where o.user.uid = ?";
        List<Long> list = this.getHibernateTemplate().find(hql, uid);
        if (list != null && list.size() > 0) {
            return list.get(0).intValue();
        }
        return 0;
    }

    // DAO层的我的订单的查询
    public List<Order> findByPageUid(Integer uid, Integer begin, Integer limit) {
        String hql = "from Order o where o.user.uid = ? order by o.ordertime desc";
        // 分页查询用execute
        List<Order> list = this.getHibernateTemplate().execute(new PageHibernateCallback<Order>(hql, new Object[]{ uid }, begin, limit));
        if (list != null && list.size() > 0) {
            return list;
        }
        return null;
    }

    public Order findByOid(Integer oid) {
        return this.getHibernateTemplate().get(Order.class, oid);
    }

    public void update(Order currOrder) {
        this.getHibernateTemplate().update(currOrder);
    }
}
