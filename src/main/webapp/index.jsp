<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="bn">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>বাংলা Scientific Calculator</title>
  <link rel="stylesheet" href="css/style.css">
</head>
<body>
  <main class="app">
    <header>
      <div class="brand-mark">∑</div>
      <div><h1>বিজ্ঞান ক্যালকুলেটর</h1><p>Bangla Scientific Calculator</p></div>
    </header>

    <section class="calculator" aria-label="Scientific calculator">
      <div class="topline">
        <span>সায়েন্টিফিক মোড</span>
        <label class="mode">Angle
          <select id="angleMode" aria-label="Angle mode">
            <option value="DEG">DEG</option><option value="RAD">RAD</option>
          </select>
        </label>
      </div>
      <div class="display">
        <div id="expression" class="expression" aria-live="polite">0</div>
        <div id="result" class="result">ফলাফল এখানে দেখাবে</div>
      </div>
      <div class="keys">
        <button class="function" data-insert="sin(">sin</button>
        <button class="function" data-insert="cos(">cos</button>
        <button class="function" data-insert="tan(">tan</button>
        <button class="function" data-insert="log(">log</button>
        <button class="function" data-insert="ln(">ln</button>

        <button class="function" data-insert="asin(">sin⁻¹</button>
        <button class="function" data-insert="acos(">cos⁻¹</button>
        <button class="function" data-insert="atan(">tan⁻¹</button>
        <button class="function" data-insert="sqrt(">√</button>
        <button class="function" data-insert="^2">x²</button>

        <button class="function" data-insert="^">xʸ</button>
        <button class="function" data-insert="!">x!</button>
        <button class="function" data-insert="(">(</button>
        <button class="function" data-insert=")">)</button>
        <button class="function" data-insert="pi">π</button>

        <button class="function" data-insert="e">e</button>
        <button class="function" data-insert="abs(">abs</button>
        <button class="function" data-insert="exp(">exp</button>
        <button class="function" data-insert="%">mod</button>
        <button class="danger" id="clear">AC</button>

        <button data-insert="7">7</button><button data-insert="8">8</button>
        <button data-insert="9">9</button><button class="operator" data-insert="/">÷</button>
        <button class="operator" data-insert="*">×</button>

        <button data-insert="4">4</button><button data-insert="5">5</button>
        <button data-insert="6">6</button><button class="operator" data-insert="-">−</button>
        <button class="operator" data-insert="+">+</button>

        <button data-insert="1">1</button><button data-insert="2">2</button>
        <button data-insert="3">3</button><button data-insert="0">0</button>
        <button data-insert=".">.</button>

        <button class="secondary" id="backspace">⌫</button>
        <button class="secondary" data-insert="00">00</button>
        <button class="equals" id="calculate">= হিসাব করুন</button>
      </div>
      <p id="message" class="message" role="status"></p>
    </section>
    <section class="help">
      <h2>ব্যবহারের নিয়ম</h2>
      <p>উদাহরণ: <code>sin(30)+sqrt(25)+log(100)</code></p>
      <p>ফাংশনের নামের পরে বন্ধনী দিন। যেমন: <code>cos(60)</code>। DEG মোডে কোণ ডিগ্রিতে, RAD মোডে রেডিয়ানে গণনা হবে।</p>
      <p class="note">নিরাপদ গণনা: expression JavaScript eval ছাড়া Java parser দিয়ে হিসাব করা হয়।</p>
    </section>
    <footer>Java Servlet • Jakarta EE • Maven • Tomcat 11</footer>
  </main>
  <script src="js/app.js"></script>
</body>
</html>
