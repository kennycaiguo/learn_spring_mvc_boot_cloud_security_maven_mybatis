# MyBatis中接口代理机制及使用

## 分析当前程序存在的问题

我们来看一下DaoImpl的代码

```java
package com.jkweilai.bank.dao.impl;

import com.jkweilai.bank.dao.AccountDao;
import com.jkweilai.bank.pojo.Account;
import com.jkweilai.bank.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;

public class AccountDaoImpl implements AccountDao {
    @Override
    public Account selectByActno(String actno) {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        Account act = (Account)sqlSession.selectOne("account.selectByActno", actno);
        return act;
    }

    @Override
    public int update(Account act) {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        int count = sqlSession.update("account.update", act);
        return count;
    }
}

```

我们不难发现，这个dao实现类中的方法代码很固定，基本上就是一行代码，通过SqlSession对象调用insert、delete、update、select等方法，这个类中的方法没有任何业务逻辑，既然是这样，**<font style="color:#E8323C;">这个类我们能不能动态的生成</font>**，以后可以不写这个类吗？答案：可以。

## MyBatis的接口代理机制

在MyBatis中，可以通过以下代码为DAO接口生成一个代理类，底层使用了JDK动态代理：

```java
AccountDao accountDao = (AccountDao)sqlSession.getMapper(AccountDao.class);
```

使用以上代码的前提是：**<font style="color:#E8323C;">AccountMapper.xml文件中的namespace必须和dao接口的全限定名称一致，id必须和dao接口中方法名一致。</font>**

![1743664727431-954c376f-2ec6-464c-a449-2bf3d4dcea86.png](./note06-MyBatis中接口代理机制及使用.assets/1743664727431-954c376f-2ec6-464c-a449-2bf3d4dcea86-832115.png)

将service中获取dao对象的代码再次修改，如下：

![1743664656687-88bdde54-4162-4274-a9b2-e5b887d185d7.png](./note06-MyBatis中接口代理机制及使用.assets/1743664656687-88bdde54-4162-4274-a9b2-e5b887d185d7-166244.png)

测试前数据：

![1660611965066-a4188327-2a59-4125-bd2d-0aa40fb8bf3a.png](./note06-MyBatis中接口代理机制及使用.assets/1660611965066-a4188327-2a59-4125-bd2d-0aa40fb8bf3a-231106.png)

测试后数据：

![1744115171914-664211b3-d213-48c6-82c3-c4264a004f83.png](./note06-MyBatis中接口代理机制及使用.assets/1744115171914-664211b3-d213-48c6-82c3-c4264a004f83-870748.png)

# mybatis提供了一种机制，可以根据sql语句和dao接口动态生成dao的实现类。底层使用的是jdk的代理机制，在内存中生成实现类。以后我们创建dao（最好改为Mapper）接口的时候，使用下面的步骤

## 1>使用AccountDao accountDao = (AccountDao)sqlSession.getMapper(AccountDao.class);

## 2>Mapper.xml中的namespace必须是dao接口的全限定路径，如org.kenny.bank.dao.AccountDao

## 3>mapper.xml中sql语句的id必须是dao里面的方法名，否则没有效果



# 演练：

## 1.把004项目复制一份，改名mybatis-005-dynamic-dao-impl，然后把它里面的target文件夹删除，修改一下pom.xml中的项目名称

![image-20261110175636016](./note06-MyBatis中接口代理机制及使用.assets/image-20261110175636016.png)

## 2.然后，在idea中的005里面的pom.xml上面点击右键——》添加为maven项目

![image-20261110175756923](./note06-MyBatis中接口代理机制及使用.assets/image-20261110175756923.png)

## 3.把它部署到tomcat

![image-20261110180015544](./note06-MyBatis中接口代理机制及使用.assets/image-20261110180015544.png)

## 4.重启服务器，可以工作正常

![image-20261110180119350](./note06-MyBatis中接口代理机制及使用.assets/image-20261110180119350.png)

## 5.把AccountMapper.xml里面的namespace改为：org.kenny.bank.dao.AccountDao，把sql语句的id改为dao的方法名称

![image-20261110183445952](./note06-MyBatis中接口代理机制及使用.assets/image-20261110183445952.png)

## 6.对TranMapper.xml的内容做和上面一样的处理

![image-20261110183705055](./note06-MyBatis中接口代理机制及使用.assets/image-20261110183705055.png)

## 7.修改serviceimpl里面的获取dao实现类的代码

![image-20261110184600993](./note06-MyBatis中接口代理机制及使用.assets/image-20261110184600993.png)

## 8.然后就可以把dao包里面的impl包和里面的实现类删除了

![image-20261110190312879](./note06-MyBatis中接口代理机制及使用.assets/image-20261110190312879.png)

![image-20261110190329997](./note06-MyBatis中接口代理机制及使用.assets/image-20261110190329997.png)

### 注意：在service实现类里面把它引用这两个dao实现类的代码删除

## 9.我们来做一次转账，这一次从124账户转账到123账户

![image-20261110190655816](./note06-MyBatis中接口代理机制及使用.assets/image-20261110190655816.png)

## 10.页面上说转账成功

![image-20261110190736298](./note06-MyBatis中接口代理机制及使用.assets/image-20261110190736298.png)

## 11.刷新数据库，发现的确转账成功

![image-20261110190817538](./note06-MyBatis中接口代理机制及使用.assets/image-20261110190817538.png)

## 12.以后用mybatis的项目只需要定义接口，不需要写实现类，前提是前面提到的3个注意点（步骤1>-3>）

## 13.mybatis有一个约定，最好把我们的dao类mapper.xxxMapper.我们可以把dao包复制一份改一下名字

![image-20261110191525624](./note06-MyBatis中接口代理机制及使用.assets/image-20261110191525624.png)

## 14.需要修改一下service实现了的代码

![image-20261110191749908](./note06-MyBatis中接口代理机制及使用.assets/image-20261110191749908.png)

## 15.然后需要修改两个sqlMapper.xml的代码

![image-20261110192140026](./note06-MyBatis中接口代理机制及使用.assets/image-20261110192140026.png)

![image-20261110192158640](./note06-MyBatis中接口代理机制及使用.assets/image-20261110192158640.png)

## 16.然后可以包dao包删除

![image-20261110192246914](./note06-MyBatis中接口代理机制及使用.assets/image-20261110192246914.png)

## 17.重启服务器，再做一次转账

![image-20261110192400288](./note06-MyBatis中接口代理机制及使用.assets/image-20261110192400288.png)

## 18.页面说转账成功

![image-20261110192430148](./note06-MyBatis中接口代理机制及使用.assets/image-20261110192430148.png)

## 19.查看数据库，发现的确转账成功

![image-20261110192508547](./note06-MyBatis中接口代理机制及使用.assets/image-20261110192508547.png)

## 20.最好连mapper.xml所在的文件夹也改为mapper

![image-20261110192808842](./note06-MyBatis中接口代理机制及使用.assets/image-20261110192808842.png)

## 21.重启服务器，在做一次转账

![image-20261110192918691](./note06-MyBatis中接口代理机制及使用.assets/image-20261110192918691.png)

![image-20261110192935595](./note06-MyBatis中接口代理机制及使用.assets/image-20261110192935595.png)

## 22.刷新数据库，发现转账的确成功

![image-20261110193005613](./note06-MyBatis中接口代理机制及使用.assets/image-20261110193005613.png)

## 为了不影响后面的学习，我们把这个项目改名:mybatis-004-maven-web-dynamic-dao-impl

![image-20261110195446728](./note06-MyBatis中接口代理机制及使用.assets/image-20261110195446728.png)