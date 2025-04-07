import type { Route } from "./+types/home";
import { Welcome } from "../welcome/welcome";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import LoginButton from "~/auth/LoginButton";
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
    </QueryClientProvider>
  )
}
