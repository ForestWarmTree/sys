package com.zt.sys.authority.logutil;

import ch.qos.logback.classic.spi.CallerData;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.db.DBAppenderBase;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Created by IntelliJ  IDEA
 * User: 王传威
 * Date: 2020/4/7
 * Time: 10:14
 */
public class MyDBLogAppender extends DBAppenderBase<ILoggingEvent> {
    private String insertSQL;
    private static final Method GET_GENERATED_KEYS_METHOD;

    private static final int TIME_INDEX = 1;
    private static final int MESSAGE_INDEX = 2;
    private static final int LEVEL_STRING_INDEX = 3;
    private static final int LOGGER_NAME_INDEX = 4;
    private static final int THREAD_NAME_INDEX = 5;
    private static final int CALLER_FILENAME_INDEX = 6;
    private static final int CALLER_CLASS_INDEX = 7;
    private static final int CALLER_METHOD_INDEX = 8;
    private static final int CALLER_LINE_INDEX = 9;
    /**
     * 这里是自定义日志的传入的参数
     * 如过需要增加，则按照规则继续往下加
     * 例：BUSINESS11_INDEX = 21;
     * 然后修改 bindLoggingEventArgumentsWithPreparedStatement 方法中的 i的最大值。
     * 加了几个参数，则 i 的最大值相应加上几
     */
    private static final int BUSINESS0_INDEX = 10;
    private static final int BUSINESS1_INDEX = 11;
    private static final int BUSINESS2_INDEX = 12;
    private static final int BUSINESS3_INDEX = 13;
    private static final int BUSINESS4_INDEX = 14;
    private static final int BUSINESS5_INDEX = 15;
    private static final int BUSINESS6_INDEX = 16;
    private static final int BUSINESS7_INDEX = 17;
    private static final int BUSINESS8_INDEX = 18;
    private static final int BUSINESS9_INDEX = 19;
    private static final int BUSINESS10_INDEX = 20;

    private static final StackTraceElement EMPTY_CALLER_DATA = CallerData.naInstance();

    static {
        // PreparedStatement.getGeneratedKeys() method was added in JDK 1.4
        Method getGeneratedKeysMethod;
        try {
            // the
            getGeneratedKeysMethod = PreparedStatement.class.getMethod("getGeneratedKeys", (Class[]) null);
        } catch (Exception ex) {
            getGeneratedKeysMethod = null;
        }
        GET_GENERATED_KEYS_METHOD = getGeneratedKeysMethod;
    }

    @Override
    public void start() {
        insertSQL = buildInsertSQL();
        super.start();
    }

    /**
     * 这里是将日志写入自定义表中，如表中增加了新的字段，则在sql中加上对应字段信息
     * @return
     */
    private static String buildInsertSQL() {
        return "INSERT INTO log_record(" +
                "time, message, level_string, logger_name, thread_name, " +
                "caller_filename, caller_class, caller_method, caller_line, business0, " +
                "business1, business2, business3, business4, business5, business6, business7, " +
                "business8, business9, business10)"+
                "VALUES (?, ?, ? ,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    }

    private void bindLoggingEventWithInsertStatement(PreparedStatement stmt, ILoggingEvent event) throws SQLException {
        stmt.setTimestamp(TIME_INDEX, new Timestamp(event.getTimeStamp()));
        stmt.setString(MESSAGE_INDEX, event.getFormattedMessage());
        stmt.setString(LEVEL_STRING_INDEX, event.getLevel().toString());
        stmt.setString(LOGGER_NAME_INDEX, event.getLoggerName());
        stmt.setString(THREAD_NAME_INDEX, event.getThreadName());
    }

    private void bindCallerDataWithPreparedStatement(PreparedStatement stmt, StackTraceElement[] callerDataArray) throws SQLException {
        StackTraceElement caller = extractFirstCaller(callerDataArray);
        stmt.setString(CALLER_FILENAME_INDEX, caller.getFileName());
        stmt.setString(CALLER_CLASS_INDEX, caller.getClassName());
        stmt.setString(CALLER_METHOD_INDEX, caller.getMethodName());
        stmt.setString(CALLER_LINE_INDEX, Integer.toString(caller.getLineNumber()));
    }

    void bindLoggingEventArgumentsWithPreparedStatement(PreparedStatement stmt, Object[] argArray) throws SQLException {

        int arrayLen = argArray != null ? argArray.length : 0;

        for (int i = 0; i < arrayLen && i < 11; i++) {
            stmt.setString(BUSINESS0_INDEX + i, asStringTruncatedTo254(argArray[i]));
        }
        if (arrayLen < 4) {
            for (int i = arrayLen; i < 11; i++) {
                stmt.setString(BUSINESS0_INDEX + i, null);
            }
        }
    }

    String asStringTruncatedTo254(Object o) {
        String s = null;
        if (o != null) {
            s = o.toString();
        }

        if (s == null) {
            return null;
        }
        if (s.length() <= 254) {
            return s;
        } else {
            return s.substring(0, 254);
        }
    }

    @Override
    protected void subAppend(ILoggingEvent event, Connection connection, PreparedStatement insertStatement) throws Throwable {
        bindLoggingEventWithInsertStatement(insertStatement, event);
        bindLoggingEventArgumentsWithPreparedStatement(insertStatement, event.getArgumentArray());
        // This is expensive... should we do it every time?
        bindCallerDataWithPreparedStatement(insertStatement, event.getCallerData());
        int updateCount = insertStatement.executeUpdate();
        if (updateCount != 1) {
            addWarn("Failed to insert loggingEvent");
        }
    }

    private StackTraceElement extractFirstCaller(StackTraceElement[] callerDataArray) {
        StackTraceElement caller = EMPTY_CALLER_DATA;
        if (hasAtLeastOneNonNullElement(callerDataArray))
            caller = callerDataArray[0];
        return caller;
    }

    private boolean hasAtLeastOneNonNullElement(StackTraceElement[] callerDataArray) {
        return callerDataArray != null && callerDataArray.length > 0 && callerDataArray[0] != null;
    }

    @Override
    protected Method getGeneratedKeysMethod() {
        return GET_GENERATED_KEYS_METHOD;
    }

    @Override
    protected String getInsertSQL() {
        return insertSQL;
    }

    @Override
    protected void secondarySubAppend(ILoggingEvent event, Connection connection, long eventId) throws Throwable {
    }
}
