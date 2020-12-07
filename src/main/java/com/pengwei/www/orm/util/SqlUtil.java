package com.pengwei.www.orm.util;

import com.pengwei.www.orm.bean.ColumnBean;
import com.pengwei.www.orm.bean.DatabaseBean;
import com.pengwei.www.orm.bean.TableBean;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * sql操作的工具类
 *
 * @author spice
 * @date 2020/12/06 23:34
 */
@SuppressWarnings("unchecked")
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
    private static int executeDML(Connection connection, String sql, Object[] params) throws Exception {
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
     * @throws SQLException 填充数据过程出现的异常
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
     * @return 插入的条数
     * @throws Exception 执行插入操作出现的异常
     */
    public static int insert(Connection connection, Object obj) throws Exception {
        Class<?> cls = obj.getClass();
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

    /**
     * 根据主键id删除一条数据
     *
     * @param connection 数据库连接
     * @param cls 操作对象的Class
     * @param id 数据的id（主键）
     * @return 删除的条数
     * @throws Exception 执行删除操作出现的异常
     */
    public static int delete(Connection connection, Class<?> cls, Object id) throws Exception {
        // 获取字节码文件对应的数据库表信息
        TableBean tableBean = DatabaseBean.getInstance().getRelationMap().get(cls);
        // 获取该数据库表的唯一主键的信息
        ColumnBean uniquePriKey = tableBean.getUniquePriKey();
        // 生成delete语句
        StringBuilder sql = new StringBuilder("DELETE FROM ").append(tableBean.getTableName()).append(" WHERE ")
                .append(uniquePriKey.getColumnName()).append(" = ?");

        return executeDML(connection, sql.toString(), new Object[]{ id });
    }

    /**
     * 根据对象（需包含主键的值）删除一条数据
     *
     * @param connection 数据库连接
     * @param obj 要删除的操作
     * @return 删除的条数
     * @throws Exception 执行删除操作出现的异常
     */
    public static int delete(Connection connection, Object obj) throws Exception {
        Class<?> cls = obj.getClass();
        // 获取字节码文件对应的数据库表信息
        TableBean tableBean = DatabaseBean.getInstance().getRelationMap().get(cls);
        // 获取该数据库表的唯一主键的信息
        ColumnBean uniquePriKey = tableBean.getUniquePriKey();
        // 获取唯一主键的值
        Object priKeyValue = ReflectUtil.invokeGetter(obj, uniquePriKey.getColumnName());

        return delete(connection, cls, priKeyValue);
    }

    /**
     * 更新数据库的一条数据
     *
     * @param connection 数据库连接
     * @param obj 要更新的对象（至少包含主键的值，可以包含要更新的值）
     * @param columnNames 要更新的字段
     * @return 更新的条数
     * @throws Exception 执行更新操作出现的异常
     */
    public static int update(Connection connection, Object obj, String[] columnNames) throws Exception {
        Class<?> cls = obj.getClass();
        // 获取字节码文件对应的数据库表信息
        TableBean tableBean = DatabaseBean.getInstance().getRelationMap().get(cls);
        // 获取该数据库表的唯一主键的信息
        ColumnBean uniquePriKey = tableBean.getUniquePriKey();

        // 用来存放预编译update语句时所需的所有参数
        List<Object> params = new ArrayList<>();
        // 完整的update语句的前半段，例如：UPDATE user SET
        StringBuilder sqlOne = new StringBuilder("UPDATE ").append(tableBean.getTableName()).append(" SET ");
        // 完整的update语句的后半段，例如：WHERE user_id = ?
        StringBuilder sqlTwo = new StringBuilder(" WHERE ").append(uniquePriKey.getColumnName()).append(" = ?");

        // 将所要修改的字段拼入update语句中
        for (String columnName : columnNames) {
            if (columnName == null) {
                // 有可能传入null值，但是null值应该被跳过
                continue;
            }
            Object columnValue = ReflectUtil.invokeGetter(obj, columnName);
            params.add(columnValue);
            sqlOne.append(", ").append(NameUtil.columnNameConvertor(columnName)).append(" = ?");
        }

        // 最后，将该数据库表的唯一主键的值加入参数列表中
        params.add(ReflectUtil.invokeGetter(obj, uniquePriKey.getColumnName()));

        // 生成完整的update语句
        StringBuilder sql = new StringBuilder(sqlOne.toString().replaceFirst(", ", "")).append(sqlTwo);

        return executeDML(connection, sql.toString(), params.toArray());
    }

    /**
     * 查询多条数据
     *
     * @param connection 数据库连接
     * @param sql sql语句
     * @param obj 查询的对象
     * @param params 查询条件参数
     * @return 查询得到的结果集
     * @throws Exception 执行查询操作出现的异常
     */
    public static <E> List<E> queryRows(Connection connection, String sql, E obj, Object[] params) throws Exception {
        List<E> list = null;

        ResultSet rs = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            fillData(preparedStatement, params);
            rs = preparedStatement.executeQuery();

            // 获取查询结果集中的元数据
            ResultSetMetaData metaData = rs.getMetaData();

            // 外层循环(即while循环)负责遍历行数据
            // 内层循环(即for循环)负责遍历列数据
            while (rs.next()) {
                if (list == null) {
                    list = new ArrayList<>();
                }

                // 调用无参构造器,创建一个po类对象，用于存储一条数据记录

                Object objectInRow = obj.getClass().getConstructor().newInstance();

                // 把查询得到的一条数据记录封装成一个po类对象
                for (int i = 0; i < metaData.getColumnCount(); i++) {
                    // 获取字段名
                    String columnName = metaData.getColumnName(i + 1);
                    // 获取字段值
                    Object columnValue = rs.getObject(i + 1);

                    // 把字段值存储到po类对象中
                    ReflectUtil.invokeSetter(objectInRow, columnName, columnValue);
                }

                // 将所有的po类对象封装成一个集合
                list.add((E) objectInRow);
            }

            return list;
        } catch (Exception e) {
            throw new Exception(e);
        } finally {
            if (Objects.nonNull(rs)) {
                rs.close();
            }
        }
    }

    /**
     * 查询一条数据
     *
     * @param connection 数据库连接对象
     * @param sql sql语句
     * @param cls 查询的对象
     * @param params 查询条件参数
     * @return 查询得到的结果
     * @throws Exception 执行查询操作出现的异常
     */
    public static Object queryUniqueRow(Connection connection, String sql, Class<?> cls, Object[] params) throws Exception {
        Object poObject = null;

        ResultSet rs = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            fillData(preparedStatement, params);
            rs = preparedStatement.executeQuery();

            // 获取查询结果集中的元数据
            ResultSetMetaData metaData = rs.getMetaData();

            if (rs.next()) {
                // 调用无参构造器,创建一个po类对象
                poObject = cls.getConstructor().newInstance();

                // 把查询得到的一条数据记录封装成一个po类对象
                for (int i = 0; i < metaData.getColumnCount(); i++) {
                    // 获取字段名
                    String columnName = metaData.getColumnName(i + 1);
                    // 获取字段值
                    Object columnValue = rs.getObject(i + 1);

                    // 把字段值存储到po类对象中
                    ReflectUtil.invokeSetter(poObject, columnName, columnValue);
                }
            }

            return poObject;
        } catch (SQLException e) {
            throw new Exception(e);
        } finally {
            if (Objects.nonNull(rs)) {
                rs.close();
            }
        }
    }

    /**
     * 查询一个值
     *
     * @param connection 数据库连接对象
     * @param sql sql语句
     * @param params 查询条件参数
     * @return 查询得到的值
     * @throws Exception 执行查询操作出现的异常
     */
    public static Object queryValue(Connection connection, String sql, Object[] params) throws Exception {
        Object value = null;

        ResultSet rs = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            fillData(preparedStatement, params);
            rs = preparedStatement.executeQuery();

            // 获取查询的结果(一个值)
            if (rs.next()) {
                value = rs.getObject(1);
            }

            return value;
        } catch (SQLException e) {
            throw new Exception(e);
        } finally {
            if (Objects.nonNull(rs)) {
                rs.close();
            }
        }
    }
}
