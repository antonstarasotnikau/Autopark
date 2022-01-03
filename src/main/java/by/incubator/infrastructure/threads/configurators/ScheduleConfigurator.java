package by.incubator.infrastructure.threads.configurators;

import by.incubator.infrastructure.configurators.ProxyConfigurator;
import by.incubator.infrastructure.core.Context;
import by.incubator.infrastructure.threads.annotations.Schedule;
import lombok.SneakyThrows;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ScheduleConfigurator implements ProxyConfigurator {
    public ScheduleConfigurator() {
    }

    @Override
    public <T> T makeProxy(T object, Class<T> implementation, Context context) throws Exception {

        List<Method> methods = new ArrayList<>(List.of(object.getClass().getMethods()));

        methods.removeIf(m -> !m.isAnnotationPresent(Schedule.class));
        if(!methods.isEmpty()){
            methods.removeIf(m -> m.getModifiers() != 1 &&
                    !m.getReturnType().equals(Void.class));
            if(!methods.isEmpty()) {
                return (T) Enhancer.create(implementation, (MethodInterceptor) this::invoke);
            } else throw new Exception("No valid methods");
        }
        return object;
    }
    @SneakyThrows
    private Object invoke(Object object, Method method, Object[] args, MethodProxy methodProxy) {
        Schedule scheduleSync = method.getAnnotation(Schedule.class);
        if(scheduleSync != null) {
            System.out.println(method);
            Thread thread = new Thread(() -> this.invoker(object, methodProxy, args,
                    scheduleSync.timeout(), scheduleSync.delta()));
            thread.setDaemon(true);
            thread.start();
            return null;
        }
        return methodProxy.invokeSuper(object, args);
    }

    private void invoker(Object obj, MethodProxy method, Object[] args, int milliseconds, int delta) {
        Thread thread = new Thread(() -> {
            while(true) {
                try{
                    Thread invokeThread = new Thread(() -> {
                        ExecutorService executorService =
                                Executors.newSingleThreadExecutor(new ThreadFactory() {
                                    @Override
                                    public Thread newThread(Runnable r) {
                                        Thread fThread = Executors.defaultThreadFactory().newThread(r);
                                        fThread.setDaemon(true);
                                        return  fThread;
                                    }
                                });
                        try {
                            executorService.submit(() -> {
                                try {
                                    return method.invokeSuper(obj, args);
                                } catch (Throwable throwable) {
                                    throwable.printStackTrace();
                                }
                                return null;
                            }).get(milliseconds, TimeUnit.MILLISECONDS);
                        } catch (TimeoutException | ExecutionException | InterruptedException exception) {
                            exception.printStackTrace();
                            executorService.shutdownNow();
                        }
                        executorService.shutdownNow();
                    });
                    invokeThread.setDaemon(true);
                    invokeThread.start();
                    Thread.currentThread().sleep(delta);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }
}
