package webshop.user.dao;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import webshop.user.vo.User;

import java.util.List;

/**
 * 用户模块持久层代码
 */
public class UserDao extends HibernateDaoSupport {

    // 按名称查询是否有该用户
    public User findByUsername(String username) {
        String hql = "from User where username = ?";
        List<User> list = this.getHibernateTemplate().find(hql, username);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }

        return null;
    }

    // 按code查询是否有该用户
    public User findByCode(String code) {
        String hql = "from User where code = ?";
        List<User> list = this.getHibernateTemplate().find(hql, code);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    // 修改用户状态的方法
    public void update(User existUser) {
        this.getHibernateTemplate().update(existUser);
    }

    // 注册用户存入数据库代码实现
    public void save(User user) {
        this.getHibernateTemplate().save(user);
    }

    // DAO层用户登入的方法
    public User login(User user) {
        String hql = "from User where username = ? and password = ? and state = ?";
        List<User> list = this.getHibernateTemplate().find(hql, user.getUsername(), user.getPassword(), 1);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

}
