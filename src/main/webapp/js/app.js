const expressionEl = document.getElementById('expression');
const resultEl = document.getElementById('result');
const messageEl = document.getElementById('message');
const angleModeEl = document.getElementById('angleMode');
let expression = '';

function render() {
  expressionEl.textContent = expression || '0';
}
function insert(value) {
  expression += value;
  render();
  messageEl.textContent = '';
  messageEl.className = 'message';
}
document.querySelectorAll('[data-insert]').forEach(button => {
  button.addEventListener('click', () => insert(button.dataset.insert));
});
document.getElementById('clear').addEventListener('click', () => {
  expression = ''; render(); resultEl.textContent = 'ফলাফল এখানে দেখাবে';
  messageEl.textContent = ''; messageEl.className = 'message';
});
document.getElementById('backspace').addEventListener('click', () => {
  expression = expression.slice(0, -1); render();
});
async function calculate() {
  if (!expression.trim()) {
    messageEl.textContent = 'প্রথমে একটি হিসাব লিখুন।';
    return;
  }
  const button = document.getElementById('calculate');
  button.disabled = true; button.textContent = 'হিসাব হচ্ছে...';
  try {
    const body = new URLSearchParams({
      expression,
      angleMode: angleModeEl.value
    });
    const response = await fetch('calculate', {
      method: 'POST',
      headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'},
      body
    });
    const data = await response.json();
    if (!response.ok || !data.ok) throw new Error(data.error || 'হিসাব করা যায়নি।');
    resultEl.textContent = '= ' + data.result;
    messageEl.textContent = 'সফলভাবে হিসাব সম্পন্ন হয়েছে।';
    messageEl.className = 'message success';
  } catch (error) {
    resultEl.textContent = '—';
    messageEl.textContent = error.message || 'সার্ভারের সঙ্গে সংযোগ করা যায়নি।';
    messageEl.className = 'message';
  } finally {
    button.disabled = false; button.textContent = '= হিসাব করুন';
  }
}
document.getElementById('calculate').addEventListener('click', calculate);
document.addEventListener('keydown', event => {
  if (event.key === 'Enter') calculate();
  if (event.key === 'Escape') document.getElementById('clear').click();
});
render();
