# MyBatis的高级映射及延迟加载

模块名：mybatis-009-advanced-mapping

准备数据库表：一个班级对应多个学生。班级表：t_clazz。学生表：t_student

```sql
drop table if exists t_clazz;
create table t_clazz(
  cid int primary key,
  cname varchar(255)
);
insert into t_clazz(cid,cname) values(1001, '高三1班');
insert into t_clazz(cid,cname) values(1002, '高三2班');

drop table if exists t_student;
create table t_student(
  sid int primary key auto_increment,
  sname varchar(255),
  cid int
);
insert into t_student(sname,cid) values('张三', 1001);
insert into t_student(sname,cid) values('李四', 1001);
insert into t_student(sname,cid) values('王五', 1001);
insert into t_student(sname,cid) values('赵六', 1002);
insert into t_student(sname,cid) values('钱七', 1002);

select * from t_clazz;
select * from t_student;
```

![1661132819754-23adfa1c-b325-41bc-8520-0355fe4c41cc.png](./note11-MyBatis的高级映射及延迟加载.assets/1661132819754-23adfa1c-b325-41bc-8520-0355fe4c41cc-279147.png)

![1661132610940-7d55793b-df68-48f4-924f-99db58220da9.png](./note11-MyBatis的高级映射及延迟加载.assets/1661132610940-7d55793b-df68-48f4-924f-99db58220da9-260417.png)

创建pojo：Student、Clazz

```java
package com.jkweilai.mybatis.pojo;

/**
 * 学生类
 * @author 老杜
 */
public class Student {
    private Integer sid;
    private String sname;
    //......
}

```

```java
package com.jkweilai.mybatis.pojo;

/**
 * 班级类
 * @author 老杜
 */
public class Clazz {
    private Integer cid;
    private String cname;
    //......
}

```

创建mapper接口：StudentMapper、ClazzMapper

创建mapper映射文件：StudentMapper.xml、ClazzMapper.xml

## 多对一

多种方式，常见的包括三种：

* 第一种方式：一条SQL语句，级联属性映射。
* 第二种方式：一条SQL语句，association。
* 第三种方式：两条SQL语句，分步查询。（这种方式常用：优点一是可复用。优点二是支持懒加载。）

### 第一种方式：级联属性映射

pojo类Student中添加一个属性：Clazz clazz; 表示学生关联的班级对象。

```java
package com.jkweilai.mybatis.pojo;

/**
 * 学生类
 * @author 老杜
 */
public class Student {
    private Integer sid;
    private String sname;
    private Clazz clazz;

    public Clazz getClazz() {
        return clazz;
    }

    public void setClazz(Clazz clazz) {
        this.clazz = clazz;
    }

    @Override
    public String toString() {
        return "Student{" +
                "sid=" + sid +
                ", sname='" + sname + '\'' +
                ", clazz=" + clazz +
                '}';
    }

    public Student() {
    }

    public Student(Integer sid, String sname) {
        this.sid = sid;
        this.sname = sname;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }
}

```

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.jkweilai.mybatis.mapper.StudentMapper">

    <resultMap id="studentResultMap" type="Student">
        <id property="sid" column="sid"/>
        <result property="sname" column="sname"/>
        <result property="clazz.cid" column="cid"/>
        <result property="clazz.cname" column="cname"/>
    </resultMap>

    <select id="selectBySid" resultMap="studentResultMap">
        select s.*, c.* from t_student s join t_clazz c on s.cid = c.cid where sid = #{sid}
    </select>

</mapper>
```

```java
package com.jkweilai.mybatis.test;

import com.jkweilai.mybatis.mapper.StudentMapper;
import com.jkweilai.mybatis.pojo.Student;
import com.jkweilai.mybatis.utils.SqlSessionUtil;
import org.junit.jupiter.api.Test;

public class StudentMapperTest {
    @Test
    public void testSelectBySid(){
        StudentMapper mapper = SqlSessionUtil.openSession().getMapper(StudentMapper.class);
        Student student = mapper.selectBySid(1);
        System.out.println(student);
    }
}

```

执行结果：

![1661134833921-9452c17c-3461-4c7f-8807-55aa3e089506.png](./note11-MyBatis的高级映射及延迟加载.assets/1661134833921-9452c17c-3461-4c7f-8807-55aa3e089506-888219.png)

### 第二种方式：association

其他位置都不需要修改，只需要修改resultMap中的配置：association即可。

```xml
<resultMap id="studentResultMap" type="Student">
  <id property="sid" column="sid"/>
  <result property="sname" column="sname"/>
  <association property="clazz" javaType="Clazz">
    <id property="cid" column="cid"/>
    <result property="cname" column="cname"/>
  </association>
</resultMap>
```

association翻译为：关联。

学生对象关联一个班级对象。

### 第三种方式：分步查询

其他位置不需要修改，只需要修改以及添加以下三处：

第一处：association中select位置填写sqlId。sqlId=namespace+id。其中column属性作为这条子sql语句的条件。

```xml
<resultMap id="studentResultMap" type="Student">
  <id property="sid" column="sid"/>
  <result property="sname" column="sname"/>
  <association property="clazz"
               select="com.jkweilai.mybatis.mapper.ClazzMapper.selectByCid"
               column="cid"/>
</resultMap>

<select id="selectBySid" resultMap="studentResultMap">
  select s.* from t_student s where sid = #{sid}
</select>
```

第二处：在ClazzMapper接口中添加方法

```java
package com.jkweilai.mybatis.mapper;

import com.jkweilai.mybatis.pojo.Clazz;

/**
 * Clazz映射器接口
 * @author 老杜
 */
public interface ClazzMapper {

    /**
     * 根据cid获取Clazz信息
     * @param cid
     * @return
     */
    Clazz selectByCid(Integer cid);
}

```

第三处：在ClazzMapper.xml文件中进行配置

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.jkweilai.mybatis.mapper.ClazzMapper">
    <select id="selectByCid" resultType="Clazz">
        select * from t_clazz where cid = #{cid}
    </select>
</mapper>
```

执行结果，可以很明显看到先后有两条sql语句执行：

![1661151746372-e3c91810-bb5f-4308-8dd5-e421e11bff50.png](./note11-MyBatis的高级映射及延迟加载.assets/1661151746372-e3c91810-bb5f-4308-8dd5-e421e11bff50-706160.png)

分步优点：

* 第一个优点：代码复用性增强。
* 第二个优点：支持延迟加载。【暂时访问不到的数据可以先不查询。提高程序的执行效率。】

## 多对一延迟加载

要想支持延迟加载，非常简单，只需要在association标签中添加fetchType="lazy"即可。

修改StudentMapper.xml文件：

```xml
<resultMap id="studentResultMap" type="Student">
  <id property="sid" column="sid"/>
  <result property="sname" column="sname"/>
  <association property="clazz"
               select="com.jkweilai.mybatis.mapper.ClazzMapper.selectByCid"
               column="cid"
               fetchType="lazy"/>
</resultMap>
```

我们现在只查询学生名字，修改测试程序：

```java
public class StudentMapperTest {
    @Test
    public void testSelectBySid(){
        StudentMapper mapper = SqlSessionUtil.openSession().getMapper(StudentMapper.class);
        Student student = mapper.selectBySid(1);
        //System.out.println(student);
        // 只获取学生姓名
        String sname = student.getSname();
        System.out.println("学生姓名：" + sname);
    }
}
```

![1661151882965-633f8039-c309-4657-8ed1-44f836d5b1e4.png](./note11-MyBatis的高级映射及延迟加载.assets/1661151882965-633f8039-c309-4657-8ed1-44f836d5b1e4-184239.png)

如果后续需要使用到学生所在班级的名称，这个时候才会执行关联的sql语句，修改测试程序：

```java
public class StudentMapperTest {
    @Test
    public void testSelectBySid(){
        StudentMapper mapper = SqlSessionUtil.openSession().getMapper(StudentMapper.class);
        Student student = mapper.selectBySid(1);
        //System.out.println(student);
        // 只获取学生姓名
        String sname = student.getSname();
        System.out.println("学生姓名：" + sname);
        // 到这里之后，想获取班级名字了
        String cname = student.getClazz().getCname();
        System.out.println("学生的班级名称：" + cname);
    }
}
```

![1661151926961-a0affa2d-2d89-4b67-8cc0-bf8604ded4fc.png](./note11-MyBatis的高级映射及延迟加载.assets/1661151926961-a0affa2d-2d89-4b67-8cc0-bf8604ded4fc-168323.png)

通过以上的执行结果可以看到，只有当使用到班级名称之后，才会执行关联的sql语句，这就是延迟加载。

在mybatis中如何开启全局的延迟加载呢？需要setting配置，如下：

![1661136161612-a7c3cc7f-fe89-4245-a297-1572d8384566.png](./note11-MyBatis的高级映射及延迟加载.assets/1661136161612-a7c3cc7f-fe89-4245-a297-1572d8384566-333391.png)

```xml
<settings>
  <setting name="lazyLoadingEnabled" value="true"/>
</settings>
```

**<font style="color:#E8323C;">把fetchType="lazy"去掉。</font>**

执行以下程序：

```java
public class StudentMapperTest {
    @Test
    public void testSelectBySid(){
        StudentMapper mapper = SqlSessionUtil.openSession().getMapper(StudentMapper.class);
        Student student = mapper.selectBySid(1);
        //System.out.println(student);
        // 只获取学生姓名
        String sname = student.getSname();
        System.out.println("学生姓名：" + sname);
        // 到这里之后，想获取班级名字了
        String cname = student.getClazz().getCname();
        System.out.println("学生的班级名称：" + cname);
    }
}
```

![1661151954051-4b5b94f7-a69d-4d21-b220-1a840862fb85.png](./note11-MyBatis的高级映射及延迟加载.assets/1661151954051-4b5b94f7-a69d-4d21-b220-1a840862fb85-974858.png)

通过以上的测试可以看出，我们已经开启了全局延迟加载策略。

开启全局延迟加载之后，所有的sql都会支持延迟加载，如果某个sql你不希望它支持延迟加载怎么办呢？将fetchType设置为eager：

```xml
<resultMap id="studentResultMap" type="Student">
  <id property="sid" column="sid"/>
  <result property="sname" column="sname"/>
  <association property="clazz"
               select="com.jkweilai.mybatis.mapper.ClazzMapper.selectByCid"
               column="cid"
               fetchType="eager"/>
</resultMap>
```

![1661151998854-a31c19fb-0af9-4d69-894d-e8086d9c0333.png](./note11-MyBatis的高级映射及延迟加载.assets/1661151998854-a31c19fb-0af9-4d69-894d-e8086d9c0333-008781.png)

这样的话，针对某个特定的sql，你就关闭了延迟加载机制。

后期我们要不要开启延迟加载机制，主要看实际的业务需求是怎样的。

## 一对多

一对多的实现，通常是在一的一方中有List集合属性。

在Clazz类中添加List<Student> stus; 属性。

```java
public class Clazz {
    private Integer cid;
    private String cname;
    private List<Student> stus;
    // set get方法
    // 构造方法
    // toString方法
}
```

一对多的实现通常包括两种实现方式：

* 第一种方式：collection
* 第二种方式：分步查询

### 第一种方式：collection

```java
package com.jkweilai.mybatis.mapper;

import com.jkweilai.mybatis.pojo.Clazz;

/**
 * Clazz映射器接口
 * @author 老杜
 */
public interface ClazzMapper {

    /**
     * 根据cid获取Clazz信息
     * @param cid
     * @return
     */
    Clazz selectByCid(Integer cid);

    /**
     * 根据班级编号查询班级信息。同时班级中所有的学生信息也要查询。
     * @param cid
     * @return
     */
    Clazz selectClazzAndStusByCid(Integer cid);
}

```

```xml
<resultMap id="clazzResultMap" type="Clazz">
  <id property="cid" column="cid"/>
  <result property="cname" column="cname"/>
  <collection property="stus" ofType="Student">
    <id property="sid" column="sid"/>
    <result property="sname" column="sname"/>
  </collection>
</resultMap>

<select id="selectClazzAndStusByCid" resultMap="clazzResultMap">
  select * from t_clazz c join t_student s on c.cid = s.cid where c.cid = #{cid}
</select>
```

注意是ofType，表示“集合中的类型”。

```java
package com.jkweilai.mybatis.test;

import com.jkweilai.mybatis.mapper.ClazzMapper;
import com.jkweilai.mybatis.pojo.Clazz;
import com.jkweilai.mybatis.utils.SqlSessionUtil;
import org.junit.jupiter.api.Test;

public class ClazzMapperTest {
    @Test
    public void testSelectClazzAndStusByCid() {
        ClazzMapper mapper = SqlSessionUtil.openSession().getMapper(ClazzMapper.class);
        Clazz clazz = mapper.selectClazzAndStusByCid(1001);
        System.out.println(clazz);
    }
}

```

执行结果：

![1661149977323-90515301-4b5b-4e3e-a17f-04d5473766e7.png](./note11-MyBatis的高级映射及延迟加载.assets/1661149977323-90515301-4b5b-4e3e-a17f-04d5473766e7-634751.png)

### 第二种方式：分步查询

修改以下三个位置即可：

```xml
<resultMap id="clazzResultMap" type="Clazz">
  <id property="cid" column="cid"/>
  <result property="cname" column="cname"/>
  <!--主要看这里-->
  <collection property="stus"
              select="com.jkweilai.mybatis.mapper.StudentMapper.selectByCid"
              column="cid"/>
</resultMap>

<!--sql语句也变化了-->
<select id="selectClazzAndStusByCid" resultMap="clazzResultMap">
  select * from t_clazz c where c.cid = #{cid}
</select>
```

```java
/**
* 根据班级编号获取所有的学生。
* @param cid
* @return
*/
List<Student> selectByCid(Integer cid);
```

```xml
<select id="selectByCid" resultType="Student">
  select * from t_student where cid = #{cid}
</select>
```

执行结果：

![1661151398803-0c0e196e-6c0a-42fb-b9ee-abd42fea2026.png](./note11-MyBatis的高级映射及延迟加载.assets/1661151398803-0c0e196e-6c0a-42fb-b9ee-abd42fea2026-783332.png)

## 一对多延迟加载

一对多延迟加载机制和多对一是一样的。同样是通过两种方式：

* 第一种：fetchType="lazy"
* 第二种：修改全局的配置setting，\*\*<font style="color:#E8323C;">lazyLoadingEnabled=true，</font>\*\*如果开启全局延迟加载，想让某个sql不使用延迟加载：fetchType="eager"

# 演练1.多对一，这一章我们使用另外一个数据库：mybatis-advance

## 1.1把008项目复制一份改名mybatis-009-advanced-mapping然后把target文件夹删除，然后在pom.xml文件里面修改项目名称，然后在idea中把它作为maven项目添加进来，然后我们需要修改一下数据库，这里我们使用mybatis-advance数据库。

![image-20261115174842127](./note11-MyBatis的高级映射及延迟加载.assets/image-20261115174842127.png)

## 我们把mybatis数据库里面的t_car数据表复制一份到mybatis-advance数据库里面，然后运行一个测试方法。发现顺利通过。这就说明我们的配置没有问题。

![image-20261115174431021](./note11-MyBatis的高级映射及延迟加载.assets/image-20261115174431021.png)

## 1.2新建一个学生表和一个班级表，注意这个学生表示和mybatis数据库里面的学生表是不一样的。而且我们这里没有使用外键。

![image-20261115175354205](./note11-MyBatis的高级映射及延迟加载.assets/image-20261115175354205.png)



## 1.3把所有与t_car相关的内容删除

![image-20261115180053023](./note11-MyBatis的高级映射及延迟加载.assets/image-20261115180053023.png)

## 1.4在org/kenny/mybatis/mapper里面新建一个StudentMapper.xml

![image-20261115180535180](./note11-MyBatis的高级映射及延迟加载.assets/image-20261115180535180.png)

## 1.5在mapper包里面新建一个StudentMapper接口

![image-20261115180506394](./note11-MyBatis的高级映射及延迟加载.assets/image-20261115180506394.png)

## 1.6在entity包里面新建一个Student类和一个Clazz类，注意，这里学生类的cid顺序使用Clazz对象来代替，方便我们级联查询

![image-20261115182127022](./note11-MyBatis的高级映射及延迟加载.assets/image-20261115182127022.png)

### Student类的代码如下，注意这里Student的属性和t_student数据表的列并不是完全对应，我们需要做一些处理

```
package org.kenny.mybatis.entity;

public class Student {
    private Integer sid;
    private String sname;
    private Clazz clazz;


    public Student() {
    }

    public Student(Integer sid, String sname, Clazz clazz) {
        this.sid = sid;
        this.sname = sname;
        this.clazz = clazz;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public Clazz getClazz() {
        return clazz;
    }

    public void setClazz(Clazz clazz) {
        this.clazz = clazz;
    }

    @Override
    public String toString() {
        return "Student{" +
                "sid=" + sid +
                ", sname='" + sname + '\'' +
                ", clazz=" + clazz +
                '}';
    }
}

```

### Clazz类的代码如下

```
package org.kenny.mybatis.entity;

public class Clazz {
    private Integer cid;
    private String cname;

    public Clazz() {
    }

    public Clazz(Integer cid, String cname) {
        this.cid = cid;
        this.cname = cname;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    @Override
    public String toString() {
        return "Clazz{" +
                "cid=" + cid +
                ", cname='" + cname + '\'' +
                '}';
    }
}

```

## 1.7给StudentMapper接口添加一个根据sid查询学生信息的方法

![image-20261115182557474](./note11-MyBatis的高级映射及延迟加载.assets/image-20261115182557474.png)

## 1.8 把StudentMapper接口的全限定类名添加到StudentMapper.xml的命名空间里面

![image-20261115185816999](./note11-MyBatis的高级映射及延迟加载.assets/image-20261115185816999.png)

## 1.9在StudentMapper.xml里面添加一条select语句，id是mapper接口的方法名，注意这里不能写resultType,只能写resultMap，需要把查询到的数据封装到Student对象里面

![image-20261115185907781](./note11-MyBatis的高级映射及延迟加载.assets/image-20261115185907781.png)

## 1.10然后我们写一个resultMap，在里面封装数据，并且把它的id写到select语句里面

![image-20261115185957602](./note11-MyBatis的高级映射及延迟加载.assets/image-20261115185957602.png)

## 1.11然后我们在测试类里面添加一个测试方法：testSelectBySid，运行测试，顺利通过

![image-20261115190821739](./note11-MyBatis的高级映射及延迟加载.assets/image-20261115190821739.png)

## 1.12关联的第二种方法就是使用association标签

![image-20261115191601079](./note11-MyBatis的高级映射及延迟加载.assets/image-20261115191601079.png)

## 1.13也是可以查询到数据的

![image-20261115191630492](./note11-MyBatis的高级映射及延迟加载.assets/image-20261115191630492.png)

## 1.14还有第三种方法,叫做分步查询。这个方法比较常用是因为它支持延迟加载。好处就是可以提高效率，我们在mapper接口里面新建一个方法：selectStudentAndClazzBySid

![image-20261116091705415](./note11-MyBatis的高级映射及延迟加载.assets/image-20261116091705415.png)

## 1.15然后我们新建一个resultMap，id为：studentResultMapStep，然后我们的sql语句的id为：selectStudentAndClazzBySid，注意这里只需要进行t_student数据表的单表查询，然后我们在resultMap标签里面写和方法2类似的代码但是需要注意，此时不需要JavaType属性，并且在关联标签里面需要一条select属性，在这里导入一个配置文件的select语句的id,需要设置column属性这里是cid，在这里查询学生关联的班级信息，我们把这一条语句写到ClazzMapper.xml中,，当然，我们需要一步步来完成，先写StudentMapper.xml里面的语句。

![image-20261116112303006](./note11-MyBatis的高级映射及延迟加载.assets/image-20261116112303006.png)

## 1.16然后我们需要创建一个ClazzMapper接口和一个ClazzMapper.xml配置文件，在ClazzMapper接里面添加一个方法：selectClazzByCid

![image-20261116112450634](./note11-MyBatis的高级映射及延迟加载.assets/image-20261116112450634.png)

## 1.17然后我们在ClazzMapper.xml文件里面添加ClazzMapper接口的命名空间映射，标签添加一个select标签，id是selectClazzByCid

![image-20261116112650147](./note11-MyBatis的高级映射及延迟加载.assets/image-20261116112650147.png)

## 1.18.这样子，就基本完成，但是此时还没有开启懒加载。我们在测试类里面添加一个测试方法：selectStudentAndClazzBySid，然后运行顺利通过，不过此时我们可以看到它是由2条sql语句的，也就是分步骤了。

![image-20261116113209888](./note11-MyBatis的高级映射及延迟加载.assets/image-20261116113209888.png)

![image-20261116113148170](./note11-MyBatis的高级映射及延迟加载.assets/image-20261116113148170.png)

## 1.19要开启懒加载的话，需要在association里面设置一个fetchType="lazy"

![image-20261116113439800](./note11-MyBatis的高级映射及延迟加载.assets/image-20261116113439800.png)

## 1.20此时在测试方法里面，如果我们还是输出整个学生信息，效果和没有lazy是一样的，但是如果我们不输出班级信息，它是不会查询班级信息的。

![image-20261116113933669](./note11-MyBatis的高级映射及延迟加载.assets/image-20261116113933669.png)

## 1.21然后我们来输出一下班级学习，此时它就会查询班及表

![image-20261116114204974](./note11-MyBatis的高级映射及延迟加载.assets/image-20261116114204974.png)

## 1.22其实我们可以配置全局懒加载，需要在mybatis-config.xml里面的settings标签里面配置

![image-20261116120725987](./note11-MyBatis的高级映射及延迟加载.assets/image-20261116120725987.png)

## 1.23然后就可以把在局部配置的懒加载删除

![image-20261116120835864](./note11-MyBatis的高级映射及延迟加载.assets/image-20261116120835864.png)

## 1.24测试一下，也是可以通过的

![image-20261116120954458](./note11-MyBatis的高级映射及延迟加载.assets/image-20261116120954458.png)

## 1.25注意，可能由一种场景就是虽然我们全局设置懒加载，但是某一条sql语句我们想让他马上加载，我们可以在条sql里面设置fetchType="eager"

![image-20261116143017165](./note11-MyBatis的高级映射及延迟加载.assets/image-20261116143017165.png)

## 1.26此时即使你没有使用到Clazz的属性，它也会一起查询

![image-20261116143200892](./note11-MyBatis的高级映射及延迟加载.assets/image-20261116143200892.png)

# 演练2.一对多

## 2.1.为了做多对一我们把009项目复制一份改名：mybatis-009-advanced-mapping-one2many

![image-20261117112111975](./note11-MyBatis的高级映射及延迟加载.assets/image-20261117112111975.png)

## 2.2然后我们修改一下Clazz类，添加一个集合属性students，然后我们需要添加这个属性的getter和setter，还需要重新编写全参数构造函数和toString方法，修改后的代码如下

```
package org.kenny.mybatis.entity;

import java.util.List;

public class Clazz {
    private Integer cid;
    private String cname;
    //添加一个集合属性来保存多的一方。
    private List<Student> students;

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public Clazz() {
    }

    public Clazz(Integer cid, String cname, List<Student> students) {
        this.cid = cid;
        this.cname = cname;
        this.students = students;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    @Override
    public String toString() {
        return "Clazz{" +
                "cid=" + cid +
                ", cname='" + cname + '\'' +
                ", students=" + students +
                '}';
    }
}

```

## 2.3给ClazzMapper类添加一个selectClazzAndStusByCid方法

![image-20261117113256178](./note11-MyBatis的高级映射及延迟加载.assets/image-20261117113256178.png)

## 2.4.然后给idea安装一个插件：MybatisX

![image-20261117113746726](./note11-MyBatis的高级映射及延迟加载.assets/image-20261117113746726.png)

![image-20261117113831191](./note11-MyBatis的高级映射及延迟加载.assets/image-20261117113831191.png)

### 注意，这个插件的功能还挺多的

![image-20261117120246667](./note11-MyBatis的高级映射及延迟加载.assets/image-20261117120246667.png)

## 2.5安装了插件后，我们在mapper的左边会有一个小鸟，在方法名称上面按alt+回车，可以在mapper.xml里面生成一个sql语句标签，自动把id设置为和mapper方法名称一致。然后我们在里面编写sql代码并且写一个对应的resultMap

![image-20261117122901581](./note11-MyBatis的高级映射及延迟加载.assets/image-20261117122901581.png)

## 2.6然后我们在测试类里面添加一个测试方法：testSelectClazzAndStusByCid，运行测试，顺利通过

![image-20261117123049937](./note11-MyBatis的高级映射及延迟加载.assets/image-20261117123049937.png)

## 注意，这种方法不支持延迟加载

## 演练3 .一对多懒加载方式

## 3.1我们在ClazzMapper.xml里面新建一个select语句，id为：selectClazzAndStusByCidStep

![image-20261117130300618](./note11-MyBatis的高级映射及延迟加载.assets/image-20261117130300618.png)

## 3.2然后根据这个resultMap的id新建一个resultMap，这个resultMap的collection标签里面需要使用一条sql语句，这一条语句来自StudentMapper.xml

![image-20261117130522695](./note11-MyBatis的高级映射及延迟加载.assets/image-20261117130522695.png)

## 3.3 然后我们在ClazzMapper接口里面添加一个selectClazzAndStusByCidStep方法。

![image-20261117130656858](./note11-MyBatis的高级映射及延迟加载.assets/image-20261117130656858.png)

## 3.4然后我们需要在StudentMapper.xml文件里面添加一条select语句，注意，id是ClazzMapper.xml里面引用的id

![image-20261117130839877](./note11-MyBatis的高级映射及延迟加载.assets/image-20261117130839877.png)

## 3.5然后我们还需要在StudentMapper接口里面添加一个selectById的方法

![image-20261117130947275](./note11-MyBatis的高级映射及延迟加载.assets/image-20261117130947275.png)

## 3.6 然后我们在测试类里面添加一个测试方法，因为我们已经在全局开启了栏加载，我们先只是生成班级的名称，此时只有一条sql语句，效果如下

![image-20261117131220446](./note11-MyBatis的高级映射及延迟加载.assets/image-20261117131220446.png)

## 3.7.然后我们开启生成学生名称的语句，效果如下

![image-20261117131415135](./note11-MyBatis的高级映射及延迟加载.assets/image-20261117131415135.png)
