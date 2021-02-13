package controllers.clients;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Client;
import utils.DBUtil;

/**
 * Servlet implementation class ClientsShowServlet
 */
@WebServlet("/clients/show")
public class ClientsShowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ClientsShowServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        Client c = em.find(Client.class, Integer.parseInt(request.getParameter("id")));

        em.close();

        request.setAttribute("client", c);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/clients/show.jsp");
        rd.forward(request, response);
    }

}
