package webshop;

import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "WebShopServlet", urlPatterns = {"/"}, loadOnStartup = 1)
public class WebShopServlet extends HttpServlet {

    private ItemStore itemStore = new ItemStore();

    @Override
    public void init() throws ServletException {
        List<Item> items = Arrays.asList(
                new Item(0, "Asus Laptop", 1600),
                new Item(1, "Harry Potter Ebook", 500)
        );
        itemStore.addAll(items);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        HttpSession session = request.getSession();
        session.setAttribute("itemStore",itemStore);

        PrintWriter out = response.getWriter();
        out.println("<html>\n" +
                "<head>\n" +
                "    <style>\n" +
                "        table, td {\n" +
                "            border: 1px solid black;\n" +
                "            border-collapse: collapse;\n" +
                "            text-align: center;\n" +
                "            padding: 20px;\n" +
                "            width:30%;\n" +
                "        }\n" +
                "\n" +
                "        table {\n" +
                "            background-color: aliceblue;\n" +
                "        }\n" +
                "\n" +
                "        button {\n" +
                "            font-size: 30px;\n" +
                "            padding: 10px;\n" +
                "        }\n" +
                "\n" +
                "        #chsc {\n" +
                "            margin-top: 30px;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<table>\n" +
                "    <tr>\n" +
                "        <td>Laptop</td>\n" +
                "        <td>1600 USD</td>\n" +
                "        <td>\n" +
                "          <button data-product-id=\"0\" class=\"add\">Add</button>\n" +
                "        </td>\n" +
                "        <td>\n" +
                "            <button data-product-id=\"0\" class=\"remove\">Remove</button>\n" +
                "        </td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td>Harry Potter Ebook</td>\n" +
                "        <td>500 USD</td>\n" +
                "        <td>\n" +
                "            <button data-product-id=\"1\" class=\"add\">Add</button>\n" +
                "        </td>\n" +
                "        <td>\n" +
                "            <button data-product-id=\"1\" class=\"remove\">Remove</button>\n" +
                "        </td>\n" +
                "    </tr>\n" +
                "</table>\n" +
                "<button id=\"chsc\"><a href=\"/cart\" style=\"text-decoration: none;\" >Checkout shopping Cart</a></button>\n" +
                "</body>\n" +
                "  <script>\n" +
                "    const sendJson = type => id => {\n" +
                "        fetch('http://localhost:8080/', {\n" +
                "          method: 'post',\n" +
                "          headers: {\n" +
                "            'Accept': 'application/json, text/plain, */*',\n" +
                "            'Content-Type': 'application/json'\n" +
                "          },\n" +
                "          body: JSON.stringify({type, id})\n" +
                "        })\n" +
                "    }\n" +
                "    const mapActionTo = (btns, action) => {\n" +
                "      btns.map(btn => {\n" +
                "        btn.addEventListener('click', e => {\n" +
                "          const id = e.target.dataset.productId\n" +
                "          action(id)\n" +
                "        })\n" +
                "      })\n" +
                "    }\n" +
                "    \n" +
                "    let addBtns = Array.from(document.querySelectorAll('.add'))\n" +
                "    \n" +
                "    let removeBtns = Array.from(document.querySelectorAll('.remove'))\n" +
                "    \n" +
                "    mapActionTo(addBtns, sendJson('add'))\n" +
                "    mapActionTo(removeBtns, sendJson('remove'))\n" +
                "    \n" +
                "  </script>\n" +
                "</html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        BufferedReader reader = request.getReader();
        Gson gson = new Gson();

        StoreAction action = gson.fromJson(reader, StoreAction.class);
        switch (action.getType()){
            case "remove":
                itemStore.remove(action.getId());
                break;
            case "add":
                itemStore.add(new Item(4, "Hard coded", -1));
                break;
        }
    }
}
