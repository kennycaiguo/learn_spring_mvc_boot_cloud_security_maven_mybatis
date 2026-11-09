package org.kenny.bank.dao.impl;

import org.apache.ibatis.session.SqlSession;
import org.kenny.bank.common.exception.DataAccessException;
import org.kenny.bank.common.util.SqlSessionUtil;
import org.kenny.bank.dao.TranDao;
import org.kenny.bank.entity.Tran;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TranDaoImpl implements TranDao {
    private static final Logger logger= LoggerFactory.getLogger(TranDaoImpl.class);
    @Override
    public int insert(Tran record) {
        try( SqlSession sqlSession = SqlSessionUtil.openSession();){
            int count = sqlSession.insert("insertTran", record);
            sqlSession.commit(); //需要提交
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            //记录日志
            logger.error("保存转账记录错误,详情：转出账号{}到转入账号{}，交易金额{}，交易时间{}，交易流水号{}",
                    record.getFromActNo(), record.getToActNo(), record.getAmount(),record.getTranTime(), record.getTranNo());
            //把异常封装后往上抛
            throw new DataAccessException("保存转账记录错误",e);
        }

    }
}
