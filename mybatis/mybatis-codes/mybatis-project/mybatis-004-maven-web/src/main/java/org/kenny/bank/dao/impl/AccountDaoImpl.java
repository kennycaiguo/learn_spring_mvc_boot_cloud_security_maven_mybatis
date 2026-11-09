package org.kenny.bank.dao.impl;

import org.apache.ibatis.session.SqlSession;
import org.kenny.bank.common.exception.DataAccessException;
import org.kenny.bank.common.util.SqlSessionUtil;
import org.kenny.bank.dao.AccountDao;
import org.kenny.bank.entity.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AccountDaoImpl implements AccountDao {
    //定义一个日志记录器
    private  static final Logger logger = LoggerFactory.getLogger(AccountDaoImpl.class);

    @Override
    public Account selectByAcctNo(String accNo) {
        try(SqlSession sqlSession = SqlSessionUtil.openSession();){
             Account  account = (Account)sqlSession.selectOne("selectByActNo", accNo);
             return account;
        } catch (Exception e) {
            e.printStackTrace();
            //记录日志
            logger.error("查询账号{}查询账户信息异常",accNo);
            //将异常转化为自定义数据访问异常，往上抛
            throw new DataAccessException("查询账号"+accNo+"查询账户信息异常",e);
        }
    }

    @Override
    public int updateBalance(Account newAccount) {
        try( SqlSession sqlSession = SqlSessionUtil.openSession()){
            int count = sqlSession.update("updateByActNo", newAccount);
            sqlSession.commit();//必须提交，否则数据库的数据不会改变。
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            //记录日志
            logger.error("更新账户{}余额异常",newAccount.getActNo());
            //将异常转化为自定义数据访问异常，往上抛
            throw new DataAccessException("更新账号"+newAccount.getActNo()+"查询账户余额异常",e);
        }

    }
}
