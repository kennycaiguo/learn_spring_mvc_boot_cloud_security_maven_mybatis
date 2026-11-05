# 2.MyBatis入门程序

![1757681056420-39d1bc52-55fe-4f3b-8183-b4d7ba79b166.jpeg](./note02-Mybatis入门程序.assets/1757681056420-39d1bc52-55fe-4f3b-8183-b4d7ba79b166-925450.jpeg)

MyBatis 英文文档地址：<https://mybatis.org/mybatis-3/>

MyBatis 中文文档地址：<https://mybatis.org/mybatis-3/zh_CN/index.html>

## MyBatis 源码下载

字节码 jar 包不需要下载，我们用 Maven 引入依赖即可。需要源码的可以从这里下载。<https://github.com/mybatis/mybatis-3>

![1763607495012-be877c81-7b0b-4872-a081-47f322629dde.png](./note02-Mybatis入门程序.assets/1763607495012-be877c81-7b0b-4872-a081-47f322629dde-331054.png)

![1763607545153-53bc5340-2312-456d-8552-986ba054c2f9.png](./note02-Mybatis入门程序.assets/1763607545153-53bc5340-2312-456d-8552-986ba054c2f9-742163.png)

## MyBatis入门程序开发步骤

### 初始化数据库表

\*\*创建数据库 \*\*<code>**mybatis**</code>

![1743559916175-57d4d771-3de7-4994-9364-6121af83577d.png](./note02-Mybatis入门程序.assets/1743559916175-57d4d771-3de7-4994-9364-6121af83577d-972243.png)

**执行 SQL 脚本：**

```sql
DROP TABLE IF EXISTS `t_car`;
CREATE TABLE `t_car`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键自增',
  `car_num` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '车牌号',
  `brand` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '品牌',
  `guide_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '指导价：万',
  `produce_time` char(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '出厂日期',
  `car_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '汽车类型：燃油车 电车 氢能源',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

INSERT INTO `t_car` VALUES (1, '京A88888', '宝驹535', 20.00, '2025-10-11', '燃油车');
INSERT INTO `t_car` VALUES (2, '京A66666', '劳斯莱斯泡影', 300.00, '2025-10-12', '新能源');
```

### IDEA 创建项目及基本设置

1. 创建空工程：`mybatis`
2. 设置 JDK 版本和编译器版本
3. 设置项目的所有字符编码方式为 UTF-8
4. 设置 Maven
5. 创建普通 `Java Maven` 模块：`mybatis-001-introduction`（不需要添加 web 支持，学 MyBatis 就像学 JDBC 一样）
6. `pom.xml`设置打包方式 `jar`
7. 引入 mybatis 依赖、mysql 驱动依赖

```xml
<!--mybatis核心依赖-->
<dependency>
    <groupId>org.mybatis</groupId>
    <artifactId>mybatis</artifactId>
    <version>3.5.19</version>
</dependency>
<!--mysql驱动依赖-->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.24</version>
    <scope>runtime</scope>
</dependency>
```

### 编写 mybatis 核心配置文件

在 `resources` 根目录下新建 `mybatis-config.xml` 配置文件（可以参考mybatis手册拷贝）

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
        <!--sql映射文件创建好之后，需要将该文件路径配置到这里-->
        <mapper resource=""/>
    </mappers>
</configuration>
```

配置文件的名字和存放位置随意，无特定要求。但一般叫做 `mybatis-config.xml`，一般存放在类根路径下。

### 编写 SqlMapper.xml 配置文件

在resources根目录下新建CarMapper.xml配置文件，在该文件中编写 SQL 语句（可以参考mybatis手册拷贝）

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<!--namespace先随意写一个-->
<mapper namespace="car">
    <!--insert sql：保存一个汽车信息-->
    <insert id="insertCar">
        insert into t_car
            (id,car_num,brand,guide_price,produce_time,car_type) 
        values
            (null,'京A66688','丰田mirai',40.30,'2025-10-05','氢能源')
    </insert>
</mapper>
```

1. **<font style="color:#E8323C;">sql语句最后结尾可以不写“;”</font>**
2. CarMapper.xml文件的名字不是固定的。可以使用其它名字。
3. CarMapper.xml文件的位置也是随意的。这里选择放在resources根下，相当于放到了类的根路径下。
4. <font style="color:#F5222D;">将CarMapper.xml文件路径配置到mybatis-config.xml：</font>

```xml
<mapper resource="CarMapper.xml"/>
```

### 编写 MyBatis 执行程序

编写MyBatisIntroductionTest代码

```java
package com.jkweilai.mybatis;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;

public class MyBatisIntroductionTest {
    public static void main(String[] args) {
        // 1. 创建SqlSessionFactoryBuilder对象
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        // 2. 创建SqlSessionFactory对象
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(is);
        // 3. 创建SqlSession对象
        SqlSession sqlSession = sqlSessionFactory.openSession();
        // 4. 执行sql
        int count = sqlSession.insert("insertCar"); // 这个"insertCar"必须是sql的id
        System.out.println("插入几条数据：" + count);
        // 5. 提交（mybatis默认采用的事务管理器是JDBC，默认是不提交的，需要手动提交。）
        sqlSession.commit();
        // 6. 关闭资源（只关闭是不会提交的）
        sqlSession.close();
    }
}
```

默认采用的事务管理器是：JDBC。JDBC事务默认是不提交的，需要手动提交。

## 改良获取输入流的代码

之前是这样写的：

```java
InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("mybatis-config.xml");
```

借助 MyBatis 中提供的 `Resources`，代码可以优化，但有前提：要求 `mybatis-config.xml`必须放在类路径当中：

```java
InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
```

## 第一个比较完整的代码写法

```java
package com.jkweilai.mybatis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;

/**
 * 比较完整的第一个mybatis程序写法
 * @author 老杜
 */
public class MyBatisCompleteCodeTest {
    public static void main(String[] args) {
        SqlSession sqlSession = null;
        try {
            // 1.创建SqlSessionFactoryBuilder对象
            SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
            // 2.创建SqlSessionFactory对象
            SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"));
            // 3.创建SqlSession对象
            sqlSession = sqlSessionFactory.openSession();
            // 4.执行SQL
            int count = sqlSession.insert("insertCar");
            System.out.println("更新了几条记录：" + count);
            // 5.提交
            sqlSession.commit();
        } catch (Exception e) {
            // 回滚
            if (sqlSession != null) {
                sqlSession.rollback();
            }
            e.printStackTrace();
        } finally {
            // 6.关闭
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
    }
}
```

以上代码也可以改良为：`try-with-resource`语法。

## 引入JUnit

写测试程序我们要频繁创建新的类，编写 main 方法，为了解决这个问题，引入 JUnit：

```xml
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter</artifactId>
    <version>5.10.2</version>
    <scope>test</scope>
</dependency>
```

编写一个测试用例，来测试insertCar业务

```java
package com.jkweilai.mybatis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;

public class CarMapperTest {
    
    @Test
    public void testInsertCar(){
        SqlSession sqlSession = null;
        try {
            // 1.创建SqlSessionFactoryBuilder对象
            SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
            // 2.创建SqlSessionFactory对象
            SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"));
            // 3.创建SqlSession对象
            sqlSession = sqlSessionFactory.openSession();
            // 4.执行SQL
            int count = sqlSession.insert("insertCar");
            System.out.println("更新了几条记录：" + count);
            // 5.提交
            sqlSession.commit();
        } catch (Exception e) {
            // 回滚
            if (sqlSession != null) {
                sqlSession.rollback();
            }
            e.printStackTrace();
        } finally {
            // 6.关闭
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
    }
}
```

## 启用标准日志组件

为了看清楚mybatis执行的具体sql，启用标准日志组件：只需要在mybatis-config.xml文件中添加以下配置：【可参考mybatis手册】

```xml
<settings>
  <setting name="logImpl" value="STDOUT_LOGGING" />
</settings>
```

<font style="color:rgb(15, 17, 21);">它就是 MyBatis </font>**<font style="color:rgb(15, 17, 21);">内置</font>**<font style="color:rgb(15, 17, 21);">的一个“日志打印器”，而不是一个独立的第三方日志框架。它本质上就是一个“让 MyBatis 用 </font><code><font style="color:rgb(15, 17, 21);background-color:rgb(235, 238, 242);">System.out.println</font></code><font style="color:rgb(15, 17, 21);"> 把 SQL 打印到控制台”的快速开关，而不是一个功能完整的日志框架。如果在开发中，想要快速打印 SQL 日志，这是一个最便捷的方案。当然，如果你需要集成第三方真正的日志框架，那也超级简单，只需要把日志框架添加到 classpath 中即可，mybatis 运行时会自动检查 classpath 中是否存在具体的日志框架，如果存在则直接使用你提供的。日志框架的查找顺序官方文档有说明：</font>

![1782741619907-2992578e-d34d-4dca-8108-2f0fa790c543.png](./note02-Mybatis入门程序.assets/1782741619907-2992578e-d34d-4dca-8108-2f0fa790c543-654735.png)

## MyBatis工具类SqlSessionUtil的封装

每一次获取SqlSession对象代码太繁琐，封装一个工具类

```java
package com.jkweilai.mybatis.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 * MyBatis工具类
 *
 * @author 老杜
 */
public class SqlSessionUtil {
    private static SqlSessionFactory sqlSessionFactory;

    /**
     * 类加载时初始化sqlSessionFactory对象
     */
    static {
        try {
            SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
            sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 每调用一次openSession()可获取一个新的会话，该会话支持自动提交。
     *
     * @return 新的会话对象
     */
    public static SqlSession openSession() {
        return sqlSessionFactory.openSession(true);
    }
}
```

测试工具类，将testInsertCar()改造

```java
@Test
public void testInsertCar(){
    SqlSession sqlSession = SqlSessionUtil.openSession();
    // 执行SQL
    int count = sqlSession.insert("insertCar");
    System.out.println("插入了几条记录:" + count);
    sqlSession.close();
}
```

## idea配置文件模板

mybatis-config.xml和SqlMapper.xml文件可以在IDEA中提前创建好模板，以后通过模板创建配置文件。

![1660629382298-df2d0e70-d8ce-4ae6-9f19-e74923adf121.png](./note02-Mybatis入门程序.assets/1660629382298-df2d0e70-d8ce-4ae6-9f19-e74923adf121-526815.png)

# 项目演练

## 1.打开navicat，新建一个mybatis数据库

![image-20261104130720680](./note02-Mybatis入门程序.assets/image-20261104130720680.png)

## 2.然后新建一个数据表t_car，sql参考上面笔记。

![image-20261104132014404](./note02-Mybatis入门程序.assets/image-20261104132014404.png)

## 3.打开idea，新建一个空工程，起名mybatis-project

![image-20261104133114691](./note02-Mybatis入门程序.assets/image-20261104133114691.png)

![image-20261104133136551](./note02-Mybatis入门程序.assets/image-20261104133136551.png)

## 4.点击文件-项目结构，现在jdk版本，语言级别选择21，点击应用然后点击确定

![image-20261104133404176](./note02-Mybatis入门程序.assets/image-20261104133404176.png)

## 5.首先，我们把上一次配置的私服注释，还是使用本地仓库

![image-20261104134231738](./note02-Mybatis入门程序.assets/image-20261104134231738.png)

![image-20261104135206458](./note02-Mybatis入门程序.assets/image-20261104135206458.png)

![image-20261104134622211](./note02-Mybatis入门程序.assets/image-20261104134622211.png)

## 6.然后点击文件设置，配置一下maven，把它配置为我们自己安装的maven，还修改配置文件的路径为我们的maven里面的conf里面的配置文件路径，然后点击确定。

![image-20261104134348738](./note02-Mybatis入门程序.assets/image-20261104134348738.png)

## 7.新建一个模块，起名：mybatis-001-introduction，这里暂时不需要web支持

![image-20261104134854394](./note02-Mybatis入门程序.assets/image-20261104134854394.png)

## 8.然后我们需要配置依赖，注意我的mysql版本的8.4.7的所以我使用的mysql启动的8.4.0

![image-20261104145235788](./note02-Mybatis入门程序.assets/image-20261104145235788.png)

![image-20261104145307082](./note02-Mybatis入门程序.assets/image-20261104145307082.png)

## 9.创建mybatis的配置文件(2个)mybatis-config.xml，需要在resource文件夹里面编写,

![image-20261104150033827](./note02-Mybatis入门程序.assets/image-20261104150033827.png)

## 然后，还需要创建一个SqlMapper.xml 配置文件，这个文件的文件名需要用我们要操作的类的类名开头，这里改为CarMapper.xml

![image-20261104152511730](./note02-Mybatis入门程序.assets/image-20261104152511730.png)

## 然后需要把这个mapper文件添加到mybatis-config.xml的mapper配置里面

![image-20261104153714179](./note02-Mybatis入门程序.assets/image-20261104153714179.png)

## 10.在java文件夹里面新建一个包org.kenny.mybatis包，里面新建一个类FirstMybatisCode,代码如下

![image-20261104163232410](./note02-Mybatis入门程序.assets/image-20261104163232410.png)

### 允许程序，发现成功了，

![image-20261104163312137](./note02-Mybatis入门程序.assets/image-20261104163312137.png)

### 刷新数据库，的确添加了一条新数据

![image-20261104163355945](./note02-Mybatis入门程序.assets/image-20261104163355945.png)

### 注意，使用mybatis时，一定要手动提交，否则数据不会保存

# 演练2.用try.catch语句来实现一遍

## 2.1新建另外一个类：FirstMybatisBetterCode，

![image-20261105121021647](./note02-Mybatis入门程序.assets/image-20261105121021647.png)

## 代码如下

```
package org.kenny.mybatis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;

public class FirstMybatisBetterCode {
    public static void main(String[] args) {
        //factory
        try(InputStream in = Resources.getResourceAsStream("mybatis-config.xml");){
            SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
            try (SqlSession session = factory.openSession()) {
                int count = session.insert("insertCar");
                System.out.println(count);
                //提交
                session.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

```

## 2.2 为了方便产生，我们回到CarMapper.xml里面，修改一下数据

![image-20261105121312950](./note02-Mybatis入门程序.assets/image-20261105121312950.png)

## 2.3 运行程序成功

![image-20261105114839020](./note02-Mybatis入门程序.assets/image-20261105114839020.png)

## 2.4查看数据库，发现的确新增了一条数据

![image-20261105114920889](./note02-Mybatis入门程序.assets/image-20261105114920889.png)

## 2.5注意: Mybatis提供的Resources类查找配置文件也是从类的根路径（也就是maven项目里面main/resources）开始的，假如我们在里面新建一个conf文件夹，里面有一个MybatisConf.xml文件，我们如何加载这个配置文件呢

![image-20261105121114869](./note02-Mybatis入门程序.assets/image-20261105121114869.png)

## 2.6.我们需要这么写，可以看到程序执行成功

## ![image-20261105121205535](./note02-Mybatis入门程序.assets/image-20261105121205535.png)

### 可以看出，mybatis核心配置文件的名称是可以随便写的，存放的位置也是比较随便的。但是最好放在resources文件夹里面。你甚至可以把这个文件放在项目的外面，然后用InputStream手动加载，也是可以的。不过不推荐，非常不好。

![image-20261105120607854](./note02-Mybatis入门程序.assets/image-20261105120607854.png)

## 注意：放在try()的括号里面的对象必须是实现了Closable接口的类的对象，否则无法放在这个括号里面

# 演练3，继续优化，引入JUnit依赖

## 3.1在pom.xml文件里面添加junit的gav坐标，这里使用5.12.2版本

![image-20261105121943166](./note02-Mybatis入门程序.assets/image-20261105121943166.png)

## 3.2在test文件夹里面新建一个包org.kenny.mybatis

![image-20261105122115645](./note02-Mybatis入门程序.assets/image-20261105122115645.png)

## 3.3然后创建一个MybatisTest类，

![image-20261105122602877](./note02-Mybatis入门程序.assets/image-20261105122602877.png)

## 内容如下

```
package org.kenny.mybatis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

public class MybatisTest {
    @Test
    public void testFirst() {
        try(InputStream in = Resources.getResourceAsStream("conf/MybatisConf.xml");){
            SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
            try (SqlSession session = factory.openSession()) {
                int count = session.insert("insertCar");
                System.out.println(count);
                //提交
                session.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
         }
    }
}

```

## 3.4我们把CarMapper的数据也修改一下

![image-20261105122715261](./note02-Mybatis入门程序.assets/image-20261105122715261.png)

## 3.5运行测试，发现通过了，

![image-20261105122811733](./note02-Mybatis入门程序.assets/image-20261105122811733.png)

## 3.6查看数据库，发现数据成功添加

![image-20261105122901906](./note02-Mybatis入门程序.assets/image-20261105122901906.png)

# 演练4.启用日志工具

## 4.1为了方便学习，我们还是使用Resources里面的mybatis-config.xml作为核心配置文件，把conf和里面的文件删除

![image-20261105123549526](./note02-Mybatis入门程序.assets/image-20261105123549526.png)

## 4.2.其实mybatis自带一个日志记录工具，我们只需要把它打开即可，在mybatis-config.xml里面添加下面的配置，注意不能随便放，标签有顺序的，否则报错。

![image-20261105124155875](./note02-Mybatis入门程序.assets/image-20261105124155875.png)

### mybatis日志工具的好处就是可以输出相关的sql语句，方便我们调试

## 4.3修改运行CarMapper.xml的数据

![image-20261105124438996](./note02-Mybatis入门程序.assets/image-20261105124438996.png)

## 4.4运行一下我们的测试程序，效果如下

![image-20261105124619592](./note02-Mybatis入门程序.assets/image-20261105124619592.png)

# 演练5.封装SqlSessionUtil工具类

## 5.1新建一个util包，在里面新建一个SqlSessionUtil类

![image-20261105132333336](./note02-Mybatis入门程序.assets/image-20261105132333336.png)

## 5.2然后外面在测试类里面新建一个方法来测试

![image-20261105132820617](./note02-Mybatis入门程序.assets/image-20261105132820617.png)

## 5.3修改一下CarMapper.xml的代码

![image-20261105132859532](./note02-Mybatis入门程序.assets/image-20261105132859532.png)

## 5.4运行测试，通过了

![image-20261105133000252](./note02-Mybatis入门程序.assets/image-20261105133000252.png)

## 5.5查看数据库，发现数据添加成功

![image-20261105133053088](./note02-Mybatis入门程序.assets/image-20261105133053088.png)

# 演练6.在idea在配置mybatis-config.xml和SqlMapper.xml模板

## 目的是方便我们在新项目在创建两个配置文件

## 6.1打开idea工具，点击文件-设置-》编辑器-》文件和代码模板

![image-20261105133856260](./note02-Mybatis入门程序.assets/image-20261105133856260.png)

## 6.2点击右边面板里面的+，右边就会有新建文件的面板，填写号文件名和扩展名，在编辑框中输入模板代码（从这里拷贝，然后做一下修改：https://mybatis.org/mybatis-3/zh_CN/getting-started.html）

![image-20261105134733786](./note02-Mybatis入门程序.assets/image-20261105134733786.png)

## 6.3然后点击确定.模板设置好了，然后这么使用呢？

## 6.4 在你需要创建的地方点击右键-》新建，就会看到我们的模板文件

![image-20261105135036508](./note02-Mybatis入门程序.assets/image-20261105135036508.png)

## 6.5点击一下，就会生成这个mybatis-config.xml文件

![image-20261105135204515](./note02-Mybatis入门程序.assets/image-20261105135204515.png)

## 6.6.我们还需要创建一个SqlMapper.xml文件，使用类的操作方法，注意因为这里的mapper文件的文件名会改变，我们在文件名一栏把它留空，然后添加最基本的代码

![image-20261105140347541](./note02-Mybatis入门程序.assets/image-20261105140347541.png)

## 6.7然后在需要使用这个配置的地方点击右键-》新建，就会看到这个配置

![image-20261105140831121](./note02-Mybatis入门程序.assets/image-20261105140831121.png)

## 6.8点击一下，此时就会弹出一个窗口要求你输入文件名，我们输入CarMapper.xml，点击确定

![image-20261105141003302](./note02-Mybatis入门程序.assets/image-20261105141003302.png)

## 6.9，此时就会生成一个CarMapper.xml文件，当然，只有骨架代码，因为这个文件里面的代码是需要经常修改的

![image-20261105141122713](./note02-Mybatis入门程序.assets/image-20261105141122713.png)

### 当然，test里面配置是不好的，我们把他们删除

# 本章学习完毕
