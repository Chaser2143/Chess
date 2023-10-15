package services;

import reqRes.LoginReq;
import reqRes.LogoutRes;

public class LogoutService {
    public LogoutRes Logout(LoginReq request){
        return new LogoutRes();
    }
}
