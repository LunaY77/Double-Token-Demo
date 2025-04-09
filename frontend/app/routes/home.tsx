import type { Route } from "./+types/home";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import LoginButton from "~/auth/LoginButton";
import TestButton from "~/TestButton";  // 修改为小写的文件名
export const queryClient = new QueryClient();

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
      <TestButton /> 
    </QueryClientProvider>
  )
}
