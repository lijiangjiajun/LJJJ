package entity;

import common.OrderStatus;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private String id;
    private Integer account_id;
    private String account_name;
    //订单项内容也需要存储到当前订单内
    public List<OrderItem> orderItemList = new ArrayList<>();

    public OrderStatus order_status;   //订单描述

    public OrderStatus getOrder_statusDesc(){
        return order_status;
    }

    public String getOrder_status(){
        return order_status.getDesc();
    }


    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public double getDiscount(){
        return (this.getTotal_moneyInt()-this.getActual_amountInt())*1.0/100;
    }

    private String create_time;
    private String account_time;
    private String finish_time;
    private Integer actual_amount;
    private Integer total_money;
    private OrderStatus orderStatus;

    //订单项的的内容也需要存储到当前订单内


    public void setId(String id) {
        this.id = id;
    }

    public void setAccount_id(Integer account_id) {
        this.account_id = account_id;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public void setAccount_time(String account_time) {
        this.account_time = account_time;
    }

    public void setFinish_time(String finish_time) {
        this.finish_time = finish_time;
    }

    public void setActual_amount(Integer actual_amount) {
        this.actual_amount = actual_amount;
    }

    public void setTotal_money(Integer total_money) {
        this.total_money = total_money;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getId() {
        return id;
    }

    public Integer getAccount_id() {
        return account_id;
    }

    public String getCreate_time() {
        return create_time;
    }

    public String getAccount_time() {
        return account_time;
    }

    public String getFinish_time() {
        return finish_time;
    }

    public Integer getActual_amountInt() {
        return actual_amount;
    }

    public double getActual_amount(){
        return actual_amount*1.0/100;
    }

    public double getTotal_money(){
        return total_money*1.0 /100;
    }

    public int getTotal_moneyInt() {
        return total_money;
    }



    public OrderStatus getOrderStatus() {
        return orderStatus;
    }
}
