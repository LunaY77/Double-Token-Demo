// import { useMutation } from "@tanstack/react-query";

export async function refreshToken() {
    const response = await fetch('/capi/user/public/refreshToken', {
      method: 'GET',
      credentials: 'include',
    });
  
    const data = await response.json();
    if (!response.ok) {
      throw new Error(data.message || "Token 刷新失败");
    }
    return data;
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