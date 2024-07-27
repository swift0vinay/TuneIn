import { LOGIN_URL, REGISTER_URL } from "./api-info";
import { removeJwtToken, saveJwtToken } from "./jwt-service";
import { LOCAL_STORAGE_USER_DETAILS, removeDataFromLocalStorage, saveDataToLocalStorage } from "./local-storage-service";

export async function register(userDetails) {
    removeJwtToken();
    removeDataFromLocalStorage(LOCAL_STORAGE_USER_DETAILS);

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
    const data = {
        "id": userData.id
    };

    saveJwtToken(userData.token);
    saveDataToLocalStorage(LOCAL_STORAGE_USER_DETAILS, data);
    return data;
}

export async function login(userDetails) {
    removeJwtToken();
    removeDataFromLocalStorage(LOCAL_STORAGE_USER_DETAILS);

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
    const data = {
        "id": userData.id
    };

    saveJwtToken(userData.token);
    saveDataToLocalStorage(LOCAL_STORAGE_USER_DETAILS, data);
    return userData;
}