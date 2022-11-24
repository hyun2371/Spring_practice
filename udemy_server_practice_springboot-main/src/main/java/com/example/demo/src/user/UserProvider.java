package com.example.demo.src.user;


import com.example.demo.config.BaseException;
import com.example.demo.src.user.model.GetOrderInfoRes;
import com.example.demo.src.user.model.GetUserInfoRes;
import com.example.demo.src.user.model.GetUserOrdersRes;
import com.example.demo.src.user.model.GetUserRes;
import com.example.demo.utils.JwtService;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;
import static com.example.demo.config.BaseResponseStatus.USERS_EMPTY_USER_ID;

//Provider : Read의 비즈니스 로직 처리
@Service
public class UserProvider {

    private final UserDao userDao;
    private final JwtService jwtService;


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public UserProvider(UserDao userDao, JwtService jwtService) {
        this.userDao = userDao;
        this.jwtService = jwtService;
    }

    public GetUserOrdersRes retrieveUserOrders(int userIdx) throws BaseException{
        if (checkUserExist(userIdx)==0){
            throw new BaseException(USERS_EMPTY_USER_ID);
        }
        try {
            GetUserInfoRes getUserInfo = userDao.selectUserInfo(userIdx);
            List<GetOrderInfoRes> getOrderInfo = userDao.selectOrderInfo(userIdx);
            GetUserOrdersRes getOrdersRes = new GetUserOrdersRes(getUserInfo,getOrderInfo);
            return getOrdersRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }


    public GetUserRes getUsersByEmail(String email) throws BaseException{
        try{
            GetUserRes getUsersRes = userDao.getUsersByEmail(email);
            return getUsersRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
                    }


    public GetUserRes getUsersByIdx(int userIdx) throws BaseException{
        try{
            GetUserRes getUsersRes = userDao.getUsersByIdx(userIdx);
            return getUsersRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }


    public int checkEmail(String email) throws BaseException{
        try{
            return userDao.checkEmail(email);
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }


    public int checkUserExist(int userIdx) throws BaseException{
        try{
            return userDao.checkUserExist(userIdx);
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
