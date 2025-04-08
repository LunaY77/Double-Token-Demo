import type { LoginCredentials, RegisterCredentials } from "../../types/authtype";

export async function loginUser(credentials: LoginCredentials) {
  const response = await fetch('/api/login', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({
      userId: credentials.username,
      password: credentials.password,
    }),
    // 确保包含凭证以接收 cookies
    credentials: 'include',
  });

  const data = await response.json();
  if (!data.success) {
    throw new Error(data.errMsg || "登录失败");
  }

  return data;
}

export async function registerUser(credentials: RegisterCredentials) {
  const response = await fetch('/api/register', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({
      username: credentials.username,
      password: credentials.password,
    }),
  });

  const data = await response.json();
  if (!data.success) {
    throw new Error(data.errMsg || "注册失败");
  }

  return data;
}


export async function checkAuthStatus() {
  const token = localStorage.getItem("token");
  if (!token) {
    return { isLoggedIn: false };
  }
  return { isLoggedIn: true, token };
}

