package controllers.reports;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.Employee;
import models.Favorite;
import models.Follow;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class ReportsIndexServlet
 */
@WebServlet("/reports/index")
public class ReportsIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportsIndexServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        int page;
        String search = request.getParameter("search");
        try {
            page = Integer.parseInt(request.getParameter("page"));

        } catch (Exception e) {
            page = 1;

        }

        HttpSession session = ((HttpServletRequest) request).getSession();
        Employee e = (Employee) session.getAttribute("login_employee");

        List<Report> reports = new ArrayList<Report>();
        long reports_count = 0;

        if (search == null) {
            // 検索条件なしの場合、すべての日報を取得
            reports = em.createNamedQuery(Report.ALL_REPORTS, Report.class)
                    .setFirstResult(15 * (page - 1))
                    .setMaxResults(15)
                    .getResultList();

            // すべての日報の件数を取得
            reports_count = (long) em.createNamedQuery(Report.REPORTS_COUNT, Long.class)
                    .getSingleResult();
        } else {
            switch (search) {
            case "all":
                // 検索条件：全検索
                reports = em.createNamedQuery(Report.ALL_REPORTS, Report.class)
                        .setFirstResult(15 * (page - 1))
                        .setMaxResults(15)
                        .getResultList();

                reports_count = (long) em.createNamedQuery(Report.REPORTS_COUNT, Long.class)
                        .getSingleResult();
                break;
            case "follow":
                // 検索条件：フォロー
                List<Employee> follows = new ArrayList<>();
                follows = em.createNamedQuery(Follow.FIND_BY_FOLLOWS, Employee.class)
                        .setParameter("employee", e)
                        .getResultList();

                if (follows.size() > 0) {
                    reports = em.createNamedQuery(Report.FIND_BY_FOLLOWS, Report.class)
                            .setParameter("el", follows)
                            .setFirstResult(15 * (page - 1))
                            .setMaxResults(15)
                            .getResultList();

                    reports_count = (long) em.createNamedQuery(Report.FIND_BY_FOLLOWS_COUNT, Long.class)
                            .setParameter("el", follows)
                            .getSingleResult();
                }

                break;
            case "favorite":
                // 検索条件：いいね
                reports = em.createNamedQuery(Favorite.FIND_BY_FAVORITES, Report.class)
                        .setParameter("employee", e)
                        .setFirstResult(15 * (page - 1))
                        .setMaxResults(15)
                        .getResultList();

                reports_count = (long) em.createNamedQuery(Favorite.FIND_BY_FAVORITES_COUNT, Long.class)
                        .setParameter("employee", e)
                        .getSingleResult();

                break;
            }
        }
        em.close();

        request.setAttribute("reports", reports);
        request.setAttribute("reports_count", reports_count);
        request.setAttribute("page", page);
        request.setAttribute("search", search);
        if (request.getSession().getAttribute("flush") != null) {
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/index.jsp");
        rd.forward(request, response);
    }

}