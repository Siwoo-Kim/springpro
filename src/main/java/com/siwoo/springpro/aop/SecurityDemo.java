package com.siwoo.springpro.aop;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.extern.java.Log;
import org.junit.Test;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Method;

import static org.junit.Assert.fail;

@Log
public class SecurityDemo {

    public static class SecureBean{
        public void writeSecureMessage(){
            System.out.println("Every time I learn something new, It pushes some old stuff out of my brain");
        }
    }

    @Getter @Setter @Builder
    public static class UserInfo{
        private String userName;
        private String password;
    }

    public static class SecurityManager{
        private static ThreadLocal<UserInfo> threadLocal = new ThreadLocal<>();

        public void login(String userName,String password){
            threadLocal.set(UserInfo.builder()
                    .userName(userName).password(password)
                    .build());
        }
        public void logout(){
            threadLocal.set(null);
        }

        public UserInfo getLoggedOnUser(){
            return threadLocal.get();
        }
    }

    public static final class SecurityAdvice implements MethodBeforeAdvice {
        private SecurityManager securityManager;

        public SecurityAdvice() {
            this.securityManager = new SecurityManager();
        }

        @Override
        public void before(Method method, Object[] args, Object target) throws Throwable {
            UserInfo userInfo = securityManager.getLoggedOnUser();

            if(ObjectUtils.isEmpty(userInfo)){
                System.out.println("No user authenticated");
                throw new SecurityException("You must login before attempting to invoke the method: "+ method.getName());
            }else if("John".equals(userInfo.getUserName())){
                System.out.println("Logged in user is "+userInfo.getUserName());
            }else{
                System.out.println("Logged in user is "+userInfo.getUserName()+" NOT GOOD :(");
                throw new SecurityException("User "+userInfo.getUserName()+" is not allwed access to method "+method.getName());
            }
        }

    }

    @Test(expected = SecurityException.class)
    public void testing() {

        SecurityManager securityManager = new SecurityManager();
        SecureBean secureBean = getSecureBean();
        securityManager.login("John","pwd");
        secureBean.writeSecureMessage();
        securityManager.logout();


        securityManager.login("invalid user","pwd");
        secureBean.writeSecureMessage();
        fail();


    }



    private static SecureBean getSecureBean() {
        SecureBean target = new SecureBean();
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(target);
        proxyFactory.addAdvice(new SecurityAdvice());
        return (SecureBean) proxyFactory.getProxy();
    }
}
