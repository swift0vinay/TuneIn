import { LOGIN_URL, REGISTER_URL } from "./api-info";
import { LOCAL_STORAGE_USER_DETAILS, saveDataToLocalStorage } from "./localStorageService";

// Default 10 min
const DEFAULT_TTL = 600000;

export async function register(userDetails) {
    const response = await fetch(REGISTER_URL, {
        method: "POST",
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(userDetails),
        mode: "cors"
    });

    if (!response.ok) {
        const errData = await response.json();
        console.error(errData.timestamp + " Register failed - " + errData.title);
        throw new Error(errData.title);
    }

    const userData = await response.json();
    userDetails = {
        ...userDetails,
        "id": userData.id
    };
    saveDataToLocalStorage(LOCAL_STORAGE_USER_DETAILS, userDetails, DEFAULT_TTL);
    return userData;
}

export async function login(userDetails) {
    const response = await fetch(LOGIN_URL, {
        method: "POST",
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(userDetails),
        mode: "cors"
    });

    if (!response.ok) {
        const errData = await response.json();
        console.error(errData.timestamp + " Login failed - " + errData.title);
        throw new Error(errData.title);
    }

    const userData = await response.json();
    userDetails = {
        ...userDetails,
        "id": userData.id
    };
    saveDataToLocalStorage(LOCAL_STORAGE_USER_DETAILS, userDetails, DEFAULT_TTL);
    return userData;
}