# learn_spring_mvc_boot_cloud_security_maven_mybatis
学习Spring相关的内容如mvc,springboot,spring cloud,mybatis,maven等等

Mybatis配置文件参考

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

### 
