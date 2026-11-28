# Spring IoC注解式开发

## 首先，必须明白：注解式开发才是spring框架的常用开发方式

## 声明Bean的注解

负责声明Bean的注解，常见的包括四个：其实他们都是同一个的别名，都@Component，之所以要有4个，是为了增加可读性

* @Component
* @Controller
* @Service
* @Repository

源码如下：

```java
@Target(value = {ElementType.TYPE})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Component {
    String value();
}

```

```java
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Controller {
    @AliasFor(
        annotation = Component.class
    )
    String value() default "";
}

```

```java
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Service {
    @AliasFor(
        annotation = Component.class
    )
    String value() default "";
}

```

```java
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Repository {
    @AliasFor(
        annotation = Component.class
    )
    String value() default "";
}

```

通过源码可以看到，@Controller、@Service、@Repository这三个注解都是@Component注解的别名。

也就是说：这四个注解的功能都一样。用哪个都可以。

只是为了增强程序的可读性，建议：

* 控制器类上使用：Controller
* service类上使用：Service
* dao类上使用：Repository

他们都是只有一个value属性。value属性用来指定bean的id，也就是bean的名字。

注意，这几个注解只是声明了Bean，并没有解决依赖注入的问题

## Spring注解的使用

如何使用以上的注解呢？

* 第一步：加入aop的依赖
* 第二步：在配置文件中添加context命名空间
* 第三步：在配置文件中指定扫描的包
* 第四步：在Bean类上使用注解

**第一步：加入aop的依赖**

当加入spring-context依赖之后，会关联加入aop的依赖。所以这一步不用做。

**第二步：在配置文件中添加context命名空间**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
                           http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">

</beans>
```

**第三步：在配置文件中指定要扫描的包**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
                           http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">
    <context:component-scan base-package="com.jkweilai.spring.bean"/>
</beans>
```

**第四步：在Bean类上使用注解**

```java
package com.jkweilai.spring.bean;

import org.springframework.stereotype.Component;

@Component(value = "userBean")
public class User {
}

```

编写测试程序：

```java
public class AnnotationTest {
    @Test
    public void testBean(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
        User userBean = applicationContext.getBean("userBean", User.class);
        System.out.println(userBean);
    }
}

```

执行结果：

![1763804203105-01de403b-0c38-49b2-b97a-539b080ff2ce.png](./note10-Spring IoC注解式开发.assets/1763804203105-01de403b-0c38-49b2-b97a-539b080ff2ce-606927.png)

**<font style="color:#E8323C;">如果注解的属性名是value，那么value是可以省略的。</font>**

```java
@Component("vipBean")
public class Vip {
}

```

```java
public class AnnotationTest {
    @Test
    public void testBean(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
        Vip vipBean = applicationContext.getBean("vipBean", Vip.class);
        System.out.println(vipBean);
    }
}

```

执行结果：

![1763804235969-ee73d66b-b32a-45e1-bd5a-14be1d80c299.png](./note10-Spring IoC注解式开发.assets/1763804235969-ee73d66b-b32a-45e1-bd5a-14be1d80c299-370980.png)

**<font style="color:#E8323C;">如果把value属性彻底去掉，spring会为 Bean自动取名吗？会的。并且默认名字的规律是：Bean类名首字母小写。</font>**

```java
@Component
public class BankDao {
}
```

也就是说，这个BankDao的bean的名字为：bankDao

测试一下

```java
public class AnnotationTest {
    @Test
    public void testBean(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
        BankDao bankDao = applicationContext.getBean("bankDao", BankDao.class);
        System.out.println(bankDao);
    }
}

```

执行结果：

![1763804278652-8433e88b-d875-44b4-b035-8088bc3849a3.png](./note10-Spring IoC注解式开发.assets/1763804278652-8433e88b-d875-44b4-b035-8088bc3849a3-012144.png)

我们将Component注解换成其它三个注解，看看是否可以用：

```java
@Controller
public class BankDao {
}

```

执行结果：

![1763804296741-fd026e58-9034-448f-8c7f-29d26316ba8d.png](./note10-Spring IoC注解式开发.assets/1763804296741-fd026e58-9034-448f-8c7f-29d26316ba8d-614220.png)

剩下的两个注解大家可以测试一下。

**<font style="color:#E8323C;">如果是多个包怎么办？有两种解决方案：</font>**

* **<font style="color:#E8323C;">第一种：在配置文件中指定多个包，用逗号隔开。</font>**
* **<font style="color:#E8323C;">第二种：指定多个包的共同父包。</font>**

先来测试一下逗号（英文）的方式：

创建一个新的包：bean2，定义一个Bean类。

```java
@Service
public class Order {
}

```

配置文件修改：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
                           http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">
    <context:component-scan base-package="com.jkweilai.spring.bean,com.jkweilai.spring.bean2"/>
</beans>
```

测试程序：

```java
public class AnnotationTest {
    @Test
    public void testBean(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
        BankDao bankDao = applicationContext.getBean("bankDao", BankDao.class);
        System.out.println(bankDao);
        Order order = applicationContext.getBean("order", Order.class);
        System.out.println(order);
    }
}

```

执行结果：

![1763804341646-5c51361e-bd26-4f3b-b86e-af784a6051f5.png](./note10-Spring IoC注解式开发.assets/1763804341646-5c51361e-bd26-4f3b-b86e-af784a6051f5-109487.png)

我们再来看看，指定共同的父包行不行：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
                           http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">
    <context:component-scan base-package="com.jkweilai.spring"/>
</beans>
```

执行测试程序：

![1763804354597-61388add-ec5c-4121-88b2-273cfafd7a12.png](./note10-Spring IoC注解式开发.assets/1763804354597-61388add-ec5c-4121-88b2-273cfafd7a12-939525.png)

## 选择性实例化Bean

假设在某个包下有很多Bean，有的Bean上标注了Component，有的标注了Controller，有的标注了Service，有的标注了Repository，现在由于某种特殊业务的需要，只允许其中所有的Controller参与Bean管理，其他的都不实例化。这应该怎么办呢？

```java
@Component
public class A {
    public A() {
        System.out.println("A的无参数构造方法执行");
    }
}

@Controller
class B {
    public B() {
        System.out.println("B的无参数构造方法执行");
    }
}

@Service
class C {
    public C() {
        System.out.println("C的无参数构造方法执行");
    }
}

@Repository
class D {
    public D() {
        System.out.println("D的无参数构造方法执行");
    }
}

@Controller
class E {
    public E() {
        System.out.println("E的无参数构造方法执行");
    }
}

@Controller
class F {
    public F() {
        System.out.println("F的无参数构造方法执行");
    }
}

```

我只想实例化bean3包下的Controller。配置文件这样写：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
                           http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">

    <context:component-scan base-package="com.jkweilai.spring.bean3" use-default-filters="false">
        <context:include-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
    </context:component-scan>
    
</beans>
```

use-default-filters="true" 表示：使用spring默认的规则，只要有Component、Controller、Service、Repository中的任意一个注解标注，则进行实例化。

**<font style="color:#E8323C;">use-default-filters="false"</font>** 表示：不再spring默认实例化规则，即使有Component、Controller、Service、Repository这些注解标注，也不再实例化。

\<context:include-filter type="annotation" expression="org.springframework.stereotype.Controller"/> 表示只有Controller进行实例化。

```java
@Test
public void testChoose(){
    ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-choose.xml");
}
```

执行结果：

![1763804449496-e4d1f7c3-436d-4f66-baff-b698715d56bf.png](./note10-Spring IoC注解式开发.assets/1763804449496-e4d1f7c3-436d-4f66-baff-b698715d56bf-846086.png)

也可以将use-default-filters设置为true（不写就是true），并且采用exclude-filter方式排除哪些注解标注的Bean不参与实例化：

```xml
<context:component-scan base-package="com.jkweilai.spring.bean3">
  <context:exclude-filter type="annotation" expression="org.springframework.stereotype.Repository"/>
  <context:exclude-filter type="annotation" expression="org.springframework.stereotype.Service"/>
  <context:exclude-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
</context:component-scan>
```

执行测试程序：

![1763804479326-d90316fa-ca13-4a1e-b50d-f69afe8bd63e.png](./note10-Spring IoC注解式开发.assets/1763804479326-d90316fa-ca13-4a1e-b50d-f69afe8bd63e-506157.png)

## 负责注入的注解

@Component @Controller @Service @Repository 这四个注解是用来声明Bean的，声明后这些Bean将被实例化。接下来我们看一下，如何给Bean的属性赋值。给Bean属性赋值需要用到这些注解：

* @Value
* @Autowired
* @Qualifier
* @Resource

### @Value

当属性的类型是简单类型时，可以使用@Value注解进行注入。

```java
@Component
public class User {
    @Value(value = "zhangsan")
    private String name;
    @Value("20")
    private int age;

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}

```

开启包扫描：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
                           http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">
    <context:component-scan base-package="com.jkweilai.spring.bean4"/>
</beans>
```

```java
@Test
public void testValue(){
    ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-injection.xml");
    Object user = applicationContext.getBean("user");
    System.out.println(user);
}
```

执行结果：

![1763804917630-cc7f0031-f604-4fc9-aab3-80642ab35aa9.png](./note10-Spring IoC注解式开发.assets/1763804917630-cc7f0031-f604-4fc9-aab3-80642ab35aa9-401263.png)

通过以上代码可以发现，我们并没有给属性提供setter方法，但仍然可以完成属性赋值。

如果提供setter方法，并且在setter方法上添加@Value注解，可以完成注入吗？尝试一下：

```java
@Component
public class User {
    
    private String name;

    private int age;

    @Value("李四")
    public void setName(String name) {
        this.name = name;
    }

    @Value("30")
    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}

```

执行结果：

![1763804943343-e101a0a8-fbce-4e33-be2f-05a95c64f7a6.png](./note10-Spring IoC注解式开发.assets/1763804943343-e101a0a8-fbce-4e33-be2f-05a95c64f7a6-613562.png)

通过测试可以得知，@Value注解可以直接使用在属性上，也可以使用在setter方法上。都是可以的。都可以完成属性的赋值。

为了简化代码，以后我们一般不提供setter方法，直接在属性上使用@Value注解完成属性赋值。

出于好奇，我们再来测试一下，是否能够通过构造方法完成注入：

```java
@Component
public class User {

    private String name;

    private int age;

    public User(@Value("隔壁老王") String name, @Value("33") int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}

```

执行结果：

![1763804995156-d949008f-37c3-4c67-8e6c-691963c4dca5.png](./note10-Spring IoC注解式开发.assets/1763804995156-d949008f-37c3-4c67-8e6c-691963c4dca5-283875.png)

通过测试得知：@Value注解可以出现在属性上、setter方法上、以及构造方法的形参上。可见Spring给我们提供了多样化的注入。太灵活了。

### @Autowired与@Qualifier

@Autowired注解可以用来注入\*\*<font style="color:#F5222D;">非简单类型</font>\*\*。被翻译为：自动连线的，或者自动装配。

单独使用@Autowired注解，**<font style="color:#F5222D;">默认根据类型装配</font>**。【默认是byType】

看一下它的源码：

```java
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Autowired {
	boolean required() default true;
}
```

源码中有两处需要注意：

* 第一处：该注解可以标注在哪里？
  * 构造方法上
  * 方法上
  * 形参上
  * 属性上
  * 注解上
* 第二处：该注解有一个required属性，默认值是true，表示在注入的时候要求被注入的Bean必须是存在的，如果不存在则报错。如果required属性设置为false，表示注入的Bean存在或者不存在都没关系，存在的话就注入，不存在的话，也不报错。

**<font style="color:#722ED1;">我们先在属性上使用@Autowired注解：</font>**

```java
public interface UserDao {
    void insert();
}

```

```java
@Repository //纳入bean管理
public class UserDaoForMySQL implements UserDao{
    @Override
    public void insert() {
        System.out.println("正在向mysql数据库插入User数据");
    }
}

```

```java
package com.jkweilai.spring.service;

import com.jkweilai.spring.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service // 纳入bean管理
public class UserService {

    @Autowired // 在属性上注入
    private UserDao userDao;
    
    // 没有提供构造方法和setter方法。

    public void save(){
        userDao.insert();
    }
}
```

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
                           http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">
    <context:component-scan base-package="com.jkweilai.spring.dao,com.jkweilai.spring.service"/>
</beans>
```

```java
@Test
public void testAutowired(){
    ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-injection.xml");
    UserService userService = applicationContext.getBean("userService", UserService.class);
    userService.save();
}
```

执行结果：

![1763805114065-6361a6f4-61c9-474b-9ae9-ef0ed63f6865.png](./note10-Spring IoC注解式开发.assets/1763805114065-6361a6f4-61c9-474b-9ae9-ef0ed63f6865-380340.png)

以上构造方法和setter方法都没有提供，经过测试，仍然可以注入成功。

**<font style="color:#722ED1;">接下来，再来测试一下@Autowired注解出现在setter方法上：</font>**

```java
@Service
public class UserService {

    private UserDao userDao;

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void save(){
        userDao.insert();
    }
}

```

执行结果：

![1763805143555-f1d1626f-416f-4812-87b2-c347277c2399.png](./note10-Spring IoC注解式开发.assets/1763805143555-f1d1626f-416f-4812-87b2-c347277c2399-057229.png)

**<font style="color:#722ED1;">我们再来看看能不能出现在构造方法上：</font>**

```java
@Service
public class UserService {

    private UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void save(){
        userDao.insert();
    }
}

```

执行结果：

![1763805158158-1864aedd-717b-4cbc-a174-1b8cf56b0907.png](./note10-Spring IoC注解式开发.assets/1763805158158-1864aedd-717b-4cbc-a174-1b8cf56b0907-033126.png)

**<font style="color:#722ED1;">再来看看，这个注解能不能只标注在构造方法的形参上：</font>**

```java
@Service
public class UserService {

    private UserDao userDao;

    public UserService(@Autowired UserDao userDao) {
        this.userDao = userDao;
    }

    public void save(){
        userDao.insert();
    }
}

```

执行结果：

![1763805183577-c0a54642-14c1-45aa-98a7-2353dc7c0dc9.png](./note10-Spring IoC注解式开发.assets/1763805183577-c0a54642-14c1-45aa-98a7-2353dc7c0dc9-499610.png)

**<font style="color:#722ED1;">还有更劲爆的，当有参数的构造方法只有一个时，@Autowired注解可以省略。</font>**

```java
@Service
public class UserService {

    private UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void save(){
        userDao.insert();
    }
}

```

执行结果：

![1763805219740-0dd5b10c-b852-4a76-9d3d-72913af93ffc.png](./note10-Spring IoC注解式开发.assets/1763805219740-0dd5b10c-b852-4a76-9d3d-72913af93ffc-218379.png)

**<font style="color:#722ED1;">当然，如果有多个构造方法，@Autowired肯定是不能省略的。</font>**

```java
@Service
public class UserService {

    private UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }
    
    public UserService(){
        
    }

    public void save(){
        userDao.insert();
    }
}

```

执行结果：

![1763805250282-dae28d2e-04b3-450c-bfb9-c3cd14458593.png](./note10-Spring IoC注解式开发.assets/1763805250282-dae28d2e-04b3-450c-bfb9-c3cd14458593-664012.png)

到此为止，我们已经清楚@Autowired注解可以出现在哪些位置了。

@Autowired注解默认是byType进行注入的，也就是说根据类型注入的，如果以上程序中，UserDao接口还有另外一个实现类，会出现问题吗？

```java
@Repository //纳入bean管理
public class UserDaoForOracle implements UserDao{
    @Override
    public void insert() {
        System.out.println("正在向Oracle数据库插入User数据");
    }
}

```

当你写完这个新的实现类之后，此时IDEA工具已经提示错误信息了：

![1763805295378-ca25b98c-bab2-4d28-b7bb-190ea894aa40.png](./note10-Spring IoC注解式开发.assets/1763805295378-ca25b98c-bab2-4d28-b7bb-190ea894aa40-886989.png)

错误信息中说：不能装配，UserDao这个Bean的数量大于1.

怎么解决这个问题呢？**<font style="color:#F5222D;">当然要byName，根据名称进行装配了。</font>**

@Autowired注解和@Qualifier注解联合起来才可以根据名称进行装配，在@Qualifier注解中指定Bean名称。

```java
@Repository // 这里没有给bean起名，默认名字是：userDaoForOracle
public class UserDaoForOracle implements UserDao{
    @Override
    public void insert() {
        System.out.println("正在向Oracle数据库插入User数据");
    }
}

```

```java
@Service
public class UserService {

    private UserDao userDao;

    @Autowired
    @Qualifier("userDaoForOracle") // 这个是bean的名字。
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void save(){
        userDao.insert();
    }
}
```

执行结果：

![1763805339010-607a6347-00c3-40f2-8e95-826b07e2641f.png](./note10-Spring IoC注解式开发.assets/1763805339010-607a6347-00c3-40f2-8e95-826b07e2641f-655561.png)

总结：

* @Autowired注解可以出现在：属性上、构造方法上、构造方法的参数上、setter方法上。
* 当带参数的构造方法只有一个，@Autowired注解可以省略。
* @Autowired注解默认根据类型注入。如果要根据名称注入的话，需要配合@Qualifier注解一起使用。

### @Resource

@Resource注解也可以完成\*\*<font style="color:#DF2A3F;">非简单类型</font>\*\*注入。那它和@Autowired注解有什么区别？

* @Resource注解是JDK扩展包中的，也就是说属于JDK的一部分。所以该注解是标准注解，更加具有通用性。(JSR-250标准中制定的注解类型。JSR是Java规范提案。)
* @Autowired注解是Spring框架自己的。
* **<font style="color:#E8323C;">@Resource注解默认根据名称装配byName，未指定name时，使用属性名作为name。通过name找不到的话会自动启动通过类型byType装配。</font>**
* **<font style="color:#E8323C;">@Autowired注解默认根据类型装配byType，如果想根据名称装配，需要配合@Qualifier注解一起用。</font>**
* @Resource注解用在属性上、setter方法上。
* @Autowired注解用在属性上、setter方法上、构造方法上、构造方法参数上。

@Resource注解属于JDK扩展包，所以不在JDK当中，需要额外引入以下依赖：【**<font style="color:#F5222D;">如果是JDK8的话不需要额外引入依赖。高于JDK11或低于JDK8需要引入以下依赖。</font>**】

```xml
<dependency>
  <groupId>jakarta.annotation</groupId>
  <artifactId>jakarta.annotation-api</artifactId>
  <version>2.1.1</version>
</dependency>
```

一定要注意：**<font style="color:#E8323C;">如果你用Spring6+，要知道它不再支持JavaEE，它支持的是JakartaEE9。</font>**

@Resource注解的源码如下：

![1665565515435-2ad5614a-8572-4c6f-80c1-efa236dbe35f.png](./note10-Spring IoC注解式开发.assets/1665565515435-2ad5614a-8572-4c6f-80c1-efa236dbe35f-454792.png)

测试一下：

```java
@Repository("xyz")
public class UserDaoForOracle implements UserDao{
    @Override
    public void insert() {
        System.out.println("正在向Oracle数据库插入User数据");
    }
}
```

```java
@Service
public class UserService {

    @Resource(name = "xyz")
    private UserDao userDao;

    public void save(){
        userDao.insert();
    }
}
```

执行测试程序：

![1763805703043-58f861ec-79ca-409e-bc92-b731277c6dd3.png](./note10-Spring IoC注解式开发.assets/1763805703043-58f861ec-79ca-409e-bc92-b731277c6dd3-986604.png)

**我们把UserDaoForOracle的名字xyz修改为userDao，让这个Bean的名字和UserService类中的UserDao属性名一致：**

```java
@Repository("userDao")
public class UserDaoForOracle implements UserDao{
    @Override
    public void insert() {
        System.out.println("正在向Oracle数据库插入User数据");
    }
}

```

```java
@Service
public class UserService {

    @Resource
    private UserDao userDao;

    public void save(){
        userDao.insert();
    }
}

```

执行测试程序：

![1763805728365-2326f7c4-ecf6-440e-a6f7-e6dd40eb914b.png](./note10-Spring IoC注解式开发.assets/1763805728365-2326f7c4-ecf6-440e-a6f7-e6dd40eb914b-209100.png)

通过测试得知，当@Resource注解使用时没有指定name的时候，还是根据name进行查找，这个name是属性名。

接下来把UserService类中的属性名修改一下：

```java
@Service
public class UserService {

    @Resource
    private UserDao userDao2;

    public void save(){
        userDao2.insert();
    }
}

```

执行结果：

![1763805768831-da788b4d-49b5-4dd0-a444-e2b0bed95b37.png](./note10-Spring IoC注解式开发.assets/1763805768831-da788b4d-49b5-4dd0-a444-e2b0bed95b37-111274.png)

根据异常信息得知：显然当通过name找不到的时候，自然会启动byType进行注入。以上的错误是因为UserDao接口下有两个实现类导致的。所以根据类型注入就会报错。

我们再来看@Resource注解使用在setter方法上可以吗？

```java
@Service
public class UserService {

    private UserDao userDao;

    @Resource
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void save(){
        userDao.insert();
    }
}

```

注意这个setter方法的方法名，setUserDao去掉set之后，将首字母变小写userDao，userDao就是name

执行结果：

![1763805795181-a8d504f6-ae7f-4ed0-af53-d42600cc3a52.png](./note10-Spring IoC注解式开发.assets/1763805795181-a8d504f6-ae7f-4ed0-af53-d42600cc3a52-644144.png)

当然，也可以指定name：

```java
@Service
public class UserService {

    private UserDao userDao;

    @Resource(name = "userDaoForMySQL")
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void save(){
        userDao.insert();
    }
}

```

执行结果：

![1763805810622-0e5e2cb0-7aa9-45b6-bf56-f35c991e7ed4.png](./note10-Spring IoC注解式开发.assets/1763805810622-0e5e2cb0-7aa9-45b6-bf56-f35c991e7ed4-734394.png)

一句话总结@Resource注解：默认byName注入，没有指定name时把属性名当做name，根据name找不到时，才会byType注入。byType注入时，某种类型的Bean只能有一个。

## 全注解式开发

所谓的全注解开发就是不再使用spring配置文件了。写一个配置类来代替配置文件。

```java
package com.jkweilai.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"com.jkweilai.spring.dao", "com.jkweilai.spring.service"})
public class SpringConfiguration {

    // 将自己new的对象纳入IoC容器的管理
    @Bean("myUserService")
    public UserService getUserService() {
        // 虽然是自己 new 的对象。如果对象中属性需要注入，Spring IoC容器会自动注入。不需要担心null。
        return new UserService();
    }
}

```

编写测试程序：不再new ClassPathXmlApplicationContext()对象了。

```java
@Test
public void testNoXml(){
    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfiguration.class);
    UserService userService = applicationContext.getBean("userService", UserService.class);
    userService.save();
    UserService myUserService = applicationContext.getBean("myUserService", UserService.class);
    myUserService.save();
}
```

以上内容主要掌握的注解有三个：

1. `@Configuration`
2. `@ComponentScan`
3. `@Bean`

# 演练1.spring注解的使用

## 1.1把001项目复制一份，改名spring-006-annotation,然后把target文件夹删除，在idea工具里面把它添加为maven项目，把beans.xml改名appContext.xml

![image-20261126193756563](./note10-Spring IoC注解式开发.assets/image-20261126193756563.png)

## 1.2在配置文件中添加context命名空间

![image-20261126194015868](./note10-Spring IoC注解式开发.assets/image-20261126194015868.png)

## 1.3.在配置文件中指定要扫描的包

![image-20261126194146542](./note10-Spring IoC注解式开发.assets/image-20261126194146542.png)

## 1.4把User类和Vip类删除，然后新建一个UserDao类，一个UserService类和一个UserController类

![image-20261126195357806](./note10-Spring IoC注解式开发.assets/image-20261126195357806.png)

## 1.5然后我们给UserController添加一个@Controller注解，给UserService添加一个@Service注解，给UserDao添加一条@Repository注解，注意光添加注解还不行，必须指定给value属性赋值，也就是给bean起名字

![image-20261126200712380](./note10-Spring IoC注解式开发.assets/image-20261126200712380.png)

## 1.6.如果我们只是希望获取他们，下面的代码是可以获取到他们的实例的。

![image-20261126201343976](./note10-Spring IoC注解式开发.assets/image-20261126201343976.png)

### 注意到了吗？UserController里面的userService属性是null，UserService对象里面的userDao属性也是null，也就是说spring的确帮我们创建了对象，但是没有做依赖注入，那么怎么办呢？这就需要用到这些注解了，我们下一节课学习

![image-20261126201628530](./note10-Spring IoC注解式开发.assets/image-20261126201628530.png)

## 1.7注意,其实这几个注解上面的value属性可以不用配置，那么他默认就是把类名的首字母小写后的名称之外bean的名称，运行程序，顺利通过

![image-20261126202902700](./note10-Spring IoC注解式开发.assets/image-20261126202902700.png)

## 注意：如果你的代码既要给前端使用，又要给后端使用，这时候就需要指定名字。其实指定名称是一个比较好的方式，可以避免bean的id冲突

## 1.8假如我们有多个包需要扫描，比如我们新建另外一个新的类OrderController它在bean2包里面

![image-20261127105556199](./note10-Spring IoC注解式开发.assets/image-20261127105556199.png)

## 1.9此时你取获取OrderController的bean，程序会崩溃

![image-20261127105818333](./note10-Spring IoC注解式开发.assets/image-20261127105818333.png)

## 1.10此时我们需要在appContext.xml中这么配置

![image-20261127105956250](./note10-Spring IoC注解式开发.assets/image-20261127105956250.png)

## 1.11然后就可以顺利通过测试

![image-20261127110039294](./note10-Spring IoC注解式开发.assets/image-20261127110039294.png)

## 1.12当然，还有一种方式就是指定公共父包

![image-20261127110431989](./note10-Spring IoC注解式开发.assets/image-20261127110431989.png)

![image-20261127110504288](./note10-Spring IoC注解式开发.assets/image-20261127110504288.png)

# 演练2.选择性实例化bean

## 2.1新建一个bean3包，在里面新建一个SpringBean.java,在里面创建一些私有类，他们各有不同的注解

![image-20261127115536800](./note10-Spring IoC注解式开发.assets/image-20261127115536800.png)

## 2.2.我们新建一个appContext2.xml配置文件，做下面的配置

![image-20261127115310684](./note10-Spring IoC注解式开发.assets/image-20261127115310684.png)

## 2.3.测试一下，果然只有用Controller注解的类被实例化了

![image-20261127115635133](./note10-Spring IoC注解式开发.assets/image-20261127115635133.png)

## 2.4.当然，也可以把不需要实例化的注解排除

![image-20261127115924656](./note10-Spring IoC注解式开发.assets/image-20261127115924656.png)

# 演练3.用Value注解注入简单数据类型

## 3.1新建一个bean4包，在里面新建一个User类，然后给它添加2个属性name和age然后分别用Value注解注入值，然后添加setter方法和toString方法

![image-20261127121216781](./note10-Spring IoC注解式开发.assets/image-20261127121216781.png)

## 3.2然后我们新建一个配置文件appContext3.xml，为了方便观察效果，位置只需要扫描bean4包

![image-20261127121650952](./note10-Spring IoC注解式开发.assets/image-20261127121650952.png)

## 3.3然后我们写一个测试方法，发现的确可以注入值

![image-20261127121824544](./note10-Spring IoC注解式开发.assets/image-20261127121824544.png)

## 3.4我们在setter方法里面添加语句输出语句，测试一下他们究竟有没有被执行

![image-20261127122141300](./note10-Spring IoC注解式开发.assets/image-20261127122141300.png)

## 3.5程序执行test04，我们发现它并没有调用setter方法

![image-20261127122304846](./note10-Spring IoC注解式开发.assets/image-20261127122304846.png)

## 3.6既然这样，我们把setter注释了。

![image-20261127122357466](./note10-Spring IoC注解式开发.assets/image-20261127122357466.png)

## 3.7发现它是可以正常运行的。

![image-20261127122447242](./note10-Spring IoC注解式开发.assets/image-20261127122447242.png)

## 3.8.其实这个Value注解也可以用在setter上。我们修改一下User类的代码

![image-20261127122736792](./note10-Spring IoC注解式开发.assets/image-20261127122736792.png)

## 3.9运行test04，发现顺利通过，此时setter方法被调用了

![image-20261127122821486](./note10-Spring IoC注解式开发.assets/image-20261127122821486.png)

### 所以，我们得到一个结论，如果Value注解直接放到属性上面，就不会调用setter方法。除非你显示的把Value注解放在setter方法上面，此时就会调用setter方法

## 注意：

### 1.Value注解不支持静态方法！！！

### 2.如果把它Value添加到一个方法上面，它不必以set开头，任何方法名都可以

### 3.如果你在属性上面添加了Value注解，设置一个值，在这个属性对应的set方法里面有添加有Value注解并且设置了一个不同的值，它会以set方法的值为准

![image-20261127132001047](./note10-Spring IoC注解式开发.assets/image-20261127132001047.png)

![image-20261127132044276](./note10-Spring IoC注解式开发.assets/image-20261127132044276.png)

### 4.Value注解还可以用在构造函数的参数上面，我们把属性上面的注解和setter上面的Value注解统统注释了。然后我们添加一个构造函数，给参数添加Value注解并且设置值

![image-20261127132821925](./note10-Spring IoC注解式开发.assets/image-20261127132821925.png)

### 测试一下，也是可以正常运行的

![image-20261127132917567](./note10-Spring IoC注解式开发.assets/image-20261127132917567.png)

### 但是，Value注解不能加在构造方法上面！！！

# 演练4.Autowired和Qualifier注解的使用

## 首先有一点需要注意，就是Autowired注解默认只能够工具类型进行自动装配，如果你想它根据名称进行自动装配，还需要结合Qualifier注解来使用，也就是需要2个注解

## 4.1我们使用bean包里面的类来进行测试，我们在UserService类里面的dao属性上面添加一个@Autowired注解，在UserController里面的service属性上面也添加一个@Autowired注解

![image-20261127135032408](./note10-Spring IoC注解式开发.assets/image-20261127135032408.png)

## 4.2为了方便查看结果，我们把appContext.xml复制一份，改名appContext4.xml，设置它只扫描bean包

![image-20261127135156324](./note10-Spring IoC注解式开发.assets/image-20261127135156324.png)

## 4.3然后我们写一个testAutowired方法，顺利通过测试

![image-20261127135414059](./note10-Spring IoC注解式开发.assets/image-20261127135414059.png)

### 可以看到，所有的属性都有值，它的确进行了自动装配

## 4.4然后我们把属性上面的注解注释，把它添加到set方法里面

![image-20261127140140728](./note10-Spring IoC注解式开发.assets/image-20261127140140728.png)

## 4.5运行测试代码，也是没有问题的

![image-20261127141611601](./note10-Spring IoC注解式开发.assets/image-20261127141611601.png)

## 4.6然后我们把set方法和他的的注解注释了，把注解添加到构造函数里面。

![image-20261127143209291](./note10-Spring IoC注解式开发.assets/image-20261127143209291.png)

## 4.7也是可以正常通过测试的

![image-20261127143324152](./note10-Spring IoC注解式开发.assets/image-20261127143324152.png)

## 4.8把Autowired注解添加到构造方法的参数上面

![image-20261127143634508](./note10-Spring IoC注解式开发.assets/image-20261127143634508.png)

## 4.9发现程序还是能够正常运行的。

![image-20261127143848523](./note10-Spring IoC注解式开发.assets/image-20261127143848523.png)

## 4.10其实Autowired注解可以省略但是必须符合下面的要求，1.只有一个有参构造函数，在这个构造函数里面必须给属性赋值，不能有无参构造函数。2.没有提供setter。我们把UserController里面的所有Autowired的注解注释或者删除。

![image-20261127144357115](./note10-Spring IoC注解式开发.assets/image-20261127144357115.png)

## 4.11测试还是可以顺利通过的

![image-20261127144454991](./note10-Spring IoC注解式开发.assets/image-20261127144454991.png)

### 注意：默认spring框架创建bean是调用无参构造函数的，所以如果你通过了一个无参构造函数和一个有参数的构造函数，spring框架会使用无参构造函数，所以如果你想省略Autowired注解，你一定不能够通过无参构造函数

# 演练5.Autowired注解配合Qualifier接口进行根据名称自动装配

## 5.1需要修改一下代码，创建一个IUserDao接口，

![image-20261127150055136](./note10-Spring IoC注解式开发.assets/image-20261127150055136.png)

### 然后创建2给实现类来实现这个接口

![image-20261127150553415](./note10-Spring IoC注解式开发.assets/image-20261127150553415.png)

## 5.2然后我们来修改一下UserService的代码

![image-20261127150811042](./note10-Spring IoC注解式开发.assets/image-20261127150811042.png)

## 5.3此时运行测试会报错，因为这里找到2个系统类型的IUserDao实现类对象，spring不知道该使用哪一个

![image-20261127151032010](./note10-Spring IoC注解式开发.assets/image-20261127151032010.png)

## 5.4此时我们需要用Qualifier注解指定究竟你是使用哪一个实现类，比如这里我们使用用于Mysql的实现类，注意Qualifier注解不能添加在构造函数上面，所以我们需要改为setter方法，把构造函数注释了

![image-20261127153046829](./note10-Spring IoC注解式开发.assets/image-20261127153046829.png)

## 5.5运行测试，顺利通过

![image-20261127153231863](./note10-Spring IoC注解式开发.assets/image-20261127153231863.png)

# 演练6.Lombok库的使用，它可以帮我们生成setter，getter和构造方法

## 6.1在项目的pom.xml里面引入Lombok

![image-20261127154638902](./note10-Spring IoC注解式开发.assets/image-20261127154638902.png)

## 6.2.创建一个Customer类，只提供属性，不通过getter、setter和构造函数

![image-20261127154900449](./note10-Spring IoC注解式开发.assets/image-20261127154900449.png)

## 6.3在这个Customer类上面添加一个@Data注解

![image-20261127155104954](./note10-Spring IoC注解式开发.assets/image-20261127155104954.png)

## 6.4然后我们可以测试一下Lombok，发现的确生成了setter方法因为可以正常给属性赋值，也能够获取得到

![image-20261127155711881](./note10-Spring IoC注解式开发.assets/image-20261127155711881.png)

## 6.5新建一个MyService类，在里面有一个UserDao类的属性在前面添加修饰符final，此时我们必须立即给它赋值或者在构造函数里面给它赋值，这里使用后者，还可以添加一个save方法在里面调用userDao的方法

![image-20261127160452696](./note10-Spring IoC注解式开发.assets/image-20261127160452696.png)

![image-20261127160959637](./note10-Spring IoC注解式开发.assets/image-20261127160959637.png)

## 6.6测试一下，顺利通过

![image-20261127161046440](./note10-Spring IoC注解式开发.assets/image-20261127161046440.png)

## 6.7此时如果我们把带参构造函数注释了，就会报错，

![image-20261127161312446](./note10-Spring IoC注解式开发.assets/image-20261127161312446.png)

### 解决办法就是添加一个lombok的RequiredArgsConstructor注解

![image-20261127161407469](./note10-Spring IoC注解式开发.assets/image-20261127161407469.png)

## 6.8再次运行测试，顺利通过

![image-20261127161529845](./note10-Spring IoC注解式开发.assets/image-20261127161529845.png)

## 注意：Lombok的注入也是根据类型来注入的，如果同时存在多个相同类型的对象，就会报错，注入失败。

# 演练7.利用Resource注解(注意是Jakarta的，不是spring的)

## 注意：这个注解是按照名称注入的

## 7.1.引入Resource注解依赖，注意它一般只能够在属性和方法上面使用，也不能用在构造函数里面。

![image-20261127182022055](./note10-Spring IoC注解式开发.assets/image-20261127182022055.png)

## 7.2.新建一个bean5包，在里面新建一个ClientDao接口，里面声明一个insert方法。然后创建一个ClientDaoImpl类实现这个接口，然后新建一个ClientService类里面有一个ClientDao类型的属性clientDao，service有一个save方法调用dao里面的insert方法，然后新建一个ClientController类，里面有一个ClientService的属性clientService，controller有一个saveClient方法调用service的save方法，都不写set方法。代码如下

![image-20261127190736217](./note10-Spring IoC注解式开发.assets/image-20261127190736217.png)

![image-20261127190043961](./note10-Spring IoC注解式开发.assets/image-20261127190043961.png)

![image-20261127190102736](./note10-Spring IoC注解式开发.assets/image-20261127190102736.png)

## 7.3此时我们来获取controller的bean是可以获取的，但是他的clientService属性是null，因为我们没有做任何注入

![image-20261127190321683](./note10-Spring IoC注解式开发.assets/image-20261127190321683.png)

## 7.4注意，此时你调用service的方法，程序立马崩溃

![image-20261127190812834](./note10-Spring IoC注解式开发.assets/image-20261127190812834.png)

## 7.5解决办法就是在service类里面的dao属性上面添加@Resource注解，在控制器类的service属性上面也添加@Resource注解

![image-20261127192144308](./note10-Spring IoC注解式开发.assets/image-20261127192144308.png)

![image-20261127192225504](./note10-Spring IoC注解式开发.assets/image-20261127192225504.png)

## 7.6再次运行程序，顺利通过

![image-20261127192521985](./note10-Spring IoC注解式开发.assets/image-20261127192521985.png)

### 注意，Resource注解也支持给属性起名字，不过，他的参数是name而不是value，这个和生成bean的注解不一样，所以需要这么写

![image-20261127193134659](./note10-Spring IoC注解式开发.assets/image-20261127193134659.png)

# 演练8.spring全注解开发，（不需要配置文件）

## 8.1把001项目复制一份，改名spring-007-no-xml,然后打开pom.xml文件修改项目名称，把target文件夹删除，然后在idea工具中添加为maven项目，因为这个项目不需要spring的配置文件，我们把spring修改的配置文件删除。把测试代码删除，只留下一个空函数

![image-20261127200138061](./note10-Spring IoC注解式开发.assets/image-20261127200138061.png)

## 8.2把bean包和它里面的类删除，在org.kenny.spring包里面新建一个SpringConfiguration类和一个service子包。在子包里面新建一个CustomerService类

![image-20261127200615606](./note10-Spring IoC注解式开发.assets/image-20261127200615606.png)

## 8.3给配置类添加一个@Configuration注解和同样ComponentScan注解，里面填写你需要扫描的包。注意此时我们没有在这个类里面写任何代码

![image-20261127202437902](./note10-Spring IoC注解式开发.assets/image-20261127202437902.png)

## 8.4然后我们编写一些测试代码，注意，此时不能再使用ClassPathApplicationContext类而是要使用AnnotationConfigApplicationContext来创建context对象，运行程序顺利获取到bean，也可以调用dao中的方法

![image-20261127202754597](./note10-Spring IoC注解式开发.assets/image-20261127202754597.png)

## 8.5可以自己new一个对象放到IoC容器，比如我们创建一个Date对象

![image-20261127203912948](./note10-Spring IoC注解式开发.assets/image-20261127203912948.png)

## 8.6然后我们在测试代码里面获取这个日期对象并且输出，是没有问题的

![image-20261127204011282](./note10-Spring IoC注解式开发.assets/image-20261127204011282.png)
