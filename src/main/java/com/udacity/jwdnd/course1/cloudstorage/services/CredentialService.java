package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.FileForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class CredentialService {
    CredentialMapper credentialMapper;
    UserMapper userMapper;
    EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, UserMapper userMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.userMapper = userMapper;
        this.encryptionService = encryptionService;
    }

    public void deleteCredential(Integer credentialId){
        credentialMapper.deleteCredential(credentialId);
    }

    public void addCredential(Credential credential){

        if (credential.getCredentialId() == null){
            credentialMapper.insert(credential);
        }else{
            credentialMapper.updateCredential(credential);
        }

    }

    public List<Credential> getCredentials(String userName){

        List<Credential> creds = credentialMapper.getCredentials(userName);


        for(int i=0; i < creds.size(); i++) {
            String encodedKey = creds.get(i).getKey();
            String decryptedPassword = encryptionService.decryptValue(creds.get(i).getPassword(), encodedKey);
            creds.get(i).setDecryptedPassword(decryptedPassword);
        }
        return creds;
    }
}
