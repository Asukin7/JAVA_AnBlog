package com.an.blog.service.impl;

import com.an.blog.bean.Role;
import com.an.blog.bean.User;
import com.an.blog.dao.UserDao;
import com.an.blog.dao.UserRoleDao;
import com.an.blog.service.MailService;
import com.an.blog.service.UserService;
import com.an.blog.util.MD5Util;
import com.an.blog.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private UserRoleDao userRoleDao;
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;
    @Autowired
    private MailService mailService;

    /**
     * 用户登录
     * @param user
     * @return 成功返回用户信息，失败返回null
     */
    @Override
    public User login(User user) {
        // 用户密码使用MD5加密
        user.setPassword(MD5Util.getMD5(user.getPassword()));
        return userDao.login(user.getUsername(), user.getPassword());
    }

    @Override
    public boolean registerStep1(User user) {
        // 随机生成六位数的验证码
        Integer verificationCode = (int) ((Math.random() * 9 + 1) * 100000);
        user.setEnabled(verificationCode);
        // 将用户数据存入redis，有效期10分钟
        redisTemplate.opsForValue().set(user.getEmail(), user, 10, TimeUnit.MINUTES);
        // 发送邮件
        mailService.sendSimpleMail(user.getEmail(), "安安博客", "您的验证码为：" + verificationCode + "，验证码有效期为十分钟，请尽快使用。");
        return true;
    }

    @Override
    public boolean registerStep2(User user) {
        // 从redis取出用户数据
        User userRedis = (User) redisTemplate.opsForValue().get(user.getEmail());
        // 检查是否存在用户数据
        if (userRedis == null) return false;
        // 对比账号密码验证码
        if (!user.getUsername().equals(userRedis.getUsername())) return false;
        if (!user.getPassword().equals(userRedis.getPassword())) return false;
        if (!user.getEnabled().equals(userRedis.getEnabled())) return false;
        // 用户有效
        user.setEnabled(1);
        // 密码使用MD5加密
        user.setPassword(MD5Util.getMD5(user.getPassword()));
        // 设置昵称
        user.setNickname(user.getUsername());
        // 设置头像
        user.setProfilePhoto("https://nnsststt.cn/images/aisakaTaiga.jpg");
        // 设置赞赏码
        user.setAppreciationCode("https://nnsststt.cn/images/appreciationCode.jpg");

        // 插入用户数据
        Integer insertUserNumber = userDao.insert(user);
        if (insertUserNumber == null || insertUserNumber == 0) return false;
        // 插入用户角色数据
        Integer insertUserRoleNumber = userRoleDao.insert(user.getId(), 2);
        if (insertUserRoleNumber == null || insertUserRoleNumber == 0) return false;

        return true;
    }

    @Override
    public boolean passwordResetStep1(User user) {
        // 随机生成六位数的验证码
        Integer verificationCode = (int) ((Math.random() * 9 + 1) * 100000);
        user.setEnabled(verificationCode);
        // 将用户数据存入redis，有效期10分钟
        redisTemplate.opsForValue().set(user.getEmail(), user, 10, TimeUnit.MINUTES);
        // 发送邮件
        mailService.sendSimpleMail(user.getEmail(), "安安博客", "您的验证码为：" + verificationCode + "，验证码有效期为十分钟，请尽快使用。");
        return true;
    }

    @Override
    public boolean passwordResetStep2(User user) {
        // 从redis取出用户数据
        User userRedis = (User) redisTemplate.opsForValue().get(user.getEmail());
        // 检查是否存在用户数据
        if (userRedis == null) return false;
        // 对比账号验证码
        if (!user.getUsername().equals(userRedis.getUsername())) return false;
        if (!user.getEnabled().equals(userRedis.getEnabled())) return false;
        // 密码重置为验证码，并使用MD5加密
        user.setPassword(MD5Util.getMD5(user.getEnabled().toString()));
        // 删除验证码
        user.setEnabled(null);

        // 修改密码
        Integer updateNumber = userDao.updatePasswordByUsernameAndEmail(user);
        if (updateNumber == null || updateNumber == 0) return false;

        return true;
    }

    @Override
    public boolean existUsername(User user) {
        if (userDao.getByUsername(user.getUsername()) == null) return false;
        else return true;
    }

    @Override
    public boolean existEmail(User user) {
        if (userDao.getByEmail(user.getEmail()) == null) return false;
        else return true;
    }

    @Override
    public boolean existUsernameAndEmail(User user) {
        if (userDao.getByUsernameAndEmail(user.getUsername(), user.getEmail()) == null) return false;
        else return true;
    }

    @Override
    public boolean isEmailSend(User user) {
        User userRedis = (User) redisTemplate.opsForValue().get(user.getEmail());
        if (userRedis == null) return false;
        else return true;
    }

    /**
     * 生成token
     * @param user
     * @return 成功返回token，失败返回null
     */
    @Override
    public String getTokenByUser(User user) {
        return TokenUtil.creatToken(user.getId(), user.getUsername());
    }

    @Override
    public User getUserInfoById(Integer id) {
        return userDao.getInfoById(id);
    }

    @Override
    public List<Role> getRoleListByUserId(Integer userId) {
        return userRoleDao.getRoleListByUserId(userId);
    }

    @Override
    public User getUserInfoByToken(String token) {
        // 获取id
        Integer id = TokenUtil.getTokenData(token, "id").asInt();// id
        if (id == null)  return null;// id为空 错误

        return userDao.getInfoById(id);
    }

    @Override
    public User getUserSafeByToken(String token) {
        // 获取id
        Integer id = TokenUtil.getTokenData(token, "id").asInt();// id
        if (id == null)  return null;// id为空 错误

        return userDao.getSafeById(id);
    }

    @Override
    public boolean checkUserByTokenAndPassword(String token, String password) {
        User user = new User();
        // 设置id
        Integer id = TokenUtil.getTokenData(token, "id").asInt();// id
        if (id != null) user.setId(id);// id非空 设置
        else return false;// id为空 错误
        // 设置username
        String username = TokenUtil.getTokenData(token, "username").asString();// username
        if (username != null) user.setUsername(username);// username非空 设置
        else return false;// username为空 错误
        // 设置password
        if (password != null) user.setPassword(MD5Util.getMD5(password));// password非空 设置
        else return false;// password为空 错误

        if (userDao.getByIdAndUsernameAndPassword(user) != null) return true;// 存在 返回true
        else return false;// 不存在 错误
    }

    @Override
    public boolean updateUserInfoByTokenAndUser(String token, User user) {
        // 设置id
        Integer id = TokenUtil.getTokenData(token, "id").asInt();// id
        if (id != null) user.setId(id);// id非空 设置
        else return false;// id为空 错误
        // 设置username
        String username = TokenUtil.getTokenData(token, "username").asString();// username
        if (username != null) user.setUsername(username);// username非空 设置
        else return false;// username为空 错误

        Integer updateNumber = userDao.updateInfo(user);
        if (updateNumber == null || updateNumber == 0) return false;
        else return true;
    }

    @Override
    public boolean updatePasswordByTokenAndPassword(String token, String password) {
        User user = new User();
        // 设置id
        Integer id = TokenUtil.getTokenData(token, "id").asInt();// id
        if (id != null) user.setId(id);// id非空 设置
        else return false;// id为空 错误
        // 设置username
        String username = TokenUtil.getTokenData(token, "username").asString();// username
        if (username != null) user.setUsername(username);// username非空 设置
        else return false;// username为空 错误
        // 设置password
        if (password != null) user.setPassword(MD5Util.getMD5(password));// password非空 设置
        else return false;// password为空 错误

        Integer updateNumber = userDao.updatePasswordByIdAndUsername(user);
        if (updateNumber == null || updateNumber == 0) return false;
        else return true;
    }

}
