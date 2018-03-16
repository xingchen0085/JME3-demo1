package cn.myxinge.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @Auth:chenxinghua
 * @Date:2018\3\16 0016 14:03
 * @Description: 百万级数据插入测试
 * 数据库：Mysql
 */
public class BatchDataInsert {
    private static Connection conn;

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
//            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mini_pay_dev_180305?rewriteBatchedStatements=true", "root", "root");
//            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?rewriteBatchedStatements=true", "root", "root");
            conn = DriverManager.getConnection("jdbc:mysql://192.168.1.10:3306/pay_dev20180301?rewriteBatchedStatements=true", "pay", "123456");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String sql = "INSERT INTO `t_trade_payment_order` (`ID`, `MERCHANT_NO`, `MERCHANT_NAME`, `MERCHANT_ORDER_NO`, `PRODUCT_NAME` , `ORDER_AMOUNT`, `ORDER_DATE`, `ORDER_TIME`, `PAY_STATUS`, `ORDER_IP` , `RETURN_URL`, `NOTIFY_URL`, `CANCEL_REASON`, `ORDER_PERIOD`, `EXPIRE_TIME` , `PAY_WAY_NO`, `PAY_WAY_NAME`, `REMARK`, `TRX_TYPE`, `TRX_NO` , `PAY_TYPE_NO`, `PAY_TYPE_NAME`, `FUND_INTO_TYPE`, `REFUND_FLAG`, `REFUND_AMOUNT` , `SPLIT_FLAG`, `GUARANTEE_FLAG`, `SPLIT_INFO`, `VERSION`, `CURR_MONTH` , `CREATE_TIME`, `CREATE_DATE`, `UPDATE_TIME`, `UPDATE_DATE`) VALUES";

    public static void main(String[] args) throws SQLException {
        batchInsert(conn);
//        test(conn);
    }


    private static void batchInsert(Connection conn) {
        // 开始时间
        Long begin = new Date().getTime();
        // sql前缀
        String prefix = sql;
        try {
            // 保存sql后缀
            StringBuffer suffix = new StringBuffer();
            // 设置事务为非自动提交
            conn.setAutoCommit(false);
            // Statement st = conn.createStatement();
            // 比起st，pst会更好些
            PreparedStatement pst = conn.prepareStatement("");
            // 外层循环，总提交事务次数
            for (int i = 1; i <= 200; i++) {
                // 第次提交步长
                for (int j = 1; j <= 8000; j++) {
                    // 构建sql后缀
                    suffix.append("('" +
                            UUID.randomUUID().toString().replace("-", "").toLowerCase() + "'" +
                            ",'88882018022810001146'," +
                            "'test20180228'," +
                            "concat('TEST00000101'," + j + ")," +
                            "concat('TSEST'," + j + ")," +
                            "'12.000000'," +
                            "'2018-03-16 15:52:43'," +
                            "'2018-03-16 15:52:43'," +
                            "'SUCCESS'," +
                            "'221.0.0.1'," +
                            "'http://127.0.0.1:9004/pc/return_url.jsp'," +
                            "'http://127.0.0.1:9004/pc/notify_url.jsp'," +
                            "NULL," +
                            "'5'," + "'" +
                            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "'" +
                            ",'KFT'," +
                            "'快付通'," +
                            "NULL," +
                            "'1'," + "'" +
                            genericTrxNoFromDate() + "'" +
                            ",'6001'," +
                            "'网银在线'," +
                            "'2'," +
                            "NULL," +
                            "NULL," +
                            "'0'," +
                            "'0'," +
                            "NULL," +
                            "NULL," +
                            "3," + "'2018-02-16 15:52:43'," +
                            "'2018-03-16 15:52:43'," +
                            "'2018-03-16 15:52:43'," +
                            "'2018-03-16 15:52:43'" +
                            "),");
                }
                // 构建完整sql
                String sql = prefix + suffix.substring(0, suffix.length() - 1);

                // 添加执行sql
                pst.addBatch(sql);
                // 执行操作
                pst.executeBatch();
                // 提交事务
                conn.commit();
                // 清空上一次添加的数据
                suffix = new StringBuffer();
            }
            // 头等连接
            pst.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // 结束时间
        Long end = new Date().getTime();
        // 耗时
        System.out.println("cast : " + (end - begin) / 1000 + " s");
    }

    /**流水号*/
    public static String genericTrxNoFromDate() {
//        String format = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());//生成前17位
        String pre = "20180316155057350";
        String randomStr = "";
        for (int i = 0; i < 9; i++) {
            int nu = (int) (Math.random() * 9);
            randomStr += nu;
        }

        return pre.concat(randomStr);
    }

    private static void test(Connection conn){
        // 开始时间
        Long begin = new Date().getTime();
        // sql前缀
        String prefix = "INSERT INTO test values";
        try {
            // 保存sql后缀
            StringBuffer suffix = new StringBuffer();
            // 设置事务为非自动提交
            conn.setAutoCommit(false);
            // Statement st = conn.createStatement();
            // 比起st，pst会更好些
            PreparedStatement pst = conn.prepareStatement("");
            // 外层循环，总提交事务次数
            for (int i = 1; i <= 100; i++) {
                // 第次提交步长
                for (int j = 1; j <= 7000; j++) {
                    // 构建sql后缀
                    suffix.append("("+j+","+
                            "'" +
                            UUID.randomUUID().toString().replace("-", "").toLowerCase() + "'" +
                            ","+
                            "'" +
                            UUID.randomUUID().toString().replace("-", "").toLowerCase() + "'" +
                            ","+
                            "'" +
                            UUID.randomUUID().toString().replace("-", "").toLowerCase() + "'" +
                            "),");
                }
                // 构建完整sql
                String sql = prefix + suffix.substring(0, suffix.length() - 1);

                // 添加执行sql
                pst.addBatch(sql);
                // 执行操作
                pst.executeBatch();
                // 提交事务
                conn.commit();
                // 清空上一次添加的数据
                suffix = new StringBuffer();
            }
            // 头等连接
            pst.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // 结束时间
        Long end = new Date().getTime();
        // 耗时
        System.out.println("cast : " + (end - begin) / 1000 + " s");
    }
}
