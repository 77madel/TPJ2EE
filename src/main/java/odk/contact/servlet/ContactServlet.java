package odk.contact.servlet;

import odk.contact.dao.ContactDao;
import odk.contact.model.Contact;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/")
public class ContactServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ContactDao contactDao;

    public void init() {
        contactDao = new ContactDao();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();

        try {
            switch (action) {
                case "/new":
                    showNewForm(request, response);
                    break;
                case "/insert":
                    insertContact(request, response);
                    break;
                case "/delete":
                    deleteContact(request, response);
                    break;
                case "/edit":
                    showEditForm(request, response);
                    break;
                case "/update":
                    updateContact(request, response);
                    break;
                case "/list":
                    listContact(request, response);
                    break;
                default:
                    showHomePage(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void showHomePage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        dispatcher.forward(request, response);
    }

    private void listContact(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<Contact> listContact = contactDao.selectAllContacts();
        request.setAttribute("listContact", listContact);
        RequestDispatcher dispatcher = request.getRequestDispatcher("contact-list.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("contact-form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Contact existingContact = contactDao.selectContact(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("contact-form.jsp");
        request.setAttribute("contact", existingContact);
        dispatcher.forward(request, response);
    }

    private void insertContact(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String prenom = request.getParameter("prenom");
        String nom = request.getParameter("nom");
        String telephone = request.getParameter("telephone");
        String competence = request.getParameter("competence");
        Contact newContact = new Contact(prenom, nom, telephone, competence);
        contactDao.insertContact(newContact);
        response.sendRedirect("list");
    }

    private void updateContact(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String prenom = request.getParameter("prenom");
        String nom = request.getParameter("nom");
        String telephone = request.getParameter("telephone");
        String competence = request.getParameter("competence");

        Contact contact = new Contact(id, prenom, nom, telephone, competence);
        contactDao.updateContact(contact);
        response.sendRedirect("list");
    }

    private void deleteContact(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        contactDao.deleteContact(id);
        response.sendRedirect("list");
    }
}
