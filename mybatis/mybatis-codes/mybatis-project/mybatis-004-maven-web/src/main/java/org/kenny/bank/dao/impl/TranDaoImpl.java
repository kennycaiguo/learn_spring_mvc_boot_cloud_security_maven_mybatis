package org.kenny.bank.dao.impl;

import org.apache.ibatis.session.SqlSession;
import org.kenny.bank.common.exception.DataAccessException;
import org.kenny.bank.common.util.SqlSessionUtil;
import org.kenny.bank.dao.TranDao;
import org.kenny.bank.entity.Tran;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TranDaoImpl implements TranDao {

    @Override
    public int insert(Tran record) {
          return SqlSessionUtil.openSession().insert("insertTran", record);
    }
}
