// import { useMutation } from "@tanstack/react-query";

export async function refreshToken() {
    try {
      const response = await fetch('http://localhost:8082/capi/user/public/refreshToken', {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
        },
        credentials: 'include',
      });
    
      const data = await response.json();
      
      if (!response.ok) {
        console.error('Refresh token error:', data);
        throw new Error(data.message || `刷新Token失败: ${response.status}`);
      }
      
      return data;
    } catch (error) {
      console.error('Refresh token request failed:', error);
      throw error;
    }
}
// export const useRefreshMutation = () => {
//   return useMutation({
//     mutationFn: refreshToken,
//     onSuccess: (data) => {
//       if (data.accessToken) {
//         localStorage.setItem("accessToken", data.accessToken);
//       }
//     },
//     onError: () => {
//       localStorage.removeItem("accessToken");
//     }
//   });
// };