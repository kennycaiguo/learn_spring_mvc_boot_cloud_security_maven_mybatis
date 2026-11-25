package org.kenny.spring.beancreate;

public class StarFactory {
    public static Star get() {
        return new Star(); //这个是我们自己创建的，我们希望把它纳入IoC容器的管理
    }
}
