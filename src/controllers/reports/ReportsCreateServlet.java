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
import models.Employee;
import models.Report;
import models.SalesCalls;
import models.validators.ReportValidator;
import utils.DBUtil;

/**
 * Servlet implementation class ReportsCreateServlet
 */
@WebServlet("/reports/create")
public class ReportsCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportsCreateServlet() {
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

            List<String> errors = new ArrayList<String>();
            Report r = new Report();

            Employee e = new Employee();
            e = (Employee) request.getSession().getAttribute("login_employee");
            r.setEmployee(e);

            List<SalesCalls> salesCallsList = new ArrayList<>();
            SalesCalls s;
            Client c;
            String p = "";
            String visit_time = "";
            for (int i = 0; i <= 2; i++) {
                p = request.getParameter("clients" + i);

                if (!"0".equals(p)) {
                    // 営業情報が記載されている場合
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

            Date report_date = new Date(System.currentTimeMillis());
            String rd_str = request.getParameter("report_date");
            if (rd_str != null && !rd_str.equals("")) {
                report_date = Date.valueOf(request.getParameter("report_date"));
            }
            r.setReport_date(report_date);

            r.setTitle(request.getParameter("title"));

            r.setContent(request.getParameter("content"));

            String attendance_time = request.getParameter("attendance_time");
            if (attendance_time != null && !attendance_time.equals("")) {
                r.setAttendance_time(LocalTime.parse(attendance_time, DateTimeFormatter.ofPattern("HH:mm")));
            }

            String leave_time = request.getParameter("leave_time");
            if (leave_time != null && !leave_time.equals("")) {
                r.setLeave_time(LocalTime.parse(leave_time, DateTimeFormatter.ofPattern("HH:mm")));

            }

            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            r.setCreated_at(currentTime);
            r.setUpdated_at(currentTime);

            List<Client> clients = em.createNamedQuery(Client.NOT_DELETED_ALL_CLIENTS, Client.class)
                    .getResultList();

            errors = ReportValidator.validate(r);
            if (errors.size() > 0) {
                em.close();

                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("report", r);
                request.setAttribute("salesList", salesCallsList);
                request.setAttribute("errors", errors);
                request.setAttribute("clients", clients);
                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/new.jsp");
                rd.forward(request, response);
            } else {
                em.getTransaction().begin();
                em.persist(r);
                em.getTransaction().commit();
                em.close();
                request.getSession().setAttribute("flush", "登録が完了しました。");

                response.sendRedirect(request.getContextPath() + "/reports/index");
            }
        }
    }

}