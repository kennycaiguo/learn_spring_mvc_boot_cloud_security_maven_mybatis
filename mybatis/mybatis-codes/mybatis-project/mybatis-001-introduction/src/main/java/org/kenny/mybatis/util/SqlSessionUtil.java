package org.kenny.mybatis.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;

public class SqlSessionUtil {
    private static SqlSessionFactory factory = null;
    private  SqlSessionUtil(){} //把构造方法设置为私有，防止在外面用这个类创建对象
    //因为整个项目只需要一个SessionFactory对象，我们只需要在静态代码块里面创建一次
    static {
      try(InputStream in = Resources.getResourceAsStream("mybatis-config.xml")){
          factory = new SqlSessionFactoryBuilder().build(in); //在try(){}里面使用的变量必须是静态变量

      } catch (Exception e) {
          e.printStackTrace();
      }

    }

    /**
     * 调用SqlSessionFactory的openSession方法返回一个SqlSession对象
     * @return 返回值：SqlSession对象
     */
    public static SqlSession openSession(){
        return factory.openSession();
    }
}
