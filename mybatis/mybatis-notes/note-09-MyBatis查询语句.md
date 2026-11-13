# MyBatis查询语句专题

模块名：mybatis-007-select

## 返回Car

之前我们已经接触过了，大家把代码看一下就行了。

有一点注意事项：查询返回结果是一个的话，返回值可以用List集合接收，也可以不用。都可以。

当查询的结果，有对应的实体类，并且查询结果只有一条时：

```java
package com.jkweilai.mybatis.mapper;

import com.jkweilai.mybatis.pojo.Car;

/**
 * Car SQL映射器
 * @author 老杜
 */
public interface CarMapper {

    /**
     * 根据id主键查询：结果最多只有一条
     * @param id
     * @return
     */
    Car selectById(Long id);
}

```

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.jkweilai.mybatis.mapper.CarMapper">
    <select id="selectById" resultType="Car">
        select id,car_num carNum,brand,guide_price guidePrice,produce_time produceTime,car_type carType from t_car where id = #{id}
    </select>
</mapper>
```

```java
package com.jkweilai.mybatis.test;

import com.jkweilai.mybatis.mapper.CarMapper;
import com.jkweilai.mybatis.pojo.Car;
import com.jkweilai.mybatis.utils.SqlSessionUtil;
import org.junit.jupiter.api.Test;

public class CarMapperTest {

    @Test
    public void testSelectById(){
        CarMapper mapper = SqlSessionUtil.openSession().getMapper(CarMapper.class);
        Car car = mapper.selectById(35L);
        System.out.println(car);
    }
}

```

执行结果：

![1744115425100-cd5032eb-c9ca-4e67-96fc-b9abac43cdb6.png](./note-09-MyBatis查询语句.assets/1744115425100-cd5032eb-c9ca-4e67-96fc-b9abac43cdb6-170794.png)

**<font style="color:#E8323C;">查询结果是一条的话可以使用List集合接收吗？当然可以</font>**。

```java
/**
* 根据id主键查询：结果最多只有一条，可以放到List集合中吗？
* @return
*/
List<Car> selectByIdToList(Long id);
```

```xml
<select id="selectByIdToList" resultType="Car">
  select id,car_num carNum,brand,guide_price guidePrice,produce_time produceTime,car_type carType from t_car where id = #{id}
</select>
```

```java
@Test
public void testSelectByIdToList(){
    CarMapper mapper = SqlSessionUtil.openSession().getMapper(CarMapper.class);
    List<Car> cars = mapper.selectByIdToList(35L);
    System.out.println(cars);
}
```

执行结果：

![1763624554278-01ed8750-bbb7-4223-8871-10b722c5f240.png](./note-09-MyBatis查询语句.assets/1763624554278-01ed8750-bbb7-4223-8871-10b722c5f240-962856.png)

## 返回List<Car>

之前我们已经接触过了，大家把代码看一下就行了。

需要注意的一点是：如果返回结果是List集合（多条记录），如果采用单个实体类接收会报异常。

当查询的记录条数是多条的时候，必须使用集合接收。如果使用单个实体类接收会出现异常。

```java
/**
* 查询所有的Car
* @return
*/
List<Car> selectAll();
```

```xml
<select id="selectAll" resultType="Car">
  select id,car_num carNum,brand,guide_price guidePrice,produce_time produceTime,car_type carType from t_car
</select>
```

```java
@Test
public void testSelectAll(){
    CarMapper mapper = SqlSessionUtil.openSession().getMapper(CarMapper.class);
    List<Car> cars = mapper.selectAll();
    cars.forEach(car -> System.out.println(car));
}
```

![1744115501196-87f1693e-c891-457d-b624-0f93603dad9e.png](./note-09-MyBatis查询语句.assets/1744115501196-87f1693e-c891-457d-b624-0f93603dad9e-036638.png)

如果返回多条记录，采用单个实体类接收会怎样？

```java
/**
* 查询多条记录，采用单个实体类接收会怎样？
* @return
*/
Car selectAll2();
```

```xml
<select id="selectAll2" resultType="Car">
  select id,car_num carNum,brand,guide_price guidePrice,produce_time produceTime,car_type carType from t_car
</select>
```

```java
@Test
public void testSelectAll2(){
    CarMapper mapper = SqlSessionUtil.openSession().getMapper(CarMapper.class);
    Car car = mapper.selectAll2();
    System.out.println(car);
}
```

执行结果：

![1744115521183-c831fb74-62be-4bad-863c-29133cb8512d.png](./note-09-MyBatis查询语句.assets/1744115521183-c831fb74-62be-4bad-863c-29133cb8512d-752736.png)

## 返回Map

当返回的数据，没有合适的实体类对应的话，可以采用Map集合接收。字段名做key，字段值做value。

查询如果可以保证只有一条数据，则返回一个Map集合即可。

![1660816974662-61782965-88fb-466e-a5ff-5af02ab614df.png](./note-09-MyBatis查询语句.assets/1660816974662-61782965-88fb-466e-a5ff-5af02ab614df-531263.png)

```java
/**
 * 通过id查询一条记录，返回Map集合
 * @param id
 * @return
 */
Map<String, Object> selectByIdRetMap(Long id);
```

```xml
<select id="selectByIdRetMap" resultType="map">
  select id,car_num carNum,brand,guide_price guidePrice,produce_time produceTime,car_type carType from t_car where id = #{id}
</select>
```

**<font style="color:#E8323C;">resultMap="map"，这是因为mybatis内置了很多别名。【参见mybatis开发手册】</font>**

```java
@Test
public void testSelectByIdRetMap(){
    CarMapper mapper = SqlSessionUtil.openSession().getMapper(CarMapper.class);
    Map<String,Object> car = mapper.selectByIdRetMap(35L);
    System.out.println(car);
}
```

执行结果：

![1763624658443-8f8d0574-745d-46f7-9a9b-ece7abb9f923.png](./note-09-MyBatis查询语句.assets/1763624658443-8f8d0574-745d-46f7-9a9b-ece7abb9f923-412460.png)

当然，如果返回一个Map集合，可以将Map集合放到List集合中吗？当然可以，这里就不再测试了。

反过来，如果返回的不是一条记录，是多条记录的话，只采用单个Map集合接收，这样同样会出现之前的异常：**<font style="color:#E8323C;">TooManyResultsException</font>**

## 返回List<Map>

查询结果条数大于等于1条数据，则可以返回一个存储Map集合的List集合。List<Map>等同于List<Car>

![1660817896708-0f3d0b44-4a0e-40ed-b609-b4d0e5fce1b6.png](./note-09-MyBatis查询语句.assets/1660817896708-0f3d0b44-4a0e-40ed-b609-b4d0e5fce1b6-949320.png)

```java
/**
 * 查询所有的Car，返回一个List集合。List集合中存储的是Map集合。
 * @return
 */
List<Map<String,Object>> selectAllRetListMap();
```

```xml
<select id="selectAllRetListMap" resultType="map">
  select id,car_num carNum,brand,guide_price guidePrice,produce_time produceTime,car_type carType from t_car
</select>
```

```java
@Test
public void testSelectAllRetListMap(){
    CarMapper mapper = SqlSessionUtil.openSession().getMapper(CarMapper.class);
    List<Map<String,Object>> cars = mapper.selectAllRetListMap();
    System.out.println(cars);
}
```

执行结果：

```json
[
  {carType=燃油车, carNum=103, guidePrice=50.30, produceTime=2020-10-01, id=33, brand=奔驰E300L}, 
  {carType=电车, carNum=102, guidePrice=30.23, produceTime=2018-09-10, id=34, brand=比亚迪汉}, 
  {carType=燃油车, carNum=103, guidePrice=50.30, produceTime=2020-10-01, id=35, brand=奔驰E300L}, 
  {carType=燃油车, carNum=103, guidePrice=33.23, produceTime=2020-10-11, id=36, brand=奔驰C200},
  ......
]
```

## 返回Map\<String,Map>

**<font style="color:#E8323C;">拿Car的id做key，以后取出对应的Map集合时更方便。</font>**

![1660818043977-d5c56423-c5db-43ca-9c0c-eaa3f22a8f21.png](./note-09-MyBatis查询语句.assets/1660818043977-d5c56423-c5db-43ca-9c0c-eaa3f22a8f21-437987.png)

```java
/**
 * 获取所有的Car，返回一个Map集合。
 * Map集合的key是Car的id。
 * Map集合的value是对应Car。
 * @return
 */
@MapKey("id")
Map<Long,Map<String,Object>> selectAllRetMap();
```

```xml
<select id="selectAllRetMap" resultType="map">
  select id,car_num carNum,brand,guide_price guidePrice,produce_time produceTime,car_type carType from t_car
</select>
```

```java
@Test
public void testSelectAllRetMap(){
    CarMapper mapper = SqlSessionUtil.openSession().getMapper(CarMapper.class);
    Map<Long,Map<String,Object>> cars = mapper.selectAllRetMap();
    System.out.println(cars);
}
```

执行结果：

```json
{
64={carType=燃油车, carNum=133, guidePrice=50.30, produceTime=2020-01-10, id=64, brand=丰田霸道}, 
66={carType=燃油车, carNum=133, guidePrice=50.30, produceTime=2020-01-10, id=66, brand=丰田霸道}, 
67={carType=燃油车, carNum=133, guidePrice=50.30, produceTime=2020-01-10, id=67, brand=丰田霸道}, 
69={carType=燃油车, carNum=133, guidePrice=50.30, produceTime=2020-01-10, id=69, brand=丰田霸道},
......
}
```

## resultMap结果映射

查询结果的列名和java对象的属性名对应不上怎么办？

* 第一种方式：as 给列起别名
* 第二种方式：使用resultMap进行结果映射
* 第三种方式：开启驼峰命名自动映射（配置settings）

### 使用resultMap进行结果映射

```java
/**
 * 查询所有Car，使用resultMap进行结果映射
 * @return
 */
List<Car> selectAllByResultMap();
```

```xml
<!--
        resultMap:
            id：这个结果映射的标识，作为select标签的resultMap属性的值。
            type：结果集要映射的类。可以使用别名。
-->
<resultMap id="carResultMap" type="car">
  <!--对象的唯一标识，官方解释是：为了提高mybatis的性能。建议写上。-->
  <id property="id" column="id"/>
  <result property="carNum" column="car_num"/>
  <!--当属性名和数据库列名一致时，可以省略。但建议都写上。-->
  <!--javaType用来指定属性类型。jdbcType用来指定列类型。一般可以省略。-->
  <result property="brand" column="brand" javaType="string" jdbcType="VARCHAR"/>
  <result property="guidePrice" column="guide_price"/>
  <result property="produceTime" column="produce_time"/>
  <result property="carType" column="car_type"/>
</resultMap>

<!--resultMap属性的值必须和resultMap标签中id属性值一致。-->
<select id="selectAllByResultMap" resultMap="carResultMap">
  select * from t_car
</select>
```

```java
@Test
public void testSelectAllByResultMap(){
    CarMapper carMapper = SqlSessionUtil.openSession().getMapper(CarMapper.class);
    List<Car> cars = carMapper.selectAllByResultMap();
    System.out.println(cars);
}
```

执行结果正常。

### 是否开启驼峰命名自动映射

使用这种方式的前提是：属性名遵循Java的命名规范，数据库表的列名遵循SQL的命名规范。

Java命名规范：首字母小写，后面每个单词首字母大写，遵循驼峰命名方式。

SQL命名规范：全部小写，单词之间采用下划线分割。

比如以下的对应关系：

| **实体类中的属性名** | **数据库表的列名** |
| -------------------- | ------------------ |
| carNum               | car_num            |
| carType              | car_type           |
| produceTime          | produce_time       |

如何启用该功能，在mybatis-config.xml文件中进行配置：

```xml
<!--放在properties标签后面-->
<settings>
  <setting name="mapUnderscoreToCamelCase" value="true"/>
</settings>
```

```java
/**
* 查询所有Car，启用驼峰命名自动映射
* @return
*/
List<Car> selectAllByMapUnderscoreToCamelCase();
```

```xml
<select id="selectAllByMapUnderscoreToCamelCase" resultType="Car">
  select * from t_car
</select>
```

```java
@Test
public void testSelectAllByMapUnderscoreToCamelCase(){
    CarMapper carMapper = SqlSessionUtil.openSession().getMapper(CarMapper.class);
    List<Car> cars = carMapper.selectAllByMapUnderscoreToCamelCase();
    System.out.println(cars);
}
```

执行结果正常。

## 返回总记录条数

需求：查询总记录条数

```java
/**
 * 获取总记录条数
 * @return
 */
Long selectTotal();
```

```xml
<!--long是别名，可参考mybatis开发手册。-->
<select id="selectTotal" resultType="long">
  select count(*) from t_car
</select>
```

```java
@Test
public void testSelectTotal(){
    CarMapper carMapper = SqlSessionUtil.openSession().getMapper(CarMapper.class);
    Long total = carMapper.selectTotal();
    System.out.println(total);
}
```

# 项目演练1：

## 1.1把005-antic2项目复制一份，改名mybatis-007-select，修改运行项目名称，然后添加为maven项目

![image-20261113163459411](./note-09-MyBatis查询语句.assets/image-20261113163459411.png)

## 1.2把mapper接口的方法删除和mapper.xml里面的sql语句删除。把测试代码也删除了。

![image-20261113163802354](./note-09-MyBatis查询语句.assets/image-20261113163802354.png)

![image-20261113163748472](./note-09-MyBatis查询语句.assets/image-20261113163748472.png)

![image-20261113163936634](./note-09-MyBatis查询语句.assets/image-20261113163936634.png)

## 1.3给CarMapper接口添加一个selectById方法。

![image-20261113164736065](./note-09-MyBatis查询语句.assets/image-20261113164736065.png)

## 1.4然后在CarMapper.xml中添加一条select语句，id就是mapper接口的方法名，需要指定返回类型为map

![image-20261113164915676](./note-09-MyBatis查询语句.assets/image-20261113164915676.png)

## 1.5然后我们编写一个测试方法：testMybatisSelectById

![image-20261113165004051](./note-09-MyBatis查询语句.assets/image-20261113165004051.png)

## 1.6运行测试顺利通过，返回的是一个map

![image-20261113165049257](./note-09-MyBatis查询语句.assets/image-20261113165049257.png)

## 1.7然后我们来给mapper接口写一个方法，返回一个List< Map>集合

![image-20261113170117428](./note-09-MyBatis查询语句.assets/image-20261113170117428.png)

## 1.8然后我们在CarMapper.xml里面添加一个select语句，id就是mapper接口的方法名

![image-20261113170223008](./note-09-MyBatis查询语句.assets/image-20261113170223008.png)

## 1.9 然后我们再写一个测试方法：testMybatisSelectCarNum

![image-20261113170317760](./note-09-MyBatis查询语句.assets/image-20261113170317760.png)

## 1.10运行测试，顺利通过

![image-20261113170406121](./note-09-MyBatis查询语句.assets/image-20261113170406121.png)

## 1.11此外还可以返回Map<long,Map< String,Object>>,不过写的时候比较麻烦，先在mapper接口里面定义方法，指定用什么来作为key

![image-20261113172245920](./note-09-MyBatis查询语句.assets/image-20261113172245920.png)

## 1.12然后在mapper.xml里面添加一个select语句，id是mapper接口的方法

![image-20261113172348623](./note-09-MyBatis查询语句.assets/image-20261113172348623.png)

## 1.13，然后我们编写一个测试方法：testMybatisSelectAllRetMap

![image-20261113172501427](./note-09-MyBatis查询语句.assets/image-20261113172501427.png)

## 1.14 运行测试，顺利通过

![image-20261113172543771](./note-09-MyBatis查询语句.assets/image-20261113172543771.png)

## 1.15也是可以返回一个Map< Long,Car>的，我们给我mapper添加一个方法：selectAllRetMap2，注意需要指定用什么来作为key

![image-20261113173112005](./note-09-MyBatis查询语句.assets/image-20261113173112005.png)

## 1.16然后在mapper.xml文件里面添加一条select语句，id就是mapper接口的方法名，注意修改返回值类型

![image-20261113173240228](./note-09-MyBatis查询语句.assets/image-20261113173240228.png)

## 1.17.然后我们编写一个测试方法：estMybatisSelectAllRetMap2

![image-20261113173353993](./note-09-MyBatis查询语句.assets/image-20261113173353993.png)

## 1.18运行测试，顺利通过

![image-20261113173448490](./note-09-MyBatis查询语句.assets/image-20261113173448490.png)