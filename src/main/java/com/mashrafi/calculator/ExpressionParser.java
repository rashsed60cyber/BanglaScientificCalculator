package com.mashrafi.calculator;

public final class ExpressionParser {
    private final String input;
    private final boolean radians;
    private int pos = -1;
    private int ch;

    public ExpressionParser(String input, boolean radians) {
        this.input = input.replace("×", "*").replace("÷", "/")
                .replace("−", "-").replace("π", "pi").replace("√", "sqrt");
        this.radians = radians;
        nextChar();
    }

    public double parse() {
        double value = parseExpression();
        skipSpaces();
        if (pos < input.length()) throw error("অপ্রত্যাশিত চিহ্ন: " + (char) ch);
        if (!Double.isFinite(value)) throw error("গণনার ফলাফল অসংজ্ঞায়িত বা অসীম।");
        return value;
    }

    private void nextChar() { ch = (++pos < input.length()) ? input.charAt(pos) : -1; }
    private void skipSpaces() { while (ch == ' ' || ch == '\t' || ch == '\n') nextChar(); }
    private boolean eat(int c) {
        skipSpaces();
        if (ch == c) { nextChar(); return true; }
        return false;
    }
    private IllegalArgumentException error(String message) {
        return new IllegalArgumentException(message + " (অবস্থান: " + Math.max(1, pos + 1) + ")");
    }

    // expression := term ((+|-) term)*
    private double parseExpression() {
        double x = parseTerm();
        while (true) {
            if (eat('+')) x += parseTerm();
            else if (eat('-')) x -= parseTerm();
            else return x;
        }
    }

    // term := unary ((*|/|%) unary)*
    private double parseTerm() {
        double x = parseUnary();
        while (true) {
            if (eat('*')) x *= parseUnary();
            else if (eat('/')) {
                double d = parseUnary();
                if (d == 0) throw error("শূন্য দিয়ে ভাগ করা যায় না।");
                x /= d;
            } else if (eat('%')) {
                double d = parseUnary();
                if (d == 0) throw error("শূন্য দিয়ে modulo করা যায় না।");
                x %= d;
            } else return x;
        }
    }

    private double parseUnary() {
        if (eat('+')) return parseUnary();
        if (eat('-')) return -parseUnary();
        return parsePower();
    }

    // power is right associative
    private double parsePower() {
        double x = parsePostfix();
        if (eat('^')) x = Math.pow(x, parseUnary());
        return x;
    }

    private double parsePostfix() {
        double x = parsePrimary();
        while (eat('!')) x = factorial(x);
        return x;
    }

    private double parsePrimary() {
        skipSpaces();
        if (eat('(')) {
            double x = parseExpression();
            if (!eat(')')) throw error("বন্ধনী ')' অনুপস্থিত।");
            return x;
        }
        if ((ch >= '0' && ch <= '9') || ch == '.') {
            int start = pos;
            while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
            if (ch == 'e' || ch == 'E') {
                nextChar();
                if (ch == '+' || ch == '-') nextChar();
                while (ch >= '0' && ch <= '9') nextChar();
            }
            try { return Double.parseDouble(input.substring(start, pos)); }
            catch (NumberFormatException ex) { throw error("সংখ্যাটি সঠিক নয়।"); }
        }
        if (Character.isLetter(ch)) {
            int start = pos;
            while (Character.isLetter(ch)) nextChar();
            String name = input.substring(start, pos).toLowerCase();
            if (name.equals("pi")) return Math.PI;
            if (name.equals("e")) return Math.E;
            double arg;
            if (!eat('(')) throw error("ফাংশনের পরে '(' ব্যবহার করুন।");
            arg = parseExpression();
            if (!eat(')')) throw error("ফাংশনের বন্ধনী ')' অনুপস্থিত।");
            return applyFunction(name, arg);
        }
        throw error("সংখ্যা, ধ্রুবক বা ফাংশন প্রত্যাশিত।");
    }

    private double applyFunction(String name, double x) {
        double angle = radians ? x : Math.toRadians(x);
        return switch (name) {
            case "sin" -> Math.sin(angle);
            case "cos" -> Math.cos(angle);
            case "tan" -> {
                if (Math.abs(Math.cos(angle)) < 1e-12) throw error("এই কোণে tan অসংজ্ঞায়িত।");
                yield Math.tan(angle);
            }
            case "asin" -> fromRadians(Math.asin(x));
            case "acos" -> fromRadians(Math.acos(x));
            case "atan" -> fromRadians(Math.atan(x));
            case "sqrt" -> {
                if (x < 0) throw error("ঋণাত্মক সংখ্যার বাস্তব বর্গমূল নেই।");
                yield Math.sqrt(x);
            }
            case "log" -> {
                if (x <= 0) throw error("log-এর জন্য ধনাত্মক সংখ্যা প্রয়োজন।");
                yield Math.log10(x);
            }
            case "ln" -> {
                if (x <= 0) throw error("ln-এর জন্য ধনাত্মক সংখ্যা প্রয়োজন।");
                yield Math.log(x);
            }
            case "abs" -> Math.abs(x);
            case "exp" -> Math.exp(x);
            case "floor" -> Math.floor(x);
            case "ceil" -> Math.ceil(x);
            default -> throw error("অজানা ফাংশন: " + name);
        };
    }

    private double fromRadians(double x) { return radians ? x : Math.toDegrees(x); }

    private double factorial(double x) {
        if (x < 0 || x != Math.rint(x) || x > 170)
            throw error("Factorial-এর জন্য ০ থেকে ১৭০ পর্যন্ত পূর্ণসংখ্যা দিন।");
        double result = 1;
        for (int i = 2; i <= (int)x; i++) result *= i;
        return result;
    }
}
