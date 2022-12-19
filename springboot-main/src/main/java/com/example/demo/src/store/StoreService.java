package com.example.demo.src.store;

import com.example.demo.config.BaseException;
import com.example.demo.src.store.model.GetStoresRes;
import com.example.demo.src.store.model.PostReviewsReq;
import com.example.demo.src.store.model.PostReviewsRes;
import com.example.demo.src.user.UserDao;
import com.example.demo.src.user.UserProvider;
import com.example.demo.src.user.model.PatchUserReq;
import com.example.demo.src.user.model.PostUserReq;
import com.example.demo.src.user.model.PostUserRes;
import com.example.demo.utils.JwtService;
import com.example.demo.utils.SHA256;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class StoreService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final StoreDao storeDao;
    private final StoreProvider storeProvider;

    @Autowired
    public StoreService(StoreDao storeDao, StoreProvider storeProvider) {
        this.storeDao = storeDao;
        this.storeProvider = storeProvider;

    }

    public PostReviewsRes createReviews(int userIdx, int storeIdx,int menuIdx,int rating,PostReviewsReq postReviewsReq) throws BaseException{


        int reviewIdx = storeDao.insertPosts(userIdx,storeIdx,menuIdx,rating,postReviewsReq.getComment());
        return new PostReviewsRes(reviewIdx);


//        try {
//
//            int reviewIdx = storeDao.insertPosts(userIdx,postReviewsReq.getComment());
//            for (int i=0;i<postReviewsReq.getPostImgUrls().size();i++){
//                storeDao.insertPostImgs(reviewIdx, postReviewsReq.getPostImgUrls().get(i));
//            }
//            return new PostReviewsRes(reviewIdx);
//        }
//        catch (Exception exception) {
//            throw new BaseException(DATABASE_ERROR);
//        }
    }
}
