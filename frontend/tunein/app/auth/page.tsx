import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import LoginUser from './loginUser';
import RegisterUser from './registerUser';

const AuthForm = () => {
    return (
        <div className='mt-20 flex flex-col items-center gap-10'>
            <Tabs defaultValue="login" className="w-1/2">
                <TabsList className="grid w-full grid-cols-2">
                    <TabsTrigger value="register">Register</TabsTrigger>
                    <TabsTrigger value="login">Login</TabsTrigger>
                </TabsList>
                <TabsContent value="register">
                    <RegisterUser />
                </TabsContent>
                <TabsContent value="login">
                    <LoginUser />
                </TabsContent>
            </Tabs>
        </div>
    )
}

export default AuthForm
