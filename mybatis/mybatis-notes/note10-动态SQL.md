# 动态SQL

有的业务场景，也需要SQL语句进行动态拼接，例如：

* 批量删除

![1660799087155-ec264d4c-cafa-458d-85b9-f18b4c34bafd.png](./note10-动态SQL.assets/1660799087155-ec264d4c-cafa-458d-85b9-f18b4c34bafd-354612.png)

```sql
delete from t_car where id in(1,2,3,4,5,6,......这里的值是动态的，根据用户选择的id不同，值是不同的);
```

* 多条件查询

![1660876082121-d5ea8e08-e642-4716-b201-79cfe3849624.png](./note10-动态SQL.assets/1660876082121-d5ea8e08-e642-4716-b201-79cfe3849624-100731.png)

```sql
select * from t_car where brand like '丰田%' and guide_price > 30 and .....;
```

创建模块：mybatis-008-dynamic-sql

## if标签

需求：多条件查询。

可能的条件包括：品牌（brand）、指导价格（guide_price）、汽车类型（car_type）

```java
package com.jkweilai.mybatis.mapper;

import com.jkweilai.mybatis.pojo.Car;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CarMapper {


    /**
     * 根据多条件查询Car
     * @param brand
     * @param guidePrice
     * @param carType
     * @return
     */
    List<Car> selectByMultiCondition(@Param("brand") String brand, @Param("guidePrice") Double guidePrice, @Param("carType") String carType);
}

```

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.jkweilai.mybatis.mapper.CarMapper">

    <select id="selectByMultiCondition" resultType="car">
        select * from t_car where
        <if test="brand != null and brand != ''">
            brand like #{brand}"%"
        </if>
        <if test="guidePrice != null and guidePrice != ''">
            and guide_price >= #{guidePrice}
        </if>
        <if test="carType != null and carType != ''">
            and car_type = #{carType}
        </if>
    </select>

</mapper>
```

```java
package com.jkweilai.mybatis.test;

import com.jkweilai.mybatis.mapper.CarMapper;
import com.jkweilai.mybatis.pojo.Car;
import com.jkweilai.mybatis.utils.SqlSessionUtil;
import org.junit.jupiter.api.Test;

import java.util.List;

public class CarMapperTest {
    @Test
    public void testSelectByMultiCondition(){
        CarMapper mapper = SqlSessionUtil.openSession().getMapper(CarMapper.class);
        List<Car> cars = mapper.selectByMultiCondition("丰田", 20.0, "燃油车");
        System.out.println(cars);
    }
}

```

执行结果：

![1660889544842-34404e94-66f2-48e5-b58a-784d6b91ee27.png](./note10-动态SQL.assets/1660889544842-34404e94-66f2-48e5-b58a-784d6b91ee27-891871.png)

如果第一个条件为空，剩下两个条件不为空，会是怎样呢？

```java
List<Car> cars = mapper.selectByMultiCondition("", 20.0, "燃油车");
```

执行结果：

![1660889738193-d5023b6c-13a9-447f-a128-45b36663c8f1.png](./note10-动态SQL.assets/1660889738193-d5023b6c-13a9-447f-a128-45b36663c8f1-875874.png)

报错了，SQL语法有问题，where后面出现了and。这该怎么解决呢？

* 可以where后面添加一个恒成立的条件。

![1660889965103-5e519823-a741-4ebf-b930-e10ffc9a4129.png](./note10-动态SQL.assets/1660889965103-5e519823-a741-4ebf-b930-e10ffc9a4129-681831.png)

执行结果：

![1660890083737-c8716d78-9e2d-4a22-bf56-12ff94b688f5.png](./note10-动态SQL.assets/1660890083737-c8716d78-9e2d-4a22-bf56-12ff94b688f5-256843.png)

如果三个条件都是空，有影响吗？

```java
List<Car> cars = mapper.selectByMultiCondition("", null, "");
```

执行结果：

![1660890238928-6f889bdf-9702-4cd7-a0ef-b875977f42f9.png](./note10-动态SQL.assets/1660890238928-6f889bdf-9702-4cd7-a0ef-b875977f42f9-474980.png)

三个条件都不为空呢？

```java
List<Car> cars = mapper.selectByMultiCondition("丰田", 20.0, "燃油车");
```

执行结果：

![1660890374052-89bc29a1-3aeb-4941-b33f-fbbbe4fbbd39.png](./note10-动态SQL.assets/1660890374052-89bc29a1-3aeb-4941-b33f-fbbbe4fbbd39-958218.png)

## where标签

where标签的作用：让where子句更加动态智能。

* 所有条件都为空时，where标签保证不会生成where子句。
* 自动去除某些条件\*\*<font style="color:#E8323C;">前面</font>\*\*多余的and或or。

继续使用if标签中的需求。

```java
/**
* 根据多条件查询Car，使用where标签
* @param brand
* @param guidePrice
* @param carType
* @return
*/
List<Car> selectByMultiConditionWithWhere(@Param("brand") String brand, @Param("guidePrice") Double guidePrice, @Param("carType") String carType);
```

```xml
<select id="selectByMultiConditionWithWhere" resultType="car">
  select * from t_car
  <where>
    <if test="brand != null and brand != ''">
      and brand like #{brand}"%"
    </if>
    <if test="guidePrice != null and guidePrice != ''">
      and guide_price >= #{guidePrice}
    </if>
    <if test="carType != null and carType != ''">
      and car_type = #{carType}
    </if>
  </where>
</select>
```

```java
@Test
public void testSelectByMultiConditionWithWhere(){
    CarMapper mapper = SqlSessionUtil.openSession().getMapper(CarMapper.class);
    List<Car> cars = mapper.selectByMultiConditionWithWhere("丰田", 20.0, "燃油车");
    System.out.println(cars);
}
```

运行结果：

![1660891031758-ce20585a-6bad-452e-b186-09cf465fecc6.png](./note10-动态SQL.assets/1660891031758-ce20585a-6bad-452e-b186-09cf465fecc6-694706.png)

如果所有条件都是空呢？

```java
List<Car> cars = mapper.selectByMultiConditionWithWhere("", null, "");
```

运行结果：

![1660891127310-9847b1b5-2bf4-42f9-8e73-38b051b398de.png](./note10-动态SQL.assets/1660891127310-9847b1b5-2bf4-42f9-8e73-38b051b398de-156281.png)

它可以自动去掉前面多余的and，那可以自动去掉前面多余的or吗？

```java
List<Car> cars = mapper.selectByMultiConditionWithWhere("丰田", 20.0, "燃油车");
```

```xml
<select id="selectByMultiConditionWithWhere" resultType="car">
  select * from t_car
  <where>
    <if test="brand != null and brand != ''">
      or brand like #{brand}"%"
    </if>
    <if test="guidePrice != null and guidePrice != ''">
      and guide_price >= #{guidePrice}
    </if>
    <if test="carType != null and carType != ''">
      and car_type = #{carType}
    </if>
  </where>
</select>
```

执行结果：

![1660891540196-f2e055dd-74a7-40f9-a883-c78a5e2d28b7.png](./note10-动态SQL.assets/1660891540196-f2e055dd-74a7-40f9-a883-c78a5e2d28b7-844959.png)

它可以自动去掉前面多余的and，那可以自动去掉后面多余的and吗？

```xml
<select id="selectByMultiConditionWithWhere" resultType="car">
  select * from t_car
  <where>
    <if test="brand != null and brand != ''">
      brand like #{brand}"%" and
    </if>
    <if test="guidePrice != null and guidePrice != ''">
      guide_price >= #{guidePrice} and
    </if>
    <if test="carType != null and carType != ''">
      car_type = #{carType}
    </if>
  </where>
</select>
```

```xml
// 让最后一个条件为空
List<Car> cars = mapper.selectByMultiConditionWithWhere("丰田", 20.0, "");
```

运行结果：

![1660891786608-272a3ab9-96f8-42bc-b5df-4d275cfe9cfe.png](./note10-动态SQL.assets/1660891786608-272a3ab9-96f8-42bc-b5df-4d275cfe9cfe-936016.png)

很显然，后面多余的and是不会被去除的。

## trim标签

trim标签的属性：

* prefix：在trim标签中的语句前\*\*<font style="color:#E8323C;">添加</font>\*\*内容
* suffix：在trim标签中的语句后\*\*<font style="color:#E8323C;">添加</font>\*\*内容
* prefixOverrides：前缀\*\*<font style="color:#E8323C;">覆盖掉（去掉）</font>\*\*
* suffixOverrides：后缀\*\*<font style="color:#E8323C;">覆盖掉（去掉）</font>\*\*

```java
/**
* 根据多条件查询Car，使用trim标签
* @param brand
* @param guidePrice
* @param carType
* @return
*/
List<Car> selectByMultiConditionWithTrim(@Param("brand") String brand, @Param("guidePrice") Double guidePrice, @Param("carType") String carType);
```

```xml
<select id="selectByMultiConditionWithTrim" resultType="car">
  select * from t_car
  <trim prefix="where" suffixOverrides="and|or">
    <if test="brand != null and brand != ''">
      brand like #{brand}"%" and
    </if>
    <if test="guidePrice != null and guidePrice != ''">
      guide_price >= #{guidePrice} and
    </if>
    <if test="carType != null and carType != ''">
      car_type = #{carType}
    </if>
  </trim>
</select>
```

```java
@Test
public void testSelectByMultiConditionWithTrim(){
    CarMapper mapper = SqlSessionUtil.openSession().getMapper(CarMapper.class);
    List<Car> cars = mapper.selectByMultiConditionWithTrim("丰田", 20.0, "");
    System.out.println(cars);
}
```

![1660894051788-34d9dd44-612e-424e-bf0a-143bea4b36bd.png](./note10-动态SQL.assets/1660894051788-34d9dd44-612e-424e-bf0a-143bea4b36bd-243363.png)

如果所有条件为空，where会被加上吗？

```java
List<Car> cars = mapper.selectByMultiConditionWithTrim("", null, "");
```

执行结果：

![1660894220001-e789ea6b-a219-4f3d-8670-b9612d1d8274.png](./note10-动态SQL.assets/1660894220001-e789ea6b-a219-4f3d-8670-b9612d1d8274-574738.png)

## set标签

主要使用在update语句当中，用来生成set关键字，同时去掉最后多余的“,”

比如我们只更新提交的不为空的字段，如果提交的数据是空或者""，那么这个字段我们将不更新。

```java
/**
* 更新信息，使用set标签
* @param car
* @return
*/
int updateWithSet(Car car);
```

```xml
<update id="updateWithSet">
  update t_car
  <set>
    <if test="carNum != null and carNum != ''">car_num = #{carNum},</if>
    <if test="brand != null and brand != ''">brand = #{brand},</if>
    <if test="guidePrice != null and guidePrice != ''">guide_price = #{guidePrice},</if>
    <if test="produceTime != null and produceTime != ''">produce_time = #{produceTime},</if>
    <if test="carType != null and carType != ''">car_type = #{carType},</if>
  </set>
  where id = #{id}
</update>
```

```java
@Test
public void testUpdateWithSet(){
    CarMapper mapper = SqlSessionUtil.openSession().getMapper(CarMapper.class);
    Car car = new Car(38L,"1001","丰田霸道2",10.0,"",null);
    int count = mapper.updateWithSet(car);
    System.out.println(count);
    SqlSessionUtil.openSession().commit();
}
```

执行结果：

![1660895330743-edd9f2e6-8cd0-4cbe-9c73-adf009466e38.png](./note10-动态SQL.assets/1660895330743-edd9f2e6-8cd0-4cbe-9c73-adf009466e38-155484.png)

## choose when otherwise

这三个标签是在一起使用的：

```xml
<choose>
  <when></when>
  <when></when>
  <when></when>
  <otherwise></otherwise>
</choose>
```

等同于：

```java
if(){
    
}else if(){
    
}else if(){
    
}else if(){
    
}else{

}
```

只有一个分支会被选择！！！！

需求：先根据品牌查询，如果没有提供品牌，再根据指导价格查询，如果没有提供指导价格，就根据生产日期查询。

```java
/**
* 使用choose when otherwise标签查询
* @param brand
* @param guidePrice
* @param produceTime
* @return
*/
List<Car> selectWithChoose(@Param("brand") String brand, @Param("guidePrice") Double guidePrice, @Param("produceTime") String produceTime);
```

```xml
<select id="selectWithChoose" resultType="car">
  select * from t_car
  <where>
    <choose>
      <when test="brand != null and brand != ''">
        brand like #{brand}"%"
      </when>
      <when test="guidePrice != null and guidePrice != ''">
        guide_price >= #{guidePrice}
      </when>
      <otherwise>
        produce_time >= #{produceTime}
      </otherwise>
    </choose>
  </where>
</select>
```

```java
@Test
public void testSelectWithChoose(){
    CarMapper mapper = SqlSessionUtil.openSession().getMapper(CarMapper.class);
    //List<Car> cars = mapper.selectWithChoose("丰田霸道", 20.0, "2000-10-10");
    //List<Car> cars = mapper.selectWithChoose("", 20.0, "2000-10-10");
    //List<Car> cars = mapper.selectWithChoose("", null, "2000-10-10");
    List<Car> cars = mapper.selectWithChoose("", null, "");
    System.out.println(cars);
}
```

![1660896273467-9ba1d6c0-9cc3-4deb-92e5-0f54b4ade614.png](./note10-动态SQL.assets/1660896273467-9ba1d6c0-9cc3-4deb-92e5-0f54b4ade614-481478.png)

## foreach标签

循环数组或集合，动态生成sql，比如这样的SQL：

```sql
delete from t_car where id in(1,2,3);
delete from t_car where id = 1 or id = 2 or id = 3;
```

```sql
insert into t_car values
  (null,'1001','凯美瑞',35.0,'2010-10-11','燃油车'),
  (null,'1002','比亚迪唐',31.0,'2020-11-11','新能源'),
  (null,'1003','比亚迪宋',32.0,'2020-10-11','新能源')
```

### 批量删除

* 用in来删除

```java
/**
* 通过foreach完成批量删除
* @param ids
* @return
*/
int deleteBatchByForeach(@Param("ids") Long[] ids);
```

```xml
<!--
collection：集合或数组【如果使用了@Param注解，则写ids，如果没有使用@Param注解，当参数是数组时固定写array，是List集合时固定写list】
item：集合或数组中的元素
separator：分隔符
open：foreach标签中所有内容的开始
close：foreach标签中所有内容的结束
-->
<delete id="deleteBatchByForeach">
  delete from t_car where id in
  <foreach collection="ids" item="id" separator="," open="(" close=")">
    #{id}
  </foreach>
</delete>
```

```java
@Test
public void testDeleteBatchByForeach(){
    CarMapper mapper = SqlSessionUtil.openSession().getMapper(CarMapper.class);
    int count = mapper.deleteBatchByForeach(new Long[]{40L, 41L, 42L});
    System.out.println("删除了几条记录：" + count);
    SqlSessionUtil.openSession().commit();
}
```

执行结果：

![1660899240200-ab225394-27f1-4ec1-94e0-b2b3d3a9b16e.png](./note10-动态SQL.assets/1660899240200-ab225394-27f1-4ec1-94e0-b2b3d3a9b16e-903737.png)

* 用or来删除

```java
/**
* 通过foreach完成批量删除
* @param ids
* @return
*/
int deleteBatchByForeach2(@Param("ids") Long[] ids);
```

```xml
<delete id="deleteBatchByForeach2">
  delete from t_car where
  <foreach collection="ids" item="id" separator="or">
    id = #{id}
  </foreach>
</delete>
```

```java
@Test
public void testDeleteBatchByForeach2(){
    CarMapper mapper = SqlSessionUtil.openSession().getMapper(CarMapper.class);
    int count = mapper.deleteBatchByForeach2(new Long[]{40L, 41L, 42L});
    System.out.println("删除了几条记录：" + count);
    SqlSessionUtil.openSession().commit();
}
```

执行结果：

![1660899667194-f150bf6b-b54f-432a-8455-4c819bac0c58.png](./note10-动态SQL.assets/1660899667194-f150bf6b-b54f-432a-8455-4c819bac0c58-479504.png)

### 批量添加

```java
/**
* 批量添加，使用foreach标签
* @param cars
* @return
*/
int insertBatchByForeach(@Param("cars") List<Car> cars);
```

```xml
<insert id="insertBatchByForeach">
  insert into t_car values 
  <foreach collection="cars" item="car" separator=",">
    (null,#{car.carNum},#{car.brand},#{car.guidePrice},#{car.produceTime},#{car.carType})
  </foreach>
</insert>
```

```java
@Test
public void testInsertBatchByForeach(){
    CarMapper mapper = SqlSessionUtil.openSession().getMapper(CarMapper.class);
    Car car1 = new Car(null, "2001", "兰博基尼", 100.0, "1998-10-11", "燃油车");
    Car car2 = new Car(null, "2001", "兰博基尼", 100.0, "1998-10-11", "燃油车");
    Car car3 = new Car(null, "2001", "兰博基尼", 100.0, "1998-10-11", "燃油车");
    List<Car> cars = Arrays.asList(car1, car2, car3);
    int count = mapper.insertBatchByForeach(cars);
    System.out.println("插入了几条记录" + count);
    SqlSessionUtil.openSession().commit();
}
```

执行结果：

![1660900226070-90533bde-4129-49b5-9fa7-639aa07c3b03.png](./note10-动态SQL.assets/1660900226070-90533bde-4129-49b5-9fa7-639aa07c3b03-215475.png)

## sql标签与include标签

sql标签用来声明sql片段

include标签用来将声明的sql片段包含到某个sql语句当中

作用：代码复用。易维护。

```xml
<sql id="carCols">id,car_num carNum,brand,guide_price guidePrice,produce_time produceTime,car_type carType</sql>

<select id="selectAllRetMap" resultType="map">
  select <include refid="carCols"/> from t_car
</select>

<select id="selectAllRetListMap" resultType="map">
  select <include refid="carCols"/> carType from t_car
</select>

<select id="selectByIdRetMap" resultType="map">
  select <include refid="carCols"/> from t_car where id = #{id}
</select>
```

# 演练1.if标签

## 1.1把007模块复制一份，改名mybatis-008-dynamic-sql，然后修改pom.xml文件里面的下面名称，然后在idea里面把它添加为maven项目

![image-20261114121207226](./note10-动态SQL.assets/image-20261114121207226.png)

## 1.2把CarMapper接口里面的方法删除，把CarMapper.xml里面的代码sql标签代码也删除，把测试类里面的代码也删除

![image-20261114121346936](./note10-动态SQL.assets/image-20261114121346936.png)

![image-20261114121314912](./note10-动态SQL.assets/image-20261114121314912.png)

![image-20261114121642387](./note10-动态SQL.assets/image-20261114121642387.png)

## 1.3我们来做应该多条件查询，之所以是动态的是因为用户不同时刻可能提供不一样的条件，甚至有时候一个条件都没有提供，如果没有条件where就是多余的。我们给mapper接口添加一个根据条件查询的方法。

![image-20261114130019812](./note10-动态SQL.assets/image-20261114130019812.png)

## 1.4 然后我们在CarMapper.xml里面添加一条查询语句，id="selectByCondition"需要使用if标签来进行条件判断

![image-20261114174251045](./note10-动态SQL.assets/image-20261114174251045.png)

## 1.5然后我们在测试类里面添加一个testSelectByCondition方法，代码如下

![image-20261114174408921](./note10-动态SQL.assets/image-20261114174408921.png)

## 1.6运行测试，顺利通过

![image-20261114174450979](./note10-动态SQL.assets/image-20261114174450979.png)

## 17，然后我们把给params这个map对象赋值的语句注释了，

![image-20261114174550264](./note10-动态SQL.assets/image-20261114174550264.png)

## 1.8再次运行测试，就会报错

![image-20261114175551713](./note10-动态SQL.assets/image-20261114175551713.png)

###  注意，还有，第一个参数必须要有值，否则也会报错，因为where 后面直接接and是错误的。where 后面需要有条件然后才可以接and



## 1.9我们放开一个注释，也就是只给brand参数赋值，运行测试也是可以通过的，说明最少要有一个参数有值

![image-20261114175810777](./note10-动态SQL.assets/image-20261114175810777.png)

### 注意：在if标签的test属性里面只能使用and，or来表示并且和或者不能使用&&和||

# 演练2.where标签

## 2.1上面的if标签有一个致命弱点，就是如果用户一个参数都不通过，就会抛异常，因为where关键字后面必须有条件，否则就报错。其实解决办法就是if标签需要配合where标签，把where标签套在if标签的外面，外面在CarMapper.xml里面创建另外一个sql语句id是selectByConditionWithWhere

![image-20261114181125998](./note10-动态SQL.assets/image-20261114181125998.png)

## 2.2然后外面在mapper接口里面新建一个selectByConditionWithWhere方法

![image-20261114181250377](./note10-动态SQL.assets/image-20261114181250377.png)

## 2.3然后外面在测试类里面新建一个测试方法，我们先不给params赋值，所有key是null

![image-20261114181426692](./note10-动态SQL.assets/image-20261114181426692.png)

## 2.4运行测试，顺利通过，此时就相当于查询所有事件

![image-20261114181556029](./note10-动态SQL.assets/image-20261114181556029.png)

## 2.5给一个参数赋值，也是可以的。

![image-20261114181700187](./note10-动态SQL.assets/image-20261114181700187.png)

## 2.6回到mapper.xml文件，我们故意在第一个条件前面添加一个and，

![image-20261114192704253](./note10-动态SQL.assets/image-20261114192704253.png)

## 2.7运行测试，还是可以正常执行，项目where标签的确能够把多余的and或者or去掉

![image-20261114192839053](./note10-动态SQL.assets/image-20261114192839053.png)

## 注意：where标签只能去除条件表达式前面的and和or，在条件表达式后面的and和or是无法去掉的。

# 演练3.trim标签

## 3.1.通过上面的学习我们知道，where标签只能够去除前面的and和or，不能去除后面的，那么trim标签就可以做到这一点。而且trim标签也可以设置在前面添加关键字和在后面添加关键字或者符号，也可以删除多余的关键字或者符号。我们在mapper.xml文件里面添加一条sql，id是selectByConditionWithTrim

![image-20261114195347447](./note10-动态SQL.assets/image-20261114195347447.png)

## 3.2然后在mapper接口里面添加一个selectByConditionWithTrim方法

![image-20261114195458825](./note10-动态SQL.assets/image-20261114195458825.png)

## 3.3 然后在测试类里面添加一个测试方法testSelectByConditionWithTrim，我们首先不传递任何参数，测试顺利通过，此时相当于选取所有记录

![image-20261114201301286](./note10-动态SQL.assets/image-20261114201301286.png)

## 3.4我们只放开第二个参数的赋值语句，此时测试失败，因为我们并没有配置它删除前面多余的and

![image-20261114201437696](./note10-动态SQL.assets/image-20261114201437696.png)

## 3.5我们配置一下删除多余的前缀

![image-20261114201648823](./note10-动态SQL.assets/image-20261114201648823.png)

## 3.6然后运行测试，就可以顺利通过

![image-20261114201752693](./note10-动态SQL.assets/image-20261114201752693.png)

## trim标签用的比较少，但是还是需要学习的。
