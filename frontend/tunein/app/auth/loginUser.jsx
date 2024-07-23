'use client'

import { Button } from '@/components/ui/button'
import {
    Card,
    CardContent,
    CardFooter,
    CardHeader,
    CardTitle,
} from "@/components/ui/card"
import { Input } from '@/components/ui/input'
import { useRouter } from 'next/navigation'
import { useState } from 'react'
import * as Yup from "yup"
import Error from "../../components/error"
import { login } from '../api/authController'
import useFetch from '../hooks/useFetch'
import { EyeIcon, EyeOffIcon } from 'lucide-react'

const LoginUser = () => {

    const [userDetails, setUserDetails] = useState({
        username: "",
        password: ""
    });

    const [validationErrors, setValidationErrors] = useState({});

    const { data, loading, error, fn: loginFn } = useFetch(login, userDetails);

    const [showPassword, setShowPassword] = useState(false);

    const router = useRouter()

    const handleInputChange = (e) => {
        const { name, value } = e.target
        setUserDetails((prev) => ({
            ...prev,
            [name]: value
        }));
    };

    async function validateInputs() {
        setValidationErrors({});
        const schema = Yup.object().shape({
            username: Yup.string().required("Username is required"),
            password: Yup.string().required("Password is required")
        });
        try {
            await schema.validate(userDetails, { abortEarly: false });
            return true;
        } catch (err) {
            const errors = {};
            await err?.inner?.forEach(err => {
                errors[err.path] = err.message;
            });
            setValidationErrors(errors);
        }
        return false;
    }

    const handleLogin = async () => {
        const result = await validateInputs();
        if (result) {
            await loginFn();
            router.push("/home");
        }
    };

    const togglePasswordVisibility = () => {
        setShowPassword(!showPassword);
    };

    return (
        <Card>
            <CardHeader>
                <CardTitle className='text-4xl'>
                    Welcome Back!
                </CardTitle>
                {error && <Error message={error.message} />}
            </CardHeader>
            <CardContent className='space-y-5'>
                <Input className='text-1xl' name="username" type='text'
                    placeholder='Enter your email or user handle' value={userDetails.username}
                    onChange={handleInputChange} />
                {validationErrors && validationErrors.username && <Error message={validationErrors.username} />}

                {
                    showPassword ?
                        <div className='relative'>
                            < Input className='text-1xl' name="password" type='text'
                                placeholder='Enter your password' value={userDetails.password}
                                onChange={handleInputChange} />
                            <EyeOffIcon onClick={togglePasswordVisibility} className='bg-p3 absolute top-2 right-1' />
                        </div> :
                        <div className='relative'>
                            < Input className='text-1xl' name="password" type='password'
                                placeholder='Enter your password' value={userDetails.password}
                                onChange={handleInputChange} />
                            <EyeIcon onClick={togglePasswordVisibility} className='bg-p3 absolute top-2 right-1' />
                        </div>
                }
                {validationErrors && validationErrors.password && <Error message={validationErrors.password} />}
            </CardContent>
            <CardFooter>
                <Button className='bg-p1 w-1/4 mx-auto my-auto' onClick={handleLogin}
                    disabled={loading}>
                    {loading ? "Loading...." : "Login"}
                </Button>
            </CardFooter>
        </Card>
    )
}

export default LoginUser
