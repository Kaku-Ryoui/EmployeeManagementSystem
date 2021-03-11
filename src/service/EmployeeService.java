package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import bean.EmployeeBean;

/**
 * ・社員情報検索サービス
 */

public class EmployeeService {

  // 接続情報
 /** ドライバーのクラス名 */
 private static final String POSTGRES_DRIVER = "org.postgresql.Driver";
 /** ・JDMC接続先情報 */
 private static final String JDBC_CONNECTION = "jdbc:postgresql://localhost:5432/Employee";
 /** ・ユーザー名 */
 private static final String USER = "postgres";
 /** ・パスワード */
 private static final String PASS = "postgres";
 /** ・タイムフォーマット */
 private static final String TIME_FORMAT = "yyyy/MM/dd HH:mm:ss";

 /** ・SQL UPDATE文 */
 private static final String SQL_UPDATE = "UPDATE Employee_table SET login_time = ? where id = ?";

 /** ・SQL SELECT文 */
 private static final String SQL_SELECT = "SELECT * from Employee_table where id = ? and password =?";

 EmployeeBean employeeDate = null;

  // 送信されたIDとPassWordを元に社員情報を検索
 public EmployeeBean search(String id, String password) {

 Connection connection = null;
 Statement statement = null;
 ResultSet resultSet = null;
 PreparedStatement preparedStatement = null;

 try {
  // データベースに接続
 Class.forName(POSTGRES_DRIVER);
 connection = DriverManager.getConnection(JDBC_CONNECTION, USER, PASS);
 statement = connection.createStatement();

  // 処理が流れた時間をフォーマットに合わせて生成
 Calendar cal = Calendar.getInstance();
 SimpleDateFormat sdFormat = new SimpleDateFormat(TIME_FORMAT);

  // PreparedStatementで使用するため、String型に変換
 String login_time = sdFormat.format(cal.getTime());

 /*
 * 任意のユーザーのログインタイムを更新できるように、プリペアドステートメントを記述。
 */

  // preparedStatementに実行したいSQLを格納
 preparedStatement = connection.prepareStatement(SQL_UPDATE);
 preparedStatement.setString(1, login_time);
 preparedStatement.setString(2, id);
 preparedStatement.executeUpdate();
 /*
 * UPDATEが成功したものを即座に表示
 * 任意のユーザーを検索できるように、プリペアドステートメントを記述。
 */
 preparedStatement = connection.prepareStatement(SQL_SELECT);
 preparedStatement.setString(1, id);
 preparedStatement.setString(2, password);

  // SQLを実行。実行した結果をresultSetに格納。
 resultSet = preparedStatement.executeQuery();

 while (resultSet.next()) {
 String tmpName = resultSet.getString("name");
 String tmpComment = resultSet.getString("comment");
 String tmpLoginTime = resultSet.getString("login_time");

 employeeDate = new EmployeeBean();
 employeeDate.setName(tmpName);
 employeeDate.setComment(tmpComment);
 employeeDate.setLogin_Time(tmpLoginTime);
 }

  // forName()で例外発生
 } catch (ClassNotFoundException e) {
 e.printStackTrace();

  // getConnection()、createStatement()、executeQuery()で例外発生
 } catch (SQLException e) {
 e.printStackTrace();

 } finally {
 try {

 if (resultSet != null) {
 resultSet.close();
 }
 if (statement != null) {
 statement.close();
 }
 if (connection != null) {
 connection.close();
 }

 } catch (SQLException e) {
 e.printStackTrace();
 }
 }
 return employeeDate;
 }
}
