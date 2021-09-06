package com.hlkj.pojo;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

public class Orders {
    @Id
    private String id;

    @Column(name = "user_id")
    private String userId;
    @Column(name = "receiver_name")
    private String receiverName;
    @Column(name = "receiver_mobile")
    private String receiverMobile;
    @Column(name = "receiver_address")
    private String receiverAddress;
    @Column(name = "total_amount")
    private Integer totalAmount;
    @Column(name = "real_pay_amount")
    private Integer realPayAmount;
    @Column(name = "post_amount")
    private Integer postAmount;
    @Column(name = "pay_method")
    private Integer payMethod;
    @Column(name = "left_msg")
    private String leftMsg;
    @Column(name = "extand")
    private String extand;
    @Column(name = "is_comment")
    private Integer isComment;
    @Column(name = "is_delete")
    private Integer isDelete;
    @Column(name = "created_time")
    private Date created_time;
    @Column(name = "updated_time")
    private Date updatedTime;

    @Override
    public String toString() {
        return "Orders{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", receiverName='" + receiverName + '\'' +
                ", receiverMobile='" + receiverMobile + '\'' +
                ", receiverAddress='" + receiverAddress + '\'' +
                ", totalAmount=" + totalAmount +
                ", realPayAmount=" + realPayAmount +
                ", postAmount=" + postAmount +
                ", payMethod=" + payMethod +
                ", leftMsg='" + leftMsg + '\'' +
                ", extand='" + extand + '\'' +
                ", isComment=" + isComment +
                ", isDelete=" + isDelete +
                ", created_time=" + created_time +
                ", updatedTime=" + updatedTime +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverMobile() {
        return receiverMobile;
    }

    public void setReceiverMobile(String receiverMobile) {
        this.receiverMobile = receiverMobile;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getRealPayAmount() {
        return realPayAmount;
    }

    public void setRealPayAmount(Integer realPayAmount) {
        this.realPayAmount = realPayAmount;
    }

    public Integer getPostAmount() {
        return postAmount;
    }

    public void setPostAmount(Integer postAmount) {
        this.postAmount = postAmount;
    }

    public Integer getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(Integer payMethod) {
        this.payMethod = payMethod;
    }

    public String getLeftMsg() {
        return leftMsg;
    }

    public void setLeftMsg(String leftMsg) {
        this.leftMsg = leftMsg;
    }

    public String getExtand() {
        return extand;
    }

    public void setExtand(String extand) {
        this.extand = extand;
    }

    public Integer getIsComment() {
        return isComment;
    }

    public void setIsComment(Integer isComment) {
        this.isComment = isComment;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Date getCreated_time() {
        return created_time;
    }

    public void setCreated_time(Date created_time) {
        this.created_time = created_time;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }
}