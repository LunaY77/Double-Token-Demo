import type { LoginCredentials, RegisterCredentials } from "../../types/authtype";

export async function loginUser(credentials: LoginCredentials) {
  try {
    const response = await fetch('http://localhost:8082/capi/user/public/login', {
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
    if (data.code !== 200) {
      throw new Error(data.message || "登录失败");
    }

    return data;
  } catch (error) {
    if (error instanceof TypeError && error.message === 'Failed to fetch') {
      throw new Error('无法连接到服务器，请检查服务器是否正在运行');
    }
    throw error;
  }
}

export async function registerUser(credentials: RegisterCredentials) {
  const response = await fetch('http://localhost:8082/capi/user/public/register', {
    method: 'POST',
    headers: {
      'Authorization': "Bearer 10001",
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({
      username: credentials.username,
      password: credentials.password,
    }),
  });

  const data = await response.json();
  if (data.code !== 200) {
    throw new Error(data.errMsg || "注册失败");
  }

  return data;
}


export async function checkAuthStatus() {
  const token = localStorage.getItem("accessToken");
  if (!token) {
    return { isLoggedIn: false };
  }
  return { isLoggedIn: true, token };
}

