export const LOCAL_STORAGE_USER_DETAILS = "userDetails";

export function saveDataToLocalStorage(key, value) {
    localStorage.setItem(key, JSON.stringify(value));
}

export function getDataFromLocalStorage(key) {
    const itemStr = localStorage.getItem(key);
    if (!itemStr)
        return null;
    const item = JSON.parse(itemStr);
    return item;
}

export function removeDataFromLocalStorage(key) {
    localStorage.removeItem(key);
}