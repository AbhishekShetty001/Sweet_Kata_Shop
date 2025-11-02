// Base API URL for your Spring Boot app
const API_BASE = 'http://localhost:8888';

// JWT helpers
function setToken(token) { localStorage.setItem('jwt', token); }
function getToken() { return localStorage.getItem('jwt'); }
function clearToken() { localStorage.removeItem('jwt'); }

// Generic fetch wrapper with JSON + Bearer token + basic 401 handling
async function apiFetch(path, options = {}) {
  const headers = options.headers || {};
  headers['Content-Type'] = 'application/json';
  const token = getToken();
  if (token) headers['Authorization'] = 'Bearer ' + token;

  const res = await fetch(API_BASE + path, { ...options, headers });

  if (res.status === 401) {
    clearToken();
    alert('Unauthorized or session expired. Please login again.');
    if (!location.pathname.endsWith('index.html')) location.href = 'index.html';
    return Promise.reject(new Error('Unauthorized'));
  }

  if (!res.ok) {
    let msg = res.statusText;
    try {
      const body = await res.json();
      msg = body.message || JSON.stringify(body);
    } catch (_e) {}
    throw new Error(msg);
  }

  if (res.status === 204) return null;
  return res.json();
}

window.api = { apiFetch, setToken, getToken, clearToken };
