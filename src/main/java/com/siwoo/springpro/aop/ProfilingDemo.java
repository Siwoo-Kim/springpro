package com.siwoo.springpro.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.Test;
import org.springframework.util.StopWatch;

import java.lang.reflect.Method;

import static com.siwoo.springpro.aop.ProxyUtil.getProxy;

public class ProfilingDemo {

    public static class WorkerBean{
        public void doSomeWork(int noOfTimes){
            for(int x=0; x<noOfTimes ;x++){
                work();
            }
        }

        private void work() {
            System.out.println("");
        }
    }

    /*Around type of an advice*/
    public static class ProfilingInterceptor implements MethodInterceptor{

        @Override
        public Object invoke(MethodInvocation methodInvocation) throws Throwable {
            StopWatch stopWatch = new StopWatch();
            stopWatch.start(methodInvocation.getMethod().getName());

            Object returnValue = methodInvocation.proceed();
            stopWatch.stop();
            dumpInfo(methodInvocation,stopWatch.getTotalTimeMillis());
            return returnValue;
        }

        private void dumpInfo(MethodInvocation methodInvocation, long totalTimeMillis) {
            Method m = methodInvocation.getMethod();
            Object target = methodInvocation.getThis();
            Object[] args = methodInvocation.getArguments();

            System.out.println("Executed method: "+m.getName());
            System.out.println("On object of type: "+target.getClass().getSimpleName());

            System.out.println("With arguments:");;
            for(Object o : args){
                System.out.println("       > "+o);
            }
            System.out.println("\n");

            System.out.println("Took: "+totalTimeMillis+" ms");
        }
    }

    @Test
    public void test() throws Exception {
        WorkerBean workerBean = getProxy(WorkerBean.class,ProfilingInterceptor.class);
        workerBean.doSomeWork(100000);
    }

}
















