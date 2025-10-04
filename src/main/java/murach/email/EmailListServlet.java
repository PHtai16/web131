package murach.email;

import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import murach.business.User;
import murach.data.UserDB;

@WebServlet("/emailList")   // 🔹 mapping servlet với đường dẫn /emailList
public class EmailListServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String url = "/index.html";

        String action = request.getParameter("action");
        if (action == null) {
            action = "join";  // default action
        }

        if (action.equals("join")) {
            url = "/index.jsp";    
        } else if (action.equals("add")) {
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String email = request.getParameter("email");

            User user = new User();
            user.setEmail(email);
            user.setFirstName(firstName);
            user.setLastName(lastName);

            String message;
            if (UserDB.emailExists(user.getEmail())) {
                message = "This email address already exists.";
                url = "/index.jsp";
            } else {
                message = "";
                url = "/thanks.jsp";
                UserDB.insert(user);
            }
            request.setAttribute("user", user);
            request.setAttribute("message", message);
        }

        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }
}
