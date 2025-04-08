import { useQuery } from '@tanstack/react-query'
import { fetchWithAuth } from './utils/auth/fetchWithAuth'

interface UserInfoResponse {
    code?: number;
    data?: {
        id?: number;
        name?: string;
    };
    message?: string;
}

export default function Test() {
    const { data: response, isLoading, refetch } = useQuery<UserInfoResponse>({
        queryKey: ['user-info'],
        queryFn: () => fetchWithAuth('/capi/user/info/me', {
            method: 'GET'
        }),
        enabled: false
    })

    return (
        <div className="flex flex-col items-center justify-center min-h-screen">
            <button 
                className="px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600"
                onClick={() => refetch()}
            >
                {isLoading ? '加载中...' : '获取用户信息'}
            </button>
            {response && (
                <div className="mt-4 space-y-2">
                    <div>状态码: {response.code}</div>
                    {response.data && (
                        <>
                            <div>用户ID: {response.data.id}</div>
                            <div>用户名: {response.data.name}</div>
                        </>
                    )}
                    {response.message && (
                        <div>消息: {response.message}</div>
                    )}
                </div>
            )}
        </div>
    )
}