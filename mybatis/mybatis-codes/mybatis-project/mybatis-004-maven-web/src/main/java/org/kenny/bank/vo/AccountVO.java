package org.kenny.bank.vo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

//视图对象，做数据展示。好处的可以隐藏一些隐私数据（又叫做所谓的脱敏显示），比如，银行账号只显示最后4位，其他用*代替
public class AccountVO {
    private String fromActNo;
    private String toActNo;
    private BigDecimal amount;
    private String tranTime;
    private String tranNo;


    public AccountVO(String fromActNo, String toActNo, BigDecimal amount, String tranTime, String tranNo) {
        this.fromActNo = fromActNo;
        this.toActNo = toActNo;
        this.amount = amount;
        this.tranTime = tranTime;
        this.tranNo = tranNo;
    }

    public AccountVO() {
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

    public String getTranTime() {
        return tranTime;
    }

    public void setTranTime(String tranTime) {
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
        return "AccountVO{" +
                "fromActNo='" + fromActNo + '\'' +
                ", toActNo='" + toActNo + '\'' +
                ", amount=" + amount +
                ", tranTime=" + tranTime +
                ", tranNo='" + tranNo + '\'' +
                '}';
    }
}
