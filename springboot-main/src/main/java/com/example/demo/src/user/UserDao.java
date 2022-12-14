package com.example.demo.src.user;


import com.example.demo.src.user.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class UserDao {

    private JdbcTemplate jdbcTemplate;


    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetUserRes> getUsers(){
        String getUsersQuery = "select userIdx,nickName,email from User";
        return this.jdbcTemplate.query(getUsersQuery,
                (rs,rowNum) -> new GetUserRes(
                        rs.getInt("userIdx"),
                        rs.getString("nickName"),
                        rs.getString("email")
                ));
    }

    public GetUserRes getUsersByEmail(String email){
        String getUsersByEmailQuery = "select userIdx,nickName,email from User where email=?";
        String getUsersByEmailParams = email;
        return this.jdbcTemplate.queryForObject(getUsersByEmailQuery,
                (rs, rowNum) -> new GetUserRes(
                        rs.getInt("userIdx"),
                        rs.getString("nickName"),
                        rs.getString("email")),
                getUsersByEmailParams);
    }

    public GetUserInfoRes selectUserInfo(int userIdx) {
        String selectUserInfoQuery = "select userIdx,nickname from User where userIdx=?";
        int selectUserInfoPram = userIdx;
        return this.jdbcTemplate.queryForObject(selectUserInfoQuery,
                (rs,rowNum)-> new GetUserInfoRes(
                        rs.getInt("userIdx"),
                        rs.getString("nickname")
                ),selectUserInfoPram);
    }

    public List<GetOrderInfoRes> selectOrderInfo(int userIdx) {
        String selectOrderInfoQuery = "SELECT DISTINCT(o.orderIdx), s.name as store,m.name as menu,totalPrice\n" +
                "                FROM Store as s\n" +
                "                   join Orders as o on s.storeIdx = o.storeIdx and userIdx = ? \n" +
                "                   join (SELECT userIdx,Orders.storeIdx,name FROM Orders,Menu WHERE Orders.storeIdx = Menu.storeIdx and userIdx=?) as m on m.storeIdx = s.storeIdx\n" +
                "                  order by o.orderIdx asc";
        int selectUserOrderPram=userIdx;
        return this.jdbcTemplate.query(selectOrderInfoQuery,
                (rs,rowNum)-> new GetOrderInfoRes(
                        rs.getInt("orderIdx"),
                        rs.getString("store"),
                        rs.getString("menu"),
                        rs.getString("totalPrice")
                ),selectUserOrderPram,selectUserOrderPram);
    }


    public GetUserRes getUsersByIdx(int userIdx){
        String getUsersByIdxQuery = "select userIdx,nickName,email from User where userIdx=?";
        int getUsersByIdxParams = userIdx;
        return this.jdbcTemplate.queryForObject(getUsersByIdxQuery,
                (rs, rowNum) -> new GetUserRes(
                        rs.getInt("userIdx"),
                        rs.getString("nickName"),
                        rs.getString("email")),
                getUsersByIdxParams);
    }

    public int createUser(PostUserReq postUserReq){
        String createUserQuery = "insert into User (name, nickName, phone, email, password) VALUES (?,?,?,?,?)";
        Object[] createUserParams = new Object[]{postUserReq.getName(), postUserReq.getNickName(),postUserReq.getPhone(), postUserReq.getEmail(), postUserReq.getPassword()};
        this.jdbcTemplate.update(createUserQuery, createUserParams);

        String lastInserIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInserIdQuery,int.class);
    }

    public int checkEmail(String email){
        String checkEmailQuery = "select exists(select email from User where email = ?)";
        String checkEmailParams = email;
        return this.jdbcTemplate.queryForObject(checkEmailQuery,
                int.class,
                checkEmailParams);

    }

    public int checkUserExist(int userIdx){
        String checkUserExistQuery="select exists(select userIdx from User where userIdx=?)";
        int checkUserExistParams = userIdx;
        return this.jdbcTemplate.queryForObject(checkUserExistQuery,
                int.class,
                checkUserExistParams);
    }

    public int modifyUserName(PatchUserReq patchUserReq){
        String modifyUserNameQuery = "update User set nickName = ? where userIdx = ? ";
        Object[] modifyUserNameParams = new Object[]{patchUserReq.getNickName(), patchUserReq.getUserIdx()};

        return this.jdbcTemplate.update(modifyUserNameQuery,modifyUserNameParams);
    }



}
