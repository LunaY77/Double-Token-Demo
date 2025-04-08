import { useQuery } from '@tanstack/react-query'
import { fetchWithAuth } from './utils/auth/fetchWithAuth'

export default function Test() {
    const { data: userData, isLoading, refetch } = useQuery({
        queryKey: ['user-info'],
        queryFn: () => fetchWithAuth('/capi/user/info/me', {
            method: 'GET'
        }),
        enabled: false // 禁用自动查询
    })

    return (
        <div className="flex flex-col items-center justify-center min-h-screen">
            <button 
                className="px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600"
                onClick={() => refetch()} // 使用 refetch 手动触发查询
            >
                {isLoading ? '加载中...' : '获取用户信息'}
            </button>
            {userData && (
                <div className="mt-4">
                    <pre>{JSON.stringify(userData, null, 2)}</pre>
                </div>
            )}
        </div>
    )
}