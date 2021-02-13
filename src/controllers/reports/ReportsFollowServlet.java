package controllers.reports;

import java.io.IOException;
import java.io.PrintWriter;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import models.Employee;
import models.Follow;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class ReportsFollowServlet
 */
@WebServlet("/reports/follow")
public class ReportsFollowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportsFollowServlet() {
        super();
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String _token = (String) request.getParameter("_token");

        int status = Integer.parseInt(request.getParameter("follow_status"));

        if (_token != null && _token.equals(request.getSession().getId())) {
            EntityManager em = DBUtil.createEntityManager();

            Follow follow = new Follow();

            // フォローした人
            Employee follow_employee = new Employee();
            follow_employee = (Employee) request.getSession().getAttribute("login_employee");

            // フォローされた人
            Report report = new Report();
            int report_id = Integer.parseInt(request.getParameter("report_id"));
            report = em.find(Report.class, report_id);
            Employee follower_employee = new Employee();
            follower_employee = report.getEmployee();

            if (status == 0) {
                follow.setFollow_employee(follow_employee);
                follow.setFollower_employee(follower_employee);

                // データ追加
                em.getTransaction().begin();
                em.persist(follow);
                em.getTransaction().commit();

                status = 1;
            } else {
                // データ削除

                try {
                    follow = em.createNamedQuery(Follow.MY_FOLLOWED_CHECK, Follow.class)
                            .setParameter("follow_employee", follow_employee)
                            .setParameter("follower_employee", follower_employee)
                            .getSingleResult();

                    follow = em.find(Follow.class, follow.getId());
                    em.getTransaction().begin();
                    em.remove(follow); // データ削除
                    em.getTransaction().commit();
                } catch (Exception e) {
                    System.out.println(e);
                }

                status = 0;
            }

            em.close();

            JSONObject result = new JSONObject();
            result.put("status", status);

            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            out.print(result);
            out.flush();
        }
    }

}
