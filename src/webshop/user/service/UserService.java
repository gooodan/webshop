package webshop.user.service;

import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;
import webshop.user.dao.UserDao;
import webshop.user.vo.User;
import webshop.utils.MailUtils;
import webshop.utils.UUIDUtils;

/**
 * 用户模块业务层代码
 *
 */
@Transactional
public class UserService {
    // 注入UserDao
    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    // 按用户名查询用户的方法
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    // 按激活码查询用户的方法
    public User findByCode(String code) {
        return userDao.findByCode(code);
    }


    // 业务层完成用户注册的代码
    public void save(User user) {
        // 将数据存入到数据库
        String code = UUIDUtils.getUUID()+UUIDUtils.getUUID();
        user.setCode(code);
        user.setState(0); // 0代表用户未激活  1代表用户已激活
        // 发送激活邮件
        MailUtils.sendMail(user.getEmail(), code);
        userDao.save(user);
    }

    // 修改用户的状态的方法
    public void update(User existUser) {
        userDao.update(existUser);
    }

    // 用户登入的方法
    public User login(User user) {
        return userDao.login(user);
    }
}
