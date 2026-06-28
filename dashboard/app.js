const API = '';
let challenges = [];
let currentChallenge = null;
let solvedSet = new Set(JSON.parse(localStorage.getItem('springArena_solved') || '[]'));

async function init() {
  const res = await fetch(`${API}/api/challenges`);
  challenges = await res.json();
  renderSidebar();
  updateProgress();
}

function renderSidebar() {
  const nav = document.getElementById('challengeList');
  nav.innerHTML = challenges.map(c => `
    <div class="nav-item ${currentChallenge?.id === c.id ? 'active' : ''}" onclick="loadChallenge('${c.id}')">
      <div class="status-dot ${solvedSet.has(c.id) ? 'solved' : ''}"></div>
      <span class="nav-title">${c.title}</span>
      <span class="nav-diff ${c.difficulty.toLowerCase()}">${c.difficulty}</span>
    </div>
  `).join('');
  document.getElementById('totalCount').textContent = challenges.length;
}

function updateProgress() {
  const total = challenges.length;
  const solved = solvedSet.size;
  const pct = total ? Math.round((solved / total) * 100) : 0;
  document.getElementById('progressText').textContent = pct + '%';
  document.getElementById('solvedCount').textContent = solved;
  document.getElementById('totalCount').textContent = total;
  const ring = document.getElementById('progressRing');
  const offset = 264 - (264 * pct / 100);
  ring.style.strokeDashoffset = offset;
}

async function loadChallenge(id) {
  const res = await fetch(`${API}/api/challenges/${id}`);
  const ch = await res.json();
  currentChallenge = ch;

  document.getElementById('landingPage').style.display = 'none';
  document.getElementById('challengePage').style.display = 'block';

  const diffEl = document.getElementById('chDifficulty');
  diffEl.textContent = ch.difficulty;
  diffEl.className = 'difficulty-badge ' + ch.difficulty.toLowerCase();
  document.getElementById('chTitle').textContent = ch.title;
  document.getElementById('chTrack').textContent = ch.track;
  document.getElementById('chTestCount').textContent = ch.testCount + ' tests';

  // Description
  document.getElementById('chDescription').innerHTML = renderMarkdown(ch.description);

  // Test files
  const testsDiv = document.getElementById('chTests');
  testsDiv.innerHTML = (ch.testFiles || []).map(f => `
    <div class="code-file">
      <div class="code-file-name">${f.path}</div>
      <pre><code>${escapeHtml(f.content)}</code></pre>
    </div>
  `).join('') || '<p style="color:var(--text-muted)">No test files found</p>';

  // Skeleton files
  const skelDiv = document.getElementById('chSkeleton');
  skelDiv.innerHTML = (ch.skeletonFiles || []).map(f => `
    <div class="code-file">
      <div class="code-file-name">${f.path}</div>
      <pre><code>${escapeHtml(f.content)}</code></pre>
    </div>
  `).join('') || '<p style="color:var(--text-muted)">No skeleton files</p>';

  // Hints
  const hintsDiv = document.getElementById('chHints');
  hintsDiv.innerHTML = (ch.hints || []).map((h, i) => `
    <div class="hint" onclick="this.classList.toggle('open')">
      <div class="hint-header">Hint ${i + 1}</div>
      <div class="hint-body">${h}</div>
    </div>
  `).join('') || '<p style="color:var(--text-muted)">No hints</p>';

  // Reset results
  document.getElementById('resultsArea').innerHTML = `
    <div class="results-placeholder"><span class="placeholder-icon">🎯</span><p>Write your solution, then hit <strong>Run Tests</strong></p></div>
  `;

  // Reset tabs
  document.querySelectorAll('.tab').forEach(t => t.classList.remove('active'));
  document.querySelectorAll('.tab-content').forEach(t => t.classList.remove('active'));
  document.querySelector('.tab[data-tab="description"]').classList.add('active');
  document.getElementById('tab-description').classList.add('active');

  renderSidebar();
}

async function gradeChallenge() {
  if (!currentChallenge) return;
  const btn = document.getElementById('runTestsBtn');
  btn.disabled = true;
  btn.classList.add('running');
  btn.innerHTML = '<span class="spinner"></span> Running tests...';
  document.getElementById('resultsArea').innerHTML = '<div class="results-placeholder"><span class="spinner" style="width:32px;height:32px;border-width:3px"></span><p style="margin-top:16px">Compiling & running tests...</p></div>';

  try {
    const res = await fetch(`${API}/api/grade/${currentChallenge.id}`, { method: 'POST' });
    const data = await res.json();
    renderResults(data);
    if (data.success) {
      solvedSet.add(currentChallenge.id);
      localStorage.setItem('springArena_solved', JSON.stringify([...solvedSet]));
      updateProgress();
      renderSidebar();
    }
  } catch (err) {
    document.getElementById('resultsArea').innerHTML = `<div class="results-placeholder"><p style="color:var(--red)">Error: ${err.message}</p></div>`;
  } finally {
    btn.disabled = false;
    btn.classList.remove('running');
    btn.innerHTML = '<span class="btn-icon">▶</span> Run Tests';
  }
}

function renderResults(data) {
  const area = document.getElementById('resultsArea');
  if (data.error && data.tests.length === 0) {
    area.innerHTML = `
      <div class="result-summary fail">
        <span class="result-icon">❌</span>
        <div class="result-info"><div class="result-title">Build Error</div><div class="result-count">${data.error}</div></div>
      </div>`;
    return;
  }
  const allPass = data.passed === data.total;
  let html = `
    <div class="result-summary ${allPass ? 'pass' : 'fail'}">
      <span class="result-icon">${allPass ? '🎉' : '⚠️'}</span>
      <div class="result-info">
        <div class="result-title">${allPass ? 'All Tests Passed!' : 'Some Tests Failed'}</div>
        <div class="result-count">${data.passed} / ${data.total} passed</div>
      </div>
    </div>`;

  for (const t of data.tests) {
    const passed = t.status === 'PASSED';
    html += `
      <div class="test-item ${passed ? 'passed' : 'failed'}">
        <span class="test-icon">${passed ? '✅' : '❌'}</span>
        <div>
          <div class="test-name">${t.name}</div>
          ${!passed && t.message ? `<div class="test-error">${escapeHtml(t.message)}</div>` : ''}
        </div>
      </div>`;
  }
  area.innerHTML = html;
}

// Tab switching
document.addEventListener('click', e => {
  if (e.target.classList.contains('tab')) {
    const tab = e.target.dataset.tab;
    document.querySelectorAll('.tab').forEach(t => t.classList.remove('active'));
    document.querySelectorAll('.tab-content').forEach(t => t.classList.remove('active'));
    e.target.classList.add('active');
    document.getElementById('tab-' + tab).classList.add('active');
  }
});

function escapeHtml(s) {
  return s.replace(/&/g,'&amp;').replace(/</g,'&lt;').replace(/>/g,'&gt;').replace(/"/g,'&quot;');
}

function renderMarkdown(text) {
  return text
    .replace(/^### (.+)$/gm, '<h3>$1</h3>')
    .replace(/^## (.+)$/gm, '<h3>$1</h3>')
    .replace(/`([^`]+)`/g, '<code>$1</code>')
    .replace(/\*\*([^*]+)\*\*/g, '<strong>$1</strong>')
    .replace(/^- (.+)$/gm, '<li>$1</li>')
    .replace(/(<li>.*<\/li>)/gs, '<ul>$1</ul>')
    .replace(/<\/ul>\s*<ul>/g, '')
    .replace(/\n\n/g, '</p><p>')
    .replace(/^(?!<[hup])/gm, '<p>')
    .replace(/\n/g, '<br>');
}

init();
