# MyBatis核心配置文件详解

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <environments default="development">
        <environment id="development">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://localhost:3306/mybatis"/>
                <property name="username" value="root"/>
                <property name="password" value="root"/>
            </dataSource>
        </environment>
    </environments>
    <mappers>
        <mapper resource="CarMapper.xml"/>
        <mapper resource="CarMapper2.xml"/>
    </mappers>
</configuration>
```

* configuration：根标签，表示配置信息。
* environments：环境（多个），以“s”结尾表示复数，也就是说mybatis的环境可以配置多个数据源。
  * default属性：表示默认使用的是哪个环境，default后面填写的是environment的id。**<font style="color:#E8323C;">default的值只需要和environment的id值一致即可</font>**。
* environment：具体的环境配置（**<font style="color:#E8323C;">主要包括：事务管理器的配置 + 数据源的配置</font>**）
  * id：给当前环境一个唯一标识，该标识用在environments的default后面，用来指定默认环境的选择。
* transactionManager：配置事务管理器
  * type属性：指定事务管理器具体使用什么方式，可选值包括两个
    * **<font style="color:#E8323C;">JDBC</font>**：MyBatis自己管理事务，**<font style="color:rgb(15, 17, 21);">委托给 JDBC Connection</font>**。**<font style="color:#E8323C;">底层原理：事务开启conn.setAutoCommit(false); ...处理业务...事务提交conn.commit(); 如果在集成环境下，例如spring和mybatis集成了，spring使用</font>**<code><font style="color:rgb(15, 17, 21);background-color:rgb(235, 238, 242);">@Transactional</font></code><font style="color:rgb(15, 17, 21);"> 注解来开启、提交或回滚事务。在这种模式下，</font><code><font style="color:rgb(15, 17, 21);background-color:rgb(235, 238, 242);">JDBC</font></code><font style="color:rgb(15, 17, 21);"> 事务管理器配置会被忽略</font>
    * **<font style="color:#E8323C;">MANAGED</font>**：MyBatis不负责管理事务，将事务管理交给其他容器来负责，比如Spring、App Server等。如果没有管理事务的容器，则没有事务。**<font style="color:#E8323C;">没有事务的含义：只要执行一条DML语句，则提交一次</font>**。
* dataSource：指定数据源
  * type属性：用来指定具体使用的数据库连接池的策略，可选值包括\*\*<font style="color:#DF2A3F;">3</font>\*\*种情况
    * **<font style="color:#E8323C;">UNPOOLED</font>**：采用传统的获取连接的方式，虽然也实现javax.sql.DataSource接口，但是并没有使用池的思想。
      * property可以是：
        * driver 这是 JDBC 驱动的 Java 类全限定名。
        * url 这是数据库的 JDBC URL 地址。
        * username <font style="color:rgb(64, 64, 64);">登录数据库的用户名</font>。
        * password <font style="color:rgb(64, 64, 64);">登录数据库的密码。</font>
        * defaultTransactionIsolationLevel <font style="color:rgb(64, 64, 64);">默认的连接事务隔离级别。</font>
        * defaultNetworkTimeout  **<font style="color:rgb(15, 17, 21);">当执行 SQL 时，等待数据库服务器返回数据的最大时间，超时则抛出异常</font>**<font style="color:rgb(64, 64, 64);">（单位：毫秒）</font>
    * **<font style="color:#E8323C;">POOLED</font>**：采用传统的javax.sql.DataSource规范中的连接池，这是mybatis默认自带的连接池，性能一般，开发时用，生产环境下通常使用第三方连接池技术，例如：HikariCP、Druid等。
      * property可以是（除了包含\*\*<font style="color:#E8323C;">UNPOOLED</font>\*\*<font style="color:rgb(64, 64, 64);">中的之外</font>）：
        * poolMaximumActiveConnections **最大活动的连接数量，默认值：10**
        * poolMaximumIdleConnections **<font style="color:rgb(64, 64, 64);">最大空闲的连接数量。</font>**
        * <font style="color:rgb(64, 64, 64);">其它....</font>
    * **<font style="color:#E8323C;">JNDI</font>**：采用服务器提供的JNDI技术实现，来获取DataSource对象，不同的服务器所能拿到DataSource是不一样。如果不是web或者maven的war工程，JNDI是不能使用的。
      * property可以是（<font style="color:#E8323C;">最多只包含以下两个属性</font>）：
        * initial_context <font style="color:rgb(64, 64, 64);">这个属性用来在 InitialContext 中寻找上下文（即，initialContext.lookup(initial_context)）这是个可选属性，如果忽略，那么将会直接从 InitialContext 中寻找 data_source 属性。</font>
        * data_source <font style="color:rgb(64, 64, 64);">这是引用数据源实例位置的上下文路径。提供了 initial_context 配置时会在其返回的上下文中进行查找，没有提供时则直接在 InitialContext 中查找。</font>
* mappers：在mappers标签中可以配置多个sql映射文件的路径
* mapper：配置某个sql映射文件的路径
  * resource属性：使用相对于类路径的资源引用方式
  * url属性：使用完全限定资源定位符（URL）方式（了解即可，开发中不用）

## 1.environment

mybatis-003-configuration

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <!--默认使用开发环境-->
    <!--<environments default="dev">-->
    <!--默认使用生产环境-->
    <environments default="production">
        <!--开发环境-->
        <environment id="dev">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://localhost:3306/mybatis"/>
                <property name="username" value="root"/>
                <property name="password" value="root"/>
            </dataSource>
        </environment>
        <!--生产环境-->
        <environment id="production">
            <transactionManager type="JDBC" />
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://localhost:3306/jkweilai"/>
                <property name="username" value="root"/>
                <property name="password" value="root"/>
            </dataSource>
        </environment>
    </environments>
    <mappers>
        <mapper resource="CarMapper.xml"/>
    </mappers>
</configuration>
```

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="car">
    <insert id="insertCar">
        insert into t_car(id,car_num,brand,guide_price,produce_time,car_type) values(null,#{carNum},#{brand},#{guidePrice},#{produceTime},#{carType})
    </insert>
</mapper>
```

```java
package com.jkweilai.mybatis;

import com.jkweilai.mybatis.pojo.Car;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;

public class ConfigurationTest {

    @Test
    public void testEnvironment() throws Exception{
        // 准备数据
        Car car = new Car();
        car.setCarNum("133");
        car.setBrand("丰田霸道");
        car.setGuidePrice(50.3);
        car.setProduceTime("2020-01-10");
        car.setCarType("燃油车");

        // 一个数据库对应一个SqlSessionFactory对象
        // 两个数据库对应两个SqlSessionFactory对象，以此类推
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();

        // 使用默认数据库
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"));
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        int count = sqlSession.insert("insertCar", car);
        System.out.println("插入了几条记录：" + count);

        // 使用指定数据库
        SqlSessionFactory sqlSessionFactory1 = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"), "production");
        SqlSession sqlSession1 = sqlSessionFactory1.openSession(true);
        int count1 = sqlSession1.insert("insertCar", car);
        System.out.println("插入了几条记录：" + count1);
    }
}
```

## 2.dataSource

### 2.1 type属性的取值有3个选项

![image-20261108174140969](./note04-MyBatis核心配置文件详解.assets/image-20261108174140969.png)

### 2.2type的取值，不一样，里面的配置信息就不一样

**数据源（dataSource）**

dataSource 元素使用标准的 JDBC 数据源接口来配置 JDBC 连接对象的资源。

- 大多数 MyBatis 应用程序会按示例中的例子来配置数据源。虽然数据源配置是可选的，但如果要启用延迟加载特性，就必须配置数据源。

有三种内建的数据源类型（也就是 type="[UNPOOLED|POOLED|JNDI]"）：

**UNPOOLED**– 这个数据源的实现会每次请求时打开和关闭连接。虽然有点慢，但对那些数据库连接可用性要求不高的简单应用程序来说，是一个很好的选择。 性能表现则依赖于使用的数据库，对某些数据库来说，使用连接池并不重要，这个配置就很适合这种情形。UNPOOLED 类型的数据源仅仅需要配置以下 5 种属性：

- `driver` – 这是 JDBC 驱动的 Java 类全限定名（并不是 JDBC 驱动中可能包含的数据源类）。
- `url` – 这是数据库的 JDBC URL 地址。
- `username` – 登录数据库的用户名。
- `password` – 登录数据库的密码。
- `defaultTransactionIsolationLevel` – 默认的连接事务隔离级别。
- `defaultNetworkTimeout` – 等待数据库操作完成的默认网络超时时间（单位：毫秒）。查看 `java.sql.Connection#setNetworkTimeout()` 的 API 文档以获取更多信息。

作为可选项，你也可以传递属性给数据库驱动。只需在属性名加上“driver.”前缀即可，例如：

- `driver.encoding=UTF8`

这将通过 DriverManager.getConnection(url, driverProperties) 方法传递值为 `UTF8` 的 `encoding` 属性给数据库驱动。

**POOLED**– 这种数据源的实现利用“池”的概念将 JDBC 连接对象组织起来，避免了创建新的连接实例时所必需的初始化和认证时间。 这种处理方式很流行，能使并发 Web 应用快速响应请求。

除了上述提到 UNPOOLED 下的属性外，还有更多属性用来配置 POOLED 的数据源：

- `poolMaximumActiveConnections` – 在任意时间可存在的活动（正在使用）连接数量，默认值：10
- `poolMaximumIdleConnections` – 任意时间可能存在的空闲连接数。
- `poolMaximumCheckoutTime` – 在被强制返回之前，池中连接被检出（checked out）时间，默认值：20000 毫秒（即 20 秒）
- `poolTimeToWait` – 这是一个底层设置，如果获取连接花费了相当长的时间，连接池会打印状态日志并重新尝试获取一个连接（避免在误配置的情况下一直失败且不打印日志），默认值：20000 毫秒（即 20 秒）。
- `poolMaximumLocalBadConnectionTolerance` – 这是一个关于坏连接容忍度的底层设置， 作用于每一个尝试从缓存池获取连接的线程。 如果这个线程获取到的是一个坏的连接，那么这个数据源允许这个线程尝试重新获取一个新的连接，但是这个重新尝试的次数不应该超过 `poolMaximumIdleConnections` 与 `poolMaximumLocalBadConnectionTolerance` 之和。 默认值：3（新增于 3.4.5）
- `poolPingQuery` – 发送到数据库的侦测查询，用来检验连接是否正常工作并准备接受请求。默认是“NO PING QUERY SET”，这会导致多数数据库驱动出错时返回恰当的错误消息。
- `poolPingEnabled` – 是否启用侦测查询。若开启，需要设置 `poolPingQuery` 属性为一个可执行的 SQL 语句（最好是一个速度非常快的 SQL 语句），默认值：false。
- `poolPingConnectionsNotUsedFor` – 配置 poolPingQuery 的频率。可以被设置为和数据库连接超时时间一样，来避免不必要的侦测，默认值：0（即所有连接每一时刻都被侦测 — 当然仅当 poolPingEnabled 为 true 时适用）。

**JNDI** – 这个数据源实现是为了能在如 EJB 或应用服务器这类容器中使用，容器可以集中或在外部配置数据源，然后放置一个 JNDI 上下文的数据源引用。这种数据源配置只需要两个属性：

- `initial_context` – 这个属性用来在 InitialContext 中寻找上下文（即，initialContext.lookup(initial_context)）。这是个可选属性，如果忽略，那么将会直接从 InitialContext 中寻找 data_source 属性。
- `data_source` – 这是引用数据源实例位置的上下文路径。提供了 initial_context 配置时会在其返回的上下文中进行查找，没有提供时则直接在 InitialContext 中查找。

和其他数据源配置类似，可以通过添加前缀“env.”直接把属性传递给 InitialContext。比如：

- `env.encoding=UTF8`

这就会在 InitialContext 实例化时往它的构造方法传递值为 `UTF8` 的 `encoding` 属性。

你可以通过实现接口 `org.apache.ibatis.datasource.DataSourceFactory` 来使用第三方数据源实现：

```
public interface DataSourceFactory {
  void setProperties(Properties props);
  DataSource getDataSource();
}
```

`org.apache.ibatis.datasource.unpooled.UnpooledDataSourceFactory` 可被用作父类来构建新的数据源适配器，比如下面这段插入 C3P0 数据源所必需的代码：

```
import org.apache.ibatis.datasource.unpooled.UnpooledDataSourceFactory;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class C3P0DataSourceFactory extends UnpooledDataSourceFactory {

  public C3P0DataSourceFactory() {
    this.dataSource = new ComboPooledDataSource();
  }
}
```

为了令其工作，记得在配置文件中为每个希望 MyBatis 调用的 setter 方法增加对应的属性。 下面是一个可以连接至 PostgreSQL 数据库的例子：

```
<dataSource type="org.myproject.C3P0DataSourceFactory">
  <property name="driver" value="org.postgresql.Driver"/>
  <property name="url" value="jdbc:postgresql:mydb"/>
  <property name="username" value="postgres"/>
  <property name="password" value="root"/>
</dataSource>
```

### 测试POOLED和UNPOOLED

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <environments default="dev">
        <environment id="dev">
            <transactionManager type="JDBC"/>
            <dataSource type="UNPOOLED">
                <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://localhost:3306/mybatis"/>
                <property name="username" value="root"/>
                <property name="password" value="root"/>
            </dataSource>
        </environment>
    </environments>
    <mappers>
        <mapper resource="CarMapper.xml"/>
    </mappers>
</configuration>
```

```java
@Test
public void testDataSource() throws Exception{
    // 准备数据
    Car car = new Car();
    car.setCarNum("133");
    car.setBrand("丰田霸道");
    car.setGuidePrice(50.3);
    car.setProduceTime("2020-01-10");
    car.setCarType("燃油车");
    // 获取SqlSessionFactory对象
    SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
    SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config3.xml"));
    // 获取SqlSession对象
    SqlSession sqlSession = sqlSessionFactory.openSession(true);
    // 执行SQL
    int count = sqlSession.insert("insertCar", car);
    System.out.println("插入了几条记录：" + count);
    // 关闭会话
    sqlSession.close();
}
```

当type是UNPOOLED，控制台输出：

![1763618272873-466b6161-369f-4c92-9903-8b76c47bb1c7.png](./note04-MyBatis核心配置文件详解.assets/1763618272873-466b6161-369f-4c92-9903-8b76c47bb1c7-881759.png)

修改配置文件mybatis-config3.xml中的配置：

```xml
<dataSource type="POOLED">
```

Java测试程序不需要修改，直接执行，看控制台输出：

![1763618305280-5809c7aa-7532-4725-bb36-80566d08730a.png](./note04-MyBatis核心配置文件详解.assets/1763618305280-5809c7aa-7532-4725-bb36-80566d08730a-356893.png)

通过测试得出：UNPOOLED不会使用连接池，每一次都会新建JDBC连接对象。POOLED会使用数据库连接池。【这个连接池是mybatis自己实现的。】



### MyBatis连接池属性

type="POOLED"的时候，它的属性有哪些？

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <environments default="dev">
        <environment id="dev">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://localhost:3306/mybatis"/>
                <property name="username" value="root"/>
                <property name="password" value="root"/>
                <!--最大的活动的连接数量-->
                <property name="poolMaximumActiveConnections" value="3"/>
                <!--最多空闲数量-->
                <property name="poolMaximumIdleConnections" value="1"/>
                <!--这是一个底层设置，如果获取连接花费了相当长的时间，连接池会打印状态日志并重新尝试获取一个连接（避免在误配置的情况下一直失败且不打印日志），默认值：20000 毫秒（即 20 秒）-->
                <property name="poolTimeToWait" value="20000"/>
                <!--一个连接被应用程序借走后，最多能用多久，超时则被强制收回-->
                <property name="poolMaximumCheckoutTime" value="20000"/>
            </dataSource>
        </environment>
    </environments>
    <mappers>
        <mapper resource="CarMapper.xml"/>
    </mappers>
</configuration>
```

poolMaximumActiveConnections：最大的活动的连接数量。默认值10

poolMaximumIdleConnections：最大的空闲连接数量。默认值5

poolMaximumCheckoutTime：**<font style="color:rgb(15, 17, 21);">一个连接被应用程序借走后，最多能用多久，超时则被强制收回</font>**。默认值20秒。

poolTimeToWait：当无法获取到空闲连接时，每隔20秒打印一次日志，避免因代码配置有误，导致傻等。（时长是可以配置的）

当然，还有其他属性。对于连接池来说，以上几个属性比较重要。

**<font style="color:#DF2A3F;">最大的活动的连接数量就是连接池连接数量的上限。默认值10，如果有10个请求正在使用这10个连接，第11个请求只能等待空闲连接。</font>**

**<font style="color:#DF2A3F;">最大的空闲连接数量。默认值5，如何已经有了5个空闲连接，当第6个连接要空闲下来的时候，连接池会选择关闭该连接对象。来减少数据库的开销。</font>**

**<font style="color:#DF2A3F;">需要根据系统的并发情况，来合理调整连接池最大连接数以及最多空闲数量。充分发挥数据库连接池的性能。【可以根据实际情况进行测试，然后调整一个合理的数量。】</font>**

下图是默认配置：

![1660126871013-88f83ea4-94e9-4088-bdb4-06a4fd73866a.png](./note04-MyBatis核心配置文件详解.assets/1660126871013-88f83ea4-94e9-4088-bdb4-06a4fd73866a-463233.png)

## 3.properties

mybatis提供了更加灵活的配置，连接数据库的信息可以单独写到一个属性资源文件中，假设在类的根路径下创建jdbc.properties文件，配置如下：

```properties
jdbc.driver=com.mysql.cj.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/mybatis
```

在mybatis核心配置文件中引入并使用：

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>

    <!--引入外部属性资源文件-->
    <properties resource="jdbc.properties">
        <property name="jdbc.username" value="root"/>
        <property name="jdbc.password" value="root"/>
    </properties>

    <environments default="dev">
        <environment id="dev">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <!--${key}使用-->
                <property name="driver" value="${jdbc.driver}"/>
                <property name="url" value="${jdbc.url}"/>
                <property name="username" value="${jdbc.username}"/>
                <property name="password" value="${jdbc.password}"/>
            </dataSource>
        </environment>
    </environments>
    <mappers>
        <mapper resource="CarMapper.xml"/>
    </mappers>
</configuration>
```

编写Java程序进行测试：

```java
@Test
public void testProperties() throws Exception{
    SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
    SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config4.xml"));
    SqlSession sqlSession = sqlSessionFactory.openSession();
    Object car = sqlSession.selectOne("selectCarByCarNum");
    System.out.println(car);
}
```

**<font style="color:#E8323C;">properties两个属性：</font>**

**<font style="color:#E8323C;">resource：这个属性从类的根路径下开始加载。【常用的。】</font>**

**<font style="color:#E8323C;">url：从指定的url加载，假设文件放在d:/jdbc.properties，这个url可以写成：file:///d:/jdbc.properties。注意是三个斜杠哦。</font>**

注意：如果不知道mybatis-config.xml文件中标签的编写顺序的话，可以有两种方式知道它的顺序：

* 第一种方式：查看dtd约束文件。
* 第二种方式：通过idea的报错提示信息。【一般采用这种方式】

## 4.mapper

mapper标签用来指定SQL映射文件的路径，包含多种指定方式，这里先主要看其中两种：

第一种：resource，从类的根路径下开始加载【比url常用】

```xml
<mappers>
  <mapper resource="CarMapper.xml"/>
</mappers>
```

如果是这样写的话，必须保证类的根下有CarMapper.xml文件。

如果类的根路径下有一个包叫做test，CarMapper.xml如果放在test包下的话，这个配置应该是这样写：

```xml
<mappers>
  <mapper resource="test/CarMapper.xml"/>
</mappers>
```

**<font style="color:#DF2A3F;">重点注意事项：以上test目录需要在resources目录下新建，默认情况下在java目录下的xml文件将不会编译到</font>**<code>**<font style="color:#DF2A3F;">target/classes</font>**</code>**<font style="color:#DF2A3F;">目录下，除非pom.xml做一些配置，例如：</font>**

```xml
<build>
    <resources>
        <resource>
            <directory>src/main/resources</directory>
        </resource>
        <resource>
            <directory>src/main/java</directory>
            <includes>
                <include>**/*.xml</include>
            </includes>
        </resource>
    </resources>
</build>
```

第二种：url，从指定的url位置加载

假设CarMapper.xml文件放在d盘的根下，这个配置就需要这样写：

```xml
<mappers>
  <mapper url="file:///d:/CarMapper.xml"/>
</mappers>
```

**<font style="color:#E8323C;">mapper还有其他的指定方式，后面再看！！！</font>**

# 演练1.mybatis核心文件环境测试

## 1.1把002模块复制一份改名mybatis-003-configuration，然后把pom里面的项目名称修改一下

![image-20261108153814266](./note04-MyBatis核心配置文件详解.assets/image-20261108153814266.png)

## 1.2然后在idea工具里面在003模块的pom.xml文件上面点击右键-》添加为maven项目

![image-20261108153939279](./note04-MyBatis核心配置文件详解.assets/image-20261108153939279.png)

## 1.3此时它就变为一个maven项目了

![image-20261108154043226](./note04-MyBatis核心配置文件详解.assets/image-20261108154043226.png)

## 1.4删除多余的mapper.xml文件，删除后如下：

![image-20261108154221542](./note04-MyBatis核心配置文件详解.assets/image-20261108154221542.png)

## 1.5在mybatis-config.xml中，我们是通过environments里面的default属性来指定默认的环境。这个id必须和environments标签里面的其中一个环境的id一致，否则无法工作。如果你设置为开发环境，它就是开发环境，如果你设置为生产环境，那它就是生产环境，比如我们再添加一个环境，id为“production”

![image-20261108155304614](./note04-MyBatis核心配置文件详解.assets/image-20261108155304614.png)

## 1.6然后我们根据需求，创建一个mybatis-production数据库，然后把它t_car复制粘贴过来，删除一些数据

![image-20261108155425979](./note04-MyBatis核心配置文件详解.assets/image-20261108155425979.png)

## 1.7我们在mybatis-config.xml里面把默认环境改为生产环境

![image-20261108155836580](./note04-MyBatis核心配置文件详解.assets/image-20261108155836580.png)

## 1.8然后我们在新增的测试代码里面插入一条新纪录

![image-20261108160021419](./note04-MyBatis核心配置文件详解.assets/image-20261108160021419.png)

## 1.9运行程序，测试通过

![image-20261108160102622](./note04-MyBatis核心配置文件详解.assets/image-20261108160102622.png)

## 1.10检查数据库，注意此时是mybatis-production数据库，发现手机新增成功

![image-20261108160203809](./note04-MyBatis核心配置文件详解.assets/image-20261108160203809.png)

## 注意：在创建SqlSessionFactory的时候，如果你不指定环境，它就会使用默认环境，你也可以指定指定环境

![image-20261108160733596](./note04-MyBatis核心配置文件详解.assets/image-20261108160733596.png)

## 1.11 我们把默认的环境改会development

![image-20261108161533578](./note04-MyBatis核心配置文件详解.assets/image-20261108161533578.png)

## 1.12在测试代码中手动指定环境

![image-20261108162356194](./note04-MyBatis核心配置文件详解.assets/image-20261108162356194.png)

## 1.13 运行测试代码，顺利通过

![image-20261108162448375](./note04-MyBatis核心配置文件详解.assets/image-20261108162448375.png)

## 1.14查看数据库，发现新增成功

![image-20261108162540485](./note04-MyBatis核心配置文件详解.assets/image-20261108162540485.png)

# 演练2.mybatis连接池的一些其他配置

## 2.1配置最大活跃连接数和最大空闲连接数

![image-20261108180940560](./note04-MyBatis核心配置文件详解.assets/image-20261108180940560.png)

## 2.2 运行一个查询测试，顺利通过，说明配置没有错

![image-20261108181026247](./note04-MyBatis核心配置文件详解.assets/image-20261108181026247.png)

## 2.3 配置poolMaximumCheckoutTime和poolTimeToWait

![image-20261108184408475](./note04-MyBatis核心配置文件详解.assets/image-20261108184408475.png)

## 2.4给测试进行断点调试，发现我们设置是值有效了

![image-20261108184613777](./note04-MyBatis核心配置文件详解.assets/image-20261108184613777.png)

# 演练3.mybatis接入外部的properties配置文件

## 3.1可以把mybatis-config.xml里面数据库连接配置单独放在一个properties文件中，这样做的好处是方便客户修改，在main/resources里面新建一个jdbc.properties文件，内容如下

![image-20261108185800414](./note04-MyBatis核心配置文件详解.assets/image-20261108185800414.png)

## 3.2然后在mybatis-config.xml里面把这个jdbc.properties中文资源引入，然后就可以在下面通过key获取它里面的值，我们这里使用开发环境

![image-20261108190711740](./note04-MyBatis核心配置文件详解.assets/image-20261108190711740.png)

## 3.3.然后运行根据id来查找的测试代码，顺利获取到值

![image-20261108190913821](./note04-MyBatis核心配置文件详解.assets/image-20261108190913821.png)

### 注意：properties标签可以用resource属性来加载外部的配置文件，也可以在标签里面配置属性，一些我们不希望用户修改的属性可以配置在这里

![image-20261108191359581](./note04-MyBatis核心配置文件详解.assets/image-20261108191359581.png)

### 此时jdbc.properties只需要配置3个key即可

![image-20261108191452521](./note04-MyBatis核心配置文件详解.assets/image-20261108191452521.png)

### 如果你的配置文件不是放在main/resources文件夹中，比如放在java文件夹里面，默认编译的时候不会把他们一起编译进来。此时你需要在pom.xml里面配置一下

![image-20261108192332159](./note04-MyBatis核心配置文件详解.assets/image-20261108192332159.png)