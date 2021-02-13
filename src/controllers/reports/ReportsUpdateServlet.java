package controllers.reports;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Client;
import models.Report;
import models.SalesCalls;
import models.validators.ReportValidator;
import utils.DBUtil;

/**
 * Servlet implementation class ReportsUpdateServlet
 */
@WebServlet("/reports/update")
public class ReportsUpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportsUpdateServlet() {
        super();
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String _token = (String) request.getParameter("_token");
        if (_token != null && _token.equals(request.getSession().getId())) {
            EntityManager em = DBUtil.createEntityManager();

            Report r = em.find(Report.class, (Integer) (request.getSession().getAttribute("report_id")));

            r.setReport_date(Date.valueOf(request.getParameter("report_date")));

            r.setTitle(request.getParameter("title"));

            r.setContent(request.getParameter("content"));

            List<SalesCalls> salesCallsList = new ArrayList<>();
            SalesCalls s;
            Client c;
            String p = "";
            String visit_time = "";
            for (int i = 0; i <= 2; i++) {
                p = request.getParameter("clients" + i);
                if (!"0".equals(p)) {
                    s = new SalesCalls();
                    c = new Client();
                    c.setId(Integer.parseInt(p));
                    s.setClient(c);
                    visit_time = request.getParameter("visit_time" + i);
                    if (visit_time != null && !visit_time.equals("")) {
                        s.setVisit_time(LocalTime.parse(visit_time, DateTimeFormatter.ofPattern("HH:mm")));
                    }
                    s.setSales_report(request.getParameter("sales_report" + i));
                    salesCallsList.add(s);
                }
            }
            r.setSalesCallsList(salesCallsList);

            String attendance_time = request.getParameter("attendance_time");
            if (attendance_time != null && !attendance_time.equals("")) {
                r.setAttendance_time(LocalTime.parse(attendance_time, DateTimeFormatter.ofPattern("HH:mm")));
            }

            String leave_time = request.getParameter("leave_time");
            if (leave_time != null && !leave_time.equals("")) {
                r.setLeave_time(LocalTime.parse(leave_time, DateTimeFormatter.ofPattern("HH:mm")));

            }
            r.setUpdated_at(new Timestamp(System.currentTimeMillis()));

            List<Client> clients = em.createNamedQuery(Client.NOT_DELETED_ALL_CLIENTS, Client.class)
                    .getResultList();

            List<String> errors = ReportValidator.validate(r);
            if (errors.size() > 0) {
                em.close();

                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("report", r);
                request.setAttribute("errors", errors);
                request.setAttribute("clients", clients);

                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/edit.jsp");
                rd.forward(request, response);
            } else {
                em.getTransaction().begin();
                em.getTransaction().commit();
                em.close();
                request.getSession().setAttribute("flush", "更新が完了しました。");

                request.getSession().removeAttribute("report_id");

                response.sendRedirect(request.getContextPath() + "/reports/index");
            }
        }
    }

}