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

@WebServlet(urlPatterns = "/product")
public class ProductServlet extends HttpServlet {

    private ProductRepository productRepository;

    @Override
    public void init() throws ServletException {
        this.productRepository = new ProductRepository();
        for (int i = 0; i < 10; i++) {
            productRepository.insert(new Product("Product "+i, 20));
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter wr = resp.getWriter();
        wr.println("<table>");
        wr.println("<tr>");
        wr.println("<th>Id</th>");
        wr.println("<th>ProductTitle</th>");
        wr.println("<th>Cost</th>");
        wr.println("</tr>");

        for (Product item : productRepository.findAll()) {
            // TODO добавить создание строк таблицы для каждого из пользователей (продуктов)
            wr.println("<tr>");
            wr.println("<th>" + item.getId() + "</th>");
            wr.println("<th>" + item.getProductTitle() + "</th>");
            wr.println("<th>" + item.getProductCost() + "</th>");
            wr.println("</tr>");
        }

        wr.println("</table>");
    }
}
