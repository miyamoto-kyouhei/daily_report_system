package controllers.reports;

import java.io.IOException;

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
 * Servlet implementation class ReportsShowServlet
 */
@WebServlet("/reports/show")
public class ReportsShowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportsShowServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        Report r = em.find(Report.class, Integer.parseInt(request.getParameter("id")));

        long favorites_count = 0;
        favorites_count = (long) em.createNamedQuery(Favorite.MY_REPORT_All_FAVORITES_COUNT, Long.class)
                .setParameter("report", r)
                .getSingleResult();

        // セッションスコープに保存された従業員（ログインユーザ）情報を取得
        HttpSession session = ((HttpServletRequest) request).getSession();
        Employee e = (Employee) session.getAttribute("login_employee");

        // いいねされているかチェック
        long favorited_status = 0;
        favorited_status = (long) em.createNamedQuery(Favorite.MY_FAVORITED_CHECK_COUNT, Long.class)
                .setParameter("employee", e)
                .setParameter("report", r)
                .getSingleResult();

        // フォローされているかチェック
        long followed_status = 0;
        followed_status = (long) em.createNamedQuery(Follow.MY_FOLLOWED_CHECK_COUNT, Long.class)
                .setParameter("follow_employee", e)
                .setParameter("follower_employee", r.getEmployee())
                .getSingleResult();

        em.close();

        request.setAttribute("report", r);
        request.setAttribute("favorites_count", favorites_count);
        request.setAttribute("favorited_status", favorited_status);
        request.setAttribute("followed_status", followed_status);
        request.setAttribute("_token", request.getSession().getId());

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/show.jsp");
        rd.forward(request, response);
    }

}