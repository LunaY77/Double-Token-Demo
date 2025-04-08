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
    
    // 先检查 HTTP 状态码
    if (!response.ok) {
      throw new Error(`请求失败: ${response.status}`);
    }

    const data = await response.json();

    // 检查业务状态码
    if (data.code === 401 && options.retry !== false) {
      try {
        // 尝试刷新 token
        const refreshResponse = await refreshToken();
        if (refreshResponse.data) {
            localStorage.setItem("accessToken", refreshResponse.data);
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

    return data;
  } catch (error) {
    throw error;
  }
}