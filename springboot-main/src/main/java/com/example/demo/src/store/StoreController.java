package com.example.demo.src.store;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.store.model.GetStoresRes;
import com.example.demo.src.store.model.PostReviewsReq;
import com.example.demo.src.store.model.PostReviewsRes;
import com.example.demo.src.user.UserProvider;
import com.example.demo.src.user.UserService;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.POST_USERS_EMPTY_EMAIL;
import static com.example.demo.config.BaseResponseStatus.POST_USERS_INVALID_EMAIL;
import static com.example.demo.utils.ValidationRegex.isRegexEmail;

@RestController
@RequestMapping("/stores") //controller 모두에 있는 uri에 기본적으로 들어감
public class StoreController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final StoreProvider storeProvider;
    @Autowired
    private final StoreService storeService;


    public StoreController(StoreProvider storeProvider, StoreService storeService){
        this.storeProvider = storeProvider;
        this.storeService = storeService;

    }
    @ResponseBody
    @GetMapping("") // (GET) 127.0.0.1:9000/users/:userIdx
    public BaseResponse<List<GetStoresRes>> getStores() {
        try{
            List<GetStoresRes> getStoresRes = storeProvider.retrieveStores();
            return new BaseResponse<>(getStoresRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @PostMapping("") // (GET) 127.0.0.1:9000/users/:userIdx
    public BaseResponse<PostReviewsRes> createReviews(@RequestBody PostReviewsReq postReviewsReq) throws BaseException {
        try{
            //validation 처리
            if (postReviewsReq.getComment().length()>450){
                return new BaseResponse<>(BaseResponseStatus.POST_POSTS_INVALID_CONTENTS);
            }
            PostReviewsRes postReviewsRes = storeService.createReviews(postReviewsReq.getUserIdx(),postReviewsReq.getStoreIdx(),postReviewsReq.getMenuIdx(),postReviewsReq.getRating(),postReviewsReq); //jwt로 userIdx를 받을 수 있기 때문
            return new BaseResponse<>(postReviewsRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }




    }

//    @ResponseBody
//    @GetMapping("") // (GET) 127.0.0.1:9000/users/:userIdx
//    public BaseResponse<List<GetStoresRes>> getStores(@RequestParam int storeIdx) {
//        try{
//            List<GetStoresRes> getStoresRes = storeProvider.retrieveStores(storeIdx);
//            return new BaseResponse<>(getStoresRes);
//        } catch(BaseException exception){
//            return new BaseResponse<>((exception.getStatus()));
//        }
//    }


}
