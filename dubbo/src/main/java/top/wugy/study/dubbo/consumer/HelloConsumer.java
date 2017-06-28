package top.wugy.study.dubbo.consumer;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import top.wugy.study.dubbo.service.HelloService;

/**
 * Created by wugy on 2017/6/26 15:03
 */
public class HelloConsumer {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("hello-consumer.xml");
        ctx.start();

        HelloService helloService = (HelloService) ctx.getBean("helloService", HelloService.class);
        System.out.println(helloService.sayHello("world"));

        try {
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
