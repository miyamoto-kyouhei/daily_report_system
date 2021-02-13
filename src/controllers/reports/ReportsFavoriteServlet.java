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
import models.Favorite;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class ReportsFavoriteServlet
 */
@WebServlet("/reports/favorite")
public class ReportsFavoriteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportsFavoriteServlet() {
        super();
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String _token = (String) request.getParameter("_token");

        int status = Integer.parseInt(request.getParameter("favorite_status"));

        long count = 0;

        if (_token != null && _token.equals(request.getSession().getId())) {
            EntityManager em = DBUtil.createEntityManager();

            Favorite favorite = new Favorite();

            // いいねした人
            Employee employee = new Employee();
            employee = (Employee) request.getSession().getAttribute("login_employee");

            // いいねした日報
            Report report = new Report();
            int report_id = Integer.parseInt(request.getParameter("report_id"));
            report.setId(report_id);

            if (status == 0) {
                // データ追加
                favorite.setEmployee(employee);
                favorite.setReport(report);

                em.getTransaction().begin();
                em.persist(favorite);
                em.getTransaction().commit();

                status = 1;
            } else {
                // データ削除
                try {
                    favorite = em.createNamedQuery(Favorite.MY_FAVORITED_CHECK, Favorite.class)
                            .setParameter("employee", employee)
                            .setParameter("report", report)
                            .getSingleResult();

                    favorite = em.find(Favorite.class, favorite.getId());
                    em.getTransaction().begin();
                    em.remove(favorite);
                    em.getTransaction().commit();
                } catch (Exception e) {
                    System.out.println(e);
                }

                status = 0;
            }

            count = (long) em.createNamedQuery(Favorite.MY_REPORT_All_FAVORITES_COUNT, Long.class)
                    .setParameter("report", report)
                    .getSingleResult();

            em.close();

            JSONObject result = new JSONObject();
            result.put("status", status);
            result.put("count", count);

            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            out.print(result);
            out.flush();

        }

    }

}
