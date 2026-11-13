package org.kenny.bank.service.impl;

import org.apache.ibatis.session.SqlSession;
import org.kenny.bank.common.exception.BusinessException;
import org.kenny.bank.common.util.SqlSessionUtil;
import org.kenny.bank.common.util.TranNoGenerator;
import org.kenny.bank.dao.AccountDao;
import org.kenny.bank.dao.TranDao;
import org.kenny.bank.dao.impl.AccountDaoImpl;
import org.kenny.bank.dao.impl.TranDaoImpl;
import org.kenny.bank.dto.AccountDTO;
import org.kenny.bank.entity.Account;
import org.kenny.bank.entity.Tran;
import org.kenny.bank.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

public class AccountServiceImpl implements AccountService {
    //定义一个日志记录器
    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);
    //定义dao实例对象
    private AccountDao accountDao = new AccountDaoImpl();
    private TranDao tranDao = new TranDaoImpl();
    //核心转账方法实现
    //必须在这里控制事务
    @Override
    public Tran transfer(AccountDTO accountDTO) {
        //重要的操作最好做日志记录
        logger.info("开始转账，从{}向{}转账{}",accountDTO.getFromAccount(),accountDTO.getToAccount(),accountDTO.getAmount());

        Tran tran=null;
        try(SqlSession sqlSession = SqlSessionUtil.openSession()){
            try{
                //执行核心业务
                //1.查询转出账户的信息，看看余额是否充足
                Account fromAcc = accountDao.selectByAcctNo(accountDTO.getFromAccount());
                //如果余额不足
                if(fromAcc.getBalance().compareTo(accountDTO.getAmount()) < 0){
                    //记录日志
                    logger.warn("{}账户余额不足",accountDTO.getFromAccount());
                    //抛异常
                    throw new BusinessException("BALANCE_NOT_ENOUGH","账户余额不足");
                }
                //2.查询转入账户的信息
                Account toAcc = accountDao.selectByAcctNo(accountDTO.getToAccount());
                //3.更新转出账户的余额
                fromAcc.setBalance(fromAcc.getBalance().subtract(accountDTO.getAmount()));
                int count = accountDao.updateBalance(fromAcc); //更新之后需要保存到数据库
                if(count != 1){ //更新失败
                    logger.warn("更新转出账户失败"); //记录日志
                    //抛异常
                    throw new BusinessException("BALANCE_UPDATE_FAIL","更新转出账户余额失败");
                }
                //4.更新转入账户的余额
                toAcc.setBalance(toAcc.getBalance().add(accountDTO.getAmount()));
                count += accountDao.updateBalance(toAcc);
                if(count != 2){ //更新失败
                    logger.warn("更新转入账户失败"); //记录日志
                    //抛异常
                    throw new BusinessException("BALANCE_UPDATE_FAIL","更新转入账户余额失败");
                }

                //5.保存转账记录
                String tranNo = TranNoGenerator.generate();
                tran = new Tran(accountDTO.getFromAccount(),accountDTO.getToAccount(),
                        accountDTO.getAmount(),LocalDateTime.now(),tranNo);
                count += tranDao.insert(tran);

                if(count != 3){
                    logger.warn("保存转账记录失败"); //记录日志
                    //抛异常
                    throw new BusinessException("INSERT_TRAN_FAIL","保存转账记录失败");
                }

                //提交事务,最好也做一下日志记录
                logger.info("转账成功，从{}向{}转账{}",accountDTO.getFromAccount(),accountDTO.getToAccount(),accountDTO.getAmount());
                sqlSession.commit();
            }  catch (Exception e) {
                e.printStackTrace();
                throw new BusinessException("TRANSFER_ERROR","转账失败");
            } finally {
                //解绑
               SqlSessionUtil.closeSession();
            }
        } catch (Exception e) {
            throw new BusinessException("TRANSFER_ERROR","转账失败");
        }

        return tran;
    }
}
