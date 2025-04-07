/**
 *
 * @returns {{timeout: number, baseURL: string}}
 */
export function serviceConfigParams() {
    const timeout = 1000 * 60 * 2; // 2 min timeout
    const baseURL = 'http://localhost:8080';

    return {timeout, baseURL};
}