
export const SESSION_TOKEN = "session-token";

export function saveJwtToken(token) {
    if (!token) {
        throw new Error("Invalid token!");
    }
    window.sessionStorage.setItem(SESSION_TOKEN, token);
}

export function loadJwtToken() {
    const token = window.sessionStorage.getItem(SESSION_TOKEN);
    return token;
}

export function removeJwtToken() {
    window.sessionStorage.removeItem(SESSION_TOKEN);
}