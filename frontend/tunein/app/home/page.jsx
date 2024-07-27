'use client'

import { useRouter } from 'next/navigation'
import { useEffect, useState } from 'react'
import { loadJwtToken } from '../api/jwt-service'
import { getDataFromLocalStorage, LOCAL_STORAGE_USER_DETAILS } from '../api/local-storage-service'

const HomePage = () => {

    const router = useRouter();

    const [token, setToken] = useState({});
    const [userDetails, setUserDetails] = useState({});

    useEffect(() => {
        const userToken = loadJwtToken();
        if (!userToken) {
            router.push("/auth");
            return;
        }
        setToken(userToken);
        const userDetails = getDataFromLocalStorage(LOCAL_STORAGE_USER_DETAILS);
        if (!userDetails) {
            router.push("/auth");
            return;
        }
        setUserDetails(userDetails);
    }, []);


    return (
        <div>
            <h1>{userDetails.id}</h1>
        </div>
    )
}

export default HomePage
