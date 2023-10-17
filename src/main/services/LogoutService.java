package services;

import reqRes.LoginReq;
import reqRes.LogoutRes;

/**
 * Completes Log Out API Request
 */
public class LogoutService {

    /**
     * Logs out the user represented by the authToken
     * @param request from Log Out API
     * @return LogOut Response
     */
    public LogoutRes Logout(LoginReq request){
        return new LogoutRes();
    }
}
