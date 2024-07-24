'use client'

import { useRouter } from 'next/navigation'
import { useEffect, useState } from 'react'
import { getDataFromLocalStorage, LOCAL_STORAGE_USER_DETAILS } from '../api/localStorageService'

const HomePage = () => {

    const router = useRouter();

    const [userDetails, setUserDetails] = useState({});

    useEffect(() => {
        const userInfo = getDataFromLocalStorage(LOCAL_STORAGE_USER_DETAILS);
        if (!userInfo) {
            router.push("/auth");
            return;
        }
        setUserDetails(userInfo);
    }, []);


    return (
        <div>
            <h1>{userDetails && userDetails.username}</h1>
        </div>
    )
}

export default HomePage
