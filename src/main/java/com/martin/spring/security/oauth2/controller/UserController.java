package com.martin.spring.security.oauth2.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import static com.martin.spring.security.oauth2.config.Configs.JDBC_DRIVER;
import static com.martin.spring.security.oauth2.config.Configs.DB_URL;
import static com.martin.spring.security.oauth2.config.Configs.USER;
import static com.martin.spring.security.oauth2.config.Configs.PASS;
import static utils.StringUtils.convertToJson;
import java.sql.*;
import java.util.LinkedHashMap;

@RestController
@RequestMapping("/secured/user")
public class UserController {
    private static final String FETCH_PROFILE_FIELDS = "SELECT * FROM user_profile WHERE username = ?";

    @RequestMapping(value = "/profile", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody
    String getUserProfile(@RequestParam String username) { Connection con = null;
        PreparedStatement statement = null;
        LinkedHashMap<String, String> result = new LinkedHashMap<>();
        try{
            //STEP 2: Register JDBC driver
            Class.forName(JDBC_DRIVER);
            //STEP 3: Open a connection
            con = DriverManager.getConnection(DB_URL, USER, PASS);
            //STEP 4: Execute a query
            statement = con.prepareStatement(FETCH_PROFILE_FIELDS);
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            //STEP 5: Extract data from result set
            while(rs.next()){
                //Retrieve by column name
                result.put("id", String.valueOf(rs.getInt("id")));
                result.put("name", rs.getString("name"));
                result.put("username", rs.getString("username"));
                result.put("email", rs.getString("email"));
                result.put("bio", rs.getString("bio"));
            }
            rs.close();
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(statement!=null)
                    con.close();
            }catch(SQLException se){
            }// do nothing
            try{
                if(con!=null)
                    con.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try
        return convertToJson(result);
    }
}