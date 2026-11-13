package org.kenny.bank.mapper;

import org.kenny.bank.entity.Tran;

public interface TranMapper {

    /**
     * 保存交易记录
     * @param record ： 交易记录
     * @return 返回1表示保存成功
     */
    int insert(Tran record);
}
