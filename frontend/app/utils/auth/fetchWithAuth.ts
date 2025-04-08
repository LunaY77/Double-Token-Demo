import { refreshToken } from './refreshAPI';

interface FetchOptions extends RequestInit {
  retry?: boolean;
}

export async function fetchWithAuth(url: string, options: FetchOptions = {}) {
  // 获取存储的 access token
  const accessToken = localStorage.getItem('accessToken');
  
  // 合并默认配置和用户配置
  const fetchOptions: RequestInit = {
    ...options,
    headers: {
      ...options.headers,
      'Authorization': `Bearer ${accessToken}`,
      'Content-Type': 'application/json',
    },
    credentials: 'include',
  };

  try {
    const response = await fetch(url, fetchOptions);

    // 如果返回 401 且未进行过重试，尝试刷新 token
    if (response.status === 401 && options.retry !== false) {
      try {
        // 尝试刷新 token
        const refreshResponse = await refreshToken();
        if (refreshResponse.accessToken) {
          // 使用新 token 重新发送请求
          return fetchWithAuth(url, {
            ...options,
            retry: false, // 防止无限循环
          });
        }
      } catch (error) {
        // 刷新失败，清除 token
        localStorage.removeItem('accessToken');
        throw new Error('认证已过期，请重新登录');
      }
    }

    // 检查响应状态
    if (!response.ok) {
      throw new Error(`请求失败: ${response.status}`);
    }

    return response.json();
  } catch (error) {
    throw error;
  }
}