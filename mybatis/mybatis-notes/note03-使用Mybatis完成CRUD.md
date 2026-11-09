# 3.使用MyBatis完成CRUD

创建module（Maven的普通Java模块）：mybatis-002-crud

## insert

1. 在编写 SQL 语句时，将值写到配置文件中肯定是不行的，因为数据是变化的。
2. 可以使用 `#{}`占位符
3. 在 Java 程序中可以通过 Map 集合给 `#{}`传值，也可以通过普通 Java 对象传值。
4. Map 传值时：`#{}`中编写 `key`，如果 `key`不存在则插入数据库中的数据是 `NULL`
5. 普通 Java 对象传值时：`#{}`中编写对象属性名（底层会调用该属性的 getter 方法）

### 使用 Map 对象传值

分析以下SQL映射文件中SQL语句存在的问题

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<!--namespace先随便写-->
<mapper namespace="car">
    <insert id="insertCar">
        insert into t_car(car_num,brand,guide_price,produce_time,car_type) values('103', '奔驰E300L', 50.3, '2022-01-01', '燃油车')
    </insert>
</mapper>
```

存在的问题是：SQL语句中的值不应该写死，值应该是用户提供的。之前的JDBC代码是这样写的：

```java
// JDBC中使用 ? 作为占位符。那么MyBatis中会使用什么作为占位符呢？
String sql = "insert into t_car(car_num,brand,guide_price,produce_time,car_type) values(?,?,?,?,?)";
// ......
// 给 ? 传值。那么MyBatis中应该怎么传值呢？
ps.setString(1,"103");
ps.setString(2,"奔驰E300L");
ps.setDouble(3,50.3);
ps.setString(4,"2022-01-01");
ps.setString(5,"燃油车");
```

在MyBatis中可以这样做：

**<font style="color:#E8323C;">在Java程序中，将数据放到Map集合中</font>**

**<font style="color:#E8323C;">在sql语句中使用 #{map集合的key} 来完成传值，#{} 等同于JDBC中的 ? ，#{}就是占位符</font>**

Java程序这样写：

```java
package com.jkweilai.mybatis;

import com.jkweilai.mybatis.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 测试MyBatis的CRUD
 * @author 老杜
 */
public class CarMapperTest {
    @Test
    public void testInsertCar(){
        // 准备数据
        Map<String, Object> map = new HashMap<>();
        map.put("k1", "103");
        map.put("k2", "奔驰E300L");
        map.put("k3", 50.3);
        map.put("k4", "2020-10-01");
        map.put("k5", "燃油车");
        // 获取SqlSession对象
        SqlSession sqlSession = SqlSessionUtil.openSession();
        // 执行SQL语句（使用map集合给sql语句传递数据）
        int count = sqlSession.insert("insertCar", map);
        System.out.println("插入了几条记录：" + count);
    }
}
```

SQL语句这样写：

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<!--namespace先随便写-->
<mapper namespace="car">
    <insert id="insertCar">
        insert into t_car(car_num,brand,guide_price,produce_time,car_type) values(#{k1},#{k2},#{k3},#{k4},#{k5})
    </insert>
</mapper>
```

**<font style="color:#E8323C;">#{} 的里面必须填写map集合的key，不能随便写。</font>**

如果#{}里写的是map集合中不存在的key会有什么问题？

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="car">
    <insert id="insertCar">
        insert into t_car(car_num,brand,guide_price,produce_time,car_type) values(#{kk},#{k2},#{k3},#{k4},#{k5})
    </insert>
</mapper>
```

运行程序：

![1763613779493-46159fd2-448c-4c2e-9006-8037d463e476.png](./note03-使用Mybatis完成CRUD.assets/1763613779493-46159fd2-448c-4c2e-9006-8037d463e476-434494.png)

![1659666614107-2df4bd58-105b-4b76-881d-e1d19b4d4b98.png](./note03-使用Mybatis完成CRUD.assets/1659666614107-2df4bd58-105b-4b76-881d-e1d19b4d4b98-253947.png)

通过测试，看到程序并没有报错。正常执行。不过 #{kk} 的写法导致无法获取到map集合中的数据，最终导致数据库表car_num插入了NULL。

### 使用普通 Java 对象传值

在以上sql语句中，可以看到#{k1} #{k2} #{k3} #{k4} #{k5}的可读性太差，为了增强可读性，我们可以将Java程序做如下修改：

```java
Map<String, Object> map = new HashMap<>();
// 让key的可读性增强
map.put("carNum", "103");
map.put("brand", "奔驰E300L");
map.put("guidePrice", 50.3);
map.put("produceTime", "2020-10-01");
map.put("carType", "燃油车");
```

SQL语句做如下修改，这样可以增强程序的可读性：

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="car">
    <insert id="insertCar">
        insert into t_car(car_num,brand,guide_price,produce_time,car_type) values(#{carNum},#{brand},#{guidePrice},#{produceTime},#{carType})
    </insert>
</mapper>
```

运行程序，查看数据库表：

![1659667098369-a13a16ec-6f02-44f2-85b7-8834b9ca161b.png](./note03-使用Mybatis完成CRUD.assets/1659667098369-a13a16ec-6f02-44f2-85b7-8834b9ca161b-404608.png)

使用Map集合可以传参，那使用\*\*<font style="color:#E8323C;">pojo</font>\*\*（简单普通的java对象）可以完成传参吗？测试一下：

* 第一步：定义一个pojo类Car，提供相关属性。

```java
package com.jkweilai.mybatis.pojo;

/**
 * POJOs，简单普通的Java对象。封装数据用的。
 * @author 老杜
 */
public class Car {
    private Long id;
    private String carNum;
    private String brand;
    private Double guidePrice;
    private String produceTime;
    private String carType;

    // setter getter constructor toString...
}
```

* 第二步：Java程序

```java
@Test
public void testInsertCarByPOJO(){
    // 创建POJO，封装数据
    Car car = new Car();
    car.setCarNum("103");
    car.setBrand("奔驰C200");
    car.setGuidePrice(33.23);
    car.setProduceTime("2020-10-11");
    car.setCarType("燃油车");
    // 获取SqlSession对象
    SqlSession sqlSession = SqlSessionUtil.openSession();
    // 执行SQL，传数据
    int count = sqlSession.insert("insertCarByPOJO", car);
    System.out.println("插入了几条记录" + count);
}
```

* 第三步：SQL语句

```xml
<insert id="insertCarByPOJO">
  <!--#{} 里写的是POJO的属性名-->
  insert into t_car(car_num,brand,guide_price,produce_time,car_type) values(#{carNum},#{brand},#{guidePrice},#{produceTime},#{carType})
</insert>
```

* 运行程序，查看数据库表：

![1659668224378-2070e0f4-8252-4978-8204-bb247d172152.png](./note03-使用Mybatis完成CRUD.assets/1659668224378-2070e0f4-8252-4978-8204-bb247d172152-790751.png)

**<font style="color:#DF2A3F;">直接告诉大家一个结论：</font>**<code>**<font style="color:#DF2A3F;">#{}</font>**</code>**<font style="color:#DF2A3F;">里写什么？</font>**

* 假如我们写的是 `#{name}`，那么 mybatis 会先调用 `getName()`方法来获取数据，如果没有 `getName()`方法，则去对象中找有没有 `name`字段，如果有 `name`字段，则直接获取该字段的值。如果没有 `getName()`方法，类中也没有 `name`字段，最终会报错。

### parameterType 属性

注意：其实传参数的时候有一个属性parameterType，这个属性用来指定传参的数据类型，不过这个属性是可以省略的

```xml
<insert id="insertCar" parameterType="java.util.Map">
  insert into t_car(car_num,brand,guide_price,produce_time,car_type) values(#{carNum},#{brand},#{guidePrice},#{produceTime},#{carType})
</insert>

<insert id="insertCarByPOJO" parameterType="com.jkweilai.mybatis.pojo.Car">
  insert into t_car(car_num,brand,guide_price,produce_time,car_type) values(#{carNum},#{brand},#{guidePrice},#{produceTime},#{carType})
</insert>
```

## delete

需求：根据car_num进行删除。

SQL语句这样写：

```xml
<delete id="deleteByCarNum">
  delete from t_car where car_num = #{SuiBianXie}
</delete>
```

Java程序这样写：

```java
@Test
public void testDeleteByCarNum(){
    // 获取SqlSession对象
    SqlSession sqlSession = SqlSessionUtil.openSession();
    // 执行SQL语句
    int count = sqlSession.delete("deleteByCarNum", "102");
    System.out.println("删除了几条记录：" + count);
}
```

**<font style="color:#E8323C;">注意：当占位符只有一个的时候，#{} 里面的内容可以随便写。</font>**

## update

需求：修改id=34的Car信息，car_num为102，brand为比亚迪汉，guide_price为30.23，produce_time为2018-09-10，car_type为电车

SQL语句如下：

```xml
<update id="updateCarByPOJO">
  update t_car set 
    car_num = #{carNum}, brand = #{brand}, 
    guide_price = #{guidePrice}, produce_time = #{produceTime}, 
    car_type = #{carType} 
  where id = #{id}
</update>
```

Java代码如下：

```java
    @Test
    public void testUpdateCarByPOJO(){
        // 准备数据
        Car car = new Car();
        car.setId(34L);
        car.setCarNum("102");
        car.setBrand("比亚迪汉");
        car.setGuidePrice(30.23);
        car.setProduceTime("2018-09-10");
        car.setCarType("电车");
        // 获取SqlSession对象
        SqlSession sqlSession = SqlSessionUtil.openSession();
        // 执行SQL语句
        int count = sqlSession.update("updateCarByPOJO", car);
        System.out.println("更新了几条记录：" + count);
    }
```

当然了，如果使用\*\*<font style="color:#E8323C;">map</font>\*\*传数据也是可以的。

## select

select语句和其它语句不同的是：查询会有一个结果集。

1. 查询结果集要封装成什么对象，你需要通过 `resultType`属性来告知 mybatis 框架，如果未指定 `resultType`则会报错。
2. 查询结果的列名需要和 Java 对象的属性名对应上，如果对应不上则 Java 对象的属性不会赋值。（只是赋系统默认值）
3. 如何让查询结果列名和 Java 对象的属性名对应上，可以使用 `as`关键字起别名。

### 查询一条数据

需求：查询id为1的Car信息

SQL语句如下：

```xml
<select id="selectCarById">
  select * from t_car where id = #{id}
</select>
```

Java程序如下：

```java
@Test
public void testSelectCarById(){
    // 获取SqlSession对象
    SqlSession sqlSession = SqlSessionUtil.openSession();
    // 执行SQL语句
    Object car = sqlSession.selectOne("selectCarById", 1);
    System.out.println(car);
}
```

运行结果如下：

```java
### Error querying database.  Cause: org.apache.ibatis.executor.ExecutorException: 
    A query was run and no Result Maps were found for the Mapped Statement 'car.selectCarById'.  【翻译】：对于一个查询语句来说，没有找到查询的结果映射。
    It's likely that neither a Result Type nor a Result Map was specified.						 【翻译】：很可能既没有指定结果类型，也没有指定结果映射。
```

以上的异常大致的意思是：对于一个查询语句来说，你需要指定它的“结果类型”或者“结果映射”。

所以说，你想让mybatis查询之后返回一个Java对象的话，至少你要告诉mybatis返回一个什么类型的Java对象，可以在<select>标签中添加resultType属性，用来指定查询要转换的类型：

```xml
<select id="selectCarById" resultType="com.jkweilai.mybatis.pojo.Car">
  select * from t_car where id = #{id}
</select>
```

运行结果：

![1763614261881-c9304f9e-7481-43f5-a3b8-8801ce957802.png](./note03-使用Mybatis完成CRUD.assets/1763614261881-c9304f9e-7481-43f5-a3b8-8801ce957802-459545.png)

运行后之前的异常不再出现了，这说明添加了resultType属性之后，解决了之前的异常，可以看出resultType是不能省略的。

仔细观察控制台的日志信息，不难看出，结果查询出了一条。并且每个字段都查询到值了：Row: 1, 100, 宝马520Li, 41.00, 2022-09-01, 燃油车

但是奇怪的是返回的Car对象，只有id和brand两个属性有值，其它属性的值都是null，这是为什么呢？我们来观察一下查询结果列名和Car类的属性名是否能一一对应：

查询结果集的列名：id, car_num, brand, guide_price, produce_time, car_type

Car类的属性名：id, carNum, brand, guidePrice, produceTime, carType

通过观察发现：只有id和brand是一致的，其他字段名和属性名对应不上，这是不是导致null的原因呢？我们尝试在sql语句中使用as关键字来给查询结果列名起别名试试：

```xml
<select id="selectCarById" resultType="com.jkweilai.mybatis.pojo.Car">
  select 
    id, car_num as carNum, brand, guide_price as guidePrice, produce_time as produceTime, car_type as carType 
  from 
    t_car 
  where 
    id = #{id}
</select>
```

运行结果如下：

![1659684140382-c8d05a79-7906-4a8f-8301-3cd8097b3eff.png](./note03-使用Mybatis完成CRUD.assets/1659684140382-c8d05a79-7906-4a8f-8301-3cd8097b3eff-717242.png)

通过测试得知，如果当查询结果的字段名和java类的属性名对应不上的话，可以采用as关键字起别名，**<font style="color:#E8323C;">当然还有其它解决方案，我们后面再看</font>**。

### 查询多条数据

查询结果是多条记录时，`resultType`不能设置为 `java.util.List`，需要设置为 `List`集合中元素的类型。

需求：查询所有的Car信息。

SQL语句如下：

```xml
<!--虽然结果是List集合，但是resultType属性需要指定的是List集合中元素的类型。-->
<select id="selectCarAll" resultType="com.jkweilai.mybatis.pojo.Car">
  <!--记得使用as起别名，让查询结果的字段名和java类的属性名对应上。-->
  select
    id, car_num as carNum, brand, guide_price as guidePrice, produce_time as produceTime, car_type as carType
  from
    t_car
</select>
```

Java代码如下：

```java
@Test
public void testSelectCarAll(){
    // 获取SqlSession对象
    SqlSession sqlSession = SqlSessionUtil.openSession();
    // 执行SQL语句
    List<Object> cars = sqlSession.selectList("selectCarAll");
    // 输出结果
    cars.forEach(car -> System.out.println(car));
}
```

## SQL Mapper的namespace

在SQL Mapper配置文件中<mapper>标签的namespace属性可以翻译为命名空间，这个命名空间主要是为了防止sqlId冲突的。

创建CarMapper2.xml文件，代码如下：

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="car2">
    <select id="selectCarAll" resultType="com.jkweilai.mybatis.pojo.Car">
        select
            id, car_num as carNum, brand, guide_price as guidePrice, produce_time as produceTime, car_type as carType
        from
            t_car
    </select>
</mapper>
```

不难看出，CarMapper.xml和CarMapper2.xml文件中都有 id="selectCarAll"

将CarMapper2.xml配置到mybatis-config.xml文件中。

```xml
<mappers>
  <mapper resource="CarMapper.xml"/>
  <mapper resource="CarMapper2.xml"/>
</mappers>
```

编写Java代码如下：

```java
@Test
public void testNamespace(){
    // 获取SqlSession对象
    SqlSession sqlSession = SqlSessionUtil.openSession();
    // 执行SQL语句
    List<Object> cars = sqlSession.selectList("selectCarAll");
    // 输出结果
    cars.forEach(car -> System.out.println(car));
}
```

运行结果如下：

```plain
org.apache.ibatis.exceptions.PersistenceException: 
### Error querying database.  Cause: java.lang.IllegalArgumentException: 
  selectCarAll is ambiguous in Mapped Statements collection (try using the full name including the namespace, or rename one of the entries) 
  【翻译】selectCarAll在Mapped Statements集合中不明确（请尝试使用包含名称空间的全名，或重命名其中一个条目）
  【大致意思是】selectCarAll重名了，你要么在selectCarAll前添加一个名称空间，要有你改个其它名字。
```

Java代码修改如下：

```java
@Test
public void testNamespace(){
    // 获取SqlSession对象
    SqlSession sqlSession = SqlSessionUtil.openSession();
    // 执行SQL语句
    //List<Object> cars = sqlSession.selectList("car.selectCarAll");
    List<Object> cars = sqlSession.selectList("car2.selectCarAll");
    // 输出结果
    cars.forEach(car -> System.out.println(car));
}
```

运行结果如下：

![1763614683732-039827cd-b984-4c65-8ea2-e75b30539e73.png](./note03-使用Mybatis完成CRUD.assets/1763614683732-039827cd-b984-4c65-8ea2-e75b30539e73-867688.png)

# 演练.用mybatis实现CRUD

# 基于Map来保存

## 1.还是mybatis-project项目，我们新建一个模块，起名：mybatis-002-crud，在pom.xml里面添加mybatis依赖和mysql驱动的依赖

```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>org.kenny</groupId>
    <artifactId>mybatis-002-crud</artifactId>
    <version>1.0-SNAPSHOT</version>

    <dependencies>
      <!--mybatis依赖-->
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis</artifactId>
            <version>3.5.19</version>
        </dependency>
        <!--mysql驱动包-->
        <dependency>
            <groupId>com.mysql</groupId>
            <artifactId>mysql-connector-j</artifactId>
            <version>8.4.0</version>
            <scope>runtime</scope>
        </dependency>

        <!--JUnit测试依赖-->
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter</artifactId>
            <version>5.12.2</version>
            <scope>test</scope>
        </dependency>
    </dependencies>
    <properties>
        <maven.compiler.source>21</maven.compiler.source>
        <maven.compiler.target>21</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

</project>
```

## 2.在main/resoureces文件夹里面添加mybatis核心配置文件和SqlMapper配置文件

![image-20261105164614158](./note03-使用Mybatis完成CRUD.assets/image-20261105164614158.png)

## 3.把我们封装的SqlSessonUtil复制粘贴过来，方便编程

![image-20261105165536532](./note03-使用Mybatis完成CRUD.assets/image-20261105165536532.png)



## 4.因为我们的CarMapper除了骨架代码之外，基本上是空的，我们先来添加插入数据代码，注意，数据不能够写死，而是要用java代码给它传值，占位符是#{}

![image-20261105170247600](./note03-使用Mybatis完成CRUD.assets/image-20261105170247600.png)

## 5.然后我们写一段测试代码来测试一下

![image-20261105174833166](./note03-使用Mybatis完成CRUD.assets/image-20261105174833166.png)

## 6.注意：此时我们需要回到CarMapper.xml中map中的key绑定到#{}里面

![image-20261105172204869](./note03-使用Mybatis完成CRUD.assets/image-20261105172204869.png)

## 7.在运行测试之前，需要检查一下mybatis-config.xml里面的mapper属性有没有设置，语句CarMapper.xml里面的namespace有没有值这两个不允许空，否则测试不通过

![image-20261105174227816](./note03-使用Mybatis完成CRUD.assets/image-20261105174227816.png)

![image-20261105174152273](./note03-使用Mybatis完成CRUD.assets/image-20261105174152273.png)

## 8.运行测试，成功通过

![image-20261105174309412](./note03-使用Mybatis完成CRUD.assets/image-20261105174309412.png)

## 9.查看数据库，发现新增成功

![image-20261105174439766](./note03-使用Mybatis完成CRUD.assets/image-20261105174439766.png)

### 注意，如果你在CarMapper中把map的一个key写错了，程序还是会正常执行，只是这个字段获取不到值

![image-20261105175356484](./note03-使用Mybatis完成CRUD.assets/image-20261105175356484.png)

![image-20261105180149298](./note03-使用Mybatis完成CRUD.assets/image-20261105180149298.png)

### 注意，为了方便编程，我们应该尽量把map的key的名称改为占位符的名称的小驼峰格式或者一致

![image-20261105181726467](./note03-使用Mybatis完成CRUD.assets/image-20261105181726467.png)

### 然后需要修改CarMapper占位符里面的key

![image-20261105181852320](./note03-使用Mybatis完成CRUD.assets/image-20261105181852320.png)

### 运行测试，顺利通过

![image-20261105181940843](./note03-使用Mybatis完成CRUD.assets/image-20261105181940843.png)

### 数据库也顺利添加数据

![image-20261105182021290](./note03-使用Mybatis完成CRUD.assets/image-20261105182021290.png)

### 还有一个需要注意的地方mybatis sqlMapper.xml的insert语句，有第二个参数，用来指定参数类型，这个参数是可省的。

![image-20261105182334482](./note03-使用Mybatis完成CRUD.assets/image-20261105182334482.png)

### 还有其他写法

![image-20261105182527090](./note03-使用Mybatis完成CRUD.assets/image-20261105182527090.png)



### 当然不设置这个参数也是可以的，只是设置了，效率会更高



# 基于实体类entity来保存

## 1.我们新建一个entity包，在里面新建一个Car类，

![image-20261105183546653](./note03-使用Mybatis完成CRUD.assets/image-20261105183546653.png)

### Car类的代码如下

```
package org.kenny.mybatis.entity;

import java.math.BigDecimal;

public class Car {
    private Long id;
    private String carNum;
    private String brand;
    private BigDecimal guidePrice;
    private String produceTime;
    private String carType;

    public Car() {
    }
    //没有id的构造方法，因为数据库里面的id是自增的
    public Car(String carNum, String brand, BigDecimal guidePrice, String produceTime, String carType) {
        this.id = null;
        this.carNum = carNum;
        this.brand = brand;
        this.guidePrice = guidePrice;
        this.produceTime = produceTime;
        this.carType = carType;
    }
    public Car(Long id, String carNum, String brand, BigDecimal guidePrice, String produceTime, String carType) {
        this.id = id;
        this.carNum = carNum;
        this.brand = brand;
        this.guidePrice = guidePrice;
        this.produceTime = produceTime;
        this.carType = carType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCarNum() {
        return carNum;
    }

    public void setCarNum(String carNum) {
        this.carNum = carNum;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public BigDecimal getGuidePrice() {
        return guidePrice;
    }

    public void setGuidePrice(BigDecimal guidePrice) {
        this.guidePrice = guidePrice;
    }

    public String getProduceTime() {
        return produceTime;
    }

    public void setProduceTime(String produceTime) {
        this.produceTime = produceTime;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", carNum='" + carNum + '\'' +
                ", brand='" + brand + '\'' +
                ", guidePrice=" + guidePrice +
                ", produceTime='" + produceTime + '\'' +
                ", carType='" + carType + '\'' +
                '}';
    }
}

```

## 2.我们给TestMybatisCrud添加一个testMybatisInsertEntity方法

![image-20261105191035127](./note03-使用Mybatis完成CRUD.assets/image-20261105191035127.png)

## 3.然后我们需要根据要求在CarMapper.xml里面新建一个id为insertCar2的insert语句，注意此时的参数类型必须是类名的全路径，否则就会找不到类而保存

![image-20261105191346613](./note03-使用Mybatis完成CRUD.assets/image-20261105191346613.png)

## 4.运行此时，顺利通过

![image-20261105191425183](./note03-使用Mybatis完成CRUD.assets/image-20261105191425183.png)

## 5.查看数据库，发现新增成功

![image-20261105191516832](./note03-使用Mybatis完成CRUD.assets/image-20261105191516832.png)

### 注意： mybatis是如何从类对象里面获取到值来填充到占位符的呢？如下面描述那样。

![image-20261106185823437](./note03-使用Mybatis完成CRUD.assets/image-20261106185823437.png)

# 演练2.delete功能的实现

## 2.1在测试类里面添加一个删除测试方法，代码如下

![image-20261106194310643](./note03-使用Mybatis完成CRUD.assets/image-20261106194310643.png)

## 2.2然后，我们需要在CarMapper里面添加一条删除语句，注意id必须和我们java代码里面的一致

![image-20261106194525453](./note03-使用Mybatis完成CRUD.assets/image-20261106194525453.png)

## 2.3运行测试，发现测试通过。

![image-20261106195008532](./note03-使用Mybatis完成CRUD.assets/image-20261106195008532.png)

## 2.4查看数据库，发现id为15的记录被删除了

![image-20261106195114936](./note03-使用Mybatis完成CRUD.assets/image-20261106195114936.png)

# 演练3.mybatis实现修改记录

## 3.1在测试类里面新建一个通过id来更新数据的测试方法

![image-20261106200857297](./note03-使用Mybatis完成CRUD.assets/image-20261106200857297.png)

## 3.2然后我们在CarMapper.xml里面添加一条update语句，id号是updateById

![image-20261106201012239](./note03-使用Mybatis完成CRUD.assets/image-20261106201012239.png)

## 3.3运行测试，顺利通过

![image-20261106201122971](./note03-使用Mybatis完成CRUD.assets/image-20261106201122971.png)

## 3.4查看数据库，发现数据被成功修改了

![image-20261106201355147](./note03-使用Mybatis完成CRUD.assets/image-20261106201355147.png)

### 当然，也可以只修改几个字段，比如我们新增一条修改语句，在修改3个字段

![image-20261106202415312](./note03-使用Mybatis完成CRUD.assets/image-20261106202415312.png)

### 然后我们修改一下测试代码

![image-20261106202505724](./note03-使用Mybatis完成CRUD.assets/image-20261106202505724.png)

### 测试通过

![image-20261106202534186](./note03-使用Mybatis完成CRUD.assets/image-20261106202534186.png)

### 检查数据库，发现也能修改成功，不修改的字段就不需要设置

![image-20261106202329074](./note03-使用Mybatis完成CRUD.assets/image-20261106202329074.png)

# 演练4.查询数据

## 4.1我们首先来学习查询一条数据，先在CarMapper.xml里面添加一条select语句，这里有个坑，需要填写resultType，否则抛异常

![image-20261107190415924](./note03-使用Mybatis完成CRUD.assets/image-20261107190415924.png)

## 4.2.然后我们写一个测试方法：testMybatisSelectById

![image-20261107191704965](./note03-使用Mybatis完成CRUD.assets/image-20261107191704965.png)

## 4.3运行程序，你会发现一个很奇怪的现象，测试没有报错，但是只有一个字段获取到值

![image-20261107190634479](./note03-使用Mybatis完成CRUD.assets/image-20261107190634479.png)

## 4.4解决办法之一就是在CarMapper里面给字段起个别名，必须和类首属性名称一致

![image-20261107191133616](./note03-使用Mybatis完成CRUD.assets/image-20261107191133616.png)

## 4.5运行测试，就可以拿到结果

![image-20261107191213873](./note03-使用Mybatis完成CRUD.assets/image-20261107191213873.png)

## 4.6需要注意，sqlSession对象里面的selectOne方法，只能够用于只有一个结果的查询，如果一个查询的结果多于1，就会抛异常

![image-20261107192620377](./note03-使用Mybatis完成CRUD.assets/image-20261107192620377.png)

## 4.7.怎么查询多个结果？其实可以使用sqlSession的selectList方法，注意，在CarMapper.xml里面的返回值类型仍然是org.kenny.mybatis.entity.Car

![image-20261107194138365](./note03-使用Mybatis完成CRUD.assets/image-20261107194138365.png)

## 4.8查询所有也是一样的道理，只是不需要后面的条件

![image-20261107195633020](./note03-使用Mybatis完成CRUD.assets/image-20261107195633020.png)

![image-20261107195701427](./note03-使用Mybatis完成CRUD.assets/image-20261107195701427.png)

## 注意，如果你的sql只返一条记录，也是可以使用selectList方法来查询的。没有问题。只是好像没有必要。但是切记，如果sql会返回多条记录不能用selectOne方法，否则抛异常。

# 演练5.关于mapper的namespace的作用练习

## 5.1新建一个测试方法改名：testMybatisNameSpace代码和testMybatisSelectById方法的代码一样

![image-20261107201934332](./note03-使用Mybatis完成CRUD.assets/image-20261107201934332.png)

## 5.2.把CarMapper.xml复制一份，改名CarMapper1.xml，只保留查询一条记录的sql配置，把命名空间改为Car1

![image-20261107202112433](./note03-使用Mybatis完成CRUD.assets/image-20261107202112433.png)

## 5.3然后在mybatis-config.xml文件里面引入这个Mapper文件

![image-20261107202221927](./note03-使用Mybatis完成CRUD.assets/image-20261107202221927.png)

## 5.4运行测试，会抛异常，因为有两个一样的sql，mybatis不知道改用哪一个

![image-20261107202412976](./note03-使用Mybatis完成CRUD.assets/image-20261107202412976.png)

## 5.5解决办法就是在sql语句的id前面添加命名空间限制

![image-20261107202610896](./note03-使用Mybatis完成CRUD.assets/image-20261107202610896.png)

