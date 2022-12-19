package com.example.demo.src.store;

import com.example.demo.src.store.model.GetStoreImgRes;
import com.example.demo.src.store.model.GetStoresRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;


@Repository
public class StoreDao {

    private JdbcTemplate jdbcTemplate;
    private List<GetStoreImgRes> getStoreImgRes;


    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int checkStoreExist(int storeIdx){
        String checkUserExistQuery="select exists(select storeIdx from Store where storeIdx=?)";
        int checkUserExistParams = storeIdx;
        return this.jdbcTemplate.queryForObject(checkUserExistQuery,
                int.class,
                checkUserExistParams);
    }

    public List<GetStoresRes> selectStores() {
        String selectStoresQuery = "select storeIdx, type, name, minimumCost,ratingAvg from Store";
        return this.jdbcTemplate.query(selectStoresQuery,
                (rs,rowNum)-> new GetStoresRes(
                        rs.getInt("storeIdx"),
                        rs.getString("type"),
                        rs.getString("name"),
                        rs.getInt("minimumCost"),
                        rs.getFloat("ratingAvg"),
                        getStoreImgRes=this.jdbcTemplate.query("select storeImgIdx,storeIdx, imgUrl from StoreImg",
                                (rk,rownum)-> new GetStoreImgRes(
                                        rk.getInt("storeImgIdx"),
                                        rk.getInt("storeIdx"),
                                        rk.getString("imgUrl")
                                ))
                ));
    }

    public int insertPosts(int userIdx, int storeIdx,int menuIdx,int rating,String comment){
        String insertReviewQuery = "insert into Review(userIdx, storeIdx,menuIdx,rating,comment) VALUES (?,?,?,?,?)";

        Object []insertReviewParams = new Object[]{userIdx, storeIdx,menuIdx,rating,comment};
        //post이므로 update
        this.jdbcTemplate.update(insertReviewQuery,
                insertReviewParams);

        //가장 마지막에 들어간 idx값을 return
        String lastInsertIdxQuery="select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdxQuery, int.class);
    };

//    public int insertPostImgs(int reviewIdx, PostImgUrlReq postImgUrlReq){
//        String insertPostImgsQuery = "insert into PostImgUrl(reviewIdx, imgUrl) VALUES (?,?)";
//
//        Object []insertReviewParams = new Object[] {reviewIdx, postImgUrlReq.getImgUrl()};
//        //post이므로 update
//        this.jdbcTemplate.update(insertPostImgsQuery,
//                insertReviewParams);
//
//        //가장 마지막에 들어간 idx값을 return
//        String lastInsertIdxQuery="select last_insert_id()";
//        return this.jdbcTemplate.queryForObject(lastInsertIdxQuery, int.class);
//    };

//    public List<GetStoresRes> selectStores(int storeIdx) {
//        String selectStoresQuery = "select storeIdx, type, name, minimumCost,ratingAvg from Store";
//        int selectStoresPram = storeIdx;
//        return this.jdbcTemplate.query(selectStoresQuery,
//                (rs,rowNum)-> new GetStoresRes(
//                        rs.getInt("storeIdx"),
//                        rs.getString("type"),
//                        rs.getString("name"),
//                        rs.getInt("minimumCost"),
//                        rs.getFloat("ratingAvg"),
//                        getStoreImgRes=this.jdbcTemplate.query("select storeImgIdx,storeIdx, imgUrl from StoreImg where storeIdx=?",
//                                (rk,rownum)-> new GetStoreImgRes(
//                                        rk.getInt("storeImgIdx"),
//                                        rk.getInt("storeIdx"),
//                                        rk.getString("imgUrl")
//                                ),rs.getInt("storeIdx"))
//                ));
//    }
}
