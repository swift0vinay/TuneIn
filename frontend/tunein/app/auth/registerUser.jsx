import { Button } from '@/components/ui/button'
import {
    Card,
    CardContent,
    CardFooter,
    CardHeader,
    CardTitle
} from "@/components/ui/card"
import { Input } from '@/components/ui/input'

const RegisterUser = () => {
    return (
        <Card>
            <CardHeader>
                <CardTitle className='text-4xl'>
                    Join the music community!
                </CardTitle>
            </CardHeader>
            <CardContent className='space-y-5'>
                <Input className='text-1xl' name="email" type='email' placeholder='Enter your email' />
                <Input className='text-1xl' name="password" type='password' placeholder='Enter your password' />
                <Input className='text-1xl' name="confirm_password" type='password' placeholder='Confirm your password' />
            </CardContent>
            <CardFooter>
                <Button className='bg-p1 w-1/4 mx-auto my-auto' >
                    Register
                </Button>
            </CardFooter>
        </Card>
    )
}

export default RegisterUser
