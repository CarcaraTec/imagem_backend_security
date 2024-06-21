package com.carcara.imagem_backend_security.service;

import com.carcara.imagem_backend_security.model.RegisterDTO;
import com.carcara.imagem_backend_security.model.User;
import com.carcara.imagem_backend_security.model.ValueDTO;
import com.carcara.imagem_backend_security.model.sharing.*;
import com.carcara.imagem_backend_security.repository.ActionRepository;
import com.carcara.imagem_backend_security.repository.SharingConfigRepository;
import com.carcara.imagem_backend_security.repository.SharingRepository;
import com.carcara.imagem_backend_security.util.DTOEncryptor;
import com.carcara.imagem_backend_security.util.EncryptionUtil;
import com.carcara.imagem_backend_security.util.HttpUtil;
import com.carcara.imagem_backend_security.util.UsuarioLogado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class SharingService {

    @Autowired
    private SharingConfigRepository repository;
    @Autowired
    private SharingRepository sharingRepository;

    @Autowired
    private ActionRepository actionRepository;
    @Autowired
    private UsuarioLogado usuarioLogado;
    public String createWebhook(CreateWebhookDTO createWebhookDTO) throws Exception {
        SecretKey secretKey = EncryptionUtil.generateKey();

        User user = usuarioLogado.resgatarUsuario();
        Sharing sharing = new Sharing(user, createWebhookDTO.url(), convertSecretKeyToString(secretKey));
        sharingRepository.save(sharing);
        List<SharingConfig> sharingConfigList = new ArrayList<>();
        for(Long idAction : createWebhookDTO.actions()){
            Action action = actionRepository.findById(idAction).orElseThrow();
            sharingConfigList.add(new SharingConfig(sharing, action));
        }
        repository.saveAll(sharingConfigList);
        return sharing.getSharingToken();
    }

    public void compartilhar(Object object) throws Exception {
        HttpUtil httpUtil = new HttpUtil();
        List<Sharing> sharings = sharingRepository.findAll();
        for(Sharing sharing : sharings){
            try{
                if(sharing.getKey()!=null){
                    DTOEncryptor.encryptAllDTO(object,convertStringToSecretKey(sharing.getKey()));
                    httpUtil.sendHttpPost(object, sharing.getUrl());
                }
            }catch (Exception e){
                System.out.println("Não foi possivel compartilhar com: "+ sharing.getUrl());
            }

        }
    }

    public static String convertSecretKeyToString(SecretKey secretKey) {
        byte[] keyBytes = secretKey.getEncoded();
        return Base64.getEncoder().encodeToString(keyBytes);
    }

    public static SecretKey convertStringToSecretKey(String keyString) {
        byte[] decodedKey = Base64.getDecoder().decode(keyString);
        return new javax.crypto.spec.SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
    }

    public Object decriptSharing(ValueDTO value, String shareToken) throws Exception {
        Sharing sharing = sharingRepository.findBySharingToken(shareToken);

        DTOEncryptor.decryptAllDTO(value, convertStringToSecretKey(sharing.getKey()));
        return value;
    }

    public void deleteSharingByUser(Integer userId){
        sharingRepository.deleteAllByCreatedBy(userId);
    }
}
