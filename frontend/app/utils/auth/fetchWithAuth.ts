import { refreshToken } from './refreshAPI';
import { queryClient } from '../../routes/home';


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
      'Authorization': `${accessToken}`,
      'Content-Type': 'application/json',
    },
    credentials: 'include',
  };

  try {
    const response = await fetch(url, fetchOptions);
    
    // 检查 HTTP 401 状态码
    if (response.status === 401 && options.retry !== false) {
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
        // 清除前端存储的 AccessToken
        localStorage.removeItem("accessToken");
        // 使 React Query 缓存失效
        queryClient.invalidateQueries({ queryKey: ["authStatus"] });
        // 抛出错误
        const authError = new Error('认证已过期，请重新登录');
        // 延迟2秒后刷新页面
        setTimeout(() => {
          window.location.reload();
        }, 2000);
        throw authError;
      }
    }

    // 检查其他错误状态码
    if (!response.ok) {
      throw new Error(`请求失败: ${response.status}`);
    }

    const data = await response.json();

    return data;
  } catch (error) {
    throw error;
  }
}