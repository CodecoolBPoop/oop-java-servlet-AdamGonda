package webshop;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "ShoppingCart", urlPatterns = {"/cart"}, loadOnStartup = 1)
public class ShoppingCartServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        HttpSession session = request.getSession();
        ItemStore itemStore = (ItemStore) session.getAttribute("itemStore");
        List<Item> items = itemStore.getItems();
        PrintWriter out = response.getWriter();

        out.print("<html><body><table>");
        out.print(
                "<head>\n" +
                "    <style>\n" +
                "        table, td {\n" +
                "            border: 1px solid black;\n" +
                "            border-collapse: collapse;\n" +
                "            text-align: center;\n" +
                "            padding: 20px;\n" +
                "            width:30%;\n" +
                "        }\n" +
                "        table {\n" +
                "            background-color: aliceblue;\n" +
                "        }\n" +
                "    </style>");
        out.print("<body><table>");
        for (Item item : items) {
            out.print("<tr><td>"+item.getName()+"</td>");
            out.print("<td>"+item.getPrice()+"USD</td></tr>");

        }
        out.print("</table>");
        out.print("<h2>Sum of price: "+items.stream().mapToInt(Item::getPrice).sum()+"</h2>");
        out.print("</body></html>");
    }
}
