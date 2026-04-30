import type { UserType } from "@/models/user";

let currentUser: UserType;

const setCurreentUserState = (user: UserType) => {
    currentUser = user;
}

const getCurrentUserState = () => {
    return currentUser;
}

export {
    setCurreentUserState,
    getCurrentUserState,
}