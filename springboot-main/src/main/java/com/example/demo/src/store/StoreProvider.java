package com.example.demo.src.store;

import com.example.demo.config.BaseException;
import com.example.demo.src.store.model.GetStoresRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class StoreProvider {

    private final StoreDao storeDao;


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public StoreProvider(StoreDao storeDao) {
        this.storeDao = storeDao;
    }

    public List<GetStoresRes> retrieveStores() throws BaseException{
        try {

            List<GetStoresRes> getStores = storeDao.selectStores();

            return getStores;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
//    public List<GetStoresRes> retrieveStores() throws BaseException{
//        if (checkStoreExist(storeIdx)==0){
//            throw new BaseException(STORES_EMTPY_STORE_ID);
//        }
//        try {
//
//            List<GetStoresRes> getStores = storeDao.selectStores(storeIdx);
//
//            return getStores;
//        }
//        catch (Exception exception) {
//            throw new BaseException(DATABASE_ERROR);
//        }
//    }






    public int checkStoreExist(int storeIdx) throws BaseException{
        try{
            return storeDao.checkStoreExist(storeIdx);
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
