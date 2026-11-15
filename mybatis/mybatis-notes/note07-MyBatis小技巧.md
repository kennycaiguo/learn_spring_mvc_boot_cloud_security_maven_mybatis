# MyBatis小技巧

## `#{}`和`${}`

* <code>**<font style="color:rgb(15, 17, 21);background-color:rgb(235, 238, 242);">#{}</font>**</code><font style="color:rgb(15, 17, 21);">：是</font>**<font style="color:rgb(15, 17, 21);">参数占位符</font>**<font style="color:rgb(15, 17, 21);">，MyBatis 会将其替换为</font><font style="color:rgb(15, 17, 21);"> </font><code><font style="color:rgb(15, 17, 21);background-color:rgb(235, 238, 242);">?</font></code><font style="color:rgb(15, 17, 21);">，并通过 PreparedStatement 安全地设置参数。能有效</font>**<font style="color:rgb(15, 17, 21);">防止 SQL 注入</font>**<font style="color:rgb(15, 17, 21);">。</font>
* <code>**<font style="color:rgb(15, 17, 21);background-color:rgb(235, 238, 242);">${}</font>**</code><font style="color:rgb(15, 17, 21);">：是</font>**<font style="color:rgb(15, 17, 21);">字符串替换</font>**<font style="color:rgb(15, 17, 21);">，MyBatis 会将其内容直接</font>**<font style="color:rgb(15, 17, 21);">拼接</font>**<font style="color:rgb(15, 17, 21);">到 SQL 语句中。</font>**<font style="color:rgb(15, 17, 21);">存在 SQL 注入风险</font>**<font style="color:rgb(15, 17, 21);">。</font>
* 在mybatis中超过98%的情况需要使用#{}，只有极少数情况使用${},比如需要给sql语句拼接一个表名。
* 注意：不能用${}来接收前端传递过来的数据，只允许在接收后端java代码传递的数据中使用
* ${}还有一个作用就是用来拼接列名，和拼接order by后面或者group by后面的字段 

需求：根据car_type查询汽车

**模块名：mybatis-005-antic**

### 使用#{}

mapper接口

```java
package com.jkweilai.mybatis.mapper;

import com.jkweilai.mybatis.pojo.Car;

import java.util.List;

/**
 * Car的sql映射对象
 * @author 老杜
 */
public interface CarMapper {

    /**
     * 根据car_num获取Car
     * @param carType
     * @return
     */
    List<Car> selectByCarType(String carType);

}

```

CarMapper.xml：**<font style="color:#E8323C;">注意namespace必须和接口名一致。id必须和接口中方法名一致</font>**。

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.jkweilai.mybatis.mapper.CarMapper">
    <select id="selectByCarType" resultType="com.jkweilai.mybatis.pojo.Car">
        select
            id,car_num as carNum,brand,guide_price as guidePrice,produce_time as produceTime,car_type as carType
        from
            t_car
        where
            car_type = #{carType}
    </select>
</mapper>
```

测试程序

```java
package com.jkweilai.mybatis.test;

import com.jkweilai.mybatis.mapper.CarMapper;
import com.jkweilai.mybatis.pojo.Car;
import com.jkweilai.mybatis.utils.SqlSessionUtil;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * CarMapper测试类
 * @author 老杜
 */
public class CarMapperTest {

    @Test
    public void testSelectByCarType(){
        CarMapper mapper = (CarMapper) SqlSessionUtil.openSession().getMapper(CarMapper.class);
        List<Car> cars = mapper.selectByCarType("燃油车");
        cars.forEach(System.out::println);
    }
}

```

执行结果：

![1660617419624-9c151df7-6c1e-469b-8489-2694009e68b6.png](./note-07-MyBatis小技巧.assets/1660617419624-9c151df7-6c1e-469b-8489-2694009e68b6-796268.png)

通过执行可以清楚的看到，sql语句中是带有 ? 的，这个 ? 就是大家在JDBC中所学的占位符，专门用来接收值的。

把“燃油车”以String类型的值，传递给 ?

这就是 #{}，它会先进行sql语句的预编译，然后再给占位符传值

### 使用${}

同样的需求，我们使用${}来完成

CarMapper.xml文件修改如下：

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.jkweilai.mybatis.mapper.CarMapper">
    <select id="selectByCarType" resultType="com.jkweilai.mybatis.pojo.Car">
        select
            id,car_num as carNum,brand,guide_price as guidePrice,produce_time as produceTime,car_type as carType
        from
            t_car
        where
            <!--car_type = #{carType}-->
            car_type = ${carType}
    </select>
</mapper>
```

再次运行测试程序：

![1660617746157-bdb389c7-5752-42f7-a023-c0b8792cf604.png](./note-07-MyBatis小技巧.assets/1660617746157-bdb389c7-5752-42f7-a023-c0b8792cf604-036765.png)

出现异常了，这是为什么呢？看看生成的sql语句：

![1660617813209-5e0d9395-452b-42ed-bee2-816d41f17c30.png](./note-07-MyBatis小技巧.assets/1660617813209-5e0d9395-452b-42ed-bee2-816d41f17c30-304741.png)

很显然，${} 是先进行sql语句的拼接，然后再编译，出现语法错误是正常的，因为 燃油车 是一个字符串，在sql语句中应该添加单引号

修改：

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.jkweilai.mybatis.mapper.CarMapper">
    <select id="selectByCarType" resultType="com.jkweilai.mybatis.pojo.Car">
        select
            id,car_num as carNum,brand,guide_price as guidePrice,produce_time as produceTime,car_type as carType
        from
            t_car
        where
            <!--car_type = #{carType}-->
            <!--car_type = ${carType}-->
            car_type = '${carType}'
    </select>
</mapper>
```

再执行测试程序：

![1660618019174-42718d2b-7cdd-4c70-8a44-055258d5ad29.png](./note-07-MyBatis小技巧.assets/1660618019174-42718d2b-7cdd-4c70-8a44-055258d5ad29-791173.png)

通过以上测试，可以看出，对于以上这种需求来说，还是建议使用 #{} 的方式。

**<font style="color:#DF2A3F;">原则：能用 #{} 就不用 ${}</font>**

### 拼接表名需要使用`${}`

业务背景：实际开发中，有的表数据量非常庞大，可能会采用分表方式进行存储，比如每天生成一张表，表的名字与日期挂钩，例如：2022年8月1日生成的表：t_user20220108。2000年1月1日生成的表：t_user20000101。此时前端在进行查询的时候会提交一个具体的日期，比如前端提交的日期为：2000年1月1日，那么后端就会根据这个日期动态拼接表名为：t_user20000101。有了这个表名之后，将表名拼接到sql语句当中，返回查询结果。那么大家思考一下，拼接表名到sql语句当中应该使用#{} 还是 ${} 呢？

使用#{}会是这样：select \* from 't_car'

使用${}会是这样：select \* from t_car

```xml
<select id="selectAllByTableName" resultType="car">
  select
  id,car_num as carNum,brand,guide_price as guidePrice,produce_time as produceTime,car_type as carType
  from
  ${tableName}
</select>
```

```java
/**
 * 根据表名查询所有的Car
 * @param tableName
 * @return
 */
List<Car> selectAllByTableName(String tableName);
```

```java
@Test
public void testSelectAllByTableName(){
    CarMapper mapper = SqlSessionUtil.openSession().getMapper(CarMapper.class);
    List<Car> cars = mapper.selectAllByTableName("t_car");
    cars.forEach(System.out::println);
}
```

执行结果：

![1660798482963-16baeed4-cd22-4a0e-a631-6f49ee9f101b.png](./note-07-MyBatis小技巧.assets/1660798482963-16baeed4-cd22-4a0e-a631-6f49ee9f101b-409951.png)

还有以下这些情况也需要使用`${}`：

1. 动态拼接列名、表名
2. group by或order by后面的列名
3. SQL函数名、关键字等。

### 使用`${}`和`#{}`的黄金法则

<font style="color:rgb(15, 17, 21);">既然</font><font style="color:rgb(15, 17, 21);"> </font><code><font style="color:rgb(15, 17, 21);background-color:rgb(235, 238, 242);">${}</font></code><font style="color:rgb(15, 17, 21);"> </font><font style="color:rgb(15, 17, 21);">这么危险，使用时必须遵守以下规则：</font>

1. **<font style="color:rgb(15, 17, 21);">绝不用于用户直接输入</font>**<font style="color:rgb(15, 17, 21);">：永远不要将未经处理的用户输入（如来自前端的表单数据）直接用在</font><font style="color:rgb(15, 17, 21);"> </font><code><font style="color:rgb(15, 17, 21);background-color:rgb(235, 238, 242);">${}</font></code><font style="color:rgb(15, 17, 21);"> </font><font style="color:rgb(15, 17, 21);">中。</font>
2. **<font style="color:rgb(15, 17, 21);">严格校验</font>**<font style="color:rgb(15, 17, 21);">：如果必须使用 </font><code><font style="color:rgb(15, 17, 21);background-color:rgb(235, 238, 242);">${}</font></code><font style="color:rgb(15, 17, 21);"> 来接收外部参数，必须在服务端进行严格的校验。只允许预期的、安全的值。</font>

## 模糊查询

需求：查询奔驰系列的汽车。【只要品牌brand中含有奔驰两个字的都查询出来。】

### 第一种：concat函数

```xml
<select id="selectLikeByBrand" resultType="Car">
  select
  id,car_num as carNum,brand,guide_price as guidePrice,produce_time as produceTime,car_type as carType
  from
  t_car
  where
  brand like concat('%',#{brand},'%')
</select>
```

执行结果：

![1660804562331-8febb980-24d8-4e18-8e4b-6416f28aa251.png](./note-07-MyBatis小技巧.assets/1660804562331-8febb980-24d8-4e18-8e4b-6416f28aa251-523889.png)

### 第二种：双引号方式

```xml
<select id="selectLikeByBrand" resultType="Car">
  select
  id,car_num as carNum,brand,guide_price as guidePrice,produce_time as produceTime,car_type as carType
  from
  t_car
  where
  brand like "%"#{brand}"%"
</select>
```

![1660804873326-12cbff35-f4fe-4019-90a4-7b758d7c4e39.png](./note-07-MyBatis小技巧.assets/1660804873326-12cbff35-f4fe-4019-90a4-7b758d7c4e39-225455.png)

## typeAliases

我们来观察一下CarMapper.xml中的配置信息：

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.jkweilai.mybatis.mapper.CarMapper">

    <select id="selectAll" resultType="com.jkweilai.mybatis.pojo.Car">
        select
            id,car_num as carNum,brand,guide_price as guidePrice,produce_time as produceTime,car_type as carType
        from
            t_car
        order by carNum ${key}
    </select>

    <select id="selectByCarType" resultType="com.jkweilai.mybatis.pojo.Car">
        select
            id,car_num as carNum,brand,guide_price as guidePrice,produce_time as produceTime,car_type as carType
        from
            t_car
        where
            car_type = '${carType}'
    </select>
</mapper>
```

resultType属性用来指定查询结果集的封装类型，这个名字太长，可以起别名吗？可以。

在mybatis-config.xml文件中使用typeAliases标签来起别名，包括两种方式：

### 第一种方式：typeAlias

```xml
<typeAliases>
  <typeAlias type="com.jkweilai.mybatis.pojo.Car" alias="Car"/>
</typeAliases>
```

* 首先要注意typeAliases标签的放置位置，如果不清楚的话，可以看看错误提示信息。
* typeAliases标签中的typeAlias可以写多个。
* typeAlias：
  * type属性：指定给哪个类起别名
  * alias属性：别名。
    * alias属性不是必须的，如果缺省的话，type属性指定的类型名的简类名作为别名。
    * alias是大小写不敏感的。也就是说假设alias="Car"，再用的时候，可以CAR，也可以car，也可以Car，都行。

### 第二种方式：package

如果一个包下的类太多，每个类都要起别名，会导致typeAlias标签配置较多，所以mybatis用提供package的配置方式，只需要指定包名，该包下的所有类都自动起别名，别名就是简类名。并且别名不区分大小写。

```xml
<typeAliases>
  <package name="com.jkweilai.mybatis.pojo"/>
</typeAliases>
```

package也可以配置多个的。

### 在SQL映射文件中用一下

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.jkweilai.mybatis.mapper.CarMapper">

    <select id="selectAll" resultType="CAR">
        select
            id,car_num as carNum,brand,guide_price as guidePrice,produce_time as produceTime,car_type as carType
        from
            t_car
        order by carNum ${key}
    </select>

    <select id="selectByCarType" resultType="car">
        select
            id,car_num as carNum,brand,guide_price as guidePrice,produce_time as produceTime,car_type as carType
        from
            t_car
        where
            car_type = '${carType}'
    </select>
</mapper>
```

运行测试程序：正常。

## mappers

SQL映射文件的配置方式包括四种：

* resource：从类路径中加载
* url：从指定的全限定资源路径中加载
* class：使用映射器接口实现类的完全限定类名
* package：将包内的映射器接口实现全部注册为映射器

### resource

这种方式是从类路径中加载配置文件，所以这种方式要求SQL映射文件必须放在resources目录下或其子目录下。

```xml
<mappers>
  <mapper resource="org/mybatis/builder/AuthorMapper.xml"/>
  <mapper resource="org/mybatis/builder/BlogMapper.xml"/>
  <mapper resource="org/mybatis/builder/PostMapper.xml"/>
</mappers>
```

### url

这种方式显然使用了绝对路径的方式，这种配置对SQL映射文件存放的位置没有要求，随意。

```xml
<mappers>
  <mapper url="file:///var/mappers/AuthorMapper.xml"/>
  <mapper url="file:///var/mappers/BlogMapper.xml"/>
  <mapper url="file:///var/mappers/PostMapper.xml"/>
</mappers>
```

### class

如果使用这种方式必须满足以下条件：

* SQL映射文件和mapper接口放在同一个目录下。
* SQL映射文件的名字也必须和mapper接口名一致。

```xml
<!-- 使用映射器接口实现类的完全限定类名 -->
<mappers>
  <mapper class="org.mybatis.builder.AuthorMapper"/>
  <mapper class="org.mybatis.builder.BlogMapper"/>
  <mapper class="org.mybatis.builder.PostMapper"/>
</mappers>
```

将CarMapper.xml文件移动到和mapper接口同一个目录下：

* **<font style="color:#DF2A3F;">在resources目录下新建：com/jkweilai/mybatis/mapper</font>**【这里千万要注意：**<font style="color:#F5222D;">不能这样新建 com.jkweilai.mybatis.dao</font>**】
* 将CarMapper.xml文件移动到mapper目录下
* 修改mybatis-config.xml文件

```xml
<mappers>
  <mapper class="com.jkweilai.mybatis.mapper.CarMapper"/>
</mappers>
```

运行程序：正常！！！

### package

如果class较多，可以使用这种package的方式，但前提条件和上一种方式一样。

```xml
<!-- 将包内的映射器接口实现全部注册为映射器 -->
<mappers>
  <package name="com.jkweilai.mybatis.mapper"/>
</mappers>
```

## 插入数据时获取自动生成的主键

前提是：主键是自动生成的。

第一种方式：可以先插入用户数据，再写一条查询语句获取id，然后再插入user_id字段。【比较麻烦】

第二种方式：mybatis提供了一种方式更加便捷。

```java
/**
 * 获取自动生成的主键
 * @param car
 */
void insertUseGeneratedKeys(Car car);
```

```xml
<insert id="insertUseGeneratedKeys" useGeneratedKeys="true" keyProperty="id">
  insert into t_car(id,car_num,brand,guide_price,produce_time,car_type) values(null,#{carNum},#{brand},#{guidePrice},#{produceTime},#{carType})
</insert>
```

```java
@Test
public void testInsertUseGeneratedKeys(){
    CarMapper mapper = SqlSessionUtil.openSession().getMapper(CarMapper.class);
    Car car = new Car();
    car.setCarNum("5262");
    car.setBrand("BYD汉");
    car.setGuidePrice(30.3);
    car.setProduceTime("2020-10-11");
    car.setCarType("新能源");
    mapper.insertUseGeneratedKeys(car);
    SqlSessionUtil.openSession().commit();
    System.out.println(car.getId());
}
```

# 项目演练

## 1.还上面的项目，新建一个模块起名mybatis-005-antic，然后添加一些依赖

![image-20261112194922778](./note-07-MyBatis小技巧.assets/image-20261112194922778.png)

## 2.新建一个包org.kenny.mybatis.util,在里面新建一个SqlSessionUtil类，然后把004项目里面的SqlSessionUtil的代码除了包声明的语句之外的所有内容复制粘贴过来

![image-20261112195414826](./note-07-MyBatis小技巧.assets/image-20261112195414826.png)

## 3.在main/resources文件夹里面新建一个mybatis-config.xml文件（我们已经配置了模板）

![image-20261112195643891](./note-07-MyBatis小技巧.assets/image-20261112195643891.png)

## 4.把001项目的CarMapper.xml拷贝粘贴过来，并且在mybatis-config.xml中引入

![image-20261112195912495](./note-07-MyBatis小技巧.assets/image-20261112195912495.png)

## 5.新建一个entity包，把Car类粘贴进来，然后新建一个mapper包，在里面新建一个CarMapper接口，给它添加一个selectAllByTableName方法声明。

![image-20261112201333325](./note-07-MyBatis小技巧.assets/image-20261112201333325.png)

## 6.然后我们在CarMapper.xml里面添加一条select语句，我们故意把动态表名用#{}来获取

![image-20261112201914044](./note-07-MyBatis小技巧.assets/image-20261112201914044.png)

## 7.在test/java里面新建一个包org.kenny.mybatis.test,然后创建一个测试类，添加一些测试代码

![image-20261112202814410](./note-07-MyBatis小技巧.assets/image-20261112202814410.png)

## 8.运行测试，发现测试不通过。

![image-20261112202913971](./note-07-MyBatis小技巧.assets/image-20261112202913971.png)

## 9.回到CarMapper.xml,我们把它#{}改为${},注意，只有拼接表名或者在group by 或者order by 后面的动态sql才使用后者

![image-20261112203249881](./note-07-MyBatis小技巧.assets/image-20261112203249881.png)

## 10.运行测试，发现成功了

![image-20261112203356196](./note-07-MyBatis小技巧.assets/image-20261112203356196.png)

### 使用${}的场景

![image-20261112212731784](./note-07-MyBatis小技巧.assets/image-20261112212731784.png)

# 项目演练2-模糊查询

## 2.1还是上面的项目，我们添加一个根据品牌来查询汽车的测试代码，首先在CarMapper.xml需要添加sql语句

![image-20261113111902828](./note-07-MyBatis小技巧.assets/image-20261113111902828.png)

## 2.2,需要给CarMapper接口添加一个selectCarByBrand方法，只有方法名和sql语句的id必须一致。

![image-20261113112035103](./note-07-MyBatis小技巧.assets/image-20261113112035103.png)

## 2.3 然后我们编写一个测试代码

![image-20261113112203661](./note-07-MyBatis小技巧.assets/image-20261113112203661.png)

## 2.4运行测试，顺利通过

![image-20261113112258026](./note-07-MyBatis小技巧.assets/image-20261113112258026.png)

## 2.5模糊查询的写法2：

![image-20261113114019663](./note-07-MyBatis小技巧.assets/image-20261113114019663.png)

# 演练3.mybatis类型别名，把一个很长的类名映射为一个短类名

## 3.1需要在mybatis-config.xml里面配置

![image-20261113121422511](./note-07-MyBatis小技巧.assets/image-20261113121422511.png)

### 注意：

```
在 MyBatis 中，框架本身不支持直接为 Mapper.xml 的 namespace（命名空间）配置别名。
MyBatis 仅在全局配置文件中提供 <typeAliases> 标签，用于为 Java 实体类起别名。
```

## 3.2然后在CarMapper.xml中就可以把类型简写了

![image-20261113122605174](./note-07-MyBatis小技巧.assets/image-20261113122605174.png)

## 3.3测试通过

![image-20261113122640253](./note-07-MyBatis小技巧.assets/image-20261113122640253.png)

## 3.5还有更好的方法，就是指定包名，然后该包下面的所有类都会自动简化

![image-20261113123252686](./note-07-MyBatis小技巧.assets/image-20261113123252686.png)

## 3.6CarMapper.xml里面还是这么写

![image-20261113123329352](./note-07-MyBatis小技巧.assets/image-20261113123329352.png)

## 3.7运行测试，顺利通过

![image-20261113123409043](./note-07-MyBatis小技巧.assets/image-20261113123409043.png)

# 演练4.mybatis配置mapper的其他写法

## 4.1 一共有种写法，不过，使用url发方式已经很少用，我们可以测试邮一下使用class的写法，我们把005项目复制一份改名XXX-005-antic2，然后在main/resources文件夹里面新建一个文件夹（注意：这里不是包，在resources文件夹里面应该）org/kenny/mybatis/mapper,把CarMapper.xml移动到他的里面，此时mapper接口的路径就会Mapper.xml的理解对应上了

![image-20261113131802280](./note-07-MyBatis小技巧.assets/image-20261113131802280.png)

## 4.2然后我们修改一下mybatis-config.xml，在mappers里面把mapper的属性从resource改为class

![image-20261113132028222](./note-07-MyBatis小技巧.assets/image-20261113132028222.png)

## 4.3 然后运行测试，发现顺利通过

![image-20261113132206606](./note-07-MyBatis小技巧.assets/image-20261113132206606.png)

### 

## 4.4这种方式在我们有很多mapper接口的时候需要写多个映射，我们还有更好的写法，就是使用package,这个package的路径需要和mapper.xml的路径一致

![image-20261113133238178](./note-07-MyBatis小技巧.assets/image-20261113133238178.png)

## 4.5然后运行测试，发现顺利通过

![image-20261113133320508](./note-07-MyBatis小技巧.assets/image-20261113133320508.png)

### 注意，以后我们开发都使用这种方式，也就是使用package的方式

# 演练5.插入数据时自动获取生成的主键

## 5.1使用005-antic2项目，我们在mapper.xml文件里面添加一条sql语句，需要设置useGeneratedKeys为true并且指定接收主键的属性

![image-20261113134925499](./note-07-MyBatis小技巧.assets/image-20261113134925499.png)

## 5.2 然后在mapper接口里面声明这个方法

![image-20261113135056764](./note-07-MyBatis小技巧.assets/image-20261113135056764.png)

## 5.3 然后我们写应该测试方法

![image-20261113135513947](./note-07-MyBatis小技巧.assets/image-20261113135513947.png)

## 5.4运行测试，顺利通过，并且可以成功获取到id

![image-20261113135612033](./note-07-MyBatis小技巧.assets/image-20261113135612033.png)

![image-20261113135639670](./note-07-MyBatis小技巧.assets/image-20261113135639670.png)

### 注意：这个功能以后可能要用到

