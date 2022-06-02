package ru.geekbrains;

import ru.geekbrains.persist.Product;
import ru.geekbrains.persist.ProductRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(urlPatterns = "/product/*")
public class ProductServlet extends HttpServlet {

    private ProductRepository productRepository;
    private static final Pattern PARAM_PATTERN = Pattern.compile("\\/(\\d+)");
    @Override
    public void init() throws ServletException {
        this.productRepository = new ProductRepository();
        for (int i = 0; i < 10; i++) {
            productRepository.insert(new Product("Продукт "+i, 20));
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (req.getPathInfo() == null || req.getPathInfo().equals("/")) {
            req.setAttribute("items", productRepository.findAll());
            getServletContext().getRequestDispatcher("/product.jsp").forward(req, resp);
        }
        else {
            Matcher matcher = PARAM_PATTERN.matcher(req.getPathInfo());
            if (matcher.matches()) {
                long id = Long.parseLong(matcher.group(1));
                Product product = this.productRepository.findById(id);
                if (product == null) {
                    resp.getWriter().println("Product not found");
                    resp.setStatus(404);
                    return;
                }
                req.setAttribute("item", product);
                getServletContext().getRequestDispatcher("/product_form.jsp").forward(req, resp);
            } else {
                resp.getWriter().println("Bad parameter value");
                resp.setStatus(400);
            }
        }
//        PrintWriter wr = resp.getWriter();
//        wr.println("<table>");
//        wr.println("<tr>");
//        wr.println("<th>Id</th>");
//        wr.println("<th>ProductTitle</th>");
//        wr.println("<th>Cost</th>");
//        wr.println("</tr>");
//        int i = 0;
//        String result = req.getPathInfo().substring(1);
//        if (result.isEmpty()) {
//            for (Product item : productRepository.findAll()) {
//                  wr.println("<tr>");
//                wr.println("<th>" + item.getId() + "</th>");
//                wr.println("<th>" + item.getProductTitle() + "</th>");
//                wr.println("<th>" + item.getProductCost() + "</th>");
//                wr.println("</tr>");
//            }
//        } else {
//            try {
//                i = Integer.parseInt(result);
//                Product item = productRepository.findById(i);
//                if (item == null) {
//                    wr.println("<tr>");
//                    wr.println("<th>EMPTY</th>");
//                    wr.println("</tr>");
//                } else {
//                    wr.println("<tr>");
//                    wr.println("<th>" + item.getId() + "</th>");
//                    wr.println("<th>" + item.getProductTitle() + "</th>");
//                    wr.println("<th>" + item.getProductCost() + "</th>");
//                    wr.println("</tr>");
//                }
//
//            }catch (NumberFormatException e) {
//                System.err.println("Неправильный формат строки!");
//            }
//        }
//
//        wr.println("</table>");
    }
}
