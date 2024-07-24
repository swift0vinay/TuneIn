'use client'

import Error from '@/components/error'
import { Button } from '@/components/ui/button'
import {
    Card,
    CardContent,
    CardFooter,
    CardHeader,
    CardTitle
} from "@/components/ui/card"
import { Input } from '@/components/ui/input'
import { useToast } from '@/components/ui/use-toast'
import { EyeIcon, EyeOffIcon } from 'lucide-react'
import { useRouter } from 'next/navigation'
import { useEffect, useState } from 'react'
import * as Yup from "yup"
import { register } from '../api/authController'
import useFetch from '../hooks/useFetch'

const RegisterUser = () => {

    const [userDetails, setUserDetails] = useState({
        username: "",
        password: "",
        confirmPassword: "",
    });

    const [validationErrors, setValidationErrors] = useState({});

    const { data, loading, error, fn: registerFn } = useFetch(register, {
        "username": userDetails.username,
        "password": userDetails.password
    });

    const [showPassword, setShowPassword] = useState(false);

    const router = useRouter();

    const { toast } = useToast();

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
            username: Yup.string().email().required("Email is required"),
            password: Yup.string().required("Password is required"),
            confirmPassword: Yup.string().required("Confirm the password again")
                .oneOf([Yup.ref("password"), null], "Passwords do not match!")
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

    const handleRegister = async () => {
        const result = await validateInputs();
        if (result) {
            await registerFn();
        }
    };

    useEffect(() => {
        if (!error && data) {
            router.push("/home");
        } else if (error) {
            toast({
                title: "Register attempt failed",
                description: error.message,
                variant: "destructive"
            });
        }
    }, [error, data]);

    const togglePasswordVisibility = () => {
        setShowPassword(!showPassword);
    };

    return (
        <Card>
            <CardHeader>
                <CardTitle className='text-4xl'>
                    Join the music community!
                </CardTitle>
            </CardHeader>
            <CardContent className='space-y-5'>
                <Input className='text-1xl' name="username" type='email' placeholder='Enter your email' value={userDetails.username}
                    onChange={handleInputChange} />
                {validationErrors && validationErrors.username && <Error message={validationErrors.username} />}

                <Input className='text-1xl' name="password" type='password' placeholder='Enter your password' value={userDetails.password}
                    onChange={handleInputChange} />
                {validationErrors && validationErrors.password && <Error message={validationErrors.password} />}

                {
                    showPassword ?
                        <div className='relative'>
                            < Input className='text-1xl' name="confirmPassword" type='text'
                                placeholder='Confirm your password' value={userDetails.confirmPassword}
                                onChange={handleInputChange} />
                            <EyeOffIcon onClick={togglePasswordVisibility} className='bg-p3 absolute top-2 right-1' />
                        </div> :
                        <div className='relative'>
                            < Input className='text-1xl' name="confirmPassword" type='password'
                                placeholder='Confirm your password' value={userDetails.confirmPassword}
                                onChange={handleInputChange} />
                            <EyeIcon onClick={togglePasswordVisibility} className='bg-p3 absolute top-2 right-1' />
                        </div>
                }
                {validationErrors && validationErrors.confirmPassword && <Error message={validationErrors.confirmPassword} />}
            </CardContent>
            <CardFooter>
                <Button className='bg-p1 w-1/4 mx-auto my-auto' onClick={handleRegister}
                    disabled={loading}>
                    {loading ? "Loading...." : "Register"}
                </Button>
            </CardFooter>
        </Card>
    )
}

export default RegisterUser
