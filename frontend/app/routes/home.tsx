import type { Route } from "./+types/home";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import LoginButton from "~/auth/LoginButton";
import TestButton from "~/testButton";  // 修改为小写的文件名
const queryClient = new QueryClient();

export function meta({}: Route.MetaArgs) {
  return [
    { title: "New React Router App" },
    { name: "description", content: "Welcome to React Router!" },
  ];
}

export default function Home() {
  return (
    <QueryClientProvider client={queryClient}>
      <LoginButton />
      <TestButton />  {/* 修改组件名称为大写 */}
    </QueryClientProvider>
  )
}
