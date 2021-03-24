package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {
    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialId}")
    Credential getCredential(String credentialId);

    @Select("SELECT * FROM CREDENTIALS, USERS WHERE USERS.userid = CREDENTIALS.userid and USERS.userName = #{userName}")
    List<Credential> getCredentials(String userName);


    @Delete("DELETE  FROM CREDENTIALS WHERE credentialid = #{credentialId}")
    Integer deleteCredential(Integer credentialId);

    @Update("UPDATE CREDENTIALS SET url = #{url}, userName = #{userName}, key = #{key}, password = #{password} , userId = #{userId}  WHERE credentialId = #{credentialId}")
    int updateCredential(Credential credential);


    @Insert("INSERT INTO CREDENTIALS (url, userName, key, password, userId) VALUES(#{url}, #{userName}, #{key}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int insert(Credential credential);
}
