import myAxios from "@/plugins/myAxios"
import { getCurrentUserState, setCurreentUserState } from "@/states/userStates";

export const getCurrentUser = async () => {
    // const currentUser = getCurrentUserState();

    // if (currentUser) {
    //     return currentUser;
    // }
    // 不存在则从后端获取
    const res = await myAxios.get('/user/current');
    if (res.code === 0) {
        setCurreentUserState(res.data);
        return res.data;
    }
    return null;
}
