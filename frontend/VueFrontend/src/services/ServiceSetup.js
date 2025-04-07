import {userStore} from "@/stores/user.js";

/**
 *
 * @returns {{bearerTokenAuth: {type: string, accessToken: UnwrapRef<string>}, timeout: number, baseURL: string}}
 */
export function serviceConfigParams() {
    const userStorage = userStore();

    const bearerTokenAuth = {
        type: 'bearer',
        accessToken: userStorage.token,
    };
    const timeout = 1000 * 60 * 2; // 2 min timeout
    const baseURL = 'http://localhost:8080';

    return {bearerTokenAuth, timeout, baseURL};
}