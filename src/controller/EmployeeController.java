package controller;

/**
 * 社員情報管理コントローラー
 */

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.EmployeeBean;
import service.EmployeeService;

@SuppressWarnings("serial")
public class EmployeeController extends HttpServlet {
 public void doPost(HttpServletRequest request, HttpServletResponse response)
 throws ServletException, IOException {

 try {
  // index.htmlから送信されたIDとPassWordの値を取得。
 String id = request.getParameter("id");
 String password = request.getParameter("password");

  // IDとPassWordと元に、社員情報を検索する関数の呼び出し、結果をJSPに渡す処理

 EmployeeService employeeService =new EmployeeService();
 EmployeeBean employeeBean = employeeService.search(id,password);
 request.setAttribute("EmployeeBean", employeeBean);

 } catch (Exception e) {
 e.printStackTrace();
 } finally {
 ServletContext context = this.getServletContext();
 RequestDispatcher dispatcher = context.getRequestDispatcher("/index.jsp");
 dispatcher.forward(request, response);
 }
 }
}