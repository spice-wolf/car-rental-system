package com.pengwei.www.orm.util;

import com.pengwei.www.orm.bean.DatabaseBean;
import com.pengwei.www.orm.bean.TableBean;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * sql操作的工具类
 *
 * @author spice
 * @date 2020/12/06 23:34
 */
public class SqlUtil {

    /**
     * 执行sql语句
     *
     * @param connection 数据库连接
     * @param sql sql语句
     * @param params 所需要的数据
     * @return 执行结果
     * @throws Exception 执行过程中的异常
     */
    private int executeDML(Connection connection, String sql, Object[] params) throws Exception {
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            fillData(preparedStatement, params);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new Exception(e.getMessage(), e.getCause());
        }
    }

    /**
     * 填充数据到sql语句中
     *
     * @param preparedStatement PreparedStatement对象
     * @param params 要填充的数据
     * @throws SQLException 填充数据过程出现异常
     */
    private static void fillData(PreparedStatement preparedStatement, Object[] params) throws SQLException {
        if (params == null || params.length == 0) {
            return;
        }

        // 注意：数组下标从0开始，但是PreparedStatement的预编译参数序号从1开始
        for (int i = 0; i < params.length; i++) {
            preparedStatement.setObject(i + 1, params[i]);
        }
    }

    /**
     * 将一条数据插入到数据库中
     *
     * @param connection 数据库连接
     * @param obj 存储数据的对象
     * @return 操作结果
     * @throws Exception 执行插入操作出现异常
     */
    public int insert(Connection connection, Object obj) throws Exception {
        Class cls = obj.getClass();
        // 获取字节码文件对应的数据库表信息
        TableBean tableInfo = DatabaseBean.getInstance().getRelationMap().get(cls);

        // 用来存放预编译insert语句时所需的所有参数
        List<Object> params = new ArrayList<>();
        // 完整的insert语句的前半段，例如：INSERT INTO user (
        StringBuilder sqlOne = new StringBuilder("INSERT INTO ").append(tableInfo.getTableName()).append(" (");
        // 完整的insert语句的后半段，例如：) VALUES (
        StringBuilder sqlTwo = new StringBuilder(") VALUES (");
        // 获取对象(obj)中的所有变量
        Field[] fields = cls.getDeclaredFields();

        for (Field f : fields) {
            String fieldName = f.getName();
            // 获取变量的值
            Object fieldValue = ReflectUtil.invokeGetter(obj, fieldName);

            // 判断变量的值是否为null
            // 如果为null，则insert语句将不包含该变量所代表的字段名
            // 如果不为null，则在insert语句中拼接上该变量所代表的字段名，表示该值要被插入到数据库表中
            if (fieldValue != null) {
                sqlOne.append(", ").append(NameUtil.columnNameConvertor(fieldName));
                sqlTwo.append(", ?");
                // 存储变量的值到参数列表中(与字段名一一对应)
                params.add(fieldValue);
            }
        }

        // 拼接形成完整的insert语句
        StringBuilder sql = new StringBuilder(sqlOne.toString().replaceFirst(", ", ""))
                .append(sqlTwo.append(")").toString().replaceFirst(", ", ""));

        return executeDML(connection, sql.toString(), params.toArray());
    }
}
