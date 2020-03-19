package com.an.blog.common;

import com.an.blog.bean.User;
import com.an.blog.service.UserService;
import com.an.blog.util.TokenUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 自定义的验证方法
 */
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    /**
     * 获得自己定义的token
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 验证登录信息
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 获取token
        String token = authenticationToken.getCredentials().toString();
        // 验证token
        if (!TokenUtil.verifyToken(token)) throw new AuthenticationException("token无效");
        // 获取id
        Integer id = TokenUtil.getTokenData(token, "id").asInt();
        // 查询数据库是否存在该用户
        User user = userService.getById(id);
        if (user == null) throw new AuthenticationException("账号不存在");
        // 该用户是否可用
        if (user.getEnabled() == 0) throw new AuthenticationException("账号已封禁");
        return new SimpleAuthenticationInfo(token, token, getName());
    }

    /**
     * 验证授权信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 这里PrincipalCollection对象存放的是SimpleAuthenticationInfo(token, token, getName())里的验证信息
        // 获取id
        Integer id = TokenUtil.getTokenData(principalCollection.getPrimaryPrincipal().toString(), "id").asInt();
        // 获取用户角色
        List<String> roles = userService.getRolesByUserId(id);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRoles(roles);
        return simpleAuthorizationInfo;
    }

}
