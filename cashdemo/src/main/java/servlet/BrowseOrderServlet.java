package servlet;
//
import com.fasterxml.jackson.databind.ObjectMapper;
import common.OrderStatus;
import entity.Account;
import entity.Goods;
import entity.Order;
import entity.OrderItem;
import util.DBUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@WebServlet("/orderBrowse")
public class BrowseOrderServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");
        System.out.println("orderBrowse");


        //1.根据当前的用户id进行订单的查找
        HttpSession session = req.getSession();
        Account account = (Account)session.getAttribute("user");

        //2.查询结果可能是多个订单，List<Order>
        List<Order> orderList = this.queryOrder(account.getId());
        System.out.println(orderList);
        //3.判断查询的结果如果是null说明没有订单
        if(orderList==null){
            System.out.println("订单列表为空");
        }
        //4.如果不是空那么将list转为json发送给前端
        else{
            ObjectMapper mapper = new ObjectMapper();
           //将响应包推送给浏览器
            PrintWriter pw = resp.getWriter();
           //将list字符串转换为字符串，并将json字符串写到printwriter里
            mapper.writeValue(pw,orderList);
            Writer writer = resp.getWriter();
            writer.write(pw.toString());
       }
    }

    private List<Order> queryOrder(int accountId){
        List<Order> list = new ArrayList<>();

        Connection connection=null;
        PreparedStatement preparedStatement =null;
        ResultSet resultSet = null;
        try {
//            String sql=this.getSql("query_order_by_account.sql");//多表查询
            String sql = "select  o1.id, o1.account_id, o1.create_time, o1.finish_time, o1.actual_amount,o1.total_money ,o1.order_status, o1.account_name,o2.id as order_id, o2.goods_id, o2.goods_name, o2.goods_introduce,o2.goods_introduce,goods_num, o2.goods_unit,o2.goods_price,o2.goods_discount from `order` o1 left join order_item o2 on o1.id = o2.order_id where o1.account_id = ? order by order_id";

            connection = DBUtil.getConnection(true);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,accountId);
            resultSet=preparedStatement.executeQuery();
            Order order=null;
            while(resultSet.next()){
                //1.订单解析
//                Order order = new Order();
//                this.extractOrder(order,resultSet);
//                list.add(order);
                if(order==null){
                    order=new Order();
                    this.extractOrder(order,resultSet);
                    list.add(order);
                }
                String order_id =  resultSet.getString("order_id");
                if(!order_id.equals(order.getId())){
                    //不同的订单
                    order=new Order();
                    this.extractOrder(order,resultSet);
                    list.add(order);
                }


                //2.订单项解析
                OrderItem orderItem = new OrderItem();
                this.extractOrderItem(resultSet);
                order.orderItemList.add(orderItem);



            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            DBUtil.close((com.mysql.jdbc.Connection) connection,preparedStatement,resultSet);
        }
        return list;
    }
    //通过文件名内sql语句提取出来   I/O流
    private String getSql(String sqlName) {
        InputStream in = this.getClass().getClassLoader()
                .getResourceAsStream("script/" + sqlName);
        StringBuffer sb = new StringBuffer();
        if (in == null) {
            throw new RuntimeException("加载sql文件出错");
        } else {
            //字节流转化为字符流
            InputStreamReader isr = new InputStreamReader(in);
            BufferedReader reader = new BufferedReader(isr);
            try {
                sb.append(reader.readLine());
                String line;
                while((line=reader.readLine())!=null){
                    sb.append(line);
                }
                System.out.println("sb:"+sb);
                return sb.toString();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("转化sql语句发生异常");
            }
        }

    }

    private void extractOrder(Order order, ResultSet resultSet) throws SQLException {
        order.setId(resultSet.getString("order_id"));//
        order.setAccount_id(resultSet.getInt("account_id"));
        order.setAccount_name(resultSet.getString("account_name"));
        order.setCreate_time(resultSet.getString("create_time"));
        order.setFinish_time(resultSet.getString("finish_time"));
        order.setActual_amount(resultSet.getInt("actual_amount"));
        order.setTotal_money(resultSet.getInt("total_money"));
        order.setOrderStatus(OrderStatus.valueOf(resultSet.getInt("order_status")));
    }

    private OrderItem extractOrderItem(ResultSet resultSet) throws SQLException {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrderId(resultSet.getString("order_id"));
        orderItem.setGoodsName(resultSet.getString("goods_name"));
        orderItem.setGoodsId(resultSet.getInt("goods_id"));
        orderItem.setGoodsIntroduce(resultSet.getString("goods_introduce"));
        orderItem.setGoodsNum(resultSet.getInt("goods_num"));
        orderItem.setGoodsUnit(resultSet.getString("goods_unit"));
        orderItem.setGoodsPrice(resultSet.getInt("goods_price"));
        orderItem.setGoodsDiscount(resultSet.getInt("goods_discount"));
        return orderItem;
    }
}



//import com.fasterxml.jackson.databind.ObjectMapper;
//import common.OrderStatus;
//import entity.Account;
//import entity.Order;
//import entity.OrderItem;
//import util.DBUtil;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.awt.image.DataBuffer;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.PrintWriter;
//import java.io.Writer;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.ConcurrentModificationException;
//import java.util.List;
//import java.util.Properties;
//import java.util.function.Predicate;
//
///**
// * @ClassName BrowseOrderServlet
// * @auther REX
// * @DATE 2020/2/14 9:19
// * @Version 1.0
// **/
//@WebServlet("/orderBrowse")
//public class BrowseOrderServlet extends HttpServlet {
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        System.out.println("browseOrder");
//        req.setCharacterEncoding("UTF-8");
//        resp.setContentType("text/http; charset=UTF-8");
//        resp.setCharacterEncoding("UTF-8");
//
//        //1，根据当前的用户id进行订单的查找，写成一个函数
//
//        //2，查询结果坑公式多个订单。List<Order>
//
//        //3.判断查询的结果，如果是空的，说明没有订单
//
//        //4，如果不是空的，那么将List转为json，发送给前端
//
//        HttpSession session = req.getSession();
//        System.out.println("sessiom ready");
//        Account account = (Account) session.getAttribute("user");
//        int account_ID = account.getId();
//        System.out.println(account_ID);
//        System.out.println("getidsuccess");
//        List<Order> orderList = search(account_ID);
//
//        if(orderList == null){
//            System.out.println("没有订单");
//        }else{
//            System.out.println("end");
//            ObjectMapper mapper = new ObjectMapper();
//
//            PrintWriter pw = resp.getWriter();
//            //
//            mapper.writeValue(pw, orderList);
//            Writer writer = resp.getWriter();
//
//            writer.write((pw.toString()));
//        }
//
//    }
//    private void extractOrder(Order order, ResultSet resultSet) throws SQLException {
//        System.out.println("开始设置order");
//        order.setId(resultSet.getString("id"));
//        System.out.println(order.getId());
//        System.out.println("设置order成功");
//        System.out.println("oederid");
//        order.setAccount_id(resultSet.getInt("account_id"));
//        order.setAccount_name(resultSet.getString("account_name"));
//        order.setCreate_time(resultSet.getString("create_time"));
//        order.setFinish_time(resultSet.getString("finish_time"));
//        order.setActual_amount(resultSet.getInt("actual_amount"));
//        order.setTotal_money(resultSet.getInt("total_money"));
//        order.setOrderStatus(OrderStatus.valueOf(resultSet.getInt("order_status")));
//        System.out.println("order设置结束");
//
//
//
//    }
//
//    private OrderItem  extractOrderItem(ResultSet resultSet) throws SQLException {
//        System.out.println("开始设置item");
//        OrderItem orderItem = new OrderItem();
//        orderItem.setOrderId(resultSet.getString("order_id"));
//        System.out.println("物品id设置成功");
//        orderItem.setGoodsId(resultSet.getInt("goods_id"));
//        orderItem.setGoodsName(resultSet.getString("goods_name"));
//        orderItem.setGoodsIntroduce(resultSet.getString("goods_introduce"));
//        orderItem.setGoodsNum(resultSet.getInt("goods_num"));
//        orderItem.setGoodsUnit(resultSet.getString("goods_unit"));
//        orderItem.setGoodsPrice(resultSet.getInt("goods_price"));
//        orderItem.setGoodsDiscount(resultSet.getInt("goods_discount"));
//        return orderItem;
//
//    }
//    public List<Order> search(int accountID) {
//        Connection connection = null;
//        PreparedStatement preparedStatement = null;
//        ResultSet resultSet = null;
//        List<Order> orders = new ArrayList<>();
//
//        try {
////            InputStream is = getClass().getClassLoader().getResourceAsStream("JDBC.properties");
////            Properties properties = new Properties();
////            properties.load(is);
//
//            String sql = "select  o1.id, o1.account_id, o1.create_time, o1.finish_time, o1.actual_amount,o1.total_money ,o1.order_status, o1.account_name,o2.id as order_id, o2.goods_id, o2.goods_name, o2.goods_introduce,o2.goods_introduce,goods_num, o2.goods_unit,o2.goods_price,o2.goods_discount from `order` o1 left join order_item o2 on o1.id = o2.order_id where o1.account_id = ? order by order_id";
//
//            //sql = properties.getProperty("sql");
//            connection = DBUtil.getConnection(true);
//            preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setInt(1, accountID);
//            resultSet = preparedStatement.executeQuery();
//            List<Order> orderList = new ArrayList<>();
//            Order order = null;
//            while (resultSet.next()) {
//
//                System.out.println("kaishi order");
//                if(order == null){
//                    order = new Order();
//                    this.extractOrder(order, resultSet);
//                    orderList.add(order);
//                }
//
//                String orderId = resultSet.getString("id");
//                if(!orderId.equals(order.getId())){
//                    order = new Order();
//                    this.extractOrder(order, resultSet);
//                    orderList.add(order);
//                }
//
//                OrderItem orderItem = new OrderItem();
//                orderItem = this.extractOrderItem(resultSet);
//                //System.out.println(order);
//                order.orderItemList.add(orderItem);
//                //System.out.println(orderList);
//            }
//            return orderList;
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            DBUtil.close((com.mysql.jdbc.Connection) connection, preparedStatement, resultSet);
//        }
//        return null;
//    }
//}
