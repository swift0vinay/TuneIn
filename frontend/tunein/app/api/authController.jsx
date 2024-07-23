import { LOGIN_URL, REGISTER_URL } from "./api-info";

export async function register(userDetails) {
    const output = await fetch(REGISTER_URL, {
        method: "POST",
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(userDetails),
        mode: "cors"
    }).then(async res => {
        if (!res.ok) {
            const errRes = await res.json();
            console.error(errRes.timestamp + " Register failed - " + errRes.title);
            throw new Error(errRes.title);
        }
        const result = await res.json();
        return result;
    }).catch(err => {
        console.error("Register failed", err);
        throw err;
    });
    return output;
}


export async function login(userDetails) {
    const output = await fetch(LOGIN_URL, {
        method: "POST",
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(userDetails),
        mode: "cors"
    }).then(async res => {
        if (!res.ok) {
            const errRes = await res.json();
            console.error(errRes.timestamp + " Login failed - " + errRes.title);
            throw new Error(errRes.title);
        }
        console.log("Login success!");
        const result = await res.json();
        return result;
    }).catch(err => {
        console.error("Login failed", err);
        throw new Error("Login failed! Please try again later!");
    });
    userDetails = {
        ...userDetails,
        "id": output.id
    };
    localStorage.setItem("userDetails", JSON.stringify(userDetails));
    return output;
}