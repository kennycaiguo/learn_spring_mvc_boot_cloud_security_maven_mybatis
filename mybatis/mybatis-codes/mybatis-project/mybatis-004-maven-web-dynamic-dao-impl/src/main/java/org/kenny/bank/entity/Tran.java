package org.kenny.bank.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

//一个对象实例对应t_tran数据表的一条记录
public class Tran {
    private Integer id;
    private String fromActNo;
    private String toActNo;
    private BigDecimal amount;
    private LocalDateTime tranTime;
    private String tranNo;

    public Tran() {}

    public Tran(String fromActNo, String toActNo, BigDecimal amount, LocalDateTime tranTime, String tranNo) {
        this.fromActNo = fromActNo;
        this.toActNo = toActNo;
        this.amount = amount;
        this.tranTime = tranTime;
        this.tranNo = tranNo;
    }
    public Tran(Integer id, String fromActNo, String toActNo, BigDecimal amount, LocalDateTime tranTime, String tranNo) {
        this.id = id;
        this.fromActNo = fromActNo;
        this.toActNo = toActNo;
        this.amount = amount;
        this.tranTime = tranTime;
        this.tranNo = tranNo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFromActNo() {
        return fromActNo;
    }

    public void setFromActNo(String fromActNo) {
        this.fromActNo = fromActNo;
    }

    public String getToActNo() {
        return toActNo;
    }

    public void setToActNo(String toActNo) {
        this.toActNo = toActNo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getTranTime() {
        return tranTime;
    }

    public void setTranTime(LocalDateTime tranTime) {
        this.tranTime = tranTime;
    }

    public String getTranNo() {
        return tranNo;
    }

    public void setTranNo(String tranNo) {
        this.tranNo = tranNo;
    }

    @Override
    public String toString() {
        return "Tran{" +
                "id=" + id +
                ", fromActNo='" + fromActNo + '\'' +
                ", toActNo='" + toActNo + '\'' +
                ", amount=" + amount +
                ", tranTime=" + tranTime +
                ", tranNo='" + tranNo + '\'' +
                '}';
    }
}
