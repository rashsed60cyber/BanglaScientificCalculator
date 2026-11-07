package com.mashrafi.calculator;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

@WebServlet("/calculate")
public class CalculatorServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");
        String expression = request.getParameter("expression");
        String angleMode = request.getParameter("angleMode");

        try (PrintWriter out = response.getWriter()) {
            if (expression == null || expression.isBlank() || expression.length() > 300) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"ok\":false,\"error\":\"অনুগ্রহ করে একটি সঠিক expression লিখুন (সর্বোচ্চ ৩০০ অক্ষর)।\"}");
                return;
            }
            try {
                ExpressionParser parser = new ExpressionParser(expression,
                        "RAD".equalsIgnoreCase(angleMode));
                double result = parser.parse();
                if (!Double.isFinite(result)) throw new IllegalArgumentException("ফলাফল নির্ণয় করা যায়নি।");
                String formatted = format(result);
                out.print("{\"ok\":true,\"result\":\"" + jsonEscape(formatted) + "\"}");
            } catch (IllegalArgumentException ex) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"ok\":false,\"error\":\"" + jsonEscape(ex.getMessage()) + "\"}");
            }
        }
    }

    private String format(double value) {
        if (value == Math.rint(value) && Math.abs(value) < 1.0e15)
            return String.format(Locale.US, "%.0f", value);
        return String.format(Locale.US, "%.12g", value);
    }

    private String jsonEscape(String s) {
        return s.replace("\\", "\\\\").replace("\"", "\\\"")
                .replace("\n", "\\n").replace("\r", "\\r");
    }
}
